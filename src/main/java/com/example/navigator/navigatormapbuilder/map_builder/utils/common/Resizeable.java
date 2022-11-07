package com.example.navigator.navigatormapbuilder.map_builder.utils.common;

@Deprecated
public interface Resizeable {
	void beginResize(double x, double y);
	void resize(double x, double y);
	void endResize(double x, double y);
}
