package com.happykhaos.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.happykhaos.main.Game;

import Graphics.Particles;

public class Fruitspawn extends Entity{

	public Fruitspawn(double x, double y, int type) {
		super(x, y, 8, 8, null, true);
		// TODO Auto-generated constructor stub
		
		switch(type)
		{
		case 0:
		this.sprite= 	Game.ssheet.getSprite(84, 1, 9, 11);
			break;
			
			
		case 1:
			this.sprite= 	Game.ssheet.getSprite(81, 33, 12, 12);
			break;
			
		case 2:
			this.sprite= 	Game.ssheet.getSprite(97, 34, 14, 12);
			break;
			
		case 3:
			this.sprite= 	Game.ssheet.getSprite(113, 34, 12, 12);
			break;
			
		}
		
		Particles p = new Particles(20,this.getX(), this.getY(), 2);
		p.MakeExplosion(20);
		p.Colors[0] = new Color(78, 182, 0);
		p.Colors[1] = new Color(50, 255, 0);
		Game.p.add(p);
		this.type = type;
	}
	
	private int blink;
	private int type;
	public void Render(Graphics g) {
		blink+= 4;
		if(blink >= 10)
		{
			blink -- ;
			super.Render(g);
		}
	
	}
	private int spawn;
	public void Tick()
	{
		spawn ++;
		if(spawn > 60)
		{
			Game.entities.remove(this);
			Fruit fruit = new Fruit(this.getX(), this.getY(), type);
			Game.entities.add(fruit);
		}
	}
	
	
	
	
	

}
