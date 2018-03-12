package frame_cache;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import compile_time_settings.PerformanceSettings;

public class FrameCacher {

	private static String dirname = "/tmp/FancySlideShowSlides";

	private final static FrameCacher instance_ = new FrameCacher();

	private int next_frame_id_ = 0;

	public static FrameCacher getInstance() {
		return instance_;
	}

	private boolean dirExists( String name ) {
		return new File( name ).exists();
	}

	private FrameCacher() {

		if( !PerformanceSettings.LOAD_CACHES ) {
			if( dirExists( dirname ) ) {
				int i = 0;
				String test_name = dirname + i;
				while ( dirExists( test_name ) ) {
					++i;
					test_name = dirname + i;
				}
				dirname = test_name;
			}
			File dir = new File( dirname );
			dir.mkdir();
		}
		if( PerformanceSettings.DELETE_CACHES ) {
			Runtime.getRuntime().addShutdownHook( new shutdown_hook() );
		}

	}

	public String createSmallerVersionPlease( BufferedImage original, double scale ) {
		if( PerformanceSettings.LOAD_CACHES ) {
			final String next_filename_temp = dirname + "/pic" + ( next_frame_id_++ ) + ".png";
			return next_filename_temp;
		}
		// Measure
		final int new_width = (int) ( original.getWidth() * scale );
		final int new_height = (int) ( original.getHeight() * scale );
		return createSmallerVersionPlease( original, new_width, new_height );
	}

	public String createSmallerVersionPlease( BufferedImage original, int new_width, int new_height ) {
		if( PerformanceSettings.LOAD_CACHES ) {
			final String next_filename_temp = dirname + "/pic" + ( next_frame_id_++ ) + ".png";
			return next_filename_temp;
		}
		
		// Draw
		final BufferedImage smalls = new BufferedImage( new_width, new_height, original.getType() );
		final Graphics2D g2d = smalls.createGraphics();
		g2d.drawImage( original, 0, 0, new_width, new_height, null );

		// Save
		final String next_filename = dirname + "/pic" + ( next_frame_id_++ ) + ".png";
		final File outputfile = new File( next_filename );
		try {
			ImageIO.write( smalls, "png", outputfile );
		}
		catch( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return next_filename;
	}

	private static class shutdown_hook extends Thread {
		public void run() {
			delete( new File( dirname ) );
		}

		void delete( File f ) {
			if( f.isDirectory() ) {
				for( File c : f.listFiles() )
					delete( c );
			}
			f.deleteOnExit();
		}
	}

}
