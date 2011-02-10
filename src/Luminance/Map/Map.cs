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
using System.IO;
using System.Linq;
using System.Text;
using IlluminatrixEngine.Framework;
using IlluminatrixEngine.Framework.Components;
using IlluminatrixEngine.Framework.Physics;
using IlluminatrixEngine.Framework.States;
using Luminance.GameUnits;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Luminance.States;

namespace Luminance.Map
{
    /// <summary>
    /// Map class, represents a level in Luminance
    /// </summary>
    public class Level : Unit
    {
        public const int CLEAR = 0;
        public const int OCCUPIED = 1;
        public const int MAP_MAX_SIZE = 100;

        /// <summary>
        /// The number of pixels to place between the outermost lines and the edge of the screen
        /// </summary>
        public const int edgePadding = 10;

        /// <summary>
        /// The color of the map's lines
        /// </summary>
        private Color lineColor = Color.Yellow;

        public Color LineColor
        {
            get { return lineColor; }
        }

        /// <summary>
        /// The raw map data
        /// </summary>
        protected int[,] sourceMap;

        /// <summary>
        /// The map data in game form
        /// </summary>
        protected Node[,] map;

        protected int mirror_count;
        public int MirrorCount
        {
            get { return mirror_count; }
        }

        protected int prism_count;
        public int PrismCount
        {
            get { return prism_count; }
        }

        protected int emitter_orientation;
        public int EmitterOrientation
        {
            get { return emitter_orientation; }
        }

        /// <summary>
        /// The number of vertical spaces
        /// </summary>
        protected int verticalSpaces;

        public int VerticalSpaces
        {
            get { return verticalSpaces; }
        }

        /// <summary>
        /// The number of horizontal spaces
        /// </summary>
        protected int horizontalSpaces;

        public int HorizontalSpaces
        {
            get { return horizontalSpaces; }
        }

        /// <summary>
        /// The shortest axis - use this as the space between lines
        /// </summary>
        protected float minAxis;

        public float MinAxis
        {
            get { return minAxis; }
        }

        /// <summary>
        /// The scale value for all units
        /// </summary>
        protected float objectScale;

        /// <summary>
        /// Default Vertex- & PixelShader for the graphics card
        /// </summary>
        protected BasicEffect defaultEffect;

        /// <summary>
        /// Vertex type that stores a position and color in each vertex
        /// </summary>
        protected VertexDeclaration positionColorVertexDeclaration;

        protected List<GridLine> hLines;
        protected List<GridLine> vLines;
        protected List<BaseEntity> goals;

        public Point HighlightedPoint;
        private Line[] _highlightLines;

        /// <summary>
        /// File to load from
        /// </summary>
        protected string _loadfrom;

        /// <summary>
        /// Constructor
        /// </summary>
        public Level(BasicWindow owner)
            : base(owner)
        {
            _loadfrom = null;
        }

        public Level(BasicWindow owner, string filename)
            : base(owner)
        {
            _loadfrom = filename;
#if DEBUG
            //Console.WriteLine("Set to load from: "+filename);
#endif
        }

