package agh.ics.oop;

import java.util.*;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected Vector2d rightTopCorner;
    protected Vector2d leftBottomCorner;
    protected final LinkedHashMap<Vector2d, Animal> animals = new LinkedHashMap<>();
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(),animal);
            return true;
        }
        else {
            return false;   // brak wyjątku
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if(animals.get(position) != null) { // return (animals.get(position) != null);
            return true;
        }
        else {
        return false;
        }
    }

    public void positionChanged (Vector2d oldPosition, Vector2d newPosition){
        // removing previous coordinate from linkedHashMap, and insert new one
        Animal animal = animals.get(oldPosition);
        animals.remove(animals.get(oldPosition));
        animals.put(newPosition, animal);
    }

    // brak toString

}
