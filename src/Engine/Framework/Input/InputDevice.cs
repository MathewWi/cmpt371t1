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
using IlluminatrixEngine.Framework.Components;

namespace IlluminatrixEngine.Framework.Input
{



    /// <summary>
    /// Basic input device abstract class.
    /// Extending this class
    /// </summary>
    /// <typeparam name="InputEnum"></typeparam>
    /// <typeparam name="InputState"></typeparam>
    public abstract class InputDevice<InputEnum, InputState>
    {
        /// <summary>
        /// This is the input event delegate.
        /// All input event must follow this delegate.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        public delegate bool InputEvent(object sender, InputArgs<InputEnum, InputState> e);

        /// <summary>
        /// Holds the last state of input.
        /// </summary>
        protected InputState _lastState;
        public InputState LastState
        {
            get { return _lastState; }
        }

        /// <summary>
        /// Holds the current state of input
        /// </summary>
        protected InputState _currentState;
        public InputState CurrentState
        {
            get { return _currentState; }
        }

        /// <summary>
        /// Stored list of all keys pressed
        /// </summary>
        protected InputEnum _currentInput;


        /// <summary>
        /// A list of keys which have been binded to events.
        /// This is used to allow a quick search for which events to fire.
        /// </summary>
        protected List<InputEnum> _inputBinded;

        /// <summary>
        /// Used to store a dictionary of events.
        /// The key used is of the enum type used
        /// to enumerate all the devices inputs.
        /// 
        /// The mapping is this:
        ///  dictionary of owners -> dictionary of keys -> events
        /// </summary>
        protected Dictionary<Object, Dictionary<InputEnum, InputEvent>> _eventDown;
        protected Dictionary<Object, Dictionary<InputEnum, InputEvent>> _eventUp;
        protected Dictionary<Object, Dictionary<InputEnum, InputEvent>> _eventHeld;


        /// <summary>
        /// Base Constructor
        /// </summary>
        public InputDevice()
        {
            _eventDown = new Dictionary<object, Dictionary<InputEnum, InputEvent>>();
            _eventUp = new Dictionary<object, Dictionary<InputEnum, InputEvent>>();
            _eventHeld = new Dictionary<object, Dictionary<InputEnum, InputEvent>>();

            _inputBinded = new List<InputEnum>();
        }


        public void Clear()
        {
            _lastState = default(InputState);
            _currentState = default(InputState);
        }

        /// <summary>
        /// Add an event to a key for when it is pressed down
        /// </summary>
        /// <param name="owner">The owner of the event</param>
        /// <param name="key">The key used to fire this event</param>
        /// <param name="e">The event to fire.</param>
        public void addInputDownEvent(Object owner, InputEnum key, InputEvent e)
        {
            _addEvent(owner, key, e, _eventDown);
        }


        /// <summary>
        /// Add an event to a key for when it is pressed down
        /// </summary>
        /// <param name="owner">The owner of the event</param>
        /// <param name="key">The key used to fire this event</param>
        /// <param name="e">The event to fire.</param>
        public void addInputUpEvent(Object owner, InputEnum key, InputEvent e)
        {
            _addEvent(owner, key, e, _eventUp);
        }


        /// <summary>
        /// Add an event to a key for when it is pressed down
        /// </summary>
        /// <param name="owner">The owner of the event</param>
        /// <param name="key">The key used to fire this event</param>
        /// <param name="e">The event to fire.</param>
        public void addInputHeldEvent(Object owner, InputEnum key, InputEvent e)
        {
            _addEvent(owner, key, e, _eventHeld);
        }


        /// <summary>
        /// Private add event functions.  A helper.
        /// </summary>
        /// <param name="owner"></param>
        /// <param name="key"></param>
        /// <param name="e"></param>
        /// <param name="eventDict"></param>
        private void _addEvent(Object owner, InputEnum key, InputEvent e,
                                Dictionary<Object, Dictionary<InputEnum, InputEvent>> eventDict)
        {
            // first time new event owners need a dictionary
            if (!eventDict.ContainsKey(owner))
            {
                eventDict.Add(owner, new Dictionary<InputEnum, InputEvent>());
            }

            // add the event mapped to this key
            eventDict[owner].Add(key, e);

            // add this key to the binded key list
            _inputBinded.Add(key);
        }


        /// <summary>
        /// Shifts the states
        /// </summary>
        public abstract void update();


        /// <summary>
        /// Process the input
        /// </summary>
        /// <param name="owner">Which owner of this input are we processing</param>
        public abstract void processInput(Object owner);
    }
}
