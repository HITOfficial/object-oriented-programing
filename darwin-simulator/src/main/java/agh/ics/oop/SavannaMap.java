package agh.ics.oop;


import agh.ics.oop.gui.IMapElement;

import java.util.*;


public class SavannaMap implements IWorldMap {
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final Vector2d jungleLowerLeft;
    private final Vector2d jungleUpperRight;
    public boolean boundedMap = false;
    public int ageCost = 5;
    public int startEnergy = 100;
    public int minReproductionEnergy;
    public int grassEnergy;

    public int animalsCounter = 0;
    public int grassCounter = 0;
    public int ageCounter = -1;
    public int[] dominantsNumber = new int[8];
    public String dominant = "";
    private final IPositionChangeObserver observer;

    public LinkedList<Animal> deadAnimalsList = new LinkedList<>();
    public int AVGEnergyOfAliveAnimals = 0;
    public int AVGLengthOfLifeAnimals = 0;
    public int AVGNumberOfChildrenAnimals = 0;

    public int magicReproductionLeft = 3;


    public LinkedHashMap<Vector2d, Grass> grass = new LinkedHashMap<>();
    // can be more than one Animal on same Coordinate, so keeping Animals in Linked list
    public LinkedHashMap<Vector2d, LinkedList<Animal>> animals = new LinkedHashMap<>();


    public SavannaMap(IPositionChangeObserver observer) {
        this.observer = observer;
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(99, 30);
        this.jungleLowerLeft = new Vector2d(45, 10);
        this.jungleUpperRight = new Vector2d(54, 20);

        for (int i = 0; i < 2; i++) {
            Animal a1 = new Animal(this, new Vector2d(45, 25), 1000);
            Animal a2 = new Animal(this, new Vector2d(35, 25), 1000);
            addAnimal(a1.position, a1);
            addAnimal(a2.position, a2);
            newAnimalUpdateCounter();
            newAnimalDominant(a1);
            newAnimalDominant(a2);
        }
    }

