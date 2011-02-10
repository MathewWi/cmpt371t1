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
using IlluminatrixEngine.Framework.States;
using IlluminatrixEngine.Framework.Effects;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace IlluminatrixEngine.Framework.Menus
{
    public class MenuEntry : Component
    {
        private String _name;
        private String[] _values;
        private ScreenText _text;

        public Vector2 Position;

        
        /// <summary>
        /// 
        /// </summary>
        /// <param name="owner"></param>
        /// <param name="name"></param>
        /// <param name="values"></param>
        public MenuEntry(BasicWindow owner, String name, String[] values)
            : base(owner)
        {
            // store the name and values for later reuse
            _name = name;
            _values = values;

            // init the entry text
            _text = new ScreenText(Engine.MenuFont, _name, 1.0f, 1.5f);
            _text.Color = Color.Black;
            if (_values != null && _values.Length > 0)
            {
                _text.Text = _name + ": " + _values[0];
            }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        public Vector2 GetCurrentSize()
        {
            return _text.GetSize();
        }


        public void setSelected(bool b)
        {
            if (b)
            {
                _text.Color = Color.Red;
                _text.effectOn();
                _text.setLoop(true);
            }
            else
            {
                _text.Color = Color.Black;
                _text.effectOff();
                _text.setLoop(false);
            }
        }

        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        public override ComponentType GetComponentType()
        {
            return ComponentType.ComponentStatic;
        }


        /// <summary>
        /// 
        /// </summary>
        public override void init()
        {
            
        }


        /// <summary>
        /// 
        /// </summary>
        public override void destroy()
        {
            
        }


        /// <summary>
        /// 
        /// </summary>
        public override void update()
        {
            _text.Position = this.Position;
            _text.update();
        }


        /// <summary>
        /// 
        /// </summary>
        public override void draw()
        {
            _text.draw();
        }
    }
}
