package com.example.navigator.navigatormapbuilder.map_builder.ui;

import javafx.scene.Node;

public class StyleBuilder {
	private StringBuilder buffer;

	public StyleBuilder() {
		buffer = new StringBuilder();
	}

	public StyleBuilder setBoarder(int top, int right, int bottom, int left){
		buffer.append("-fx-border-color: #757575;")
				.append(String.format("-fx-border-WIDTH: %d %d %d %d;", top, right, bottom, left));
		return this;
	}

	public StyleBuilder setMenuBgStyle(){
		buffer.append("-fx-background-color: #999");
		//buffer.append("-fx-background-color: #6a1b9a");
		return this;
	}

	public StyleBuilder setAccentBgStyle(){
		buffer.append("-fx-background-color: #666;");
		return this;
	}

	public void apply(Node target){
		target.setStyle(buffer.toString());
		buffer = new StringBuilder();
	}

	public void clear(){
		buffer = new StringBuilder();
	}

}
