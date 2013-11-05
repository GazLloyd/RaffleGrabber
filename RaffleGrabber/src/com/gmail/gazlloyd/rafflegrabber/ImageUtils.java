package com.gmail.gazlloyd.rafflegrabber;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.Queue;
/*
 * Adapted from the ImageUtils in the BankStitcher program by a_proofreader
 * http://rs.wikia.com/User:a_proofreader
 */
public class ImageUtils
{
	
	public static final Color white = new Color(255,255,255), black = new Color(0,0,0), blue = new Color(0,0,255);
	public static final int whiteRGB = white.getRGB(), blackRGB = black.getRGB(), blueRGB = blue.getRGB();
	
	public static Point indexOfIgnoreWhite(BufferedImage haystack, BufferedImage needle)
	{
		int hw = haystack.getWidth();
		int hh = haystack.getHeight();
		int nw = needle.getWidth();
		int nh = needle.getHeight();
		for (int hy = 0; hy <= hh - nh; hy++)
			for (int hx = 0; hx <= hw - nw; hx++)
			{
				boolean found = true;
				label:
				for (int ny = 0; ny < nh; ny++)
				{
					for (int nx = 0; nx < nw; nx++)
					{
						if((needle.getRGB(nx, ny) == whiteRGB && haystack.getRGB(hx+nx, hy+ny) == blackRGB)
								|| (needle.getRGB(nx,ny) == blackRGB && haystack.getRGB(hx+nx, hy+ny) != blackRGB))
						{
							found = false;
							break label;
						}
						
					}
				}
				if (found) {
					return new Point(hx, hy);
				}
			}
		return null;
	}
	
	public static Point indexOfName(BufferedImage haystack, BufferedImage needle)
	{
		int hw = haystack.getWidth();
		int hh = haystack.getHeight();
		int nw = needle.getWidth();
		int nh = needle.getHeight();
		for (int hy = 0; hy <= hh - nh; hy++)
			for (int hx = 0; hx <= hw - nw; hx++)
			{
				boolean found = true;
				label:
				for (int ny = 0; ny < nh; ny++)
				{
					for (int nx = 0; nx < nw; nx++)
					{
						int hayRGB = haystack.getRGB(hx+nx, hy+ny);
						int needleRGB = needle.getRGB(nx,ny);
						
						if((needleRGB == whiteRGB && hayRGB != whiteRGB)
								|| (needleRGB == blackRGB && hayRGB != blackRGB)
								|| (needleRGB == blueRGB && (hayRGB == blackRGB || hayRGB == whiteRGB)))
						{
							found = false;
							break label;
						}
						
					}
				}
				if (found) {
					return new Point(hx, hy);
				}
			}
		return null;
	}
	

	public static Point fuzzyIndexOf(BufferedImage haystack, BufferedImage needle, int threshold)
	{
		int hw = haystack.getWidth();
		int hh = haystack.getHeight();
		int nw = needle.getWidth();
		int nh = needle.getHeight();
		for (int hy = 0; hy <= hh - nh; hy++)
			for (int hx = 0; hx <= hw - nw; hx++)
			{
				boolean found = true;

				label:
				for (int ny = 0; ny < nh; ny++)
				{
					for (int nx = 0; nx < nw; nx++)
					{
						if (!fuzzyColorMatch(needle.getRGB(nx, ny), haystack.getRGB(hx + nx, hy + ny), threshold)) {
							found = false;
							break label;
						}
					}
				}
					if (found) {
						return new Point(hx, hy);
					}
			}
		return null;
	}

	public static boolean equals(BufferedImage a, BufferedImage b)
	{
		int aw = a.getWidth();int ah = a.getHeight();int bw = b.getWidth();int bh = b.getHeight();
		if ((aw != bw) || (ah != bh))
			return false;
		for (int y = 0; y < ah; y++)
			for (int x = 0; x < bw; x++) {
				if (a.getRGB(x, y) != b.getRGB(x, y)) {
					return false;
				}
			}
		return true;
	}

	public static boolean fuzzyEquals(BufferedImage a, BufferedImage b, int threshold)
	{
		int aw = a.getWidth();int ah = a.getHeight();int bw = b.getWidth();int bh = b.getHeight();
		if ((aw != bw) || (ah != bh))
			return false;
		for (int y = 0; y < ah; y++)
			for (int x = 0; x < bw; x++) {
				if (!fuzzyColorMatch(a.getRGB(x, y), b.getRGB(x, y), threshold)) {
					return false;
				}
			}
		return true;
	}

	public static void fuzzyFloodFill(BufferedImage image, int x, int y, int threshold, int newColor)
	{
		int w = image.getWidth();int h = image.getHeight();
		BitSet[] seen = new BitSet[w];
		for (int i = 0; i < seen.length; i++)
			seen[i] = new BitSet(h);
		
		Queue<Point> toVisit = new LinkedList();
		int origColor = image.getRGB(x, y);
		seen[x].set(y);
		toVisit.add(new Point(x, y));

		while (!toVisit.isEmpty())
		{
			Point curPoint = (Point)toVisit.poll();
			for (Direction2D direction : Direction2D.values())
			{
				Point newPoint = direction.move(curPoint);
				if ((newPoint.x >= 0) && (newPoint.y >= 0) && (newPoint.x < w) && (newPoint.y < h) && (!seen[newPoint.x].get(newPoint.y)) && (fuzzyColorMatch(image.getRGB(newPoint.x, newPoint.y), origColor, threshold))) {
					image.setRGB(newPoint.x, newPoint.y, newColor);
					seen[newPoint.x].set(newPoint.y);
					toVisit.add(newPoint);
				}
			}
		}
	}

	public static boolean fuzzyColorMatch(int color1, int color2, int threshold)
	{
		if (color1 == color2)
			return true;
		int difference = Math.abs((color1 & 0xFF) - (color2 & 0xFF));
		if (difference > threshold)
			return false;
		difference += Math.abs((color1 >> 8 & 0xFF) - (color2 >> 8 & 0xFF));
		if (difference > threshold)
			return false;
		difference += Math.abs((color1 >> 16 & 0xFF) - (color2 >> 16 & 0xFF));
		if (difference > threshold)
			return false;
		difference += Math.abs((color1 >> 24 & 0xFF) - (color2 >> 24 & 0xFF));
		return difference <= threshold;
	}
}