package control_panel;

public class ControlPanelModel {

	private WestPanelModel west_;
	private CenterPanelModel center_;
	private EastPanelModel east_;
	
	public ControlPanelModel( graph.Graph graph ) {
		west_ = new WestPanelModel();
		center_ = new CenterPanelModel( graph );
		east_ = new EastPanelModel();
	}
	
	public WestPanelModel getWestPanelModel() {
		return west_;
	}
	
	public CenterPanelModel getCenterPanelModel() {
		return center_;
	}
	
	public EastPanelModel getEastPanelModel() {
		return east_;
	}

}
