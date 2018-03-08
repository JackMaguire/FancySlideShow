package control_panel;

public class ControlPanelModel {

	private final WestPanelModel west_;
	private final CenterPanelModel center_;
	private final EastPanelModel east_;

	public ControlPanelModel( graph.Graph graph ) {
		center_ = new CenterPanelModel( graph );
		west_ = new WestPanelModel();
		east_ = new EastPanelModel( center_ );
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
