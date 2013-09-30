package com.gmail.gazlloyd.rafflegrabber;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.gmail.gazlloyd.rafflegrabber.gui.Main;

public class Entrant {

	private RaffleImage img;
	private String name;
	private HashMap<ResourceType, Integer> resources;

	public enum ResourceType
	{
		TIMBER, STONE, ORE, PRECIOUS_ORE, CHARCOAL, BARS, PRECIOUS_BARS, CLOTH, RATIONS, MINIONS
	}

	public Entrant(BufferedImage img, String name, int timber, int stone, int ore, int pore, int coal, int bars, int pbars, int cloth, int rations, int minions)
	{
		try
		{
			this.img = new RaffleImage(img);
		}

		catch (RaffleImageException e)
		{
			//print something
		}


		this.name = name;

		resources = new HashMap();

		resources.put(ResourceType.TIMBER, timber);
		resources.put(ResourceType.STONE, stone);
		resources.put(ResourceType.ORE, ore);
		resources.put(ResourceType.PRECIOUS_ORE, pore);
		resources.put(ResourceType.CHARCOAL, coal);
		resources.put(ResourceType.BARS, bars);
		resources.put(ResourceType.PRECIOUS_BARS, pbars);
		resources.put(ResourceType.CLOTH, cloth);
		resources.put(ResourceType.RATIONS, rations);
		resources.put(ResourceType.MINIONS, minions);

	}

	public Entrant(BufferedImage img) throws RaffleImageException
	{
		this.img = new RaffleImage(img);

		this.name = readName();

		resources = new HashMap();
		resources.put(ResourceType.TIMBER, readTimber());
		resources.put(ResourceType.STONE, readStone());
		resources.put(ResourceType.ORE, readOre());
		resources.put(ResourceType.PRECIOUS_ORE, readPOre());
		resources.put(ResourceType.CHARCOAL, readCoal());
		resources.put(ResourceType.BARS, readBars());
		resources.put(ResourceType.PRECIOUS_BARS, readPBars());
		resources.put(ResourceType.CLOTH, readCloth());
		resources.put(ResourceType.RATIONS, readRations());
		resources.put(ResourceType.MINIONS, readMinions());
	}

	public String readName() throws RaffleImageException
	{
		return ResourceReader.readName(img.croppedImg);
	}

	public int readTimber() throws RaffleImageException
	{
		return ResourceReader.readInt(img.croppedImg.getSubimage(95, 78, 46, 13));
	}

	public int readStone() throws RaffleImageException
	{
		return ResourceReader.readInt(img.croppedImg.getSubimage(95, 103, 46, 13));
	}

	public int readCoal() throws RaffleImageException
	{
		return ResourceReader.readInt(img.croppedImg.getSubimage(95, 128, 46, 13));
	}

	public int readOre() throws RaffleImageException
	{
		return ResourceReader.readInt(img.croppedImg.getSubimage(95, 153, 46, 13));
	}

	public int readBars() throws RaffleImageException
	{
		return ResourceReader.readInt(img.croppedImg.getSubimage(95, 178, 46, 13));
	}

	public int readPOre() throws RaffleImageException
	{
		return ResourceReader.readInt(img.croppedImg.getSubimage(245, 78, 46, 13));
	}

	public int readPBars() throws RaffleImageException
	{
		return ResourceReader.readInt(img.croppedImg.getSubimage(245, 103, 46, 13));
	}

	public int readCloth() throws RaffleImageException
	{
		return ResourceReader.readInt(img.croppedImg.getSubimage(245, 128, 46, 13));
	}

	public int readRations() throws RaffleImageException
	{
		return ResourceReader.readInt(img.croppedImg.getSubimage(245, 153, 46, 13));
	}

	public int readMinions() throws RaffleImageException
	{
		return ResourceReader.readInt(img.croppedImg.getSubimage(245, 178, 46, 13));
	}


	public RaffleImage getImg()
	{
		return img;
	}

	public HashMap<ResourceType, Integer> getResources()
	{
		return resources;
	}

	public String getName()
	{
		return name;
	}


	public String toString()
	{
		return name + " has gathered:\n" + resources.get(ResourceType.TIMBER) + " timber\n" 
				+ resources.get(ResourceType.STONE) + " stone\n" 
				+ resources.get(ResourceType.CHARCOAL) + " coal\n" 
				+ resources.get(ResourceType.ORE) + " ore\n"
				+ resources.get(ResourceType.BARS) + " bars\n"
				+ resources.get(ResourceType.PRECIOUS_ORE) + " precious ore\n"
				+ resources.get(ResourceType.PRECIOUS_BARS) + " precious bars\n" 
				+ resources.get(ResourceType.CLOTH) + " cloth\n" 
				+ resources.get(ResourceType.RATIONS) + " rations\n" 
				+ "and " + resources.get(ResourceType.MINIONS) + " minions\n"; 
	}

	public String printString()
	{
		return name+","+resources.get(ResourceType.TIMBER)+","+resources.get(ResourceType.STONE)
				+","+resources.get(ResourceType.CHARCOAL)+","+resources.get(ResourceType.ORE)
				+","+resources.get(ResourceType.BARS)+","+resources.get(ResourceType.PRECIOUS_ORE)
				+","+resources.get(ResourceType.PRECIOUS_BARS)+","+resources.get(ResourceType.CLOTH)
				+","+resources.get(ResourceType.RATIONS)+","+resources.get(ResourceType.MINIONS); 
	}
	
	public void saveImg() throws IOException
	{
		ImageIO.write(img.croppedImg, "png", new File(Main.getPath(), "\\auto\\"+Calendar.getInstance().getTime().toString().replace(':','-')+ " - " + name + ".png"));
	}
	
	public void amend(String name, HashMap<ResourceType, Integer> res)
	{
		this.name = name;
		this.resources = res;
	}
	
}
