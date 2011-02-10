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
using Microsoft.Xna.Framework;
using IlluminatrixEngine.Framework.Components;
using IlluminatrixEngine.Framework.States;
using IlluminatrixEngine.Framework;
using Microsoft.Xna.Framework.Graphics;
using IlluminatrixEngine.Framework.Physics;

namespace Luminance.GameUnits
{
    public class Beam : BaseEntity
    {
        private BaseEntity source;
        private BaseEntity dest;
        private Vector3 initialDirection;
        private Ray r;
        private double maxLength;
        private float length;
        private Color color;
        private bool drawable;

        //private Effect _effect;

        public bool Drawable
        {
            get { return drawable; }
            set { drawable = value; }
        }

        public BaseEntity Source
        {
            get { return source; }
        }

        public BaseEntity Dest
        {
            get { return dest; }
        }

        public Color Color
        {
            get { return color; }
            set { color = value; }
        }

        public float Length
        {
            get { return length; }
            set { length = value; }
        }

        public Vector3 Position
        {
            get { return r.Position; }
        }
        private float rotation;

        public float Rotation
        {
            get { return rotation; }
            set { rotation = value; }
        }

        public Beam(BasicWindow w, BaseEntity owner, Vector3 direction)
            : base(w)
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
            r = new Ray(owner.NewState.position, direction);
            initialDirection = direction;
            source = owner;
            length = float.MaxValue;
            maxLength = Math.Sqrt(Math.Pow(Engine.GraphicsDevice.Viewport.Height, 2) + Math.Pow(Engine.GraphicsDevice.Viewport.Width, 2));
            color = Color.White;
            drawable = false;
        }

        public override String getIconTextureKey()
        {
            return "texture.beamicon";
        }

        public override ComponentType GetComponentType()
        {
            return ComponentType.Component3D;
        }

        public float? intersects(BaseEntity b)
        {
            r.Position = this.Source.NewState.position;
            r.Direction = Vector3.Transform(this.initialDirection, Matrix.CreateRotationY(rotation));
            return r.Intersects(b.getBoundingBox());
        }

        public void setDestination(BaseEntity value)
        {
            if (dest != null)
            {
                dest.removeInput(this);
            }
            dest = value;
            if (dest != null)
            {
                dest.addInput(this);
            }
        }

        public override void init()
        {
            /*_effect = Engine.GameOwner.Content.Load<Effect>("laser_shader");

            model = Engine.GameOwner.Content.Load<Model>("LaserBolt2");
            foreach (ModelMesh mesh in model.Meshes)
                foreach (ModelMeshPart part in mesh.MeshParts)
                    part.Effect = _effect;*/

            base.init();
        }

