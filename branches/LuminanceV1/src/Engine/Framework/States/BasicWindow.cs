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

namespace IlluminatrixEngine.Framework.States
{
    /// <summary>
    /// Create a basic window class
    /// Classes are binded to States.
    /// It is a one time bind, Windows cannot be moved between states.
    /// If you are trying to move an active window instance between states,
    /// you are doing something wrong.
    /// </summary>
    public abstract class BasicWindow
    {
        /// <summary>
        /// The State which owns this window.
        /// This should not ever change except when the window is constructed.
        /// ie. There is no way to move a SINGLE window instance to another state.
        /// </summary>
        private BasicState _ownerState;
        public BasicState State
        {
            get { return _ownerState; }
        }


        /// <summary>
        /// List of components owned by this window
        /// </summary>
        protected ComponentCollection _components;


        /// <summary>
        /// Zorder getter and setter
        /// </summary>
        private int _zorder;
        public int Zorder
        {
            get { return _zorder; }
            set { _zorder = value; }
        }

        /// <summary>
        /// Public constructor
        /// </summary>
        /// <param name="s">The owner of this window.</param>
        public BasicWindow(BasicState s)
        {           
            if (s == null)
            {
                throw new Exception("Cannot have a null owner!");
            }
            _ownerState = s;

            _components = new ComponentCollection();
        }


        /// <summary>
        /// Add a component to this windows unit pool
        /// </summary>
        /// <param name="c"></param>
        public void addComponent(Component c)
        {
            _components.Add(c);

            c.init();
        }

        /// <summary>
        /// Remove a component from this windows unit pool
        /// </summary>
        /// <param name="c"></param>
        public void removeComponent(Component c)
        {
            _components.Remove(c);

            if (c != null)
            {
                c.destroy();
            }
        }


        /// <summary>
        /// Sort windows by there z-order in the list.
        /// </summary>
        /// <param name="src"></param>
        /// <param name="trg"></param>
        /// <returns></returns>
        public static int comparator(BasicWindow src, BasicWindow trg)
        {
            if (src.Zorder > trg.Zorder)
                return 1;
            else if (src.Zorder < trg.Zorder)
                return -1;
            else
                return 0;
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
    }
}
