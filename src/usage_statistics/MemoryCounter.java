package usage_statistics;

import java.util.*;

public class MemoryCounter {

	private final static MemoryCounter instance_ = new MemoryCounter();
	private final Map< String, Long > bytes_for_token_ = new HashMap< String, Long >();

	public MemoryCounter() {
		Runtime.getRuntime().addShutdownHook( new shutdown_hook( bytes_for_token_ ) );
	}

	public static MemoryCounter getInstance() {
		return instance_;
	}

	public void addBytesForToken( String token, long bytes ) {
		if( !bytes_for_token_.containsKey( token ) ) {
			bytes_for_token_.put( token, bytes );
		} else {
			bytes_for_token_.put( token, bytes_for_token_.get( token ) + bytes );
		}
	}

	public long getBytesForToken( String token ) {
		if( !bytes_for_token_.containsKey( token ) )
			return 0;
		return bytes_for_token_.get( token );
	}

	private static class shutdown_hook extends Thread {

		private final Map< String, Long > bytes_for_token_;

		public shutdown_hook( Map< String, Long > bytes_for_token ) {
			bytes_for_token_ = bytes_for_token;
		}

		public void run() {
			Iterator< ? > it = bytes_for_token_.entrySet().iterator();
			System.out.println( "Memory usage:" );
			while ( it.hasNext() ) {
				Map.Entry pair = (Map.Entry) it.next();
				System.out.println( "\t" + pair.getKey() + " = " + pair.getValue() );
			}
		}
	}

}
