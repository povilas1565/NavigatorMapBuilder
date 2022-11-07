package com.example.navigator.navigatormapbuilder.map_builder.canvas.node;

import com.example.navigator.navigatormapbuilder.map_builder.core.navigation.Node;
import com.example.navigator.navigatormapbuilder.map_builder.ui.dialogs.DialogViewer;
import javafx.scene.Parent;
import com.example.navigator.navigatormapbuilder.map_builder.canvas.holder.HolderManager;
import com.example.navigator.navigatormapbuilder.map_builder.canvas.tools.Tool;

public class NodeTool extends Tool {

	public NodeTool(HolderManager manager) {
		super(manager);
	}


	@Override
	public void onPressed(double x, double y) {
		if(manager.hasAccess(x, y)) {
			Node target = new Node(x, y);

			NodeHolder nodeHolder = new NodeHolder(target);
			manager.push(nodeHolder);

			String title = "New node";
			Parent layout = nodeHolder.getInfo(manager.getManager());

			DialogViewer.showDialog(title,layout);
		}
	}

	@Override
	public void onDragged(double x, double y) {

	}


	@Override
	public void onReleased(double x, double y) {

	}



}
