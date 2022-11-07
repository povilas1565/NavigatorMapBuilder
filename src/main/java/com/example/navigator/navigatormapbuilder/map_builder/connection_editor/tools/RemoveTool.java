package com.example.navigator.navigatormapbuilder.map_builder.connection_editor.tools;

import com.example.navigator.navigatormapbuilder.map_builder.connection_editor.LayersName;
import com.example.navigator.navigatormapbuilder.map_builder.connection_editor.node.LevelConnectionEntity;
import com.example.navigator.navigatormapbuilder.map_builder.connection_editor.node.LevelEntity;
import com.example.navigator.navigatormapbuilder.map_builder.connection_editor.node.LevelEntityManager;
import com.example.navigator.navigatormapbuilder.map_builder.utils.common.ScreenTool;

public class RemoveTool implements ScreenTool {
	private LevelEntityManager manager;

	public RemoveTool(LevelEntityManager manager) {
		this.manager = manager;
	}

	@Override
	public void onPressed(double x, double y) {
		LevelConnectionEntity connection = manager.selectConnection(x, y);

		if(connection != null) {
			LevelEntity first = connection.getBeginEntity();
			LevelEntity second = connection.getEndEntity();


			connection.getConnection().dispose();

			first.removeConnection(connection);
			second.removeConnection(connection);

			manager.remove(connection);
			manager.getLayers()[LayersName.CONNECTIONS].getChildren()
					.remove(connection.getLine());
		}
	}

	@Override
	public void onDragged(double x, double y) {

	}

	@Override
	public void onReleased(double x, double y) {

	}
}
