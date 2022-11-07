package com.example.navigator.navigatormapbuilder.map_builder.utils.common;

import com.example.navigator.navigatormapbuilder.map_builder.core.Level;
import com.example.navigator.navigatormapbuilder.map_builder.core.Project;
import com.example.navigator.navigatormapbuilder.map_builder.utils.ImageUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public abstract class ProjectAsyncAction extends AsyncAction{
	protected final Map<Level, byte[]> levelMap;

	public ProjectAsyncAction(Project project) {
		levelMap = new HashMap<>();
		initLevelMap(project);
	}


	protected void initLevelMap(Project project) {
		for (Level level : project.getLevels()) {
			Image background = level.getBackground();
			int width = (int) background.getWidth();
			int height = (int) background.getHeight();
			byte[] base64Image = ImageUtils.getImageFromNode(new ImageView(background), width, height, false);
			levelMap.put(level, base64Image);
		}
	}
}
