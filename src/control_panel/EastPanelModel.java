package control_panel;

import frame_graph.FrameNode;
import graph.ConceptualGraphType;
import graph.ConceptualNodeType;

public class EastPanelModel {

	private final CenterPanelModel parent_model_;

	private final BottomSideModel bottom_model_;
	private final ConceptualGraphType graph_;

	public EastPanelModel( CenterPanelModel model, ConceptualGraphType graph ) {
		parent_model_ = model;
		graph_ = graph;
		bottom_model_ = new BottomSideModel( graph_ );
	}

	public CenterPanelModel centerModel() {
		return parent_model_;
	}

	public BottomSideModel bottomModel() {
		return bottom_model_;
	}

	protected static class BottomSideModel {
		private ConceptualNodeType node_;
		private String[] forward_choices_ = new String[ 0 ];
		private int[] forward_ints_ = new int[ 0 ];
		private int for_choice_ = -1;

		private String[] reverse_choices_ = new String[ 0 ];
		private int[] reverse_ints_ = new int[ 0 ];
		private int rev_choice_ = -1;

		private final ConceptualGraphType graph_;

		public BottomSideModel( ConceptualGraphType graph ) {
			graph_ = graph;
		}

		public ConceptualNodeType node() {
			return node_;
		}

		public void setNode( ConceptualNodeType node ) {
			node_ = node;

			forward_ints_ = node.getFrameNode().upstreamIDs();
			forward_choices_ = new String[ forward_ints_.length ];
			for( int i = 0; i < forward_ints_.length; ++i ) {
				forward_choices_[ i ] = graph_.getNode( forward_ints_[ i ] ).name();
			}

			FrameNode fnode = node.getFrameNode().forwardNode();
			for_choice_ = -1;
			if( fnode != null ) {
				int upstream_id = fnode.UPSTREAM_PRIMARY_ID;
				for( int i = 0; i < forward_ints_.length; ++i ) {
					if( forward_ints_[ i ] == upstream_id ) {
						for_choice_ = i;
					}
				}
			}

			reverse_ints_ = node.getFrameNode().downstreamIDs();
			reverse_choices_ = new String[ reverse_ints_.length ];
			for( int i = 0; i < reverse_ints_.length; ++i ) {
				reverse_choices_[ i ] = graph_.getNode( reverse_ints_[ i ] ).name();
			}

			FrameNode rnode = node.getFrameNode().reverseNode();
			rev_choice_ = -1;
			if( rnode != null ) {
				int downstream_id = rnode.DOWNSTREAM_PRIMARY_ID;
				for( int i = 0; i < reverse_ints_.length; ++i ) {
					if( reverse_ints_[ i ] == downstream_id ) {
						rev_choice_ = i;
					}
				}
			}
		}

		public String getTitle() {
			if( node_ != null )
				return node_.name();
			else
				return "";
		}

		public String[] getForwardChoices() {
			return forward_choices_;
		}

		public int getForwardChoice() {
			return for_choice_;
		}

		public void setForwardChoice( int choice ) {
			for_choice_ = choice;
			node_.getFrameNode().setForwardNode( forward_ints_[ choice ] );
		}

		public String[] getReverseChoices() {
			return reverse_choices_;
		}

		public int getReverseChoice() {
			return rev_choice_;
		}

		public void setReverseChoice( int rev_choice ) {
			rev_choice_ = rev_choice;
			node_.getFrameNode().setReverseNode( reverse_ints_[ rev_choice ] );
		}
	}

}
