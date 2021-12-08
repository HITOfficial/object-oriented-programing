package agh.ics.oop;

import javax.xml.stream.FactoryConfigurationError;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GrassField extends AbstractWorldMap {
    private final List<Grass> grassesList = new LinkedList<>();
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
                if (checkGrass(position)) {
                    this.grassesList.add(new Grass(position));
                    break;
                }
            }
        }
    }

    public boolean checkGrass(Vector2d position) {
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
        return position.follows(this.leftBottomCorner) && position.precedes(this.rightTopCorner) && !isOccupied(position);  // w RectangularMap jest identyczna metoda - jak się to ma do nieograniczoności mapy?
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
}
