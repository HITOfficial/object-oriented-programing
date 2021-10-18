package agh.ics.oop;

import org.junit.Test;

public class MapDirectionTest {

    @Test
    public static void nextMapDirection() {
        for (MapDirection arg : MapDirection.values()) {
            System.out.println(arg.next());
        }
    }

    @Test
    public static void previousMapDirection() {
        for (MapDirection arg : MapDirection.values()) {
            System.out.println(arg.previous());
        }
    }
}