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
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Media;

namespace IlluminatrixEngine.Framework.Resources
{
    public static class Resources
    {
        private static Dictionary<String, Texture2D> _texture2D;
        private static Dictionary<String, Model> _models;
        private static Dictionary<String, SoundEffect> _sfx;
        private static Dictionary<String, Song> _songs;
        private static Song _curSong;

        static Resources()
        {
            _texture2D = new Dictionary<string, Texture2D>();
            _models = new Dictionary<string, Model>();
            _sfx = new Dictionary<string, SoundEffect>();
            _songs = new Dictionary<string, Song>();
        }

        public static void addTexture(String key, String contentName)
        {
            _texture2D.Add(key, Engine.GameOwner.Content.Load<Texture2D>(contentName));
        }

        public static Texture2D getTexture(String key)
        {
            return _texture2D[key];
        }


        public static void addModel(String key, String contentName)
        {
            _models.Add(key, Engine.GameOwner.Content.Load<Model>(contentName));
        }

        public static Model getModel(String key)
        {
            return _models[key];
        }


        public static void addSfx(String key, String contentName)
        {
            _sfx.Add(key, Engine.GameOwner.Content.Load<SoundEffect>(contentName));
        }

        public static SoundEffect getSfx(String key)
        {
            return _sfx[key];
        }


        public static void addSong(String key, String contentName)
        {
            _songs.Add(key, Engine.GameOwner.Content.Load<Song>(contentName));
        }

        public static void playSong(String key)
        {
            if (_curSong != null)
            {
                MediaPlayer.Stop();
            }

            _curSong = _songs[key];
            MediaPlayer.Play(_curSong);
            MediaPlayer.IsRepeating = true;
        }
    }
}
