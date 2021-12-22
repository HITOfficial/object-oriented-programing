package agh.ics.oop;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class SimulationEngine implements Runnable {
    public SavannaMap map;
    public IPositionChangeObserver observer;


    public SimulationEngine(IPositionChangeObserver observer, boolean boundedMap, Vector2d lowerLeft, Vector2d upperRight, Vector2d jungleLowerLeft, Vector2d jungleUpperRight, int ageCost, int grassEnergy, int animalsNumber, int startEnergy) {
        this.observer = observer;
        this.map = new SavannaMap(observer,boundedMap,lowerLeft,upperRight,jungleLowerLeft,jungleUpperRight,ageCost,grassEnergy,animalsNumber,startEnergy);
    }


    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            map.nextAge();
            try {
                Thread.sleep(200);

            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }
}
