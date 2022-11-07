package com.example.navigator.navigatormapbuilder.map_builder.canvas.tools;

import com.example.navigator.navigatormapbuilder.map_builder.canvas.holder.Holder;
import com.example.navigator.navigatormapbuilder.map_builder.ui.dialogs.DialogViewer;
import javafx.scene.control.ScrollPane;
import com.example.navigator.navigatormapbuilder.map_builder.canvas.holder.HolderManager;

public class InfoTool extends Tool {

	public InfoTool(HolderManager manager) {
		super(manager);
	}


	@Override
	public void onPressed(double x, double y) {
		holders = manager.selectAll(x, y);

		for (Holder h : holders) {
			showDialog(h);
		}
	}

	@Override
	public void onDragged(double x, double y) {

	}


	@Override
	public void onReleased(double x, double y) {

	}

	private void showDialog(Holder holder) {
		String titleName = holder.toString();

		ScrollPane layout = holder.getInfo(manager.getManager());
		if (layout != null) {
			DialogViewer.showDialog(titleName, layout);
		}
	}
}
