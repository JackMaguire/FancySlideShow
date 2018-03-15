package graph;

//import java.awt.image.DataBuffer;

import compile_time_settings.DebugToggles;
import frame_graph.FrameGraph;

public class ConceptualGraph implements ConceptualGraphType {

	private final NodeType[] nodes_;
	private ConceptualEdgeType[] edges_ = new ConceptualEdgeType[ 0 ];

	// private final ArrayList< ArrayList< Integer > > subgraph_ids_;
	private final int[] num_nodes_in_subgraph_;
	private final String[] subgraph_names_;

	public ConceptualGraph( int num_nodes ) {
		this( num_nodes, 1 );
	}

	public ConceptualGraph( int num_nodes, int num_subgraphs ) {
		nodes_ = new NodeType[ num_nodes ];
		/*subgraph_ids_ = new ArrayList< ArrayList< Integer > >( num_subgraphs );
		for( int i = 0; i < subgraph_ids_.size(); ++i ) {
			subgraph_ids_.set( i, new ArrayList< Integer >() );
		}*/

		num_nodes_in_subgraph_ = new int[ num_subgraphs ];
		for( int i = 0; i < num_subgraphs; ++i ) {
			num_nodes_in_subgraph_[ i ] = 0;
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
		return num_nodes_in_subgraph_.length;
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
		++num_nodes_in_subgraph_[ subgraph ];
	}

	@Override
	public NodeType getNode( int node_index ) {
		return nodes_[ node_index ];
	}

	@Override
	public void addEdge( ConceptualEdgeType edge ) {
		nodes_[ edge.incomingNodeIndex() ].addUpstreamEdge( edge );
		nodes_[ edge.outgoingNodeIndex() ].addDownstreamEdge( edge );
		registerEdge( edge );
	}

	@Override
	public ConceptualEdgeType getEdge( int outgoing_node_index, int incoming_node_index ) {
		ConceptualEdgeType[] edges = nodes_[ outgoing_node_index ].getDownstreamEdges();
		for( ConceptualEdgeType edge : edges ) {
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
	public ConceptualEdgeType[] getEdges() {
		return edges_;
	}

	private void registerEdge( ConceptualEdgeType E ) {
		ConceptualEdgeType[] edges_new = new ConceptualEdgeType[ edges_.length + 1 ];
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

		for( ConceptualEdgeType edge : edges_ ) {
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
		return num_nodes_in_subgraph_[ subgraph ];
	}
}
