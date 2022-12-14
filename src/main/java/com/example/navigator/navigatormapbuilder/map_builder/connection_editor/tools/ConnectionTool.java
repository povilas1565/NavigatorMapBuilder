package com.example.navigator.navigatormapbuilder.map_builder.connection_editor.tools;

import com.example.navigator.navigatormapbuilder.map_builder.connection_editor.LayersName;
import com.example.navigator.navigatormapbuilder.map_builder.connection_editor.node.LevelConnectionEntity;
import com.example.navigator.navigatormapbuilder.map_builder.connection_editor.node.LevelEntity;
import com.example.navigator.navigatormapbuilder.map_builder.connection_editor.node.LevelEntityManager;
import com.example.navigator.navigatormapbuilder.map_builder.core.navigation.ConnectionFormatException;
import com.example.navigator.navigatormapbuilder.map_builder.core.navigation.LevelConnection;
import com.example.navigator.navigatormapbuilder.map_builder.utils.common.ScreenTool;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class ConnectionTool implements ScreenTool {
    private static final double WIDTH = 10;
    private LevelEntityManager manager;
    private Line tmp;
    private LevelEntity beginEntity;
    private String beginSocketName;

    public ConnectionTool(LevelEntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void onPressed(double x, double y) {
        beginEntity = manager.select(x, y);
        if (beginEntity != null) {
            int beginSocketId = beginEntity.getSocketId(x, y);
            if (beginSocketId != -1) {
                beginSocketName = beginEntity.getSocketNameById(beginSocketId);
                Circle beginSocket = beginEntity.getSocketById(beginSocketId);


                tmp = new Line(beginSocket.getCenterX(), beginSocket.getCenterY(),
                        beginSocket.getCenterX(), beginSocket.getCenterY());
                tmp.setStrokeWidth(WIDTH);

                manager.getLayers()[LayersName.CONNECTIONS].getChildren().add(tmp);
            }
        }
    }

    @Override
    public void onDragged(double x, double y) {
        if (tmp != null) {
            tmp.setEndX(x);
            tmp.setEndY(y);
        }
    }

    @Override
    public void onReleased(double x, double y) {
        LevelEntity endEntity = manager.select(x, y);
        int endSocketId;

        if (beginEntity != null && tmp != null && endEntity != null
                && (endSocketId = endEntity.getSocketId(x, y)) != -1
        ) {
            try {
                String endSocketName = endEntity.getSocketNameById(endSocketId);

                LevelConnection connection = new LevelConnection(
                        beginEntity,
                        endEntity,
                        beginSocketName,
                        endSocketName,
                        manager.getConnectionsManager()
                );


                if (manager.getConnectionsManager()
                        .add(connection)) {

                    LevelConnectionEntity connectionEntity = new LevelConnectionEntity(
                            tmp,
                            beginEntity,
                            beginSocketName,
                            endEntity,
                            endSocketName,
                            connection
                    );

                    beginEntity.addConnection(connectionEntity);
                    endEntity.addConnection(connectionEntity);

                    Circle endSocket = endEntity.getSocketById(endSocketId);
                    tmp.setEndX(endSocket.getCenterX());
                    tmp.setEndY(endSocket.getCenterY());

                    manager.push(connectionEntity);

                } else {
                    removeFromView();
                }

            } catch (ConnectionFormatException e) {
                //e.printStackTrace();
                System.out.println("Cycle connection");
                removeFromView();
            }
        } else {
            removeFromView();
        }
    }

    public void removeFromView() {
        manager.getLayers()[LayersName.CONNECTIONS]
                .getChildren()
                .remove(tmp);
    }
}
