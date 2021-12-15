package agh.ics.oop;


public class Grass {
    private Vector2d position;
    private int energy;

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


}
