package util;

public class ImageScale {

	private static double getScale( int panelD, int imageD ) {
		return ( (double) panelD ) / imageD;
	}

	private static double getScale( int panel_width, int panel_height, int image_width, int image_height ) {
		return Math.min( getScale( panel_width, image_width ), getScale( panel_height, image_height ) );
	}
	
}
