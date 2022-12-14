package com.example.navigator.navigatormapbuilder.map_builder.canvas.tools;

import com.example.navigator.navigatormapbuilder.map_builder.canvas.holder.Holder;
import com.example.navigator.navigatormapbuilder.map_builder.canvas.holder.HolderManager;

public class StrokeTool extends Tool {

	public StrokeTool(HolderManager manager) {
		super(manager);
	}


	@Override
	public void onPressed(double x, double y) {
		holders = manager.selectAll(x, y);

		for(Holder h : holders){
			h.setStroke(manager.getCanvasProperties().getColor());
		}
	}

	@Override
	public void onDragged(double x, double y) {

	}


	@Override
	public void onReleased(double x, double y) {

	}
}