        /// <summary>
        /// Initialize the map.
        /// One time call!
        /// </summary>
        public override void init()
        {
            if (map != null)
            {
                foreach (Node n in map)
                {
                    BaseEntity t = n.getUnit();
                    if (t != null)
                    {
                        _owner.removeComponent(t);
                    }
                    n.makeFree();
                }
                map = null;
                goals.Clear();
                hLines.Clear();
                vLines.Clear();
            }

            if (_loadfrom != null)
            {
#if DEBUG
                Console.WriteLine("Loading from: "+_loadfrom);
#endif
                ReadMap(_loadfrom);
            }
            
            horizontalSpaces = sourceMap.GetLength(1);
            verticalSpaces = sourceMap.GetLength(0);

            //Find the distance between lines
            float hDistance = MAP_MAX_SIZE / horizontalSpaces;
            float vDistance = MAP_MAX_SIZE / VerticalSpaces;
            minAxis = Math.Min(vDistance, hDistance);
            objectScale = minAxis * 0.85f;

            //Hack to flip the map
            map = new Node[horizontalSpaces, VerticalSpaces];
            for (int i = 0; i < horizontalSpaces; i++)
            {
                for (int j = 0; j < VerticalSpaces; j++)
                {
                    map[i, j] = new Node(j, i, sourceMap[j,i]);
                }
            }

            this.createMap();
            
            // Declare the vertex format we will use for the rotating triangle's vertices
            this.positionColorVertexDeclaration = new VertexDeclaration(
                Engine.GraphicsDevice, VertexPositionColor.VertexElements
            );

            resetCamera();

            // init the rendering effect
            this.defaultEffect = new BasicEffect(Engine.GraphicsDevice, null);
            this.defaultEffect.World = Matrix.Identity;
            this.defaultEffect.Projection = Owner.State.Projection;
            this.defaultEffect.View = Owner.State.CurrentCamera.getViewMatrix();

            // init the highlight lines
            _highlightLines = new Line[4];
            _highlightLines[0] = new Line();
            _highlightLines[1] = new Line();
            _highlightLines[2] = new Line();
            _highlightLines[3] = new Line();
        }


        public void resetCamera()
        {
            // position camera
            float mapWidth = (horizontalSpaces * minAxis);
            float mapHeight = (VerticalSpaces * minAxis);
            float camX = mapWidth / 2;
            float camZ = (mapHeight + 20) / 2;

            float maxSize = Math.Max(camX, camZ);
            float camY = 1.5f * (maxSize / (float)(Math.Tan((Math.PI / 6))));
            //float camY = 2 * (maxSize / (float)(Math.Tan((Math.PI / 4))));

            Owner.State.CurrentCamera.cameraPitch = 0.0f;
            Owner.State.CurrentCamera.cameraYaw = 0.0f;

            Owner.State.CurrentCamera.setPosition(0, 0, 0);
            Owner.State.CurrentCamera.setReference(0, 0, 1);
            Owner.State.CurrentCamera.vTarget = Owner.State.CurrentCamera.vReference + Owner.State.CurrentCamera.vPos;

            Owner.State.CurrentCamera.update(new Vector3(camX, camY, camZ), new Vector3(0, 0, 0));
            Owner.State.CurrentCamera.update(Vector3.Zero, new Vector3((float)Math.PI, (float)Math.PI / 2, 0));
        }


        /// <summary>
        /// Destroy any map resources.
        /// One time call!
        /// </summary>
        public override void destroy() { }


        /// <summary>
        /// Updates the map
        /// </summary>
        public override void update() { }


