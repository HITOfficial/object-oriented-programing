package agh.ics.oop;


import agh.ics.oop.gui.App;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

public class World {

    public static void main(String[] args) {
//        Application.launch(app.class, args);
        try {
//            MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
//            GrassField map = new GrassField(10);
//            Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
//            SimulationEngine engine = new SimulationEngine(directions, map, positions);
//            engine.run();

//            SavannaMap map = new SavannaMap();


            Application.launch(App.class);



//            map.addAnimal(a1);
//            map.addAnimal(a2);
//            map.addAnimal(a3);
//            map.addAnimal(a5);
//            map.addAnimal(a4);
//            map.grass.put(new Vector2d(0,0), new Grass(new Vector2d(0,0)));
            System.out.println("xx");



        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }}
