// BV Ue2 WS2024/25 Vorgabe
//
// Copyright (C) 2024 by Klaus Jung
// All rights reserved.
// Date: 2024-09-30
 		   		   	  

package bv_ws2425;


public class GeometricTransform {
 		   		   	  
	public enum InterpolationType { 
		NEAREST("Nearest Neighbour"), 
		BILINEAR("Bilinear");
		
		private final String name;       
	    private InterpolationType(String s) { name = s; }
	    public String toString() { return this.name; }
	};
	
	public void perspective(RasterImage src, RasterImage dst, double angle, double perspectiveDistortion, InterpolationType interpolation) {
		switch(interpolation) {
		case NEAREST:
			perspectiveNearestNeighbour(src, dst, angle, perspectiveDistortion);
			break;
		case BILINEAR:
			perspectiveBilinear(src, dst, angle, perspectiveDistortion);
			break;
		default:
			break;	
		}
		
	}
 		   		   	  
	/**
	 * @param src source image
	 * @param dst destination Image
	 * @param angle rotation angle in degrees
	 * @param perspectiveDistortion amount of the perspective distortion 
	 */
	public void perspectiveNearestNeighbour(RasterImage src, RasterImage dst, double angle, double perspectiveDistortion) {
 		   		   	  
		// TODO: implement the geometric transformation using nearest neighbour image rendering


		int width_src = src.width;
		int height_src =src.height;
		int height_dst = dst.height;
		int width_dst = dst.width;

        double rads = Math.toRadians(angle);



		for (int yd = 0; yd < height_dst; yd++) {
			for (int xd = 0; xd < width_dst; xd++) {

				double yd_centered = yd - ((double) height_dst /2);
				double xd_centered = xd - ((double)width_dst/2);

				double ys = yd_centered / (Math.cos(rads) - yd_centered * perspectiveDistortion * Math.sin(rads));
				double xs = xd_centered * (perspectiveDistortion * Math.sin(rads) * ys + 1);


				int ys_centered = (int) Math.round(ys + ((double) height_src / 2));
				int xs_centered = (int) Math.round(xs + ((double) width_src / 2));


				if (xs_centered >= 0 && xs_centered < width_src && ys_centered >= 0 && ys_centered < height_src) {
					int pos_src = ys_centered * width_src + xs_centered;
					int pos_dst = yd * width_dst + xd;
					dst.argb[pos_dst] = src.argb[pos_src];
				} else {
					dst.argb[yd * width_dst + xd] = 0xFFFFFFFF;
				}
			}
		}




		
		// NOTE: angle contains the angle in degrees, whereas Math trigonometric functions need the angle in radiant
		
	}


	/**
	 * @param src source image
	 * @param dst destination Image
	 * @param angle rotation angle in degrees
	 * @param perspectiveDistortion amount of the perspective distortion 
	 */
	public void perspectiveBilinear(RasterImage src, RasterImage dst, double angle, double perspectiveDistortion) {
 		   		   	  
		// TODO: implement the geometric transformation using bilinear interpolation

		int width_src = src.width;
		int height_src = src.height;
		int width_dst = dst.width;
		int height_dst = dst.height;

		double rads = Math.toRadians(angle);

		double centerX = width_dst/2.0;
		double centerY = height_dst/2.0;

		for (int yd = 0; yd < height_dst; yd++) {
			for (int xd = 0; xd < width_dst; xd++) {
				double yd_centered = yd - centerY;
				double xd_centered = xd - centerX;

				double ys = yd_centered / (Math.cos(rads) - yd_centered * perspectiveDistortion * Math.sin(rads));
				double xs = xd_centered * (perspectiveDistortion * Math.sin(rads) * ys + 1);

				int  ys_centered = (int)Math.round(ys +  (height_src / 2.0));
				int  xs_centered = (int)Math.round(xs +  (width_src / 2.0));

				if (xs_centered >= 0 && xs_centered < width_src && ys_centered >= 0 && ys_centered < height_src) {

					int x_left = xs_centered;
					int x_right = Math.min(x_left + 1, width_src - 1);
					int y_top = ys_centered;
					int y_bot = Math.min(y_top + 1, height_src - 1);

					double h = xs_centered - x_left;
					double v = ys_centered - y_top;

					int a = src.argb[y_top * width_src + x_left];
					int b = src.argb[y_top * width_src + x_right];
					int c = src.argb[y_bot * width_src + x_left];
					int d = src.argb[y_bot * width_src + x_right];

					int red_a = (a >> 16) & 0xFF;
					int green_a = (a >> 8) & 0xFF;
					int blue_a = a & 0xFF;

					int red_b = (b >> 16) & 0xFF;
					int green_b = (b >> 8) & 0xFF;
					int blue_b = b & 0xFF;

					int red_c = (c >> 16) & 0xFF;
					int green_c = (c >> 8) & 0xFF;
					int blue_c = c & 0xFF;

					int red_d = (d >> 16) & 0xFF;
					int green_d = (d >> 8) & 0xFF;
					int blue_d = d & 0xFF;

					double red_top = red_a * (1 - h) + red_b * h;
					double red_bottom = red_c * (1 - h) + red_d * h;

					double green_top = green_a * (1 - h) + green_b * h;
					double green_bottom = green_c * (1 - h) + green_d * h;

					double blue_top = blue_a * (1 - h) + blue_b * h;
					double blue_bottom = blue_c * (1 - h) + blue_d * h;

					int R = (int) (red_top * (1 - v) + red_bottom * v);
					int G = (int) (green_top * (1 - v) + green_bottom * v);
					int B = (int) (blue_top * (1 - v) + blue_bottom * v);

					int interpolatedColor = (0xFF << 24) | (R << 16) | (G << 8) | B;
					dst.argb[yd * width_dst + xd] = interpolatedColor;
				} else {
					dst.argb[yd * width_dst + xd] = 0xFFFFFFFF;
				}
			}
		}
		// NOTE: angle contains the angle in degrees, whereas Math trigonometric functions need the angle in radiant

 	}
 // dst Bild is bigger than the src
	// for loops must be done in the dst (as if making a new image)  and because its a different size (new canvas)
 		   		   	  
}
 		   		   	  