        /// <summary>
        /// Draw the map
        /// </summary>
        public override void draw()
        {
            Engine.GraphicsDevice.VertexDeclaration = this.positionColorVertexDeclaration;
            Engine.GraphicsDevice.RenderState.CullMode = CullMode.None;

            this.defaultEffect.World = Matrix.Identity;// Physics.generateWorldMatrix(this);
            this.defaultEffect.Projection = Owner.State.Projection;
            this.defaultEffect.View = Owner.State.CurrentCamera.getViewMatrix();
            
            this.defaultEffect.VertexColorEnabled = true;
            this.defaultEffect.LightingEnabled = true;
            this.defaultEffect.AmbientLightColor = lineColor.ToVector3();

            this.defaultEffect.Begin();
            foreach (EffectPass pass in this.defaultEffect.CurrentTechnique.Passes)
            {
                pass.Begin();
                // Draw the triangle using the XNA default shader
                foreach (GridLine l in vLines)
                {                    
                    VertexPositionColor[] ary = l.getVertices();
                    Engine.GraphicsDevice.DrawUserPrimitives<VertexPositionColor>(PrimitiveType.LineList, ary, 0, 1);
                }

                // Draw the triangle using the XNA default shader
                foreach (GridLine l in hLines)
                {
                    VertexPositionColor[] ary = l.getVertices();
                    Engine.GraphicsDevice.DrawUserPrimitives<VertexPositionColor>(PrimitiveType.LineList, ary, 0, 1);
                }
                pass.End();
            }
            this.defaultEffect.End();

            this.defaultEffect.VertexColorEnabled = false;
            this.defaultEffect.LightingEnabled = true;
            this.defaultEffect.AmbientLightColor = Color.Green.ToVector3();

            // draw square highlighting
            if (HighlightedPoint.X >= 0 && HighlightedPoint.Y >= 0)
            {
                Vector3 cellWorld = toScreenPos(HighlightedPoint);
                cellWorld.X -= minAxis / 2;
                cellWorld.Z -= minAxis / 2;
                cellWorld.Y = 0.75f;


                _highlightLines[0].v1 = cellWorld;
                _highlightLines[0].v2.X = cellWorld.X + minAxis;
                _highlightLines[0].v2.Y = cellWorld.Y;
                _highlightLines[0].v2.Z = cellWorld.Z;


                _highlightLines[1].v1 = cellWorld;
                _highlightLines[1].v2.X = cellWorld.X;
                _highlightLines[1].v2.Y = cellWorld.Y;
                _highlightLines[1].v2.Z = cellWorld.Z + minAxis;


                _highlightLines[2].v1.X = cellWorld.X + minAxis;
                _highlightLines[2].v1.Y = cellWorld.Y;
                _highlightLines[2].v1.Z = cellWorld.Z;

                _highlightLines[2].v2.X = cellWorld.X + minAxis;
                _highlightLines[2].v2.Y = cellWorld.Y;
                _highlightLines[2].v2.Z = cellWorld.Z + minAxis;


                _highlightLines[3].v1.X = cellWorld.X;
                _highlightLines[3].v1.Y = cellWorld.Y;
                _highlightLines[3].v1.Z = cellWorld.Z + minAxis;

                _highlightLines[3].v2.X = cellWorld.X + minAxis;
                _highlightLines[3].v2.Y = cellWorld.Y;
                _highlightLines[3].v2.Z = cellWorld.Z + minAxis;

                this.defaultEffect.Begin();
                this.defaultEffect.LightingEnabled = true;
                this.defaultEffect.AmbientLightColor = Color.Green.ToVector3();
                foreach (EffectPass pass in this.defaultEffect.CurrentTechnique.Passes)
                {
                    pass.Begin();
                    Engine.GraphicsDevice.DrawUserPrimitives<Vector3>(PrimitiveType.LineList, _highlightLines[0].getPoints(), 0, 1);
                    Engine.GraphicsDevice.DrawUserPrimitives<Vector3>(PrimitiveType.LineList, _highlightLines[1].getPoints(), 0, 1);
                    Engine.GraphicsDevice.DrawUserPrimitives<Vector3>(PrimitiveType.LineList, _highlightLines[2].getPoints(), 0, 1);
                    Engine.GraphicsDevice.DrawUserPrimitives<Vector3>(PrimitiveType.LineList, _highlightLines[3].getPoints(), 0, 1);
                    pass.End();
                }
                this.defaultEffect.End();
            }
        }


        /// <summary>
        /// Get the component type of this level object
        /// </summary>
        /// <returns></returns>
        public override ComponentType GetComponentType()
        {
            return ComponentType.Component3D;
        }


