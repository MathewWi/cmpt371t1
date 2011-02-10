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
using Microsoft.Xna.Framework.Graphics;

namespace IlluminatrixEngine.Framework.Effects
{
    public class ScreenText : ScreenEffect
    {
        public SpriteFont Font;
        public String Text;
        public float Scale;
        public Vector2 Position;
        public Vector2 Origin;
        public Color Color;
        public SpriteEffects SpriteEffect;
        public float Rotation;
        

        /// <summary>
        /// Public constructor
        /// </summary>
        /// <param name="offTimeInSecond"></param>
        /// <param name="onTimeInSecond"></param>
        public ScreenText(SpriteFont font, String text, float offTimeInSecond, float onTimeInSecond) :
            base(offTimeInSecond, onTimeInSecond)
        {
            Text = text;
            Font = font;
            Scale = 1.0f;
            Rotation = 0.0f;
            Position = new Vector2();
            Origin = new Vector2(0, 0);
            SpriteEffect = new SpriteEffects();
        }


        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        public Vector2 GetSize()
        {
            return this.Font.MeasureString(this.Text);
        }


        /// <summary>
        /// Gets the current alpha of the screen transition, ranging
        /// from 255 (fully active, no transition) to 0 (transitioned
        /// fully off to nothing).
        /// </summary>
        public byte TransitionAlpha
        {
            get { return (byte)(255 - TransitionPosition * 255); }
        }


        /// <summary>
        /// Simply call the engines fade for now
        /// </summary>
        public override void draw()
        {
            // When the menu selection changes, entries gradually fade between
            // their selected and deselected appearance, rather than instantly
            // popping to the new state.
            float fadeSpeed = (float)Engine.GameTime.ElapsedGameTime.TotalSeconds * 4;


           // if (_state == ScreenEffectState.TransitionOn

            float selectionFade = 2.0f;

            // Pulsate the size of the selected menu entry.
            double time = Engine.GameTime.TotalGameTime.TotalSeconds;

            float pulsate = (float)Math.Sin(time * 6) + 1;

            float scale = 1 + TransitionPosition * 0.05f * selectionFade;


            Engine.SpriteBatch.Begin();
            Engine.SpriteBatch.DrawString(this.Font, 
                                            this.Text, 
                                            this.Position, 
                                            this.Color, 
                                            this.Rotation, 
                                            this.Origin, 
                                            this.Scale * scale, 
                                            this.SpriteEffect, 
                                            0.0f);
            Engine.SpriteBatch.End();
        }


    }
}
