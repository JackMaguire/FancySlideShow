package usage_statistics;
import java.util.*;
public class MemoryCounter {

	private final static MemoryCounter instance_ = new MemoryCounter();
	
	//private long bytes_for_buffered_images_; 
	private final Map< String, Long > bytes_for_token_ = new HashMap< String, Long >();
	
	public MemoryCounter() {}
	
	public static MemoryCounter getInstance() {
		return instance_;
	}
	
	public void addBytesForToken( String token, long bytes ) {
		if( ! bytes_for_token_.containsKey( token ) ) {
			bytes_for_token_.put( token, bytes );
		} else {
			bytes_for_token_.put( token, bytes_for_token_.get( token ) + bytes );
		}
	}
	
	public long getBytesForToken( String token ) {
		return bytes_for_token_.get( token );
	}
	
}
