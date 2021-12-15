package agh.ics.oop;

import javax.xml.stream.FactoryConfigurationError;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GrassField extends AbstractWorldMap {
    private final LinkedHashMap<Vector2d, Grass> grasses = new LinkedHashMap<>();
    private final Vector2d leftBottomCorner = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
    private final Vector2d rightTopCorner = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);

    public GrassField(int n) {
        createGrasses(n);
    }

    public void createGrasses(int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            while (true) {
                Vector2d position = new Vector2d((int) Math.sqrt(random.nextInt(n * 10)), (int) Math.sqrt(random.nextInt(n * 10)));
                if (grasses.get(position) == null) {
                    Grass grass = new Grass(position);
                    this.grasses.put(position, grass);
                    mapBoundary.addPair(position, grass.getClass());
                    break;
                }
            }
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        // being inside map and place to which is moving isn't locked by another animal
        return position.follows(this.leftBottomCorner) && position.precedes(this.rightTopCorner) && !isOccupied(position);
    }

    @Override
    public Object objectAt(Vector2d position) {
        Animal animal = animals.get(position);
        Grass grass = grasses.get(position);
        if (animal != null) {
            return animal;
        }
        else if (grass != null) {
            return grass;
        }
        else {
            return null;
        }
    }

    public Vector2d getLowerLeft() {
        return mapBoundary.getLowerLeft();
    }

    public Vector2d getUpperRight() {
        return mapBoundary.getUpperRight();
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(mapBoundary.getLowerLeft(), mapBoundary.getUpperRight());
    }

    @Override
    public void update() {

    }
}
