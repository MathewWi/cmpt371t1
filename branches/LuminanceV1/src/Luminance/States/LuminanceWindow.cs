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
using System.Diagnostics;

using IlluminatrixEngine.Framework;
using IlluminatrixEngine.Framework.States;
using IlluminatrixEngine.Framework.Physics;
using IlluminatrixEngine.Framework.Components;
using IlluminatrixEngine.Framework.Input;
using IlluminatrixEngine.Framework.Effects;

using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

using Luminance.GameUnits;
using Luminance.Map;
using IlluminatrixEngine.Framework.Utils;
using System.IO;
using System.Threading;
using IlluminatrixEngine.Framework.Resources;

namespace Luminance.States
{
    public class LuminanceWindow : BasicWindow
    {
        /// <summary>
        /// Storing the graphics device for ease
        /// </summary>
        //private GraphicsDevice _graphics;

        /// <summary>
        /// The level this window is displaying
        /// </summary>
        private Level _map;
        public Level Map
        {
            get { return _map; }
        }

        private const int QUEUE_SIZE = 8;
        private Thread _generation_thread;
        private Queue<Level> _level_queue;
        private object _level_queue_lock;
        private bool _call_init, _wait_for_level;
        
        /// <summary>
        /// Tool Belt instance
        /// </summary>
        private Toolbelt _tools;

        private MouseInput _mouse;
        private float currentRotation = 0;

        public const float MAX_ROTATION_PER_SCROLL = (float) Math.PI / 2;

        private Skybox _sky;

        private List<String> _levels;
        private int _currentLevel;
        public int CurrentLevel
        {
            get { return _currentLevel; }
        }
        public int FinalLevel
        {
            get { return _levels.Count - 1; }
        }
        
        public bool RandomMode;
        
