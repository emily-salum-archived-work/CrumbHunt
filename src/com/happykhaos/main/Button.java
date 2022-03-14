package com.happykhaos.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.DataBufferInt;

public class Button {
	
	private int LocationX;

	private int LocationY;
	
	private int SizeX;

	private int SizeY;
	
	private int Textoffset = 1;
	
	public Text text;
	public OptionMenu menu;
		
	public boolean underLined;
	public int UnderlineSizeX, UnderlineSizeY;
	public Color underline;
	
	public boolean buttonbackground = true;
	
	
	public int SizeIncrease = 0;
	
	public int[] pixels;
	
	public Button()
	{
		//pixels = ((DataBufferInt )Menu.button.getRaster().getDataBuffer()).getData();
	}
	public void InsertValues(int x, int y, int xx, int yy, int offset, OptionMenu menu)
	{

		this.LocationX = x;
		this.LocationY = y;
		this.SizeX = xx;
		this.SizeY = yy;
		this.Textoffset = offset;
		if(menu != null)
		{
			this.menu = menu;
			text = new Text(this.Textoffset,this.getLocationX() , this.getLocationY() + menu.YOffset, menu.text, 15);			
		}
		else
		{
			text = new Text(this.Textoffset,this.getLocationX() , this.getLocationY() , Color.blue , 15);
		}


	}
	
	public void ActivateunderLine(Color underline, int X, int Y) {
		this.underLined = true;
		this.buttonbackground = false;
		this.underline = underline;
		this.UnderlineSizeX = X;
		this.UnderlineSizeY = Y;
	}


	public void RenderButton(Graphics g)
	{
		if(this.underLined)
		{
			Color c = g.getColor();
			g.setColor(this.underline);
					g.fillRect(getLocationX() - this.UnderlineSizeX / 2, getLocationY() - this.UnderlineSizeY / 2, getSizeX() + this.UnderlineSizeX, getSizeY() + this.UnderlineSizeY);
					g.setColor(c);
		}

	    g.fillRect(getLocationX() - SizeIncrease / 2, getLocationY()- SizeIncrease / 2, getSizeX() + SizeIncrease, getSizeY() + SizeIncrease);
		//Game.g(getLocationX() - 20, getLocationY() - 2, getSizeX(), getSizeY());



		
	}
	

	

	public int getTextOffset()
	{
		return Textoffset;
	}
	
	public int getLocationX() {
		return LocationX;
	}

	public void setLocationX(int locationX) {
		LocationX = locationX;
	}

	public int getLocationY() {
		return LocationY;
	}

	public void setLocationY(int locationY) {
		LocationY = locationY;
	}

	public int getSizeX() {
		return SizeX;
	}

	public void setSizeX(int sizeX) {
		SizeX = sizeX;
	}
	
	public void setTextOffset(int offset)
	{
		Textoffset = offset;

		text.setOffset(offset);
	}
	public int getSizeY() {
		return SizeY;
	}

	public void setSizeY(int sizeY) {
		SizeY = sizeY;
	}
	
	
}
