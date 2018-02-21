package graph;

import java.awt.image.BufferedImage;
//import java.util.Arrays;

public class Node implements NodeType {

	final String name_;
	int index_ = 0;
	
	private EdgeType[] upstream_edges_= new EdgeType[ 0 ];
	private EdgeType[] downstream_edges_= new EdgeType[ 0 ];
	
	public Node( String name ) {
		name_ = name;
	}
	
	@Override
	public String name() {
		return name_;
	}
	
	@Override
	public EdgeType[] getUpstreamEdges() {
		return upstream_edges_;
	}
	
	@Override
	public EdgeType[] getDownstreamEdges() {
		return downstream_edges_;
	}
	
	@Override
	public BufferedImage getNextImage() {
		return null;
	}

	@Override
	public void addUpstreamEdge(EdgeType E) { 
		EdgeType[] upstream_edges_new = new EdgeType[ upstream_edges_.length + 1 ];
		for( int i=0; i < upstream_edges_.length; ++i ) {
			upstream_edges_new[i] = upstream_edges_[i];
		}
		upstream_edges_new[ upstream_edges_.length ] = E;
		upstream_edges_ = upstream_edges_new;
		//Arrays.sort(upstream_edges_);
	}

	@Override
	public void addDownstreamEdge(EdgeType E) {
		EdgeType[] downstream_edges_new = new EdgeType[ downstream_edges_.length + 1 ];
		for( int i=0; i < downstream_edges_.length; ++i ) {
			downstream_edges_new[i] = downstream_edges_[i];
		}
		downstream_edges_new[ downstream_edges_.length ] = E;
		downstream_edges_ = downstream_edges_new;
		//Arrays.sort(downstream_edges_);
	}
	
	@Override
	public int index() {
		return index_;
	}

	@Override
	public void setIndex(int index) {
		index_ = index;
	}
	
}
