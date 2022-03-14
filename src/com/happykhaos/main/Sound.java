package com.happykhaos.main;


import java.io.*;


import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;

import javax.sound.sampled.*;
public class Sound {
	
/*	private AudioClip clip;
	SourceDataLine sourceLine = null;*/
	
	public static class Clips
	{
		public Clip[] clips;
		private int p;
		private int count;
		
		public Clips(byte[] buffer, int count) throws LineUnavailableException , IOException, UnsupportedAudioFileException
		{
			if(buffer == null)
			{
				return;
			}
			
			clips = new Clip[count];
			this.count = count;
			for(int i = 0; i < count; i++)
			{
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
			
			}
			
		}
		
		public void Stop()
		{
			clips[p].stop();
		}
		
		public void Play()
		{
			if(clips == null) return;
			
			clips[p].stop();
			
			clips[p].setFramePosition(0);	
			FloatControl volume= (FloatControl) clips[p].getControl(FloatControl.Type.MASTER_GAIN); 
			volume.setValue(Game.Volume);
			clips[p].start();
			
			p++;
			
			if(p >= count)
			{
				p = 0;
			}
			
		}
		
		
		public void Loop()
		{
			if(clips == null) return;
			clips[p].setFramePosition(0);
			clips[p].loop(300);
		}
		
		
		public static Clips Load(String name , int count)
		{
			try {
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(name));
				
				byte[] buffer = new byte[1024];
				
				int read = 0;
				
				while((read = dis.read(buffer)) >= 0)
				{
					baos.write(buffer, 0, read);
					
				}
				dis.close();
				byte[] data = baos.toByteArray();
				return new Clips(data,count);
				
			} 
			catch(Exception i){
				
				try {
					return new Clips(null,0);
				} catch(Exception ee)
				{
					return null;
				}
				
			}
		}
		
		
	}

	public static Clips movesound = Clips.Load("/move.wav", 1);
	
	public static Clips ballhit = Clips.Load("/ballcollision.wav", 1);
	
	public static Clips buttonPress = Clips.Load("/button.wav", 1);
	
	public static Clips hurtSound = Clips.Load("/hurt.wav", 1);
	
	public static Clips hitSound = Clips.Load("/Hit.wav", 1);
	
	public static Clips deadSound = Clips.Load("/dead.wav", 1);
	
	
	public static Clips PurpleMusic = Clips.Load("/purplesong.wav", 1);
	
	
	
	
	
	
	
	/*public static final Sound movementsound = new Sound("/move.wav");
	public static final Sound ballsound = new Sound("/ballcollision.wav");
	public static final Sound buttonsound = new Sound("/button.wav");
	public static final Sound hurtsound = new Sound("/hurt.wav");
	public static final Sound deadsound = new Sound("/dead.wav");
	public static final Sound hitsound = new Sound("/Hit.wav");

	public Sound(String name) 
	{
		
		
		
		//java.net.URL url = this.getClass().getResource(name);
		
			try {

				
				clip = Applet.newAudioClip(Sound.class.getResource(name));

				
				/*Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         System.out.print("f "+clip.getFrameLength());
		         System.out.print("a "+clip.getLevel());
		        //clip.setFramePosition(0);


			}
			catch(Throwable e)
			{
				
			}
	

	}
	
	public void Loop()
	{
		try {
			 new Thread(new Runnable()
			 {
				 public void run()
				 {
					 clip.loop(0); 
			         System.out.print("a "+clip.getLevel());

				 }
				 
			 }).start();
		}
		catch(Throwable e)
		{
			
		}
	}
	
	public void Play()
	{
	
			try {
				new Thread() {
					public void run() {
						clip.play();
					}
				}.start();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	
	
}*/
}