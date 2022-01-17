package agh.ics.oop.gui;

import agh.ics.oop.IPositionChangeObserver;
import agh.ics.oop.Vector2d;
import javafx.application.Platform;


import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
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
import javafx.scene.control.TextField;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Vector;

public class App extends Application implements IPositionChangeObserver {   // za duża ta klasa
    //    private Stage;
    private GridPane boundedMap;
    private GridPane unboundedMap;

    // map properties
    public Vector2d lowerLeft;
    public Vector2d upperRight;
    public Vector2d jungleLowerLeft;
    public Vector2d jungleUpperRight;
    public int ageCost;
    public int grassEnergy;
    public int animalsNumber;
    public int startEnergy;
    public int width;
    public int height;

    Stage stage;

    private HBox infoContainer;
    private LineChart lineChart;
    private XYChart.Series dataSeriesBoundedMapAnimal;
    private XYChart.Series dataSeriesUnboundedMapAnimal;
    private XYChart.Series dataSeriesUnboundedMapAVGLifeLength;
    private XYChart.Series dataSeriesUnboundedMapAVGEnergy;
    private XYChart.Series dataSeriesUnboundedMapAVGChildren;

    private XYChart.Series dataSeriesBoundedMapGrass;
    private XYChart.Series dataSeriesUnboundedMapGrass;
    private XYChart.Series dataSeriesBoundedMapAVGLifeLength;
    private XYChart.Series dataSeriesBoundedMapAVGEnergy;
    private XYChart.Series dataSeriesBoundedMapAVGChildren;

    private VBox additionalInfoWrapper;

    private HBox boundedMapGenotypeDominantWrapper;
    private HBox unboundedMapGenotypeDominantWrapper;
    private Label boundedMapGenotypeDominant;
    private Label unboundedMapGenotypeDominant;

    private HBox boundedMapActualAgeWrapper;
    private HBox unboundedMapActualAgeWrapper;

    private HBox ToggleEnginesWrapper;

    private Label boundedMapActualAge;
    private Label unboundedMapActualAge;
    private Label boundedMapMagicReproductionsLeft;
    private Label unboundedMapMagicReproductionsLeft;

    private Thread boundedMapEngineThread;
    private boolean boundedMapEngineON = true;
    private Button boundedMapEngineButton;

    private Thread unboundedMapEngineThread;
    private boolean unboundedMapEngineON = true;
    private Button unboundedMapEngineButton;

    public Animal boundedMapAnimalToFollow = null;
    public VBox boundedMapAnimalInfo = null;

    public Animal unboundedMapAnimalToFollow = null;
    public VBox unboundedMapAnimalInfo = null;

    public Button exitAndSaveDataButton;


    public void boundedMapToggleEngineThread() {
        boundedMapEngineON = !boundedMapEngineON;
        if (boundedMapEngineON) {
            boundedMapEngineThread.resume();
        } else {
            boundedMapEngineThread.suspend();
        }
    }

    public void unboundedMapToggleEngineThread() {
        unboundedMapEngineON = !unboundedMapEngineON;
        if (unboundedMapEngineON) {
            unboundedMapEngineThread.resume();
        } else {
            unboundedMapEngineThread.suspend();
        }
    }


