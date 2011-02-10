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
using Luminance.GameUnits;
using IlluminatrixEngine.Framework.States;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using IlluminatrixEngine.Framework.Resources;

namespace Luminance.Map
{
    public enum ToolType
    {
        Block,
        Emitter,
        Mirror,
        Prism,
        Goal,
        Eraser
    }


    /// <summary>
    /// Class to maintain a list of tools the user has
    /// </summary>
    public class Toolbelt
    {
        /// <summary>
        /// 
        /// </summary>
        private Dictionary<ToolType, int> _belt;
        public Dictionary<ToolType, int> Belt
        {
            get { return _belt; }
        }


        /// <summary>
        /// Window that is rendering this toolbelt
        /// </summary>
        private BasicWindow _owner;


        /// <summary>
        /// 
        /// </summary>
        public Rectangle BeltRect;
        public int BeltEntryWidth;
        public int BeltEntryHeight;
        
        private int _selected;
        public int Selected
        {
            get { return _selected; }
        }

        private int _highlighted;
        public int Highlighted
        {
            get { return _highlighted; }
        }

        private BaseEntity _selectedInstance;
        public BaseEntity SelectedEntityInstance
        {
            get { return _selectedInstance; }
        }

        /// <summary>
        /// 
        /// </summary>
        public Toolbelt(BasicWindow owner)
        {
            _belt = new Dictionary<ToolType, int>();
            _owner = owner;

            _selected = -1;
            _highlighted = -1;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="type"></param>
        /// <param name="amount"></param>
        public void addTool(ToolType type, int amount)
        {
            // store current selected 
            bool bResetSelected = false;
            ToolType tmpType = ToolType.Block;
            if (_selected != -1 && _selected < _belt.Count)
            {
                tmpType = _belt.ElementAt(_selected).Key;
                bResetSelected = true;
            }

            // new item
            if (!_belt.ContainsKey(type))
            {
                _belt.Add(type, amount);
            }
            else
            {
                _belt[type] += amount;
            }
               
            //check to reset
            if (bResetSelected)
            {
                // find the type index...
                int i = 0;
                for (i = 0; i < _belt.Count; i++)
                {
                    if (_belt.ElementAt(i).Key == tmpType)
                        break;
                }
                setSelected(i);
            }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="type"></param>
        public void dropTool(ToolType type)
        {
           bool b=  _belt.Remove(type);
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="index"></param>
        public void setHighlighted(int index)
        {
            // adjust new highlight
            _highlighted = index;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="index"></param>
        public void setSelected(int index)
        {
            // already selected, do nothing
            if (_selected == index)
                return;

            // only let selections if there are some left
            if (_belt.ElementAt(index).Value > 0)
            {
                _owner.removeComponent(SelectedEntityInstance);
                _selected = index;
                _selectedInstance = getInstanceFromType(_belt.ElementAt(index).Key);
                _owner.addComponent(SelectedEntityInstance);
            }
            else
            {
                _selected = -1;
            }
        }


        public void placeSelected(Level m, int screenX, int screenY)
        {
            // get and place the unit
            BaseEntity u = this.SelectedEntityInstance;
            if (!m.placeObject(u, new Vector3(screenX, screenY, 0)))
            {
                return;
            }

            // sfx
            Resources.getSfx("sfx.placeObject").Play();

            // remove from the window
            _owner.removeComponent(SelectedEntityInstance);

            // decrement the value
            _belt[_belt.ElementAt(_selected).Key]--;

            BaseEntity temp = u;

            // this tool item is out, select null
            if (_belt.ElementAt(_selected).Value <= 0)
            {
                this.dropTool(_belt.ElementAt(_selected).Key);

                this._selected = -1;
                this._selectedInstance = null;

                return;
            }

            // this tool item still has an amount, get new instance
            this._selectedInstance = getInstanceFromType(_belt.ElementAt(_selected).Key);
            if (this._selectedInstance != null)
            {
                float rotation = u.Yrotation;
                if (SelectedEntityInstance is Mirror)
                {
                    rotation += ((Mirror)SelectedEntityInstance as Mirror).MIRROR_ROTATION_OFFSET;
                }
                this.SelectedEntityInstance.RotateAboutY(rotation);
                _owner.addComponent(SelectedEntityInstance);
            }

            // activate u
            u.IsActive = true;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        public int getAmountOfSelected()
        {
            if (_selected >= 0)
            {
                return _belt.ElementAt(_selected).Value;
            }

            return 0;
        }


        /// <summary>
        /// Get an instance of a tool base version based on its type.
        /// </summary>
        /// <param name="type"></param>
        /// <returns></returns>
        public BaseEntity getInstanceFromType(ToolType type)
        {
            if (type == ToolType.Block)
            {
                return new Block(_owner);
            }
            else if (type == ToolType.Emitter)
            {
                return new Emitter(_owner);
            }
            else if (type == ToolType.Goal)
            {
                return new Goal(_owner);
            }
            else if (type == ToolType.Mirror)
            {
                return new Mirror(_owner);
            }
            else if (type == ToolType.Prism)
            {
                return new Prism(_owner);
            }
            else if (type == ToolType.Eraser)
            {
                return new Eraser(_owner);
            }

            return null;
        }


        /// <summary>
        /// Get an instance of a tool base version based on its type.
        /// </summary>
        /// <param name="type"></param>
        /// <returns></returns>
        public Texture2D getIconFromType(ToolType type)
        {
            if (type == ToolType.Block)
            {
                return Resources.getTexture("texture.brickicon");
            }
            else if (type == ToolType.Emitter)
            {
                return Resources.getTexture("texture.emittericon");
            }
            else if (type == ToolType.Goal)
            {
                return Resources.getTexture("texture.goalicon");
            }
            else if (type == ToolType.Mirror)
            {
                return Resources.getTexture("texture.mirroricon");
            }
            else if (type == ToolType.Prism)
            {
                return Resources.getTexture("texture.prismicon");
            }
            else if (type == ToolType.Eraser)
            {
                return Resources.getTexture("texture.erasericon");
            }

            return null;
        }


        /// <summary>
        /// Get an instance of a tool base version based on its type.
        /// </summary>
        /// <param name="type"></param>
        /// <returns></returns>
        public ToolType getTypeFromInstance(BaseEntity e)
        {
            if (e is Block)
            {
                return ToolType.Block;
            }
            else if (e is Emitter)
            {
                return ToolType.Emitter;
            }
            else if (e is Goal)
            {
                return ToolType.Goal;
            }
            else if (e is Mirror)
            {
                return ToolType.Mirror;
            }
            else if (e is Prism)
            {
                return ToolType.Prism;
            }
            else if (e is Eraser)
            {
                return ToolType.Eraser;
            }

            //error exception, @HACK
            throw new Exception("This entity is not a tool!");
        }
    }
}
