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
using Microsoft.Xna.Framework.Content;


namespace IlluminatrixEngine.Framework.Cameras
{
    /// <summary>
    /// Advanced Camera class for 3D games
    /// </summary>    
    public class Camera
    {
        /// <summary>
        /// Position of the camera
        /// </summary>
        public Vector3 vPos;
        public Vector3 vTarget;
        public Vector3 vUp;
        public Vector3 vReference;

        public float cameraYaw;
        public float cameraPitch;


        /// <summary>
        /// Basic constructor
        /// </summary>
        public Camera()
        {
            vPos = new Vector3(0.0f, 0.0f, 0.0f);
            vReference = new Vector3(0.0f, 0.0f, 0.0f);
            vUp = Vector3.Up;

            vTarget = vReference + vPos;
        }


        /// <summary>
        /// Full constructor
        /// </summary>
        /// <param name="xPos"></param>
        /// <param name="yPos"></param>
        /// <param name="xPos"></param>
        /// <param name="xUp"></param>
        /// <param name="yUp"></param>
        /// <param name="zUp"></param>
        /// <param name="xRef"></param>
        /// <param name="yRef"></param>
        /// <param name="zRef"></param>
        public Camera(float xPos, float yPos, float zPos,
                float xUp, float yUp, float zUp,
                float xRef, float yRef, float zRef)                
        {
            vPos = new Vector3(xPos, yPos, zPos);
            vUp = new Vector3(xUp, yUp, zUp);
            vReference = new Vector3(xRef, yRef, zRef);

            vTarget = vReference + vPos;
        }

        public void update(Vector3 vMove, Vector3 vRotation)
        {
            // @TODO
            cameraPitch += vRotation.Y;// (mouseY * 0.4f) * deltaTime;
            cameraYaw -= vRotation.X;// (mouseX * 0.4f) * deltaTime;

            cameraPitch = MathHelper.Clamp(cameraPitch, MathHelper.ToRadians(-89.9f), MathHelper.ToRadians(89.9f));

            //Mouse.SetPosition(graphics.GraphicsDevice.Viewport.Width / 2, graphics.GraphicsDevice.Viewport.Height / 2);

            Matrix cameraViewRotationMatrix = Matrix.CreateRotationX(cameraPitch) * Matrix.CreateRotationY(cameraYaw);
            Matrix cameraMoveRotationMatrix = Matrix.CreateRotationX(cameraPitch) * Matrix.CreateRotationY(cameraYaw);

            Vector3 transformedCameraReference = Vector3.Transform(vReference, cameraViewRotationMatrix);

            vPos += Vector3.Transform(vMove, cameraMoveRotationMatrix);
            vTarget = transformedCameraReference + vPos;

            /*System.Console.Write("vPos: " + vPos + "\n");
            System.Console.Write("vTarget: " + vTarget + "\n");
            System.Console.Write("vReference: " + vReference + "\n\n");*/
        }

        /// <summary>
        /// Get the camera view matrix.
        /// To be used with effects during rendering phase.
        /// </summary>
        /// <returns>The LookAt matrix for this camera.</returns>
        public Matrix getViewMatrix()
        {
            return Matrix.CreateLookAt(vPos, vTarget, vUp);            
        }


        public void setPosition(float x, float y, float z)
        {
            vPos.X = x;
            vPos.Y = y;
            vPos.Z = z;
        }


        public void setReference(float x, float y, float z)
        {
            vReference.X = x;
            vReference.Y = y;
            vReference.Z = z;
        }


        public void setUp(float x, float y, float z)
        {
            vUp.X = x;
            vUp.Y = y;
            vUp.Z = z;
        }
    }
}
