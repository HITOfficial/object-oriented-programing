package agh.ics.oop;

import agh.ics.oop.gui.IMapElement;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.ArrayList;

public class Animal implements IMapElement {
    public Genes genes;
    public MapDirection direction;

    public IWorldMap map;
    public int energy;
    public int startEnergy;
    public ArrayList<IPositionChangeObserver> observerList = new ArrayList<>();
    protected Vector2d position;
    protected Vector2d newPosition;

    public Animal() {
        direction = MapDirection.NORTH;
        position = new Vector2d(2, 2);
        genes = new Genes();
    }

    public Animal(IWorldMap map) {
        this();
        this.map = map;
    }

    // animal on game start
    public Animal(IWorldMap map, Vector2d position) {
        this(map);
        this.position = position;
    }

    // animal from reproduction
    public Animal(IWorldMap map, Vector2d position, int startEnergy) {
        this(map, position);
        this.energy = startEnergy;
        this.startEnergy = startEnergy;
    }

    public boolean isDead() {
        return this.energy <= 0;
    }

    public void updateEnergy(int val) {
        this.energy += val;
    }

    public void rotate() {
        for (int i = 0; i <= genes.randomRotate(); i++) {
            // changing direction and trying to move;
            this.direction = this.direction.next();
        }
    }

    public void move() {
        Vector2d newPosition = position.add(direction.toUnitVector());

        if (map.changePosition(newPosition, this)) {
            for (IPositionChangeObserver observer : observerList) {
                observer.positionChanged();
            }
        }
    }

    public Animal reproduction(Animal other) {
        int childEnergy = (int) (0.25 * this.energy) + (int) (0.25 * other.energy);
        this.updateEnergy((int) ((-1) * 0.25 * this.energy));
        other.updateEnergy((int) ((-1) * 0.25 * other.energy));
        Animal childAnimal = new Animal(map, this.position, childEnergy);
        childAnimal.genes = new Genes(this.genes.getGenes(), other.genes.getGenes(), this.energy, other.energy);
        return other;
    }


    @Override
    public VBox draw(GridPane grid, Vector2d position, Object type) {
        return null;
    }

    public Color getColor() {
        if (energy < 0.2 * startEnergy) return new Color(255,248,220);
        else if (energy < 0.5 * startEnergy) return new Color(255,235,205);
        else if (energy < 0.8 * startEnergy) return new Color(255,228,196);
        else if (energy < 1.5 * startEnergy) return new Color(245,222,179);
        else if (energy < 3 * startEnergy) return new Color(210,180,140);
        return new Color(163, 141, 110);
    }
}
