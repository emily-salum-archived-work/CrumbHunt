package Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.happykhaos.entities.Entity;
import com.happykhaos.main.Camera;
import com.happykhaos.main.Game;
import com.happykhaos.main.Text;

public class Particles extends Entity{

	public Particles(int lifeTime, double x, double y, int colors) {
		super(x, y, 1, 1, null, true);
		this.Colors = new Color[colors];
		this.lifeTime = lifeTime;
		// TODO Auto-generated constructor stub
	}



	
	private int FollowX, FollowY;
	
	public boolean Destroy;
	public int life, lifeTime ;
	
	public boolean Noise, Explosion, simpleRender, IconRender;
	
	
	public BufferedImage box;
	public Text text;
	public String stringtext;

	private double dx;
	private double dy;
	public int sizex,sizey;
	
	private pixelPosition[] px;
	
	public Color[] Colors;
	
	
	
	
	public void ActivateIcon(BufferedImage b, int dx, int dy, int sizex, int sizey)
	{
		box = b;
		this.dx = dx;
		this.dy = dy;
		this.sizex = sizex;
		this.sizey = sizey;
		IconRender = true;
	}
	
	
	public void ActivateText(int size, String text, Color c)
	{
		this.text = new Text(0,this.getX(), this.getY(),c,size);
		stringtext = text;
		
		
	}
	
	
	
	public void Tick()
	{
		life++;
		
		this.setX((int) (this.getX() + dx));
		this.setY((int) (this.getY() + dy));
		if(life > lifeTime)
		{
			 Game.p.remove(this);
			 life = 0;
		}
	}
	
	public void IconRender(Graphics g)
	{
		g.drawImage(box, this.getX(), this.getY(),sizex,sizey,null);
		
	
	}
	
	
	public void Render(Graphics g)
	{
	
		if(IconRender)
		{
			IconRender(g);
		}
		
		if(Noise)
		{
			Noise(g);
		}
		
		if(Explosion)
		{
			Explosion(g);
		}
		
		if(simpleRender)
		{
			g.setColor(Colors[Game.rand.nextInt(Colors.length )]);
			g.drawRect(this.getX() - Camera.x, this.getY() - Camera.y, 1, 1);
		}
		
		if(text != null)
		{
			text.RenderText((Graphics2D) g, stringtext);
			text.ChangeY((int) dy);
		}
		
	}
	
	

	
	public void Explosion(Graphics g)
	{


		for(int i = 0; i < px.length;i++)
		{
			px[i].MovePixel();
		
			
			g.setColor(Colors[Game.rand.nextInt(Colors.length )]);
			g.fillRect((int)(px[i].x - Camera.x )  , (int)px[i].y - Camera.y, 1 ,1);			
		}

		
	}
	

	
	

	
	public void MakeExplosion(  int quantity)
	{
		Explosion = true;
		
	
		
		px = new pixelPosition[quantity];
		
		for(int i = 0; i < px.length;i++)
		{
			px[i] = new pixelPosition();
			
			px[i].x = this.getX();
			px[i].y = this.getY();
			px[i].speed += Game.rand.nextFloat();	
		dx = Game.rand.nextGaussian();
		dy = Game.rand.nextGaussian();
			px[i].dx = dx;
			px[i].dy = dy;
		
		
		}
			
	}

	
	
	
	
	private int noisepix;
	private int pixelposition;
	public int pixelnumber = 5;
	public int Interval = 1;
	
	public void ActivateNoise(int x, int y)
	{
		Noise = true;
		FollowX = x;
		FollowY = y;
	}
	
	private void Noise(Graphics g)
	{
		
		noisepix++;
		if(noisepix == Interval )
		{
			noisepix = 0;
			pixelposition ++;
			if(pixelposition >= 18)
			{
				pixelposition = 5;
			}
			for(int i = 0; i < pixelnumber; i++)
			{
				pixelposition ++;
				if(pixelposition >= 18)
				{
					pixelposition = 5;
				}
				
				int d = Game.rand.nextInt(2) - Game.rand.nextInt(3);
				
				
				for(int b = 0; b < Colors.length; b++)
				{
					d = Game.rand.nextInt(4) - Game.rand.nextInt(8);
					g.setColor(Colors[b]);
					g.fillRect(FollowX - Camera.x + (int)pixelposition  , FollowY - Camera.y + 5 + i + d  ,1,1);
				}
				
					
				
				
				

			}
		}
	}
	
	
}
