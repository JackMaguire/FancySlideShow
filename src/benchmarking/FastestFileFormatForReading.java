package benchmarking;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class FastestFileFormatForReading {

	public static void main( String[] args ) throws IOException {

		File img = new File( "/Users/jackmaguire/Dropbox/March2018LabMeetingSlides/MCHBNetTitle/0001.png" );
		BufferedImage pic = null;
		try {
			pic = ImageIO.read( img );
		}
		catch( IOException e ) {
			e.printStackTrace();
		}

		// Map< String, Double > results = new HashMap< String, Double >();

		System.out.println( "format\tms_per_instance:" );
		System.out.println( "png\t" + ms_per_instance( pic, "PNG" ) );
		System.out.println( "gif\t" + ms_per_instance( pic, "GIF" ) );
		System.out.println( "jpg\t" + ms_per_instance( pic, "JPG" ) );
		// System.out.println( "bmp\t" + ms_per_instance( pic, "BMP" ) );
		// System.out.println( "wbmp\t" + ms_per_instance( pic, "WBMP" ) );

		/*On iMac:
		 * format	ms_per_instance:
		png	0.187
		gif	0.09388
		jpg	0.12324
		 */

	}

	public final static double ms_per_instance( BufferedImage pic, String format ) throws IOException {
		final int num_outerloops = 100;

		final File f = new File( "/tmp/FancySlideShow_FastestFileFormatForReading." + format.toLowerCase() );
		ImageIO.write( pic, format, f );

		long start_time = System.currentTimeMillis();

		for( int i = 0; i < num_outerloops; ++i ) {
			if( ImageIO.read( f ).getHeight() == 0 ) {
				System.out.println( "dummy" );
			}
		}

		long end_time = System.currentTimeMillis();

		final double time_elapsed = end_time - start_time;
		final double time_per_frame = time_elapsed / ( 250 * num_outerloops );
		// System.out.println( "Milliseconds per frame: " + time_per_frame );
		// System.out.println( "Total time elapsed: " + time_elapsed + " ms" );
		return time_per_frame;
	}

}
