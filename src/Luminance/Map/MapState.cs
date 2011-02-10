/*
Copyright (C) 2008 by:
    Stephen Damm
    Greg Logan
    Kyle Fisher
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the <organization> nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
using System;
using System.Collections.Generic;
using System.Text;

using GameAI;

namespace Luminance.Map
{
    class MapState : GraphState, ICloneable
    {
        // State info
        private GameUnit[,] map;
        public GameUnit[,] Map
        {
            get { return map; }
        }
        private int cost;
        private static int width, height;

        // Color keys for multicoloured search positions
        private const int W = 0, R = 1, G = 2, B = 3;

        // Sugar for keeping indices correct
        private const int COL = 0, ROW = 1;

        // Row,col,dir that the prism is on
        private int pRow, pCol;
        private Direction pDirection;
        private Direction pDirectionAuto
        { 
            get { return pDirection; }
            set 
            {
                pDirection = value;

                sRow[R] = sRow[G] = sRow[B] = pRow;
                sCol[R] = sCol[G] = sCol[B] = pCol;

                //Set the light directions too
                switch (value)
                {
                    case Direction.NORTH:
                        sDirection[R] = Direction.EAST;
                        sDirection[G] = value;
                        sDirection[B] = Direction.WEST;
                        break;
                    case Direction.EAST:
                        sDirection[R] = Direction.SOUTH;
                        sDirection[G] = value;
                        sDirection[B] = Direction.NORTH;
                        break;
                    case Direction.SOUTH:
                        sDirection[R] = Direction.WEST;
                        sDirection[G] = value;
                        sDirection[B] = Direction.EAST;
                        break;
                    case Direction.WEST:
                        sDirection[R] = Direction.NORTH;
                        sDirection[G] = value;
                        sDirection[B] = Direction.SOUTH;
                        break;
                    default:
                        sDirection[R] = value;
                        sDirection[G] = value;
                        sDirection[B] = value;
                        break;
                }
            }
        }

        private static Direction emdir;
        public static Direction EmitterDirection
        {
            get { return emdir; }
        }

        // Row,col,dir of the last coloured search position
        // W will be initialized to the emitter, RGB the prism upon placement
        private int[] sRow, sCol;
        private Direction[] sDirection;

        // A visited list so we can detect strange mirror cycles
        private static bool[,] visited;

        private static bool[] _goal = { false, false, false, false };
        private static GameUnit[] _goals = { GameUnit.EMPTY, GameUnit.GOAL_RED, GameUnit.GOAL_GREEN, GameUnit.GOAL_BLUE };

        private List<string> instruction;

        // Initial constructor
        public MapState(GameUnit[,] map, int eRow, int eCol, Direction eDirection)
        {
            this.map = (GameUnit[,])map.Clone();
            this.cost = 0;
            width = map.GetLength(0);
            height = map.GetLength(1);
            visited = new bool[width, height];
            
            this.sDirection = new Direction[4];
            this.sRow = new int[4];
            this.sCol = new int[4];

            MapState.emdir = eDirection;
            this.sDirection[W] = eDirection;
            this.sRow[W] = eRow;
            this.sCol[W] = eCol;

            for (int i = R; i <= B; i++)
            {
                this.sDirection[i] = Direction.NONE;
                this.sRow[i] = this.sCol[i] = -1;
            }

            this.pCol = this.pRow = -1;
            this.pDirection = Direction.NONE;

            this.instruction = new List<string>(6);
        }

        // Used by clone to force explicit settings
        private MapState(MapState other)
        {
            this.map = (GameUnit[,])other.map.Clone();
            this.cost = other.cost + 1;
            
            this.sDirection = (Direction[])other.sDirection.Clone();
            this.sRow = (int[])other.sRow.Clone();
            this.sCol = (int[])other.sCol.Clone();

            this.pRow = other.pRow;
            this.pCol = other.pCol;
            this.pDirection = other.pDirection;

            this.instruction = new List<string>(other.instruction);
        }

        public object Clone() { return new MapState(this); }
        public int Cost() { return cost; }

        public GraphState[] Successors()
        {
            List<GraphState> successors = new List<GraphState>();

            Console.WriteLine("=== Successors "+cost+" ===");
            MapGenerator.PrintMap(Console.Out, map);
            Console.WriteLine("=== After ===");

            for (int i = W; i <= B; i++)
                ExploreColor(successors, i);

            MapGenerator.PrintMap(Console.Out, map);
            Console.WriteLine("=== /Successors ===");

            return successors.ToArray();
        }

        // Searches through the specified color trying to find a solution
        private void ExploreColor(List<GraphState> successors, int color)
        {
            // If we haven't placed a prism, we can't explore the non-white spectrum
            if (pRow == -1 && (color == R || color == G || color == B)) return;

            // We've placed a prism, can't explore white
            if (pRow != -1 && color == W) return;

            GameUnit unit;
            MapState next;

            switch (sDirection[color])
            {
                case Direction.NORTH:
                    for (int row = sRow[color] - 1; row >= 0; row--)
                    {
                        if (map[sCol[color], row] == GameUnit.EMPTY)
                        {
                            //Console.WriteLine("(" + sCol[color] + "," + row + ") valid for item.");

                            unit = GameUnit.MIRRORNESW;
                            next = (MapState)Clone();
                            next.map[sCol[color], row] = unit;
                            next.sCol[color] = sCol[color];
                            next.sRow[color] = row;
                            next.sDirection[color] = ChangeDirection(unit, this.sDirection[color]);
                            next.instruction.Add("Placed NESW at [" + sCol[color] + "," + row + "] C="+color);
                            successors.Add(next);

                            unit = GameUnit.MIRRORNWSE;
                            next = (MapState)Clone();
                            next.map[sCol[color], row] = unit;
                            next.sCol[color] = sCol[color];
                            next.sRow[color] = row;
                            next.sDirection[color] = ChangeDirection(unit, this.sDirection[color]);
                            next.instruction.Add("Placed NWSE at [" + sCol[color] + "," + row + "] C=" + color);
                            successors.Add(next);

                            if (color == W && pRow == -1 && row > 0 && sCol[color] > 0 && sCol[color] < (width-1))
                            {
                                unit = GameUnit.PRISM;
                                next = (MapState)Clone();
                                next.cost--; // Prisms don't increase cost count
                                next.map[sCol[W], row] = unit;

                                next.pCol = sCol[W];
                                next.pRow = row;
                                next.pDirectionAuto = this.sDirection[color];

                                next.sCol[W] = -1;
                                next.sRow[W] = -1;
                                next.sDirection[W] = Direction.NONE;
                                next.instruction.Add("Placed PRISM at [" + next.pCol + "," + next.pRow + "] C=" + color);
                                successors.Add(next);
                            }

                            map[sCol[color], row] = GameUnit.LIGHT;
                        }
                        else break; // we hit something
                    }
                    break;
                case Direction.SOUTH:
                    for (int row = sRow[color] + 1; row < height; row++)
                    {
                        if (map[sCol[color], row] == GameUnit.EMPTY)
                        {
                            //Console.WriteLine("(" + sCol[color] + "," + row + ") valid for item.");

                            unit = GameUnit.MIRRORNESW;
                            next = (MapState)Clone();
                            next.map[sCol[color], row] = unit;
                            next.sCol[color] = sCol[color];
                            next.sRow[color] = row;
                            next.sDirection[color] = ChangeDirection(unit, this.sDirection[color]);
                            next.instruction.Add("Placed NESW at [" + sCol[color] + "," + row + "] C=" + color);
                            successors.Add(next);

                            unit = GameUnit.MIRRORNWSE;
                            next = (MapState)Clone();
                            next.map[sCol[color], row] = unit;
                            next.sCol[color] = sCol[color];
                            next.sRow[color] = row;
                            next.sDirection[color] = ChangeDirection(unit, this.sDirection[color]);
                            next.instruction.Add("Placed NWSE at [" + sCol[color] + "," + row + "] C=" + color);
                            successors.Add(next);

                            if (color == W && pRow == -1 && row < (height-1) && sCol[color] > 0 && sCol[color] < (width-1))
                            {
                                unit = GameUnit.PRISM;
                                next = (MapState)Clone();
                                next.cost--; // Prisms don't increase cost count
                                next.map[sCol[W], row] = unit;

                                next.pCol = sCol[W];
                                next.pRow = row;
                                next.pDirectionAuto = this.sDirection[color];

                                next.sCol[W] = -1;
                                next.sRow[W] = -1;
                                next.sDirection[W] = Direction.NONE;
                                next.instruction.Add("Placed PRISM at [" + next.pCol + "," + next.pRow + "] C=" + color);
                                successors.Add(next);
                            }

                            map[sCol[color], row] = GameUnit.LIGHT;
                        }
                        else break;
                    }
                    break;
                case Direction.EAST:
                    for (int col = sCol[color] + 1; col < width; col++)
                    {
                        if (map[col, sRow[color]] == GameUnit.EMPTY)
                        {
                            //Console.WriteLine("(" + col + "," + sRow[color] + ") valid for item.");

                            unit = GameUnit.MIRRORNESW;
                            next = (MapState)Clone();
                            next.map[col, sRow[color]] = unit;
                            next.sCol[color] = col;
                            next.sRow[color] = sRow[color];
                            next.sDirection[color] = ChangeDirection(unit, this.sDirection[color]);
                            next.instruction.Add("Placed NESW at [" + col + "," + sRow[color] + "] C=" + color);
                            successors.Add(next);

                            unit = GameUnit.MIRRORNWSE;
                            next = (MapState)Clone();
                            next.map[col, sRow[color]] = unit;
                            next.sCol[color] = col;
                            next.sRow[color] = sRow[color];
                            next.sDirection[color] = ChangeDirection(unit, this.sDirection[color]);
                            next.instruction.Add("Placed NWSE at [" + col + "," + sRow[color] + "] C=" + color);
                            successors.Add(next);

                            if (color == W && pRow == -1 && col < (width-1) && sRow[color] > 0 && sRow[color] < (height-1))
                            {
                                unit = GameUnit.PRISM;
                                next = (MapState)Clone();
                                next.cost--; // Prisms don't increase cost count
                                next.map[col, sRow[W]] = unit;

                                next.pCol = col;
                                next.pRow = sRow[W];
                                next.pDirectionAuto = this.sDirection[color];

                                next.sCol[W] = -1;
                                next.sRow[W] = -1;
                                next.sDirection[W] = Direction.NONE;
                                next.instruction.Add("Placed PRISM at [" + next.pCol + "," + next.pRow + "] C=" + color);
                                successors.Add(next);
                            }

                            map[col, sRow[color]] = GameUnit.LIGHT;
                        }
                        else break;
                    }
                    break;
                case Direction.WEST:
                    for (int col = sCol[color] - 1; col >= 0; col--)
                    {
                        if (map[col, sRow[color]] == GameUnit.EMPTY)
                        {
                            //Console.WriteLine("(" + col + "," + sRow[color] + ") valid for item.");

                            unit = GameUnit.MIRRORNESW;
                            next = (MapState)Clone();
                            next.map[col, sRow[color]] = unit;
                            next.sCol[color] = col;
                            next.sRow[color] = sRow[color];
                            next.sDirection[color] = ChangeDirection(unit, this.sDirection[color]);
                            next.instruction.Add("Placed NESW at [" + col + "," + sRow[color] + "] C=" + color);
                            successors.Add(next);

                            unit = GameUnit.MIRRORNWSE;
                            next = (MapState)Clone();
                            next.map[col, sRow[color]] = unit;
                            next.sCol[color] = col;
                            next.sRow[color] = sRow[color];
                            next.sDirection[color] = ChangeDirection(unit, this.sDirection[color]);
                            next.instruction.Add("Placed NWSE at [" + col + "," + sRow[color] + "] C=" + color);
                            successors.Add(next);

                            if (color == W && pRow == -1 && col > 0 && sRow[color] > 0 && sRow[color] < (height - 1))
                            {
                                unit = GameUnit.PRISM;
                                next = (MapState)Clone();
                                next.cost--; // Prisms don't increase cost count
                                next.map[col, sRow[W]] = unit;

                                next.pCol = col;
                                next.pRow = sRow[W];
                                next.pDirectionAuto = this.sDirection[color];

                                next.sCol[W] = -1;
                                next.sRow[W] = -1;
                                next.sDirection[W] = Direction.NONE;
                                next.instruction.Add("Placed PRISM at [" + next.pCol + "," + next.pRow + "] C=" + color);
                                successors.Add(next);
                            }

                            map[col, sRow[color]] = GameUnit.LIGHT;
                        }
                        else break;
                    }
                    break;
                default:
                    break;
            }
        }

        // Runs through the map like a state machine.
        // If hits a goal
        public bool LeafNode()
        {
            // If we haven't laid the prism, we can't possibly be done.            
            if (pRow == -1) return false;

            for (int C = R; C <= B; C++)
            {
                // Set visited to false
                for (int w = 0; w < width; w++)
                    for (int h = 0; h < height; h++)
                        visited[w, h] = false;
                _goal[C] = false;

                // Direction the light is travelling
                Direction direction = sDirection[C];
                int col = sCol[C];
                int row = sRow[C];

                // Walk the map (like a state machine) to check solvability
                while (direction != Direction.NONE)
                {
                    // Adjust position according to direction
                    switch (direction)
                    {
                        case Direction.EAST:
                            col++; break;
                        case Direction.NORTH:
                            row--; break;
                        case Direction.WEST:
                            col--; break;
                        case Direction.SOUTH:
                            row++; break;
                    }

                    // Check
                    if (col < 0 || row < 0 || col >= width || row >= height)
                        return false;
                    else if (visited[col, row]) break;
                    else if (map[col, row] == _goals[C])
                    {
                        _goal[C] = true;
                        break;
                    }
                    else
                    {
                        visited[col, row] = true;
                        direction = ChangeDirection(map[col, row], direction);
                    }
                }
            }

            // Have we solved all goals?
            return _goal[R] && _goal[G] && _goal[B];
        }

        // Controls the direction of light when a unit is encountered
        private static Direction ChangeDirection(GameUnit unit, Direction direction)
        {
            switch (unit)
            {
                case GameUnit.MIRRORNESW:
                    if (direction == Direction.NORTH) direction = Direction.WEST;
                    else if (direction == Direction.EAST) direction = Direction.SOUTH;
                    else if (direction == Direction.SOUTH) direction = Direction.EAST;
                    else if (direction == Direction.WEST) direction = Direction.NORTH;
                    else direction = Direction.NONE;
                    break;
                case GameUnit.MIRRORNWSE:
                    if (direction == Direction.SOUTH) direction = Direction.WEST;
                    else if (direction == Direction.EAST) direction = Direction.NORTH;
                    else if (direction == Direction.NORTH) direction = Direction.EAST;
                    else if (direction == Direction.WEST) direction = Direction.SOUTH;
                    else direction = Direction.NONE;
                    break;
                case GameUnit.EMPTY:
                    break;
                case GameUnit.LIGHT:
                    break;
                default:
                    direction = Direction.NONE;
                    break;
            }
            return direction;
        }

        public string HashString()
        {
            StringBuilder str = new StringBuilder(width * height);
            for (int r = 0; r < height; r++)
                for (int c = 0; c < width; c++)
                    str.Append((int)map[c, r]);
            return str.ToString();
        }

        public void PrintInstructions()
        {
            foreach (string i in instruction)
                Console.WriteLine(i);
        }
    }
}
