package com.gmail.gazlloyd.rafflegrabber;

import com.gmail.gazlloyd.rafflegrabber.gui.Main;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ResourceReader {

	public static BufferedImage[] numImgs = new BufferedImage[10];
	public static HashMap<Character, BufferedImage> nameImgs;
	public static char[] characters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	public static char[] otherChars = {'0','1','2','3','4','5','6','7','8','9','-','_'};
	public static BufferedImage nameLeft, nameRight;

	public static void initialise()
	{

        Main.logger.info("Loading resource reader...");
		nameImgs = new HashMap();

		try
		{
            Main.logger.info("Loading number images");
			for (int i = 0; i < 10; i++)
				numImgs[i] = ImageIO.read(ResourceReader.class.getClassLoader().getResourceAsStream("templates/"+i+".png"));

            Main.logger.info("Loading alphabetic character images");
			for (char ch : characters)
			{
				nameImgs.put(Character.toUpperCase(ch), ImageIO.read(ResourceReader.class.getClassLoader().getResourceAsStream("templates/name/u"+ch+".png")));
				nameImgs.put(Character.toLowerCase(ch), ImageIO.read(ResourceReader.class.getClassLoader().getResourceAsStream("templates/name/l"+ch+".png")));
			}

            Main.logger.info("Loading numeric and special characters");
			for (char ch : otherChars)
				nameImgs.put(ch, ImageIO.read(ResourceReader.class.getClassLoader().getResourceAsStream("templates/name/"+ch+".png")));

            Main.logger.info("Loading space");
			nameImgs.put(' ', ImageIO.read(ResourceReader.class.getClassLoader().getResourceAsStream("templates/name/space.png")));

            Main.logger.info("Loading left and right delimiters for name");
			nameLeft = ImageIO.read(ResourceReader.class.getClassLoader().getResourceAsStream("templates/namefindleft.png"));
			nameRight = ImageIO.read(ResourceReader.class.getClassLoader().getResourceAsStream("templates/namefindright.png"));



		}
		catch (IOException e)
		{
            Main.logger.severe("There was an error loading one of the images");
		}

	}

	public static int readInt(BufferedImage img) throws RaffleImageException
	{
        Main.logger.info("Starting to read image");
		BufferedImage workingImg = img;

		ArrayList<Integer> ints = new ArrayList(4);

		boolean nonefound = true;

		jloop:
			for(int j = 0; j < 4; j++)
			{
				Point leftmost = new Point(workingImg.getWidth()-2, 0);
				boolean notfound = true;
				int leftint=0;
				for (int i = 0; i < 10; i++)
				{
					Point currpoint = ImageUtils.indexOfIgnoreWhite(workingImg, numImgs[i]);

					if (currpoint != null && currpoint.x < leftmost.x)
					{
						leftmost = currpoint;
						leftint = i;
						notfound = false;
						nonefound = false;
					}
				}

				if (notfound)
				{
					break;
				}

				else
				{
					ints.add(leftint);
					int w = numImgs[leftint].getWidth();
					workingImg = workingImg.getSubimage(leftmost.x+w, 0, workingImg.getWidth()-w-leftmost.x, workingImg.getHeight());
				}
			}

		if (nonefound) {
            Main.logger.warning("Did not find any numbers in the image");
			throw new RaffleImageException("Could not read one or more resource values - make sure the image is uncovered!");
        }

		int returnInt;

		switch(ints.size())
		{
		case 1:
			returnInt = ints.get(0);
			//System.out.println("one digit - "+returnInt);
			break;
		case 2:
			returnInt = ints.get(0)*10+ints.get(1);
			//System.out.println("two digits - "+returnInt);
			break;
		case 3:
			returnInt = ints.get(0)*100+ints.get(1)*10+ints.get(2);
			//System.out.println("three digits - "+returnInt);
			break;
		case 4:
			returnInt = ints.get(0)*1000+ints.get(1)*100+ints.get(2)*10+ints.get(3);
			//System.out.println("four digits - "+returnInt);
			break;
		default:
			returnInt = 0;
		}

        Main.logger.info("Found the value: " + returnInt);
		return returnInt;
	}

	public static String readName(BufferedImage img) throws RaffleImageException
	{
        Main.logger.info("Attempting to read name...");
		Point left = ImageUtils.indexOfName(img, nameLeft);
		Point right = ImageUtils.indexOfName(img, nameRight);

		if (left == null || right == null) {
            Main.logger.warning("Could not find left or right area of the name");
			throw new RaffleImageException("Cannot read name - make sure the image is uncovered");
        }
		else
		{
			char[] name = new char[15];
			BufferedImage subimg = img.getSubimage(left.x+nameLeft.getWidth(),left.y, right.x-left.x-nameLeft.getWidth(), nameRight.getHeight());
			boolean nonefound = true;
			int i = 0;

			while(subimg.getWidth() > 3)
			{
				//System.out.println("loop j " + j);
				Point leftmost = new Point(subimg.getWidth()-1, 0);
				boolean notfound = true;
				char leftChar = '0';
				for (char ch : nameImgs.keySet())
				{
					//System.out.println("loop i " + i);
					Point currpoint = ImageUtils.indexOfName(subimg, nameImgs.get(ch));

					if (currpoint != null && currpoint.x < leftmost.x)
					{
						//System.out.println("found one!");
						leftmost = currpoint;
						leftChar = ch;
						notfound = false;
						nonefound = false;
					}
				}


				if (!notfound)
				{

					//System.out.println("found one, not breaking");
					name[i] = leftChar;
					int w = nameImgs.get(leftChar).getWidth();
					//System.out.println("cropping working image");
					subimg = subimg.getSubimage(leftmost.x+w, 0, subimg.getWidth()-w-leftmost.x, subimg.getHeight());
					i++;
				}

				else break;
			}

			if (nonefound) {
                Main.logger.warning("Did not find a name");
				throw new RaffleImageException("Could not read name - make sure the image is uncovered!");
            }
            else {
                Main.logger.info("Found name: " + new String(name).trim());
				return (new String(name)).trim();
            }
		}

	}

}
