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

using Microsoft.Xna.Framework.Input;

namespace IlluminatrixEngine.Framework.Input
{
    public class KeyboardInput : InputDevice<Keys, KeyboardState>
    {
        /// <summary>
        /// Constructor of the Keyboard input device.
        /// </summary>
        public KeyboardInput()
        {
            // get our objects initialized by a quick call to processInput.
            processInput(null);
        }


        /// <summary>
        /// Updates this input device.
        /// This transitions it across states.
        /// </summary>
        public override void update()
        {
            //store the last state
            _lastState = _currentState;

            //fetch the current state
            _currentState = Keyboard.GetState();
        }


        /// <summary>
        /// Perform a basic update of the keyboard.
        /// This updates the state and stores the old one.
        /// All events fired from particular keys will be done here.
        /// </summary>
        public override void processInput(Object owner)
        {
            // loop through each key in the dictionary
            foreach (Keys key in _inputBinded)
            {
                //check if key was pressed down
                if (wasKeyPressed(key))
                {
                    if (_eventDown.ContainsKey(owner) && _eventDown[owner].ContainsKey(key))
                    {
                        _eventDown[owner][key](this, new InputArgs<Keys, KeyboardState>(key, this));
                    }
                }

                // check if key is released up
                if (wasKeyReleased(key))
                {
                    if (_eventUp.ContainsKey(owner) && _eventUp[owner].ContainsKey(key))
                    {
                        _eventUp[owner][key](this, new InputArgs<Keys, KeyboardState>(key, this));
                    }
                }

                // check if key is held
                if (wasKeyHeld(key))
                {
                    if (_eventHeld.ContainsKey(owner) && _eventHeld[owner].ContainsKey(key))
                    {
                        _eventHeld[owner][key](this, new InputArgs<Keys, KeyboardState>(key, this));
                    }
                }
            }
        }


        /// <summary>
        /// Determine whether a particular is down as of this current state.
        /// </summary>
        /// <param name="key">The key to check for</param>
        /// <returns>Whether this key is down as of last update</returns>
        public bool isKeyDown(Keys key)
        {
            return _currentState.IsKeyDown(key);
        }


        /// <summary>
        /// Determine whether a particular is up as of this current state.
        /// </summary>
        /// <param name="key">The key to check for</param>
        /// <returns>Whether this key is up as of last update</returns>
        public bool isKeyUp(Keys key)
        {
            return _currentState.IsKeyUp(key);
        }


        /// <summary>
        /// Check if this key was pressed down as of last update.
        /// This means it was up last state and down this frame.
        /// </summary>
        /// <param name="key">The key to check for</param>
        /// <returns>Only returns true if key was up last state and down in the current state</returns>
        public bool wasKeyPressed(Keys key)
        {
            return LastState.IsKeyUp(key) && _currentState.IsKeyDown(key);
        }


        /// <summary>
        /// Check if this key was released between updates.
        /// </summary>
        /// <param name="key">The key to check for</param>
        /// <returns>Only returns true if key was down last state and up in the current state</returns>
        public bool wasKeyReleased(Keys key)
        {
            return LastState.IsKeyDown(key) && _currentState.IsKeyUp(key);
        }


        /// <summary>
        /// Check if this key is being held down.
        /// </summary>
        /// <param name="key">The key to check for</param>
        /// <returns>Only returns true if key was down last state and down in the current state</returns>
        public bool wasKeyHeld(Keys key)
        {
            return LastState.IsKeyDown(key) && _currentState.IsKeyDown(key);
        }
    }
}
