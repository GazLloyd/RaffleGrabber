package com.gmail.gazlloyd.rafflegrabber.gui;

import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Logger;

import javax.swing.UIManager;

import com.gmail.gazlloyd.rafflegrabber.ImageHandler;
import com.gmail.gazlloyd.rafflegrabber.RaffleImage;
import com.gmail.gazlloyd.rafflegrabber.ResourceReader;

public class Main {

	public static final String defaultPath = System.getProperty("user.home")+"\\Dropbox\\testestetstets";
	private static File path;
	private static File log;
    public static Logger logger;

	public static void main(String[] args)
	{
        logger = Logger.getLogger("RaffleGrabber");
        logger.info("Starting application");

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            logger.info("Obtained system look and feel");
		}

		catch(Exception e)
		{
            logger.severe("Failed to get system look and feel");
			e.printStackTrace();
		}

        logger.info("Checking for Dropbox folder...");

		if (!(new File(defaultPath)).canRead())
		{
            logger.warning("Failed to find dropbox folder, launching popup");
            new DropboxPopup();
		}

		else {
            logger.info("Found Dropbox folder!");
            cont(new File(defaultPath));
        }
    }
    public static void cont(File pathIn) {
        Main.path = pathIn;
		File logDir = new File(path,"\\auto");
		log = new File(logDir, "\\log.txt");
		
		if(!logDir.exists()) {
            logger.info("Log file directory not found, creating...");
			logDir.mkdir();
        }
		
		if (!log.exists())
		{
            logger.info("Log file not found, creating...");
			new RaffleErrorPopup("Log file not found in "+log+"\n\n Creating...");
			try
			{
				log.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(log, true));
				bw.write("timestamp,name,timber,stone,charcoal,ore,bars,pore,pbars,cloth,rations,minons");
				bw.close();
                logger.info("Created base log file");
			}

			catch (Exception e)
			{
                logger.severe("Could not create log file");
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		ImageHandler ih = new ImageHandler();
		ResourceReader.initialise();
		RaffleImage.initialise();
		RaffleFrame rFrame = null;

        logger.info("Attempting to create raffle frame...");
		try
		{
			rFrame = new RaffleFrame(ih,path);
            logger.info("Raffle frame created!");
		}

		catch (HeadlessException he)
		{
            logger.severe("Running in a console environment, shutting down");
			System.err.println("This application requires a graphical display.");
			System.exit(1);
		}
		catch (Exception e)
		{
            logger.severe("There was an error creating the raffle frame");
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
