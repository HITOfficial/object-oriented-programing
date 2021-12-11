package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void init() {
        MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
    }

    public void start(Stage primaryStage) {
        // ??
        MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        GrassField map = new GrassField(10);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        primaryStage.setTitle("Pets");
        GridPane gridPane = new GridPane();


        Vector2d lowerLeft = map.getLowerLeft();
        Vector2d upperRight = map.getUpperRight();

        int lowerLeftX = lowerLeft.getX();
        int lowerLeftY = lowerLeft.getY();

        int upperRightX = upperRight.getX();
        int upperRightY = upperRight.getY();


        Label xyLabel = new Label("x/y");
        xyLabel.setMinWidth(50);
        xyLabel.setMinHeight(50);
        xyLabel.setAlignment(Pos.CENTER);


        // first column
        gridPane.add(xyLabel, 0, 0, 1, 1);
        for (int i = lowerLeftX; i <= upperRightX; i++) {
            Label l = new Label(String.valueOf(i));
            l.setAlignment(Pos.CENTER);
            l.setMinWidth(50);
            l.setMinHeight(50);
            gridPane.add(l, 1 + i - lowerLeftX, 0, 1, 1);
        }
        // first row
        for (int i = upperRightY; i >= lowerLeftY; i--) {
            Label l = new Label(String.valueOf(i));
            l.setAlignment(Pos.CENTER);
            l.setMinWidth(50);
            l.setMinHeight(50);
            gridPane.add(l, 0, 1 + upperRightY - i, 1, 1);
        }

        // all occupied fields
        for (int i = lowerLeftX; i <= upperRightX; i++) {
            for (int j = upperRightY; j >= lowerLeftY; j--) {
                // checking if something is on this coordinate
                if (map.objectAt(new Vector2d(i, j)) != null) {
                    Object objectAtPosition = map.objectAt(new Vector2d(i, j));
                    Label label = new Label(objectAtPosition.toString());
                    label.setAlignment(Pos.CENTER);
                    label.setMinWidth(50);
                    label.setMinHeight(50);
                    gridPane.add(label, 1 + upperRightY - j, 1 + i - lowerLeftX, 1, 1);
                }
            }
        }

        gridPane.setGridLinesVisible(true);
        Scene scene = new Scene(gridPane, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
