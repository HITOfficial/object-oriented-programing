package agh.ics.oop.gui;

import agh.ics.oop.IPositionChangeObserver;
import agh.ics.oop.Vector2d;
import javafx.application.Platform;


import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
    private GridPane boundedMap;
    private GridPane unboundedMap;
    private SimulationEngine engine;

    Vector2d lowerLeft = new Vector2d(0, 0);
    Vector2d upperRight = new Vector2d(99, 30);
    Vector2d jungleLowerLeft = new Vector2d(45, 10);
    Vector2d jungleUpperRight = new Vector2d(54, 20);

    private HBox infoContainer;
    private LineChart lineChart;
    private XYChart.Series dataSeriesBoundedMapAnimal;
    private XYChart.Series dataSeriesUnboundedMapAnimal;
    private XYChart.Series dataSeriesBoundedMapGrass;
    private XYChart.Series dataSeriesUnboundedMapGrass;
    private VBox root;
    private VBox mapsWrapper;


    public void start(Stage stage) {
        HBox infoContainer = new HBox();
        this.infoContainer = infoContainer;
        VBox root = new VBox();
        this.root = root;

        // lineChart
        createLineChart();

        // bounded map gird
        GridPane boundedMap = new GridPane();
        this.boundedMap = boundedMap;
        //unbounded map grid
        GridPane unboundedMap = new GridPane();
        this.unboundedMap = boundedMap;
        VBox mapsWrapper = new VBox();
        this.mapsWrapper = mapsWrapper;
        mapsWrapper.getChildren().addAll(boundedMap, unboundedMap);


        // running
        startSimulationEngine();

//        this.stage = stage;
        // running engine
        stage.setTitle("Evolution Simulator");
//        GridPane gridPane = new GridPane();
//        gridPane.setGridLinesVisible(true);


        // main scene
        Scene scene = new Scene(root, 1300.0f, 440.0f);


        boundedMap.setBackground(new Background(new BackgroundFill(Paint.valueOf("rgb(250,250,210)"), CornerRadii.EMPTY, Insets.EMPTY)));
        boundedMap.setMinWidth(1300.0f);
        boundedMap.setMinHeight(440.0f);

        boundedMap.setMinSize(10, 10);


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


//        positionChanged();

        root.getChildren().addAll(infoContainer, mapsWrapper);

        stage.setScene(scene);
        stage.show();
    }

    private void lineChartUpdate(SavannaMap map) {
        if (map.boundedMap) {
            dataSeriesBoundedMapAnimal.getData().add(new XYChart.Data(map.ageCounter, map.animalsCounter));
            dataSeriesBoundedMapGrass.getData().add(new XYChart.Data(map.ageCounter, map.grassCounter));
        } else {
            dataSeriesUnboundedMapAnimal.getData().add(new XYChart.Data(map.ageCounter, map.animalsCounter));
            dataSeriesUnboundedMapGrass.getData().add(new XYChart.Data(map.ageCounter, map.grassCounter));
        }
    }

    private void createLineChart() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Day");
        // population
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Population");
        this.lineChart = new LineChart(xAxis, yAxis);
        lineChart.setMaxHeight(300);
        lineChart.setMinHeight(300);
        lineChart.setMinWidth(800);
        lineChart.setMaxWidth(800);

        // Bounded map Series
        this.dataSeriesBoundedMapAnimal = new XYChart.Series();
        dataSeriesBoundedMapAnimal.setName("Bounded map Animals");
        this.dataSeriesBoundedMapGrass = new XYChart.Series();
        dataSeriesBoundedMapGrass.setName("Bounded map Grass");
        // Unbounded map Series
        this.dataSeriesUnboundedMapAnimal = new XYChart.Series();
        dataSeriesUnboundedMapAnimal.setName("Unbounded map Animals");
        this.dataSeriesUnboundedMapGrass = new XYChart.Series();
        dataSeriesUnboundedMapGrass.setName("Unbounded map Grass");


        lineChart.getData().addAll(dataSeriesBoundedMapAnimal, dataSeriesBoundedMapGrass);
        lineChart.getData().addAll(dataSeriesUnboundedMapAnimal, dataSeriesUnboundedMapGrass);

        this.infoContainer.getChildren().add(lineChart);
    }

    public void updateMap(SavannaMap map) {
        GridPane pane;
        // condition which grid refreshing
        if (map.boundedMap) {
            pane = boundedMap;
        } else {
            pane = unboundedMap;
        }

        pane.getChildren().clear();
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
                pane.add(vBox, i, 1 + upperRight.getY() - j, 1, 1);
            }
        }
    }

    public void startSimulationEngine() {
        this.engine = new SimulationEngine(this);
        Thread engineThread = new Thread(engine);
        engineThread.start();
    }

    @Override
    public void positionChanged(SavannaMap map) {
        Platform.runLater(() -> {
            updateMap(map);
            lineChartUpdate(map);
        });
    }

}
