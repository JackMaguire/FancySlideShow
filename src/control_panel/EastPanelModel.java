package control_panel;

public class EastPanelModel {

	private final CenterPanelModel center_model_;
	
	public EastPanelModel( CenterPanelModel model ) {
		center_model_ = model;
	}
	
	public CenterPanelModel centerModel() {
		return center_model_;
	}
}
