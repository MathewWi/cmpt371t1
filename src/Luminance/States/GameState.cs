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

using IlluminatrixEngine.Framework.Input;
using IlluminatrixEngine.Framework.States;
using IlluminatrixEngine.Framework.Cameras;
using IlluminatrixEngine.Framework.Components;
using Microsoft.Xna.Framework;
using IlluminatrixEngine.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Luminance.Map;
using IlluminatrixEngine.Framework.Effects;
using System.Threading;
using IlluminatrixEngine.Framework.Utils;
using IlluminatrixEngine.Framework.Resources;

namespace Luminance.States
{
    class GameState : BasicState
    {
        /// <summary>
        /// The KeyboardInput device for this state
        /// </summary>
        KeyboardInput keyb;

        /// <summary>
        /// Mouse Input device
        /// </summary>
        MouseInput mouse;


        /// <summary>
        /// Camera
        /// </summary>
        private Camera _gameCam;


        /// <summary>
        /// Matrix for controlling the location of rendered polygons
        /// </summary>
        //private Matrix world;
        private Vector2 _oldMousePos;
        private ScreenPopup _popup;

        public const float CAMERA_SPEED = 10.0f;
        public const String PAUSED_TEXT = "PAUSED\n\n     Enter: Unpause\n     Q\\Escape: Quit\n     R: Retry\n     M: Menu";
        public const String VICTORY_TEXT = "LEVEL COMPLETED IN $1\n\n     N: Next Level\n     Q: Quit\n     R: Retry\n     M: Menu";
        public const String COMPLETION_TEXT = "GAME COMPLETED\n\n     N: Next Level (Random Mode)\n     Q: Quit\n     R: Retry\n     M: Menu";
        public const String HELP_TEXT = "HELP\n     -Left Click : Place tool in grid.\n     -Mouse Wheel : Rotate current tool.\n     -W/S : Move camera in direction it is facing\n     -A/D : Strafe camera\n     -Right click : Turn and rotate camera\n     -Space : Reset camera position\n\n     Mirror Tool : Reflects Light\n     Prism Tool : Splits light into primaries.\n\n     F1\\Enter: Close Help\n     Q\\Escape: Quit\n     R: Retry\n     M: Menu";
        public const String WELCOME_TEXT = "WELCOME TO LUMINANCE!\n\nGuide the light to each orb.\n-Press F1 for help\n\nLEFT CLICK TO BEGIN!";
        
        /// <summary>
        /// 
        /// </summary>
        private Toolbelt _toolbelt;
        private Dictionary<ToolType, int> storedToolbelt = new Dictionary<ToolType, int>();

        private bool _editmode;       
        public bool EditMode
        {
            get { return _editmode; }
            set
            {
                // Ignore non-change
                if (_editmode == value) return;

                if (_editmode) // We're switching from edit
                {
                    _toolbelt.Belt.Clear();
                    foreach (KeyValuePair<ToolType, int> set in storedToolbelt)
                    {
                        _toolbelt.addTool(set.Key, set.Value);
                    }
                    storedToolbelt.Clear();
                }
                else // We're switching into edit
                {
                    foreach (KeyValuePair<ToolType, int> set in _toolbelt.Belt)
                    {
                        storedToolbelt.Add(set.Key, set.Value);
                    }
                    _toolbelt.Belt.Clear();

                    _toolbelt.addTool(ToolType.Block, Int16.MaxValue);
                    _toolbelt.addTool(ToolType.Emitter, Int16.MaxValue);
                    _toolbelt.addTool(ToolType.Goal, Int16.MaxValue);
                    _toolbelt.addTool(ToolType.Eraser, Int16.MaxValue);
                }
                _editmode = value;
            }
        }

        private LuminanceWindow _lumWin;
        private HUDWindow _hudWin;

        /// <summary>
        /// Allow others to get at this states current camera
        /// </summary>
        public override Camera CurrentCamera
        {
            get
            {
                return _gameCam;
            }
        }

        private bool _bFirstLoad = true;

