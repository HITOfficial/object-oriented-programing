package agh.ics.oop.gui;

import agh.ics.oop.IPositionChangeObserver;
import agh.ics.oop.Vector2d;
import javafx.application.Platform;


import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;
import java.awt.*;

import javafx.geometry.Insets;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Vector;

public class App extends Application implements IPositionChangeObserver {
    //    private Stage;
    private GridPane gridPane;
    private SimulationEngine engine;

    Vector2d lowerLeft = new Vector2d(0, 0);
    Vector2d upperRight = new Vector2d(99, 30);
    Vector2d jungleLowerLeft = new Vector2d(45, 10);
    Vector2d jungleUpperRight = new Vector2d(54, 20);


    public void start(Stage stage) {
        // running
        startSimulationEngine();
//
//        this.stage = stage;
        // running engine
        stage.setTitle("Evolution Simulator");
        GridPane gridPane = new GridPane();
        this.gridPane = gridPane;
        gridPane.setGridLinesVisible(true);

        Scene scene = new Scene(gridPane, 1300.0f, 440.0f);

        gridPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("rgb(250,250,210)"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setMinWidth(1300.0f);
        gridPane.setMaxWidth(440.0f);

        gridPane.setMinSize(10, 10);


//        Label l = new Label("TEXT");
//        VBox vBox = MapElement.draw(new Animal());
//        l.setAlignment(Pos.CENTER);
//        l.setMinWidth(50);
//        l.setMinHeight(50);
//        desert.add(vBox, 0, 1, 100, 5);




//        for (int i = lowerLeft.getX(); i <= upperRight.getX(); i++) {
//            for (int j = lowerLeft.getY(); j <= upperRight.getY(); j++) {
//                Vector2d position = new Vector2d(i, j);
//                VBox vBox = MapElement.draw(new Animal());
////                if (engine.map.animals.get(position).size() > 0) {
////                    vBox = MapElement.draw(engine.map.animals.get(position).get(0));
////                } else if (engine.map.grass.get(position) != null) {
////                    vBox = MapElement.draw(engine.map.animals.get(position).get(0));
////                } else {
////                    vBox = new VBox(10);
////                }
//                // checking if is on desert position;
//                if (position.follows(jungleLowerLeft) && position.precedes(jungleUpperRight)) {
//                    vBox.setStyle("-fx-background-color: rgb(14, 102, 13);");
//                }
//                vBox.setAlignment(Pos.CENTER);
////                vBox.getChildren().add(new Label(position.toString()));
//                gridPane.add(vBox, j,  + upperRight.getX() - i, 1, 1);
//            }
//        }


//        gridPane.add(desert, 5, 5, upperRight.getX(), upperRight.getY());
//        updateMap();


        positionChanged();


        stage.setScene(scene);
        stage.show();

//        startSimulationEngine();

//        gridPane.add(, 0, 0 + upperRightY - i, 1, 1);

    }

    public void updateMap() {
        gridPane.getChildren().clear();
        for (int i = lowerLeft.getX(); i <= upperRight.getX(); i++){
            Label l = new Label(String.valueOf(i));
            l.setMinWidth(10.0f);
            l.setMinHeight(10.0f);
            gridPane.add(l, 1 + i, 0, 1, 1);
        }
        for (int i = lowerLeft.getY(); i <= upperRight.getY(); i++){
            Label l = new Label(String.valueOf(i));
            l.setMinWidth(10);
            l.setMinHeight(10);
            gridPane.add(l, 0, 1+i, 1, 1);
        }

        for (int i = lowerLeft.getX(); i <= upperRight.getX(); i++) {
            for (int j = lowerLeft.getY(); j <= upperRight.getY(); j++) {
                Vector2d position = new Vector2d(i, j);
                VBox vBox;
                IMapElement object = engine.map.objectAt(position);
                vBox = MapElement.draw(object);
                // checking if is on desert position;
                if (position.follows(jungleLowerLeft) && position.precedes(jungleUpperRight)) {
                    vBox.setStyle("-fx-background-color: rgb(14, 102, 13);");
                }
                vBox.setAlignment(Pos.CENTER);
                gridPane.add(vBox, 1+ i,1+ upperRight.getY() - j , 1, 1);
            }
        }
    }

//    public void updateMap() {
//        for (int i = lowerLeft.getX(); i <= upperRight.getX(); i++) {
//            for (int j = lowerLeft.getY(); j <= upperRight.getY(); j++) {
//                Vector2d position = new Vector2d(i, j);
//                VBox vBox = MapElement.draw(null);
////                if (engine.map.animals.get(position).size() > 0) {
////                    vBox = MapElement.draw(engine.map.animals.get(position).get(0));
////                } else if (engine.map.grass.get(position) != null) {
////                    vBox = MapElement.draw(engine.map.animals.get(position).get(0));
////                } else {
////                    vBox = new VBox(10);
////                }
//                // checking if is on desert position;
//                if (position.follows(jungleLowerLeft) && position.precedes(jungleUpperRight)) {
//                    vBox.setStyle("-fx-background-color: rgb(14, 102, 13);");
//                }
//                vBox.setAlignment(Pos.CENTER);
//                gridPane.add(vBox, upperRight.getX() - i, j, 1, 1);
//            }
//        }
//    }


//    public GridPane desertCanvas() {
//        GridPane desertPane = new GridPane();
//        desertPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("rgb(250,250,210)"), CornerRadii.EMPTY, Insets.EMPTY)));
//        desertPane.setMinSize(50, 50);
//
//        // I dont know what is wrong but empty grid is hidden, even with min size
//        Label l = new Label();
//        l.setAlignment(Pos.CENTER);
//        l.setMinWidth(1000);
//        l.setMinHeight(300);
////        desertPane.add(l,0,0,1,1);
//        desertPane.add(jungleCanvas(), 30, 30, 30, 30);
//        desertPane.setMinWidth(1000);
//        desertPane.setMaxWidth(300);
//        desertPane.setGridLinesVisible(true);
//        return desertPane;
//    }
//
//
//    public GridPane jungleCanvas() {
//        GridPane junglePane = new GridPane();
//        junglePane.setBackground(new Background(new BackgroundFill(Paint.valueOf("rgb(14, 102, 13)"), CornerRadii.EMPTY, Insets.EMPTY)));
//        junglePane.setMinSize(50, 50);
//
//        Label l = new Label("TEXT");
//        l.setAlignment(Pos.CENTER);
//        l.setMinWidth(100);
//        l.setMinHeight(100);
//        junglePane.add(l, jungleLowerLeft.getY(), jungleLowerLeft.getY(), jungleUpperRight.subtract(jungleLowerLeft).getY(), jungleUpperRight.subtract(jungleLowerLeft).getY());
//        junglePane.setMinWidth(100);
//        junglePane.setMaxWidth(100);
//        junglePane.setGridLinesVisible(true);
//        return junglePane;
//    }


    public VBox drawAnimal(Animal animal) {
        return null;
    }


    public void startSimulationEngine() {
        this.engine = new SimulationEngine(this);
        Thread engineThread = new Thread(engine);
        engineThread.start();
    }

    @Override
    public void positionChanged() {
        Platform.runLater(() -> {
            updateMap();
        });
    }

}
