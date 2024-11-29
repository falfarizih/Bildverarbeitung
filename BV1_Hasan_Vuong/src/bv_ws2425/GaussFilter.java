// BV Ue1 WS2024/25 Vorgabe
//
// Copyright (C) 2024 by Klaus Jung
// All rights reserved.
// Date: 2024-09-30
 		   		   	  

package bv_ws2425;

public class GaussFilter {
 		   		   	  
	private double[][] kernel;
 		   		   	  
	public double[][] getKernel() {
		return kernel;
	}

	public void apply(RasterImage src, RasterImage dst, int kernelSize, double sigma) {
 		   		   	  
		// TODO: Implement a Gauss filter of size "kernelSize" x "kernelSize" with given "sigma"

		int hotSpotX = kernelSize/2;
		int hotSpotY = kernelSize/2;
		int height = src.height;
		int width = src.width;



		// Step 1: Allocate appropriate memory for the field variable "kernel" representing a 2D array.
		kernel = new double[kernelSize][kernelSize];
		// Step 2: Fill in appropriate values into the "kernel" array.

		for (int ky = 0; ky < kernelSize; ky++) {
			for (int kx = 0; kx < kernelSize; kx++) {
				double distance = Math.sqrt(Math.pow(kx - hotSpotX, 2) + Math.pow(ky - hotSpotY, 2));
				kernel[kx][ky] = Math.exp(-(distance * distance) / (2 * sigma * sigma));
			}
		}

		// Hint:
		// Use g(d) = e^(- d^2 / (2 * sigma^2)), where d is the distance of a coefficient's position to the hot spot.
		// Note that in this comment e^ denotes the exponential function and ^2 the square. In Java ^ is a different operator. 
		
		// Step 3: Normalize the "kernel" such that the sum of all its values is one.

		double sum = 0;
		for (int x = 0; x < kernelSize; x++) {
			for (int y = 0; y < kernelSize; y++) {
				sum += kernel[x][y];
			}
		}

		for (int x = 0; x < kernelSize; x++) {
			for (int y = 0; y < kernelSize; y++) {
				kernel[x][y] = kernel[x][y] / sum;
			}
		}
		// Step 4: Apply the filter given by "kernel" to the source image "src". The result goes to image "dst".
		// Use "constant continuation" for boundary processing.
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {

				float r = 0;
				float g = 0;
				float b = 0;

				for (int ky = -hotSpotX; ky <= hotSpotX; ky++) {
					for (int kx = -hotSpotX; kx <= hotSpotY; kx++) {
						// int pos = (y+ky)*width + (x+kx);



						int X = Math.min(Math.max(x + kx, 0), width - 1);
						int Y = Math.min(Math.max(y + ky, 0), height - 1);



						int pos = Y * width + X;
						int pixelValue = src.argb[pos];

						double kernelValue = kernel[kx + hotSpotX][ky + hotSpotY];

						int R = (pixelValue >> 16) & 0xff;
						int G = (pixelValue >>  8) & 0xff;
						int B =  pixelValue        & 0xff;


						r += (float) (R * kernelValue);
						g += (float) (G * kernelValue);
						b += (float) (B * kernelValue);


					}
				}

				int rn = Math.min(Math.max((int)(r), 0), 255);
				int gn = Math.min(Math.max((int)(g), 0), 255);
				int bn = Math.min(Math.max((int)(b), 0), 255);


				int pos = y*width + x;
				dst.argb[pos] = (0xFF<<24) | (rn<<16) | (gn << 8) | bn;
			}
		}

		//accessing the pixel from the previous aufgabe =
		//int pixelValue = src.argb[pos];
		//int pixelValue = dst.argb[pos];
	}
		   		     	

}
 		   		   	  




