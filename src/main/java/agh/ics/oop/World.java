package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        MoveDirection[] directions2 = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        IWorldMap map2 = new GrassField(10);
        Vector2d[] positions2 = {new Vector2d(1, 1), new Vector2d(0, 0)};
        SimulationEngine engine2 = new SimulationEngine(directions2, map2, positions2);
        engine2.run();
        Grass grass = new Grass(new Vector2d(2,2));
        System.out.println(grass);


//        MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
//        IWorldMap map = new RectangularMap(10, 5);
//        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
//        SimulationEngine engine = new SimulationEngine(directions, map, positions);
//        engine.run();
    }
}
