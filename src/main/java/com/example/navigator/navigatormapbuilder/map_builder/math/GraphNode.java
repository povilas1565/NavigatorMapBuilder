package com.example.navigator.navigatormapbuilder.map_builder.math;

import java.util.List;

public interface GraphNode<T> {
	List<T> getNeighbors();

	String getHashName();
}
