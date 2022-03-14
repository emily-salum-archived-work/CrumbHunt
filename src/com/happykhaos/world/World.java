package com.happykhaos.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.happykhaos.enemyList.Enemy;
import com.happykhaos.enemyList.Spider;
import com.happykhaos.entities.*;
import com.happykhaos.main.Camera;
import com.happykhaos.main.Game;


public class World {
	
	
	public static Tile[] tiles;
	public static int WIDTH;
	public static int HEIGHT;
	public static int vampirespawnX = 0;
	public static int vampirespawnY = 0;

	public static int agiotaspawnX = 0;
	public static int agiotaspawnY = 0;
	
	public static final int TILE_SIZE = 17;
	
	public boolean WorldReady = false;
	
	public World(String path)
	{
	
		if(!Game.genWorld)
		{
			
		
		
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			
			int[] pixels = new int[WIDTH * HEIGHT];
			tiles = new Tile[WIDTH * HEIGHT];
			map.getRGB(0,0, WIDTH, HEIGHT, pixels,0 ,WIDTH);
			
			for(int xx = 0; xx < WIDTH; xx++)
			{
				for(int yy = 0; yy < HEIGHT; yy++)
				{
					
					
					int pixelAtual = pixels[xx+(yy * WIDTH)];
		            RenderFunction(pixelAtual, xx, yy);
					

				

					
				}
				
				

			}
			
			SpawnEnemies();
			InicialFruits();
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else
		{
					
		}
		
		

		
		this.WorldReady = true;
		
	}
	
	
	

public static int fruitSpawn;
	public int enemynumber, fruitnumber;
	public void SpawnEnemies() 
	{
	
		for(int i = 0; i < enemynumber; i++)
		{
			while(true)
			{
				int xx = Game.rand.nextInt(WIDTH);
				int yy = Game.rand.nextInt(HEIGHT);
				if(!(tiles[xx + yy * WIDTH] instanceof WallTile) && !EnemySpace(xx,yy) && !InPlayerRange(xx,yy))
				{
					Enemy en = new Enemy(xx * TILE_SIZE,yy * TILE_SIZE, 10,10,2, Game.ssheet.getSprite(34, 65, 40, 13), 3);
					en.normalSprite = Game.ssheet.getSprite(34, 1, 11, 12);
					Game.enemies.add(en);	
					break;
				}

			}			
		}


	}
	
	public boolean InPlayerRange(int xx, int yy)
	{
		
		Entity en = new Entity(xx - 40,yy - 40,80,80,null, true);
		
		return Entity.isColliding(en, Game.player);
				
			
		
		
	}
	
	public boolean EnemySpace(int xx, int yy)
	{
		for(int i = 0; i < Game.enemies.size(); i++)
		{
			if(Game.enemies.get(i).isColliding(xx, yy))
			{
				return true;
			}
		}
		
		return false;
	}
	

	public void Tick()
	{
		if(Season.returnState() == "Summer")
		{
			fruitSpawn ++;
			
			if(fruitSpawn > 200)
			{
				fruitSpawn = 0;
				SpawnFruit();
			}				
		}
		
		spiderSpawn ++;
		if(spiderSpawn > 600)
		{
			spiderSpawn = 0;
			SpawnSpider();
		}

	}
	
	public int spiderSpawn;
	
	public boolean FruitSpace(int xx, int yy)
	{
		
		for(int i = 0; i < Game.entities.size(); i++)
		{
			if(Game.entities.get(i).isColliding(xx, yy))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	public void InicialFruits()
	{
		for(int i = 0; i < fruitnumber; i++)
		{
			while(true)
			{
				int xx = Game.rand.nextInt(WIDTH);
				int yy = Game.rand.nextInt(HEIGHT);
				if(!(tiles[xx + yy * WIDTH] instanceof WallTile) && !FruitSpace(xx,yy) && !InPlayerRange(xx,yy))
				{
					Fruit fruit = new Fruit(xx * TILE_SIZE,yy * TILE_SIZE,Game.rand.nextInt(4));
					Game.entities.add(fruit);
					break;
				}

			}			
		}
		

	}
	public void SpawnSpider()
	{
		while(true)
		{
		int x = Game.rand.nextInt(World.WIDTH - 1);
		int y = Game.rand.nextInt(World.HEIGHT - 1);
		
		if(tiles[x + y * World.WIDTH] instanceof WallTile)
		{
			continue;
		}
		
		Spider spider = new Spider(x * World.TILE_SIZE, y * World.TILE_SIZE, 16, 16, 1f, Game.ssheet.getSprite(193, 1 , 13, 13), 1);
		Game.enemies.add(spider);
		
		break;
		
		
		}
	}
	
	public void SpawnFruit()
	{
		while(true)
		{
		int x = Game.rand.nextInt(World.WIDTH - 1);
		int y = Game.rand.nextInt(World.HEIGHT - 1);
		
		if(tiles[x + y * World.WIDTH] instanceof WallTile)
		{
			continue;
		}
		
		Fruitspawn fruit = new Fruitspawn(x * World.TILE_SIZE, y * World.TILE_SIZE, Game.rand.nextInt(4));
		Game.entities.add(fruit);
		
		break;
		
		
		}
		
		
		
	}
	
	public void Render(Graphics g)
{
		
		int xstart = Camera.x/TILE_SIZE ;
		int ystart = Camera.y/TILE_SIZE ;
		
		int xend = xstart + (Game.WIDTH * Game.SCALE /TILE_SIZE);
		int yend = ystart + (Game.HEIGHT * Game.SCALE/ TILE_SIZE);
		
		for(int xx = xstart; xx <= xend; xx++)
		{
			for(int yy = ystart; yy <= yend; yy++)
			{
				if((xx < 0) ||(yy < 0) || (xx >= WIDTH) || (yy >= HEIGHT))
				{
					continue;
				}
				if((xx + (yy* WIDTH) > tiles.length) ||(xx + (yy* WIDTH) < 0))
				{
					continue;
				}
				Tile tile = tiles[xx + (yy* WIDTH)];
				if(tile != null)
				{
					tile.Render(g);
				}

	
			}
		}
}
	

	public static void RenderMiniMap()
	{
		
		for(int i = 0; i < Game.minimappixels.length; i++)
		{
			Game.minimappixels[i] = 0;
		}
		
		for(int xx = 0; xx < World.WIDTH; xx++)
		{
			for(int yy = 0; yy < World.HEIGHT; yy++)
			{
				if(tiles[xx + (yy * World.WIDTH)] instanceof WallTile && tiles[xx + (yy * World.WIDTH)].visible)
				{
					Game.minimappixels[xx + (yy * World.WIDTH)] = 0xFF00B718;
				}
				
				
				if(Game.player.getX() / World.TILE_SIZE == xx && Game.player.getY() / World.TILE_SIZE == yy)
				{
					Game.minimappixels[xx + (yy * World.WIDTH)] = 0xFFFFE23F; 
				//	continue;
				}
				
				for(int i = 0; i < Game.enemies.size(); i++)
				{
					if(Game.enemies.get(i).getX() / World.TILE_SIZE == xx && Game.enemies.get(i).getY() / World.TILE_SIZE == yy
							&& Game.enemies.get(i).visible)
					{
						Game.minimappixels[xx + (yy * World.WIDTH)] = 0xFF0000;
					//	continue;
					}
				}
			
	
				
			}
		}
		
		
	}
	
	public static boolean Space_free_Dynamic(int x, int y, int w, int h)
	{
		int x1 =x / w;
		int y1 = y / h;
		if(x1 < 0)
		{
			return true;
		}
		if(y1 < 0)
		{
			return true;
		}
		
		int x2 = (x + w - 1) /w;
		int y2 = y / h ;
		
		int x3 = x / w;
		int y3 = (y + h - 1) / h;
		
		int x4 =  (x + w - 1) /w;
		int y4 =(y + h - 1) / h;

		
		 return !(tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile
				|| tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile
				|| tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile
				|| tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile);
		
	}
	
	public static boolean Space_free(int x, int y, int w, int h, int z)
	{



		
	
		
		
		int x1a = (x ) / TILE_SIZE;
		int y1a = (y ) / TILE_SIZE;
		
	
		
		int x2a = (x + 14 - 1) / TILE_SIZE;
		int y2a = (y )/ TILE_SIZE;
		
	
		
		
		int x3a = (x ) / TILE_SIZE;
		int y3a = (y + 14 - 1) / TILE_SIZE;
		
	

		int x4a = (x + 14 - 1) / TILE_SIZE;
		int y4a = (y + 14 - 1) / TILE_SIZE;
		
	
		

		return !(/*tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile
				|| tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile
				|| tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile
				|| tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile
				||*/tiles[x1a + (y1a * World.WIDTH)] instanceof WallTile
				|| tiles[x2a + (y2a * World.WIDTH)] instanceof WallTile
				|| tiles[x3a + (y3a * World.WIDTH)] instanceof WallTile
				|| tiles[x4a + (y4a * World.WIDTH)] instanceof WallTile
				)
				
				
				;
		
	

				
			
		
	}

	
	

	public void RenderFunction(int pixelAtual, int xx, int yy)
	{


		BufferedImage ground;
	BufferedImage wall;
	
	if(Season.returnState() == "Summer")
	{
		ground = Tile.tile_FLOOR;
		wall = Tile.tile_WALL;
	}
	else
	{
		ground = Tile.tile_FLOOR_ICE;
		wall = Tile.tile_WALL_ICE;
	}
	
	



		tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE,yy * TILE_SIZE, ground);
		
		switch(pixelAtual) 
		{
		case 0xFFFF6A00:
			Game.player.setX(xx * TILE_SIZE);
			Game.player.setY(yy * TILE_SIZE);
			break;
			
		case 0xFF282FFF:
			tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE,yy * TILE_SIZE, ground);
			break;
			
		case 0xFFFF4800:
			fruitnumber ++;
			break;
			
			
		case 0xFF000000:
			tiles[xx + (yy * WIDTH)] = new WallTile(xx * TILE_SIZE,yy * TILE_SIZE, wall);			
			break;
			
		case 0xFF606060:
			if(Game.rand.nextInt(2) == 1)
			{
				tiles[xx + (yy * WIDTH)] = new WallTile(xx * TILE_SIZE,yy * TILE_SIZE, wall);
			}
			break;
			
		case 0xFFFF0000:

			
			enemynumber++;
			break;
			
		case 0xFF27B2FD:
			AreaHandle a = new AreaHandle(xx * TILE_SIZE - TILE_SIZE,yy * TILE_SIZE);
			Game.entities.add(a);
			break;


			
		}
		

		


		
				

						
						
					
						
					
				
				
			

			
		}
		
	

	

}
