package com.happykhaos.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.happykhaos.enemyList.Enemy;
import com.happykhaos.main.Camera;
import com.happykhaos.main.Game;
import com.happykhaos.main.Node;
import com.happykhaos.main.Vector2i;
import com.happykhaos.world.WallTile;
import com.happykhaos.world.World;

public class Entity 
{
	
	
	
	
	 protected double x;
	 protected double y;
	 public int z = 0;
	
	 protected int width;
	 protected int height;
	 
	 public float speed;
	 
	 protected BufferedImage sprite;
	 public boolean cameraControlled;
	 
	 public int depth = 0;
	 
	 public List<Node> path;
	 
	 
	 public Entity(double x, double y, int width, int height, BufferedImage sprite, boolean cameraControlled)
	 {
		 this.x = x;
		 this.y = y;
		 this.height = height;
		 this.width = width;
		 this.sprite = sprite;
		 this.cameraControlled = cameraControlled;
	 }
	 
	 
	 public static Comparator<Entity> depthSorter = new Comparator<Entity>()
	 {
		 
		 @Override
		 
		 public int compare(Entity e1, Entity e2)
		 {
			 if(e1.depth > e2.depth)
			 {
				 return 1;
			 }
			 
			 if(e1.depth < e2.depth)
			 {
				 return -1;
		
			 }
			 return 0;
		 }
		 
	 }
	 ;
	 
	 public void FollowPath(List<Node> path)
	 {
		 if(path != null)
		 {
			if(path.size() > 0)
			{
	
				Vector2i target = path.get(path.size() - 1).tile;
				//int xprev = target.x;
				//int yprev = target.y;
				
				if(this.getX() < target.x * World.TILE_SIZE && (!isColliding((int)(this.getX() + speed), this.getY())))
				{
					if(! (this.getX() + speed > target.x * World.TILE_SIZE))
					{

						x += (int)speed;
					}
					else
					{
						x += target.x * World.TILE_SIZE - this.getX(); 
					}
	
				}
				else
				{
					if(this.getX() > target.x * World.TILE_SIZE && (!isColliding((int)(this.getX() - speed), this.getY())))
					{
						
						if(! (this.getX() - speed < target.x * World.TILE_SIZE))
						{

							x -= (int)speed;
						}
						else
						{
							x -= this.getX() - target.x * World.TILE_SIZE; 
						}
					}
				}
				
				
				if(this.getY() < target.y * World.TILE_SIZE && !isColliding(this.getX(), (int)(this.getY() + speed)))
				{
					if(! (this.getY() + speed > target.y * World.TILE_SIZE))
					{

						y += (int)speed;
					}
					else
					{
						y += target.y * World.TILE_SIZE - this.getY(); 
					}
				}
				else
				{
					if(this.getY() > target.y * World.TILE_SIZE && (!isColliding(this.getX(), (int)(this.getY() - speed))))
					{
						if(! (this.getY() - speed < target.y * World.TILE_SIZE))
						{
							y -= (int)speed;
						}
						else
						{
							y -= this.getY() - target.y * World.TILE_SIZE  ; 
						}

					}
				}
				
				
				if(x == target.x * World.TILE_SIZE && y == target.y * World.TILE_SIZE)
				{
					path.remove(path.size() - 1);
				}
				
				
				
				
				
			}
		 }
	 }
	 
	 public Rectangle GetRectangle()
	 {
		 return new Rectangle(this.getX() , this.getY() , this.getWidth(), this.getHeight());
	 }
			 
	 public void Render(Graphics g)
	 {
		 
		 int renderX = this.getX();
		 
		
		 int renderY = this.getY();
		 
		 if(this.cameraControlled) {
			 renderX -= Camera.x;
			 renderY -= Camera.y;
		 } 
		 
		 g.drawImage(sprite, renderX, renderY, this.getWidth(), this.getHeight(), null);
		 
		 
	 } 
	 
	 public static double DistanceOf(int x1, int y1, int x2, int y2)
	 {
		 
		 return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) );
	 }
	 
	 
	 public static boolean BetweenWalls(int x1, int y1, int x2, int y2)
	 {

		 
		 return false;
	 }
	 
		public boolean isColliding(int x, int y)
		{
			
			Rectangle EnemyCurrent = new Rectangle(x,y ,this.getWidth(), this.getHeight());
			
			for(int i = 0; i < Game.enemies.size(); i++)
			{
				Enemy en= Game.enemies.get(i);
				if(en == this)
				{
					continue;
				}
				
				if(EnemyCurrent.intersects(en.GetRectangle()))
				{
					 return true;
				}
				
				
				
			}
			
			return false;
		}
	 
	 public static boolean isColliding(Entity e1, Entity e2)
	 {
		 Rectangle e1mask = new Rectangle(e1.getX(), e1.getY(), e1.getWidth(), e1.getHeight());
		 Rectangle e2mask = new Rectangle(e2.getX(), e2.getY(), e2.getWidth(), e2.getHeight());
		 
		 return e1mask.intersects(e2mask);
	 }
	 
	 public void Tick()
	 {
		 
	 }
	 
	 
	 public void setX(int newX)
	 {
		 this.x = newX;
	 }
	 
	 public void setY(int newY)
	 {
		 this.y = newY;
	 }
	 
	 
	 public void setWidth(int newWidth)
	 {
		 this.width = newWidth;
	 }
	 
	 public void setHeight(int newHeight)
	 {
		 this.height = newHeight;
	 }
	 
	 
	 public int getX()
	 {
		 return (int)this.x;
	 }
	 
	 public int getY()
	 {
		 return (int)this.y;
	 }
	 
	 
	 public int getWidth()
	 {
		 return this.width;
	 }
	 
	 public int getHeight()
	 {
		 return this.height;
	 }
	 
	 
	
}
