package com.gmail.gazlloyd.rafflegrabber;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Gareth Lloyd on 30/01/14.
 */
public class test {

    public test() {

    }

    static BufferedImage cornertr, cornerbr, cornertl, cornerbl, trtest, tltest, brtest, bltest;
    static int white;

    static void init() {
        try
        {
            System.out.println("Loading images...");
            cornertr = ImageIO.read(test.class.getClassLoader().getResourceAsStream("templates/cornertr.png"));
            cornerbr = ImageIO.read(test.class.getClassLoader().getResourceAsStream("templates/cornerbr.png"));
            cornertl = ImageIO.read(test.class.getClassLoader().getResourceAsStream("templates/cornertl.png"));
            cornerbl = ImageIO.read(test.class.getClassLoader().getResourceAsStream("templates/cornerbl.png"));

            trtest = ImageIO.read(test.class.getClassLoader().getResourceAsStream("templates/testtr.png"));
            tltest = ImageIO.read(test.class.getClassLoader().getResourceAsStream("templates/testtl.png"));
            brtest = ImageIO.read(test.class.getClassLoader().getResourceAsStream("templates/testbr.png"));
            bltest = ImageIO.read(test.class.getClassLoader().getResourceAsStream("templates/testbl.png"));
            System.out.println("Images loaded");
        }

        catch (IOException e)
        {
            System.out.println("IOException\n");
            e.printStackTrace();
            System.exit(1);
            //Main.logger.severe("Failed to load corners of interface");
        }

        catch (Exception e) {
            System.out.println("Exception\n");
            e.printStackTrace();
            System.exit(1);
        }

        white = (new Color(255,255,255)).getRGB();
    }


    public static void main(String[] args) {

        init();

        //int y = cornertr.getHeight(), x = cornertr.getWidth();

        //System.out.println("Dimensions: "+x+"x"+y+"\nBeginning loop...\n");

        /*for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.println("("+i+","+j+"): "+ cornertr.getRGB(i,j));
            }
            System.out.println();
        }*/
        System.out.println("tl");
        dotest(cornertl,tltest);
        System.out.println("bl");
        dotest(cornerbl,bltest);
        System.out.println("tr");
        dotest(cornertr,trtest);
        System.out.println("br");
        dotest(cornerbr,brtest);
    }


    public static void dotest(BufferedImage temp, BufferedImage test) {
        System.out.println("Beginning colour comparison");
        int y = temp.getHeight(), x = temp.getWidth();

        int totaldiff = 0, maxdiff = 0;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.println("(" + i + "," + j + "): " + temp.getRGB(i, j) + " : " + test.getRGB(i, j));
                if (temp.getRGB(i,j) == white) {
                    System.out.println("White found, ignoring");
                    continue;
                }
                int diff = fuzzyColorMatch(temp.getRGB(i,j), test.getRGB(i,j),20);
                totaldiff += diff;
                maxdiff = Math.max(maxdiff, diff);
            }
        }
        System.out.println("\nTotal difference: "+totaldiff+"\nMax difference: "+maxdiff+"\n");



    }
    public static int fuzzyColorMatch(int color1, int color2, int threshold)
    {
        if (color1 == color2) {
            //System.out.println("\t difference: none");
            return 0;
        }
        int difference = Math.abs((color1 & 0xFF) - (color2 & 0xFF));
        if (difference > threshold) {
            System.out.println("\t\t difference: "+difference);
            return difference;
        }
        difference += Math.abs((color1 >>> 8 & 0xFF) - (color2 >>> 8 & 0xFF));
        if (difference > threshold) {
            System.out.println("\t\t difference: "+difference);
            return difference;
        }
        difference += Math.abs((color1 >>> 16 & 0xFF) - (color2 >>> 16 & 0xFF));
        if (difference > threshold) {
            System.out.println("\t\t difference: "+difference);
            return difference;
        }
        difference += Math.abs((color1 >>> 24 & 0xFF) - (color2 >>> 24 & 0xFF));
        if (difference >=  threshold) {
            System.out.println("\t\t difference: "+difference);
            return difference;
        }
        return 0;
    }

}
