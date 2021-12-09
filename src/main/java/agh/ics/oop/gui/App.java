package agh.ics.oop.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    public void start(Stage primaryStage) {
        primaryStage.show();
        Label label = new Label("Pet");
        Scene scene = new Scene(label, 400,400);

        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        GridPane.setHalignment(label,HPos.Center);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
