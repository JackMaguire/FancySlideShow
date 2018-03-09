package benchmarking;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ReadingBufferedImageFromDisk {

	public final static void main( String[] args ) {
		
		String[] filenames = new String[ 250 ];
		for( int i = 0; i < 250; ++i ) {
			filenames[ i ] = "src/PicsForDemo/Track1/0" + String.format( "%03d", i + 1 ) + ".png";
		}
	
		final int num_outerloops = 100;
		
		long start_time = System.currentTimeMillis();
	
		for( int i = 0; i < num_outerloops; ++i ) {
			for( int j = 0; j < 250; ++j ) {
				File img = new File( filenames[ j ] );
				try {
					BufferedImage image = ImageIO.read( img );
					if( image.getHeight() == 0 ) {
						System.out.println( "This is included to make sure image is not optimized away." );
					}
				}
				catch( IOException e ) {
					e.printStackTrace();
				}
			}
		}
		
		long end_time = System.currentTimeMillis();
		
		final double time_elapsed = end_time - start_time;
		final double time_per_frame = time_elapsed / ( 250 * num_outerloops );
		System.out.println( "Milliseconds per frame: " + time_per_frame );
		System.out.println( "Total time elapsed: " + time_elapsed + " ms");
		
		/*
		 * From iMac:
		 * Milliseconds per frame: 8.22656
		 * Total time elapsed: 205664.0 ms
		 */
	}
	
}
