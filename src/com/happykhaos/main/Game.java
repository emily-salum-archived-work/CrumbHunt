package com.happykhaos.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
//import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
//import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


import com.happykhaos.enemyList.Enemy;
import com.happykhaos.entities.Entity;
import com.happykhaos.entities.Player;
import com.happykhaos.world.Season;
import com.happykhaos.world.Tile;
import com.happykhaos.world.World;

import GameStates.Nest;
import GameStates.Shop;
import GameStates.ShopItem;
import GameStates.UI;
import Graphics.Particles;
import Graphics.SpriteSheet;




public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static float Volume = 0f;
	
	public static JFrame frame;
    private boolean IsRunning = true;
    private Thread thread;
    public static int WIDTH = 360;
    public  static int HEIGHT = 350;
    public final static int SCALE = 2;
    
    // cutscene meio estranha ai
    public static int entrance = 1;
    public static int beggin = 2;
    public static int playing = 3;
    public static int SceneState = 3;
    
   public static boolean sceneTransition;
    
   static int minimapX;

static int minimapY;
    
    public static BufferedImage image;
    
    public static BufferedImage background;
   


  
    public static List<Entity> entities;
    public static List<Enemy> enemies;
    

	 public static List<Particles> p;
    
    public static boolean Area1Kill= false;
    
    public static boolean firstGame  = true;


	

	
	
    
 
    public static SpriteSheet ssheet = new SpriteSheet("/pac_spritesheet.png");
    
    public static BufferedImage minimap; 	

    
    public static World world;
    
    public static boolean genWorld = false;
    
    public static Player player;

    public static UI ui;
    public Menu menu;
    public GameOver gameover;
    public static Nest nest;
    public static Shop shop;
    
    public static InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("Cute_Font.ttf");
    public static InputStream streamOpt = ClassLoader.getSystemClassLoader().getResourceAsStream("Sweet Creamy.ttf");
    
    
    public static BufferedImage backgroundTree;
    
    public static Font newFont;
    public static Font optionFont;
    
    
	public static String GameState = "MENU";
	
	public static int[] pixels;
	public int[] lightmappixels;
	public static int[] minimappixels;
	

    public static Random rand;

    
    public static int CurrentLevel = 0;
    public boolean Direction = false;
    public static boolean HasStartingLocation = false;
    
 
    
    
    private int d;
    
    public static void main(String[] args) 
    {
        Game teste = new Game();
        teste.start();

    }
    
    public Game()
    {
    	
    	rand = new Random();
 
    
   
    	
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
  
      this.setPreferredSize(new Dimension(WIDTH * SCALE ,HEIGHT * SCALE ));
        Inicialize();
        
        try {
			backgroundTree = ImageIO.read(getClass().getResource("/tree.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

        try {
  			newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(20f);
  		} catch (FontFormatException e) {
  
  			e.printStackTrace();
  		} catch (IOException e) {
  			
  			e.printStackTrace();
  		}
        
        
        try {
  			optionFont = Font.createFont(Font.TRUETYPE_FONT, streamOpt).deriveFont(20f);
  		} catch (FontFormatException e) {
  		
  			e.printStackTrace();
  		} catch (IOException e) {
  		
  			e.printStackTrace();
  		}
       	ui = new UI();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        
        entities = new ArrayList<Entity>();

        enemies = new ArrayList<Enemy>();

        player = new Player(0, 0, 14, 14);
        entities.add(player);
        menu = new Menu();
        gameover = new GameOver();
         nest = new Nest();
         shop = new Shop();
        p = new ArrayList<Particles>();
        


  


    
        
 
		Season.ActEffects();
    }
    
   
    
    public static void Reset(int previousLevel)
    {
    
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        entities = new ArrayList<Entity>();

        enemies = new ArrayList<Enemy>();

        player = new Player(0, 0, 14, 14);

        p = new ArrayList<Particles>();
        
       
        	
        
        Season.ActEffects();
        
        	Player.life = Player.standardMaxlife + (shop.ReturnItem("life").level-1) * 10;
        	player.speed += (shop.ReturnItem("speed").level-1) / 4.5f;
    		MommyBird.Carry += (shop.ReturnItem("carry").level - 1);
    		MommyBird.bonus_fruits = shop.ReturnItem("bonus fruits").level;
        entities.add(player);
 

        world = new World("/mapPac.png");
    
      

 
        		
         
             	
             	
          
        	        minimapX = 0;
        	        minimapY = 0;

        		Game.GameState = "NORMAL";
    }
    
    public void Inicialize()
    {
        frame = new JFrame("Teste");
        frame.add(this);
       // frame.setUndecorated(true);
        frame.setResizable(false);
        frame.pack();
        
        frame.setTitle("Crumb Hunt");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
      
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(getClass().getResource("/cursor.png"));
        
        Cursor c = toolkit.createCustomCursor(image, new Point(0,0), "img");
        frame.setCursor(c);
   //     frame.setIconImage(icon);
        frame.setAlwaysOnTop(true);
        
        
    }
    
    public synchronized void start()
    {
        thread = new Thread(this);
        IsRunning = true;
        thread.start();
  
    }
    
    
    
    public synchronized void stop()
    {
        IsRunning = false;
        try{
            thread.join();
        } catch(InterruptedException e)
        {
        e.printStackTrace();
        }
        
    }   
    
    public void Tick()
    {
    
    	for(int i = 0; i < p.size(); i++)
    	{
    		p.get(i).Tick();
    	}
    	
    	if(GameState == "NORMAL") 
    	{
    		
    


    	
    	if(world.WorldReady)
    	{
    		world.Tick();
    		if(keyFrame > 0)
    		{
    			keyFrame --;
    			player.MoveCommand(key);
    		}
    		if(CurrentLevel == 0)
        	{
        		if(enemies.size() == 0)
        		{
        			Area1Kill = true;
        		}
        	}
            	for(int i = 0; i < entities.size(); i++)
            	{
            		Entity 	e = entities.get(i);
            		e.Tick();
            	}    	
            	
            	for(int i = 0; i < enemies.size(); i++)
            	{
            		Entity 	e = enemies.get(i);
            		e.Tick();
            	}    	
    		
    		

            	
        //	Camera.x = 40;
        	
        	
        	for(int i = 0; i < World.tiles.length; i++)
        	{
        		Tile e = World.tiles[i];
        		if(e != null)
        		{
            		e.Tick();
        		}

        	}
    	}


    	}
    	else
    	{
    		if(GameState == "GAMEOVER")
    		{
    			gameover.Tick();
    			if(this.CurrentLevel == 4)
    			{
    				Sound.PurpleMusic.Stop();
    			}
    		}
    		else
    		{
    			if(GameState == "MENU")
    			{

    				menu.Tick();
    				
    			}
    		}
    	}
    	
    	  
    	
    }
 
    	public static void g(int xm, int ym,int xo1, int yo1, int Color)
    	{
    		for(int xx = 0; xx < xm; xx++)
    		{
    			for(int yy = 0; yy < ym; yy++)
    			{
  
    			
    				
    				int xo = xo1 + xx;
    				int yo = yo1 + yy;
    				if(xo < 0)
    				{
    					continue;
    				}
    				else
    				{
        				if(xo > WIDTH)
        				{
        					continue;
        				}
    				}


    				if(yo < 0)
    				{
    					continue;
    				}
    				else
    				{
        				if(yo > HEIGHT)
        				{
        					continue;
        				}
    				}
    				if(xo + (yo * WIDTH) < 0)
    				{
    					continue;
    				}
    				
    				if(xo + (yo * WIDTH) < pixels.length)
    				{
    					pixels[xo + (yo * WIDTH)] = Color;    					
    				}

    				
    			
    			}
    		}
    	}

    	public void applyLight()
    	{
    		for(int xx = 0; xx < Game.WIDTH; xx++)
    		{
        		for(int yy = 0; yy < Game.HEIGHT; yy++)
        		{
        			if(lightmappixels[xx + (yy* Game.WIDTH)] == 0xFFFFFFFF)
        			{
        				int pixel = Pixel.getLightBlend(pixels[xx + yy* Game.WIDTH], 0xA0A0A0, 0);
        				pixels[xx + (yy * Game.WIDTH)] = pixel;
        			}
        			
        		
        			
        			
        		}
    		}
    	}
    	
    	public boolean isColliding(int x1, int x2, int y1, int y2, int[] pixels1, int[] pixels2)
    	{
    		
    		
    		
    		
    		return false;
    	}
    	
        public void Render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        
        if (bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        
        g.setColor(new Color(0,0,0));
        g.fillRect(0,0, WIDTH, HEIGHT);

        
        Graphics c = image.getGraphics();
        
        
        if(Game.GameState == "NORMAL" )
        {

        	if(world.WorldReady)
        	{
                world.Render(g);
        	}

        	Collections.sort(entities, Entity.depthSorter);
        	for(int i = 0; i < entities.size(); i++)
        	{
        		Entity 	e = entities.get(i);
        		e.Render(g);
        	}

          	for(int i = 0; i < enemies.size(); i++)
        	{
        		Entity 	e = enemies.get(i);
        		e.Render(g);
        	}    
        	
        	
        
        	
 
        }

      
		/*boolean mouseInteracted = false;
		for(int i = 0; i < npcList.size(); i++)
		{
			if(!mouseInteracted && npcList.get(i).next)
			{
				mouseInteracted= npcList.get(i).showMessage;
			}
			
		}*/
        
        ui.Render(c);
        //applyLight();
        if(Game.GameState == "NORMAL" && Camera.Transition == false)
        {
        	
        
        	
        		minimapX =  Camera.clamp(Camera.x / World.TILE_SIZE - Game.WIDTH / World.TILE_SIZE / 2, 0, World.WIDTH - 20);	
        	
        	
        	
        	
            	minimapY = Camera.clamp(Camera.y / World.TILE_SIZE - Game.HEIGHT / World.TILE_SIZE / 2, 0, World.HEIGHT - 20);        		
        	

           // g.setColor(Color.yellow);
         //   g.fillRect(Game.WIDTH / 10 - 1, Game.HEIGHT / 2 + 49, 22, 22);        	
          //  g.drawImage(minimap.getSubimage(minimapX, minimapY, 20, 20), Game.WIDTH / 10, Game.HEIGHT / 2 + 50, 20, 20, null);   

        }
        

        


     
  


        if(GameState == "NEST")
        {
        	
        	nest.Render(g);
        }
        
        if(GameState == "SHOP")
        {
        	shop.Render(g);
        }
        	
   
      
  		if(GameState == "GAMEOVER")
		{
  			gameover.Render(c);


		}
  		else
  		{
  			if(GameState == "MENU")
  			{
  				menu.Render(g);
  				
  			}
  		}
  		
  		
    	for(int i = 0; i < p.size(); i++)
    	{
    		p.get(i).Render(g);
    	}
    	
        g.dispose();
        g = bs.getDrawGraphics();


     
  

        g.drawImage(image, 0 ,0 ,         WIDTH * SCALE , HEIGHT * SCALE , null);
      
   
        
        bs.show();
    }
        
        
        
       
        
        
    public void run() 
    {
    	requestFocus();
        long LastTime = System.nanoTime();
        double AmmountOfTicks = 60.0;
        double Ns = 1000000000 / AmmountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        requestFocus();
        while(IsRunning)
        {
            long now = System.nanoTime();
            delta += (now - LastTime) / Ns;
            LastTime = now;
            if (delta >= 1)
            {
                Tick();
                Render();
                frames++;
                delta--;
        
            }
            if (System.currentTimeMillis() - timer >= 5000)
            {	
//               System.out.println("Level :"+ CurrentLevel);
            //    World.Spawn();

                frames = 0;
                timer += 1000;
            }
        }
        stop();
    
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	private int keyFrame;
	private String key;
	@Override
	public void keyPressed(KeyEvent e) {
		
	//	if(e.getKeyCode() == KeyEvent.VK_SPACE)
	//	{
	//		player.jump = true;
	//	}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE)	
		{

			boolean interacted = false;
		
			
			if(!interacted)
			{

				player.SpaceCommand();			
			}
	
			

			
		}
		
		if((e.getKeyCode() == KeyEvent.VK_RIGHT) || e.getKeyCode() == KeyEvent.VK_D )
		{

			player.MoveCommand("right");
			key = "right";
			this.keyFrame = 10;
		}
		else
		{
			if(e.getKeyCode() == KeyEvent.VK_LEFT ||e.getKeyCode() == KeyEvent.VK_A)
			{
				player.MoveCommand("left");
				key = "left";
				this.keyFrame = 10;
			}
		}
		
		
		
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
		{

			player.MoveCommand("up");
			key = "up";
			this.keyFrame = 10;
			if(GameState == "MENU")
			{
				menu.opts.up = true;
			}
			
		}
		else
		{
			if(e.getKeyCode() == KeyEvent.VK_DOWN ||e.getKeyCode() == KeyEvent.VK_S)
			{
			
				player.MoveCommand("down");
				key = "down";
				this.keyFrame = 10;
				if(GameState == "MENU")
				{
					menu.opts.down = true;
				}
			}
		}
		

		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		

		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			UI.ButtonClicked();
			Game.GameState = "PAUSE";
		}
		
		/*
			if((e.getKeyCode() == KeyEvent.VK_RIGHT) || e.getKeyCode() == KeyEvent.VK_D)
			{
				player.right = false;
			}
			else
			{
				if(e.getKeyCode() == KeyEvent.VK_LEFT ||e.getKeyCode() == KeyEvent.VK_A)
				{
					player.left = false;

				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
			{
				player.up= false;
			}
			else
			{
				if(e.getKeyCode() == KeyEvent.VK_DOWN ||e.getKeyCode() == KeyEvent.VK_S)
				{
					player.down = false;

				}
			}		
		*/
	
		

		
	}

	

	
	@Override
	public void mouseClicked(MouseEvent e) {

		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		

		
		boolean mouseInteracted = false;


		
		if(Game.GameState == "GAMEOVER")
		{
		//	Game.CurrentLevel = 0;
			//Game.Reset(CurrentLevel);
		
			Player.food +=Player.fruitsCollected - Player.fruitsCollected / 2  ;
			Player.fruitsCollected = 0;
			GameState = "NEST";
			nest.NextTurn();
		}
		else
		{
			if(Game.GameState == "MENU")
			{
				menu.ClickedOption(e.getX()/ SCALE 	, e.getY() / SCALE);
				
			}
			else
			{
				if(Game.GameState == "ENDRUN")
				{
					ui.endRun.ClickedOption(e.getX() / SCALE, e.getY() / SCALE);
					ui.PauseOption(e.getX() / SCALE, e.getY() / SCALE);
					
				}
				else
				{
					if(Game.GameState == "SHOP")
					{
						shop.ClickItem(e.getX() / SCALE, e.getY() / SCALE);
					}
				}
			}
			
		}
		
		}
		

		

		
	

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(Game.GameState == "MENU")
		{
			menu.EnterOption(e.getX()/ SCALE , e.getY()/ SCALE);
			
		}
		
		if(Game.GameState == "NEST")
		{
	
			nest.ConfirmClick(e.getX()/ SCALE , e.getY()/ SCALE);
			
		}
		
		if(Game.GameState == "SHOP")
		{
			shop.ConfirmClick(e.getX()/ SCALE , e.getY()/ SCALE);
		}

	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.print("moving");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

		menu.opts.OnMouseOver(e.getX()/ SCALE, e.getY()/ SCALE);
		ui.endRun.OnMouseOver(e.getX()/ SCALE, e.getY()/ SCALE);
		nest.opts.OnMouseOver(e.getX()/ SCALE, e.getY()/ SCALE);

		
		
	}

}