        /// <summary>
        /// Create the map
        /// </summary>
        public void createMap()
        {
            hLines = new List<GridLine>();
            vLines = new List<GridLine>();
            goals = new List<BaseEntity>();

            //Find the number of lines to draw
            int horizontalLines = VerticalSpaces + 1;
            int verticalLines = horizontalSpaces + 1;

            float vLength = minAxis * VerticalSpaces;
            float hLength = minAxis * horizontalSpaces;

            //Draw the vertical lines (to create the horizontal grid)
            for (int i = 0; i < verticalLines; i++)
            {
                GridLine l = new GridLine(
                    new Vector3(i * minAxis, 0, 0),
                    new Vector3(i * minAxis, 0, vLength),
                    lineColor);
                vLines.Add(l);
            }

            //Draw the horizontal lines (to create the vertical grid)
            for (int i = 0; i < horizontalLines; i++)
            {
                GridLine l = new GridLine(
                    new Vector3(0, 0, i * minAxis),
                    new Vector3(hLength, 0, i * minAxis),
                    lineColor);
                hLines.Add(l);
            }

            //Draw any blocked cells
            for (int i = 0; i < horizontalSpaces; i++)
            {
                for (int j = 0; j < VerticalSpaces; j++)
                {
                    BaseEntity e = null;
                    int type = map[i, j].getType();
                    switch (type) {
                        case Node.BLOCKED:
                            e = new Block(_owner);
                            break;
                        case Node.EMITTER:
                            e = new Emitter(_owner);
                            e.RotateAboutY((float)(Math.PI / 180.0) * emitter_orientation);
                            break;
                        case Node.GOAL_RED:
                            e = new Goal(_owner);
                            ((Goal)e).RequiredColor = Color.Red;
                            goals.Add(e);
                            break;
                        case Node.GOAL_GREEN:
                            e = new Goal(_owner);
                            ((Goal)e).RequiredColor = Color.Green;
                            goals.Add(e);
                            break;
                        case Node.GOAL_BLUE:
                            e = new Goal(_owner);
                            ((Goal)e).RequiredColor = Color.Blue;
                            goals.Add(e);
                            break;
                        case Node.GOAL:
                            e = new Goal(_owner);
                            goals.Add(e);
                            break;
                    }
                    if (e != null)
                    {
                        e.NewState.Scale = this.objectScale;
                        this.placeObject(e, new Point(i, j));
                    }
                }
            }
        }

        /// <summary>
        /// Checks to see if all goals are met
        /// </summary>
        public bool isWinnar()
        {
            foreach (Goal g in goals)
            {
                if (!g.hasInputs())
                {
                    return false;
                }
            }
            return true;
        }


        /// <summary>
        /// Checks to see if a point is free
        /// </summary>
        protected bool isValidPosition(Point p)
        {
            if (isValidGridspace(p))
            {
                return !map[p.X, p.Y].isBlocked();
            }
            else
            {
                return false;
            }
        }

