/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wizardball.graphics;

import java.util.Random;

/**
 *
 * @author 20603
 */
public class Screen  //fills in and displays pixels (aka render)
{
    
    private int width, height;
    public int[] pixels;
    
    public final int TILE_MAP_WIDTH = 8;
    public final int TILE_MAP_WIDTH_MASK = TILE_MAP_WIDTH - 1; //for bitwise operations
    public final int TILE_MAP_HEIGHT = 4;
    public final int TILE_MAP_HEIGHT_MASK = TILE_MAP_HEIGHT - 1;
    
    private int tileWidth = 16;
    
    public int[] tiles = new int[TILE_MAP_WIDTH * TILE_MAP_HEIGHT]; //array holding tiles for level tiling(?)
    
    private Random random = new Random(); //makes a java random generator, apparently really great for games
    
    public Screen(int width, int height) //constructor
    { 
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        
        for(int i = 0; i < TILE_MAP_WIDTH * TILE_MAP_HEIGHT; i++)
        {
            tiles[i] = random.nextInt(0xffffff); //sets each tile color to a random color between 0xffffff (white) and 0x000000 (black)
            
        }
        
    }
    
    public void clear()
    { //clears the screen so animations don't overlap
        for(int i = 0; i < pixels.length;i++)
        {
            pixels[i] = 0;
        }
    }
    
    public void render(int dx, int dy) 
    {
        
        for (int y = 0; y < height; y++) //these for loops go through every pixel in pixels
        { 
            int y2 = y + dy; //variable to manipulate for animation
            //if (y2 < 0 || y2 >= height) break;
            for (int x = 0; x < width; x++)
            {
                int x2 = x + dx;
                //int x2 = x + dx;
                //if (x2 < 0 || x2 >= width) break;
                int tileIndex = ((x2 >> 4) & (TILE_MAP_WIDTH_MASK)) + ((y2 >> 4) & (TILE_MAP_HEIGHT_MASK)) * TILE_MAP_HEIGHT; //finds the tile index for this pixel by dividing by the width of each tile (giving every 32 pixels) and multiplying by the width of the tile map (64 tiles)
                // the & TILE_MAP_WIDTH_MASK "resets" to 0 if over 64, preventing array index problems, think about bitwise
                //int tileIndex = (x / tileWidth) + (y / tileWidth) * tileMapWidth; //bitwise operators improve speed, this code and the line 2 above do the same thing
                ////////////////bitwise operators, know//////////////////////////
                pixels[x  + (y * width)] = tiles[tileIndex]; //assigns color to that pixel(index expression converts x,y coordinate to single number for one dimensional array index)
            }
        }
    }
    
}