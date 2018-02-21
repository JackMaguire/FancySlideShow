package graph;

//Comparable methods will only be used for sorting!
public interface EdgeType extends Comparable<EdgeType> {

	int outgoingNodeIndex();
	int incomingNodeIndex();
	
	boolean hasImages();
	
}
