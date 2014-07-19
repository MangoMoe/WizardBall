/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wizardball;

/**
 *
 * @author Dallin
 * 
 * Next Episode: 23
 */
import wizardball.graphics.Screen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import wizardball.input.Keyboard;

public class WizardBall extends Canvas implements Runnable
{

    private static final long serialVersionUID = 1L;
    
    public static int width = 600;
    public static int height = 325;
    public static int scale = 2; //resolution
    public static String title = "WizardBall";

    private Thread thread;
    private JFrame frame; //frame put outside of a method so its scope includes all methods we want to use it for
    private Keyboard key;
    private boolean running = false; //game is running
    
    private Screen screen;
    
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//main image to display with a buffer
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();//array holding pixels for screen that can be written to, retrieves the image raster and writes it to the array
    //this is a pointer to the array returned by the function
    public WizardBall()
    {
        Dimension size = new Dimension(width*scale, height*scale);
        setPreferredSize(size); //this is an inherited method from canvas
        
        screen = new Screen(width, height);
                
        frame = new JFrame();
        key = new Keyboard();
        
        addKeyListener(key); //need to add to get keyinput
    }

    public synchronized void start() //starts thread with game
    { 
            running = true;
            thread = new Thread(this, "Display"); //new thread to hold game itself
            thread.start();
    }

    public synchronized void stop() //stops thread with game
    { 
            running = false;
            try 
            {
                    thread.join(); //stopping the game rejoins its thread with the others, gets rid of it
            }
            catch (InterruptedException e) 
            { //if something goes wrong with the treads I guess
                    e.printStackTrace();
            }
    }
    
    int dx = 0, dy = 0;
    public void update()
    {
        key.update(); //get key pressed values and do something about them
        
        if(key.up) dy--;
        if(key.down) dy++;
        if(key.left) dx--;
        if(key.right) dx++;
    }
    public void render()
    { //buffer strategy
        BufferStrategy buffer = getBufferStrategy(); //retrieves the bufferstrategy inherited from canvas
        if (buffer == null) //if bufferStrategy hasn't been created yet
        { 
            createBufferStrategy(3); //creates a buffer strategy, 3 parameter is highly recommended (triple buffering), helps it work more efficiently
            return;
        }
        screen.clear(); //clears current screen
        screen.render(dx,dy); //renders new screen to display
        
        for(int i = 0; i < pixels.length;i++) //copies pixels from the screen object
        { 
            pixels[i] = screen.pixels[i];
        }
        
        Graphics g = buffer.getDrawGraphics();//links graphics (where we write data) and buffer
        //********put graphics methods between here
        //g.setColor(new Color(90, 10, 225));
        //g.setColor(Color.CYAN);
        //g.fillRect(0,0,getWidth(),getHeight());//fills a rectangle at 0,0 (top left corner for java) getWidth() wide and getHeight() tall
        
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null); //make sure this is after the fill rect, or it will draw the rectangle over it
        //********and here
        g.dispose(); //removes graphics after each frame, if don't do, will eventually graph
        buffer.show(); //need to show available buffers and swap(or blitt) buffers. important or game will crash
    }
    public void run() //WizardBall implements runnable, so when thread started, run method runs
    { 
        long lastTime = System.nanoTime(); //uses nanoseconds, more precise than current time in milliseconds
        long timer = System.currentTimeMillis(); //timer variable for fps counter
        final double ns = 1000000000.0 / 60.0; //use this variable to ensure 60 times a second
        double delta = 0.0;
        int frames = 0, updates = 0; //first is how many frames per second, second should be 60 updates per second
        while (running) //loops while game going. two parts: graphical and logical
        { 
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1)
            {
                update();
                updates++;
                delta--;
            } //limited to updating 60 times per second
            render(); //unlimited updating, called as many times as possible
            frames++; //increment number of frames per second
            
            if(System.currentTimeMillis() - timer > 1000) //if 1 second has passed
            {
                timer += 1000;//reset timer
                //System.out.println(updates + " ups, " + frames + " fps");//display fps and updates per second
                frame.setTitle(title + "    |   " + updates + " ups, " + frames + " fps");//display title of program and fps counter
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }
    
    public static void main(String[] args) 
    {
        WizardBall game = new WizardBall();
        game.frame.setResizable(false);//resizing can cause graphics errors, make sure to do first
        game.frame.setTitle("WizardBall");
        game.frame.add(game);//adds instance of game to the window (can add because subclass of canvas)
        game.frame.pack();//set size of frame based on component (canvas size)
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//makes sure program shuts down when window closed
        game.frame.setLocationRelativeTo(null);//centers window in middle of screen
        game.frame.setVisible(true);//makes sure window can be seen
        
        game.start();//start game loop and new thread
    }
}
