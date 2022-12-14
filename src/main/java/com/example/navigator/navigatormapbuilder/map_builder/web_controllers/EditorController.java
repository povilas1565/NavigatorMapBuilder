package com.example.navigator.navigatormapbuilder.map_builder.web_controllers;

import com.example.navigator.navigatormapbuilder.map_builder.core.Level;
import com.example.navigator.navigatormapbuilder.map_builder.core.Project;
import com.example.navigator.navigatormapbuilder.map_builder.core.ProjectModel;
import com.example.navigator.navigatormapbuilder.map_builder.io.Serializer;
import com.example.navigator.navigatormapbuilder.map_builder.web_controllers.response.BaseResponse;

import java.util.Map;

public class EditorController implements Controller {

	private final ProjectUpdateController updateController;
	private static final EditorController instance = new EditorController();

	public static EditorController getInstance() {
		return instance;
	}

	private EditorController() {
		updateController = ProjectUpdateController.getInstance();
	}

	public EditorController setCredentials(Project project) {
		ProjectModel model = project.getModel();

		Serializer serializer = new Serializer();
		String body = serializer.writeProject(project);

		model.setBody(body);
		updateController.setMethod(ProjectUpdateController.UPDATE)
				.setCredentials(model);
		return this;
	}

	public EditorController setCredentials(Project project, Map<Level,byte[]> map) {
		ProjectModel model = project.getModel();

		Serializer serializer = new Serializer();
		String body = serializer.writeProject(project,map);

		model.setBody(body);
		updateController.setMethod(ProjectUpdateController.UPDATE)
				.setCredentials(model);
		return this;
	}

	@Override
	public BaseResponse execute() {
		return updateController.execute();
	}
}
