package agh.ics.oop;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class SimulationEngine implements Runnable {
    public LinkedHashMap<Vector2d, Grass> grass = new LinkedHashMap<>();
    // can be more than one Animal on same Coordinate, so keeping Animals in Linked list
    public LinkedHashMap<Vector2d, LinkedList<Animal>> animals = new LinkedHashMap<>();
    public LinkedList<Animal> startingAnimals = new LinkedList<>();
    public SavannaMap map;
    public IPositionChangeObserver observer;


    public SimulationEngine(IPositionChangeObserver observer) {
        this.map = new SavannaMap(observer);
        this.observer = observer;

        LinkedList<Animal> animals = new LinkedList<>();
        Animal a1 = new Animal(map, new Vector2d(0,0), 165);
        Animal a2 = new Animal(map, new Vector2d(0,0), 65);
        Animal a5 = new Animal(map, new Vector2d(0,0), 60);
        Animal a3 = new Animal(map, new Vector2d(0,0), 40);
        Animal a4 = new Animal(map, new Vector2d(0,0), 40);

        animals.add(a1);
        animals.add(a2);
        animals.add(a3);
        animals.add(a4);
        animals.add(a5);

        System.out.println("engine start");
        map.addAnimals(startingAnimals);
        this.startingAnimals = startingAnimals;
    }


    @Override
    public void run() {
        // starting simulation
        map.addAnimals(startingAnimals);
        map.nextAge();


        for (int i = 0; i < 10; i++) {
            map.nextAge();
            System.out.println("next Age");
            try {
                Thread.sleep(50);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
