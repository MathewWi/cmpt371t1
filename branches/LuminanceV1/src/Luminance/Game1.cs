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
using IlluminatrixEngine.Framework.Cameras;

using Luminance.States;
using IlluminatrixEngine.Framework.Resources;

namespace Luminance
{
    /// <summary>
    /// This is the main game class for the XNA project.
    /// It performs all initialization necessary.
    /// Otherwise it passed off the tasks of updating and rendering to the engine.
    /// </summary>
    public class Game1 : Microsoft.Xna.Framework.Game
    {
        GraphicsDeviceManager graphics;

        public Game1()
        {
            graphics = new GraphicsDeviceManager(this);
            Content.RootDirectory = "Content";
        }

        /// <summary>
        /// Allows the game to perform any initialization it needs to before starting to run.
        /// This is where it can query for any required services and load any non-graphic
        /// related content.  Calling base.Initialize will enumerate through any components
        /// and initialize them as well.
        /// </summary>
        protected override void Initialize()
        {
            // Initialize the engine
            Engine.initialize(this, graphics, Window);

            // load all game resources
            Resources.addTexture("texture.toolbg", "tool_background");
            Resources.addTexture("texture.brickicon", "brickIcon");
            Resources.addTexture("texture.mirroricon", "mirrorIcon");
            Resources.addTexture("texture.prismicon", "prismIcon");
            Resources.addTexture("texture.goalicon", "goalIcon");
            Resources.addTexture("texture.emittericon", "emitterIcon");
            Resources.addTexture("texture.erasericon", "eraserIcon");
            Resources.addSfx("sfx.select", "selectEntry");
            Resources.addSfx("sfx.eraserUsed", "eraserUsed");
            Resources.addSfx("sfx.placeObject", "placeObject");
            Resources.addSfx("sfx.toolBeltSelect", "toolBeltSelect");
            Resources.addSfx("sfx.victory", "victorySound");
            Resources.addSong("music.menu", "menuMusic");

            Resources.addModel("model.block", "Block");
            Resources.addModel("model.emitter", "Emitter");
            Resources.addModel("model.goal", "Goal");
            Resources.addModel("model.mirror", "Mirror");
            Resources.addModel("model.prism", "Prism");

            // Create the states
            Engine.addState(new TitleState(), "State.Title");
            Engine.addState(new MenuState(), "State.Menu");
            Engine.addState(new GameState(), "State.Game");
            Engine.switchToState("State.Menu");
            
            // Always pass along to the base.
            base.Initialize();
        }


        /// <summary>
        /// LoadContent will be called once per game and is the place to load
        /// all of your content.
        /// </summary>
        protected override void LoadContent()
        {
            // We will enumerate a resource handler class
            // A way that we can restore, load and unload resources
        }


        /// <summary>
        /// UnloadContent will be called once per game and is the place to unload
        /// all content.
        /// </summary>
        protected override void UnloadContent()
        {
            // TODO: Unload any non ContentManager content here
        }


        /// <summary>
        /// Allows the game to run logic such as updating the world,
        /// checking for collisions, gathering input, and playing audio.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Update(GameTime gameTime)
        {
            //PUT TEST CODE HERE
            // END TEST CODE

            // Pass directly to the Engine's update
            Engine.Update(gameTime);

            // Always pass along to the base
            base.Update(gameTime);
        }


        /// <summary>
        /// This is called when the game should draw itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Draw(GameTime gameTime)
        {
            //PUT TEST CODE HERE
            //END TEST CODE

            // Pass drawing directly to the engine
            Engine.Draw(gameTime);

            // Always pass along to the base
            base.Draw(gameTime);
        }
    }
}
