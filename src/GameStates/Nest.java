package GameStates;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.happykhaos.entities.Birdie;
import com.happykhaos.entities.Player;
import com.happykhaos.main.Game;
import com.happykhaos.main.MommyBird;
import com.happykhaos.main.OptionMenu;
import com.happykhaos.main.Text;
import com.happykhaos.world.Season;

import Graphics.Particles;

public class Nest {

	

	public OptionMenu opts;
	public Text fruitCounter;
	public Text seasonCount;
	
	
	public static List<Particles> displayBoxes = new ArrayList<Particles>();
	public static Particles currentBox;
	
	public static Birdie[] birdies;
	
	public static MommyBird mom;
	
	public Nest()
	{
		String[] t = {"GO LOOK FOR FOOD", "GO TO SHOP"};
		opts = new OptionMenu( t, new Color(20,200,50),new Color(20,200,50),new Color(20,200,50),new Color(20,20,50));
		opts.option[0].InsertValues(Game.WIDTH - 160, Game.HEIGHT - 35, 115, 30, 15, null);
		opts.option[0].text.textColor = new Color(100,20,20);
		opts.option[0].text.setOffset(5);
		opts.option[0].text.y += 20;
		opts.option[0].text.AddOutline(Color.black);
		
		
		opts.option[1].InsertValues(0, Game.HEIGHT - 35, 115, 30, 15, null);
		opts.option[1].text.textColor = new Color(100,20,20);
		opts.option[1].text.setOffset(5);
		opts.option[1].text.y += 20;
		opts.option[1].text.AddOutline(Color.black);
		
		//opts.ChangeTextSize(40);
		fruitCounter = new Text(0, 15,27,Color.magenta, 20);
		fruitCounter.ChangeFont(Game.optionFont);
		fruitCounter.AddOutline(Color.black);
		seasonCount =  new Text(0,Game.WIDTH / 2 + 20, 27,Color.orange, 20);
		seasonCount.AddOutline(Color.black);
		seasonCount.ChangeFont(Game.optionFont);

		
		birdies =  new Birdie[5];
		
		
		mom = new MommyBird(Game.WIDTH + 20, 50,40,40);
		for(int i = 0; i < birdies.length; i++)
		{
			BufferedImage b = null;
			String name = "";
			Color c = null;
			int yd = 0;
			switch(i)
			{
			case 0:
				b = Game.ssheet.getSprite(97, 1, 13, 13);
				name = "Peach";
				c = new Color(255, 17,61);
				yd = 20;
				break;
			case 1:
				b = Game.ssheet.getSprite(113, 1, 13, 13);
				name = "Lina";
				c = Color.magenta;
				break;
				
				
			case 2:
				b = Game.ssheet.getSprite(129, 1, 13, 13);
				name = "Nommy";
				c = Color.orange;
				break;
				

			case 3:
				b = Game.ssheet.getSprite(145, 1, 13, 13);
				name = "Rippa";
				c = Color.red;
				break;
				
			case 4:
				b = Game.ssheet.getSprite(161, 1, 13, 13);
				name = "Kiwi";
				c = Color.blue;
				break;
			}
			
			
			birdies[i] = new Birdie(40 + i * 50,165 + yd,30,30,b, this);
			birdies[i].name = name;
			birdies[i].nameText.textColor = c;
			
		}
		
	}
	
	
	public void NextTurn()
	{
		Game.ui.Save();
		Season.PassSeason();
		transitionFrames = 0;
		transitionState = 0;
	mom.setX(Game.WIDTH - 20);
	mom.setY(50);
		for(int i = 0; i < birdies.length; i++)
		{
			

			birdies[i].hungerCurrent --;
			if(birdies[i].hungerCurrent < 0)
			{
				birdies[i].hungerCurrent = 0;
				birdies[i].show = false;
			}
			if(birdies[i].hungerCurrent < 2 )
			{
				int t = Game.rand.nextInt(2);
				int z = 0;
				String s = "";
				switch(t)
				{
				case 0:
					s = "I am hungry!";
					z= 20;
					break;
					
				case 1:
					s = "GIMME FOOD!";
					z =20;
					break;
				}
				birdies[i].Box(s,150,z);
			}
		
			
			
		
			
			
			
		}
		
	}
	
	
	public void MouseOver(int x, int y)
	{
		opts.OnMouseOver(x, y);
	}
	
	
	public float transitionFrames = 0;
	public int transitionState = 0;
	public int xBirdDestination = Game.WIDTH - 60, yBirdDestination = 160;
	
