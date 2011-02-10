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
using System.Collections.Specialized;
using System.Linq;
using System.Text;

using Microsoft.Xna.Framework;
using IlluminatrixEngine.Framework.Components;
using IlluminatrixEngine.Framework.Cameras;

namespace IlluminatrixEngine.Framework.States
{

    public abstract class BasicState
    {
        /// <summary>
        /// Getter/Setter for the Windows
        /// </summary>
        protected WindowCollection _windows;
        public WindowCollection Windows
        {
            get { return _windows; } 
        }


        /// <summary>
        /// Getter/Setter for the projection matrix for this state
        /// </summary>
        protected Matrix _projection;
        public Matrix Projection
        {
            get { return _projection; }
        }


        /// <summary>
        /// Abstract getter and setter for the camera.
        /// Every state must be able to get its current camera.
        /// </summary>
        public abstract Camera CurrentCamera { get; }


        /// <summary>
        /// Basic constructore
        /// </summary>
        /// <param name="owner">What game object owns this state</param>
        public BasicState()
        {
            _windows = new WindowCollection();
        }


        /// <summary>
        /// Add a window to the collection
        /// </summary>
        /// <param name="zorder">The z-order to render this window as.  Renders from lowest to highest</param>
        /// <param name="window"></param>
        public void addWindow(int zorder, BasicWindow window)
        {   
            // add the window to the list
            _windows.Add(window);

            // sort the list of windows to keep it z-ordered
            _windows.Sort(new Comparison<BasicWindow>(BasicWindow.comparator));

            // call init on the window as it is now in the engine
            window.init();
        }


        public abstract void init();
        
        
        public abstract void destroy();
        
        
        public abstract void suspend();
        
        
        public abstract void resume();


        /// <summary>
        /// Basic update method used by most all states.
        /// </summary>
        public abstract void update();


        /// <summary>
        /// Basic draw method used by most all states.
        /// </summary>
        public abstract void draw();

        //end class <-- so many { } this was added lol
    }
}
