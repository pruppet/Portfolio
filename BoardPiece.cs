using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Input;

namespace ticTacToe
{
    /*
     * Author: Maggie Laidlaw
     * Date: March 29, 2012
     * 
     * Class for the board piece sprite for a tic tac toe game. 
     * Handles if the piece is clicked and switches between turns. 
     * */

    class BoardPiece
    {
        //Two options for board piece
        Texture2D textureImageX;
        Texture2D textureImageO;

        //variables for board piece
        protected Vector2 position;
        int width;
        int height;
        public char state = ' ';
        //Signal for if the piece is still playable
        public Boolean clicked = false;
     

        Vector2 mousePosition;

        //constructor for the board piece.
        public BoardPiece(Texture2D tImageO, Texture2D tImageX, Vector2 pos, int w, 
            int h)
        {
            this.textureImageX = tImageX;
            this.textureImageO = tImageO;
            this.position = pos;
            this.width = w;
            this.height = h;
        }

        //Draws the board piece depending on the state of the piece
        public virtual void Draw(GameTime gameTime, SpriteBatch spriteBatch)
        {
            if (state == 'x')
            {
                spriteBatch.Draw(textureImageX, position,
                    new Rectangle(0, 0, width, height),
                    Color.White, 0, Vector2.Zero,
                    1f, SpriteEffects.None, 0.15f);
            }

            else if (state == 'o')
            {
                spriteBatch.Draw(textureImageO, position,
                    new Rectangle(0, 0, width, height),
                    Color.White, 0, Vector2.Zero,
                    1f, SpriteEffects.None, 0.15f);
            }
        }

        //Update the piece
        public void Update(GameTime gameTime)
        {
            MouseState mouseState = Mouse.GetState();
            mousePosition.X = mouseState.X;
            mousePosition.Y = mouseState.Y;

            //Checks if the piece is clicked and still playable
            if (mouseState.LeftButton == ButtonState.Pressed)
            {
                if (((mousePosition.X > position.X) && (mousePosition.X < (position.X + width + 5))) && ((mousePosition.Y > position.Y) && (mousePosition.Y < (position.Y + height + 5))) && !clicked)
                {
                    //if the piece was clicked and is still playable, set the state of the piece and the next turn.
                    state = SpriteManager.turn;
                    clicked = true;
                    if (state == 'o')
                        SpriteManager.turn = 'x';
                    else
                        SpriteManager.turn = 'o';
                }
            }
        }

    }
}
