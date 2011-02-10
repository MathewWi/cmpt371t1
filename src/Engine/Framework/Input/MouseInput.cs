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
using IlluminatrixEngine.Framework.Utils;

namespace IlluminatrixEngine.Framework.Input
{
    // Enum of mouse buttons... Yes XNA does not provide one
    public enum MouseButtons { Left, Right, Middle, X1, X2, ScrollWheelValue };

    /// <summary>
    /// Class used to handle mouse input.
    /// Some of the code for handling mouse input in an OO way 
    /// was taken from the innovation engine.  This is only done because
    /// Microsoft provides no easy way to deal with the mouse like it does
    /// the keyboard.  
    /// </summary>
    public class MouseInput : InputDevice<MouseButtons, MouseState>
    {
        /// <summary>
        /// Dictionary for mouse scroll wheel events
        /// </summary>
        private Dictionary<Object, InputEvent> _eventScrollWheel;


        /// <summary>
        /// Dictionary for movement events by owner
        /// </summary>
        private Dictionary<Object, InputEvent> _eventMouseMovement;


        /// <summary>
        /// Public Constructor
        /// </summary>
        public MouseInput()
        {
            Mouse.WindowHandle = Engine.GameOwner.Window.Handle;

            // init the scroll wheel event dictionary
            _eventScrollWheel = new Dictionary<object, InputDevice<MouseButtons, MouseState>.InputEvent>();

            // init the movement event dictionary
            _eventMouseMovement = new Dictionary<object, InputDevice<MouseButtons, MouseState>.InputEvent>();

            // process input now so we get some basic states
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
            _currentState = Mouse.GetState(); 
        }


        /// <summary>
        /// Perform a basic update of the keyboard
        /// A null owner will fire no events, but update input.
        /// </summary>
        public override void processInput(Object owner)
        {
            if (owner != null)
            {
                // loop through each key in the dictionary
                foreach (MouseButtons button in EnumHelper.GetEnumValues<MouseButtons>())
                {
                    // process button pressed events
                    if (WasButtonPressed(button))
                    {
                        if (_eventDown.ContainsKey(owner) && _eventDown[owner].ContainsKey(button))
                        {
                            _eventDown[owner][button](this, new InputArgs<MouseButtons, MouseState>(button, this));
                        }
                    }

                    // process button up events
                    if (WasButtonReleased(button))
                    {
                        if (_eventUp.ContainsKey(owner) && _eventUp[owner].ContainsKey(button))
                        {
                            _eventUp[owner][button](this, new InputArgs<MouseButtons, MouseState>(button, this));
                        }
                    }

                    // process button held events
                    if (WasButtonHeld(button))
                    {
                        if (_eventHeld.ContainsKey(owner) && _eventHeld[owner].ContainsKey(button))
                        {
                            _eventHeld[owner][button](this, new InputArgs<MouseButtons, MouseState>(button, this));
                        }
                    }
                }

                // now check for mouse wheel scroll event
                if (_currentState.ScrollWheelValue != _lastState.ScrollWheelValue)
                {
                    if (_eventScrollWheel.ContainsKey(owner))
                    {
                        _eventScrollWheel[owner](this, new InputArgs<MouseButtons, MouseState>(MouseButtons.ScrollWheelValue, this));
                    }
                }

                // finally check for movement and fire the movement handler
                // fire the event if either the X's or the Y's are different.
                if (_lastState.X != _currentState.X || _lastState.Y != _currentState.Y)
                {
                    if (_eventMouseMovement.ContainsKey(owner))
                    {
                        _eventMouseMovement[owner](this, new InputArgs<MouseButtons, MouseState>(MouseButtons.Left, this));
                    }
                }
            } //end if(owner!=null)
        } //end function (processInput())


        /// <summary>
        /// Sets the mouse position
        /// </summary>
        /// <param name="x"></param>
        /// <param name="y"></param>
        public void setCursorPosition(int x, int y)
        {
            Mouse.SetPosition(x, y);

            // TODO MAYBE UPDATE STATES
        }


