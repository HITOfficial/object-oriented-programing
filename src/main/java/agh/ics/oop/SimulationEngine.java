package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine {
    private final MoveDirection[] directionsArray;
    private final List<Animal> animalsList;
    private final IWorldMap map;

    public SimulationEngine(MoveDirection[] directionsArray, IWorldMap map, Vector2d[] animalsArray) {
        this.directionsArray = directionsArray;
        this.map = map;
        this.animalsList = new ArrayList<>();
        for (Vector2d position : animalsArray) {
            Animal animal = new Animal(this.map, position);
            if (map.canMoveTo(position)){
                this.animalsList.add(animal);
                this.map.place(animal);
            }
        }
    }

    public Animal getAnimal(int i) {
        return animalsList.get(i);
    }

    @Override
    public void run() {
        for (int i = 0; i < this.directionsArray.length; i++) {
            this.animalsList.get(i % this.animalsList.size()).move(this.directionsArray[i]);
        }
    }
}
