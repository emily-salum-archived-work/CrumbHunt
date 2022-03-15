package com.happykhaos.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.happykhaos.main.Camera;
import com.happykhaos.main.Game;
import com.happykhaos.main.Text;

import GameStates.Nest;
import Graphics.Particles;


public class Birdie extends Entity{
	
	
	public Birdie(double x, double y, int width, int height, BufferedImage sprite, Nest nest) {
		super(x, y, width, height, sprite, false);
		// TODO Auto-generated constructor stub
		
		nameText = new Text(0,(int)x, (int)y + 45, Color.black, 20f);
		nameText.AddOutline(Color.black);
		
		
		this.nest = nest;
	}
	public static int maximumHunger = 5;
	public int hungerCurrent = 3;
	public String name = "";
	public boolean show = true;
	
	public Nest nest;
	
	public Text nameText;
	
	public void Tick()
	{
		boxInterval++;
	}
	
	
	public void CheckClick(int x, int y)
	{
		if(!show)
		{
			return;
		}
		
		if((x >=this.getX()  ) && (x <= this.getX()   +this.getWidth() ))
		{
			if((y >= this.getY() ) && (y <=this.getY()   + this.getHeight()  ))
			{
				if(hungerCurrent < maximumHunger)
				{
					if(Player.food > 0)
					{
						this.hungerCurrent ++;
						Player.food --;
						
						
						Particles p = new Particles(20, this.getX() + this.getWidth() - 2, this.getY(), 0);
						p.ActivateIcon(Game.ssheet.getSprite(177, 1, 12, 12), 0,-1,10,10);
						Game.p.add(p);
		
						
					}
		
				}
				else
				{
					if(boxInterval > 60)
					{
						Box("I am full!", 60, 15);
								boxInterval = 0;
					}

				}
		
			}
		}
	}
	
	
	private int boxInterval;
	
	public void Box(String text, int duration, int size)
	{
		if(!show)
		{
			return;
		}
		Particles box = new Particles(duration, this.getX() - 20, this.getY() - 20 , 0);
		box.ActivateIcon(Game.ssheet.getSprite(192, 17, 24, 24), 0,-1,140,50);
		box.ActivateText(33, text, c);
		box.text.x += 20;
		box.text.y += 20;
		box.text.fontSize = size;
		
		box.text.AddOutline(Color.black);
		this.nest.displayBoxes.add(box);
	}
	
	public Color c;
	public void Render(Graphics g)
	{
		if(!show)
		{
			return;
		}
		
		Tick();
		
		
	//	super.Render(g);
		
		
		 g.drawImage(sprite, this.getX(), this.getY() , this.getWidth(), this.getHeight(), null);
		
		g.setColor(Color.black);
		g.fillRect(this.getX() , this.getY() + 50, 10, 5);

		
		nameText.RenderText((Graphics2D) g, name);
		//g.setColor(Color.white);		
		for(int i = 0; i < hungerCurrent; i++)
		{

			g.fillRect(this.getX() + i * 2 , this.getY() + 50, 2, 5);
		}
		c = g.getColor();
		
	}
	
	
	
	

}
