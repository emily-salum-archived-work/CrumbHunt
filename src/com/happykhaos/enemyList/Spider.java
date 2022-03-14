package com.happykhaos.enemyList;

import java.awt.image.BufferedImage;

import com.happykhaos.main.Game;

public class Spider extends Enemy{

	public Spider(double x, double y, int width, int height,  float speed, BufferedImage attack, int moves) {
		super(x, y, width, height, speed, attack, moves);
		// TODO Auto-generated constructor stub
		
		this.normalSprite =  Game.ssheet.getSprite(193, 1, 14, 14);
		this.spriteatual = this.normalSprite;
		 z = 360;
		 w = width;
		 h = height;
	}
	
	public int w, h;
	
	public void Tick()
	{
		if(z == 0)
		{
			super.Tick();
		}
		else
		{
			z --;
			this.setHeight(h + z / 20);
			this.setWidth(w + z / 20);
		}
	}
	
	

}
