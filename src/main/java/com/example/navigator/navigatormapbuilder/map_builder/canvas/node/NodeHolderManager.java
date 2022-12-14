package com.example.navigator.navigatormapbuilder.map_builder.canvas.node;

import com.example.navigator.navigatormapbuilder.map_builder.core.navigation.Node;
import javafx.scene.layout.Pane;
import com.example.navigator.navigatormapbuilder.map_builder.canvas.holder.Holder;

import java.util.ArrayList;

public class NodeHolderManager {
	private ArrayList<Node> nodeSystem;
	private ArrayList<Holder> nodeHolders;

	public NodeHolderManager(ArrayList<Node> nodeSystem) {
		this.nodeSystem = nodeSystem;
		nodeHolders = new ArrayList<>();
	}


	public Holder select(double x, double y) {
		return nodeHolders.stream()
				.filter(v -> v.contains(x, y))
				.findFirst()
				.orElse(null);
	}

	public void push(Holder holder) {
		nodeHolders.add(holder);
		if (holder instanceof NodeHolder) {
			NodeHolder nodeHolder = (NodeHolder) holder;
			nodeSystem.add(nodeHolder.getTarget());
		}
	}

	public void remove(Holder holder, Pane[] layers){
		nodeHolders.remove(holder);
		holder.remove(layers);
		if(holder instanceof  NodeHolder){
			NodeHolder nodeHolder = (NodeHolder) holder;
			nodeSystem.remove(nodeHolder.getTarget());

			ArrayList<NodeConnectionHolder> attachedConnections
					= new ArrayList<>(nodeHolder.getAttachedConnections());
			for (NodeConnectionHolder connectionHolder : attachedConnections) {
				nodeHolders.remove(connectionHolder);
				connectionHolder.remove(layers);
			}
		}

	}


	public NodeHolder selectHolder(double x, double y) {
		return nodeHolders.stream()
				.filter(v -> v instanceof NodeHolder)
				.filter(v -> v.contains(x, y))
				.map(e -> (NodeHolder) e)
				.findFirst()
				.orElse(null);
	}

	public boolean contains(Holder holder){
		return nodeHolders.contains(holder);
	}
}
