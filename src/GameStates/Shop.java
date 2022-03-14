package GameStates;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.happykhaos.main.Game;
import com.happykhaos.main.OptionMenu;
import com.happykhaos.main.Text;

public class Shop {
	
	
	public OptionMenu opts;
	public Text fruitCounter;
	
	public Shop()
	{
		items = new ShopItem[4];
		
		String[] t = {"GO LOOK FOR FOOD", "GO BACK TO NEST"};
		opts = new OptionMenu( t, new Color(20,200,50),new Color(20,200,50),new Color(20,200,50),new Color(20,20,50));
		opts.option[0].InsertValues(Game.WIDTH - 120, Game.HEIGHT - 35, 115, 30, 15, null);
		opts.option[0].text.textColor = new Color(100,20,20);
		opts.option[0].text.setOffset(5);
		opts.option[0].text.y += 20;
		opts.option[0].text.AddOutline(Color.black);
		
		
		opts.option[1].InsertValues(Game.WIDTH - 300, Game.HEIGHT - 35, 115, 30, 15, null);
		opts.option[1].text.textColor = new Color(100,20,20);
		opts.option[1].text.setOffset(5);
		opts.option[1].text.y += 20;
		opts.option[1].text.AddOutline(Color.black);
		


		
		
		
		
		
		fruitCounter = new Text(0, 15,17,Color.magenta, 20);
		fruitCounter.ChangeFont(Game.optionFont);
		fruitCounter.AddOutline(Color.black);
		
		for(int i = 0; i < items.length; i++)
		{
			String name = null;
			
			switch(i)
			{
			case 0:
				name = "life";
				break;
				
			case 1:
				name = "speed";
				break;
				
			case 2:
				name = "carry";
				break;
				
			case 3:
				
				name = "bonus fruits";
				break;
			}
			// make sprites
			items[i] = new ShopItem(name, 1, 55 , 64 * (i + 1) , 20, 20);
			items[i].text.ChangeFont(Game.optionFont);
			
			
			
		}

		
	}
	
	public void ConfirmClick(int x, int y)
	{
	
		

		
		int b = opts.ReturnOption(x, y);
		
		if(b != -1)
		{
			UI.ButtonClicked();
		}
		switch(b)
		{
		case 0:
			
			
			Game.Reset(0);
			Game.player.setX(Game.player.getX() + 10);
			break;
			
			
		case 1:
			
			
			Game.GameState = "NEST";
			Game.nest.transitionFrames = 0;
			Game.nest.transitionState = 0;
			break;
		case 2:
			items[0].Buy();
			break;
		case 3:
			items[1].Buy();
			break;
		case 4:
			items[2].Buy();
			break;
		}
		
		
	}
	
	
	public ShopItem ReturnItem(String name)
	{
		
		for(int i = 0; i < items.length; i++)
		{
			if(name == items[i].name)
			{
				return items[i];
			}
		}
		
		return null;
	}
	
	public void Tick()
	{
		
	}
	
	public ShopItem[] items;
	private float transitionFrames;
	
	
	public void ClickItem(int x, int y)
	{
		
		for(int i = 0; i < items.length; i++)
		{
			items[i].CheckClick(x,y);
		}
		
		
	}
	
	
	public void Render(Graphics g)
	{
		
		Graphics2D g2 = (Graphics2D)g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,transitionFrames);
		g2.setComposite(ac);
		
		if(!(transitionFrames + 0.01f >= 1))
		{
			transitionFrames += 0.01f;

		}
	
		g.setColor(Color.green);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		fruitCounter.RenderText((Graphics2D) g, "food:"+ Game.player.food);
		opts.RenderOptions(g2);
		for(int i = 0; i < items.length; i++)
		{
			items[i].Render(g);
		}
		
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1);
		g2.setComposite(ac);
		
	}
	

}
