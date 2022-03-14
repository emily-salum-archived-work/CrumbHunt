package com.happykhaos.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Text {
	
	public List<Text> outlines;

	public int x,y,offset;
	public float fontSize, maxfontSize, minFontSize, natfontSize;
	public Color textColor;
	public boolean blinking;
	public int blinkFrames;
	
	public int alphaValue = 255;
	public boolean changeAlphaUp, changeAlphaDown, end;
	public int intensity;
	public Font font ;
	
	
	public boolean resize = false, maxAchieved, minAchieved;
	

	public void Transition(int intensity, String Methode)
	{
		
		if(Methode == "Once")
		{
			end = true;
		}	
		if(Methode == "Loop")
		{
			end = false;
		}
		
		this.intensity = intensity;
		if(intensity > 0)
		{
			changeAlphaUp = true;
			changeAlphaDown = false;
			return;
		}
		if(intensity < 0)
		{
			changeAlphaUp = false;
			changeAlphaDown = true;
			return;
		}

	
	}
	
	public Text(int offset,int x, int y, Color textColor, float fontSize)
	{
		this.offset = offset;
		this.x = x;
		this.y = y;
		this.fontSize = fontSize;
		this.textColor = textColor;
		font = Game.newFont;
		outlines = new ArrayList<Text>();
	}
	
	
	public void ChangeY(int change)
	{
		for(int i = 0; i < outlines.size(); i++)
		{
			outlines.get(i).ChangeY(change);
			
		}
		
		
		this.y += change;
		
	}
	
	public void Blink(boolean b)
	{
		blinking = b;
	}
	
	
	public void setOffset(int offset)
	{
		this.offset = offset;
	}
	
	public Color GetColor(int alphaValue)
	{
		if(textColor == null)
		{
			return null;
		}
		Color color = new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), alphaValue);
		return color;
	}
	
	

	
	public void ChangeAlpha()
	{
		if(this.changeAlphaUp)
		{
			if(this.alphaValue < 255)
			{
				alphaValue++;
			}
			else
			{
				if(!end)
				{
					this.changeAlphaDown = true;
				}
				this.changeAlphaUp = false;
			}

		}
		
		if(this.changeAlphaDown)
		{
			if(this.alphaValue > 0)
			{
				alphaValue --;
			}
			else
			{
				if(!end)
				{
					this.changeAlphaUp = true;
				}
				this.changeAlphaDown = false;
			}

		
	}
	}
	
	
	public void ChangeFont(Font font)
	{
		this.font = font;
	}
	
	public void RenderText(Graphics2D g, String content)
	{
	
		int j = this.intensity;
		do
		{
		this.ChangeAlpha();

		if(j < 0)
		{
			j++;
		}
		else
		{
			j--;
		}

		} while( j != 0);
		
		
		if(resize)
		{
			if((!this.maxAchieved)|| (this.minAchieved))
			{
				fontSize += 0.5f;
				if(fontSize == natfontSize)
				{
					this.resize = false;
				}
			}
			else
			{
				if(!this.minAchieved)
				{
					fontSize -= 0.5f;
				}
			}
			if(fontSize >= this.maxfontSize)
			{
				maxAchieved = true;
			}
			if(fontSize <= this.minFontSize)
			{
				minAchieved = true;
			}
			
		}
		blinkFrames++;
		if((!this.blinking) || (blinkFrames >= 30))
		{
			if(blinkFrames > 60)
			{
				blinkFrames = 0;
			}

			
			for(int i = 0; i < outlines.size(); i++)
			{
				g.setFont(font.deriveFont(this.fontSize));	
				g.setColor(outlines.get(i).GetColor(this.alphaValue));
				g.drawString(content, outlines.get(i).x + offset, outlines.get(i).y);
			}
			
		
	
		g.setFont(font.deriveFont(fontSize));	
		g.setColor(this.GetColor(this.alphaValue));
		g.drawString(content, x + offset, y);
		

		}
	}
	
	public void SizeAnimation()
	{
		resize = true;
		minFontSize = this.fontSize/ 1.4f;
		this.maxfontSize = this.fontSize * 1.4f;
		this.maxAchieved = false;
		this.minAchieved = false;
		this.natfontSize = this.fontSize;
	}
	
	public void AddOutline(Color color)
	{
		
			Text text = new Text(this.offset,this.x + outlines.size() + 1, this.y, color , this.fontSize);
			outlines.add(text);
	

	}

	public void changeX(int i) {
		for(int id = 0; id < outlines.size(); id++)
		{
			outlines.get(id).changeX(i);
			
		}
		
		
		this.x += i;
		
	}
	
}
