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

using Microsoft.Xna.Framework.Content;

using IlluminatrixEngine.Framework;
using IlluminatrixEngine.Framework.Physics;
using IlluminatrixEngine.Framework.Components;
using IlluminatrixEngine.Framework.States;

using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using IlluminatrixEngine.Framework.Resources;

namespace Luminance.GameUnits
{
    public class Emitter : BaseEntity
    {
        /// <summary>
        /// Basic Emitter Constructor
        /// </summary>
        public Emitter(BasicWindow w) : base(w)
        {
            outputs.Add(createBeam(-Vector3.UnitZ));
            outputs.ElementAt(0).Drawable = true;
        }

        public override String getIconTextureKey()
        {
            return "texture.emittericon";
        }

        public override void init()
        {
            model = Resources.getModel("model.emitter");
        }

        public override bool hasInputs()
        {
            return true;
        }
    }
}
