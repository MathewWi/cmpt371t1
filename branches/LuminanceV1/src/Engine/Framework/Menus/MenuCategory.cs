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
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using IlluminatrixEngine.Framework.Effects;

namespace IlluminatrixEngine.Framework.Menus
{
    public class MenuCategory : Component
    {
        /// <summary>
        /// List of menu entries in this category
        /// </summary>
        private List<MenuEntry> _menuEntries;
        public List<MenuEntry> MenuEntries
        {
            get { return _menuEntries; }
        }
        public MenuEntry CurrentEntry
        {
            get { return (_selected < _menuEntries.Count ? _menuEntries[_selected] : null); }
        }


        /// <summary>
        /// Name of the Category
        /// </summary>
        private String _name;
        public String Name
        {
            get { return _name; }
            set { _name = value; }
        }


        /// <summary>
        /// Rectangle defining the bounds of this category
        /// </summary>
        private Rectangle _rect;


        /// <summary>
        /// 
        /// </summary>
        private ScreenText _categoryText;

        /// <summary>
        /// Helper vector to calculate positions of entries
        /// </summary>
        private Vector2 _vSpacing;

        /// <summary>
        /// Helper to calculate positions of entries
        /// </summary>
        private Vector2 _vCenter;


        private int _selected;

        /// <summary>
        /// Public Constructor
        /// </summary>
        /// <param name="owner"></param>
        /// <param name="name"></param>
        public MenuCategory(BasicWindow owner, String name)
            : base(owner)
        {
            _menuEntries = new List<MenuEntry>();
            _selected = 0;
            _name = name;
        }


        public void setSelected(MenuEntry ent)
        {
            if (_menuEntries[_selected] != null)
            {
                _menuEntries[_selected].setSelected(false);
            }
            _selected = _menuEntries.IndexOf(ent);
            _updateSelected();
        }

        /// <summary>
        /// 
        /// </summary>
        public void incrementSelection()
        {
            if (_menuEntries[_selected] != null)
            {
                _menuEntries[_selected].setSelected(false);
            }
            _selected++;
            _updateSelected();
        }


        /// <summary>
        /// 
        /// </summary>
        public void decrementSelection()
        {
            if (_menuEntries[_selected] != null)
            {
                _menuEntries[_selected].setSelected(false);
            }
            _selected--;
            _updateSelected();
        }


        private void _updateSelected()
        {
            // cap selected
            if (_selected >= _menuEntries.Count)
            {
                _selected = 0;
            }
            else if (_selected < 0)
            {
                _selected = _menuEntries.Count - 1;
            }

            _menuEntries[_selected].setSelected(true);
        }


        /// <summary>
        /// Adds an entry, recalculates the best position for all entries
        /// </summary>
        /// <param name="ent"></param>
        public void addEntry(MenuEntry ent)
        {
            // precalcs, index of new entry
            int index = _menuEntries.Count;
            Vector2 topEntryPos = new Vector2(_vCenter.X - (ent.GetCurrentSize().X / 2),
                                              _categoryText.Position.Y + _categoryText.GetSize().Y + _rect.Height * 0.15f);

            // add to the list
            _menuEntries.Add(ent);

            // if this is our first entry in the list, default select it
            if (_menuEntries.Count == 1) 
            {
                _selected = 0;
                _updateSelected();
            }

            // figure out how big our entry rectangle will be
            // do this by creating a rectangle and adding all the sizes
            Vector2 vRect = new Vector2();
            for (int i = 0; i < _menuEntries.Count; i++)
            {
                // do max algo for longest width
                if (_menuEntries[i].GetCurrentSize().X > vRect.X)
                {
                    vRect.X = _menuEntries[i].GetCurrentSize().X;
                }

                // just add our heights
                vRect.Y += _menuEntries[i].GetCurrentSize().Y + _vSpacing.Y;
            }

            // now center the rect, changing the X and Y from sizes to coordinates
            vRect.X = (_vCenter.X - (vRect.X / 2));
            vRect.Y = (_vCenter.Y - (vRect.Y / 2));

            // reposition all
            for (int i = 0; i < _menuEntries.Count; i++)
            {
                _menuEntries[i].Position.X = vRect.X;
                _menuEntries[i].Position.Y = vRect.Y + (i * (_vSpacing.Y + ent.GetCurrentSize().Y));
            }
        }


        /// <summary>
        /// This is a static type component
        /// Not to be checked for collision or anything
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
            // grab view and init the rect for the menu
            Viewport v = Engine.GraphicsDevice.Viewport;
            _rect = new Rectangle(v.X, v.Y, v.Width, v.Height);

