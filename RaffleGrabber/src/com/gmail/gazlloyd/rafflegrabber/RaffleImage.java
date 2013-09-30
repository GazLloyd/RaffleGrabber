package com.gmail.gazlloyd.rafflegrabber;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RaffleImage {
	
	public BufferedImage rawImg;
	public BufferedImage croppedImg;
	
	public static BufferedImage cornertr, cornerbr, cornertl, cornerbl;
	
	public static final int X = 352, Y = 252;
	
	public RaffleImage(BufferedImage img) throws RaffleImageException
	{
		this.rawImg = img;
		process();
	}
	
	public void process() throws RaffleImageException
	{
		Point tr = ImageUtils.fuzzyIndexOf(rawImg, cornertr, 6);
		Point br = ImageUtils.fuzzyIndexOf(rawImg, cornerbr, 6);
		Point tl = ImageUtils.fuzzyIndexOf(rawImg, cornertl, 6);
		Point bl = ImageUtils.fuzzyIndexOf(rawImg, cornerbl, 6);
		
		if (tr == null || br == null || tl == null || bl == null)
		{
			throw new RaffleImageException("Could not find entire resource window\n\nPlease make sure it is completely visible and unobstructed");
		}
		else
		{
			//print("successfully found raffle window!")
			croppedImg = rawImg.getSubimage(tl.x, tl.y, X, Y);
		}
		
	}
	
	
	public static void initialise()
	{
		try
		{
			cornertr = ImageIO.read(RaffleImage.class.getClassLoader().getResourceAsStream("templates/cornertr.png"));
			cornerbr = ImageIO.read(RaffleImage.class.getClassLoader().getResourceAsStream("templates/cornerbr.png"));
			cornertl = ImageIO.read(RaffleImage.class.getClassLoader().getResourceAsStream("templates/cornertl.png"));
			cornerbl = ImageIO.read(RaffleImage.class.getClassLoader().getResourceAsStream("templates/cornerbl.png"));
		}
		
		catch (IOException e)
		{
			//print some message about failure to load here
		}
	}
	
	
}
