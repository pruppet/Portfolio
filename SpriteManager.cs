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

namespace ticTacToe
{
    /*
     * Author: Maggie Laidlaw
     * Date: March 29, 2012
     * 
     * Manages most content for a tic tac toe game.
     * */
    class SpriteManager : Microsoft.Xna.Framework.DrawableGameComponent
    {
        SpriteBatch spriteBatch;

        //mouse variables
        Texture2D mouseTexture;
        Vector2 mousePosition;

        //initialize the board pieces of the board
        public static BoardPiece piece1a;
        public static BoardPiece piece1b;
        public static BoardPiece piece1c;

        public static BoardPiece piece2a;
        public static BoardPiece piece2b;
        public static BoardPiece piece2c;

        public static BoardPiece piece3a;
        public static BoardPiece piece3b;
        public static BoardPiece piece3c;

        public static char turn = 'x';//x always starts
        public static char player1 = 'h';//h = human, c = computer. 
        public static char player2 = 'h';//h = human, c = computer. 
        //public static Boolean signal = false;

        //signals if the game is won
        public static Boolean winState = false;
        //sets winner
        public int winner = 0;

        //font variable
        SpriteFont winFont;

        public SpriteManager(Game game)
            : base(game)
        {
        }

        public override void Initialize()
        {
            base.Initialize();
        }
        
        //Load all the content for the game.
        protected override void LoadContent()
        {
            spriteBatch = new SpriteBatch(Game.GraphicsDevice);

            //Loads cursor
            mousePosition = Vector2.Zero;
            mouseTexture = Game.Content.Load<Texture2D>("Images/cursor");

            //Initialise board pieces
            piece1a = new BoardPiece(
                Game.Content.Load<Texture2D>(@"Images/o"), Game.Content.Load<Texture2D>(@"Images/x"),
                new Vector2(5, 5), 290, 290);

            piece1b = new BoardPiece(
                Game.Content.Load<Texture2D>(@"Images/o"), Game.Content.Load<Texture2D>(@"Images/x"),
                new Vector2(302, 5), 290, 290);

            piece1c = new BoardPiece(
                Game.Content.Load<Texture2D>(@"Images/o"), Game.Content.Load<Texture2D>(@"Images/x"),
                new Vector2(604, 5), 290, 290);

            piece2a = new BoardPiece(
                Game.Content.Load<Texture2D>(@"Images/o"), Game.Content.Load<Texture2D>(@"Images/x"),
                new Vector2(5, 302), 290, 290);

            piece2b = new BoardPiece(
                Game.Content.Load<Texture2D>(@"Images/o"), Game.Content.Load<Texture2D>(@"Images/x"),
                new Vector2(302, 302), 290, 290);

            piece2c = new BoardPiece(
                Game.Content.Load<Texture2D>(@"Images/o"), Game.Content.Load<Texture2D>(@"Images/x"),
                new Vector2(604, 302), 290, 290);

            piece3a = new BoardPiece(
                Game.Content.Load<Texture2D>(@"Images/o"), Game.Content.Load<Texture2D>(@"Images/x"),
                new Vector2(5, 604), 290, 290);

            piece3b = new BoardPiece(
                Game.Content.Load<Texture2D>(@"Images/o"), Game.Content.Load<Texture2D>(@"Images/x"),
                new Vector2(302, 604), 290, 290);

            piece3c = new BoardPiece(
                Game.Content.Load<Texture2D>(@"Images/o"), Game.Content.Load<Texture2D>(@"Images/x"),
                new Vector2(604, 604), 290, 290);

            //Load font.
            winFont = Game.Content.Load<SpriteFont>(@"Fonts\winFont");

            base.LoadContent();
        }

        //Draw items
        public override void Draw(GameTime gameTime)
        {
            spriteBatch.Begin(SpriteSortMode.BackToFront, BlendState.AlphaBlend);

            //Display the cursor
            spriteBatch.Draw(mouseTexture, mousePosition, null, Color.White, 0f,
                Vector2.Zero, 1.0f, SpriteEffects.None, 0f);

            //Write the winner 
            if (SpriteManager.winState)
            {
                spriteBatch.DrawString(winFont, "Player "+winner+" wins.",
                    new Vector2(10, 10), Color.Red, 0, Vector2.Zero, 1,
                    SpriteEffects.None, 0.1f);
            }

            //Draw the board pieces
            piece1a.Draw(gameTime, spriteBatch);
            piece1b.Draw(gameTime, spriteBatch);
            piece1c.Draw(gameTime, spriteBatch);

            piece2a.Draw(gameTime, spriteBatch);
            piece2b.Draw(gameTime, spriteBatch);
            piece2c.Draw(gameTime, spriteBatch);

            piece3a.Draw(gameTime, spriteBatch);
            piece3b.Draw(gameTime, spriteBatch);
            piece3c.Draw(gameTime, spriteBatch);

            spriteBatch.End();

            base.Draw(gameTime);
        }

        //Update game.
        public override void Update(GameTime gameTime)
        {
            //Update cursor
            MouseState mouseState = Mouse.GetState();
            mousePosition.X = mouseState.X;
            mousePosition.Y = mouseState.Y;

            //If game is not won, keep tracking moves and if the game is won.
            if (!winState)
            {
                checkMove(gameTime);

                checkWin(piece1a.state, piece1b.state, piece1c.state,
                    piece2a.state, piece2b.state, piece2c.state,
                    piece3a.state, piece3b.state, piece3c.state);
            }

            base.Update(gameTime);
        }

        //Update the board piece only if is not clicked
        public void checkMove(GameTime gameTime)
        {
                if (!piece1a.clicked)
                    piece1a.Update(gameTime);

                if (!piece1b.clicked)
                    piece1b.Update(gameTime);

                if (!piece1c.clicked)
                    piece1c.Update(gameTime);

                if (!piece2a.clicked)
                    piece2a.Update(gameTime);

                if (!piece2b.clicked)
                    piece2b.Update(gameTime);

                if (!piece2c.clicked)
                    piece2c.Update(gameTime);

                if (!piece3a.clicked)
                    piece3a.Update(gameTime);

                if (!piece3b.clicked)
                    piece3b.Update(gameTime);

                if (!piece3c.clicked)
                    piece3c.Update(gameTime);
        }

        //checks if there is winner.
        public void checkWin(char a1, char b1, char c1, 
            char a2, char b2, char c2, char a3, char b3, char c3)
        {
            isSame(a1, b1, c1);
            isSame(a2, b2, c2);
            isSame(a3, b3, c3);

            isSame(a1, a2, a3);
            isSame(b1, b2, b3);
            isSame(c1, c2, c3);
            
            isSame(a1, b2, c3);
            isSame(a3, b2, c1);
        }

        //Checks if there is a winning move made.
        //If yes, make win state true and set winner.
        public void isSame(char a, char b, char c)
        {
            if (a != ' ' && b != ' ' && c != ' ')
            {
                if (a == b && b == c && a == c)
                {
                    if (a == 'x')
                    {
                        winner = 1;
                        winState = true;
                    }
                    else if (a == 'o')
                    {
                        winner = 2;
                        winState = true;
                    }

                }
            }
        }       
    }
}
