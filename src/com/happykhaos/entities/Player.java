package com.happykhaos.entities;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


import com.happykhaos.main.Camera;
import com.happykhaos.main.Game;
import com.happykhaos.main.GameOver;
import com.happykhaos.main.MommyBird;
import com.happykhaos.main.Sound;

import com.happykhaos.world.World;

import GameStates.Nest;
import Graphics.Particles;

public class Player extends Entity
{
	public boolean right,up,left,down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = 0;

	
	private int frames = 0, maxframes = 10, index = 0, maxindex = 3; 
	
	private int framesound = 0, maxsoundframe = 20;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	
	private BufferedImage CurrentSprite;
	
	private BufferedImage damagedPlayer;
	
	public static int life = 50;
	public static int Maxlife , standardMaxlife= 50;

	private int DamageFrames;
	public boolean isDamaged = false;
	public boolean Throw;

	
	public static String mode = "NORMAL";
	
	
	

	
	
	
	

	public static boolean SpaceTransition;
	

	public float HungerBonus = 0;
	

	
	public static int fruitsCollected;
	
	public static int food = 0;
	public static float StandardVelocity = 1.7f;

	
	public Player(int x, int y, int width, int height)
	{
		super(x, y, width, height, null, true);
		//speed = StandardVelocity;
		rightPlayer = new BufferedImage[3];
		leftPlayer = new BufferedImage[3];
		upPlayer = new BufferedImage[3];
		downPlayer = new BufferedImage[3];
	
		depth = 1;
		
		leftPlayer[0] = Game.ssheet.getSprite(1, 81, 13, 11);
	    leftPlayer[1] = Game.ssheet.getSprite(17, 81, 13, 11);
	    leftPlayer[2] = Game.ssheet.getSprite(34, 81, 12, 11);
	    
		rightPlayer[0] = Game.ssheet.getSprite(1, 1, 13, 12);
		rightPlayer[1] = Game.ssheet.getSprite(17, 51, 13, 12);
		rightPlayer[2] = Game.ssheet.getSprite(32, 52, 13, 11);
		
		downPlayer[0] = Game.ssheet.getSprite(1, 1, 13, 12);
		downPlayer[1] = Game.ssheet.getSprite(1, 1, 13, 12);
		downPlayer[2] = Game.ssheet.getSprite(1, 1, 13, 12);
		
		upPlayer[0] = Game.ssheet.getSprite(1, 1, 13, 12);
		upPlayer[1] = Game.ssheet.getSprite(1, 1, 13, 12);
		upPlayer[2] = Game.ssheet.getSprite(1, 1, 13, 12);
		
CurrentSprite = rightPlayer[0];

		

	}
	
	public int getY()
	{
		return (int)(this.y - this.z);
	}
	
	public void Render(Graphics g)
	{
		
		g.setColor(Color.RED);
		g.fillRect( 20, Game.HEIGHT - 40, (this.Maxlife)  , 15);

		g.setColor(Color.GREEN);
		g.fillRect( 20,  Game.HEIGHT - 40, (this.life) , 15);

	
		if(isDamaged == true)
		{
			g.drawImage(damagedPlayer,this.getX() - Camera.x + 2 , this.getY() - Camera.y  ,this.getWidth(), this.getHeight(), null);
			return;
		}
		
		Graphics2D g2 = (Graphics2D) g;
		/*
		AffineTransform old = g2.getTransform();
		//g2.translate();
		g2.rotate(Math.toRadians(60), this.getX(),this.getY());
		if(dir == up_dir)
		{
		
			g2.rotate(90, 10, this.getY());
			
		}
		else
		{
			if(dir == down_dir)
			{
				g2.rotate(180, 0, 0);
			}
		
				
		}*/
		g2.drawImage(CurrentSprite,this.getX() - Camera.x , this.getY() - Camera.y , null);
		//g2.setTransform(old);

		
		
		
		

	}
	
	


	
	public void Damage(int Damage)
	{
		if(life > 0)
		{
			
			
			
				Sound.hitSound.Play();
				life-= Damage;
			this.isDamaged = true;
			

		}
	}
	




	
	
	
	


	
	
	

	
	public void Tick()
	{
		
	
			
			
			
			moved = false;
			
		
			
			
			
		
					
			
		
			NormalMove();
			
		
	
				
			
		
			
			if(this.isDamaged)
			{
				this.DamageFrames ++;
				if(this.DamageFrames == 15)
				{
					this.DamageFrames = 0;
					this.isDamaged = false;
				}
			}
			

			
			
			
			if(life <= 0)
			{
				Game.GameState = "GAMEOVER";
				Sound.deadSound.Play();
				
				if((Player.fruitsCollected /2) %2 != 0)
				{
					Player.fruitsCollected += 1;
				}
				//Player.fruitsCollected *= MommyBird.bonus_fruits + 1;
		  
			}
			

			
			
		
			


			}
			
			
		
		
		
	
	
