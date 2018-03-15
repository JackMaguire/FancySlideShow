package frame_cache;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import compile_time_settings.PerformanceSettings;

public class FrameCacher {

	private final static String dirname = "/tmp/FancySlideShowSlides";

	private final static String format = "png";

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
			File dir = new File( dirname );
			if( dirExists( dirname ) ) {
				deleteEverythingInside( dir );
			} else {
				dir.mkdir();
			}
		}
		if( PerformanceSettings.DELETE_CACHES ) {
			Runtime.getRuntime().addShutdownHook( new shutdown_hook() );
		}

	}

	public String nextFilename( boolean increment ) {
		final String filename = dirname + "/pic" + next_frame_id_ + "." + format;
		if( increment )
			++next_frame_id_;
		return filename;
	}

	public String createSmallerVersionPlease( BufferedImage original, double scale ) {
		if( PerformanceSettings.LOAD_CACHES ) {
			final String next_filename_temp = nextFilename( true );
			return next_filename_temp;
		}
		// Measure
		final int new_width = (int) ( original.getWidth() * scale );
		final int new_height = (int) ( original.getHeight() * scale );
		return createSmallerVersionPlease( original, new_width, new_height );
	}

	public String createSmallerVersionPlease( BufferedImage original, int new_width, int new_height ) {
		if( PerformanceSettings.LOAD_CACHES ) {
			final String next_filename_temp = nextFilename( true );
			return next_filename_temp;
		}

		// Draw
		final BufferedImage smalls = new BufferedImage( new_width, new_height, original.getType() );
		final Graphics2D g2d = smalls.createGraphics();
		g2d.drawImage( original, 0, 0, new_width, new_height, null );

		// Save
		final String next_filename = nextFilename( true );
		final File outputfile = new File( next_filename );
		try {
			ImageIO.write( smalls, format, outputfile );
		}
		catch( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return next_filename;
	}

	private static void deleteEverythingInside( File f ) {
		if( f.isDirectory() ) {
			for( File c : f.listFiles() )
				delete( c );
		}
	}

	private static void delete( File f ) {
		if( f.isDirectory() ) {
			for( File c : f.listFiles() )
				delete( c );
		}
		f.deleteOnExit();
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
