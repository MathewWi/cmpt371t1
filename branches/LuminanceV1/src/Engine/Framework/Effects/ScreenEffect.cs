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
using Microsoft.Xna.Framework;

namespace IlluminatrixEngine.Framework.Effects
{
    public enum ScreenEffectState
    {
        TransitionOn,
        TransitionOff,
        TransitionLoop,
        TransitionComplete
    }

    public abstract class ScreenEffect
    {
        private TimeSpan _transitionOnTime;
        public TimeSpan TransitionOnTime
        {
            get { return _transitionOnTime; }
            set { _transitionOnTime = value; }
        }


        private TimeSpan _transitionOffTime;
        public TimeSpan TransitionOffTime
        {
            get { return _transitionOffTime; }
            set { _transitionOffTime = value; }
        }


        private float _transitionPosition;
        public float TransitionPosition
        {
            get { return _transitionPosition; }
        }


        private ScreenEffectState _lastState;
        public ScreenEffectState LastState
        {
            get { return _lastState; }
        }

        private ScreenEffectState _state;
        public ScreenEffectState State
        {
            get { return _state; }
        }

        private bool _loop;
        
        /// <summary>
        /// Public constructor
        /// </summary>
        /// <param name="offTime"></param>
        /// <param name="onTime"></param>
        public ScreenEffect(float offTimeInSecond, float onTimeInSecond)
        {
            _transitionOffTime = TimeSpan.FromSeconds(offTimeInSecond);
            _transitionOnTime = TimeSpan.FromSeconds(onTimeInSecond);
            _state = ScreenEffectState.TransitionComplete;
            _loop = false;

        }


        public void effectOn()
        {
            _lastState = _state;
            _state = ScreenEffectState.TransitionOn;
            //_transitionPosition = 0;
        }


        public void effectOff()
        {
            _lastState = _state;
            _state = ScreenEffectState.TransitionOff;
            //_transitionPosition = 1;
        }

        public void setLoop(bool b)
        {
            _loop = b;
        }

        public bool isComplete()
        {
            return _state == ScreenEffectState.TransitionComplete;
        }


        /// <summary>
        /// Base update, used to actually perform the transition position
        /// </summary>
        public virtual void update()
        {
            // we can just shortcut this function 
            if (_state == ScreenEffectState.TransitionComplete)
            {
                return;
            }

            float transitionDelta;
            float direction = (_state == ScreenEffectState.TransitionOn ? 1 : -1);
            TimeSpan time = (_state == ScreenEffectState.TransitionOn ? _transitionOnTime : _transitionOffTime);

            // fetch our delta used to adjust position.
            if (time == TimeSpan.Zero)
                transitionDelta = 1;
            else
                transitionDelta = (float)(Engine.GameTime.ElapsedGameTime.TotalMilliseconds /
                                          time.TotalMilliseconds);

            // Update the transition position.
            _transitionPosition += transitionDelta * direction;

            // Did we reach the end of the transition?
            if (((direction < 0) && (_transitionPosition <= 0)) ||
                ((direction > 0) && (_transitionPosition >= 1)))
            {
                _transitionPosition = MathHelper.Clamp(_transitionPosition, 0, 1);

                // deal with not looping and looping
                if (!_loop)
                {
                    _lastState = _state;
                    _state = ScreenEffectState.TransitionComplete;
                }
                else
                {
                    // flip state
                    if (_state == ScreenEffectState.TransitionOn)
                        this.effectOff();
                    else
                        this.effectOn();
                }
            }
        }

        public abstract void draw();
    }
}
