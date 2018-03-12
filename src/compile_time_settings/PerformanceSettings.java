package compile_time_settings;

public class PerformanceSettings {

	// Frame Caching
	public final static double PRIMARY_NODE_CACHE_RATIO = 1.0;// 0 means use following:
	public final static int PRI_MAX_WIDTH = 1024;
	public final static int PRI_MAX_HEIGHT = 1024;

	public final static double SECONDARY_NODE_CACHE_RATIO = 1.0;// 0 means use following:
	public final static int SEC_MAX_WIDTH = 1024;
	public final static int SEC_MAX_HEIGHT = 1024;

	public final static boolean DELETE_CACHES = true;
	public final static boolean LOAD_CACHES = false;
}
