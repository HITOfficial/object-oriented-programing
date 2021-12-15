package agh.ics.oop;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class Animal {
    private final IWorldMap map;
    private Vector2d position;
    private MapDirection direction = MapDirection.NORTH;
    private final LinkedList<IPositionChangeObserver> observers = new LinkedList<>();

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.position = initialPosition;
        this.map = map;
    }

    public Animal(IWorldMap map) {
        this(map, new Vector2d(2, 2));
    }

    public Animal() {
        this(new RectangularMap(5, 5));
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public MapDirection getDirection() {
        return this.direction;
    }

    public String toString() {
        return switch (this.direction) {
            case NORTH -> "^";
            case EAST -> ">";
            case SOUTH -> "v";
            case WEST -> "<";
        };
    }

    void addObserver(IPositionChangeObserver observer) {
        this.observers.add(observer);
    }

    void removeObserver(IPositionChangeObserver observer) {
        this.observers.remove(observer);
    }

    private void positionChanged() throws FileNotFoundException, InterruptedException {
        for (IPositionChangeObserver observer : observers) {
            observer.update();
        }
    }

    public boolean move(MoveDirection direction) throws FileNotFoundException, InterruptedException {
        Vector2d newPosition = position;
        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> newPosition = newPosition.add(this.direction.toUnitVector());
            case BACKWARD -> newPosition = newPosition.subtract(this.direction.toUnitVector());
        }
        if (this.map.canMoveTo(newPosition)) {
            this.position = newPosition;
            positionChanged();
            return true;
        } else {
            return false;
        }
    }
}
