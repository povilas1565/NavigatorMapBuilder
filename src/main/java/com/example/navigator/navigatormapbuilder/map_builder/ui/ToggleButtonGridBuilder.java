package com.example.navigator.navigatormapbuilder.map_builder.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import com.example.navigator.navigatormapbuilder.map_builder.utils.ImageUtils;

import java.io.IOException;
import java.util.ArrayList;


public class ToggleButtonGridBuilder {
	private String[] source;
	private EventHandler<ActionEvent> onClick;
	private boolean isUseImage;
	private boolean isUseName;

	public ToggleButtonGridBuilder setSource(String[] source) {
		this.source = source;
		return this;
	}

	public ToggleButtonGridBuilder setOnClick(EventHandler<ActionEvent> onClick) {
		this.onClick = onClick;
		return this;
	}

	public ToggleButtonGridBuilder setUseImage(boolean useImage) {
		isUseImage = useImage;
		return this;
	}

	public ToggleButtonGridBuilder setUseName(boolean useName) {
		isUseName = useName;
		return this;
	}

	public ArrayList<ToggleButton> build() {

		ArrayList<ToggleButton> result = new ArrayList<>();

		for (String name : source) {
			ToggleButton toggleButton = isUseName
					? new ToggleButton(name)
					: new ToggleButton();

			toggleButton.setOnAction(onClick);

			toggleButton.setMinSize(50,50);
			toggleButton.setPrefSize(50,50);
			toggleButton.setMaxSize(50,50);

			try {
				if (isUseImage && name != null && !name.isEmpty()) {
					ImageView graphic = ImageUtils.loadImage("image/buttons/" + name + ".png");
					toggleButton.setGraphic(graphic);
					graphic.setFitWidth(47);
					graphic.setFitHeight(47);
				}
			} catch (IOException ex) {
				System.out.println("Error while reading layout");
			}catch (NullPointerException ex){
				System.out.println("Image not found");
			}
			result.add(toggleButton);
		}
		return result;

	}
}
