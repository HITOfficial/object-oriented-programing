package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

abstract class AbstractWorldMap implements IWorldMap {
    protected Vector2d rightTopCorner;
    protected Vector2d leftBottomCorner;
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

    @Override
    public String toString() {
        return mapVisualizer.draw(leftBottomCorner, rightTopCorner);
    }
}