        /// <summary>
        /// Public Constructor, with owner param.
        /// THIS WILL MAKE THE MAP FOLLOW A LEVEL PROGRESSION!
        /// </summary>
        /// <param name="s"></param>
        public LuminanceWindow(BasicState s, MouseInput mouse, String levelListFile)
            : base(s)
        {
            _level_queue = new Queue<Level>(QUEUE_SIZE);
            _level_queue_lock = new object();
            _call_init = false;
            _wait_for_level = false;

            // Load any cached level
            foreach (string level in Directory.GetFiles(@"..\Levels\Random", "*.map"))
            {
#if DEBUG
                Console.WriteLine("Loading cache ... " + level);
#endif
                _level_queue.Enqueue(new Level(this, level));
            }

            _generation_thread = new Thread(GenerationThread);
            _generation_thread.Priority = ThreadPriority.Lowest;
            _generation_thread.IsBackground = true;
            _generation_thread.Name = "GenerationThread";
            _generation_thread.Start();
            
            _mouse = mouse;
            mouse.addInputDownEvent(this, MouseButtons.Left, new MouseInput.InputEvent(mouseLeftClickPlaceObject));
            mouse.addScrollEvent(this, new MouseInput.InputEvent(mouseWheelScroll));
            mouse.addMovementEvent(this, new MouseInput.InputEvent(mouseMovement));

            RandomMode = false;

            // load the file list and get all the file names
            _levels = new List<String>();
            String tmpString;
            StreamReader streamReader = new StreamReader(levelListFile);
            while (!streamReader.EndOfStream)
            {
                tmpString = streamReader.ReadLine();
#if DEBUG
                //Console.WriteLine(tmpString);
#endif
                _levels.Add(tmpString);
            }
            streamReader.Close();

            // set the current level
            _currentLevel = 0;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="tools"></param>
        public void setToolBelt(Toolbelt tools)
        {
            _tools = tools;
        }


        /// <summary>
        /// 
        /// </summary>
        public void nextLevel()
        {
            if (!RandomMode)
            {
                _currentLevel++;
                if (_currentLevel >= _levels.Count)
                    RandomMode = true;
            }
            init();
        }


        /// <summary>
        /// Initialize this the main game window
        /// </summary>
        public override void init()
        {
            setupGame(false);
        }

        /// <summary>
        /// Initialize this the main game window
        /// </summary>
        protected void setupGame(bool reload)
        {

            // Nuke all the current entities
            List<BaseEntity> remove = _components.OfType<BaseEntity>().ToList();
            foreach (BaseEntity b in remove)
            {
                _components.Remove(b);
            }

            // fix loading bugs by removeing sky and map from list
            if (_sky != null)
            {
                removeComponent(_sky);
            }

            if (_map != null)
            {
                removeComponent(_map);
            }

            // If called when there isn't a map yet, we'll have to bail
        
            if (RandomMode)
            {
                if (reload)
                {
                    _map = new Level(this, @"..\Levels\lastMap.map");
                }
                else
                {
                    lock (_level_queue_lock)
                    {
                        if (_level_queue.Count == 0)
                        {
#if DEBUG
                            Console.WriteLine("setupGame(): Don't have a level yet, bail!");
#endif
                            _wait_for_level = true;
                            return;
                        }
                        else // take advantage of having the lock
                        {
                            _map = _level_queue.Dequeue();
                            Console.WriteLine("init(): Pulsing ...");
                            Monitor.Pulse(_level_queue_lock);
                        }
                    }
                }
            } else
                _map = new Level(this, @"..\Levels\" + _levels[_currentLevel]);
        
            // store 
            //_graphics = Engine.GraphicsDevice; isn't used?
            _sky = new Skybox(this);
            
            addComponent(_sky);
            addComponent(_map);

            // Set level specific tools
            _tools.dropTool(ToolType.Prism);
            _tools.dropTool(ToolType.Mirror);
            if (_map.MirrorCount > 0) _tools.addTool(ToolType.Mirror, _map.MirrorCount);
            if (_map.PrismCount > 0) _tools.addTool(ToolType.Prism, _map.PrismCount);
         
            // start user on first toolbelt item
            _tools.setSelected(0);
        }

        /// <summary>
        /// Reloads the current map
        /// </summary>
        public void reloadMap()
        {
            setupGame(true);
        }

        /// <summary>
        /// 
        /// </summary>
        public override void destroy()
        {
   
        }

        /// <summary>
        /// Checks to see if the map is detecting a win
        /// </summary>
        public bool isWinnar()
        {
            // Since GameState's update() checks this every time, we need 
            // to return false if we are waiting for a map.
            lock (_level_queue_lock)
            {
                return !_wait_for_level && !_call_init && _map.isWinnar();
            }
        }

        /// <summary>
        /// Update all units in the unit bucket
        /// TODO - Check component flags and types before calling update()
        /// </summary>
        public override void update()
        {
            Vector3 v = _map.findMapIntersection(new Vector3(_mouse.CurrentState.X, _mouse.CurrentState.Y, 0));

            if (_tools.SelectedEntityInstance != null)
            {
                _tools.SelectedEntityInstance.newPosition(v.X, 0, v.Z);
            }

            // draw everything in the component bucket
            foreach (Component c in _components)
            {
                if (c.IsActive)
                {
                    c.update();
                }
            }

            // if we requested a random level, deal with it
            lock (_level_queue_lock)
            {
                if (_call_init)
                {
#if DEBUG
                    Console.WriteLine("Got signaled, calling init.");
#endif
                    // TODO: kill the loading dialog?
                    _call_init = false;
                    init();
                }
            }
        }

        // runs as a thread constantly generating levels
        private void GenerationThread()
        {
            Level current;
            for (; ; )
            {
                // Wait to generate next level
                lock (_level_queue_lock)
                {
                    if (_level_queue.Count >= QUEUE_SIZE)
                    {
                        Console.WriteLine("GenerationThread(): Waiting ...");
                        Monitor.Wait(_level_queue_lock);
                    }
                }

                // Generate a level
                string filename = @"..\Levels\Random\" + DateTime.Now.TimeOfDay.TotalMilliseconds + ".map";
#if DEBUG
                Console.WriteLine("GenerationThread(): Generating map in "+filename);
#endif
                
                MapGenerator.GenerateMap(filename);
                current = new Level(this, filename);

                lock (_level_queue_lock)
                {
                    _level_queue.Enqueue(current);

                    // If other thread is waiting, signal to call init
                    if (_wait_for_level)
                    {
#if DEBUG
                        Console.WriteLine("GenerationThread(): Other thread was waiting, signaling.");
#endif
                        _wait_for_level = false;
                        _call_init = true;
                    }
                }

                // we need to wait a bit since our random filenames are time based
                Thread.Sleep(3);
            }
        }

        /// <summary>
        /// Draw all units in the unit bucket
        /// TODO - Check component flags and types before calling update()
        /// </summary>
        int load = 0, ltime = 45;
        string loading = "Must be poor; I ran out of cache!", dots;
        public override void draw()
        {
            updateBeams();

            if (_wait_for_level || _call_init)
            {
                dots = "";
                if (++load >= ltime*8) load = 0;
                for (int i = 0; i < load/ltime; i++) dots += ".";

                Viewport view = Engine.GraphicsDevice.Viewport;
                ScreenText text = new ScreenText(Engine.GameFont, loading, 1.0f, 1.0f);
                text.Color = Color.White;
                text.Scale = 2.0f;
                text.Position.X = (view.Width / 2) - ((text.GetSize().X / 2) * 2);
                text.Position.Y = view.Height * 0.4f;
                text.draw();

                text = new ScreenText(Engine.GameFont, dots, 1.0f, 1.0f);
                text.Color = Color.White;
                text.Scale = 2.0f;
                text.Position.X = (view.Width / 2) - ((text.GetSize().X / 2) * 2);
                text.Position.Y = view.Height * 0.5f;
                text.draw();
            }

            // draw everything in the component bucket
            foreach (Component c in _components)
            {
                if (c.IsVisible)
                {
                    c.draw();
                }
            }
        }


        /// <summary>
        /// 
        /// </summary>
        public void updateBeams()
        {
            List<BaseEntity> entityList = _components.OfType<BaseEntity>().ToList();
            Emitter em = null;
            foreach (BaseEntity entity in entityList)
            {
                entity.removeAllInputs();
                if (entity is Emitter)
                {
                    em = (Emitter) entity;
                }
            }
            if (em == null) return; // Happens when waiting on level to load
            Queue<BaseEntity> queue = new Queue<BaseEntity>();
            List<BaseEntity> visited = new List<BaseEntity>();
            queue.Enqueue(em);
            while (queue.Count > 0)
            {
                BaseEntity e = queue.Dequeue();
                List<BaseEntity> wavefront = e.tryBeamTo(entityList).ToList();
                foreach (BaseEntity b in wavefront)
                {
                    if (!visited.Contains(b))
                    {
                        queue.Enqueue(b);
                        visited.Add(b);
                    }
                }
            }
        }

        /// <summary>
        /// Saves whatever map is loaded to disk with specified name.
        /// </summary>
        /// <param name="filename"></param>
        public void SaveMap(string filename)
        {
            _map.WriteMap(filename);
        }

        /// <summary>
        /// Place the current tool on the map.  
        /// </summary>
        /// <param name="screenX"></param>
        /// <param name="screenY"></param>
        public void placeCurrentToolOnMap(int screenX, int screenY)
        {
            if (_tools.Selected >= 0)
            {
#if DEBUG
                //Console.WriteLine("Placing map object ...");                            
#endif
                // remove item from the belt
                _tools.placeSelected(_map, screenX, screenY);                
            }
        }

        /// <summary>
        /// Handle placing objects on the map
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// <returns></returns>
        public bool mouseLeftClickPlaceObject(object sender, InputArgs<MouseButtons, MouseState> e)
        {
            //if (_map.isValidPosition(new Vector3(e.currentState.X, e.currentState.Y, 0)))
            if (_tools.SelectedEntityInstance is Eraser)
            {
                BaseEntity ent = _map.clearObject(new Vector3(e.currentState.X, e.currentState.Y, 0));
                if (ent != null)
                {
                    Resources.getSfx("sfx.eraserUsed").Play();
                    _tools.addTool(_tools.getTypeFromInstance(ent), 1);
                }
            }
            else
            {
                placeCurrentToolOnMap(e.currentState.X, e.currentState.Y);
                return true;
            }
            
            return false;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// <returns></returns>
        public bool mouseWheelScroll(object sender, InputArgs<MouseButtons, MouseState> e)
        {
            // Hacked in support for using left and right arrows for rotation.
            // If e is null, then sender will be the deltaScroll value.
            int deltaScroll;
            if (e == null)
            {
                deltaScroll = (int)sender;
            }
            else
            {
                deltaScroll = e.currentState.ScrollWheelValue - e.lastState.ScrollWheelValue;
            }

            if (deltaScroll > 0)
            {
                currentRotation += MAX_ROTATION_PER_SCROLL;
                if (_tools.SelectedEntityInstance != null)
                {
                    _tools.SelectedEntityInstance.RotateAboutY(MAX_ROTATION_PER_SCROLL);
                }
            }
            else
            {
                currentRotation -= MAX_ROTATION_PER_SCROLL;
                if (_tools.SelectedEntityInstance != null)
                {
                    _tools.SelectedEntityInstance.RotateAboutY(-MAX_ROTATION_PER_SCROLL);
                }
            }
            return true;
        }

        /// <summary>
        /// Deal with mouse movement here.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// <returns></returns>
        public bool mouseMovement(object sender, InputArgs<MouseButtons, MouseState> e)
        {
            //make sure mouse is in bounds
            if (e.currentState.X >= 0 && e.currentState.X <= Engine.Screen.ClientBounds.Width &&
                e.currentState.Y >= 0 && e.currentState.Y <= Engine.Screen.ClientBounds.Height)
            {
                // check we have a belt item to snap to mouse && 
                if ( _tools.Selected >= 0 ) {
                    Vector3 v = _map.findMapIntersection(new Vector3(e.currentState.X, e.currentState.Y, 0));
                    _tools.SelectedEntityInstance.newPosition(v);
                }
            }

            // check to see if we are in the map and highlight it
            Point p = _map.getMapPoint(new Vector3(e.currentState.X, e.currentState.Y, 0));
            _map.HighlightedPoint = p;

            return true;
        }
    }
}
