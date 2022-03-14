package GameStates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.happykhaos.entities.Player;
import com.happykhaos.main.Game;
import com.happykhaos.main.MommyBird;
import com.happykhaos.main.OptionMenu;
import com.happykhaos.main.Sound;
import com.happykhaos.main.Text;
import com.happykhaos.world.Season;

public class UI {


	
	
	
	
	
	public OptionMenu endRun;
	
	public Text continuet;
	
	public Text score;
	
	public UI()
	{
		
		

		String[] t = {"yes", "no"};
			endRun = new OptionMenu(t, new Color(20,200,20),new Color(20,100,20), new Color(200,20,20 ), new Color(20,200,200));
			
			for(int i = 0; i < endRun.option.length; i++)
			{
				endRun.option[i].InsertValues(Game.WIDTH / 2 - 90 + i * 100,Game.HEIGHT - 170, 50, 30, 5, endRun);
				
			}
			endRun.ChangeTextFont(Game.optionFont);
			endRun.ActivateOutline(Color.black);
		score = new Text(20, Game.WIDTH / 2 - 40, Game.HEIGHT - 20, new Color(220, 90, 20), 17);
		score.AddOutline(new Color(100,100,30));
		
		
		continuet = new Text(0,Game.WIDTH / 2 - 130, 70,  new Color(100, 200, 100), 17);
continuet.AddOutline(Color.black);


	}
	
	public void Render(Graphics g)
	{
		
		

	
		

		
		if(Game.GameState == "NORMAL")
		{
			if(score.font != null)
			{
				score.RenderText((Graphics2D) g,  Player.fruitsCollected + "/" + MommyBird.Carry);			
			}
			else
			{
				score.font = Game.newFont;
			}			
		}
		


		
		if(Game.GameState == "ENDRUN")
		{
			g.setFont(Game.newFont);
			g.setColor(new Color(188,44,10));
			g.fillRect(Game.WIDTH / 5 - 20, 30, 250, 250);
			g.setColor(Color.cyan);
			
			endRun.RenderOptions(g);
			continuet.RenderText((Graphics2D) g, "do you wish to go back to the nest?");
			

		}
 
		
	}
	
	public static void ButtonClicked()
	{
		Sound.buttonPress.Play();
	}
	


	public void Save()
	{
		String[] opt1 = {"fruits","season", 
				"PeachHunger", "LinaHunger", "NommyHunger", "RippaHunger", "KiwiHunger"
				,"Speed", "Carry", "Life","bonus_fruits", "firstGame"}; 
		String[] opt2 = {Integer.toString(Player.food),
				Integer.toString(Season.CurrentMonth),
				Integer.toString(Nest.birdies[0].hungerCurrent),
				Integer.toString(Nest.birdies[1].hungerCurrent),
				Integer.toString(Nest.birdies[2].hungerCurrent),
				Integer.toString(Nest.birdies[3].hungerCurrent),
				Integer.toString(Nest.birdies[4].hungerCurrent)
				, Integer.toString(Game.shop.ReturnItem("speed").level)
						, Integer.toString(Game.shop.ReturnItem("carry").level)
						, Integer.toString(Game.shop.ReturnItem("life").level)
						, Integer.toString(Game.shop.ReturnItem("bonus fruits").level)
						,Integer.toString(Game.firstGame ? 1 : 0)};
		
		SaveGame(opt1, opt2, 3);

	}
	
	public void PauseOption(int x , int y)
	{
		int i = endRun.ReturnOption(x, y);

		
		switch(i)
		{
		case 0:
			Player.food += Player.fruitsCollected * (MommyBird.bonus_fruits);
			Player.fruitsCollected = 0;
			Game.GameState = "NEST";
			Game.nest.NextTurn();
			Player.SpaceTransition = false;
			break;
			
		case 1: 
			
			Game.GameState = "NORMAL";
			Game.player.left = false;
			Game.player.setX(Game.player.getX() + 10);
			
		//	Player.SpaceTransition = false;
			
			
			break;
			
			
		}
		
		
	}

	
	
	public static void applySave(String str)
	{
		String[] spl = str.split("/");

		for(int i = 0; i < spl.length; i++)
		{

			String[] spl2 = spl[i].split("-");

			switch(spl2[0])
			{
			case "fruits":
				Player.food = Integer.parseInt(spl2[1]);
				break;
				
			case "season":
				Season.CurrentMonth = Integer.parseInt(spl2[1]);
				break;
				
			case "PeachHunger":
				Nest.birdies[0].hungerCurrent = Integer.parseInt(spl2[1]);
				break;
				
			case "LinaHunger":
				Nest.birdies[1].hungerCurrent = Integer.parseInt(spl2[1]);
				break;
				
				
			case "NommyHunger":
				Nest.birdies[2].hungerCurrent = Integer.parseInt(spl2[1]);
				break;
				
			case "RippaHunger":
				Nest.birdies[3].hungerCurrent = Integer.parseInt(spl2[1]);
				break;
				
			case "KiwiHunger":
				Nest.birdies[4].hungerCurrent = Integer.parseInt(spl2[1]);
				break;
				
			case "Life":
				Game.shop.ReturnItem("life").level = Integer.parseInt(spl2[1]);
				Game.shop.ReturnItem("life").UpdateCost();
				break;
				
			case "Speed":
				Game.shop.ReturnItem("speed").level = Integer.parseInt(spl2[1]);
				Game.shop.ReturnItem("speed").UpdateCost();
				break;
				
			case "Carry":
				Game.shop.ReturnItem("carry").level = Integer.parseInt(spl2[1]);
				Game.shop.ReturnItem("carry").UpdateCost();
				break;
				
			case "bonus_fruits":
				Game.shop.ReturnItem("bonus fruits").level = Integer.parseInt(spl2[1]);
				Game.shop.ReturnItem("bonus fruits").UpdateCost();
				
				
				break;
				
			case "firstGame":
				Game.firstGame = Integer.parseInt(spl2[1])== 1;
				break;
				
			
				
			}
			
		}
		Game.nest.NextTurn();
		
	}
	
	public static String LoadGame(int encode)
	{
		String line = "";
		File file = new File("save.txt");
		if(file.exists())
		{
			try
			{
				String singleline = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				
				try
				{
					while((singleline = reader.readLine()) != null)
					{
		
						String[] trans = singleline.split("-");
						char[] val = trans[1].toCharArray();
	
						trans[1] = "";
						line+= trans[0];
						line += "-";
						for(int i = 0; i < val.length; i++)
						{
							val[i] -= encode;
							trans[1] += val[i];
						}



						line+= trans[1];
	
						line+= "/";
					}
					
				}
				catch(IOException e)
				{
					
				}
				
			}catch(FileNotFoundException e)
			{
				
			}
			
		}
		
		return line;
	}
	
	
	
	
	public static void SaveGame(String[] val1, String[] val2, int encode)
	{
		BufferedWriter writer = null;

		try
		{
			
			writer = new BufferedWriter(new FileWriter("save.txt"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
		
		for(int i = 0; i < val1.length; i++)
		{
			String current = val1[i] + "-";
	
			char[] value = (val2[i]).toCharArray();
			for(int n = 0; n < value.length; n++)
			{
				value[n] += encode;
				current+= value[n];
			}
			
			try
			{
				writer.write(current);
				if(i < val1.length - 1)
				{
					writer.newLine();
				}
			}
			catch(IOException e){
				
			}
			
			

			
		}
		
		
		try
		{
			writer.flush();
			writer.close();
		} catch(IOException e)
		{
			
		}
		
		
	}

}
