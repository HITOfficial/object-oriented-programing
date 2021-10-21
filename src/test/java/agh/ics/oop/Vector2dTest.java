package agh.ics.oop;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Vector2dTest {
    public Vector2d position1 = new Vector2d(1, 2);
    public Vector2d position2 = new Vector2d(-2, 1);
    public Vector2d position3 = new Vector2d(0, 0);
    public Vector2d position4 = new Vector2d(0, 0);
    public Vector2d position5 = new Vector2d(-1000, 1000);
    public Vector2d position6 = new Vector2d(1000, -1000);

    @Test
    public void textEquals() {
        assertEquals(false, position1.equals(position2));
        assertEquals(true, position3.equals(position4));
        assertEquals(false, position5.equals(position6));
    }

    @Test
    public void testPrecedes() {
        assertEquals(false, position1.precedes(position2));
        assertEquals(true, position3.precedes(position4));
        assertEquals(false, position5.precedes(position6));
    }

    @Test
    public void testFollows() {
        assertEquals(true, position1.follows(position2));
        assertEquals(true, position3.follows(position4));
        assertEquals(false, position5.follows(position6));
    }

    @Test
    public void testUpperRight() {
        assertEquals(new Vector2d(1, 2), position1.upperRight(position2));
        assertEquals(new Vector2d(0, 0), position3.upperRight(position4));
        assertEquals(new Vector2d(1000, 1000), position5.upperRight(position6));
    }

    @Test
    public void testLowerLeft() {
        assertEquals(new Vector2d(-2, 1), position1.lowerLeft(position2));
        assertEquals(new Vector2d(0, 0), position3.lowerLeft(position4));
        assertEquals(new Vector2d(-1000, -1000), position5.lowerLeft(position6));
    }

    @Test
    public void testAdd() {
        assertEquals(new Vector2d(-1, 3), position1.add(position2));
        assertEquals(new Vector2d(0, 0), position3.add(position4));
        assertEquals(new Vector2d(0, 0), position5.add(position6));
    }

    @Test
    public void testSubtrack() {
        assertEquals(new Vector2d(3, 1), position1.subtrack(position2));
        assertEquals(new Vector2d(0, 0), position3.subtrack(position4));
        assertEquals(new Vector2d(-2000, 2000), position5.subtrack(position6));
    }

    @Test
    public void testOpposite() {
        assertEquals(new Vector2d(-1, -2), position1.opposite());
        assertEquals(new Vector2d(2, -1), position2.opposite());
        assertEquals(new Vector2d(0, 0), position3.opposite());
        assertEquals(new Vector2d(0, 0), position4.opposite());
        assertEquals(new Vector2d(1000, -1000), position5.opposite());
        assertEquals(new Vector2d(-1000, 1000), position6.opposite());
    }

}
