package graph;

import java.util.ArrayList;

//import java.awt.image.DataBuffer;

import compile_time_settings.DebugToggles;
import frame_graph.FrameGraph;

public class Graph implements GraphType {

	private final NodeType[] nodes_;
	private EdgeType[] edges_ = new EdgeType[ 0 ];

	private final ArrayList< Integer >[] subgraphs_;
	private final String[] subgraph_names_;

	public Graph( int num_nodes ) {
		this( num_nodes, 1 );
	}

	public Graph( int num_nodes, int num_subgraphs ) {
		nodes_ = new NodeType[ num_nodes ];
		subgraphs_ = new ArrayList[ num_subgraphs ];
		for( int i = 0; i < subgraphs_.length; ++i ) {
			subgraphs_[ i ] = new ArrayList< Integer >();
		}

		subgraph_names_ = new String[ num_subgraphs ];
		for( int i = 0; i < subgraph_names_.length; ++i ) {
			subgraph_names_[ i ] = "" + i;
		}
	}

	public void setSubgraphNames( String[] names ) {
		final int num_elements_to_copy = Math.min( names.length, subgraph_names_.length );
		for( int i = 0; i < num_elements_to_copy; ++i ) {
			subgraph_names_[ i ] = names[ i ];
		}
	}

	public void setSubgraphName( int subgraph, String name ) {
		subgraph_names_[ subgraph ] = name;
	}
	
	public String getNamesforSubgraph( int subgraph ) {
		return subgraph_names_[ subgraph ];
	}

	@Override
	public int numSubgraphs() {
		return subgraphs_.length;
	}

	@Override
	public ArrayList< Integer > getNodesForSubgraph( int subgraph ) {
		return subgraphs_[ subgraph ];
	}

	@Override
	public int numNodes() {
		return nodes_.length;
	}

	public void setNode( NodeType node, int node_index ) {
		setNode( node, node_index, 0 );
	}

	@Override
	public void setNode( NodeType node, int node_index, int subgraph ) {
		node.setIndex( node_index, subgraph );
		nodes_[ node_index ] = node;
		subgraphs_[ subgraph ].add( node_index );
	}

	@Override
	public NodeType getNode( int node_index ) {
		return nodes_[ node_index ];
	}

	@Override
	public void addEdge( EdgeType edge ) {
		nodes_[ edge.incomingNodeIndex() ].addUpstreamEdge( edge );
		nodes_[ edge.outgoingNodeIndex() ].addDownstreamEdge( edge );
		registerEdge( edge );
	}

	@Override
	public EdgeType getEdge( int outgoing_node_index, int incoming_node_index ) {
		EdgeType[] edges = nodes_[ outgoing_node_index ].getDownstreamEdges();
		for( EdgeType edge : edges ) {
			if( edge.incomingNodeIndex() == incoming_node_index ) {
				return edge;
			}
		}
		return null;
	}

	public NodeType[] getNodes() {
		return nodes_;
	}

	@Override
	public EdgeType[] getEdges() {
		return edges_;
	}

	private void registerEdge( EdgeType E ) {
		EdgeType[] edges_new = new EdgeType[ edges_.length + 1 ];
		for( int i = 0; i < edges_.length; ++i ) {
			edges_new[ i ] = edges_[ i ];
		}
		edges_new[ edges_.length ] = E;
		E.setIndex( edges_.length );
		edges_ = edges_new;
	}

	@Override
	public FrameGraph createFrameGraph() {
		FrameGraph fg = new FrameGraph( nodes_ );

		for( NodeType node : nodes_ ) {
			node.applyToFrameGraph( fg );
		}

		for( EdgeType edge : edges_ ) {
			edge.applyToFrameGraph( fg );
		}

		if( DebugToggles.DEBUG_FRAME_GRAPH ) {
			final long primary = usage_statistics.MemoryCounter.getInstance().getBytesForToken( "PrimaryFGNodes" );
			final long secondary = usage_statistics.MemoryCounter.getInstance().getBytesForToken( "SecondaryFGNodes" );
			System.out.println( "Bytes required for FG: " + ( primary + secondary ) );
			System.out.println( "\tPrimary:" + primary );
			System.out.println( "\tSecondary:" + secondary );
		}
		return fg;
	}

	@Override
	public int numNodesInSubgraph( int subgraph ) {
		return subgraphs_[ subgraph ].size();
	}
}
