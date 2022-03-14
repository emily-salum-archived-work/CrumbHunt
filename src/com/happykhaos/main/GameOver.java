package com.happykhaos.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.happykhaos.entities.Player;

public class GameOver {

	public int MessageFrames;
	public boolean showMessage;
	
	public Text gameOverText;
	public Text continueText;
	
	
//	public static String textContinue = "click to continue";
	
	public GameOver()
	{
		gameOverText = new Text(0,Game.WIDTH  /3 - 25, (int)(Game.HEIGHT * Game.SCALE / 20), Color.white, 25);
		gameOverText.AddOutline(Color.black);
		
		continueText = new Text(0,Game.WIDTH  /5 + 30, (int)(Game.HEIGHT * Game.SCALE /6), Color.white, 15);
		continueText.AddOutline(Color.black);
	//	continueText.Blink(true);
	}
	
	public void Tick()
	{
		if((GameOverFrames + 0.005f <= 1))
		{
			GameOverFrames += 0.005f;
		}
	}
	
	public float GameOverFrames = 0;
	
	public void Render(Graphics g)
	{
		
		Graphics2D g2 = (Graphics2D)g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,GameOverFrames);
		g2.setComposite(ac);
		
		
		
		
			g2.setColor(new Color(40,20,200));
  			
		g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

		gameOverText.RenderText(g2, "GAME OVER");

		if(Player.fruitsCollected / 2 == 1)
		{
			this.continueText.RenderText(g2,  "YOU LOST A FRUIT...");
		}
		else
		{
			this.continueText.RenderText(g2,  "YOU LOST  "+ Player.fruitsCollected / 2 +" FRUITS...");			
		}


		
	}
	
}
