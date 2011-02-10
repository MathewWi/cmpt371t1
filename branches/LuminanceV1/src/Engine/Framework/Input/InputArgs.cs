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

namespace IlluminatrixEngine.Framework.Input
{

    /// <summary>
    /// This class is written to be used as arguments for the input event handlers.
    /// It is used so wherever the event handler is, it can deal with all aspects of input.
    /// Includes references to the input class, the input button which caused it and the state of the input device.
    /// </summary>
    /// <typeparam name="InputEnum"></typeparam>
    /// <typeparam name="InputState"></typeparam>
    public class InputArgs<InputEnum, InputState> : EventArgs
    {
        /// <summary>
        /// Input device which owns the state.
        /// </summary>
        public InputDevice<InputEnum, InputState> owner;
        

        /// <summary>
        /// The button which tr
        /// </summary>
        public InputEnum caller;


        /// <summary>
        /// Store the current state of the input device when the event was triggered.
        /// </summary>
        public InputState currentState;

        /// <summary>
        /// Store the current state of the input device when the event was triggered.
        /// </summary>
        public InputState lastState;

        
        /// <summary>
        /// Basic constructor required to use this as args.
        /// </summary>
        /// <param name="obj">The object which caused this call.</param>
        /// <param name="device">The input device to get the state from.</param>
        public InputArgs(InputEnum obj, InputDevice<InputEnum, InputState> device)
        {
            this.caller = obj;
            this.owner = device;
            this.currentState = device.CurrentState;
            this.lastState = device.LastState;
        }

    }

}
