package agh.ics.oop;

import org.junit.Test;

public class Vector2dTest {
    public final Vector2d position1;
    public final Vector2d position2;
    public Vector2dTest(Vector2d position1, Vector2d position2) {
        this.position1 = position1;
        this.position2 = position2;
    }

    @Test
    public void runVector2dTest() {
        System.out.println(position1.equals(position2));
        System.out.println(position1.toString());
        System.out.println(position1.precedes(position2));
        System.out.println(position1.follows(position2));
        System.out.println(position1.upperRight(position2));
        System.out.println(position1.lowerLeft(position2));
        System.out.println(position1.add(position2));
        System.out.println(position1.subtrack(position2));
        System.out.println(position1.opposite());
    }
}
