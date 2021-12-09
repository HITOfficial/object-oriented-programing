package agh.ics.oop;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

public class World {
    public static void main(String[] args) {
        Application.launch(app.class,args);
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
    }
}
