package com.gmail.gazlloyd.rafflegrabber;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.gmail.gazlloyd.rafflegrabber.gui.Main;
import com.gmail.gazlloyd.rafflegrabber.gui.RaffleErrorPopup;

public class ImageHandler {

    public ImageHandler() {

    }

    public RaffleImage loadRaffleImage(String path) {
        try {
            BufferedImage f = ImageIO.read(new File(path));
            return ResourceReader.getRaffleImg(f);
        }

        catch (IOException e) {
            Main.logger.severe("ioexception when loading " + path);
        }

        catch (RaffleImageException e) {
            new RaffleErrorPopup(e.msg);
        }

        return null;

    }

    public BufferedImage loadImage(String path) {
        return loadImage(new File(path));

    }

    public BufferedImage loadImage(File file) {
        try {
            return ImageIO.read(file);
        }

        catch (IOException e) {
            Main.logger.severe("ioexception when loading " + file);
        }

        return null;
    }


    public void saveImage(RaffleImage img, String path) {
        try {
            ImageIO.write(img.croppedImg, "png", new File(path));
        }

        catch (IOException e) {
            Main.logger.severe("Failed to save to " + path);
        }
    }








}
