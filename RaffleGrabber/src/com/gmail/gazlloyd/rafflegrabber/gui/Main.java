package com.gmail.gazlloyd.rafflegrabber.gui;

import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.UIManager;

import com.gmail.gazlloyd.rafflegrabber.ImageHandler;
import com.gmail.gazlloyd.rafflegrabber.RaffleImage;
import com.gmail.gazlloyd.rafflegrabber.ResourceReader;

public class Main {

	public static final String defaultPath = System.getProperty("user.home")+"\\Dropbox\\Raffle entries";
	private static File path;
	private static File log;

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}


		if (!(new File(defaultPath)).canRead())
		{
			new ErrorPopup();
		}

		else cont(new File(defaultPath));
	}


	public static void cont(File path)
	{
		Main.path = path;
		File logDir = new File(path,"\\auto");
		log = new File(logDir, "\\log.txt");
		
		if(!logDir.exists())
			logDir.mkdir();
		
		if (!log.exists())
		{
			new RaffleErrorPopup("Log file not found in "+log+"\n\n Creating...");
			try
			{
				log.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(log, true));
				bw.write("timestamp,name,timber,stone,charcoal,ore,bars,pore,pbars,cloth,rations,minons");
				bw.close();
			}

			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		ImageHandler ih = new ImageHandler();
		ResourceReader.initialise();
		RaffleImage.initialise();
		RaffleFrame rFrame = null;
		try
		{
			rFrame = new RaffleFrame(ih,path);
		}

		catch (HeadlessException he)
		{
			System.err.println("This application requires a graphical display.");
			System.exit(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		rFrame.setVisible(true);
	}

	public static File getLog()
	{
		return log;
	}
	
	public static File getPath()
	{
		return path;
	}


}
