package com.gmail.gazlloyd.rafflegrabber;

import com.gmail.gazlloyd.rafflegrabber.gui.Main;

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
        Main.logger.info("Creating new raffle image");
		this.rawImg = img;
		process();
	}
	
	public void process() throws RaffleImageException
	{
        Main.logger.info("Attempting to find raffle window...");
		Point tr = ImageUtils.findCornerIndex(rawImg, cornertr, 6);
		Point br = ImageUtils.findCornerIndex(rawImg, cornerbr, 6);
		Point tl = ImageUtils.findCornerIndex(rawImg, cornertl, 6);
		Point bl = ImageUtils.findCornerIndex(rawImg, cornerbl, 6);


        if (tr == null) {
            Main.logger.warning("Could not find top right!");
        }
        if (br == null) {
            Main.logger.warning("Could not find bottom right!");
        }
        if (tl == null) {
            Main.logger.warning("Could not find top left!");
        }
        if (bl == null) {
            Main.logger.warning("Could not find bottom left!");
        }

		if (tr == null || br == null || tl == null || bl == null)
		{
            Main.logger.warning("Could not find corners of the raffle window");
			throw new RaffleImageException("Could not find entire resource window\n\nPlease make sure it is completely visible and unobstructed");
		}
		else
		{
            Main.logger.info("Found raffle window");
			croppedImg = rawImg.getSubimage(tl.x, tl.y, X, Y);
		}
		
	}
	
	
	public static void initialise()
	{
        Main.logger.info("Initialising raffle image");
		try
		{
			cornertr = ImageIO.read(RaffleImage.class.getClassLoader().getResourceAsStream("templates/cornertr.png"));
			cornerbr = ImageIO.read(RaffleImage.class.getClassLoader().getResourceAsStream("templates/cornerbr.png"));
			cornertl = ImageIO.read(RaffleImage.class.getClassLoader().getResourceAsStream("templates/cornertl.png"));
			cornerbl = ImageIO.read(RaffleImage.class.getClassLoader().getResourceAsStream("templates/cornerbl.png"));
		}
		
		catch (IOException e)
		{
            Main.logger.severe("Failed to load corners of interface");
		}
	}
	
	
}
