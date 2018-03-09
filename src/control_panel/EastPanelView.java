package control_panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import compile_time_settings.DebugToggles;
import compile_time_settings.ControlPanelMonitorSettings;

public class EastPanelView extends JPanel {

	private static final long serialVersionUID = 331403087034827832L;
	private final TopSide topside_;
	private final BottomSide bottomside_ = new BottomSide();

	private final EastPanelModel model_;

	public EastPanelView( EastPanelModel model ) {
		this.setMinimumSize( new Dimension( ControlPanelMonitorSettings.EAST_WIDTH, 100 ) );
		this.setPreferredSize( new Dimension( ControlPanelMonitorSettings.EAST_WIDTH, 100 ) );

		model_ = model;
		topside_ = new TopSide( model_.centerModel() );

		this.setLayout( new GridLayout( 2, 1 ) );
		add( topside_ );
		add( bottomside_ );
	}

	public static class TopSide extends JPanelWithKeyListener {

		private static final long serialVersionUID = 7212467413352884098L;
		private final CenterPanelModel center_panel_model_;

		private int previous_width_ = 0;
		private int previous_height_ = 0;
		private int previous_node_ = 0;

		public TopSide( CenterPanelModel center_panel_model ) {
			center_panel_model_ = center_panel_model;
		}

		private double getScale( int panelWidth, int panelHeight, int imageWidth, int imageHeight ) {

			if( imageWidth < panelWidth || imageHeight < panelHeight ) {
				double xScale = ( (double) imageWidth ) / panelWidth;
				double yScale = ( (double) imageHeight ) / panelHeight;
				return Math.min( xScale, yScale );
			}

			if( imageWidth > panelWidth && imageHeight > panelHeight ) {
				double xScale = ( (double) panelWidth ) / imageWidth;
				double yScale = ( (double) panelHeight ) / imageHeight;
				return Math.min( xScale, yScale );
			}

			return 1;
		}

		public void paintComponent( Graphics g ) {
			// avoid redoing work if possible
			// g.fillRect( 0, 0, 100, 100 );
			if( getWidth() == previous_width_ && getHeight() == previous_height_
					&& center_panel_model_.selectedNode().index() == previous_node_ ) {
				return;
			}
			
			if( center_panel_model_.selectedNode() == null )
				return;

			final BufferedImage image = center_panel_model_.selectedNode().getThumbnailImage();
	
			if( image == null )
				return;

			previous_width_ = getWidth();
			previous_height_ = getHeight();
			previous_node_ = center_panel_model_.selectedNode().index();

			super.paintComponent( g );
			// super.paint( g );

			Graphics2D g2 = (Graphics2D) g;

			// int panelWidth = this.getWidth();
			// int panelHeight = this.getHeight();
			final int imageWidth = image.getWidth();
			final int imageHeight = image.getHeight();
			//final double scale = getScale( previous_width_, previous_height_, imageWidth, imageHeight );
			final double scale = 1;
			
			//g2.drawImage( image, 0, 0, (int) ( imageWidth * scale ), (int) ( imageHeight * scale ), null );
			g2.drawImage( image, 0, 0, imageWidth, imageHeight, null );
	    
			if( DebugToggles.DEBUG_CONTROL_PANEL_VIEW ) {
				System.out.println( "---North East---" );
				System.out.println( "panel width: " + previous_width_ );
				System.out.println( "panel height: " + previous_height_ );
				System.out.println( "image width: " + imageWidth );
				System.out.println( "image height: " + imageHeight );
				System.out.println( "scale: " + scale );
				System.out.println( "scaled image width: " + (int) ( imageWidth * scale ) );
				System.out.println( "scaled image height: " + (int) ( imageHeight * scale ) );
				System.out.println( "---North East---" );
			}
		}
	}

	public static class BottomSide extends JPanel {

		private static final long serialVersionUID = 7222467413252884098L;

		public BottomSide() {
			//this.add( new JButton( "                                    " ) );
		}

	}

}
