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
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;
using Microsoft.Xna.Framework.Net;
using Microsoft.Xna.Framework.Storage;

using IlluminatrixEngine.Framework;
using IlluminatrixEngine.Framework.States;
using IlluminatrixEngine.Framework.Effects;
using IlluminatrixEngine.Framework.Resources;


namespace IlluminatrixEngine.Framework
{
    /// <summary>
    /// Think of it as the entire cars engine.
    /// The engine is providing an API and services for other components to 
    /// talk to.
    /// </summary>
    public static class Engine
    {
        /// <summary>
        /// The owner for this game object
        /// </summary>
        public static Game GameOwner;


        /// <summary>
        /// 
        /// </summary>
        public static GraphicsDeviceManager GraphicsDeviceManager;


        /// <summary>
        /// The GraphicsDevice the engine is using
        /// </summary>
        public static GraphicsDevice GraphicsDevice;

        /// <summary>
        /// The SpriteBatch for the engine
        /// </summary>
        public static SpriteBatch SpriteBatch;

        /// <summary>
        /// The object holding all the information about the screen sizes.
        /// </summary>
        public static GameWindow Screen;


        /// <summary>
        /// The font to use when writing debug text to the screen.
        /// </summary>
        public static SpriteFont DebugFont;
        public static SpriteFont GameFont;
        public static SpriteFont MenuFont;


        /// <summary>
        /// Object storing our game states
        /// </summary>
        private static Dictionary<String, BasicState> _gameStates;

        /// <summary>
        /// The game will only be officially in one state
        /// Although we can accomodate transitions between states.
        /// </summary>
        private static BasicState _currentState;
        public static BasicState CurrentState
        {
            get { return _currentState; }
        }

        /// <summary>
        /// For transition purposes we will keep track of the old state.
        /// A transition manager can be added to perform the switch.
        /// </summary>
        private static BasicState _lastState;


        /// <summary>
        /// Property BlankTexture
        /// - This is a purely blank white texture
        /// </summary>
        private static Texture2D _blankTexture;
        public static Texture2D BlankTexture
        {
            get { return _blankTexture; }            
        }


        private static ScreenFade _basicFade;
        public static ScreenFade BasicFade
        {
            get { return _basicFade; }
        }


        private static Rectangle _fadeRect;
        private static Color _fadeColor;

        /// <summary>
        /// The gametime as of the last call to update or draw
        /// </summary>
        private static GameTime _gameTime;
        public static GameTime GameTime
        {            
            get { return Engine._gameTime; }
        }


        public static float DeltaGameTime
        {
            get { return (float)_gameTime.ElapsedGameTime.TotalSeconds; }
        }

        /// <summary>
        /// 
        /// </summary>
        public static int StateChangeProgress = 0;


        /// <summary>
        /// 
        /// </summary>
        /// <param name="time"></param>
        public delegate void UpdaterFunction();


        /// <summary>
        /// 
        /// </summary>
        private static UpdaterFunction _updateFunction;


        /// <summary>
        /// 
        /// </summary>
        private static UpdaterFunction _drawFunction;


        /// <summary>
        /// Put the key in and turn the ignition.
        /// In our case the key is the Game to tie to the engine. 
        /// This is required to be called one.
        /// </summary>
        public static void initialize(Game gowner, GraphicsDeviceManager gds, GameWindow window)
        {
            // default updater function
            setDefaultUpdateAndDraw();

            // init engine vars
            Engine.GameOwner = gowner;
            Engine.Screen = window;
            Engine.GraphicsDeviceManager = gds;
            Engine.GraphicsDevice = gds.GraphicsDevice;
            
            // globally used sprite batch, only ever need one!
            Engine.SpriteBatch = new SpriteBatch(gds.GraphicsDevice);

            // load our debug text font, stands out hopefully.
            Resources.Resources.addSfx("sfx.changeEntry", "changeEntry");

            Engine.DebugFont = GameOwner.Content.Load<SpriteFont>("DebugText");
            Engine.GameFont = GameOwner.Content.Load<SpriteFont>("gameFont");
            Engine.MenuFont = GameOwner.Content.Load<SpriteFont>("menuFont");

            // load a blank texture for our entire engine to use when rendering alpha'd sprite batches
            _blankTexture = GameOwner.Content.Load<Texture2D>("blank");
            _basicFade = new ScreenFade(1.25f, 1.25f);

            _fadeColor = new Color(Color.Black, (byte)255);
            _fadeRect = new Rectangle(0, 0,
                                        GraphicsDevice.Viewport.Width, GraphicsDevice.Viewport.Height);
        }


