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

            SavannaMap map = new SavannaMap();
            Animal a1 = new Animal(map, new Vector2d(0,0), 165);
            Animal a2 = new Animal(map, new Vector2d(0,0), 65);
            Animal a5 = new Animal(map, new Vector2d(0,0), 60);
            Animal a3 = new Animal(map, new Vector2d(0,0), 40);
            Animal a4 = new Animal(map, new Vector2d(0,0), 40);

            map.addAnimal(a1);
            map.addAnimal(a2);
            map.addAnimal(a3);
            map.addAnimal(a5);
            map.addAnimal(a4);
//            map.grass.put(new Vector2d(0,0), new Grass(new Vector2d(0,0)));
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();
            map.nextAge();


        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }}
