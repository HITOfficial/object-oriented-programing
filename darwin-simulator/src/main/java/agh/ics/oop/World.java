package agh.ics.oop;


import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

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
            System.out.println((int) Math.round(Math.random()));
            System.out.println((int) Math.round(Math.random()));
            System.out.println((int) Math.round(Math.random()));
            System.out.println((int) Math.round(Math.random()));
            System.out.println((int) Math.round(Math.random()));

            SavannaMap map = new SavannaMap();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();

            Animal animal1 = new Animal(map, new Vector2d(3,33), 100);
            Animal animal2 = new Animal(map, new Vector2d(3,33), 1606);

            Animal animal3 = animal1.reproduction(animal2);

        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }}
