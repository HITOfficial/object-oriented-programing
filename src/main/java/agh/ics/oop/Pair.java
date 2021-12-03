package agh.ics.oop;

import java.util.Objects;

public class Pair<Vector2d, type extends Object> {
    public Vector2d vector;
    public Object type;

    public Pair(Vector2d vector, Object type){
        this.vector = vector;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(vector, pair.vector) && Objects.equals(type, pair.type);
    }

    public int hashCode() {
        return Objects.hash(vector, type);
    }
}
