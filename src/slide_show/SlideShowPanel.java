package slide_show;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import compile_time_settings.CompileTimeSettings;
import compile_time_settings.SlideShowPanelSettings;

public class SlideShowPanel extends JPanel {

	private static final long serialVersionUID = -7189726966653966497L;

	private BufferedImage image_;

	// private boolean first_paint_ = true;

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

		int panelWidth = this.getWidth();
		int panelHeight = this.getHeight();

		// if( first_paint_ ) {
		// first_paint_ = false;
		// g.setColor( SlideShowPanelSettings.BACKGROUND );
		// g.fillRect( 0, 0, panelWidth, panelHeight );
		// }

		if( image_ == null )
			return;

		Graphics2D g2 = (Graphics2D) g;

		final int imageWidth = image_.getWidth();
		final int imageHeight = image_.getHeight();
		final double scale = getScale( panelWidth, panelHeight, imageWidth, imageHeight );

		final int scaled_image_width = (int) ( imageWidth * scale );
		final int scaled_image_height = (int) ( imageHeight * scale );

		if( CompileTimeSettings.DEBUG_SLIDE_SHOW ) {
			System.out.println( "---SlideShowPanel---" );
			System.out.println( "imageWidth: " + imageWidth );
			System.out.println( "imageHeight: " + imageHeight );
			System.out.println( "panelWidth: " + panelWidth );
			System.out.println( "panelHeight: " + panelHeight );
			System.out.println( "scaled_image_width: " + scaled_image_width );
			System.out.println( "scaled_image_height: " + scaled_image_height );
			System.out.println( "scale: " + scale );
			System.out.println( "---SlideShowPanel---" );
		}

		if( panelHeight - scaled_image_height > 1 ) {
			// whitespace on top and bottom
			final int buffersize = ( panelHeight - scaled_image_height ) / 2;
			g2.drawImage( image_, 0, buffersize, scaled_image_width, scaled_image_height, null );
			if( CompileTimeSettings.DEBUG_SLIDE_SHOW ) {
				System.out.println( "whitespace on top and bottom" );
				System.out.println( "panelHeight: " + panelHeight );
				System.out.println( "scaled_image_height: " + scaled_image_height );
				System.out.println( "buffersize: " + buffersize );
			}
		} else if( panelWidth - scaled_image_width > 1 ) {
			// whitespace on sides
			final int buffersize = ( panelWidth - scaled_image_width ) / 2;
			g2.drawImage( image_, buffersize, 0, scaled_image_width, scaled_image_height, null );
			if( CompileTimeSettings.DEBUG_SLIDE_SHOW ) {
				System.out.println( "whitespace on sides" );
				System.out.println( "panelWidth: " + panelWidth );
				System.out.println( "scaled_image_width: " + scaled_image_width );
				System.out.println( "buffersize: " + buffersize );
			}
		} else {
			if( CompileTimeSettings.DEBUG_SLIDE_SHOW ) {
				System.out.println( "no whitespace" );
			}
			g2.drawImage( image_, 0, 0, scaled_image_width, scaled_image_height, null );
		}

	}

	private double getScale( int panelWidth, int panelHeight, int imageWidth, int imageHeight ) {

		if( imageWidth > panelWidth || imageHeight > panelHeight ) {
			double xScale = ( (double) imageWidth ) / panelWidth;
			double yScale = ( (double) imageHeight ) / panelHeight;
			return Math.min( xScale, yScale );
		}

		if( imageWidth < panelWidth && imageHeight < panelHeight ) {
			double xScale = ( (double) panelWidth ) / imageWidth;
			double yScale = ( (double) panelHeight ) / imageHeight;
			return Math.min( xScale, yScale );
		}

		return 1;
	}

}
