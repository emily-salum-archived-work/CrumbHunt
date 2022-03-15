package GameStates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.happykhaos.entities.Entity;
import com.happykhaos.entities.Player;
import com.happykhaos.main.Game;
import com.happykhaos.main.Button;
import com.happykhaos.main.OptionMenu;
import com.happykhaos.main.Text;

public class ShopItem extends Entity{

	public ShopItem(String name, int level, double x, double y, int width, int height) {
		super(x, y, width, height, null, false);

		
	this.name = name;
		

	
		text = new Text(0, this.getX()  + 200, this.getY() - 10, Color.white, 16);
		text.AddOutline(Color.black);

		nameText = new Text(0, this.getX(), this.getY() - 10, Color.white, 15);
		nameText.AddOutline(Color.black);
		nameText.ChangeFont(Game.optionFont);
		button = new Button();
		switch(name)
		{
		case "life":
			nameText.textColor = Color.red;
			text.textColor = Color.red;
			this.sprite = Game.ssheet.getSprite(210, 66, 15, 19);
			
			button.InsertValues(130 , 35 , 115, 30, 15, null);
			button.text.textColor = new Color(100,200,210);
			button.text.setOffset(20);
			button.text.y += 20;
			button.text.AddOutline(Color.black);
			standardCost = 3;
			
			break;
			
		case "speed":
			nameText.textColor = Color.yellow;	
			text.textColor = Color.yellow;	
			this.sprite = Game.ssheet.getSprite(227, 66, 15, 19);
			
			button.InsertValues(130 , 100 , 115, 30, 15, null);
			button.text.textColor = new Color(120,60,200);
			button.text.setOffset(20);
			button.text.y += 20;
			button.text.AddOutline(Color.black);
			
			standardCost = 4;
			break;
			
		case "carry":
			nameText.textColor = Color.orange;
			text.textColor = Color.orange;
			this.sprite = Game.ssheet.getSprite(242, 67, 15, 19);
			
			button.InsertValues(130 , 165 , 115, 30, 15, null);
			
			button.text.textColor = new Color(100,100,200);
			button.text.setOffset(20);
			button.text.y += 20;
			button.text.AddOutline(Color.black);
			
			standardCost = 6;
			
			break;
			
		case "bonus fruits":
			nameText.textColor = Color.magenta;
			nameText.changeX(-50);
			text.textColor = Color.magenta;
			this.sprite = Game.ssheet.getSprite(260, 66, 14, 19);
			
			button.InsertValues(130 , 230 , 115, 30, 15, null);
			
			button.text.textColor = new Color(100,100,200);
			button.text.setOffset(20);
			button.text.y += 20;
			button.text.AddOutline(Color.black);
			
			standardCost = 7;
			
			
			break;
		}
	

		
		


		
		this.level = level;
		cost = standardCost * level;
	
	}
	
	
	public String name;
	public int level;
	public int cost , standardCost = 1;
	public Text text;
	public Text nameText;
	public Button button;

	public OptionMenu opts;
	
	public void CheckClick(int x, int y)
	{
		if((x >=button.getLocationX()  ) &&
				(x <= button.getLocationX()   + button.getSizeX() ))
		{
			if((y >= button.getLocationY() ) && (y <=button.getLocationY()   + button.getSizeY() ))
			{
				if(Player.food >= cost)
				{
					Buy();
				}
			
			}
		}
	}
	
	public void Buy()
	{
		if(Player.food >= cost)
		{
			level ++;
			Player.food -= cost;
			UpdateCost();
		}
	}
	
	public void UpdateCost()
	{
		cost = standardCost * level;
	}
	
	public void Render(Graphics g)
	{
	
		//super.Render(g);
		
		
		for(int i = 0; i < level; i++)
		{
			g.drawImage(sprite, this.getX() + i * 10, this.getY() , this.getWidth(), this.getHeight(), null);
		}
	

		text.RenderText((Graphics2D) g, "costs: "+cost + " ");
		
		
		
		
		nameText.RenderText((Graphics2D) g,  name);
		
		//g.setColor(Color.black);
		button.RenderButton(g);
		button.text.RenderText((Graphics2D) g, "UPGRADE");
		//g.drawImage(sprite, this.getX() + 80, this.getY() - 30 , this.getWidth(), this.getHeight(), null);
	}
	
}