        public override void draw()
        {
            Engine.GraphicsDevice.VertexDeclaration = positionColorVertexDeclaration;
            Engine.GraphicsDevice.RenderState.CullMode = CullMode.None;

            //this.NewState.Scale = 3.0f;
            this.defaultEffect.World = Physics.generateWorldMatrix(this);
            this.defaultEffect.Projection = Owner.State.Projection;
            this.defaultEffect.View = Owner.State.CurrentCamera.getViewMatrix();
            this.defaultEffect.VertexColorEnabled = true;

            r.Position = this.Source.NewState.position;
            r.Direction = Vector3.Transform(this.initialDirection, Matrix.CreateRotationY(rotation));

            VertexPositionColor[] points = new VertexPositionColor[2];
            points[0] = new VertexPositionColor(r.Position, color);
            if (dest != null)
            {
                points[1] = new VertexPositionColor(dest.NewState.position, color);
            }
            else
            {
                points[1] = new VertexPositionColor(r.Position + (r.Direction * (float) Math.Min(Length, maxLength)), color);
            }

            //setup effect
            /*Matrix[] mats = new Matrix[2];
            mats[0] = Physics.generateWorldMatrix(this);
            mats[1] = mats[0] * Owner.State.CurrentCamera.getViewMatrix() * Owner.State.Projection;
            _effect.Parameters["world_matrices"].SetValue(mats);
            _effect.Parameters["laser_bolt_color"].SetValue(Color.Blue.ToVector4());
            _effect.Parameters["center_to_viewer"].SetValue(Owner.State.CurrentCamera.vTarget);
            _effect.CurrentTechnique = _effect.Techniques["laserbolt_technique"];

            model.Meshes[0].Draw();*/

            //_effect.Begin();
            defaultEffect.Begin();
            //foreach (EffectPass pass in _effect.CurrentTechnique.Passes)
            foreach (EffectPass pass in defaultEffect.CurrentTechnique.Passes)
            {
                pass.Begin();

                //NEW
                /*VertexDeclaration myVertexDeclaration = new VertexDeclaration(Engine.GraphicsDevice, VertexPositionColor.VertexElements);
                VertexPositionColor[] vertices = new VertexPositionColor[4];

                vertices[0].Position = new Vector3(650, 100, 0);
                vertices[0].Color = Color.Red;

                vertices[1].Position = new Vector3(750, 200, 0);
                vertices[1].Color = Color.Red;

                vertices[2].Position = new Vector3(650, 200, 0);
                vertices[2].Color = Color.Red;

                vertices[3].Position = new Vector3(750, 100, 0);
                vertices[3].Color = Color.Red;

                VertexBuffer vertexbuffer = new VertexBuffer(Engine.GraphicsDevice, VertexPositionColor.SizeInBytes * vertices.Length, BufferUsage.WriteOnly);
                vertexbuffer.SetData(vertices);

                // set up our indices
                // because a square consist of 2 triangles, we need 6 points in our indexbuffer
                short[] indices = new short[6];

                indices[0] = 0;
                indices[1] = 1;
                indices[2] = 2;
                indices[3] = 0;
                indices[4] = 3;
                indices[5] = 1;

                IndexBuffer indexbuffer = new IndexBuffer(Engine.GraphicsDevice, typeof(short), 6, BufferUsage.WriteOnly);
                indexbuffer.SetData(indices);

                Engine.GraphicsDevice.VertexDeclaration = myVertexDeclaration;
                Engine.GraphicsDevice.Vertices[0].SetSource(vertexbuffer, 0, VertexPositionColor.SizeInBytes);
                // prepare the graphics device for drawing by setting the indices
                Engine.GraphicsDevice.Indices = indexbuffer;

                Engine.GraphicsDevice.DrawIndexedPrimitives(PrimitiveType.TriangleList, 0, 0, 4, 0, 2);
                */
                // OLD
                // Draw the triangle using the XNA default shader
                if (dest == null)
                {
                    Engine.GraphicsDevice.DrawUserPrimitives<VertexPositionColor>(
                      PrimitiveType.LineList,
                      points,
                      0, 1
                    );


                    Engine.GraphicsDevice.DrawUserPrimitives<VertexPositionColor>(
                      PrimitiveType.LineList,
                      new VertexPositionColor[] {
                      new VertexPositionColor(r.Position-(Vector3.UnitX*0.10f), color),
                      new VertexPositionColor(r.Position-(Vector3.UnitX*0.10f) + (r.Direction * (float) Math.Min(Length, maxLength)), color)
                  },
                      0, 1
                    );
                    Engine.GraphicsDevice.DrawUserPrimitives<VertexPositionColor>(
                      PrimitiveType.LineList,
                      new VertexPositionColor[] {
                      new VertexPositionColor(r.Position-(Vector3.UnitZ*0.10f), color),
                      new VertexPositionColor(r.Position-(Vector3.UnitZ*0.10f) + (r.Direction * (float) Math.Min(Length, maxLength)), color)
                  },
                      0, 1
                    );
                    Engine.GraphicsDevice.DrawUserPrimitives<VertexPositionColor>(
                      PrimitiveType.LineList,
                      new VertexPositionColor[] {
                      new VertexPositionColor(r.Position+Vector3.UnitX*0.10f, color),
                      new VertexPositionColor(r.Position+Vector3.UnitX*0.10f + (r.Direction * (float) Math.Min(Length, maxLength)), color)
                  },
                      0, 1
                    );
                    Engine.GraphicsDevice.DrawUserPrimitives<VertexPositionColor>(
                      PrimitiveType.LineList,
                      new VertexPositionColor[] {
                      new VertexPositionColor(r.Position+Vector3.UnitZ*0.10f, color),
                      new VertexPositionColor(r.Position+Vector3.UnitZ*0.10f + (r.Direction * (float) Math.Min(Length, maxLength)), color)
                  },
                      0, 1
                    );
                }
                else
                {
                    Engine.GraphicsDevice.DrawUserPrimitives<VertexPositionColor>(
                        PrimitiveType.LineList,
                        new VertexPositionColor[] {
                            new VertexPositionColor(r.Position, color),
                            new VertexPositionColor(dest.NewState.position, color)
                        },
                        0, 1
                    );
                    Engine.GraphicsDevice.DrawUserPrimitives<VertexPositionColor>(
                      PrimitiveType.LineList,
                      new VertexPositionColor[] {
                        new VertexPositionColor(r.Position - (Vector3.UnitX * 0.10f), color),
                        new VertexPositionColor(dest.NewState.position - (Vector3.UnitX * 0.01f), color)
                    },
                      0, 1
                    );
                    Engine.GraphicsDevice.DrawUserPrimitives<VertexPositionColor>(
                      PrimitiveType.LineList,
                      new VertexPositionColor[] {
                      new VertexPositionColor(r.Position - (Vector3.UnitZ * 0.10f), color),
                      new VertexPositionColor(dest.NewState.position - (Vector3.UnitZ * 0.10f), color)
                  },
                      0, 1
                    );
                    Engine.GraphicsDevice.DrawUserPrimitives<VertexPositionColor>(
                      PrimitiveType.LineList,
                      new VertexPositionColor[] {
                      new VertexPositionColor(r.Position + (Vector3.UnitX * 0.10f), color),
                      new VertexPositionColor(dest.NewState.position + (Vector3.UnitX * 0.10f), color)
                  },
                      0, 1
                    );
                    Engine.GraphicsDevice.DrawUserPrimitives<VertexPositionColor>(
                      PrimitiveType.LineList,
                      new VertexPositionColor[] {
                      new VertexPositionColor(r.Position + (Vector3.UnitZ * 0.10f), color),
                      new VertexPositionColor(dest.NewState.position + (Vector3.UnitZ * 0.10f), color)
                  },
                      0, 1
                    );
                }
                pass.End();
            }
            //_effect.End();
            defaultEffect.End();
        }
    }
}