    public void mainMenuScene() {
        VBox mainMenuWrapper = new VBox();
        GridPane mainMenuGridPane = new GridPane();
        mainMenuGridPane.setVgap(5);
        mainMenuGridPane.setHgap(10);

        TextField widthInput = new TextField("100");
        TextField heightInput = new TextField("30");
        TextField animalsInput = new TextField("5");
        TextField startEnergyInput = new TextField("150");
        TextField ageCostInput = new TextField("1");
        TextField grassEnergyInput = new TextField("15");
        TextField jungleRatioInput = new TextField("0.3");

        mainMenuGridPane.setPadding(new Insets(10, 0, 7, 15));
        mainMenuGridPane.add(new Label("width"), 0, 0);
        mainMenuGridPane.add(widthInput, 1, 0);
        mainMenuGridPane.add(new Label("height"), 0, 1);
        mainMenuGridPane.add(heightInput, 1, 1);
        mainMenuGridPane.add(new Label("animals"), 0, 2);
        mainMenuGridPane.add(animalsInput, 1, 2);
        mainMenuGridPane.add(new Label("starting energy"), 0, 3);
        mainMenuGridPane.add(startEnergyInput, 1, 3);
        mainMenuGridPane.add(new Label("age cost"), 0, 4);
        mainMenuGridPane.add(ageCostInput, 1, 4);
        mainMenuGridPane.add(new Label("grass energy"), 0, 5);
        mainMenuGridPane.add(grassEnergyInput, 1, 5);
        mainMenuGridPane.add(new Label("jungle ratio (0-1)"), 0, 6);
        mainMenuGridPane.add(jungleRatioInput, 1, 6);
        Button startSimulationButton = new Button("Start Simulation");
        Label mainMenuLabel = new Label("Set Simulation Properties");
        mainMenuWrapper.getChildren().addAll(mainMenuLabel, mainMenuGridPane, startSimulationButton);
        mainMenuWrapper.setAlignment(Pos.CENTER);

        Scene mainMenu = new Scene(mainMenuWrapper, 300, 300);
        stage.setScene(mainMenu);
        stage.show();
        startSimulationButton.setOnMouseClicked((e) -> validateAndStartSimulation(widthInput, heightInput, animalsInput, startEnergyInput, ageCostInput, grassEnergyInput, jungleRatioInput));
    }

    public void validateAndStartSimulation(TextField widthInput, TextField heightInput, TextField animalsInput, TextField startEnergyInput, TextField ageCostInput, TextField grassEnergyInput, TextField jungleRatioInput) {
        if (Integer.parseInt(widthInput.getText()) <= 0) {
            throw new IllegalArgumentException(widthInput.getText() + "width must be a positive number!");
        }
        if (Integer.parseInt(heightInput.getText()) <= 0) {
            throw new IllegalArgumentException(heightInput.getText() + " height must be a positive number!");
        }
        if (Integer.parseInt(animalsInput.getText()) <= 0) {
            throw new IllegalArgumentException(widthInput.getText() + " number of animals must be a positive number!");
        }
        if (Integer.parseInt(startEnergyInput.getText()) <= 0) {
            throw new IllegalArgumentException(startEnergyInput.getText() + " start energy must be a positive number!");
        }
        if (Integer.parseInt(ageCostInput.getText()) <= 0) {
            throw new IllegalArgumentException(ageCostInput.getText() + " age cost must be a positive number!");
        }
        if (Integer.parseInt(grassEnergyInput.getText()) <= 0) {
            throw new IllegalArgumentException(widthInput.getText() + " grass energy must be a positive number!");
        }
        if ((Float.parseFloat(jungleRatioInput.getText()) <= 0 || Float.parseFloat(jungleRatioInput.getText()) >= 1)) {
            throw new IllegalArgumentException(jungleRatioInput.getText() + " jungle ratio must be a number between 0 and 1!");
        }

        int width = Integer.parseInt(widthInput.getText());
        int height = Integer.parseInt(heightInput.getText());
        int animalsNumber = Integer.parseInt(animalsInput.getText());
        int startEnergy = Integer.parseInt(startEnergyInput.getText());
        int ageCost = Integer.parseInt(ageCostInput.getText());
        int grassEnergy = Integer.parseInt(grassEnergyInput.getText());
        float jungleRatio = Float.parseFloat(jungleRatioInput.getText());
        setMapProperties(width, height, animalsNumber, startEnergy, ageCost, grassEnergy, jungleRatio);
        // running Simulation scene and starting two simulations
        runSimulationScene();
    }


    private void setMapProperties(int width, int height, int animals, int startEnergy, int ageCost, int grassEnergy, float jungleRatio) {
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width, height);

