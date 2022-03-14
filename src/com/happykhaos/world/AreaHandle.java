package com.happykhaos.world;

import java.awt.image.BufferedImage;

import com.happykhaos.entities.Entity;
import com.happykhaos.entities.Player;
import com.happykhaos.main.Game;

public class AreaHandle extends Entity{

	public AreaHandle(double x, double y) {
		super(x, y, World.TILE_SIZE, World.TILE_SIZE, null, true);
		// TODO Auto-generated constructor stub
	}
	
	
	public void Tick()
	{
		if(isColliding(this, Game.player))
		{
			if(!Player.SpaceTransition)
			{
				Game.GameState = "ENDRUN";
				Player.SpaceTransition = true;
				
			}
		}
		else
		{
			Player.SpaceTransition = false;
		}
	}

}
