package agh.ics.oop;

import agh.ics.oop.gui.App;

import java.util.*;

// becouse I don't know any sorted linked lists, and TreeSet cannot have duplicates so complexity to find all animals with same Energy == O(N)


public class SavannaMap implements IWorldMap {
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final Vector2d jungleLowerLeft;
    private final Vector2d jungleUpperRight;

    private final boolean boundedMap = true;
    public final int ageCost = 5;
    public final int startEnergy = 100;
    public final int minReproductionEnergy = 20;
    private int animalsCounter = 0;
    private int grassCounter = 0;
    private int ageCounter = 0;
    private IPositionChangeObserver observer;

    public LinkedHashMap<Vector2d, Grass> grass = new LinkedHashMap<>();
    // can be more than one Animal on same Coordinate, so keeping Animals in Linked list
    public LinkedHashMap<Vector2d, LinkedList<Animal>> animals = new LinkedHashMap<>();


    public SavannaMap(IPositionChangeObserver observer) {
        this.observer = observer;
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(99, 30);
        this.jungleLowerLeft = new Vector2d(45, 10);
        this.jungleUpperRight = new Vector2d(54, 20);
        animalsLinkedList();
    }



    public SavannaMap(Vector2d lowerLeft, Vector2d upperRight, Vector2d jungleLowerLeft, Vector2d jungleUpperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.jungleLowerLeft = jungleLowerLeft;
        this.jungleUpperRight = jungleUpperRight;
        animalsLinkedList();
    }

    // creating on every Hash of Vector2d LinkedList
    private void animalsLinkedList() {
        for (int i = lowerLeft.getX(); i < upperRight.getX(); i++) {
            for (int j = lowerLeft.getY(); j < upperRight.getY(); j++) {
                animals.put(new Vector2d(i, j), new LinkedList<Animal>());
            }
        }
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
        if (isOccupiedByAnimal(position)) {
            for (Animal animal : animals.get(position)) {
                if (animal.energy > maxEnergy) {
                    maxEnergy = animal.energy;
                }
            }
            return allAnimalsWithGivenEnergy(maxEnergy, position);
        } else {
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
            LinkedList<Animal> top2Animal = top2EnergyAnimals(position);
            if (top2Animal.size() > 0) {
                tmpLinkedList.add(top2EnergyAnimals(position).get(0));
            }
        }
        return tmpLinkedList;
    }


    public void nextAge() {
        spawnGrass();
        moveAnimals();
        eatGrass();
        reproduction();
        updateAgeCounter();

        // UI info about reload map
        this.observer.positionChanged();
    }


    public void addAnimals(LinkedList<Animal> animalsList) {
        for( Animal animal : animalsList) {
            addAnimal(animal);
        }
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

    // single reproduction complexity +/- O(x*y*n)
    private void reproduction() {
        // maximal one reproduction on a Coordinate
        for (Vector2d position : animals.keySet()) {
            LinkedList<Animal> animalsList = reproductionAnimals(position);
            if (animalsList.size() == 2) {
                if (animalsList.get(1).energy >= minReproductionEnergy && animalsList.get(0).energy >= minReproductionEnergy) {
                    Animal newAnimal = animalsList.get(0).reproduction(animalsList.get(1));
                    animals.get(newAnimal.position).push(newAnimal);
                    updateAnimalsCounter();
                }
            }
        }
    }


    public void addAnimal(Animal animal) {
        LinkedList<Animal> x = animals.get(animal.position);
        animals.get(animal.position).add(animal);
    }


    @Override
    public boolean place(Animal animal) {
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }


    public boolean canMoveTo(Vector2d position) {
        if (boundedMap) {
//            new position still on map
            if (position.precedes(upperRight) && position.follows(lowerLeft)) {
                // removing from previous position
                return true;
            }
        }
        // not bounded map
        else {
            int newX;
            int newY;
            // X: right -> left
            if (position.getX() > upperRight.getX()) {
                newX = position.getX() % (upperRight.getX() - lowerLeft.getX());
            }
            // X: left -> right
            else {
                newX = position.getX() + upperRight.getX() - lowerLeft.getX();
            }
            // Y: top -> bottom
            if (position.getY() > upperRight.getY()) {
                newY = position.getY() % (upperRight.getY() - lowerLeft.getY());
            }
            // Y: bottom -> top
            else {
                newY = position.getY() + upperRight.getY() - lowerLeft.getY();
            }
            return true;
        }
        // converting move to be still on map
        return false;

    }

    public void eatGrass() {
        LinkedList<Grass> grassToRemoveList = new LinkedList<>();
        for (Vector2d key : grass.keySet()) {
            LinkedList<Animal> animalsToEatGrass = top1EnergyAnimals(key);
            if (animalsToEatGrass.size() > 0) {
                // all animals with top energy consume grass
                for (Animal animal : animalsToEatGrass) {
                    animal.updateEnergy(grass.get(key).getEnergy() / animalsToEatGrass.size());
                }
                grassToRemoveList.add(grass.get(key));
            }
        }
        for (Grass g : grassToRemoveList) {
            grass.remove(g.getPosition());
        }
    }

    public void moveAnimals() {
        LinkedList<Animal> deadAnimals = new LinkedList<>();
        LinkedList<Animal> movedAnimals = new LinkedList<>();
        for (Vector2d position : animals.keySet()) {
            for (Animal animal : animals.get(position)) {
                // new era energy cost
                animal.updateEnergy((-1) * ageCost);
                // if animal is dead after position changed -> removing him from map
                if (animal.isDead()) {
                    deadAnimals.add(animal);
                }

                animal.rotate();
                Vector2d newPosition = position.add(animal.direction.toUnitVector());
                if (canMoveTo(newPosition)) {
                    animal.newPosition = newPosition;
                    movedAnimals.add(animal);
                }
            }
        }
        // removing dead animals
        for (Animal animal : deadAnimals) {
            Vector2d position = animal.position;
            animals.get(position).remove(animal);
        }
        // moving animals on hashmap
        for (Animal animal : movedAnimals) {
            animals.get(animal.position).remove(animal);
            animal.position = animal.newPosition;
            animals.get(animal.position).add(animal);
        }
    }


    @Override
    public Object objectAt(Vector2d position) {
        if (isOccupiedByAnimal(position)) {
            return animals.get(position).get(0);
        }
        return grass.get(position);
    }

    // at least one animal on this position;
    public boolean isOccupiedByAnimal(Vector2d position) {
        return animals.get(position).size() > 0;
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
