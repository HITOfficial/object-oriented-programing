package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;

public class RectangularMap extends AbstractWorldMap {
    private final Vector2d leftBottomCorner = new Vector2d(0,0);
    private final Vector2d rightTopCorner;

    public RectangularMap(int width, int height) {
        this.rightTopCorner = new Vector2d(width-1, height-1);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        // being inside map and place to which is moving isn't locked by another animal
        return position.follows(this.leftBottomCorner) && position.precedes(this.rightTopCorner) && !isOccupied(position);
    }

    @Override
    public Object objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(leftBottomCorner, rightTopCorner);
    }
}
