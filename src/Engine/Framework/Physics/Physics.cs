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

using IlluminatrixEngine.Framework.Components;
using Microsoft.Xna.Framework;


namespace IlluminatrixEngine.Framework.Physics
{
    public delegate bool TestCollisionEvent(Unit u, Unit[] v);


    /// <summary>
    /// Static Physics class
    /// - 
    /// </summary>
    public static class Physics
    {
        /// <summary>
        /// Shifts a units between states.
        /// Performs step, etc.
        /// </summary>
        /// <param name="u"></param>
        public static void updateUnit(Unit u)
        {
            if (u.GetComponentType() == ComponentType.Component2D)
            {
                update2DUnit(u);
            }
            else if (u.GetComponentType() == ComponentType.Component3D)
            {
            }
        }


        /// <summary>
        /// Handle 2D unit updates
        /// </summary>
        /// <param name="u"></param>
        public static void update2DUnit(Unit u)
        {
            // shift the state, DO NOT touch LastState after this
            u.LastState = u.NewState;

            // store packets in vars for ease of coding
            PhysicsPacket state = u.NewState;

            // adjust the current state
            state.position.X++;
            state.position.Y++;

            // TODO
            /*
             Ideally here we want to move the unit on the path it is going on.
             So if it has a velocity and an angle, use the stuff we learned in class to move it
             */

            /* after this function you can perform collisions and determine
             where along the difference between the lastState and the newState to be
             */
        }


        /// <summary>
        /// Generate the world matrix for this unit based on its 
        /// position and angle.
        /// </summary>
        /// <param name="u"></param>
        /// <returns></returns>
        public static Matrix generateWorldMatrix(Unit u)
        {
            Matrix world = Matrix.Identity;

            if (u.GetComponentType() == ComponentType.Component3D)
            {
                world = Matrix.CreateScale(u.NewState.Scale) * Matrix.CreateRotationX(u.Xrotation) * Matrix.CreateRotationY(u.Yrotation) * Matrix.CreateRotationZ(u.Zrotation) * Matrix.CreateTranslation(u.NewState.position);
            }

            return world;
        }
    }
}
