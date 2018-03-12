package applications;

import java.util.HashMap;
import java.util.Map;

public class App {

	// private static String[] things_;
	private final static Map< String, StringArg > string_args_ = new HashMap< String, StringArg >();

	void init( String[] args ) {

		string_args_.put( "-cache_slides", new CacheSlides() );

		for( int i = 0; i < args.length; i++ ) {
			StringArg sa = string_args_.get( args[ i ] );
			if( sa != null && i != args.length - 1 ) {
				sa.run( args[ i + 1 ] );
				++i;
				continue;
			}
		}

	}

	private static abstract class StringArg {
		abstract void run( String s );
	}

	private static class CacheSlides extends StringArg {
		void run( String S ) {
			System.exit( 0 );
		}
	}

}
