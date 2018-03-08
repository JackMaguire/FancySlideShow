package usage_statistics;
import java.util.*;
public class MemoryCounter {

	private final static MemoryCounter instance_ = new MemoryCounter();
	
	//private long bytes_for_buffered_images_; 
	private final Map< String, Integer > bytes_for_token_ = new HashMap< String, Integer >();
	
	public MemoryCounter() {}
	
	public MemoryCounter getInstance() {
		return instance_;
	}
	
	public void addBytesForToken( String token, int bytes ) {
		if( ! bytes_for_token_.containsKey( token ) ) {
			bytes_for_token_.put( token, bytes );
		} else {
			bytes_for_token_.put( token, bytes_for_token_.get( token ) + bytes );
		}
	}
	
	public int getBytesForToken( String token ) {
		return bytes_for_token_.get( token );
	}
	
}