            // lets move it in 30% on all sides, 15% on height
            _rect.X += (int)(_rect.Width * 0.30f);
            _rect.Y += (int)(_rect.Height * 0.15f);
            _rect.Width -= (int)(_rect.Width * 0.60f);
            _rect.Height -= (int)(_rect.Height * 0.30f);

            // precalc some values for positioning, simplify this
            _vSpacing = new Vector2(_rect.Width * 0.05f, _rect.Height * 0.025f);
            _vCenter = new Vector2( _rect.X + (_rect.Width / 2), _rect.Y + (_rect.Height / 2) );

            // position category name
            _categoryText = new ScreenText(Engine.MenuFont, _name, 1.5f, 1.0f);
            _categoryText.Scale = 1.25f;
            _categoryText.Color = Color.Black;
            _categoryText.Position.X = _rect.X + (_vSpacing.X / _categoryText.Scale);
            _categoryText.Position.Y = _rect.Y + (_vSpacing.Y / _categoryText.Scale);
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
            Viewport v = Engine.GraphicsDevice.Viewport;
            _rect = new Rectangle(v.X, v.Y, v.Width, v.Height);

            // lets move it in 30% on all sides, 15% on height
            _rect.X += (int)(_rect.Width * 0.30f);
            _rect.Y += (int)(_rect.Height * 0.15f);
            _rect.Width -= (int)(_rect.Width * 0.60f);
            _rect.Height -= (int)(_rect.Height * 0.30f);

            // precalc some values for positioning, simplify this
            _vSpacing = new Vector2(_rect.Width * 0.05f, _rect.Height * 0.025f);
            _vCenter = new Vector2(_rect.X + (_rect.Width / 2), _rect.Y + (_rect.Height / 2));

            // position category name
            _categoryText = new ScreenText(Engine.MenuFont, _name, 1.5f, 1.0f);
            _categoryText.Scale = 1.55f;
            _categoryText.Color = Color.Wheat;
            _categoryText.Position.X = _rect.X + (_vSpacing.X / _categoryText.Scale);
            _categoryText.Position.Y = _rect.Y + (_vSpacing.Y / _categoryText.Scale);

            // figure out how big our entry rectangle will be
            // do this by creating a rectangle and adding all the sizes
            Vector2 vRect = new Vector2();
            for (int i = 0; i < _menuEntries.Count; i++)
            {
                // do max algo for longest width
                if (_menuEntries[i].GetCurrentSize().X > vRect.X)
                {
                    vRect.X = _menuEntries[i].GetCurrentSize().X;
                }

                // just add our heights
                vRect.Y += _menuEntries[i].GetCurrentSize().Y + _vSpacing.Y;
            }

            // now center the rect, changing the X and Y from sizes to coordinates
            vRect.X = (_vCenter.X - (vRect.X / 2));
            vRect.Y = (_vCenter.Y - (vRect.Y / 2));

            // reposition all
            for (int i = 0; i < _menuEntries.Count; i++)
            {
                _menuEntries[i].Position.X = vRect.X;
                _menuEntries[i].Position.Y = vRect.Y + (i * (_vSpacing.Y + _menuEntries[i].GetCurrentSize().Y));
            }

            // update all the entries
            for (int i = 0; i < _menuEntries.Count; i++)
            {
                // update it
                _menuEntries[i].update();
            }
        }


        /// <summary>
        /// 
        /// </summary>
        public override void draw()
        {
            // draw category background
            Engine.SpriteBatch.Begin();
            Engine.SpriteBatch.Draw(Engine.BlankTexture, _rect, new Color(Color.Black, (byte)85));

            // render alpha background lines
            /*for (int i = 0; i <= (_rect.Height/2)-1; i++)
            {
                // even line
                if (i % 2 == 0)
                {
                    // draw gray line with alpha
                    Engine.SpriteBatch.Draw(Engine.BlankTexture,
                                            new Rectangle(_rect.X, _rect.Y + i*2, _rect.Width, 1),
                                            new Color(Color.Honeydew, (byte)25));
                }
                // odd line
                else
                {
                    // draw less gray line alpha
                    // draw gray line with alpha
                    Engine.SpriteBatch.Draw(Engine.BlankTexture,
                                            new Rectangle(_rect.X, _rect.Y + i*2, _rect.Width, 1),
                                            new Color(Color.SeaShell, (byte)10));
                }
            }*/
            Engine.SpriteBatch.End();

            // draw category name
            _categoryText.draw();

            // draw all the entries
            foreach (MenuEntry ent in _menuEntries)
            {
                ent.draw();
            }
        }


    }
}
