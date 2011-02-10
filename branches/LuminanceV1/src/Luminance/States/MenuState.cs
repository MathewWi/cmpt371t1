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
using IlluminatrixEngine.Framework.Cameras;
using Microsoft.Xna.Framework;
using IlluminatrixEngine.Framework;
using Microsoft.Xna.Framework.Graphics;
using IlluminatrixEngine.Framework.Input;
using Microsoft.Xna.Framework.Input;
using IlluminatrixEngine.Framework.Effects;

using IlluminatrixEngine.Framework.Menus;
using IlluminatrixEngine.Framework.Resources;
using IlluminatrixEngine.Framework.Utils;
using System.Threading;

namespace Luminance.States
{
    class MenuState : BasicState
    {
        private MenuWindow _menuWindow;
        private KeyboardInput _keyb;

        public override Camera CurrentCamera
        {
            get { throw new NotImplementedException(); }
        }


        /// <summary>
        /// 
        /// </summary>
        public MenuState()
        {
            Resources.addTexture("menu.background", "menuBackground");
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="o"></param>
        /// <param name="args"></param>
        /// <returns></returns>
        public bool menuSelectStart(Object o, MenuEventArgs args)
        {
            Resources.getSfx("sfx.select").Play();
            Engine.switchToState("State.Game");
            ((GameState)Engine.CurrentState).EditMode = false;
            ((GameState)Engine.CurrentState).RandomMaps(false);
            return true;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="o"></param>
        /// <param name="args"></param>
        /// <returns></returns>
        public bool menuSelectRandom(Object o, MenuEventArgs args)
        {
            Resources.getSfx("sfx.select").Play();
            Engine.switchToState("State.Game");
            ((GameState)Engine.CurrentState).EditMode = false;
            ((GameState)Engine.CurrentState).RandomMaps(true);
            
            return true;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="o"></param>
        /// <param name="args"></param>
        /// <returns></returns>
        public bool menuSelectEditor(Object o, MenuEventArgs args)
        {
            Resources.getSfx("sfx.select").Play();
            Engine.switchToState("State.Game");
            ((GameState)Engine.CurrentState).EditMode = true;
            return true;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="o"></param>
        /// <param name="args"></param>
        /// <returns></returns>
        public bool menuSelectQuit(Object o, MenuEventArgs args)
        {
            Resources.getSfx("sfx.select").Play();
            Engine.shutdownEngine(false);

            return true;
        }


        /// <summary>
        /// 
        /// </summary>
        public override void init()
        {
            // init the kyabord
            _keyb = new KeyboardInput();
            _keyb.addInputDownEvent(this, Keys.Escape, new KeyboardInput.InputEvent(keyboardEscape));

            // create and init the menu window
            _menuWindow = new MenuWindow(this);
            addWindow(0, _menuWindow);

            // add the main menu category
            MenuCategory mainMenu = new MenuCategory(_menuWindow, "Luminance");
            _menuWindow.addCategory(mainMenu);

            // add some entries to the main menu
            MenuEntry startGame = new MenuEntry(_menuWindow, "Start Game", null);
            MenuEntry randomMode = new MenuEntry(_menuWindow, "Random Levels", null);
            MenuEntry levelEditor = new MenuEntry(_menuWindow, "Level Editor", null);           
            MenuEntry quitGame = new MenuEntry(_menuWindow, "Quit", null);
            _menuWindow.addEntryToCategory(mainMenu, startGame, new MenuEvent(menuSelectStart), null);
            _menuWindow.addEntryToCategory(mainMenu, randomMode, new MenuEvent(menuSelectRandom), null);
            _menuWindow.addEntryToCategory(mainMenu, levelEditor, new MenuEvent(menuSelectEditor), null);
            _menuWindow.addEntryToCategory(mainMenu, quitGame, new MenuEvent(menuSelectQuit), null);
        }

        public override void destroy()
        {
            
        }


        public override void resume()
        {
            Resources.playSong("music.menu");
        }

        public override void suspend()
        {

        }

        public override void update()
        {
            // check for input here
            _keyb.update();

            // check for fullscreen switch
            if ((_keyb.wasKeyHeld(Keys.RightAlt) || _keyb.wasKeyHeld(Keys.LeftAlt)) && _keyb.wasKeyPressed(Keys.Enter))
            {
                Engine.setFullScreen(Engine.GraphicsDevice.DisplayMode.Width, Engine.GraphicsDevice.DisplayMode.Height);
                return;
            }

            _keyb.processInput(this);

            // update each window
            foreach (BasicWindow w in _windows)
            {
                // update the window
                w.update();
            }            
        }

        public override void draw()
        {
            //clear to black
            Engine.GraphicsDevice.Clear(Color.Black);
#if DEBUG
            Engine.drawFramerate();
#endif

            // draw the sexy background
            Engine.SpriteBatch.Begin();
            Engine.SpriteBatch.Draw(Resources.getTexture("menu.background"), new Rectangle(0, 0, Engine.GraphicsDevice.Viewport.Width, Engine.GraphicsDevice.Viewport.Height), Color.White);
            Engine.SpriteBatch.End();

            // if the window count is above 0, lets render them
            if (Windows.Count > 0)
            {
                // loop each window in the z-order
                foreach (BasicWindow win in Windows)
                {
                    win.draw();
                }
            }
        }


        /// <summary>
        /// Quit the game
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// <returns></returns>
        public bool keyboardEscape(object sender, InputArgs<Keys, KeyboardState> e)
        {
            Engine.shutdownEngine(false);

            return true;
        }

    }
}
