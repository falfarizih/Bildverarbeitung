// BV Ue4 WS2024/25 Vorgabe
//
// Copyright (C) 2024 by Klaus Jung
// All rights reserved.
// Date: 2024-09-30
 		   		   	  

package bv_ws2425;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Histogram {
 		   		   	  
	private static final int grayLevels = 256;
	
    private GraphicsContext gc;
    private int maxHeight;
    
    private int[] histogram = new int[grayLevels];
 		   		   	  
    public Histogram() {
	}
    
	public Histogram(GraphicsContext gc, int maxHeight) {
		this.gc = gc;
		this.maxHeight = maxHeight;
	}
	
	public int[] getValues() {
		return histogram;
	}

	public void setImageRegion(RasterImage image, int regionStartX, int regionStartY, int regionWidth, int regionHeight) {
		
		// TODO: calculate histogram[] out of the gray values found the given image region

	}
	
	public Integer getMinimum() {
		// Will be used in Exercise 5.
		return null;
	}
 		   		   	  
	public Integer getMaximum() {
		// Will be used in Exercise 5.
		return null;
	}
 		   		   	  
	public Double getMean() {
		// Will be used in Exercise 5.
		return null;
	}
 		   		   	  
	public Integer getMedian() {
		// Will be used in Exercise 5.
		return null;
	}
 		   		   	  
	public Double getVariance() {
		// Will be used in Exercise 5.
		return null;
	}
 		   		   	  
	public Double getEntropy() {
		// Will be used in Exercise 5.
		return null;
	}
 		   		   	  
	public void draw(Color lineColor) {
		if(gc == null) return;
		gc.clearRect(0, 0, grayLevels, maxHeight);
		gc.setStroke(lineColor);
		gc.setLineWidth(1);
 		   		   	  
		// TODO: draw histogram[] into the gc graphic context
		// Note that we need to add 0.5 to all coordinates to align points to pixel centers 
		
		double shift = 0.5;
		
		// Remark: This is some dummy code to give you an idea for line drawing		
		gc.strokeLine(shift, shift, grayLevels-1 + shift, maxHeight-1 + shift);
		gc.strokeLine(grayLevels-1 + shift, shift, shift, maxHeight-1 + shift);
		
	}
 		   		   	  
}
 		   		   	  