        /// <summary>
        /// 
        /// </summary>
        /// <param name="b"></param>
        public void RandomMaps(bool b)
        {
            _lumWin.RandomMode = b;
            _lumWin.init(); // we don't want reload to be true...
        }
        
        /// <summary>
        /// One time initialization of the game state!
        /// </summary>
        public override void init()
        {
            // initialize the keyboard, bind some events to it.
            keyb = new KeyboardInput();

            // init the mouse plus add some example events
            mouse = new MouseInput();
            
            // init the camera
            _gameCam = new Camera();
            _gameCam.setUp(0, 1, 0);
         
            // init edit mode to false
            _editmode = false;

            // Create a new perspective projection matrix
            _projection = Matrix.CreatePerspectiveFieldOfView(
              MathHelper.ToRadians(45),
              (float)Engine.GraphicsDevice.Viewport.Width / (float)Engine.GraphicsDevice.Viewport.Height,
              1.0f, 4000.0f
            );

            // create the luminance window
            _lumWin = new LuminanceWindow(this, mouse, @"..\Levels\levelList.txt");

            // init the toolbelt
            _toolbelt = new Toolbelt(_lumWin);
            _toolbelt.addTool(ToolType.Mirror, 3);
            _toolbelt.addTool(ToolType.Prism, 1);
            _toolbelt.addTool(ToolType.Eraser, int.MaxValue);
            _lumWin.setToolBelt(_toolbelt);

            // create the HUD
            _hudWin = new HUDWindow(this, mouse, _toolbelt);
            mouse.addInputDownEvent(_hudWin, MouseButtons.Left, new MouseInput.InputEvent(_hudWin.mouseLeftClick));

            // add the game window
            addWindow(0, _hudWin);
            addWindow(1, _lumWin);
            Engine.makeMouseVisible(true);
        }


        /// <summary>
        /// One time destroy call to this state.
        /// Assumed unusable after this call has been made.
        /// </summary>
        public override void destroy()
        {

        }


        /// <summary>
        /// Called from the engine.
        /// This resumes the state from being frozen.
        /// Intended to perform any reinitialization required.
        /// Will do nothing if not in a suspended state.
        /// </summary>
        public override void resume()
        {
            // TODO - ADD A TRANSITION EFFECT
            _lumWin.reloadMap();
            Thread.Sleep(100);
        }


        /// <summary>
        /// Called from the engine.
        /// This puts the state into a frozen state.
        /// Any operations required to make this happen go here.
        /// Extra calls once suspended do nothing.
        /// </summary>
        public override void suspend()
        {
            // TODO - ADD A TRANSITION EFFECT
        }


        /// <summary>
        /// 
        /// </summary>
        public void updateWithPopup()
        {
            _popup.update();

            // popup was closing
            if (_popup.LastState == ScreenEffectState.TransitionOff && _popup.isComplete())
            {
                //hack
                _bFirstLoad = false;
                
                // destroy popup, resumes game.                
                _popup = null;
                return;
            }
            
            if (_bFirstLoad  && _popup.isComplete())
            {
                if (mouse.WasButtonHeld(MouseButtons.Left))
                {
                    _hudWin.resetTimer();
                    _popup.effectOff();
                }
                return;
            }

            if (!_bFirstLoad)
            {
                // check unpause
                if (!_lumWin.isWinnar() && (keyb.wasKeyPressed(Keys.Enter) || keyb.wasKeyPressed(Keys.F1)))
                {
                    Resources.getSfx("sfx.select").Play();
                    _popup.effectOff();
                }

                // restart level
                if (keyb.wasKeyHeld(Keys.R))
                {
                    Resources.getSfx("sfx.select").Play();
                    _lumWin.reloadMap();
                    _popup.effectOff();
                }
                // quit game
                else if (keyb.wasKeyHeld(Keys.Q) || keyb.wasKeyPressed(Keys.Escape))
                {
                    Resources.getSfx("sfx.select").Play();
                    Engine.shutdownEngine(false);
                }
                // menu
                else if (keyb.wasKeyHeld(Keys.M))
                {
                    Resources.getSfx("sfx.select").Play();
                    _popup = null;
                    Engine.switchToState("State.Menu");
                }

                // if winner, then check for next level
                if (_lumWin.isWinnar())
                {
                    if (keyb.wasKeyHeld(Keys.N) || mouse.WasButtonPressed(MouseButtons.Left))
                    {
                        Resources.getSfx("sfx.select").Play();
                        _hudWin.resetTimer();
                        _lumWin.nextLevel();
                        _popup.effectOff();
                    }
                }
            }
            return;
        }