    public SavannaMap(IPositionChangeObserver observer, boolean boundedMap, Vector2d lowerLeft, Vector2d upperRight, Vector2d jungleLowerLeft, Vector2d jungleUpperRight, int ageCost, int grassEnergy, int animalsNumber, int startEnergy) {
        this.observer = observer;
        this.boundedMap = boundedMap;
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.jungleLowerLeft = jungleLowerLeft;
        this.jungleUpperRight = jungleUpperRight;
        this.ageCost = ageCost;
        this.grassEnergy = grassEnergy;
        this.startEnergy = startEnergy;
        this.minReproductionEnergy = startEnergy / 2;
        // insterting random animals into map
        for (int i = 0; i < animalsNumber; i++) {
            int x = (int) (Math.random() * (upperRight.getX() - lowerLeft.getX() + 1)) + lowerLeft.getX();
            int y = (int) (Math.random() * (upperRight.getY() - lowerLeft.getY() + 1)) + lowerLeft.getY();
            Vector2d position = new Vector2d(x, y);
            Animal animal = new Animal(this, position, startEnergy);
            addAnimal(position, animal);
            newAnimalUpdateCounter();
            newAnimalDominant(animal);
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


    public void nextAge() {
        removeDeadAnimals();
        spawnGrass();

        moveAllAnimals();
        eatGrass();

        // magic reproduction
        if (magicReproductionLeft > 0 && animalsCounter <= 5) {
            magicReproduction();
        }
        else {
        reproduction();
        }

        updateAgeCounter();

        calculateDominatingGenotype();
        calculateAVGEnergyOfAliveAnimals();
        calculateAVGLengthOfLiveAnimals();
        calculateAVGNumberOfChildrenAnimals();

        // UI info to reload the map
        this.observer.positionChanged(this);
    }

    public void addAnimal(Vector2d position, Animal animal) {
        if (animals.get(position) == null) {
            animals.put(position, new LinkedList<>());
        }
        animals.get(position).add(animal);
    }

    public void removeAnimal(Vector2d position, Animal animal) {
        animals.get(position).remove(animal);
        if (animals.get(position).size() == 0) {
            animals.remove(position);
        }
    }


    private void spawnGrass() {
        // jungle is a square so area = x^2
        int junglePossibilities = (int) Math.pow((jungleUpperRight.subtract(jungleLowerLeft).getX() + 1), 2);
        int junglePossibilitiesCopy = junglePossibilities;


        while (junglePossibilities > 0) {
            // random grass spawn in jungle
            int x = (int) (Math.random() * (jungleUpperRight.getX() - jungleLowerLeft.getX() + 1)) + jungleLowerLeft.getX();
            int y = (int) (Math.random() * (jungleUpperRight.getY() - jungleLowerLeft.getY() + 1)) + jungleLowerLeft.getY();
            Vector2d jungleVector = new Vector2d(x, y);
            if (objectAt(jungleVector) == null) {
                grass.put(jungleVector, new Grass(jungleVector));
                newGrassUpdateCounter();
                break;
            }
            junglePossibilities -= 1;
        }

        // desert possibilities -> map all possibilities without jungle coordinates
        int desertPossibilities = (upperRight.getX() - lowerLeft.getX() + 1) * (upperRight.getX() - lowerLeft.getX() + 1) - junglePossibilitiesCopy;
        while (desertPossibilities > 0) {
            int x = (int) (Math.random() * (upperRight.getX() - lowerLeft.getX() + 1)) + lowerLeft.getX();
            int y = (int) (Math.random() * (upperRight.getY() - lowerLeft.getY() + 1)) + lowerLeft.getY();
            Vector2d desertVector = new Vector2d(x, y);
            if (desertVector.follows(jungleUpperRight) && desertVector.precedes(jungleUpperRight)) {
                continue;
            } else {
//                Vector2d desertVector = new Vector2d(x, y);
                if (objectAt(desertVector) == null) {
                    grass.put(desertVector, new Grass(desertVector));
                    newGrassUpdateCounter();
                    break;
                }
                desertPossibilities -= 1;
            }
        }

    }

    private LinkedList<Animal> nRandomAnimals(LinkedList<Animal> animalsList, int n) {
        while (animalsList.size() > n) {
            animalsList.remove(Math.random() * animalsList.size());
        }
        return animalsList;
    }

    // single reproduction complexity +/- O(x*y*n)
    private void reproduction() {
        // maximal one reproduction on a Coordinate
        LinkedList<Animal> animalsToBorn = new LinkedList();
        for (Vector2d position : animals.keySet()) {
            LinkedList<Animal> animalsList = reproductionAnimals(position);
            if (animalsList.size() >= 2) {
                nRandomAnimals(animalsList, 2);
                // more than one animal with top energy -> taking randomly two from the size
                if (animalsList.get(1).energy >= minReproductionEnergy && animalsList.get(0).energy >= minReproductionEnergy) {
                    Animal newAnimal = animalsList.get(0).reproduction(animalsList.get(1), ageCounter);
                    animalsToBorn.add(newAnimal);
                }
            }
        }
        for (Animal animal : animalsToBorn) {
            addAnimal(animal.position, animal);
            newAnimalUpdateCounter();
            newAnimalDominant(animal);
        }
    }

    public void magicReproduction() {
        LinkedList<Animal> animalsToBorn = new LinkedList<>();
        for (Vector2d position : animals.keySet()) {
            for (Animal animal : animals.get(position)) {
                // animal on new unoccupied by another animal place
                int newAnimalPossibilities = (upperRight.getX() - lowerLeft.getX() + 1) * (upperRight.getX() - lowerLeft.getX() + 1) - animalsCounter;
                while (newAnimalPossibilities > 0) {
                    int x = (int) (Math.random() * (upperRight.getX() - lowerLeft.getX() + 1)) + lowerLeft.getX();
                    int y = (int) (Math.random() * (upperRight.getY() - lowerLeft.getY() + 1)) + lowerLeft.getY();
                    Vector2d newAnimalPosition = new Vector2d(x, y);
                    if (!isOccupiedByAnimal(newAnimalPosition)) {
                        Animal newAnimal = animal.magicReproduction(newAnimalPosition, ageCounter);
                        animalsToBorn.add(newAnimal);
                        break;
                    }
                    newAnimalPossibilities -= 1;
                }
            }
        }
        for (Animal animal : animalsToBorn) {
            addAnimal(animal.position, animal);
            newAnimalUpdateCounter();
            newAnimalDominant(animal);
        }
        magicReproductionLeft -= 1;
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


    @Override
    public boolean place(Animal animal) {
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public IMapElement objectAt(Vector2d position) {
        if (isOccupiedByAnimal(position)) {
            return animals.get(position).get(0);
        }
        return grass.get(position);
    }


    public boolean moveTo(Vector2d position, Animal animal) {
        if (boundedMap) {
//            new position still on map
            if (position.precedes(upperRight) && position.follows(lowerLeft)) {
                animal.newPosition = position;
                return true;
            } else {
                return false;
            }
        }
        // not bounded map
        else {
            if (position.precedes(upperRight) && position.follows(lowerLeft)) {
                animal.newPosition = position;
                return true;
            }
            int newX;
            int newY;
            // X: right -> left
            if (position.getX() > upperRight.getX()) {
                newX = position.getX() % (upperRight.getX());
            }
            // X: left -> right
            else {
                newX = position.getX() + upperRight.getX();
            }
            // Y: top -> bottom
            if (position.getY() > upperRight.getY()) {
                newY = position.getY() % (upperRight.getY());
            }
            // Y: bottom -> top
            else {
                newY = position.getY() + upperRight.getY();
            }
            animal.newPosition = new Vector2d(newX, newY);
            return true;
        }
        // converting move to be still on map
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
            eatenGrassUpdateCounter();
        }
    }


    public void removeDeadAnimals() {
        LinkedList<Animal> deadAnimals = new LinkedList<>();
        for (Vector2d position : animals.keySet()) {
            for (Animal animal : animals.get(position)) {
                // new age energy cost
                animal.updateEnergy((-1) * ageCost);
                // if animal is dead after position changed -> removing him from map
                if (animal.isDead()) {
                    deadAnimals.add(animal);
                }
            }
        }
        for (Animal animal : deadAnimals) {
            Vector2d position = animal.position;
            removeAnimal(position, animal);
            deadAnimalUpdateCounter();
            removeDeadAnimalDominant(animal);
            animal.setDeathDate(ageCounter);
            deadAnimalsList.add(animal);
        }
    }

    public void moveAllAnimals() {
        LinkedList<Animal> movedAnimals = new LinkedList<>();
        for (Vector2d position : animals.keySet()) {
            for (Animal animal : animals.get(position)) {
                // condition: changed direction 0deg. or 180deg.
                if (animal.rotateAndMove()) {
                    Vector2d newPosition = position.add(animal.direction.toUnitVector());
                    if (moveTo(newPosition, animal)) {
                        movedAnimals.add(animal);
                    }
                }
            }
        }

        // changing position of animals on hashmap
        for (Animal animal : movedAnimals) {
            Vector2d position = new Vector2d(animal.position.getX(), animal.position.getY());
            removeAnimal(position, animal);
            animal.position = new Vector2d(animal.newPosition.getX(), animal.newPosition.getY());
            addAnimal(animal.position, animal);
        }
    }

    private void newAnimalDominant(Animal animal) {
        for (int i = 0; i < animal.genes.getGenotypeDominant().size(); i++) {
            dominantsNumber[animal.genes.getGenotypeDominant().get(i)] += 1;
        }
    }

    private void removeDeadAnimalDominant(Animal animal) {
        for (int i = 0; i < animal.genes.getGenotypeDominant().size(); i++) {
            dominantsNumber[animal.genes.getGenotypeDominant().get(i)] -= 1;
        }
    }


    private void calculateDominatingGenotype() {
        int tmpMax = 0;
        for (int n : dominantsNumber) {
            tmpMax = Math.max(tmpMax, n);
        }
        // can be more than one dominating Genotype -> saving it As string
        String result = "";
        for (int i = 0; i < dominantsNumber.length; i++) {
            if (dominantsNumber[i] == tmpMax) {
                result += i + " ";
            }
        }
        if (tmpMax > 0) {
            dominant = result;
        } else {
            dominant = "no animals left";
        }
    }


    private void calculateAVGEnergyOfAliveAnimals() {
        int energy = 0;
        for (Vector2d position : animals.keySet()) {
            for (Animal animal : animals.get(position)) {
                energy += animal.energy;
            }
        }
        if (animalsCounter > 0) {
            AVGEnergyOfAliveAnimals = energy / animalsCounter;
        } else {
            AVGEnergyOfAliveAnimals = 0;
        }
    }

    private void calculateAVGLengthOfLiveAnimals() {
        if (deadAnimalsList.size() == 0) {
            AVGLengthOfLifeAnimals = 0;
        } else {
            int liveLength = 0;
            for (Animal animal : deadAnimalsList) {
                liveLength += animal.liveLength();
            }
            AVGLengthOfLifeAnimals = liveLength / deadAnimalsList.size();
        }
    }

    private void calculateAVGNumberOfChildrenAnimals() {
        int children = 0;
        for (Vector2d position : animals.keySet()) {
            for (Animal animal : animals.get(position)) {
                children += animal.numberOfChildren;
            }
        }
        if (animalsCounter > 0) {
            AVGNumberOfChildrenAnimals = children / animalsCounter;
        } else {
            AVGNumberOfChildrenAnimals = 0;
        }
    }


    // at least one animal on this position;
    public boolean isOccupiedByAnimal(Vector2d position) {
        return animals.get(position) != null && animals.get(position).size() > 0;
    }

    public Animal animalAt(Vector2d position) {
        if (isOccupiedByAnimal(position)) {
            return animals.get(position).get(0);
        } else {
            return null;
        }
    }

    private void newGrassUpdateCounter() {
        this.grassCounter += 1;
    }

    private void eatenGrassUpdateCounter() {
        this.grassCounter -= 1;
    }

    private void newAnimalUpdateCounter() {
        this.animalsCounter += 1;
    }

    private void deadAnimalUpdateCounter() {
        this.animalsCounter -= 1;
    }

    private void updateAgeCounter() {
        this.ageCounter += 1;
    }

}
