package com.gmail.gazlloyd.rafflegrabber;

import com.gmail.gazlloyd.rafflegrabber.gui.Main;
import java.awt.image.BufferedImage;

public class RaffleImage {

    public BufferedImage rawImg;
    public BufferedImage croppedImg;

    public RaffleImage(BufferedImage img, BufferedImage croppedImg) {
        Main.logger.info("Creating new raffle image");
        this.rawImg = img;
        this.croppedImg = croppedImg;
    }
}
