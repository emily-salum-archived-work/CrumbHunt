package com.happykhaos.main;

import com.happykhaos.entities.Entity;
import com.happykhaos.world.World;

public class Camera {
	public final static int x = -60, y = -60;
	
	public static Entity character;
	public static boolean Transition;

	public static boolean TransitioncanEnd;
	
	/*public static void followFocus()
	{
		
		
		
		boolean moved = false;
		if(x > character.getX() + Game.WIDTH / 2)
		{
			x --;
			moved = true;
		}
		else
		{
			if(x < character.getX() - Game.WIDTH / 2)	
			{
				moved = true;
				x++;
			}
		}
		
		if(y > character.getY() + Game.HEIGHT / 2)
		{
			y --;
			moved = true;
		}
		else
		{
			if(y < character.getY() - Game.HEIGHT / 2)	
			{
				moved = true;
				y++;
			}
		}
		
		
		if(!moved)
		{
			
			TransitioncanEnd = true;
		}
		
		
	}*/
	
	public static void StartTransition(Entity focus)
	{
		
		
	
		Transition = true;
		character = focus;
	}
	
	public static int clamp(int Atual, int Min, int Max)
	{
		if(Atual < Min)
		{
			Atual = Min;
		}

			if(Atual > Max)
			{
				Atual = Max;
			}
		
		return Atual;
		
	}

}
