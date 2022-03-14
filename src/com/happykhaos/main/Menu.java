package com.happykhaos.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import GameStates.UI;



public class Menu {


	
	
	public OptionMenu opts;
	public Text title;
	public Text name;
	

	
	public Menu()
	{
		opts = new OptionMenu(new String[]{"continue", "new game", "exit"}, 
				new Color(240,60,80), 
				new Color(200,200,100),
				new Color(70, 70, 250),
				new Color(230,170,170));

		title = new Text(5,Game.WIDTH / 2 - 100, 50,  new Color(100,100,222), 40);
	//	underway.AddOutline(new Color(200,20,200));
		title.AddOutline(new Color(200,20,120));		
		name = new Text(5,Game.WIDTH / 2 - 10, 60,  new Color(200,100,22), 22);
		
		name.AddOutline(Color.black);

		//underway.AddOutline(new Color(180,100,180));
		
		title.alphaValue = 0;
		title.Transition(3, "Once");
		//opts.SwapTextColors();


		title.AddOutline(Color.black);
		
		//option[0].setLocationX(0);;
		for(int i = 0; i < opts.option.length  ; i++)
		{
		opts.option[i].InsertValues(Game.WIDTH / 2 - 120, 100  + (i * 70), 230, 55, 1, opts);
		}
		
		
		opts.option[0].text.offset = 20;
		
		opts.option[1].text.offset = 20;
		
		opts.option[2].text.offset = 70;
		
	//	opts.option[0].setTextOffset(20);
		//opts.option[1].setTextOffset(15);
	
	//	opts.option[2].setTextOffset(50);
		
		
		//opts.ActivateOutline(Color.blue);
		//opts.ActivateOutline(Color.magenta);
		opts.ActivateOutline(new Color(250,255,25));
	//	opts.option[0].text.Blink(true);
		
			opts.ChangeTextSize(30);
			opts.DecreaseTextY(30);
			opts.ChangeTextFont(Game.optionFont);
	}
	
	
	public void Tick()
	{
		if(transitioning)
		{

			if(transitionFrames - 0.01f <=  0.2f)
			{
				transitioning = false;
				OptionExecution();
			}
			else
			{
				transitionFrames -= 0.01f;
			}
		}
		if(opts.up)
		{
			opts.up = false;
			opts.CurrentOption --;
			if(opts.CurrentOption < 0)
			{
				opts.CurrentOption = opts.maxOptions;	
			}
		}
		
		
		if(opts.down)
		{
			opts.down = false;
			opts.CurrentOption ++;
			if(opts.CurrentOption > opts.maxOptions)
			{
				opts.CurrentOption = 0;	
			}
		}
		
		
	}
	
	
	public void OptionExecution()
	{
		switch(option)
		{
		case "load":
			File file = new File("save.txt"); 
			if(file.exists())
			{
				String saver = UI.LoadGame(3);
				UI.applySave(saver);
			}
		
			Game.GameState = "NEST";

			
			break;
			
		case "new":
			File filet = new File("save.txt"); 
			filet.delete();
	

			Game.GameState = "NEST";
			break;
			
		case "exit":
			System.exit(1);
			break;
		}
	}
	
	public float transitionFrames = 1;
	public boolean transitioning;
	public String option;
	
	public void EnterOption(int x, int y)
	{
		int optionchoosen = opts.ReturnOption(x, y);

		
		
		switch(optionchoosen)
		{
		case 0:
			option = "load";
			transitioning = true;
			
	
			
	
			break;
			
		case 1:
			option = "new";
			transitioning = true;
		
			
			break;
			
		case 2:
			transitioning = true;
			option = "exit";
		
			break;
			
		}
		
		
	}
	
	public void ClickedOption(int x, int y)
	{
		opts.ClickedOption(x, y);
		
	}
	

	
	public void Render(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		//g.drawImage(Game.background, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		g2.setColor(new Color(20,255,220,255));
		
		//g.setColor(new Color(200,150,200));
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,transitionFrames);
		g2.setComposite(ac);
		g2.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		
		
		title.RenderText((Graphics2D) g, "CRUMB HUNT");
		name.RenderText((Graphics2D) g, "BY EMILY SALUM");
		

		

		

		
		opts.RenderOptions(g);

		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1);
		g2.setComposite(ac);
		
	
		
		
		

		
		
		
		
		
	}
	
}
