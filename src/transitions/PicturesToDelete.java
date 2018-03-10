package transitions;

import java.io.File;
import java.util.ArrayList;

public class PicturesToDelete {

	private final static ArrayList< String > filenames_ = new ArrayList< String >();
	private static boolean hook_has_been_added_ = false;

	public void registerFile( String filename ) {
		filenames_.add( filename );
		if( !hook_has_been_added_ ) {
			Runtime.getRuntime().addShutdownHook( new shutdown_hook( filenames_ ) );
			hook_has_been_added_ = true;
		}
	}

	private static class shutdown_hook extends Thread {

		private final ArrayList< String > filenames_;

		public shutdown_hook( ArrayList< String > filenames ) {
			filenames_ = filenames;
		}

		public void run() {
			for( String S : filenames_ ) {
				File f = new File( S );
				f.delete();
			}
		}
	}
}
