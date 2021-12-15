package agh.ics.oop;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

// becouse I don't know any sorted linked lists, and TreeSet cannot have duplicates so complexity to find all animals with same Energy == O(N)


public class SavannaMap implements IWorldMap {
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final Vector2d jungleLowerLeft;
    private final Vector2d jungleUpperRight;

    private final boolean boundedMap = true;
    public final int EraCost = -5;
    public final int startEnergy = 100;
    public final int minReproductionEnergy = 20;
    private int animalsCounter = 0;
    private int grassCounter = 0;
    private int ageCounter = 0;


    public LinkedHashMap<Vector2d, Grass> grass = new LinkedHashMap<>();
    // can be more than one Animal on same Coordinate, so keeping Animals in Linked list
    public LinkedHashMap<Vector2d, LinkedList<Animal>> animals = new LinkedHashMap<>();


    public SavannaMap() {
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(100, 30);
        this.jungleLowerLeft = new Vector2d(45, 10);
        this.jungleUpperRight = new Vector2d(55, 20);
    }

    public SavannaMap(Vector2d lowerLeft, Vector2d upperRight, Vector2d jungleLowerLeft, Vector2d jungleUpperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.jungleLowerLeft = jungleLowerLeft;
        this.jungleUpperRight = jungleUpperRight;
    }

    public LinkedList<Animal> allAnimalsWithGivenEnergy(int energy, Vector2d position) {
        LinkedList<Animal> tmpLinkedList = new LinkedList<>();
        for (Animal animal : animals.get(position)) {
            if (animal.energy == energy) {
                tmpLinkedList.add(animal);
            }
        }
        return tmpLinkedList;
    }

    // finding all animals with the highest energy on the same Coordinate complexity O(2n)
    public LinkedList<Animal> top1EnergyAnimals(Vector2d position) {
        int maxEnergy = 0;
        if (animalAt(position)) {
            for (Animal animal : animals.get(position)) {
                if (animal.energy > maxEnergy) {
                    maxEnergy = animal.energy;
                }
            }
            return allAnimalsWithGivenEnergy(maxEnergy, position);
        }
        else {
            return new LinkedList<>();
        }
    }

    // complexity O(3n)
    public LinkedList<Animal> top2EnergyAnimals(Vector2d position) {
        LinkedList<Animal> tmpLinkedList = new LinkedList<>();
        int top1Energy = 0;
        // top1
        for (Animal animal : animals.get(position)) {
            if (animal.energy > top1Energy) {
                top1Energy = animal.energy;
            }
        }

        // top2
        int maxEnergy = 0;
        for (Animal animal : animals.get(position)) {
            if (animal.energy > maxEnergy && animal.energy != top1Energy) {
                maxEnergy = animal.energy;
            }
        }
        return allAnimalsWithGivenEnergy(maxEnergy, position);
    }


    public LinkedList<Animal> reproductionAnimals(Vector2d position) {
        LinkedList<Animal> tmpLinkedList = top1EnergyAnimals(position);
        // are at least 2 animals on the same coordinate;
        if (tmpLinkedList.size() < 2 && animals.get(position).size() > 1) {
            tmpLinkedList.add(top2EnergyAnimals(position).get(0));
        }
        return tmpLinkedList;

    }


    public void nextAge() {
        // moving all animals <- in move is removing all animals without enought energy
        spawnGrass();
        moveAnimals();
        eatGrass();
        reproduction();
        updateAgeCounter();

    }


    private void spawnGrass() {
        // jungle is a square so area = x^2
        int junglePossibilities = (int) Math.pow((jungleUpperRight.subtract(jungleLowerLeft).getX() + 1), 2);
        int junglePossibilitiesCopy = junglePossibilities;


        while (junglePossibilities > 0) {
            // random grass field
            int x = (int) (Math.random() * jungleUpperRight.getX() - jungleLowerLeft.getX() + 1) + jungleLowerLeft.getX();
            int y = (int) (Math.random() * jungleUpperRight.getY() - jungleLowerLeft.getY() + 1) + jungleLowerLeft.getY();
            Vector2d jungleVector = new Vector2d(x, y);
            if (objectAt(jungleVector) == null) {
                grass.put(jungleVector, new Grass(jungleVector));
                updateGrassCounter();
                break;
            }
            junglePossibilities -= 1;
        }

        // desert possibilities -> map all possibilities without jungle coordinates
        int desertPossibilities = (int) (upperRight.getX() - lowerLeft.getX() + 1) * (upperRight.getX() - lowerLeft.getX() + 1) - junglePossibilitiesCopy;
        while (desertPossibilities > 0) {
            // random grass field
            int x = (int) (Math.random() * jungleUpperRight.getX() - jungleLowerLeft.getX() + 1) + jungleLowerLeft.getX();
            int y = (int) (Math.random() * jungleUpperRight.getY() - jungleLowerLeft.getY() + 1) + jungleLowerLeft.getY();
            // checking if it is not jungle coordinate
            if ((x >= jungleLowerLeft.getX() && x <= jungleUpperRight.getX()) && (y >= jungleLowerLeft.getY() && y <= jungleUpperRight.getY())) {
                // jungle coordinate
                continue;
            } else {
                Vector2d desertVector = new Vector2d(x, y);
                if (objectAt(desertVector) == null) {
                    grass.put(desertVector, new Grass(desertVector));
                    updateGrassCounter();
                    break;
                }
                desertPossibilities -= 1;
            }
        }

    }

    private void reproduction() {
        // maximal one reproducition on a Cooordinate
        for (Vector2d position : animals.keySet()) {
            LinkedList<Animal> animalsList = reproductionAnimals(position);
            if (animalsList.size() == 2) {
                if (animalsList.get(1).energy >= minReproductionEnergy) {
                    Animal newAnimal = animalsList.get(0).reproduction(animalsList.get(1));
                    animals.get(newAnimal.position).push(newAnimal);
                    updateAnimalsCounter();
                }
            }
        }
    }


    @Override
    public boolean place(Animal animal) {
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    private void removeDeadAnimal(Vector2d position, Animal animal) {
        if (animal.isDead()) {
            animals.get(position).remove(animal);
        }
    }

    public void eatGrass() {
        for (Vector2d key : grass.keySet()) {
            LinkedList<Animal> animalsToEatGrass = top1EnergyAnimals(key);
            if (animalsToEatGrass.size() > 0) {
                // all animals with top energy consume grass
                for (Animal animal : animalsToEatGrass) {
                    animal.updateEnergy(grass.get(key).getEnergy() / animalsToEatGrass.size());
                }
                grass.remove(key);
            }
        }
    }

    public void moveAnimals() {
        for (Vector2d position : animals.keySet()) {
            for (Animal animal : animals.get(position)) {
                animal.move();
                // if animal is dead after position changed -> removing him from map
                removeDeadAnimal(position, animal);
            }
        }
    }


    @Override
    public Object objectAt(Vector2d position) {
        if (animalAt(position)) {
            return animals.get(position).get(0);
        }
        return grass.get(position);
    }

    // at least one animal on this position;
    public boolean animalAt(Vector2d position) {
        return animals.get(position) != null;
    }

    private void updateGrassCounter() {
        this.grassCounter += 1;
    }

    private void updateAnimalsCounter() {
        this.animalsCounter += 1;
    }

    private void updateAgeCounter() {
        this.animalsCounter += 1;
    }
}
