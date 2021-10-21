package agh.ics.oop;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Vector2dTest {
    public Vector2d position1 = new Vector2d(1, 2);
    public Vector2d position2 = new Vector2d(-2, 1);

    @Test
    public void textEquals() {
        assertEquals(false, position1.equals(position2));
    }

    @Test
    public void testPrecedes() {
        assertEquals(false, position1.precedes(position2));
    }

    @Test
    public void testFollows() {
        assertEquals(true, position1.follows(position2));
    }

    @Test
    public void testUpperRight() {
        assertEquals(new Vector2d(1, 2), position1.upperRight(position2));
    }

    @Test
    public void testLowerLeft() {
        assertEquals(new Vector2d(-2, 1), position1.lowerLeft(position2));
    }

    @Test
    public void testAdd() {
        assertEquals(new Vector2d(-1, 3), position1.add(position2));
    }

    @Test
    public void testSubtrack() {
        assertEquals(new Vector2d(3, 1), position1.subtrack(position2));
    }

    @Test
    public void testOpposite() {
        assertEquals(new Vector2d(-1, -2), position1.opposite());
    }

}
