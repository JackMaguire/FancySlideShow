package control_panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import compile_time_settings.DebugToggles;
import control_panel.EastPanelModel.BottomSideModel;
import frame_graph.FrameNode;
import graph.GraphType;
import graph.Node;
import graph.NodeType;
import compile_time_settings.ControlPanelMonitorSettings;

public class EastPanelView extends JPanel {

	private static final long serialVersionUID = 331403087034827832L;
	private final TopSide topside_;
	private final BottomSide bottomside_;

	private final EastPanelModel model_;

	public EastPanelView( EastPanelModel model ) {
		this.setMinimumSize( new Dimension( ControlPanelMonitorSettings.EAST_WIDTH, 100 ) );
		this.setPreferredSize( new Dimension( ControlPanelMonitorSettings.EAST_WIDTH, 100 ) );

		model_ = model;
		topside_ = new TopSide();

		bottomside_ = new BottomSide( model_.bottomModel() );

		this.setLayout( new GridLayout( 2, 1 ) );
		add( topside_ );
		add( bottomside_ );
	}

	public void setSelectedNode( NodeType node ) {
		topside_.setNode( node );
		bottomside_.reinit( node );
		repaint();
	}

	public static class TopSide extends JPanelWithKeyListener {

		private static final long serialVersionUID = 7212467413352884098L;

		private int previous_width_ = 0;
		private int previous_height_ = 0;
		private int previous_node_ = 0;

		private NodeType node_;

		public TopSide() {
		}

		public void setNode( NodeType node ) {
			node_ = node;
		}

		private double getScale( int panelD, int imageD ) {
			return ( (double) panelD ) / imageD;
		}

		private double getScale( int panel_width, int panel_height, int image_width, int image_height ) {
			return Math.min( getScale( panel_width, image_width ), getScale( panel_height, image_height ) );
		}

		public void paintComponent( Graphics g ) {
			if( node_ == null )
				return;

			final BufferedImage image = node_.getThumbnailImage();

			if( image == null )
				return;

			previous_width_ = getWidth();
			previous_height_ = getHeight();
			previous_node_ = node_.index();

			super.paintComponent( g );
			// super.paint( g );

			Graphics2D g2 = (Graphics2D) g;

			// int panelWidth = this.getWidth();
			// int panelHeight = this.getHeight();
			final int imageWidth = image.getWidth();
			final int imageHeight = image.getHeight();
			final double scale = getScale( previous_width_, previous_height_, imageWidth, imageHeight );
			//final double scale = 1;

			// g2.drawImage( image, 0, 0, (int) ( imageWidth * scale ), (int) ( imageHeight
			// * scale ), null );
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

	public static class BottomSide extends JPanelWithKeyListener {

		private static final long serialVersionUID = 7222467413252884098L;

		private final JLabel jlabel_ = new JLabel( "_" );
		private final JComboBox< String > forward_ = new JComboBox< String >();
		private final JComboBox< String > reverse_ = new JComboBox< String >();

		private final BottomSideModel model_;

		private final JLabel mem_usage_ = new JLabel( "_ " );

		public BottomSide( BottomSideModel model ) {
			model_ = model;
			initComponents();
		}

		public BottomSide( BottomSideModel model, NodeType node ) {
			model_ = model;
			initComponents();
			reinit( node );
		}

		private void initComponents() {
			setLayout( new GridLayout( 5, 2 ) );
			add( jlabel_ );
			add( new JLabel( " " ) );

			add( new JLabel( "Forward: " ) );
			add( forward_ );

			add( new JLabel( "Reverse: " ) );
			add( reverse_ );

			JButton remove_all_focus = new JButton( "Clear Focus" );
			remove_all_focus.addActionListener( new RemoveFocusListener( this ) );
			add( remove_all_focus );

			JButton garbage_collection = new JButton( "Run Garbage Collection" );
			garbage_collection.addActionListener( new RunGarbageCollection() );
			add( garbage_collection );

			JButton calc_mem = new JButton( "Calc Mem Usage:" );
			calc_mem.addActionListener( new MemCalc( mem_usage_ ) );
			add( calc_mem );
			add( mem_usage_ );

			forward_.addItemListener( new ForwardJComboBoxListener( forward_, model_ ) );
			reverse_.addItemListener( new ReverseJComboBoxListener( reverse_, model_ ) );
		}

		public void reinit( NodeType node ) {
			model_.setNode( node );
			jlabel_.setText( model_.getTitle() );

			forward_.removeAllItems();
			for( String s : model_.getForwardChoices() ) {
				forward_.addItem( s );
			}
			forward_.setSelectedIndex( model_.getForwardChoice() );

			reverse_.removeAllItems();
			for( String s : model_.getReverseChoices() ) {
				reverse_.addItem( s );
			}
			reverse_.setSelectedIndex( model_.getReverseChoice() );
		}

	}

	protected static class ForwardJComboBoxListener implements ItemListener {

		private final BottomSideModel model_;
		private final JComboBox< String > forward_;

		public ForwardJComboBoxListener( JComboBox< String > forward, BottomSideModel model ) {
			model_ = model;
			forward_ = forward;
		}

		@Override
		public void itemStateChanged( ItemEvent e ) {
			if( e.getStateChange() == ItemEvent.SELECTED ) {
				model_.setForwardChoice( forward_.getSelectedIndex() );
			}
		}

	}

	protected static class ReverseJComboBoxListener implements ItemListener {

		private final BottomSideModel model_;
		private final JComboBox< String > reverse_;

		public ReverseJComboBoxListener( JComboBox< String > reverse, BottomSideModel model ) {
			model_ = model;
			reverse_ = reverse;
		}

		@Override
		public void itemStateChanged( ItemEvent e ) {
			if( e.getStateChange() == ItemEvent.SELECTED ) {
				model_.setReverseChoice( reverse_.getSelectedIndex() );
			}
		}

	}

	protected static class RemoveFocusListener implements ActionListener {

		private final JPanelWithKeyListener owner_;

		public RemoveFocusListener( JPanelWithKeyListener owner ) {
			owner_ = owner;
		}

		@Override
		public void actionPerformed( ActionEvent e ) {
			owner_.requestFocus();
		}

	}

	protected static class RunGarbageCollection implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ) {
			System.gc();
		}

	}

	protected static class MemCalc implements ActionListener {

		private final JLabel mem_label_;

		public MemCalc( JLabel mem_label ) {
			mem_label_ = mem_label;
		}

		@Override
		public void actionPerformed( ActionEvent e ) {
			mem_label_.setText( "" + ( ( Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() ) / 1000000 ) + " MB");
		}

	}

}