        // jungle map center 1/2 difference X -> left, right, bottom up 1/2 difference Y
        int jungleHalfX = (int) Math.round((double) (width / 2) * (double) jungleRatio) / 2;
        int jungleHalfY = (int) Math.round((double) (height / 2) * (double) jungleRatio) / 2;

        this.jungleLowerLeft = new Vector2d((width / 2) - jungleHalfX, (height / 2) - jungleHalfY);
        this.jungleUpperRight = new Vector2d((width / 2) + jungleHalfX, (height / 2) + jungleHalfY);
        this.ageCost = ageCost;
        this.grassEnergy = grassEnergy;
        this.animalsNumber = animals;
        this.startEnergy = startEnergy;
        this.width = width;
        this.height = height;
    }

    public void start(Stage stage) {
        this.stage = stage;
        // main menu
        mainMenuScene();

    }


    private void runSimulationScene() {
        HBox infoContainer = new HBox();
        this.infoContainer = infoContainer;

        VBox root = new VBox();

        // lineChart
        createLineChart();

        // total age dominant, magic reproductions left- labels with info
        constructInfo();

        // bounded map gird
        GridPane boundedMap = new GridPane();
        this.boundedMap = boundedMap;
        //unbounded map grid
        GridPane unboundedMap = new GridPane();
        this.unboundedMap = unboundedMap;

        Label boundedMapLabel = new Label("BOUNDED MAP");
        Label unboundedMapLabel = new Label("UNBOUNDED MAP");

        VBox mapsWrapper = new VBox();
        mapsWrapper.setAlignment(Pos.CENTER);
        mapsWrapper.setSpacing(5);
        mapsWrapper.getChildren().addAll(boundedMapLabel, boundedMap, unboundedMapLabel, unboundedMap);


        // running engine
        startSimulationEngine(false);
        startSimulationEngine(true);


        // main scene
        Scene scene = new Scene(root, Math.min(1200, width * 21), 300 + (2 * height * 10) + 70);

        // maps resolution
        boundedMap.setBackground(new Background(new BackgroundFill(Paint.valueOf("rgb(250,250,210)"), CornerRadii.EMPTY, Insets.EMPTY)));
        boundedMap.setMinWidth(width * 10);
        boundedMap.setMinHeight(height * 10);
        boundedMap.setMaxHeight(height * 10 + 10);
        boundedMap.setMaxWidth(width * 10 + 10);

        unboundedMap.setBackground(new Background(new BackgroundFill(Paint.valueOf("rgb(250,250,210)"), CornerRadii.EMPTY, Insets.EMPTY)));
        unboundedMap.setMinWidth(width * 10);
        unboundedMap.setMinHeight(height * 10);
        unboundedMap.setMaxHeight(height * 10 + 10);
        unboundedMap.setMaxWidth(width * 10 + 10);
        root.getChildren().addAll(infoContainer, mapsWrapper);

        stage.setTitle("Evolution Simulator");
        stage.setScene(scene);
    }

    private void lineChartUpdate(SavannaMap map) {
        if (map.boundedMap) {
            dataSeriesBoundedMapAnimal.getData().add(new XYChart.Data(map.ageCounter, map.animalsCounter));
            dataSeriesBoundedMapGrass.getData().add(new XYChart.Data(map.ageCounter, map.grassCounter));
            dataSeriesBoundedMapAVGLifeLength.getData().add(new XYChart.Data(map.ageCounter, map.AVGLengthOfLifeAnimals));
            dataSeriesBoundedMapAVGEnergy.getData().add(new XYChart.Data(map.ageCounter, map.AVGEnergyOfAliveAnimals));
            dataSeriesBoundedMapAVGChildren.getData().add(new XYChart.Data(map.ageCounter, map.AVGNumberOfChildrenAnimals));

        } else {
            dataSeriesUnboundedMapAnimal.getData().add(new XYChart.Data(map.ageCounter, map.animalsCounter));
            dataSeriesUnboundedMapGrass.getData().add(new XYChart.Data(map.ageCounter, map.grassCounter));
            dataSeriesUnboundedMapAVGLifeLength.getData().add(new XYChart.Data(map.ageCounter, map.AVGLengthOfLifeAnimals));
            dataSeriesUnboundedMapAVGEnergy.getData().add(new XYChart.Data(map.ageCounter, map.AVGEnergyOfAliveAnimals));
            dataSeriesUnboundedMapAVGChildren.getData().add(new XYChart.Data(map.ageCounter, map.AVGNumberOfChildrenAnimals));
        }
    }

    private void createLineChart() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Age");
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
        this.dataSeriesBoundedMapAVGLifeLength = new XYChart.Series();
        dataSeriesBoundedMapAVGLifeLength.setName("Bounded map AVG Live Length");
        this.dataSeriesBoundedMapAVGEnergy = new XYChart.Series();
        dataSeriesBoundedMapAVGEnergy.setName("Bounded map AVG Energy");
        this.dataSeriesBoundedMapAVGChildren = new XYChart.Series();
        dataSeriesBoundedMapAVGChildren.setName("Bounded map AVG Children Number");


