package com.c2_systems.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class App {
	 
	private static ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
	
    public static void main( String[] args ) {

    	readCoordsFromFile();
    	
    	plotCoords();

    }
    
    private static void readCoordsFromFile() {
    	BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("C:\\Users\\root\\Desktop\\coords.csv"));
			String line = reader.readLine();

			while (line != null) {
				String[] lineSplit = line.split(",");
				
				int x = Integer.parseInt(lineSplit[0]);
				int y = Integer.parseInt(lineSplit[1]);
				
				Coordinate c = new Coordinate(x, y);
				System.out.println(c);
				App.coordinates.add(c);

				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private static void plotCoords() {

    	int crossLength = 25;
    	int crossWidth = 8;
    	
	    try {
			
	    	BufferedImage img = ImageIO.read(new File("C:\\Users\\root\\Desktop\\unmarked.jpeg"));
	    	Graphics2D g = img.createGraphics();
	    	
	    	int avgX = 0;
	    	int avgY = 0;
	    	int divisor = 0;
	    	
	    	for(Coordinate c : coordinates) {

	    		
	    		int x = c.getX();
	    		int y = c.getY();
	    		
	    		avgX = avgX + x;
	    		avgY = avgY + y;
	    		divisor++;
	    			    		
	    		g.setStroke(new BasicStroke(crossWidth));
	    		
		    	g.setColor(Color.BLACK);
		    	g.drawLine(x+crossWidth, y-crossLength, x+crossWidth, y+crossLength);
		    	g.drawLine(x-crossWidth, y-crossLength, x-crossWidth, y+crossLength);
		    	g.drawLine(x-crossLength, y+crossWidth, x+crossLength, y+crossWidth);
		    	g.drawLine(x-crossLength, y-crossWidth, x+crossLength, y-crossWidth);
		    	
	    		g.setColor(Color.WHITE);
		    	g.drawLine(x, y-crossLength, x, y+crossLength);
		    	g.drawLine(x-crossLength, y, x+crossLength, y);
		    	
		    	
	    	}
	    	
	    	
	    	// avg
	    	avgX = Math.round(avgX/divisor);
	    	avgY = Math.round(avgY/divisor);
	    	
	    	crossLength = (int) (crossLength * 1.25);
	    	crossWidth = (int) (crossWidth * 1.25);
	    	
	    	g.setStroke(new BasicStroke(crossWidth));
    		
	    	g.setColor(Color.BLACK);
	    	g.drawLine(avgX+crossWidth, avgY-crossLength, avgX+crossWidth, avgY+crossLength);
	    	g.drawLine(avgX-crossWidth, avgY-crossLength, avgX-crossWidth, avgY+crossLength);
	    	g.drawLine(avgX-crossLength, avgY+crossWidth, avgX+crossLength, avgY+crossWidth);
	    	g.drawLine(avgX-crossLength, avgY-crossWidth, avgX+crossLength, avgY-crossWidth);
	    	
    		g.setColor(Color.YELLOW);
	    	g.drawLine(avgX, avgY-crossLength, avgX, avgY+crossLength);
	    	g.drawLine(avgX-crossLength, avgY, avgX+crossLength, avgY);
	    	
	    	g.setFont(new Font("Calibri", Font.BOLD, 72)); 
	    	g.drawString("Avg: " + avgX + ", " + avgY + "   (" + divisor + " coordinates)", 100, 125);
	    	
	    	
	    	
	    	ImageIO.write(img, "jpg", new File("C:\\Users\\root\\Desktop\\marked.jpeg"));
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    

}


