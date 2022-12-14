package com.example.navigator.navigatormapbuilder.map_builder.canvas.tools;

import com.example.navigator.navigatormapbuilder.map_builder.canvas.holder.Holder;
import com.example.navigator.navigatormapbuilder.map_builder.canvas.holder.HolderManager;

public class RemoveTool extends Tool {

	public RemoveTool(HolderManager manager) {
		super(manager);
	}

	@Override
	public void onPressed(double x, double y) {
		Holder holder = manager.selectFirst(x,y);
		if(holder != null) {
			manager.remove(holder);
		}
	}

	@Override
	public void onDragged(double x, double y) {

	}

	@Override
	public void onReleased(double x, double y) {

	}


}