        /// <summary>
        /// Adds a movement event to the mouse
        /// </summary>
        /// <param name="owner">The owner of this movement event.</param>
        /// <param name="e">The event to bind to it.</param>
        public void addMovementEvent(Object owner, InputEvent e )
        {
            if (owner == null)
            {
                throw new Exception("cannot have a null owner in input");
            }

            _eventMouseMovement.Add(owner, e);
        }


        /// <summary>
        /// Adds a movement event to the mouse
        /// </summary>
        /// <param name="owner">The owner of this movement event.</param>
        /// <param name="e">The event to bind to it.</param>
        public void addScrollEvent(Object owner, InputEvent e)
        {
            if (owner == null)
            {
                throw new Exception("cannot have a null owner in input");
            }

            _eventScrollWheel.Add(owner, e);
        }


        /// <summary>
        /// Check if a mouse button is down in current state.
        /// </summary>
        /// <param name="Button">The button from our MouseButtons enum</param>
        /// <returns>True if the button is down.</returns>
        public bool IsButtonDown(MouseButtons Button)
        {
            return IsButtonDown(Button, _currentState);
        }


        /// <summary>
        /// Internal helper for determining if the button is down.
        /// </summary>
        /// <param name="Button">The button to check for.</param>
        /// <param name="State">The state to check in.</param>
        /// <returns></returns>
        bool IsButtonDown(MouseButtons Button, MouseState State)
        {
            return GetButtonState(Button, State) == ButtonState.Pressed;
        }


        /// <summary>
        /// Check if a mouse button is up in current state.
        /// </summary>
        /// <param name="Button">The button from our MouseButtons enum</param>
        /// <returns>True if the button is down.</returns>
        public bool IsButtonUp(MouseButtons Button)
        {
            return IsButtonUp(Button, _currentState);
        }


        /// <summary>
        /// Internal helper for determining if the button is up.
        /// </summary>
        /// <param name="Button">The button to check for.</param>
        /// <param name="State">The state to check in.</param>
        /// <returns></returns>
        bool IsButtonUp(MouseButtons Button, MouseState State)
        {
            return GetButtonState(Button, State) == ButtonState.Released;
        }


        /// <summary>
        /// Check if a button was pressed recently.
        /// </summary>
        /// <param name="Button">The button to check.</param>
        /// <returns>True iff this button was up last state and down this state.</returns>
        public bool WasButtonPressed(MouseButtons Button)
        {
            return IsButtonUp(Button, _lastState) && IsButtonDown(Button, _currentState);
        }


        /// <summary>
        /// Check if a button was released recently.
        /// </summary>
        /// <param name="Button">The button to check.</param>
        /// <returns>True iff this button was down last state and up this state.</returns>
        public bool WasButtonReleased(MouseButtons Button)
        {
            return IsButtonDown(Button, _lastState) && IsButtonUp(Button, _currentState);
        }


        /// <summary>
        /// Check if a button is being held
        /// </summary>
        /// <param name="Button">The button to check.</param>
        /// <returns>True iff this button was down last state and down this state.</returns>
        public bool WasButtonHeld(MouseButtons Button)
        {
            return IsButtonDown(Button, _lastState) && IsButtonDown(Button, _currentState);
        }


        /// <summary>
        /// Another helper function because there is no Mouse enum from microsoft.
        /// </summary>
        /// <param name="Button">The button to check for.</param>
        /// <param name="State">The state to check in.</param>
        /// <returns>The button state of the button in the state specified.</returns>
        ButtonState GetButtonState(MouseButtons Button, MouseState State)
        {
            if (Button == MouseButtons.Left)
                return State.LeftButton;
            else if (Button == MouseButtons.Middle)
                return State.MiddleButton;
            else if (Button == MouseButtons.Right)
                return State.RightButton;
            else if (Button == MouseButtons.X1)
                return State.XButton1;
            else if (Button == MouseButtons.X2)
                return State.XButton2;

            return ButtonState.Released;
        }

    }
}