	public void Tick()
	{
		
		if(transitionState == 1)
		{
			if(this.mom.getX() > xBirdDestination)
			{
				mom.setX(mom.getX() - 1);
			}
			else
				{
				if(this.mom.getX() < xBirdDestination)
				{
					mom.setX(mom.getX() + 1);
				}
			}
			
			if(this.mom.getY() > yBirdDestination)
			{
				mom.setY(mom.getY() - 1);
			}
			else
				{
				if(this.mom.getY() < yBirdDestination)
				{
					mom.setY(mom.getY() + 1);
				}
			}
			
			
			
		}
		
		
		if(displayBoxes.size() != 0 && (this.currentBox == null || this.currentBox.life == 0)) {
			this.currentBox = displayBoxes.remove(0);
			Game.p.add(this.currentBox);
		}
		
	}
	
	public void Render(Graphics g)
	{
		Tick();
		Graphics2D g2 = (Graphics2D)g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,transitionFrames);
		g2.setComposite(ac);
		
		if(transitionState == 0)
		{
			if(!(transitionFrames + 0.01f >= 1))
			{
				transitionFrames += 0.01f;

			}
			else
			{
				transitionState = 1;
			}
		}
		else
		{
			if(transitionState == 2)
			{
				if((transitionFrames - 0.01f >= 0))
				{
					transitionFrames -= 0.01f;

				}
				else
				{
					transitionState = 1;
					Game.Reset(0);
					Game.player.setX(Game.player.getX() + 10);
			
				}
			}
			else
			{
				if(transitionState == 3)
				{
					
					if((transitionFrames - 0.01f >= 0))
					{
						transitionFrames -= 0.01f;

					}
					else
					{
						transitionState = 1;
						Game.p.clear();
						Game.GameState = "SHOP";
					}
				}
			}
		}

		
		g.setColor(new Color(100,100,255));
		//g.fillRect(0,0,Game.WIDTH, Game.HEIGHT);
		g2.drawImage(Game.backgroundTree, 0,0,Game.WIDTH, Game.HEIGHT, null);
		
		g.setColor(Color.yellow);
		g.fillRect(Game.WIDTH / 2 + 20, 30,40,10);
		g.setColor(Color.blue);
		g.fillRect(Game.WIDTH / 2 + 60, 30,80,10);
		if(Season.returnState() == "Summer")
		{
			g.setColor(new Color(250,80,20));			
		}
		else
		{
			g.setColor(new Color(100,200,200));	
		}

		g.fillRect(Game.WIDTH / 2 + Season.CurrentMonth * 10 + 20 , 30, 10,10);
		
		for(int i = 0; i < birdies.length; i++)
		{
			birdies[i].Render(g);
			
		}
		
		mom.Render(g);
		//g.setColor(new Color(20, 20, 100));

        opts.RenderOptions(g);
        fruitCounter.RenderText((Graphics2D) g, "food:"+ Player.food);
        seasonCount.RenderText((Graphics2D) g, "SEASON:");

		
	}
	
	
	public void showDisplayBox(Particles box) {
		displayBoxes.add(box);
	}
	
	public void ConfirmClick(int x, int y)
	{
	
			if(transitionState != 1)
			{
				return;
			}
		for(int i = 0; i <birdies.length; i++)
		{
			birdies[i].CheckClick(x, y);
		}

		
		int b = opts.ReturnOption(x, y);
		
		switch(b)
		{
		case 0:
			UI.ButtonClicked();
			
			this.transitionState = 2;
			break;
			
			
		case 1:
			UI.ButtonClicked();
			
			this.transitionState = 3;
			break;
			
		}
		
		
	}
}