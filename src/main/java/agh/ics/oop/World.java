package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));

        MapDirectionTest.nextMapDirection();
        MapDirectionTest.previousMapDirection();
        Vector2dTest vector2dTest = new Vector2dTest(position1, position2);
        vector2dTest.runVector2dTest();
    }

}
