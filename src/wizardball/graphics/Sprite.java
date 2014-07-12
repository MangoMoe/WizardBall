package wizardball.graphics;

public class Sprite	// assumes square sprite
{
	
	private final int SIZE;	// size of sprite (default 16, some bigger sprites could be 32)
	private int x,y;
	public int[] pixels;	// needs to be public?
	private SpriteSheet sheet;	// sprite sheet that is the source for the sprite
	
	public Sprite(int size, int x, int y, SpriteSheet sheet)
	{
		SIZE = size;
		this.x = x * SIZE;	// where located on spritesheet,
							//multiplying by size allows you to select by sprite instead of by pixel
		this.y = y * SIZE;
		this.sheet = sheet;
		
		pixels = new int[SIZE * SIZE];	// initialize pixel array to hold sprite
		load();	// load sprite image from spritesheet
		
	}
	
	private void load()	// the spritesheet has already loaded the image we need, we just need to access it
	{
		//TODO: replace these varialbes with better names (just not x and y to avoid naming collisions)
		for(int b = 0; b < SIZE; b++)	// go through each pixel in sprite we want
			{
				for(int a = 0; a < SIZE; a++)
				{
					pixels[a + b*SIZE] = sheet.pixels[(a + this.x) + (b + this.y) * sheet.size()];
				}
			}
	}
	
}
