// BV Ue3 WS2024/25 Vorgabe
//
// Copyright (C) 2024 by Klaus Jung
// All rights reserved.
// Date: 2024-09-30
 		   		   	  

package bv_ws2425;

public class MorphologicFilter {
 		   		   	  
	// filter implementations go here:
	
	public void copy(RasterImage src, RasterImage dst) {
		// TODO: just copy the image
		// dst.argb = src.argb --> wrong, because this is only referencing to the src array, not copying
		//	#1 kann man mit for schleife machen
		// #2 kann man clone machen
		// #3 kann man mit System.arraycopy(src.argb, 0, dst.argb, 0, src.argb.length)
		if(src.argb.length == dst.argb.length){
			System.arraycopy(src.argb, 0, dst.argb, 0, src.argb.length);
		}
	}
	
	public void dilation(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// kernel's first dimension: y (row), second dimension: x (column)
		// TODO: dilate the image using the given kernel
		int kernel_height = kernel.length;
		int kernel_width = kernel[0].length;
		int kernel_centerY = kernel_height / 2;
		int kernel_centerX = kernel_width / 2;

		copy(src, dst);

		for (int y = 0; y < src.height; y++) {
			for (int x = 0; x < src.width; x++) {
				if (src.argb[y * src.width + x] == 0xff000000) {
					for (int ky = 0; ky < kernel_height; ky++) {
						for (int kx = 0; kx < kernel_width; kx++) {
							if (kernel[ky][kx]) {
								int ny = y + ky - kernel_centerY;
								int nx = x + kx - kernel_centerX;

								if (ny >= 0 && ny < src.height && nx >= 0 && nx < src.width) {
									dst.argb[ny * src.width + nx] = 0xff000000;
								}
							}
						}
					}
				}
			}
		}

	}
 		   		   	  
	public void erosion(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// This is already implemented. Nothing to do.
		// It will function once you implemented dilation and RasterImage invert()
		src.invert();
		dilation(src, dst, kernel);
		dst.invert();

		src.invert();   //this one is only to revert the src to original
	}

	public void opening(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// TODO: implement opening by using dilation() and erosion()
		RasterImage temp = new RasterImage(src.width, src.height);

		erosion(src, temp, kernel);
		dilation(temp, dst, kernel);
	}
	
	public void closing(RasterImage src, RasterImage dst, boolean[][] kernel) {
		// TODO: implement closing by using dilation() and erosion()
		RasterImage temp = new RasterImage(src.width, src.height);

		dilation(src, temp, kernel);
		erosion(temp, dst, kernel);
	}
	
	
 		   		   	  	
	

}
 		   		   	  




