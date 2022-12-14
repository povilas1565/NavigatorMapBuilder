package com.example.navigator.navigatormapbuilder.map_builder.connection_editor.node;

import com.example.navigator.navigatormapbuilder.map_builder.connection_editor.LayersName;
import com.example.navigator.navigatormapbuilder.map_builder.core.navigation.LevelNode;
import com.example.navigator.navigatormapbuilder.map_builder.core.navigation.Node;
import com.example.navigator.navigatormapbuilder.map_builder.core.navigation.NodeType;
import com.example.navigator.navigatormapbuilder.map_builder.utils.Vector2;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class LevelEntity {
	private static final double SOCKET_RADIUS = 30.0;
	private static final double SINGLETON_RADIUS = 50.0;
	private static final double OFFSET = 15.0;
	private Circle bg;
	private Label lvlName;
	private List<Circle> sockets;
	private List<Label> tooltips;
	private List<String> socketsHashNames;
	private HashSet<LevelConnectionEntity> connections;
	private LevelNode node;

	private boolean isSingleton;

	private Vector2 origin;
	private Vector2 beginPosition;
	private Vector2[] socketsPositions;
	private double r;

	public LevelEntity(LevelNode node) {
		this.node = node;
		sockets = new ArrayList<>();
		socketsHashNames = new ArrayList<>(node.getSocketsHashNames());
		lvlName = new Label(node.getName());

		connections = new HashSet<>();
		tooltips = new ArrayList<>();

		List<String> names = node.getLevel()
				.getNodeSystem()
				.stream()
				.filter(v -> v.getType() == NodeType.ZONE_CONNECTION)
				.map(Node::getName)
				.collect(Collectors.toList());

		initSockets(names);
	}

	public LevelEntity(double x, double y, LevelNode node) {
		this(node);
		setPosition(x, y);
	}

	private void initSockets(List<String> strings) {
		switch (strings.size()) {
			case 1:
				initSingleton(strings.iterator().next());
				break;
			case 0:
				initNoneSocket();
				break;
			default:
				initMultiSockets(strings);
				break;
		}
	}

	private void initMultiSockets(List<String> strings) {
		final Paint socketColor = Color.GREEN;
		final Paint bgColor = Color.CORAL;

		double angle = Math.PI * 2.0 / strings.size();
		double side = 2 * SOCKET_RADIUS + OFFSET;
		r = side / (2 * Math.sin(angle / 2));

		bg = new Circle(r);
		bg.setFill(bgColor);

		for (int i = 0; i < strings.size(); i++) {
			double tmpAngle = angle * i;
			double x = r * Math.cos(tmpAngle);
			double y = r * Math.sin(tmpAngle);

			Circle socket = new Circle(x, y, SOCKET_RADIUS, socketColor);

			Label label = new Label(strings.get(i));
			label.setStyle("-fx-text-fill: #e9c8ab;");
			//setToolTipPos(label,socket);

			sockets.add(socket);
			tooltips.add(label);
		}

	}

	private void initNoneSocket() {
		final Paint noneSocketColor = Color.GREY;
		r = SINGLETON_RADIUS;
		isSingleton = true;
		bg = new Circle(r);
		bg.setFill(noneSocketColor);
	}

	private void initSingleton(String name) {
		final Paint socketColor = Color.GREEN;

		r = SINGLETON_RADIUS;
		isSingleton = true;
		bg = new Circle(r);
		bg.setFill(socketColor);

		Label label = new Label(name);

		label.setStyle("-fx-text-fill: pink;");
		setToolTipPos(label,bg);

		sockets.add(bg);
		tooltips.add(label);
	}

	public void setPosition(double x, double y) {
		beginReplace(bg.getCenterX(), bg.getCenterY());
		endReplace(x, y);
	}

	public void beginReplace(double x, double y) {
		origin = new Vector2(x, y);
		beginPosition = new Vector2(bg.getCenterX(), bg.getCenterY());
		socketsPositions = new Vector2[sockets.size()];

		for (int i = 0; i < sockets.size(); i++) {
			Circle socket = sockets.get(i);
			socketsPositions[i] = new Vector2(socket.getCenterX(), socket.getCenterY());

			tooltips.get(i).setVisible(false);
		}

		lvlName.setVisible(false);

		for (LevelConnectionEntity connection : connections) {
			connection.beginResize(this);
		}
	}

	public void replace(double x, double y) {
		Vector2 currentPosition = new Vector2(x, y);
		Vector2 delta = origin.subtract(currentPosition);

		moveCircle(bg, delta, beginPosition);

		for (int i = 0; i < sockets.size(); i++) {
			Circle socket = sockets.get(i);
			moveCircle(socket, delta, socketsPositions[i]);
		}

		for (LevelConnectionEntity connection : connections) {
			int index = socketsHashNames.indexOf(connection.getEditSocket());
			Circle socket = getSocketById(index);
			connection.resize(socket.getCenterX(), socket.getCenterY());
		}
	}

	private void moveCircle(Circle circle, Vector2 delta, Vector2 beginPosition) {
		circle.setCenterX(beginPosition.getX() + delta.getX());
		circle.setCenterY(beginPosition.getY() + delta.getY());
	}

	public void endReplace(double x, double y) {
		replace(x, y);

		double nameX = bg.getCenterX() - getNameWidth();
		double nameY = isSingleton
				? bg.getCenterY() + r + SOCKET_RADIUS * 0.5
				: bg.getCenterY() + r + SOCKET_RADIUS * 1.5;

		for (int i = 0; i < tooltips.size(); i++) {
			Label label = tooltips.get(i);
			Circle socket = sockets.get(i);

			setToolTipPos(label, socket);
			tooltips.get(i).setVisible(true);
		}

		lvlName.setLayoutX(nameX);
		lvlName.setLayoutY(nameY);
		lvlName.setVisible(true);
	}

	private void setToolTipPos(Label label, Circle socket) {
		label.setLayoutX(socket.getCenterX()-socket.getRadius()*0.8);
		label.setLayoutY(socket.getCenterY()-socket.getRadius()*0.3);
	}

	private double getNameWidth() {
		return lvlName.getText().length() * 3.5;
	}

	public boolean contains(double x, double y) {
		if (isSingleton) {
			return bg.contains(x, y);
		} else {
			return bg.contains(x, y) || sockets.stream().anyMatch(e -> e.contains(x, y));
		}
	}

	public String socketContains(double x, double y) {
		int id;
		return (id = getSocketId(x, y)) != -1
				? socketsHashNames.get(id)
				: null;
	}

	@Deprecated
	public Circle getSocket(int socket) {
		if (socket < 0 || socket >= sockets.size()) return null;
		return sockets.get(socket);
	}

	public Circle getSocket(double x, double y) {
		int id;
		return (id = getSocketId(x, y)) != -1
				? sockets.get(id)
				: null;
	}

	public int getSocketId(double x, double y) {
		for (int i = 0; i < sockets.size(); i++) {
			Circle socket = sockets.get(i);

			if (socket.contains(x, y)) {
				return i;
			}
		}

		return -1;
	}


	public void splitLayers(Pane[] layers) {
		layers[LayersName.MAIN].getChildren()
				.addAll(bg, lvlName);

		if (!isSingleton) {
			layers[LayersName.MAIN].getChildren()
					.addAll(sockets);
		}

		layers[LayersName.MAIN].getChildren()
				.addAll(tooltips);
	}

	public LevelNode getNode() {
		return node;
	}

	public double getR() {
		return r;
	}

	public double getX() {
		return bg.getCenterX();
	}

	public double getY() {
		return bg.getCenterY();
	}

	public void addConnection(LevelConnectionEntity connection) {
		connections.add(connection);
	}

	public void removeConnection(LevelConnectionEntity connection) {
		connections.remove(connection);
	}


	public String getSocketNameById(int beginSocketId) {
		return beginSocketId != -1 && beginSocketId < sockets.size()
				? socketsHashNames.get(beginSocketId) : null;
	}

	public Circle getSocketById(int beginSocketId) {
		return beginSocketId != -1 && beginSocketId < sockets.size()
				? sockets.get(beginSocketId) : null;
	}

	public Circle getSocketByName(String name) {
		for(int i = 0; i< socketsHashNames.size(); i++){
			if(socketsHashNames.get(i).equals(name)){
				return sockets.get(i);
			}
		}
		return null;
	}
}
