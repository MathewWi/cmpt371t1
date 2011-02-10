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
using System.IO;
using System.Threading;

using GameAI;

namespace Luminance.Map
{

    // Psuedo-units to help in the level generation
    // WARNING: ORDER HERE MATTERS!
    public enum GameUnit : int
    {
        EMPTY = Node.FREE,
        BLOCK = Node.BLOCKED,
        EMITTER = Node.EMITTER,
        GOAL = Node.GOAL,
        GOAL_RED = Node.GOAL_RED,
        GOAL_GREEN = Node.GOAL_GREEN,
        GOAL_BLUE = Node.GOAL_BLUE,
        PRISM, MIRRORNESW, MIRRORNWSE,
        LIGHT
    }

    // Direction of light
    public enum Direction : int
    {
        NONE=-1, NORTH=0, EAST=270, SOUTH=180, WEST=90
    }

    public static class MapGenerator
    {

        private static int times = 0;

        private static Random randy = new Random(DateTime.Now.Millisecond);

        public static void GenerateMap(string filename)
        {
            GenerateMap(filename, -1, -1);
        }

        public static void GenerateMap(string filename, int width, int height)
        {
            GameUnit[,] map;
            GraphState optimal=null;
            GraphSearch gs = new GraphSearch();
            int cost=99;
            int min_cost=2, max_cost=2;
			
			MapState state=null;

            if (width < 1 || height < 1)
            {
				double size = randy.NextDouble();
                //size = 0.1;
				int min_width, max_width, max_area;

                // Choose class
				if (size < 0.2)
                {
					min_width = 5;
					max_width = 5;
			    } 
                else if (size < 0.8)
                {
                    min_width = 6;
				    max_width = 8;
                } 
                else 
                {	
                    min_width = 8;
					max_width = 12;
				}
				
                // Set area to the average of the min and max height
				max_area = (int)Math.Pow((min_width+max_width)/2,2);
                width = randy.Next(max_width-min_width+1) + min_width;
                height = max_area / width;
            }

            int rotation=0;

            map = new GameUnit[width, height];

            do
            {
                Console.WriteLine("GenerateMap(): Entering randomization loop.");

                // Init map to empty
                for (int r = 0; r < height; r++)
                    for (int c = 0; c < width; c++)
                        map[c, r] = GameUnit.EMPTY;

                // Slap down three goals
                int placed = 0, rr, rc;
                do
                {
                    rc = randy.Next(width);
                    rr = randy.Next(height);
                    if (map[rc, rr] == GameUnit.EMPTY)
                    {
                        switch (placed)
                        {
                            case 0:
                                map[rc, rr] = GameUnit.GOAL_RED;
                                break;
                            case 1:
                                map[rc, rr] = GameUnit.GOAL_GREEN;
                                break;
                            case 2:
                                map[rc, rr] = GameUnit.GOAL_BLUE;
                                break;
                        }
                        placed++;
                    }
                } while (placed < 3);

                // Slap down an emitter
                placed = 1;
                rotation = 90 * randy.Next(4); // anti-clockwise
                do
                {
                    rc = randy.Next(width - 2) + 1; // don't want it against edge
                    rr = randy.Next(height - 2) + 1;
                    if (map[rc, rr] == GameUnit.EMPTY &&
                        map[rc + 1, rr] == GameUnit.EMPTY &&
                        map[rc - 1, rr] == GameUnit.EMPTY &&
                        map[rc, rr + 1] == GameUnit.EMPTY &&
                        map[rc, rr - 1] == GameUnit.EMPTY
                        )
                    {
                        map[rc, rr] = GameUnit.EMITTER;
                        state = new MapState(map, rr, rc, (Direction)rotation);
                        placed = 0;
                    }
                    else if (++placed == 6) break;
                } while (placed > 0);
                if (placed > 0) continue;

                // LOAD TEST MAP HERE
                //state = TestMap();

                // Do search.  Should never loop, but just incase...
//#if DEBUG
                Console.WriteLine("GenerateMap(): Trying to solve ... ");
                PrintMap(Console.Out, state.Map);
                map = state.Map;
//#endif
                cost = gs.IterativeDeepening(state, out optimal, max_cost);

                //if (cost < min_cost || cost > max_cost) return;

            } while (cost < min_cost || cost > max_cost);

            state = (MapState)optimal;
            
#if DEBUG
            Console.WriteLine("GenerateMap(): Solved in " + cost);
            PrintMap(Console.Out, state.Map);
            state.PrintInstructions();
#endif

            // Write to file
            StreamWriter f_out = File.CreateText(filename);

            f_out.WriteLine("LEVEL");
            f_out.WriteLine(width + " " + height);
            f_out.WriteLine("TOOLS");
            f_out.WriteLine("MIRROR " + cost);
            f_out.WriteLine("PRISM 1");
            f_out.WriteLine("EMITTER " + (int)MapState.EmitterDirection);

            f_out.WriteLine("MAP");
            PrintMap(f_out, map);
            f_out.WriteLine("END");
            f_out.Flush();
            f_out.Close();

//#if DEBUG
            Console.WriteLine("GenerateMap(): Wrote level to " + filename + ".");
//#endif
        }

        // Prints the numeric map values
        public static void PrintMap(TextWriter o_stream, GameUnit[,] map)
        {
            int width = map.GetLength(0);
            int height = map.GetLength(1);

            for (int r = 0; r < height; r++)
            {
                for (int c = 0; c < width; c++)
                {
                    o_stream.Write(((int)map[c, r]) + " ");
                }
                o_stream.WriteLine();
            }
        }

        private static MapState TestMap()
        {
            int col = 5, row = 5;
            int ecol = 2, erow = 3;

            // START NO TOUCHY
            GameUnit[,] map = new GameUnit[col, row];
            int c, r;
            for (c=0; c<col; c++)
                for (r=0; r<row; r++)
                    map[c,r] = GameUnit.EMPTY;
            map[ecol, erow] = GameUnit.EMITTER;
            // END NO TOUCHY

            map[4, 4] = GameUnit.GOAL_RED;
            map[0, 4] = GameUnit.GOAL_BLUE;
            map[2, 0] = GameUnit.GOAL_GREEN;
            
            return new MapState(map, erow, ecol, Direction.NORTH);
        }
    }
}
