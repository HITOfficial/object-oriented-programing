package agh.ics.oop;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class MovesTest {

    @Test
    public void MoveTest (){
        MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        assertTrue(engine.getAnimal(0).isAt(new Vector2d(3, 0)));
        assertFalse(engine.getAnimal(0).isAt(new Vector2d(4, 0)));
        assertTrue(engine.getAnimal(1).isAt(new Vector2d(2, 4)));
        assertFalse(engine.getAnimal(1).isAt(new Vector2d(3, 4)));
    }
}