	public void MoveCommand(String direction)
	{
		if(direction == "right" && World.Space_free((int)(this.getX() + Math.ceil( speed- HungerBonus)) , this.getY(), 0 ,0,z ))
		{
			
			right = true;
			left = false;
			down = false;
			up= false;
		}
		else
		{
			if(direction == "left" && World.Space_free((int)(this.getX() - Math.ceil( speed  -HungerBonus)), this.getY(),0, 0,z))
			{
				left = true;
				right = false;
				down = false;
				up= false;
			}

		}
		
		if(direction == "up" && World.Space_free(this.getX() , (int)(this.getY() - Math.ceil(speed -HungerBonus)) ,0, 0,z))
		{
			down = false;
			up= true;
			left = false;
			right = false;
		}
		else
		{
			if(direction == "down" && World.Space_free(this.getX(), (int)( this.getY() +  Math.ceil(speed -HungerBonus)),0, 0,z))
			{
				down = true;
				up= false;
				left = false;
				right = false;
			}

		}
	}
	
	public void SpaceCommand()
	{

	
	}
	

	public void NormalMove()
	{
		if(right && World.Space_free((int)(this.getX() + Math.ceil( speed- HungerBonus)) , this.getY(), 0 ,0,z ))
	{
		x+= Math.ceil(speed - HungerBonus);
		
		CurrentSprite = this.rightPlayer[index];
		dir = right_dir;
		moved = true;
	}
	else
	{
		if(left && World.Space_free((int)(this.getX() - Math.ceil( speed  -HungerBonus)), this.getY(),0, 0,z))
		{
			x-= Math.ceil(speed - HungerBonus);
			CurrentSprite = this.leftPlayer[index];
			dir = left_dir;
			moved = true;
		}

	}
	
	if(up && World.Space_free(this.getX() , (int)(this.getY() - Math.ceil(speed -HungerBonus)) ,0, 0,z))
	{
		y-= Math.ceil(speed - HungerBonus);
		CurrentSprite = this.upPlayer[index];
		dir = up_dir;
		moved = true;
	}
	else
	{
		if(down && World.Space_free(this.getX(), (int)( this.getY() +  Math.ceil(speed -HungerBonus)),0, 0,z))
		{
			y+= Math.ceil(speed - HungerBonus);
			CurrentSprite = this.downPlayer[index];
			dir = down_dir;
			moved = true;
		}

	}
	
	if(moved)
	{
		Particles particles = new Particles(20,this.getX(), this.getY(),2);
		Game.p.add(particles);
		particles.ActivateNoise(this.getX()  , this.getY() );
		
		particles.pixelnumber = 1;
		particles.Interval =  1;
		
		particles.Colors[0] = Color.white;
		particles.Colors[1] = Color.LIGHT_GRAY;	
		
		this.framesound--;
		if(this.framesound <= 0)
		{
			Sound.movesound.Play();
			framesound = this.maxsoundframe ;
		}
		
		frames++;
		if(frames == maxframes)
		{

			frames = 0;
			index++;
			if(index == maxindex)
			{
				index = 0;
			}
		}
	}
	}
	
	
	
	

	
}
