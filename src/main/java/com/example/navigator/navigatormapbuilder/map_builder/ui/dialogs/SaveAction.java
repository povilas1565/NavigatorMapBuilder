package com.example.navigator.navigatormapbuilder.map_builder.ui.dialogs;

import com.example.navigator.navigatormapbuilder.map_builder.core.Project;
import com.example.navigator.navigatormapbuilder.map_builder.io.Serializer;
import com.example.navigator.navigatormapbuilder.map_builder.utils.StringUtils;
import com.example.navigator.navigatormapbuilder.map_builder.utils.common.ProjectAsyncAction;
import com.example.navigator.navigatormapbuilder.map_builder.web_controllers.EditorController;
import com.example.navigator.navigatormapbuilder.map_builder.web_controllers.response.BaseResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class SaveAction extends ProjectAsyncAction {

	private final Project project;
	private boolean localSave;

	public SaveAction(Project project) {
		super(project);
		this.project = project;

	}

	@Override
	public void run() {
		try {
			if (localSave) {
				try {
					//saves locally
					Serializer serializer = new Serializer();
					String res = serializer.writeProject(project, levelMap);

					File save = new File("saves/" + StringUtils.nextHashName() + ".json");
					PrintStream printStream = new PrintStream(save);
					printStream.print(res);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			EditorController editorController = EditorController.getInstance();
			BaseResponse response = editorController.setCredentials(project, levelMap)
					.execute();

			System.out.println(response.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error while saving");
		}
		super.run();
	}

	public boolean isLocalSave() {
		return localSave;
	}

	public SaveAction setLocalSave(boolean localSave) {
		this.localSave = localSave;
		return this;
	}


}
