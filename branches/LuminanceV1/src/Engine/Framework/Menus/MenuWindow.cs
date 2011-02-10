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

using IlluminatrixEngine.Framework.States;
using IlluminatrixEngine.Framework.Input;
using Microsoft.Xna.Framework.Graphics;
using IlluminatrixEngine.Framework.Components;
using Microsoft.Xna.Framework.Input;


namespace IlluminatrixEngine.Framework.Menus
{
    public class MenuWindow : BasicWindow
    {
        //private List<MenuCategory> _categories;
        private KeyboardInput _keyb;
        private MouseInput _mouse;
        private Dictionary<MenuEntry, MenuEvent> _toggleEvents;
        private Dictionary<MenuEntry, MenuEvent> _selectedEvents;
        private MenuCategory _currentCategory;


        /// <summary>
        /// Basic Constructor
        /// </summary>
        /// <param name="s"></param>
        public MenuWindow(BasicState s)
            : base(s)
        {
            // initialize the keyboard events
            _keyb = new KeyboardInput();
            _keyb.addInputDownEvent(this, Keys.Enter, keyEnterPressed);
            _keyb.addInputDownEvent(this, Keys.Up, keyUpPressed);
            _keyb.addInputDownEvent(this, Keys.Down, keyDownPressed);
            _keyb.addInputDownEvent(this, Keys.Left, keyLeftPressed);
            _keyb.addInputDownEvent(this, Keys.Right, keyRightPressed);

            _mouse = new MouseInput();

            // init the event dictionaries
            _toggleEvents = new Dictionary<MenuEntry, MenuEvent>();
            _selectedEvents = new Dictionary<MenuEntry, MenuEvent>();
        }

        public void addCategory(MenuCategory cat)
        {
            this.addComponent(cat);

            if (_currentCategory == null)
            {
                _currentCategory = cat;
            }
        }

        public void addEntryToCategory(MenuCategory cat, MenuEntry ent, MenuEvent selected, MenuEvent toggle)
        {
            // add selected event for this entry if one was provided
            if (selected != null)
            {
                _selectedEvents.Add(ent, selected);
            }
                
            // add toggle event for entry if one was provided
            if (toggle != null)
            {
                _toggleEvents.Add(ent, toggle);
            }

            // actually add the entry
            cat.addEntry(ent);
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="cat"></param>
        public void switchToCategory(MenuCategory cat)
        {
            if (_components.Contains(cat))
            {
                _currentCategory = cat;
            }
            else
            {
                throw new Exception("Invalid category called!");
            }
        }


        /// <summary>
        /// Fire the selected event of the current entry in the current category.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// <returns></returns>
        public bool keyEnterPressed(object sender, InputArgs<Keys, KeyboardState> e)
        {
            if (_selectedEvents.ContainsKey(_currentCategory.CurrentEntry) &&
                _selectedEvents[_currentCategory.CurrentEntry] != null)
            {
                _selectedEvents[_currentCategory.CurrentEntry](this, new MenuEventArgs(_currentCategory, _currentCategory.CurrentEntry));
            }

            return true;
        }


        /// <summary>
        /// Scrolls up the menu
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// <returns></returns>
        public bool keyUpPressed(object sender, InputArgs<Keys, KeyboardState> e)
        {
            Resources.Resources.getSfx("sfx.changeEntry").Play();
            _currentCategory.decrementSelection();

            return true;
        }


        /// <summary>
        /// Scrolls down the menu
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// <returns></returns>
        public bool keyDownPressed(object sender, InputArgs<Keys, KeyboardState> e)
        {
            Resources.Resources.getSfx("sfx.changeEntry").Play();
            _currentCategory.incrementSelection();

            return true;
        }


        /// <summary>
        /// Scrolls the entry value to the left
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// <returns></returns>
        public bool keyLeftPressed(object sender, InputArgs<Keys, KeyboardState> e)
        {
            return true;
        }


        /// <summary>
        /// Scrolls the entry value to the right
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// <returns></returns>
        public bool keyRightPressed(object sender, InputArgs<Keys, KeyboardState> e)
        {
            return true;
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
            _keyb.update();
            _keyb.processInput(this);

            _mouse.update();

            // mouse override selection here
            foreach (MenuEntry ent in _currentCategory.MenuEntries)
            {
                if (_mouse.CurrentState.X >= ent.Position.X &&
                    _mouse.CurrentState.Y >= ent.Position.Y &&
                    _mouse.CurrentState.X <= ent.Position.X + ent.GetCurrentSize().X &&
                    _mouse.CurrentState.Y <= ent.Position.Y + ent.GetCurrentSize().Y &&
                    _currentCategory.CurrentEntry != ent)
                {
                    Resources.Resources.getSfx("sfx.changeEntry").Play();
                    _currentCategory.setSelected(ent);
                    break;
                }
            }

            if (_mouse.WasButtonHeld(MouseButtons.Left))
            {
                if (_selectedEvents.ContainsKey(_currentCategory.CurrentEntry) &&
                    _selectedEvents[_currentCategory.CurrentEntry] != null)
                {
                    _selectedEvents[_currentCategory.CurrentEntry](this, new MenuEventArgs(_currentCategory, _currentCategory.CurrentEntry));
                }
            }

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
        /// 
        /// </summary>
        public override void draw()
        {
            // draw everything in the component bucket
            foreach (Component c in _components)
            {
                if (c.IsVisible)
                {
                    c.draw();
                }
            }
        }


    }
}
