package com.happykhaos.main;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.happykhaos.entities.Birdie;
import com.happykhaos.entities.Entity;

public class MommyBird extends Entity{

	
	
	public MommyBird(double x, double y, int width, int height) {
		super(x, y, width, height, Game.ssheet.getSprite(17, 1, 	12, 12), false);
		// TODO Auto-generated constructor stub
		//this.nameText.textColor = Color.magenta;
	}
	// this class will be used for mommy bird stats
	
	public static int Carry = 9;
	public static int bonus_fruits;
	
	public void Box()
	{
		
	}
	
	//public static int CurrentHunger = 3;
	
	


	
}