        /// <summary>
        /// Update the input and all the windows.
        /// </summary>
        public override void update()
        {
            // update our inputs NOW for this frame
            keyb.update();
            mouse.update();

            // if popup is up, do not update anything but it
            if (_popup != null)
            {
                updateWithPopup();
                return;
            }

            // first load welcome message
            if (_bFirstLoad)
            {
                _popup = new ScreenPopup(WELCOME_TEXT, 0.75f, 0.75f);
                _popup.effectOn();
            }

            // check for fullscreen switch
            if ((keyb.wasKeyHeld(Keys.RightAlt) || keyb.wasKeyHeld(Keys.LeftAlt)) && keyb.wasKeyPressed(Keys.Enter))
            {
                Engine.setFullScreen(Engine.GraphicsDevice.DisplayMode.Width, Engine.GraphicsDevice.DisplayMode.Height);
                return;
            }

            // check for enter to pause/popup
            if (keyb.wasKeyPressed(Keys.Enter) || keyb.wasKeyPressed(Keys.Escape))
            {
                Resources.getSfx("sfx.select").Play();
                _popup = new ScreenPopup(PAUSED_TEXT, 0.75f, 0.75f);
                _popup.effectOn();
            }

            // check for help popup
            if (keyb.wasKeyHeld(Keys.F1))
            {
                Resources.getSfx("sfx.select").Play();
                _popup = new ScreenPopup(HELP_TEXT, 0.75f, 0.75f);
                _popup.effectOn();
            }

            // check for winner
            if (_lumWin.isWinnar())
            {
                Resources.getSfx("sfx.victory").Play();

                if (_lumWin.CurrentLevel == _lumWin.FinalLevel)
                {
                    _popup = new ScreenPopup(COMPLETION_TEXT, 1.0f, 1.0f);
                }
                else
                {
                    TimeSpan span =_hudWin.timeDelta();
                    String time = "" +
                        span.Hours.ToString("00") + ":" +
                        span.Minutes.ToString("00") + ":" +
                        span.Seconds.ToString("00");
                    
                    _popup = new ScreenPopup(VICTORY_TEXT.Replace("$1", time), 1.0f, 1.0f);
                }
                _popup.effectOn();                
            }

            // check for edit mode toggle
            if ((keyb.wasKeyHeld(Keys.RightControl) || keyb.wasKeyHeld(Keys.LeftControl)) && keyb.wasKeyPressed(Keys.E))
            {
                EditMode = !EditMode;
            }

            // check for camera movement
            Vector3 cameraRotationVector = Vector3.Zero;
            Vector3 cameraMoveVector = Vector3.Zero;

            if (mouse.WasButtonPressed(MouseButtons.Right))
            {
                // snap to center before movement
                _oldMousePos = new Vector2(mouse.CurrentState.X, mouse.CurrentState.Y);
                mouse.setCursorPosition(Engine.GraphicsDevice.Viewport.Width / 2, Engine.GraphicsDevice.Viewport.Height / 2);
            }
            else if (mouse.WasButtonHeld(MouseButtons.Right))
            {                
                cameraRotationVector.X = ((mouse.CurrentState.X - Engine.GraphicsDevice.Viewport.Width / 2) * 0.4f) * Engine.DeltaGameTime;
                cameraRotationVector.Y = ((mouse.CurrentState.Y - Engine.GraphicsDevice.Viewport.Height / 2) * 0.4f) * Engine.DeltaGameTime;

                // re adjust the mouse
                mouse.setCursorPosition(Engine.GraphicsDevice.Viewport.Width / 2, Engine.GraphicsDevice.Viewport.Height / 2);
                Engine.makeMouseVisible(false);
            }
            else if (mouse.WasButtonReleased(MouseButtons.Right))
            {
                mouse.setCursorPosition((int)_oldMousePos.X, (int)_oldMousePos.Y);
                Engine.makeMouseVisible(true);
            }

            float camMoveSpeed = CAMERA_SPEED * (keyb.wasKeyHeld(Keys.LeftShift) || keyb.wasKeyHeld(Keys.RightShift) ? 5 : 1);
            if (keyb.wasKeyHeld(Keys.W))
            {
                cameraMoveVector.Z += camMoveSpeed * Engine.DeltaGameTime;
            }
            if (!(keyb.wasKeyHeld(Keys.LeftControl)||keyb.wasKeyHeld(Keys.RightControl))&&keyb.wasKeyHeld(Keys.S))
            {
                cameraMoveVector.Z -= camMoveSpeed * Engine.DeltaGameTime;
            }
            if (keyb.wasKeyHeld(Keys.A))
            {
                cameraMoveVector.X += camMoveSpeed * Engine.DeltaGameTime;
            }
            if (keyb.wasKeyHeld(Keys.D))
            {
                cameraMoveVector.X -= camMoveSpeed * Engine.DeltaGameTime;
            }
            if (keyb.wasKeyHeld(Keys.Q) || keyb.wasKeyHeld(Keys.Space))
            {
                cameraMoveVector.Y += camMoveSpeed * Engine.DeltaGameTime;
            }
            if (keyb.wasKeyHeld(Keys.E))
            {
                cameraMoveVector.Y -= camMoveSpeed * Engine.DeltaGameTime;
            }
            if (keyb.wasKeyHeld(Keys.Space))
            {
                _lumWin.Map.resetCamera();
            }

            // Use left and right arrows for mirror rotation
            if (keyb.wasKeyReleased(Keys.Right))
                _lumWin.mouseWheelScroll(1, null);
            if (keyb.wasKeyReleased(Keys.Left))
                _lumWin.mouseWheelScroll(-1, null);

            // Save the map with CTRL+S
            if (keyb.wasKeyHeld(Keys.LeftControl) || keyb.wasKeyHeld(Keys.RightControl))
            {
                if (keyb.wasKeyReleased(Keys.S))
                {
                    _lumWin.SaveMap(@"..\Levels\Saved.map");
                }
            }

            // update the camera with the new data
            _gameCam.update(cameraMoveVector, cameraRotationVector);

            // just check for toolbelt, @HACK
            int index = _hudWin.collideWithToolEntry(mouse.CurrentState.X, mouse.CurrentState.Y);
            if (index >= 0)
            {
                mouse.processInput(_hudWin);
            }
            else
            {
                mouse.processInput(_lumWin);
            }

            // we do not have window specific input yet, so all input owners are this state
            keyb.processInput(this);
            mouse.processInput(this);

            keyb.processInput(_lumWin);
            keyb.processInput(_hudWin);

            // update windows
            _lumWin.update();
            _hudWin.update();
        }


        /// <summary>
        /// Draws all the windows
        /// </summary>
        public override void draw()
        {
            //clear to black
            Engine.GraphicsDevice.Clear(Color.Black);

            // if the window count is above 0, lets render them
            if (Windows.Count > 0)
            {
                // loop each window in the z-order
                foreach (BasicWindow win in Windows)
                {
                    win.draw();
                }
            }
            /*if (_lumWin.isWinnar())
            {
                SpriteBatch batch = new SpriteBatch(Engine.GraphicsDevice);
                batch.Begin();
                batch.DrawString(Engine.GameFont, "You Win!\nQ:  Quit\nR:  Restart", new Vector2(Engine.GraphicsDevice.Viewport.Width / 2, Engine.GraphicsDevice.Viewport.Height / 2), Color.Blue);
                batch.End();
            }*/

            // draw the popup
            if (_popup != null)
            {
                _popup.draw();
            }

#if DEBUG
            Engine.drawFramerate();
#endif
        }

    }
}
