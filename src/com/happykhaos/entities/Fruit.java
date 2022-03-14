package com.happykhaos.entities;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.happykhaos.main.Game;
import com.happykhaos.main.MommyBird;
import com.happykhaos.main.Sound;

import Graphics.Particles;

public class Fruit extends Entity{

	
	
	public Fruit(int x, int y, int type) {
		super(x, y, 12, 12, null, true);
		// TODO Auto-generated constructor stub
		
		switch(type)
		{
		case 0:
		this.sprite= 	Game.ssheet.getSprite(51	, 1, 9, 11);
			break;
			
			
		case 1:
			this.sprite= 	Game.ssheet.getSprite(81, 17, 12, 12);
			break;
			
		case 2:
			this.sprite= 	Game.ssheet.getSprite(97, 17, 14, 12);
			break;
			
		case 3:
			this.sprite= 	Game.ssheet.getSprite(113, 17, 12, 12);
			break;
			
		}
	}
	
	
	public void Tick()
	{
		if(isColliding(this, Game.player))
		{
			if(Player.fruitsCollected < MommyBird.Carry)
			{
				Game.entities.remove(this);
				Player.fruitsCollected ++;
				Particles p = new Particles(20,this.getX(), this.getY() , 2);
				p.MakeExplosion(20);
				p.Colors[0] = new Color(255,72,0);
				p.Colors[1] = new Color(50,255,0);
				Game.p.add(p);
				Sound.hurtSound.Play();				
			}

			
		}
	}
	
	

}
