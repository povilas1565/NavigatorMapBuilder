package com.example.navigator.navigatormapbuilder.map_builder.canvas.holder;

import com.example.navigator.navigatormapbuilder.map_builder.canvas.LayersName;
import com.example.navigator.navigatormapbuilder.map_builder.canvas.probe.Probe;
import com.example.navigator.navigatormapbuilder.map_builder.canvas.probe.ProbeManager;
import com.example.navigator.navigatormapbuilder.map_builder.utils.StringUtils;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

import java.util.*;

public abstract class Holder {
	public static final int ADDITIONAL_WIDTH = 5;
	protected ArrayList<Probe> probes;

	public abstract void splitLayers(Pane[] layers);

	public abstract void extractProbes(ProbeManager manager);

	public abstract void remove(Pane[] layers);

	protected void remove(Pane[] layers, Shape decoration, Shape... hitBoxes) {
		layers[LayersName.DECORATION]
				.getChildren()
				.remove(decoration);

		layers[LayersName.INPUT_LAYER]
				.getChildren()
				.removeAll(hitBoxes);
	}

	public abstract void beginReplace(double x, double y);

	public abstract void replace(double x, double y);

	public abstract void endReplace(double x, double y, ProbeManager manager);

	public abstract void beginResize(double x, double y);

	public abstract void resize(double x, double y);

	public abstract void endResize(double x, double y, ProbeManager manager);

	public abstract void reBuildProbes(ProbeManager manager);

	public abstract void setStrokeWidth(double width);

	public abstract void setStroke(Paint color);

	public abstract void setFill(Paint color);

	public abstract ScrollPane getInfo(ProbeManager manager);

	//fixme: оптимизировать пересчет хитбоксов
	public abstract void reBuildHitBoxes();

	public abstract Shape getShape();

	public abstract boolean contains(double x, double y);

	public abstract boolean containsInner(double x, double y);

	protected void initProbes(Shape shape, Probe... probes) {
		this.probes = new ArrayList<>();

		// Remove duplicate probes
		HashSet<Probe> uniqueProbes = new HashSet<>();
		Collections.addAll(uniqueProbes, probes);

		for (Iterator<Probe> iterator = uniqueProbes.iterator(); iterator.hasNext(); ) {
			Probe probe = iterator.next();
			if (probe != null) {
				probe.getAttachedShapes().add(shape);
				this.probes.add(probe);
			}else{
				System.out.println("Err while init new holder");
				iterator.remove();
			}
		}

	}

	public boolean isAttachedToProbe(Probe probe) {
		return probes.contains(probe);
	}

	@Override
	public String toString() {
		return StringUtils.holderToString(this.getClass());
	}

	public List<Probe> getProbes() {
		return probes;
	}
}
