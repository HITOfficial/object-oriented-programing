package agh.ics.oop;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

public class World {
    public static void main(String[] args) {
        try {
            MoveDirection[] directions = OptionsParser.parse(new String[]{"f","x", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
            IWorldMap map = new RectangularMap(10, 5);
            Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
            SimulationEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
//        assertEquals(engine.getAnimal(0).getPosition(), new Vector2d(3, 0));
//        assertFalse(engine.getAnimal(0).getPosition().equals(new Vector2d(4, 0)));
//        assertTrue(engine.getAnimal(1).getPosition().equals(new Vector2d(2, 4)));
//        assertFalse(engine.getAnimal(1).getPosition().equals(new Vector2d(0, 7)));


//        MoveDirection[] directions2 = OptionsParser.parse(new String[]{"f", "b", "f", "b", "f", "b", "f", "b", "f", "b", "f", "b", "f", "b", "f", "b"});
//        IWorldMap map2 = new GrassField(10);
//        Vector2d[] positions2 = {new Vector2d(1, 1), new Vector2d(0, 0)};
//        SimulationEngine engine2 = new SimulationEngine(directions2, map2, positions2);
//        engine2.run();
//        assertTrue(engine2.getAnimal(0).getPosition().equals(new Vector2d(1, 9)));
//        assertFalse(engine2.getAnimal(0).getPosition().equals(new Vector2d(0, 4)));
//        assertTrue(engine2.getAnimal(1).getPosition().equals(new Vector2d(0, -8)));
//        assertFalse(engine2.getAnimal(1).getPosition().equals(new Vector2d(1, 1)));
    }
}
