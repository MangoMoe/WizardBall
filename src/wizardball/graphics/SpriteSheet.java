package wizardball.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet 
{
	
	private String path;		// file path to spritesheet
	private final int SIZE;		// size of spritesheet
	public int[] pixels;
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/spriteSheet.png", 256);
														// make sure the build path includes the "res" (short for resources)
														// by going to properties/java build path/libraries/add class folder
														// also make sure path string starts with forward slash
	
	public SpriteSheet(String path, int size)
	{
		this.path = path;				// Input file path
		SIZE = size;					// Input spritesheet size (permanent)
		pixels = new int[SIZE * SIZE];	// create pixel array
		load();
	}
	
	public int size()
	{
		return SIZE;
	}
	
	private void load()
	{
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path)); // read in image
			int w = image.getWidth(); 	// inside try block because temporary
			int h = image.getHeight();
			
			image.getRGB(0, 0, w, h, pixels, 0, w); // convert image to pixel array
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
