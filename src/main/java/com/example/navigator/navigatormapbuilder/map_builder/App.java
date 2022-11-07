package com.example.navigator.navigatormapbuilder.map_builder;

import com.example.navigator.navigatormapbuilder.map_builder.ui.control.Navigator;
import com.example.navigator.navigatormapbuilder.map_builder.ui.control.Screen;
import com.example.navigator.navigatormapbuilder.map_builder.ui.screens.LoginScreen;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {

	@Override
	public void init() throws Exception {
		super.init();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		/*ProjectLoader loader = new ProjectLoader(new File("saves/471355bfa0a04f7f.json"));
		Screen firstScreen = new LvlEditScreen(loader.load());*/
		//Screen firstScreen = new LvlEditScreen(new Project());
		Screen firstScreen = new LoginScreen();
		//Screen firstScreen = new ProjectListPage();
		//Screen firstScreen = new ProjectSetupPage(true);
		Navigator.init(primaryStage,firstScreen);
	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
