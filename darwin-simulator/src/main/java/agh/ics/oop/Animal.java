package agh.ics.oop;
// czemu to jest schowane w jakimś podkatalogu
import agh.ics.oop.gui.IMapElement;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.util.Objects;

public class Animal implements IMapElement {
    // masa pól publicznych; żeby chociaż część była finalna
    public Genes genes;
    public MapDirection direction;
    public IWorldMap map;
    public int energy;
    public int startEnergy;
    public int bornDate;
    public int deathDate;
    public int numberOfChildren;
    protected Vector2d position;
    protected Vector2d newPosition;

    public Animal() {   // czy zwierzę może nie dostać mapy?
        numberOfChildren = 0;
        bornDate = 0;
        deathDate = -1;
        direction = MapDirection.NORTH;
        position = new Vector2d(2, 2);
        genes = new Genes();    // a gdzie energy, startEnergy?
    }

    public Animal(IWorldMap map) {
        this();
        this.map = map;
    }

    // animal on game start
    public Animal(IWorldMap map, Vector2d position) {
        this(map);
        this.position = position;
//        this.newPosition = position;
    }


    public Animal(IWorldMap map, Vector2d position, int startEnergy) {
        this(map, position);
        this.position = new Vector2d(position.getX(), position.getY());
        this.energy = startEnergy;
        this.startEnergy = startEnergy;
    }

    // animal from reproduction
    public Animal(IWorldMap map, Vector2d position, int startEnergy, int bornDate) {
        this(map, position, startEnergy);
        this.bornDate = bornDate;
    }

    // animal from magic reproduction
    public Animal(IWorldMap map, Vector2d position, int startEnergy, int bornDate, int[] genes) {
        this(map, position, startEnergy, bornDate);
        // copy of parent genes
        this.genes = new Genes(genes);
    }

    public boolean isDead() {
        return this.energy <= 0;
    }

    public void updateEnergy(int val) {
        this.energy += val;
    }

    public boolean rotateAndMove() {    // ta metoda nie rusza zwierzęcia
        int counter = 0;
        for (int i = 0; i <= genes.randomRotate(); i++) {
            this.direction = this.direction.next();
            counter += 1;
        }
        // can move animal
        return counter == 0 || counter == 4;
    }

    public Animal reproduction(Animal other, int bornDate) {
        int childEnergy = (int) (0.25 * this.energy) + (int) (0.25 * other.energy);
        this.updateEnergy((int) (-0.25 * this.energy));
        other.updateEnergy((int) ((-1) * 0.25 * other.energy));
        Animal childAnimal = new Animal(map, new Vector2d(other.position.getX(), other.position.getY()), childEnergy, bornDate);    // po co tworzyć nowy wektor, skoro jest niemodyfikowalny?
        childAnimal.genes = new Genes(this.genes.getGenes(), other.genes.getGenes(), this.energy, other.energy);
        this.numberOfChildren += 1;
        other.numberOfChildren += 1;
        return childAnimal;
    }

    public Animal magicReproduction(Vector2d position, int bornDate) {
        int childEnergy = this.startEnergy;
        this.updateEnergy((int) ((-1) * 0.25 * this.energy));
        this.numberOfChildren += 1;
        Animal childAnimal = new Animal(map, position, childEnergy, bornDate, this.genes.getGenes());
        return childAnimal;
    }


    public boolean equals(Object other) {
        if (this == other) {
            // same object
            return true;
        } else if (!(other instanceof Animal)) {
            // other is an object from another class
            return false;
        } else {
            Animal tmpAnimal = (Animal) other;
            // checking if objects has equal values
            return this.position == tmpAnimal.position && this.genes == tmpAnimal.genes && this.startEnergy == tmpAnimal.startEnergy;
        }
    }

    public void setDeathDate(int deathDate) {
        this.deathDate = deathDate;
    }

    public int liveLength() {
        return deathDate - bornDate;
    }


    @Override
    public VBox draw(Object type) {
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.position.getX(), this.position.getY(), this.genes);
    }

    public Paint getColor() {   // lepiej to przerzucić do GUI
        if (energy < 0.2 * startEnergy) return Paint.valueOf("rgb(255,248,220)");
        else if (energy < 0.5 * startEnergy) return Paint.valueOf("rgb(255,235,205)");
        else if (energy < 0.8 * startEnergy) return Paint.valueOf("rgb(255,228,196)");
        else if (energy < 1.5 * startEnergy) return Paint.valueOf("rgb(245,222,179)");
        else if (energy < 3 * startEnergy) return Paint.valueOf("rgb(210,180,140)");
        return Paint.valueOf("rgb(163,141,110)");
    }
}
