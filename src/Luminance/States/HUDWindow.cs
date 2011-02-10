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
using System.Diagnostics;

using IlluminatrixEngine.Framework;
using IlluminatrixEngine.Framework.States;
using IlluminatrixEngine.Framework.Physics;
using IlluminatrixEngine.Framework.Components;
using IlluminatrixEngine.Framework.Input;

using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

using Luminance.GameUnits;
using Luminance.Map;
using IlluminatrixEngine.Framework.Effects;
using IlluminatrixEngine.Framework.Resources;


namespace Luminance.States
{
    public class HUDWindow : BasicWindow
    {
        private MouseInput _mouse;
        private Toolbelt _tools;


        private int _selected;
        public int Selected
        {
            get { return _selected; }
        }

        private TimeSpan _startTime;

        /// <summary>
        /// Public Constructor, with owner param.
        /// </summary>
        /// <param name="s"></param>
        public HUDWindow(BasicState s, MouseInput mouse, Toolbelt tools)
            : base(s)
        {
            _mouse = mouse;
            _tools = tools;
            _selected = -1;
        }


        /// <summary>
        /// 
        /// </summary>
        public void resetTimer()
        {
            _startTime = Engine.GameTime.TotalRealTime;
        }


        public TimeSpan timeDelta()
        {
            return Engine.GameTime.TotalRealTime - _startTime;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="x"></param>
        /// <param name="y"></param>
        /// <returns></returns>
        public int collideWithToolEntry(int x, int y)
        {
            // verify we in y range
            if (y > _tools.BeltRect.Y && y < _tools.BeltRect.Y + _tools.BeltRect.Height)
            {
                // verify the x
                if (x > _tools.BeltRect.X && x < _tools.BeltRect.X + _tools.BeltRect.Width)
                {
                    // figure out which item, constraint x to our index
                    int index = (x - _tools.BeltRect.X) / _tools.BeltEntryWidth;

                    return index;
                }
            }

            return -1;
        }


        /// <summary>
        /// Handle placing objects on the map
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// <returns></returns>
        public bool mouseLeftClick(object sender, InputArgs<MouseButtons, MouseState> e)
        {
            int index = this.collideWithToolEntry(e.currentState.X, e.currentState.Y);
#if DEBUG
            //Console.WriteLine("Select ToolEntry: " + index);
#endif
            if (index >= 0 && index != _tools.Selected)
            {
                Resources.getSfx("sfx.toolBeltSelect").Play();
            }
            _tools.setSelected(index);

            return true;
        }


        /// <summary>
        /// Initialize this the main game window
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
        /// Update all units in the unit bucket
        /// TODO - Check component flags and types before calling update()
        /// </summary>
        public override void update()
        {
            _tools.setHighlighted(collideWithToolEntry(_mouse.CurrentState.X, _mouse.CurrentState.Y));

            // draw everything in the component bucket
            foreach (Component c in _components)
            {
                if (c.IsActive)
                {
                    c.update();
                }
            }
        }


        /// <summary>
        /// Draw all units in the unit bucket
        /// TODO - Check component flags and types before calling update()
        /// </summary>
        public override void draw()
        {
            //draw the toolbelt across the screen
            Viewport view = Engine.GraphicsDevice.Viewport;

            // draw the time
            TimeSpan span = timeDelta();//Engine.GameTime.TotalRealTime;
            String time = "" +
             span.Hours.ToString("00") + ":" +
             span.Minutes.ToString("00") + ":" +
             span.Seconds.ToString("00");

            ScreenText text = new ScreenText(Engine.GameFont, time, 1.0f, 1.0f);
            text.Color = Color.White;
            text.Scale = 2.0f;
            text.Position.X = (view.Width / 2) - ((text.GetSize().X / 2) * 2);
            text.Position.Y = view.Height * 0.01f;
            text.draw();

            // get some basic sizes
            int topY = (int)(view.Height * 0.85f);

            _tools.BeltEntryWidth = (int)(view.Width * 0.12f);
            _tools.BeltEntryHeight = view.Height - topY;

            int amountRectWidth = (int)(_tools.BeltEntryWidth * 0.20f);
            int amountRectHeight = (int)(_tools.BeltEntryHeight * 0.20f);

            int iconWidth = (int)(_tools.BeltEntryWidth * 0.75f);
            int iconHeight = (int)(_tools.BeltEntryHeight * 0.65f);

            // draw a line across screen
            Rectangle toolbeltTopLine = new Rectangle(0,
                                                        topY,
                                                        view.Width,
                                                        (int)(view.Height * 0.010f));



            // figure out how many different tools we have to get rect sizes
            _tools.BeltRect = new Rectangle((view.Width / 2) - ((_tools.BeltEntryWidth * _tools.Belt.Count) / 2),
                                            topY,
                                             _tools.BeltEntryWidth * _tools.Belt.Count,
                                            _tools.BeltEntryHeight);
           
            // draw each tool belt entry
            for (int i = 0; i < _tools.Belt.Count; i++)
            {
                Engine.SpriteBatch.Begin();

                // rect of the item
                Rectangle itemRect = new Rectangle(_tools.BeltRect.X + (i * _tools.BeltEntryWidth),
                                                    _tools.BeltRect.Y,
                                                     _tools.BeltEntryWidth,
                                                    _tools.BeltEntryHeight);

                //draw amount rectangle
                Rectangle itemAmount = new Rectangle( (itemRect.X + itemRect.Width/2) - (amountRectWidth / 2),
                                                     itemRect.Y,
                                                     amountRectWidth,
                                                     amountRectHeight);

                Rectangle itemIconRect = new Rectangle((itemRect.X + itemRect.Width / 2) - (iconWidth / 2),
                                                        (itemRect.Y + itemRect.Height / 2) - (iconHeight / 2),
                                                        iconWidth,
                                                        iconHeight);

                // draw tool frame with appropriate colors
                Color toolColor = Color.White;
                if (_tools.Highlighted == i)
                {
                    toolColor = Color.Yellow;
                }

                if (_tools.Selected == i)
                {
                    toolColor = Color.Green;
                }
                Engine.SpriteBatch.Draw(Resources.getTexture("texture.toolbg"), itemRect, toolColor);

                // draw the icon
                Engine.SpriteBatch.Draw(_tools.getIconFromType(_tools.Belt.ElementAt(i).Key), itemIconRect, Color.White);
                Engine.SpriteBatch.End();
    
                // draw the amount left of this tool
                int amountNum = _tools.Belt.ElementAt(i).Value;
                String amountString;
                if (amountNum > 99) 
                {
                    amountString = "";
                }
                else
                {
                    amountString = amountNum.ToString();
                }

                text.Scale = 1.0f;
                text.Text = amountString;
                text.Position = new Vector2((itemAmount.X + itemAmount.Width / 2) - (text.GetSize().X / 2),
                                            (itemAmount.Y + itemAmount.Height / 2) - (text.GetSize().Y / 2));
                text.Color = Color.Black;
                text.draw();

                text.Text = _tools.Belt.ElementAt(i).Key.ToString();
                text.Position.X = (itemAmount.X + itemAmount.Width / 2) - (text.GetSize().X / 2);
                text.Position.Y = itemIconRect.Y + itemIconRect.Height - 2.0f;
                text.draw();
            }


        }


     
    }
}