        protected bool isValidGridspace(Point p)
        {
            if (p.X >= 0 && p.Y >= 0 &&
                p.X < this.horizontalSpaces && p.Y < this.VerticalSpaces)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        /// <summary>
        /// Gets the map point from a mouse click
        /// </summary>
        public Point getMapPoint(Vector3 mouseClickPos)
        {
            return toMapPos(findMapIntersection(mouseClickPos));
        }

        /// <summary>
        /// Checks to see if a mouse position is valid
        /// </summary>
        public bool isValidPosition(Vector3 mouseClickPos)
        {
            return isValidPosition(getMapPoint(mouseClickPos));
        }

        /// <summary>
        /// Returns a point representing the location of the mouse click on the grid
        /// </summary>
        public Point toMapPos(Vector3 pickedPosition)
        {
            int xPos = -1;
            int yPos = -1;
            for (int i = 0; i < horizontalSpaces; i++)
            {
                if (vLines.ElementAt(i).p1.Position.X < pickedPosition.X &&
                    vLines.ElementAt(i+1).p1.Position.X > pickedPosition.X)
                {
                    xPos = i;
                    break;
                }
            }
            for (int i = 0; i < VerticalSpaces; i++)
            {
                if (hLines.ElementAt(i).p1.Position.Z < pickedPosition.Z &&
                    hLines.ElementAt(i + 1).p1.Position.Z > pickedPosition.Z)
                {
                    yPos = i;
                    break;
                }
            }
            return new Point(xPos, yPos);
        }

        /// <summary>
        /// Convert a map point to screen position
        /// </summary>
        protected Vector3 toScreenPos(Point mapPos)
        {
            return new Vector3(mapPos.X * minAxis + 0.5f * minAxis, 0, mapPos.Y * minAxis + 0.5f * minAxis);
        }

        /// <summary>
        /// Places an object on the map
        /// </summary>
        public bool placeObject(BaseEntity e, Vector3 screenPos)
        {
            Point mapPos = getMapPoint(screenPos);
            return placeObject(e, mapPos);
        }

        /// <summary>
        /// Places an object on the map
        /// </summary>
        protected bool placeObject(BaseEntity e, Point p)
        {
            if (isValidPosition(p))
            {
                e.NewState.Scale = this.objectScale;
                if (e is Goal)
                {
                    //@HACK:  Wtf, why is this object twice the size of everything else?
                    e.NewState.Scale = this.objectScale / 2.0f;
                }
                else if (e is Block)
                {
                    //@TODO:  Make this scaling crap better
                    e.NewState.Scale = this.objectScale / 0.85f;
                }
                map[p.X, p.Y].makeBlocked(e);
                Owner.addComponent(e);
                e.IsPlaced = true;
                Vector3 loc = toScreenPos(p);
                e.newPosition(loc);
                if (e is Goal)
                {
                    goals.Add(e);
                }
                return true;
            }
            else if (e is Eraser)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        /// <summary>
        /// Find the intersection of the mouse click with the map plane
        /// </summary>
        public Vector3 findMapIntersection(Vector3 screenPos)
        {
            //http://www.gamedev.net/community/forums/topic.asp?topic_id=503488
            //@TODO:  Check to see if this collides with units on the map
            Vector3 temp = screenPos;
            temp.Z = 0;
            Vector3 nearPoint = Engine.GraphicsDevice.Viewport.Unproject(temp, Owner.State.Projection, Owner.State.CurrentCamera.getViewMatrix(), Matrix.Identity);
            temp.Z = 1;
            Vector3 farPoint = Engine.GraphicsDevice.Viewport.Unproject(temp, Owner.State.Projection, Owner.State.CurrentCamera.getViewMatrix(), Matrix.Identity);

            // compute normalized direction vector from nearPoint to farPoint
            Vector3 direction = farPoint - nearPoint;
            direction.Normalize();

            // create a ray using nearPoint as the source
            Ray r = new Ray(nearPoint, direction);

            // calculate the ray-plane intersection point
            Vector3 n = new Vector3(0f, 1f, 0f);
            Plane p = new Plane(n, 0f);

            // calculate distance of intersection point from r.origin
            float denominator = Vector3.Dot(p.Normal, r.Direction);
            float numerator = Vector3.Dot(p.Normal, r.Position) + p.D;
            float t = -(numerator / denominator);

            // calculate the picked position on the y = 0 plane
            Vector3 pickedPosition = nearPoint + direction * t;

            return pickedPosition;
        }

        /// <summary>
        /// Removes an object from the screen
        /// </summary>
        public BaseEntity clearObject(Vector3 screenPos)
        {
            Point p = toMapPos(findMapIntersection(screenPos));
            return clearObject(p);
        }

        /// <summary>
        /// Removes an object from the screen
        /// </summary>
        protected BaseEntity clearObject(Point p)
        {
            
            if (isValidGridspace(p)) //If the point is occupied
            {
                BaseEntity e = map[p.X, p.Y].getUnit();
                if ((e is Block || e is Emitter || e is Goal) && !(_owner.State as GameState).EditMode)
                {
                    return null;
                }
                if (e != null)
                {
                    e.IsPlaced = false;
                    map[p.X, p.Y].makeFree();
                    _owner.removeComponent(e);
                    if (e is Goal)
                    {
                        goals.Remove(e);
                    }
                    return e;
                }
            }
            return null;
        }


        /* File format: Note: Write doesn't support writing of tools.
         * 
         * "LEVEL"
         * width height
         * "TOOLS"
         * "MIRRORS" number_of
         * "MAP"
         * [0,0] [0,1] [0,2] ...
         * [1,0] [1,1] [1,2] ...
         * ...
         * "END"
         */

        /// <summary>
        /// Writes a file to a map.  Throws any exception 
        /// thrown by File.WriteAllText on failure
        /// </summary>
        /// <param name="filename">The file name to write out to.</param>
        public void WriteMap(string filename)
        {
            // Build file in a string
            StringBuilder out_str = new StringBuilder();
            out_str.AppendLine("LEVEL");
            out_str.Append(horizontalSpaces);
            out_str.Append(" ");
            out_str.Append(VerticalSpaces);
            out_str.AppendLine();
            out_str.AppendLine("MAP");
            for (int c = 0; c < VerticalSpaces; c++)
            {
                for (int r = 0; r < horizontalSpaces; r++)
                {
                    out_str.Append(sourceMap[c, r]); // column by row
                    if (c!=(horizontalSpaces-1)) out_str.Append(" ");
                }
                out_str.AppendLine();
            }
            
            out_str.AppendLine("END");
            
            // Try writing out the file
            try { File.WriteAllText(filename, out_str.ToString()); }
            catch { throw; }
        }

        /// <summary>
        /// Will eventually load a map from a file...
        /// </summary>
        /// <param name="filename">The file to load from.</param>
        public void ReadMap(string filename)
        {
#if DEBUG
            Console.WriteLine("ReadMap(): Told to open " + filename);
#endif
            FileStream in_file = null;
            try
            {
                in_file = File.Open(filename, FileMode.Open, FileAccess.Read);
            }
            catch 
            {
#if DEBUG
                Console.WriteLine("ReadMap(): Open failed.  Switching to failsafe.");
#endif
                ReadMap(@"..\Levels\lastMap.map"); 
                return; 
            }
#if DEBUG
            Console.WriteLine("ReadMap(): File open successful.");
#endif
            StreamReader in_stream = new StreamReader(in_file);

            int vert, horz;
            string[] size;
            
            char[] split_on = { ' ' };

            // First line must be "LEVEL"
            in_stream.ReadLine();

            size = in_stream.ReadLine().Split(split_on, 2);
            int.TryParse(size[0], out horz);
            int.TryParse(size[1], out vert);

            // Tool management here!
            mirror_count = 0;
            prism_count = 0;
            emitter_orientation = 0;

            string temp = in_stream.ReadLine();
            if ("TOOLS".Equals(temp))
            {
                while (!"MAP".Equals(temp = in_stream.ReadLine()))
                {
                    size = temp.Split(split_on, 2);
                    if ("MIRROR".Equals(size[0]))
                        int.TryParse(size[1], out mirror_count);
                    else if ("PRISM".Equals(size[0]))
                        int.TryParse(size[1], out prism_count);
                    else if ("EMITTER".Equals(size[0]))
                        int.TryParse(size[1], out emitter_orientation);
                }
            }

            // Read map
            sourceMap = new int[vert, horz];
            for (int v = 0; v < vert; v++)
            {
                size = in_stream.ReadLine().Split(split_on, horz);
                for (int h = 0; h < horz; h++)
                {
                    int.TryParse(size[h], out sourceMap[v, h]);
                }
            }

            // All done
            in_stream.Close();
            in_file.Close();

            if (filename.Contains("Random"))
            {
                if (File.Exists(@"..\Levels\lastMap.map"))
                {
                    File.Delete(@"..\Levels\lastMap.map");
                }
                File.Copy(filename, @"..\Levels\lastMap.map");
                File.Delete(filename);
            }
        }


    }
}

