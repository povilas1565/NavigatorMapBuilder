package com.example.navigator.navigatormapbuilder.map_builder.canvas.tools;

import com.example.navigator.navigatormapbuilder.map_builder.canvas.holder.Holder;
import com.example.navigator.navigatormapbuilder.map_builder.utils.common.ScreenTool;
import com.example.navigator.navigatormapbuilder.map_builder.canvas.holder.HolderManager;

import java.util.ArrayList;

public abstract class Tool implements ScreenTool {
	protected HolderManager manager;
	protected ArrayList<Holder> holders;

	public Tool(HolderManager manager) {
		this.manager = manager;
	}

}
