package com.gmail.gazlloyd.rafflegrabber.gui;

import java.awt.HeadlessException;
import java.io.*;
import java.util.logging.Logger;

import java.util.Scanner;

import javax.swing.UIManager;

import com.gmail.gazlloyd.rafflegrabber.ImageHandler;
import com.gmail.gazlloyd.rafflegrabber.RaffleImage;
import com.gmail.gazlloyd.rafflegrabber.ResourceReader;

public class Main {

	public static final String defaultPath = System.getProperty("user.home")+File.separator+"Dropbox"+File.separator+"Raffle entries";
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
        
        String path;
        
        path = defaultPath;
        
        try {
            Main.logger.info("Attempting to check for previously saved folder...");
            File settingsFile;
            if (System.getProperty("os.name").matches("[Ww][Ii][Nn][Dd][Oo][Ww][Ss] .*")) {
                File dir = new File(System.getenv("APPDATA")+File.separator+".rafflegrabber");
                Main.logger.info("OS is Windows, loading from: "+dir+File.separator+".rafflegrabber");
                settingsFile = new File(dir + File.separator + ".rafflegrabber");
            }
            else {
                settingsFile = new File(System.getProperty("user.home")+File.separator+".rafflegrabber");
                Main.logger.info("Non-Windows OS, loading from "+System.getProperty("user.home")+File.separator+".rafflegrabber");
            }

	        if(settingsFile.canRead()) {
                Main.logger.info("Successfully found settings!");
	        	String otherPath = new Scanner(settingsFile, "UTF-8").useDelimiter("\\A").next();
	        	if((new File(otherPath)).canRead()) {
	        		path = otherPath;
                    Main.logger.info("Successfully read and saved settings!");
	        	}
                else
                    Main.logger.warning("Could not read saved settings, checking default instead...");

	        }
            else
                Main.logger.warning("Didn't find any previously saved settings, checking default instead...");
	    }
	    catch (FileNotFoundException e) {
	    	Main.logger.warning("FileNotFound Exception");
            e.printStackTrace();
	    }

		if (!(new File(path)).canRead())
		{
            logger.warning("Failed to find dropbox, launching popup");
            new DropboxPopup();
		}

		else {
            logger.info("Found Dropbox folder!");
            cont(new File(path));
        }
    }
    public static void cont(File pathIn) {
        Main.path = pathIn;
		File logDir = new File(path,File.separator+"auto");
		log = new File(logDir, File.separator+"log.txt");
		
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
