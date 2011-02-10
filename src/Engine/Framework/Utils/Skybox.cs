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
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Storage;
using IlluminatrixEngine.Framework.Physics;
using IlluminatrixEngine.Framework.Components;
using IlluminatrixEngine.Framework.States;

namespace IlluminatrixEngine.Framework.Utils
{
    public class Skybox : Unit
    {
        Texture2D[] textures = new Texture2D[6];
        Effect effect;

        VertexBuffer vertices;
        IndexBuffer indices;
        VertexDeclaration vertexDecl;

        //Vector3 vCameraDirection;
        //Vector3 vCameraPosition;

        Matrix viewMatrix;
        Matrix projectionMatrix;

        ContentManager content;

        public Skybox(BasicWindow owner) : base(owner)
        {
            textures = new Texture2D[6];
        }

        /*public Vector3 CameraDirection
        {
            get { return vCameraDirection; }
            set { vCameraDirection = value; }
        }

        public Vector3 CameraPosition
        {
            get { return vCameraPosition; }
            set 
            {
                vCameraPosition = value;
                worldMatrix = Matrix.CreateTranslation(vCameraPosition);
            }
        }*/

        public Matrix ViewMatrix
        {
            set { viewMatrix = value; }
            get { return viewMatrix; }
        }

        public Matrix ProjectionMatrix
        {
            set { projectionMatrix = value; }
            get { return projectionMatrix; }
        }

        public ContentManager ContentManager
        {
            set { content = value; }
        }

        public override ComponentType GetComponentType()        
        {
            return ComponentType.ComponentStatic;
        }

        public override void init()
        {
            textures[0] = Engine.GameOwner.Content.Load<Texture2D>("Skybox\\back");
            textures[1] = Engine.GameOwner.Content.Load<Texture2D>("Skybox\\front");
            textures[2] = Engine.GameOwner.Content.Load<Texture2D>("Skybox\\bottom");
            textures[3] = Engine.GameOwner.Content.Load<Texture2D>("Skybox\\top");
            textures[4] = Engine.GameOwner.Content.Load<Texture2D>("Skybox\\left");
            textures[5] = Engine.GameOwner.Content.Load<Texture2D>("Skybox\\right");

            effect = Engine.GameOwner.Content.Load<Effect>("Skybox\\skybox");

            IGraphicsDeviceService graphicsService = (IGraphicsDeviceService)
                Engine.GameOwner.Services.GetService(typeof(IGraphicsDeviceService));

            vertexDecl = new VertexDeclaration(graphicsService.GraphicsDevice,
                new VertexElement[] {
                    new VertexElement(0,0,VertexElementFormat.Vector3,
                           VertexElementMethod.Default,
                            VertexElementUsage.Position,0),
                    new VertexElement(0,sizeof(float)*3,VertexElementFormat.Vector2,
                           VertexElementMethod.Default,
                            VertexElementUsage.TextureCoordinate,0)});


            vertices = new VertexBuffer(graphicsService.GraphicsDevice, 
                                typeof(VertexPositionTexture), 
                                4 * 6, 
                                BufferUsage.WriteOnly);

            VertexPositionTexture[] data = new VertexPositionTexture[4*6];
            
            Vector3 vExtents = new Vector3(2000, 2000, 2000);
            //back
            data[0].Position = new Vector3(vExtents.X, -vExtents.Y, -vExtents.Z);
            data[0].TextureCoordinate.X = 1.0f; data[0].TextureCoordinate.Y = 1.0f;
            data[1].Position = new Vector3(vExtents.X, vExtents.Y, -vExtents.Z);
            data[1].TextureCoordinate.X = 1.0f; data[1].TextureCoordinate.Y = 0.0f;
            data[2].Position = new Vector3(-vExtents.X, vExtents.Y, -vExtents.Z);
            data[2].TextureCoordinate.X = 0.0f; data[2].TextureCoordinate.Y = 0.0f;
            data[3].Position = new Vector3(-vExtents.X, -vExtents.Y, -vExtents.Z);
            data[3].TextureCoordinate.X = 0.0f; data[3].TextureCoordinate.Y = 1.0f;

            //front
            data[4].Position = new Vector3(-vExtents.X, -vExtents.Y, vExtents.Z);
            data[4].TextureCoordinate.X = 1.0f; data[4].TextureCoordinate.Y = 1.0f;
            data[5].Position = new Vector3(-vExtents.X, vExtents.Y, vExtents.Z);
            data[5].TextureCoordinate.X = 1.0f; data[5].TextureCoordinate.Y = 0.0f;
            data[6].Position = new Vector3(vExtents.X, vExtents.Y, vExtents.Z);
            data[6].TextureCoordinate.X = 0.0f; data[6].TextureCoordinate.Y = 0.0f;
            data[7].Position = new Vector3(vExtents.X, -vExtents.Y, vExtents.Z);
            data[7].TextureCoordinate.X = 0.0f; data[7].TextureCoordinate.Y = 1.0f;

            //bottom
            data[8].Position = new Vector3(-vExtents.X, -vExtents.Y, -vExtents.Z);
            data[8].TextureCoordinate.X = 1.0f; data[8].TextureCoordinate.Y = 0.0f;
            data[9].Position = new Vector3(-vExtents.X, -vExtents.Y, vExtents.Z);
            data[9].TextureCoordinate.X = 1.0f; data[9].TextureCoordinate.Y = 1.0f;
            data[10].Position = new Vector3(vExtents.X, -vExtents.Y, vExtents.Z);
            data[10].TextureCoordinate.X = 0.0f; data[10].TextureCoordinate.Y = 1.0f;
            data[11].Position = new Vector3(vExtents.X, -vExtents.Y, -vExtents.Z);
            data[11].TextureCoordinate.X = 0.0f; data[11].TextureCoordinate.Y = 0.0f;

            //top
            data[12].Position = new Vector3(vExtents.X, vExtents.Y, -vExtents.Z);
            data[12].TextureCoordinate.X = 0.0f; data[12].TextureCoordinate.Y = 0.0f;
            data[13].Position = new Vector3(vExtents.X, vExtents.Y, vExtents.Z);
            data[13].TextureCoordinate.X = 0.0f; data[13].TextureCoordinate.Y = 1.0f;
            data[14].Position = new Vector3(-vExtents.X, vExtents.Y, vExtents.Z);
            data[14].TextureCoordinate.X = 1.0f; data[14].TextureCoordinate.Y = 1.0f;
            data[15].Position = new Vector3(-vExtents.X, vExtents.Y, -vExtents.Z);
            data[15].TextureCoordinate.X = 1.0f; data[15].TextureCoordinate.Y = 0.0f;


            //left
            data[16].Position = new Vector3(-vExtents.X, vExtents.Y, -vExtents.Z);
            data[16].TextureCoordinate.X = 1.0f; data[16].TextureCoordinate.Y = 0.0f;
            data[17].Position = new Vector3(-vExtents.X, vExtents.Y, vExtents.Z);
            data[17].TextureCoordinate.X = 0.0f; data[17].TextureCoordinate.Y = 0.0f;
            data[18].Position = new Vector3(-vExtents.X, -vExtents.Y, vExtents.Z);
            data[18].TextureCoordinate.X = 0.0f; data[18].TextureCoordinate.Y = 1.0f;
            data[19].Position = new Vector3(-vExtents.X, -vExtents.Y, -vExtents.Z);
            data[19].TextureCoordinate.X = 1.0f; data[19].TextureCoordinate.Y = 1.0f;

            //right
            data[20].Position = new Vector3(vExtents.X, -vExtents.Y, -vExtents.Z);
            data[20].TextureCoordinate.X = 0.0f; data[20].TextureCoordinate.Y = 1.0f;
            data[21].Position = new Vector3(vExtents.X, -vExtents.Y, vExtents.Z);
            data[21].TextureCoordinate.X = 1.0f; data[21].TextureCoordinate.Y = 1.0f;
            data[22].Position = new Vector3(vExtents.X, vExtents.Y, vExtents.Z);
            data[22].TextureCoordinate.X = 1.0f; data[22].TextureCoordinate.Y = 0.0f;
            data[23].Position = new Vector3(vExtents.X, vExtents.Y, -vExtents.Z);
            data[23].TextureCoordinate.X = 0.0f; data[23].TextureCoordinate.Y = 0.0f;

            vertices.SetData<VertexPositionTexture>(data);


            indices = new IndexBuffer(graphicsService.GraphicsDevice, 
                                typeof(short),6*6, 
                                BufferUsage.WriteOnly);

            short[] ib = new short[6 * 6];

            for (int x = 0; x < 6; x++)
            {
                ib[x * 6 + 0] = (short) (x * 4 + 0);
                ib[x * 6 + 2] = (short) (x * 4 + 1);
                ib[x * 6 + 1] = (short) (x * 4 + 2);

                ib[x * 6 + 3] = (short) (x * 4 + 2);
                ib[x * 6 + 5] = (short) (x * 4 + 3);
                ib[x * 6 + 4] = (short) (x * 4 + 0);
            }

            indices.SetData<short>(ib);
            
        }

