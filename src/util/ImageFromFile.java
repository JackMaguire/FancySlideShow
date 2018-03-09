package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageFromFile {

	public static BufferedImage imageFromFile( String filename ) {
		File img = new File( filename );
		try {
			BufferedImage image = ImageIO.read( img );
			return image;
		}
		catch( IOException e ) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
