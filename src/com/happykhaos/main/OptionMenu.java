package com.happykhaos.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import GameStates.UI;

public class OptionMenu {
	
	public String[] Options;
	public boolean up, down;
	public int CurrentOption = -1;
	public int maxOptions;
	
	public Color normal;
	public Color over;
	public Color clicked;
	
	public Color outline;

	
	public Color text;
	
	public int Overed = -1;
	
	public Button[] option; 

	
	public int YOffset = 20;
	
	public boolean Opened = false;
	
	public void ActivateOutline(Color outline)
	{
		for(int i = 0; i < option.length; i++)
		{
			option[i].text.AddOutline(outline);
		}

	}
	
	public void ChangeTextFont(Font font)
	{
		for(int i = 0; i < option.length; i++)
		{
			option[i].text.ChangeFont(font); 
		}
	}
	
	public void DecreaseTextY(int dy)
	{
		for(int i = 0; i < option.length; i++)
		{
			option[i].text.ChangeY(dy); 
		}
	}
	
	public void ChangeTextSize(int size)
	{
		for(int i = 0; i < option.length; i++)
		{
			option[i].text.fontSize = size;
		}
	}
	
	public void Close()
	{
		this.Opened = false;
	}
	
	public void SwapTextColors()
	{
		Color c = this.outline;
		this.outline = this.text;
		this.text = c;
	}
	
	public OptionMenu(String[] options, Color normal, Color clicked, Color text, Color over)
	{
		this.normal = normal;
		this.clicked = clicked;
		this.text = text;
		this.over = over;

		Options = options;
		maxOptions = Options.length - 1;
		option = new Button[Options.length ];
		for(int i = 0; i < option.length ; i++)
		{
		option[i] = new Button();
		}
		
		


		
	}
	
	

	
	public void OnMouseOver(int x, int y)
	{
		for(int i = 0; i < option.length; i++)
		{
			if((x >= option[i].getLocationX()) && (x <= option[i].getLocationX() + option[i].getSizeX() ))
			{
				if((y >= option[i].getLocationY()) && (y <=option[i].getLocationY() + option[i].getSizeY() ))
				{

					this.Overed = i;
					return;
				}
			}
		}
		
		this.Overed = -1;
	}
	
	public void UnderlineButtons(Color underlines, int x, int y)
	{
		for(int i = 0; i < option.length ; i++)
		{
			option[i].ActivateunderLine(underlines, x, y);
			
		}
	}
	
	public void RenderOptions(Graphics g)
	{
		for(int i = 0; i < option.length ; i++)
		{
			g.setColor(normal);
			if(CurrentOption == i)
			{
				g.setColor(clicked);
			}
			else
			{
				if(this.Overed == i)
				{
					g.setColor(over);

				}

			}

			option[i].text.Blink(Overed == i);
			if(Overed == i)
			{
				option[i].SizeIncrease = 4;
			}
			else
			{
				option[i].SizeIncrease = 0;
			}

			option[i].RenderButton(g);
			option[i].text.RenderText((Graphics2D)g, Options[i]);
			
				
			if(!this.Opened)
			{
				option[i].text.SizeAnimation();
			}
			//	g.drawString(Options[i], option[i].getLocationX() + option[i].getTextOffset(), option[i].getLocationY() + this.YOffset);
		}
		
		
		this.Opened = true;
	}
	
	public void ClickedOption(int x, int y)
	{
		for(int i = 0; i < option.length; i++)
		{
			if((x >= option[i].getLocationX()) && (x <= option[i].getLocationX() + option[i].getSizeX() ))
			{
				if((y >= option[i].getLocationY()) && (y <=option[i].getLocationY() + option[i].getSizeY() ))
				{
					UI.ButtonClicked();
					this.CurrentOption = i;
					break;
				}
			}
		}
	}
	
	public int ReturnOption(int x, int y)
	{
		int optionchoosen = -1;
		for(int i = 0; i < option.length; i++)
		{
			if((x >= option[i].getLocationX()) && (x <=option[i].getLocationX() + option[i].getSizeX() ))
			{
				if((y >= option[i].getLocationY()) && (y <=option[i].getLocationY() + option[i].getSizeY() ))
				{
					optionchoosen = i;
					break;
				}
			}
		}
		
		return optionchoosen;
	}
	

}
