package wizardball.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet 
{
	
	private String path;		// file path
	private final int SIZE;		// size of spritesheet
	public int[] pixels;
	
	public SpriteSheet(String path, int size)
	{
		this.path = path;				// Input file path
		SIZE = size;					// Input spritesheet size (permanent)
		pixels = new int[SIZE * SIZE];	// create pixel array
		load();
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