        /// <summary>
        /// 
        /// </summary>
        public static void setDefaultUpdateAndDraw()
        {
            _updateFunction = new UpdaterFunction(Engine.defaultUpdate);
            _drawFunction = new UpdaterFunction(Engine.defaultDraw);
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="time"></param>
        public static void Update(GameTime time)
        {
            _gameTime = time;   
            _updateFunction();
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="time"></param>
        public static void Draw(GameTime time)
        {
            _gameTime = time;
            _drawFunction();
        }


        /// <summary>
        /// 
        /// </summary>
        private static void _shutdown()
        {
            // take down all the states
            foreach (String s in _gameStates.Keys)
            {
                _gameStates[s].destroy();
            }

            // take down the game engine
            Engine.GameOwner.Exit();
        }


        /// <summary>
        /// Puts the engine into the shutdown state, or closes it immediately.
        /// </summary>
        public static void shutdownEngine(bool bNow)
        {
            // put the engine into a shutdown state
            // nice way to shutdown
            _updateFunction = new UpdaterFunction(Engine.shutdownUpdate);
            _drawFunction = new UpdaterFunction(Engine.shutdownDraw);
            Engine.BasicFade.effectOff();

            // instance shutdown
            if (bNow)
            {
                _shutdown();
            }
        }

       
        /// <summary>
        /// 
        /// </summary>
        public static void shutdownUpdate()
        {
            if (Engine.BasicFade.isComplete())
            {
                _shutdown();
            }

            Engine.BasicFade.update();
        }


        /// <summary>
        /// 
        /// </summary>
        public static void shutdownDraw()
        {
            // draw only our current state
            _currentState.draw();

            Engine.BasicFade.draw();
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="time"></param>
        public static void stateChangeUpdate()
        {
            // update fades and states
            _currentState.update();
            Engine.BasicFade.update();

            // _last state has faded out
            if (StateChangeProgress == 0 && Engine.BasicFade.isComplete())
            {
                Engine.BasicFade.effectOn();
                StateChangeProgress = 1;
            }
            else if (StateChangeProgress == 1 && Engine.BasicFade.isComplete())
            {
                setDefaultUpdateAndDraw();
                StateChangeProgress = 0;
            }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="time"></param>
        public static void stateChangeDraw()
        {
            // draw last state, still fading off it
            if (_lastState != null && StateChangeProgress == 0)
            {
                _lastState.draw();
            }
            // draw current state now
            else if (StateChangeProgress == 1)
            {
                _currentState.draw();
            }

            // draw fade
            Engine.BasicFade.draw();
        }


        /// <summary>
        /// The main update method.
        /// This will process all necessary updates to all game components.
        /// </summary>
        /// <param name="gameTime"></param>
        public static void defaultUpdate()
        {
            _currentState.update();
        }


        /// <summary>
        /// The main draw method.
        /// This will render the list of components ready to render.
        /// </summary>
        /// <param name="gameTime"></param>
        public static void defaultDraw()
        {
            _currentState.draw();
        }


        /// <summary>
        /// Add a state based on a name.
        /// This name is a magic string, please define them somewhere
        /// to keep consistency.
        /// </summary>
        /// <param name="state">New state to add</param>
        /// <param name="name">Name to use for this new state</param>
        public static void addState(BasicState state, String name) 
        {
            // initialize the game states array if needed
            if (_gameStates == null)
            {
                _gameStates = new Dictionary<string,BasicState>();
            }

            // init the new state
            state.init();

            // store the new state
            _gameStates.Add(name, state);
        }


        /// <summary>
        /// Switch to a particular state.
        /// This is a hard, stop and switch right now.
        /// @TODO - MAKE SURE THIS PROPERLY SWITCHES STATES
        /// </summary>
        /// <param name="name"></param>
        public static void switchToState(String name)
        {
            if (_gameStates.ContainsKey(name))
            {
                //store last state
                _lastState = _currentState;

                // suspend the last state
                if (_lastState != null)
                {
                    _lastState.suspend();
                }

                // switch to the new state
                _currentState = _gameStates[name];

                // resume the state
                _currentState.resume();

                Engine.BasicFade.effectOff();
                StateChangeProgress = 0;
                _updateFunction = new UpdaterFunction(Engine.stateChangeUpdate);
                _drawFunction = new UpdaterFunction(Engine.stateChangeDraw);
            }
            else
            {
                throw new Exception("Engine.switchToState: Invalid state! - " + name);
            }
        }


        /// <summary>
        /// Basic function to render the current framerate
        /// </summary>
        private static float _FPS = 0f, _TotalTime = 0f, _DisplayFPS = 0f;
        public static void drawFramerate()
        {
            // Calculate the Frames Per Second
            float ElapsedTime = (float)GameTime.ElapsedRealTime.TotalSeconds;
            _TotalTime += ElapsedTime;

            if (_TotalTime >= 1)
            {
                _DisplayFPS = _FPS;
                _FPS = 0;
                _TotalTime = 0;
            }
            _FPS += 1;

            // Format the string appropriately
            string FpsText = _DisplayFPS.ToString() + " FPS";
            Vector2 FPSPos = new Vector2((GraphicsDevice.Viewport.Width - Engine.DebugFont.MeasureString(FpsText).X) - 15, 10);

            Engine.SpriteBatch.Begin();
            SpriteBatch.DrawString(Engine.DebugFont, FpsText, FPSPos, Color.LightGreen);
            Engine.SpriteBatch.End();
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="b"></param>
        public static void makeMouseVisible(Boolean b)
        {
            Engine.GameOwner.IsMouseVisible = b;
        }


        /// <summary>
        /// Draws a giant 2D rectangle over the screen at a particular alpha.
        /// This gives the 
        /// </summary>
        /// <param name="alpha"></param>
        public static void fadeToBlack(int alpha)
        {
            // setup the fade rectangle and color
            Viewport viewport = GraphicsDevice.Viewport;
            _fadeRect.Width = viewport.Width;
            _fadeRect.Height = viewport.Height;
            _fadeColor.A = (byte)alpha;

            // draw the fade
            Engine.SpriteBatch.Begin();
            Engine.SpriteBatch.Draw(Engine.BlankTexture,
                                    _fadeRect,
                                    _fadeColor);
            Engine.SpriteBatch.End();
        }


        /// <summary>
        /// 
        /// </summary>
        public static void setFullScreen(int resX, int resY)
        {
            Engine.GraphicsDeviceManager.PreferredBackBufferWidth = resX;
            Engine.GraphicsDeviceManager.PreferredBackBufferHeight = resY;

            //Engine.GraphicsDeviceManager.IsFullScreen = false;
            Engine.GraphicsDeviceManager.ToggleFullScreen();
        }


    }
}
