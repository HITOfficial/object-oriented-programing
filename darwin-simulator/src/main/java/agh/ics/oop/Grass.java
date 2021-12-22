package agh.ics.oop;


import agh.ics.oop.gui.IMapElement;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class Grass implements IMapElement {
    private Vector2d position;
    private final int energy;

    public Grass() {
        position = new Vector2d(10,10);
        this.energy = (int) (Math.random() * 150) + 50;
    }
    public Grass(Vector2d position) {
        this();
        this.position = position;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public int getEnergy() {
        return this.energy;
    }


    @Override
    public VBox draw(Object type) {
        return null;
    }

    @Override
    public Paint getColor() {

        return Paint.valueOf("rgb(0,128,0)");
    }
}
