package agh.ics.oop;

import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {
    public SortedSet<Pair<Vector2d, Object>> sortedSetX = new TreeSet<Pair<Vector2d, Object>>((e1, e2) -> {
        if (e1.vector.getX() == e2.vector.getX()) {
            return e1.vector.getY() - e2.vector.getY();
        } else {
            return e1.vector.getX() - e2.vector.getX();
        }
    });
    public SortedSet<Pair<Vector2d, Object>> sortedSetY = new TreeSet<Pair<Vector2d, Object>>((e1, e2) -> {
        if (e1.vector.getY() == e2.vector.getY()) {
            return e1.vector.getX() - e2.vector.getX();
        } else {
            return e1.vector.getY() - e2.vector.getY();
        }
    });

    public void positionChanged(Vector2d previous, Vector2d actual, Object type) {
        removePair(previous, type);
        addPair(actual, type);
    }

    public void addPair(Vector2d vector, Object type) {
        sortedSetX.add(new Pair(vector, type));
        sortedSetY.add(new Pair(vector, type));
    }

    public void removePair(Vector2d vector, Object type) {
        sortedSetX.remove(new Pair<>(vector, type));
        sortedSetY.remove(new Pair<>(vector, type));
    }

    public Vector2d getLowerLeft() {
        return new Vector2d(sortedSetX.first().vector.getX(), sortedSetY.first().vector.getY());
    }

    public Vector2d getUpperRight() {
        return new Vector2d(sortedSetX.last().vector.getX(), sortedSetY.last().vector.getY());
    }
}
