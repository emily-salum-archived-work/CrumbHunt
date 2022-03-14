package com.happykhaos.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.happykhaos.main.Camera;
import com.happykhaos.main.Game;

public class Tile 
{
	
	public static BufferedImage tile_FLOOR = Game.ssheet.getSprite(17,17,16,16);
	public static BufferedImage tile_FLOOR_ICE = Game.ssheet.getSprite(17,33,16,16);	
	
	public static BufferedImage tile_WALL = Game.ssheet.getSprite(33,17,16,16);
	public static BufferedImage tile_WALL_ICE = Game.ssheet.getSprite(33,33,16,16);
	
	protected BufferedImage sprite;
	protected int x;
	protected int y;
	public boolean visible = true;
	
	
	public Tile(int x, int y, BufferedImage sprite)
	{
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void Tick()
	{
	
	
	}
	
	public void Render(Graphics g)
	{
	
			g.drawImage(sprite, x - Camera.x,  y - Camera.y,null);		
		
	
	
		

	}
	
	
	
	public int GetX()
	{
		return x;
	}
	
	
	public int GetY()
	{
		return y;
	}
	
}
