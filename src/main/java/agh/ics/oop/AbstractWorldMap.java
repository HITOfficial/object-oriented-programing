package agh.ics.oop;

import java.util.*;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected Vector2d rightTopCorner;
    protected Vector2d leftBottomCorner;
    protected final LinkedHashMap<Vector2d, Animal> animals = new LinkedHashMap<>();
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected final MapBoundary mapBoundary = new MapBoundary();

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            mapBoundary.addPair(animal.getPosition(),animal.getClass());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if (animals.get(position) != null) {
            return true;
        } else {
            return false;
        }
    }


//    @Override
//    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Object type) {
//        // removing previous coordinate from linkedHashMap, and insert new one
//        Animal animal = animals.get(oldPosition);
//        mapBoundary.removePair(oldPosition, type);
//        mapBoundary.removePair(newPosition, type);
//        animals.remove(animal);
//        animals.put(newPosition, animal);
//    }

}
