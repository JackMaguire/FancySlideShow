package test;

public class FrameRateMeasurer {

	// private final static FrameRateMeasurer instance_ = new FrameRateMeasurer();
	private final static int num_measurements = 1024;
	private final static int max_bin_ = 100;

	private final static long[] measurements_ = new long[ num_measurements ];
	private static int id = 0;
	// private final static int[] counts_ = new int[ max_bin_size_ ];

	public FrameRateMeasurer() {
		/*for( int i = 0; i < counts_.length; ++i ) {
			counts_[ i ] = 0;
		}*/
	}

	public static void measure() {
		if( id < measurements_.length ) {
			measurements_[ id ] = System.currentTimeMillis();
		}
		++id;
		if( id == measurements_.length ) {
			report();
		}
	}

	public static void report() {
		int[] counts_ = new int[ max_bin_ ];
		for( int i = 0; i < counts_.length; ++i ) {
			counts_[ i ] = 0;
		}

		for( int i = 0; i < id - 1; ++i ) {
			final long diff = measurements_[ i + 1 ] - measurements_[ i ];
			if( diff >= max_bin_ ) {
				++counts_[ max_bin_ - 1 ];
			} else {
				++counts_[ (int) diff ];
			}
		}

		System.out.println( "delay\tcount" );
		for( int i = 0; i < max_bin_; ++i ) {
			System.out.println( i + "\t" + counts_[ i ] );
		}
	}
}
