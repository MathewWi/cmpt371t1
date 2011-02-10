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
    public class Prism : BaseEntity
    {
        /// <summary>
        /// Basic Mirror Constructor
        /// </summary>
        public Prism(BasicWindow w)
            : base(w)
        {
            outputs.Add(createBeam(-Vector3.UnitZ)); //Up
            outputs.Add(createBeam(-Vector3.UnitX));  //Left
            outputs.Add(createBeam(Vector3.UnitZ));  //Down
            outputs.Add(createBeam(Vector3.UnitX)); //Right
            this.NewState.Scale = 2.0f;
        }

        public override String getIconTextureKey()
        {
            return "texture.prismicon";
        }

        public override void init()
        {
            model = Resources.getModel("model.prism");
        }

        protected override void updateOutputs()
        {
            foreach (Beam b in outputs)
            {
                b.Drawable = false;
            }
            Beam up = outputs.ElementAt(0);
            Beam left = outputs.ElementAt(1);
            Beam down = outputs.ElementAt(2);
            Beam right = outputs.ElementAt(3);
            foreach (Beam b in inputs)
            {
                if (b.Color != Color.White)
                {
                    continue;
                }
                if (up.intersects(b.Source) != null)
                {
                    left.Drawable = true;
                    left.Color = Color.Red;
                    down.Drawable = true;
                    down.Color = Color.Green;
                    right.Drawable = true;
                    right.Color = Color.Blue;
                }

                if (left.intersects(b.Source) != null)
                {
                    down.Drawable = true;
                    down.Color = Color.Red;
                    right.Drawable = true;
                    right.Color = Color.Green;
                    up.Drawable = true;
                    up.Color = Color.Blue;
                }

                if (down.intersects(b.Source) != null)
                {
                    left.Drawable = true;
                    left.Color = Color.Blue;
                    up.Drawable = true;
                    up.Color = Color.Green;
                    right.Drawable = true;
                    right.Color = Color.Red;
                }

                if (right.intersects(b.Source) != null)
                {
                    up.Drawable = true;
                    up.Color = Color.Red;
                    left.Drawable = true;
                    left.Color = Color.Green;
                    down.Drawable = true;
                    down.Color = Color.Blue;
                }
            }
        }
    }
}
