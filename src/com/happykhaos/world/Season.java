package com.happykhaos.world;

import com.happykhaos.entities.Player;
import com.happykhaos.main.Game;
import com.happykhaos.main.MommyBird;

public class Season {

	
	
	public static int CurrentMonth;
	
	
	public static final int SummerEnd = 4;
	public static final int WinterEnd = 11;
	
	public static void PassSeason()
	{
		CurrentMonth ++;
		if(CurrentMonth > WinterEnd)
		{
			CurrentMonth = 0;
		}
	}
	
	
	public static String returnState()
	{
		if(CurrentMonth < SummerEnd)
		{
			return "Summer";
		}
		else
		{
			return "Winter";
		}
	}
	
	public static void ActEffects()
	{
		if(CurrentMonth < SummerEnd )
		{
			MommyBird.Carry = 8;
			Game.player.speed =1.5f;
		}
		else
		{
			MommyBird.Carry = 6;
			Game.player.speed = 1f;
		}
		
		
		
	}
	
	
}
