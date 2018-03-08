package graph;

//Comparable methods will only be used for sorting!
public interface EdgeType extends Comparable< EdgeType > {

	String name();

	int outgoingNodeIndex();

	int incomingNodeIndex();

	boolean hasImages();

	int index();

	void setIndex( int index );

}
