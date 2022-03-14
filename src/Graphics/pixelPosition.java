package Graphics;

import com.happykhaos.main.Game;

public class pixelPosition {

	
	public int x,y;
	
	public double dx,dy;
	public double speed = 1;
	
	public void MovePixel()
	{
		
		float r = Game.rand.nextFloat();
		x += dx * speed + r;
		y += dy* speed + r;
	}
	
}