        public override void destroy()
        {

        }

        public override void update()
        {

        }



        public override void draw()
        {
            if (vertices == null)
                return;

            effect.Begin();
            effect.Parameters["worldViewProjection"].SetValue(
                Matrix.CreateTranslation(Owner.State.CurrentCamera.vPos) * 
                Owner.State.CurrentCamera.getViewMatrix() * 
                Owner.State.Projection);
        

            for (int x = 0; x < 6; x++)
            {
                float f=0;
                Vector3 vDirection = _owner.State.CurrentCamera.vTarget - _owner.State.CurrentCamera.vPos;

                switch(x)
                {
                case 0: //back
                    f = Vector3.Dot(vDirection, new Vector3(0, 0, 1));
                    break;
                case 1: //front
                    f = Vector3.Dot(vDirection, new Vector3(0, 0, -1));
                    break;
                case 2: //bottom
                    f = Vector3.Dot(vDirection, new Vector3(0, 1, 0));
                    break;
                case 3: //top
                    f = Vector3.Dot(vDirection, new Vector3(0, -1, 0));
                    break;
                case 4: //left
                    f = Vector3.Dot(vDirection, new Vector3(1, 0, 0));
                    break;
                case 5: //right
                    f = Vector3.Dot(vDirection, new Vector3(-1, 0, 0));
                    break;
                }

                if (f <= 0)
                {
                    Engine.GraphicsDevice.VertexDeclaration = vertexDecl;
                    Engine.GraphicsDevice.Vertices[0].SetSource(vertices, 0, 
                        vertexDecl.GetVertexStrideSize(0));

                    Engine.GraphicsDevice.Indices = indices;

                    effect.Parameters["baseTexture"].SetValue(textures[x]);
                    effect.Techniques[0].Passes[0].Begin();


                    Engine.GraphicsDevice.DrawIndexedPrimitives(PrimitiveType.TriangleList, 
                        0,x*4,4,x*6,2);
                    effect.Techniques[0].Passes[0].End();
                    
                }
            }
            
            effect.End();
        }

        public int DrawOrder
        {
            get { return 0; }
        }

        public bool Visible
        {
            get { return true; }
        }
    }
}


