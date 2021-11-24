package agh.ics.oop;

import java.util.Objects;
import java.util.Vector;

public class Vector2d {
    public final int x;
    public final int y;

    // construction to class
    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean precedes(Vector2d other) {
        if (x <= other.x && y <= other.y) {
            return true;
        } else {
            return false;
        }
    }

    public boolean follows(Vector2d other) {
        if (x >= other.x && y >= other.y) {
            return true;
        } else {
            return false;
        }
    }

    public Vector2d upperRight(Vector2d other) {
        // using Math method to return larger of args
        return new Vector2d(Math.max(x, other.x), Math.max(y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other) {
        // using Math method to return lower of args
        return new Vector2d(Math.min(x, other.x), Math.min(y, other.y));
    }



    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.x, y - other.y);
    }

    public boolean equals(Object other) {
        if (this == other) {
            // same object
            return true;
        } else if (!(other instanceof Vector2d)) {
            // other is an object from another class
            return false;
        } else {
            Vector2d tmpPoint = (Vector2d) other;
            // checking if objects has equal values
            return this.x == tmpPoint.x && this.y == tmpPoint.y;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x,this.y);
    }

    public Vector2d opposite() {
        return new Vector2d(-1 * x, -1 * y);
    }

}
