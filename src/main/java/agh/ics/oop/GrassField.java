package agh.ics.oop;

import javax.xml.stream.FactoryConfigurationError;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GrassField extends AbstractWorldMap {
    private final Vector2d rightTopCorner;
    private final List<Grass> grassesList = new LinkedList<>();

    public GrassField(int n) {
        this.rightTopCorner = new Vector2d((int) Math.sqrt(n), (int) Math.sqrt(n));
        newGrassList(n);
    }

    public void newGrassList(int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            while (true) {
                Vector2d position = new Vector2d((int) Math.sqrt(random.nextInt(n)) + 1, (int) Math.sqrt(random.nextInt(n)) + 1);
                if (canAddGrass(position)) {
                    this.grassesList.add(new Grass(position));
                    break;
                }
            }
        }
    }

    public boolean canAddGrass(Vector2d position) {
        for (Grass grass : grassesList) {
            if (grass.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        // being inside map and place to which is moving isn't locked by another animal
        return position.follows(this.leftBottomCorner) && position.precedes(this.rightTopCorner) && !isOccupied(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal animal : animalsList) {
            if (animal.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animalsList) {
            if (animal.getPosition().equals(position)) {
                return animal;
            }
        }
        for (Grass grass : grassesList) {
            if (grass.getPosition().equals(position)) {
                return grass;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(leftBottomCorner, rightTopCorner);
    }
}
