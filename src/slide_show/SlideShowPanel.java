package slide_show;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import compile_time_settings.DebugToggles;
import compile_time_settings.SlideShowPanelSettings;

public class SlideShowPanel extends JPanel {

	private static final long serialVersionUID = -7189726966653966497L;

	private BufferedImage image_;

	// private boolean first_paint_ = true;

	private double screen_ratio_ = 0;

	public SlideShowPanel() {
		this.setBackground( SlideShowPanelSettings.BACKGROUND );
	}

	public SlideShowPanel( BufferedImage image ) {
		image_ = image;
	}

	public void setImage( BufferedImage image ) {
		image_ = image;
		repaint();
		revalidate();
	}

	public void paint( Graphics g ) {

		super.paint( g );

		final int panel_width = this.getWidth();
		final int panel_height = this.getHeight();
		if( screen_ratio_ == 0 ) {
			screen_ratio_ = ( (double) panel_height / panel_width );
		}

		// if( first_paint_ ) {
		// first_paint_ = false;
		g.setColor( SlideShowPanelSettings.BACKGROUND );
		g.fillRect( 0, 0, panel_width, panel_height );
		// }

		if( image_ == null )
			return;

		Graphics2D g2 = (Graphics2D) g;

		final int image_width = image_.getWidth();
		final int image_height = image_.getHeight();
		final double image_ratio = ( (double) image_height / image_width );
		// final double scale = getScale( panel_width, panel_height, image_width,
		// image_height );
		final double scale = Math.min( getScale( panel_width, image_width ), getScale( panel_height, image_height ) );

		final int scaled_image_width = (int) ( image_width * scale );
		final int scaled_image_height = (int) ( image_height * scale );

		if( DebugToggles.DEBUG_SLIDE_SHOW ) {
			System.out.println( "---SlideShowPanel---" );
			System.out.println( "imageWidth: " + image_width );
			System.out.println( "imageHeight: " + image_height );
			System.out.println( "panelWidth: " + panel_width );
			System.out.println( "panelHeight: " + panel_height );
			System.out.println( "scaled_image_width: " + scaled_image_width );
			System.out.println( "scaled_image_height: " + scaled_image_height );
			System.out.println( "scale: " + scale );
			System.out.println( "---SlideShowPanel---" );
		}

		final int side_buffersize = ( panel_width - scaled_image_width ) / 2;
		final int top_buffersize = ( panel_height - scaled_image_height ) / 2;
		g2.drawImage( image_, side_buffersize, top_buffersize, scaled_image_width, scaled_image_height, null );

		/*if( screen_ratio_ > image_ratio ) {
			// whitespace on top and bottom
			final int buffersize = ( panel_height - scaled_image_height ) / 2;
			g2.drawImage( image_, 0, buffersize, scaled_image_width, scaled_image_height, null );
			if( DebugToggles.DEBUG_SLIDE_SHOW ) {
				System.out.println( "whitespace on top and bottom" );
				System.out.println( "panelHeight: " + panel_height );
				System.out.println( "scaled_image_height: " + scaled_image_height );
				System.out.println( "buffersize: " + buffersize );
			}
		} else if( image_ratio > screen_ratio_ ) {
			// whitespace on sides
			final int buffersize = ( panel_width - scaled_image_width ) / 2;
			g2.drawImage( image_, buffersize, 0, scaled_image_width, scaled_image_height, null );
			if( DebugToggles.DEBUG_SLIDE_SHOW ) {
				System.out.println( "whitespace on sides" );
				System.out.println( "panelWidth: " + panel_width );
				System.out.println( "scaled_image_width: " + scaled_image_width );
				System.out.println( "buffersize: " + buffersize );
			}
		} else {
			if( DebugToggles.DEBUG_SLIDE_SHOW ) {
				System.out.println( "no whitespace" );
			}
			g2.drawImage( image_, 0, 0, scaled_image_width, scaled_image_height, null );
		}*/

	}

	private double getScale( int panelD, int imageD ) {
		return ( (double) panelD ) / imageD;
	}

	private double getScale( int panel_width, int panel_height, int image_width, int image_height ) {
		return Math.min( getScale( panel_width, image_width ), getScale( panel_height, image_height ) );
	}

}
