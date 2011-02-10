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
using System.Linq;
using System.Text;

using IlluminatrixEngine.Framework;
using IlluminatrixEngine.Framework.Physics;
using IlluminatrixEngine.Framework.Components;
using IlluminatrixEngine.Framework.States;

using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using IlluminatrixEngine.Framework.Resources;

namespace Luminance.GameUnits
{

    public abstract class BaseEntity : Unit
    {
        /// <summary>
        /// Vertex type that stores a position and color in each vertex
        /// </summary>
        protected VertexDeclaration positionColorVertexDeclaration;


        /// <summary>
        /// Default Vertex- & PixelShader for the graphics card
        /// </summary>
        protected BasicEffect defaultEffect;

        /// <summary>
        /// The model for the unit
        /// </summary>
        protected Model model;
        /// <summary>
        /// The bounding box for line collisions
        /// </summary>
        protected BoundingBox box;
        /// <summary>
        /// The input beams
        /// </summary>
        protected List<Beam> inputs;
        /// <summary>
        /// The output beams
        /// </summary>
        protected List<Beam> outputs;
        /// <summary>
        /// A check variable to ensure that the game doesn't end when the user is still trying to place an object
        /// </summary>
        protected bool placed;

        public bool IsPlaced
        {
            get { return placed; }
            set { isPlaced(value); }
        }

        /// <summary>
        /// The ambient light
        /// </summary>
        public Vector3 AmbientLightColor = new Vector3(1.0f, 1.0f, 1.0f);

        /// <summary>
        /// Basic Cube Constructor
        /// </summary>
        public BaseEntity(BasicWindow w) : base(w)
        {
            // Declare the vertex format we will use for the rotating triangle's vertices
            this.positionColorVertexDeclaration = new VertexDeclaration(
              Engine.GraphicsDevice, VertexPositionColor.VertexElements
            );

            // Create a new default effect containing universal
            // vertex and pixel shaders that we can use to draw our triangle
            this.defaultEffect = new BasicEffect(Engine.GraphicsDevice, null);
            this.defaultEffect.World = Matrix.Identity;
            this.defaultEffect.Projection = Owner.State.Projection;
            this.defaultEffect.View = Owner.State.CurrentCamera.getViewMatrix();
            this.init();
            box = new BoundingBox(new Vector3(-1, -1, -1), new Vector3(1, 1, 1));
            outputs = new List<Beam>();
            inputs = new List<Beam>();
            placed = false;
            xrotation = 0;
            yrotation = 0;
            zrotation = 0;
        }

        public override void init() { }
        public override void destroy() { }
        public override void update() { }

        /// <summary>
        /// Helper to create a beam in a given direction
        /// </summary>
        public Beam createBeam(Vector3 direction)
        {
            Beam b = new Beam(_owner, this, direction);
            b.Drawable = false;
            return b;
        }

        /// <summary>
        /// Updates the tool's position
        /// </summary>
        public override void newPosition(Vector3 pos)
        {
            float halfScale = this.NewState.Scale / 2;

            NewState.position = pos;

            box.Min.X = pos.X - halfScale;
            box.Min.Y = pos.Y - halfScale;
            box.Min.Z = pos.Z - halfScale;
            box.Max.X = pos.X + halfScale;
            box.Max.Y = pos.Y + halfScale;
            box.Max.Z = pos.Z + halfScale;
        }

        /// <summary>
        /// Sets the isPlaced variable
        /// </summary>
        private void isPlaced(bool value)
        {
            placed = value;
            if (placed == false)
            {
                foreach (Beam b in outputs)
                {
                    b.setDestination(null);
                }
            }
        }

        public abstract String getIconTextureKey();

        public override void draw()
        {
            foreach (ModelMesh mesh in model.Meshes)
            {
                foreach (BasicEffect currentEffect in mesh.Effects)
                {
                    currentEffect.World = Physics.generateWorldMatrix(this);
                    currentEffect.View = Owner.State.CurrentCamera.getViewMatrix();
                    currentEffect.Projection = Owner.State.Projection;
                    
                    // have these objects emit some light
                    currentEffect.LightingEnabled = true;
                    currentEffect.AmbientLightColor = this.AmbientLightColor;
                }           
                mesh.Draw();
            }

            if (hasInputs())
            {
                foreach (Beam b in outputs)
                {
                    if (b.Drawable)
                    {
                        b.draw();
                    }
                }
            }
        }

        /// <summary>
        /// Checks to see if this tool has any output beams
        /// </summary>
        public virtual Boolean hasBeam()
        {
            return outputs.Count > 0;
        }

        /// <summary>
        /// Adds an input beam
        /// </summary>
        public void addInput(Beam other)
        {
            inputs.Add(other);
            updateOutputs();
        }

        /// <summary>
        /// Removes an input
        /// </summary>
        public void removeInput(Beam other)
        {
            inputs.Remove(other);
            updateOutputs();
        }

        /// <summary>
        /// Checks to see if this tool has any input beams
        /// </summary>
        public virtual bool hasInputs()
        {
            return inputs.Count > 0;
        }

        /// <summary>
        /// Attempts to link this tool with a list of tools
        /// </summary>
        public List<BaseEntity> tryBeamTo(List<BaseEntity> list)
        {
            if (!hasInputs() || !hasBeam())
            {
                return new List<BaseEntity>();
            }

            List<BaseEntity> wavefront = new List<BaseEntity>();
            foreach (Beam b in outputs)
            {
                b.setDestination(null);
                float? dist = float.MaxValue;
                foreach (BaseEntity other in list)
                {
                    if (other == this)
                    {
                        continue;
                    }
                    float? d = b.intersects(other);
                    if (d != null)
                    {
                        if (dist == null || (dist != null && d < dist))
                        {
                            if (IsPlaced && b.Drawable)
                            {
                                wavefront.Remove(b.Dest);
                                wavefront.Add(other);
                                b.setDestination(other);
                            }
                            dist = d;
                        }
                    }
                }
                b.Length = (float)dist;
            }
            return wavefront;
        }

        /// <summary>
        /// Removes all input beams
        /// </summary>
        public void removeAllInputs()
        {
            inputs.Clear();
        }

        /// <summary>
        /// Updates the output beams
        /// </summary>
        protected virtual void updateOutputs() { }

        /// <summary>
        /// Gets the bounding box for this tool
        /// </summary>
        public BoundingBox getBoundingBox()
        {
            return this.box;
        }

        public override ComponentType GetComponentType()
        {
            return ComponentType.Component3D;
        }

        /// <summary>
        /// Rotates this tool about the X axis
        /// </summary>
        public virtual void RotateAboutX(float radians)
        {
            this.xrotation += radians;
        }

        /// <summary>
        /// Rotates this tool about the Y axis
        /// </summary>
        public virtual void RotateAboutY(float radians)
        {
            this.yrotation += radians;
            if (this.hasBeam()) {
                foreach (Beam b in outputs)
                {
                    b.Rotation += radians;
                }
                updateOutputs();
            }
        }

        public void setYRotation(float radians)
        {
            this.yrotation = 0;
            RotateAboutY(radians);
        }

        /// <summary>
        /// Rotates this tool about the Z axis
        /// </summary>
        public virtual void RotateAboutZ(float radians)
        {
            this.zrotation += radians;
        }
    }
}
