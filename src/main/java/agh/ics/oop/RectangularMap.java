package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

public class RectangularMap implements IWorldMap {
    private final Vector2d cornerTopRight;
    private final  Vector2d cornerBottomLeft = new Vector2d(0, 0);
    private final List<Animal> animalsList = new LinkedList<>();
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);

    public RectangularMap(int width, int height) {
        this.cornerTopRight = new Vector2d(width, height);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        // being inside map and place to which is moving isn't locked by another animal
        return position.follows(this.cornerBottomLeft) && position.precedes(this.cornerTopRight) && !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (!isOccupied(animal.getPosition())){
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
            if (animal.isAt(position)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animalsList){
            if (animal.isAt(position)){
                return animal;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return mapVisualizer.draw(cornerBottomLeft,cornerTopRight);
    }
}
