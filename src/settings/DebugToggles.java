package settings;

public class DebugToggles {

	private final static boolean DEBUG_ALL = false;

	public final static boolean DEBUG_APPLICATIONS = DEBUG_ALL || false;
	public final static boolean DEBUG_CONTROL_PANEL = DEBUG_ALL || false;
	public final static boolean DEBUG_FRAME_GRAPH = DEBUG_ALL || false;
	public final static boolean DEBUG_FRAME_RATE = DEBUG_ALL || false;
	public final static boolean DEBUG_GRAPH = DEBUG_ALL || false;
	public final static boolean DEBUG_KEYS = DEBUG_ALL || false;
	public final static boolean DEBUG_SLIDE_SHOW = DEBUG_ALL || false;
	public final static boolean DEBUG_SLIDE_SHOW_RENDER = DEBUG_SLIDE_SHOW || false;
	public final static boolean DEBUG_CONTROL_PANEL_VIEW = DEBUG_ALL || false;
}
