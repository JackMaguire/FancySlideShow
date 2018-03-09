package benchmarking;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import slide_show.SlideShowPanel;

public class DrawingBufferedImageToScreen {

	public static void main( String[] args ) throws IOException {

		String[] filenames = new String[ 250 ];
		BufferedImage[] images = new BufferedImage[ 250 ];
		for( int i = 0; i < 250; ++i ) {
			filenames[ i ] = "src/PicsForDemo/Track1/0" + String.format( "%03d", i + 1 ) + ".png";
			File img = new File( filenames[ i ] );
			images[ i ] = ImageIO.read( img );
		}

		SlideShowPanel slide_show_panel = new SlideShowPanel();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = ge.getScreenDevices();
		JFrame ssframe = createSlideshowJFrame( slide_show_panel );
		if( devices.length > 1 ) {
			devices[ 1 ].setFullScreenWindow( ssframe );
		} else {
			devices[ 0 ].setFullScreenWindow( ssframe );
		}

		final int num_outerloops = 10;

		long start_time = System.currentTimeMillis();

		for( int i = 0; i < num_outerloops; ++i ) {
			for( int j = 0; j < 250; ++j ) {
				slide_show_panel.setImage( images[ j ] );
				slide_show_panel.repaint();
				/*
				 * try { Thread.sleep( 10 ); } catch( InterruptedException e ) { // TODO
				 * Auto-generated catch block e.printStackTrace(); }
				 */
			}
		}

		long end_time = System.currentTimeMillis();

		final double time_elapsed = end_time - start_time;
		final double time_per_frame = time_elapsed / ( 250 * num_outerloops );
		System.out.println( "Milliseconds per frame: " + time_per_frame );
		System.out.println( "Total time elapsed: " + time_elapsed + " ms" );

		/*
		 * This is just stupid iMac: Milliseconds per frame: 0.0048 Total time elapsed:
		 * 12.0 ms
		 */
	}

	private static JFrame createSlideshowJFrame( SlideShowPanel slide_show_panel ) {
		JFrame F = new JFrame( "SlideShow" );
		F.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		F.add( slide_show_panel );
		F.setExtendedState( JFrame.MAXIMIZED_BOTH );
		F.setUndecorated( true );
		F.setVisible( true );
		return F;
	}

}
