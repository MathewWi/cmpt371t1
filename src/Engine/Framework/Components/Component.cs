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

namespace IlluminatrixEngine.Framework.Components
{
    /// <summary>
    /// Anything intending to be updated/drawn by the Engine's current state
    /// must be a component.
    /// </summary>
    public abstract class Component
    {
        /// <summary>
        /// Active means recieves updates
        /// </summary>
        private bool _isActive;
        public bool IsActive
        {
            get { return _isActive; }
            set { _isActive = value; }
        }


        /// <summary>
        /// Visible means recieves rendering
        /// </summary>
        private bool _isVisible;
        public bool IsVisible
        {
            get { return _isVisible; }
            set { _isVisible = value; }
        }

        /// <summary>
        /// Owner of this component
        /// Only windows can be owners of components.
        /// </summary>
        protected BasicWindow _owner;
        public BasicWindow Owner
        {
            get { return _owner; }
        }


        /// <summary>
        /// Enforced ALL units to be constructed with a owner of type window
        /// </summary>
        /// <param name="o"></param>
        public Component(BasicWindow o)
        {
            if (o == null)
            {
                throw new Exception("Component cannot have a null owner!");
            }

            _owner = o;

            _isActive = true;
            _isVisible = true;
        }

        /// <summary>
        /// Called once to initialize the component
        /// </summary>
        public abstract void init();

        /// <summary>
        /// Called when the game destroys the component
        /// This is not necessarily when it is destroyed in memory for example.
        /// </summary>
        public abstract void destroy();

        /// <summary>
        /// Called every update cycle by the engine iff 
        /// the component is active, in the current state.
        /// </summary>
        public abstract void update();

        /// <summary>
        /// Called every draw cycle by the engine iff
        /// the component active, in the current state, visible,
        /// in the frustrum.
        /// </summary>
        public abstract void draw();


        /// <summary>
        /// Abstract function, must be overridden to tell the engine
        /// What type of object you are using.
        /// </summary>
        /// <returns></returns>
        public abstract ComponentType GetComponentType();
    }
}
