package agh.ics.oop;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary  {
    public SortedSet<Vector2d> sortedSetX = new TreeSet<>(Comparator.comparingInt(e -> e.getX()));
    public SortedSet<Vector2d> sortedSetY = new TreeSet<>(Comparator.comparingInt(e -> e.getY()));

    public void positionChanged(Vector2d previous, Vector2d actual) {
        removeVector(previous);
        addVector(actual);
    }

    public void addVector(Vector2d vector) {
        sortedSetX.add(vector);
        sortedSetY.add(vector);
    }

    public void removeVector(Vector2d vector) {
        sortedSetX.remove(vector);
        sortedSetY.remove(vector);
    }

    public Vector2d getLowerLeft() {
        return new Vector2d(sortedSetX.first().x, sortedSetY.first().getY());
    }

    public Vector2d getUpperRight() {
        return new Vector2d(sortedSetX.last().getX(), sortedSetY.last().getY());
    }
}
