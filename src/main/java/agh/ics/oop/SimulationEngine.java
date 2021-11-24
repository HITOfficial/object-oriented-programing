package agh.ics.oop;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class SimulationEngine implements IEngine {
    private final MoveDirection[] directionsArray;
    private final ArrayList<Animal> animalsArray = new ArrayList<>();
    private final IWorldMap map;

    public SimulationEngine(MoveDirection[] directionsArray, IWorldMap map, Vector2d[] animalsArray) {
        this.directionsArray = directionsArray;
        this.map = map;
        for (Vector2d position : animalsArray) {
            Animal animal = new Animal(this.map, position);
            if (map.canMoveTo(position)){
                this.animalsArray.add(animal);
                this.map.place(animal);
            }
        }
    }

    public Animal getAnimal(int i) {
        return animalsArray.get(i);
    }

    @Override
    public void run() {
        for (int i = 0; i < directionsArray.length; i++) {
            animalsArray.get(i % animalsArray.size()).move(directionsArray[i]);
        }
    }
}