//        // Unbounded map Series
        this.dataSeriesUnboundedMapAnimal = new XYChart.Series();
        dataSeriesUnboundedMapAnimal.setName("Unbounded map Animals");
        this.dataSeriesUnboundedMapGrass = new XYChart.Series();
        dataSeriesUnboundedMapGrass.setName("Unbounded map Grass");
        this.dataSeriesUnboundedMapAVGLifeLength = new XYChart.Series();
        dataSeriesUnboundedMapAVGLifeLength.setName("Unbounded map AVG Life Length");
        this.dataSeriesUnboundedMapAVGEnergy = new XYChart.Series();
        dataSeriesUnboundedMapAVGEnergy.setName("Unbounded map AVG Energy");
        this.dataSeriesUnboundedMapAVGChildren = new XYChart.Series();
        dataSeriesUnboundedMapAVGChildren.setName("Unbounded map AVG Children Number");


        lineChart.getData().addAll(dataSeriesBoundedMapAnimal, dataSeriesBoundedMapGrass, dataSeriesBoundedMapAVGEnergy, dataSeriesBoundedMapAVGLifeLength, dataSeriesBoundedMapAVGChildren, dataSeriesUnboundedMapAnimal, dataSeriesUnboundedMapGrass, dataSeriesUnboundedMapAVGEnergy, dataSeriesUnboundedMapAVGLifeLength, dataSeriesUnboundedMapAVGChildren);
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
                IMapElement object = map.objectAt(position);
                vBox = MapElement.draw(object);

                if (map.isOccupiedByAnimal(position)) {
                    Animal animalToFollow = map.animalAt(position);
                    // following animal
                    vBox.setOnMouseClicked((e) -> animalInfo(animalToFollow, map.boundedMap));
                }
                // checking if is on desert position;
                if (position.follows(jungleLowerLeft) && position.precedes(jungleUpperRight)) {
                    vBox.setStyle("-fx-background-color: rgb(14, 102, 13);");
                }
                vBox.setAlignment(Pos.CENTER);
                pane.add(vBox, i, 1 + upperRight.getY() - j, 1, 1);
            }
        }
    }


    private void animalInfo(Animal animal, boolean boundedMap) {
        boolean flag = false;
        Stage animalWindow = new Stage();
        VBox allAnimalInfo = new VBox();
        // condition Engine Thread suspended
        if ((!boundedMapEngineON) && boundedMap) {
            flag = true;
            animalWindow.setTitle("Bounded Map Animal");
            // starting to follow animal
            this.boundedMapAnimalToFollow = animal;
            this.boundedMapAnimalInfo = allAnimalInfo;

        }
        if ((!unboundedMapEngineON) && (!boundedMap)) {
            flag = true;
            animalWindow.setTitle("Unbounded Map Animal");
            // starting to follow animal
            this.unboundedMapAnimalToFollow = animal;
            this.unboundedMapAnimalInfo = allAnimalInfo;
        }

        if (flag) {
            allAnimalInfo.setAlignment(Pos.CENTER);
            updateAnimalFollowingScene(allAnimalInfo, animal);
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(allAnimalInfo);
            Scene animalScene = new Scene(stackPane, 500, 100);
            animalWindow.setScene(animalScene);
            animalWindow.show();
        }
    }

    public void updateAnimalFollowingScene(VBox allAnimalInfo, Animal animal) {
        allAnimalInfo.getChildren().clear();
        // animal genotype
        HBox animalGenotype = new HBox();
        Label labelGenotype = new Label("Animal genotype: ");
        String AnimalGenotypeString = "";
        for (int g : animal.genes.getGenes()) {
            AnimalGenotypeString += g + " ";
        }
        Label labelGenotypeValue = new Label(AnimalGenotypeString);
        animalGenotype.setAlignment(Pos.CENTER);
        animalGenotype.getChildren().addAll(labelGenotype, labelGenotypeValue);

        // animal children number
        HBox animalChildren = new HBox();
        Label labelChildren = new Label("Animal number of children: ");
        Label labelChildrenValue = new Label(String.valueOf(animal.numberOfChildren));
        animalChildren.setAlignment(Pos.CENTER);
        animalChildren.getChildren().addAll(labelChildren, labelChildrenValue);


        // animal age of death
        HBox animalDeath = new HBox();
        Label labelDeath = new Label("Animal death age: ");
        Label labelDeathValue = new Label();
        if (animal.deathDate == -1) {
            labelDeathValue.setText("Still alive");
        } else {
            labelDeathValue.setText(String.valueOf(animal.deathDate));
        }
        animalDeath.setAlignment(Pos.CENTER);
        animalDeath.getChildren().addAll(labelDeath, labelDeathValue);

        allAnimalInfo.getChildren().addAll(animalGenotype, animalChildren, animalDeath);
    }


    private void constructInfo() {
        this.additionalInfoWrapper = new VBox();

        this.ToggleEnginesWrapper = new HBox();
        this.boundedMapEngineButton = new Button("Start / Resume Bounded Map");
        this.unboundedMapEngineButton = new Button("Start / Resume Unbounded Map");
        this.ToggleEnginesWrapper.getChildren().addAll(boundedMapEngineButton, unboundedMapEngineButton);

        this.boundedMapActualAgeWrapper = new HBox();
        Label boundedMapActualAgeLabel = new Label("Bounded Map Actual Age: ");
        this.boundedMapActualAge = new Label("");
        this.boundedMapGenotypeDominantWrapper = new HBox();
        Label boundedDominantLabel = new Label("Bounded Map Dominant: ");
        this.boundedMapGenotypeDominant = new Label();
        boundedMapGenotypeDominant.setText("");
        HBox boundedMapMagicReproductionsLeftWrapper = new HBox();
        Label boundedMapMagicReproductionsLeftLabel = new Label("Bounded Map Magic Reproductions Left: ");
        this.boundedMapMagicReproductionsLeft = new Label();

        boundedMapActualAgeWrapper.getChildren().addAll(boundedMapActualAgeLabel, boundedMapActualAge);
        boundedMapGenotypeDominantWrapper.getChildren().addAll(boundedDominantLabel, boundedMapGenotypeDominant);
        boundedMapMagicReproductionsLeftWrapper.getChildren().addAll(boundedMapMagicReproductionsLeftLabel, boundedMapMagicReproductionsLeft);

        this.unboundedMapActualAgeWrapper = new HBox();
        Label unboundedMapActualAgeLabel = new Label("Unbounded Map Actual Age: ");
        this.unboundedMapActualAge = new Label("");
        this.unboundedMapGenotypeDominantWrapper = new HBox();
        Label unboundedDominantLabel = new Label("Unbounded Map Dominant: ");
        this.unboundedMapGenotypeDominant = new Label("");
        HBox unboundedMapMagicReproductionsLeftWrapper = new HBox();
        Label unboundedMapMagicReproductionsLeftLabel = new Label("Unbounded Map Magic Reproductions Left: ");
        this.unboundedMapMagicReproductionsLeft = new Label("");

        unboundedMapActualAgeWrapper.getChildren().addAll(unboundedMapActualAgeLabel, unboundedMapActualAge);
        unboundedMapGenotypeDominantWrapper.getChildren().addAll(unboundedDominantLabel, unboundedMapGenotypeDominant);
        unboundedMapMagicReproductionsLeftWrapper.getChildren().addAll(unboundedMapMagicReproductionsLeftLabel, unboundedMapMagicReproductionsLeft);


        this.exitAndSaveDataButton = new Button("Exit And Save Data");
        exitAndSaveDataButton.setOnAction(e -> exitAndSaveSimulationData());

        additionalInfoWrapper.setAlignment(Pos.CENTER);
        additionalInfoWrapper.setSpacing(20);
        additionalInfoWrapper.getChildren().addAll(boundedMapActualAgeWrapper, boundedMapGenotypeDominantWrapper, boundedMapMagicReproductionsLeftWrapper, unboundedMapActualAgeWrapper, unboundedMapGenotypeDominantWrapper, unboundedMapMagicReproductionsLeftWrapper, ToggleEnginesWrapper, exitAndSaveDataButton);
        infoContainer.getChildren().add(additionalInfoWrapper);


    }

    public void startSimulationEngine(boolean bounded) {
        if (bounded) {
            SimulationEngine engine = new SimulationEngine(this, bounded, lowerLeft, upperRight, jungleLowerLeft, jungleUpperRight, ageCost, grassEnergy, animalsNumber, startEnergy);
            Thread boundedMapEngineThread = new Thread(engine);
            this.boundedMapEngineThread = boundedMapEngineThread;

            boundedMapEngineButton.setOnMouseClicked(e -> boundedMapToggleEngineThread());
            boundedMapEngineThread.start();
        } else {
            SimulationEngine engine = new SimulationEngine(this, bounded, lowerLeft, upperRight, jungleLowerLeft, jungleUpperRight, ageCost, grassEnergy, animalsNumber, startEnergy);
            Thread unboundedMapEngineThread = new Thread(engine);
            this.unboundedMapEngineThread = unboundedMapEngineThread;
            unboundedMapEngineButton.setOnMouseClicked(e -> unboundedMapToggleEngineThread());
            unboundedMapEngineThread.start();
        }
    }

    public void additionalInfoUpdate(SavannaMap map) {
        if (map.boundedMap) {
            boundedMapGenotypeDominant.setText(map.dominant);
            boundedMapActualAge.setText(String.valueOf(map.ageCounter));
            boundedMapMagicReproductionsLeft.setText(String.valueOf(map.magicReproductionLeft));
        } else {
            unboundedMapGenotypeDominant.setText(map.dominant);
            unboundedMapActualAge.setText(String.valueOf(map.ageCounter));
            unboundedMapMagicReproductionsLeft.setText(String.valueOf(map.magicReproductionLeft));

        }
    }

    @Override
    public void positionChanged(SavannaMap map) {
        Platform.runLater(() -> {
            updateMap(map);
            lineChartUpdate(map);
            additionalInfoUpdate(map);
            if (map.boundedMap) {
                // checking if App is following animal
                if (boundedMapAnimalToFollow != null) {
                    updateAnimalFollowingScene(this.boundedMapAnimalInfo, this.boundedMapAnimalToFollow);
                }
            } else {
                if (unboundedMapAnimalToFollow != null) {
                    updateAnimalFollowingScene(this.unboundedMapAnimalInfo, this.unboundedMapAnimalToFollow);
                }
            }
        });
    }


    private void saveToCSV(String path, XYChart.Series animalDataSeries, XYChart.Series grassDataSeries, XYChart.Series avgEnergyDataSeries, XYChart.Series avgLiveLengthDataSeries, XYChart.Series avgChildrenDataSeries) {
        try (PrintWriter writer = new PrintWriter(path)) {
            StringBuilder dataBuilder = new StringBuilder();
            String separator = ";";
            String newLine = "\n";

            dataBuilder.append("date");
            dataBuilder.append(separator);
            dataBuilder.append("animals");
            dataBuilder.append(separator);
            dataBuilder.append("grass");
            dataBuilder.append(separator);
            dataBuilder.append("avg energy");
            dataBuilder.append(separator);
            dataBuilder.append("avg life");
            dataBuilder.append(separator);
            dataBuilder.append("avg children");
            dataBuilder.append(newLine);

            int animalsCounter = 0;
            int grassCounter = 0;
            int avgEnergyCounter = 0;
            int avgLiveCounter = 0;
            int avgChildrenCounter = 0;

            int lastBoundedId = dataSeriesBoundedMapAnimal.getData().size();

            for (int i = 0; i < lastBoundedId; i++) {
                // single Age data
                XYChart.Data animals = (XYChart.Data) animalDataSeries.getData().get(i);
                XYChart.Data grass = (XYChart.Data) grassDataSeries.getData().get(i);
                XYChart.Data avgEnergy = (XYChart.Data) avgEnergyDataSeries.getData().get(i);
                XYChart.Data avgLive = (XYChart.Data) avgLiveLengthDataSeries.getData().get(i);
                XYChart.Data avgChildren = (XYChart.Data) avgChildrenDataSeries.getData().get(i);

                animalsCounter += (int) animals.getYValue();
                grassCounter += (int) grass.getYValue();
                avgEnergyCounter += (int) avgEnergy.getYValue();
                avgLiveCounter += (int) avgLive.getYValue();
                avgChildrenCounter += (int) avgChildren.getYValue();

                dataBuilder.append(i);
                dataBuilder.append(separator);
                dataBuilder.append(animals.getYValue());
                dataBuilder.append(separator);
                dataBuilder.append(grass.getYValue());
                dataBuilder.append(separator);
                dataBuilder.append(avgEnergy.getYValue());
                dataBuilder.append(separator);
                dataBuilder.append(avgLive.getYValue());
                dataBuilder.append(separator);
                dataBuilder.append(avgChildren.getYValue());
                dataBuilder.append(newLine);

            }
            // avg data
            dataBuilder.append(newLine);
            dataBuilder.append("avg");
            dataBuilder.append(separator);
            dataBuilder.append((double) animalsCounter / (double) lastBoundedId);
            dataBuilder.append(separator);
            dataBuilder.append((double) grassCounter / (double) lastBoundedId);
            dataBuilder.append(separator);
            dataBuilder.append((double) avgEnergyCounter / (double) lastBoundedId);
            dataBuilder.append(separator);
            dataBuilder.append((double) avgLiveCounter / (double) lastBoundedId);
            dataBuilder.append(separator);
            dataBuilder.append((double) avgChildrenCounter / (double) lastBoundedId);
            dataBuilder.append(newLine);
            writer.write(dataBuilder.toString());

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exitAndSaveSimulationData() {
        String boundedMapCSVPath = "src/main/resources/boundedMapSimulationData.csv";
        String unboundedMapCSVPath = "src/main/resources/unboundedMapSimulationData.csv";

        // bounded map data from simulation to CSV
        saveToCSV(boundedMapCSVPath, dataSeriesBoundedMapAnimal, dataSeriesBoundedMapGrass, dataSeriesBoundedMapAVGEnergy, dataSeriesBoundedMapAVGLifeLength, dataSeriesBoundedMapAVGChildren);
        // unbounded map data from simulation to CSV
        saveToCSV(unboundedMapCSVPath, dataSeriesUnboundedMapAnimal, dataSeriesUnboundedMapGrass, dataSeriesUnboundedMapAVGEnergy, dataSeriesUnboundedMapAVGLifeLength, dataSeriesUnboundedMapAVGChildren);

        Platform.exit();
    }
}
