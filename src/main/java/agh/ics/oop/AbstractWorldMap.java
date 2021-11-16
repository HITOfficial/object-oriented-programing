package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

abstract class AbstractWorldMap implements IWorldMap {
    protected final Vector2d leftBottomCorner = new Vector2d(0, 0);
    protected final List<Animal> animalsList = new LinkedList<>();
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            animalsList.add(animal);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal animal : animalsList){
            if (animal.getPosition().equals(position)){
                return true;
            }
        }
        return false;
    }
}
