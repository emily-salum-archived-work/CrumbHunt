package com.happykhaos.enemyList;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.happykhaos.entities.Entity;
import com.happykhaos.main.AStar;
import com.happykhaos.main.Camera;
import com.happykhaos.main.Game;
import com.happykhaos.main.Sound;
import com.happykhaos.main.Vector2i;
import com.happykhaos.world.WallTile;
import com.happykhaos.world.World;

import Graphics.SpriteSheet;

public class Enemy extends Entity{

	
	public String EnemyName;
	public int Life = 50;
	public int MaxLife= 50;
	protected int frames = 0, maxframes = 5; 

	protected BufferedImage[] attacksprite;
	public BufferedImage spriteatual;
	public BufferedImage normalSprite;
	
	public String State = "normal";
	// normal, attacking, attacked, attack
	
	private boolean LeftToRight;

	
	public int deathFrames;
	
	private int startX, startY;
	private int minX, minY;
	private int maxX, maxY;
	private boolean directX, directY;
	
	protected int Vision = 90;
	
	public Enemy( double x, double y, int width, int height, float speed, BufferedImage attack, int moves) {
		super(x, y, width, height, null, true);
		
		attacksprite = new BufferedImage[moves]; 
		for(int i = 0; i < moves; i++)
		{
			attacksprite[i] = attack.getSubimage(0 + i * attack.getWidth() / moves, 0, attack.getWidth() / moves, attack.getHeight());
		}
		
		
		

	startX = (int) x;
	startY = (int) y;
	WalkPattern();
	this.speed = speed ;
		// TODO Auto-generated constructor stub
	}
	
public boolean visible = true;
	
	public void WalkPattern()
	{
		for(int i = startX; i != startX - 8; i --)
		{
			if(Game.world.tiles[i / World.TILE_SIZE + (startY * World.WIDTH / World.TILE_SIZE)] instanceof WallTile)
			{
				minX = i + 1;
				break;
			}
			if(i - 1 == startX - 5)
			{
				minX = i;
			}
		}
		
		

		
		for(int i = startX; i != startX + 8; i ++)
		{
			if(Game.world.tiles[i /World.TILE_SIZE  + (startY * World.WIDTH/World.TILE_SIZE )] instanceof WallTile)
			{
				maxX = i- 1;
				break;
			}
			if(i + 1 == startX + 5)
			{
				maxX = i ;
			}
		}

		
	
		
		directX = true;
		//called once instantiated, creates walking pattern for normal state
	}
	
	public void NormalMove()
	{
		if(this.directX) 
		{
			if(this.getX() == this.maxX)
			{
				directX = false;
				
			}
			else
			{
				this.setX((int) (this.getX() + 1));
			}
		}
		else
		{
			if(this.getX() == this.minX)
			{
				directX = true;
				
			}
			else
			{
				this.setX((int) (this.getX() - 1));
			}
		}
	}
	
	private int attackFrame = 0, attackInterval, attackMaxInterval = 10;
	private boolean attacking;
	public void Tick()
	{
		

		
		depth = 0;
		
		if(State == "normal")
		{
			this.spriteatual = this.normalSprite;
			if(Game.rand.nextInt(100) < 30)
			{
				NormalMove();
			}

			if(DistanceOf(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < Vision) 
			{
				this.State = "attack";
			}
		
		}
	
		
		if(Game.rand.nextInt(100) < 30)
		{

			if(State == "attack")
			{
				
				Move();
				if(isCollidingWithPlayer(10,10))
				{

					
						State = "attacking";
						
						attackInterval = attackMaxInterval;
								
					

				}
				
			}
			
		
			
			
		
		}	
		
		
		if(!isCollidingWithPlayer(10,10) && State == "attacking")
		{

			
			//this.spriteatual = normalSprite;
			//State = "attack";
				
						
			

		}
		
		if(State == "attacked")
		{
			if(attackFrame - 1 >= 0 )
			{
				if(attackInterval > attackMaxInterval)
				{
					attackFrame --;
					attackInterval = 0;
				}
				else
				{
					attackInterval++;
				}
				this.spriteatual = attacksprite[attackFrame];
			}
			else
			{
				
				if(attackInterval > attackMaxInterval)
				{
					this.spriteatual = normalSprite;
					State = "attack";
					attackInterval = 0;
				}
				else
				{
					attackInterval++;
				}
			}

		}
		
		
		if(State == "attacking")
		{
			
			
			
			if(attackFrame + 1 <= attacksprite.length -  1)
			{
				if(attackInterval > attackMaxInterval)
				{
					attackFrame ++;
					this.spriteatual = attacksprite[attackFrame];
					attackInterval = 0;
				}
				else
				{
					attackInterval++;
				}
				
			}
			else
			{
				if(isCollidingWithPlayer(10,10))
				{
					DamagePlayer();
				}
				
				
				State = "attacked";
			}
		}
		
		
		if(Life <= 0)
		{
			this.State = "dead";
			deathFrames ++;
			
			if(deathFrames > 60)
			{
				Game.entities.remove(this);
				Game.enemies.remove( this);				
			}

		}
			

		
		
		
		
	}
	
	
	public void DamagePlayer()
	{
		
		Game.player.Damage(10);
	}

	
	
	public void Move()
	{
		
		
	
			Vector2i start = new Vector2i(this.getX() / World.TILE_SIZE , this.getY() / World.TILE_SIZE );
			Vector2i target = new Vector2i(Game.player.getX() / World.TILE_SIZE ,  Game.player.getY() / World.TILE_SIZE );
			
			path = AStar.PathFinding(Game.world,start , target);
		


		
		
				this.FollowPath(path);		
		
	
		
		
		
		
	}
	
	public boolean isCollidingWithPlayer(int widthmask, int heightmask)
	{
		
		
		
		Rectangle Enemy = new Rectangle(this.getX() - widthmask / 2, this.getY() - heightmask / 2 ,
				this.getWidth() + widthmask, this.getHeight() + heightmask);
		
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), Game.player.getWidth(), Game.player.getHeight());
		
		
		return Enemy.intersects(player);
	}
	
	

	
	

	
	public void DamageSound()
	{
	Sound.hurtSound.Play();
	}
	

	
	
	public void Die()
	{
	

	}

	
	public void RenderHealth(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y  - 3, (this.MaxLife) /2 , 5);

		g.setColor(Color.GREEN);
		g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y - 3, (this.Life) /2, 5);
	

		
	}
	
	
	private float alpha = 1, alphaFrames;
	public void Render(Graphics g)
	{
		
		Graphics2D g2 = (Graphics2D) g;
		
		if(State == "dead")
		{
			alphaFrames ++;
			
			if(alphaFrames > 6)
			{
				alphaFrames = 0;
				alpha -= 0.1f;
			}
		}
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		g2.setComposite(ac);
		g.drawImage(spriteatual, getX() - Camera.x, getY() - Camera.y - z, width, height,null);
		
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1);
		g2.setComposite(ac);
		
		if(State != "dead")
		{
	  //      RenderHealth(g);			
		}

		

		
		
	}
	

}
