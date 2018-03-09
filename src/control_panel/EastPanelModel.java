package control_panel;

import graph.NodeType;

public class EastPanelModel {

	private final CenterPanelModel parent_model_;

	private final BottomSideModel bottom_model_ = new BottomSideModel();
	
	public EastPanelModel( CenterPanelModel model ) {
		parent_model_ = model;
	}

	public CenterPanelModel centerModel() {
		return parent_model_;
	}
	
	public BottomSideModel bottomModel() {
		return bottom_model_;
	}
	
	protected static class BottomSideModel {
		private NodeType node_;
		String[] forward_choices_ = new String[ 0 ];
		String[] reverse_choices_ = new String[ 0 ];
		
		public NodeType node() {
			return node_;
		}
		
		public void setNode( NodeType node ) {
			node_ = node;
			
			//forward_choices_ = node.getFrameNode().
		}
		
		public String getTitle() {
			if( node_ != null )
				return node_.name();
			else
				return "";
		}
	}
	
}
