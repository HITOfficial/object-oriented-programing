package agh.ics.oop;

public class Animal {
    private final IWorldMap map;
    private Vector2d position;
    private MapDirection direction = MapDirection.NORTH;

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.position = initialPosition;
        this.map= map;
    }

    public Animal(IWorldMap map){
        this(map, new Vector2d(2,2));
    }

    public Animal() {
        this(new RectangularMap(5,5));
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public MapDirection getDirection(){
        return this.direction;
    }

    public String toString() {
        return switch (this.direction){
            case NORTH -> "^";
            case EAST -> ">";
            case SOUTH -> "v";
            case WEST -> "<";
        };
    }

    public void move(MoveDirection direction) {
        Vector2d newPosition = position;
        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> newPosition = newPosition.add(this.direction.toUnitVector());
            case BACKWARD -> newPosition = newPosition.subtract(this.direction.toUnitVector());
        }
        if (this.map.canMoveTo(newPosition)){
            this.position = newPosition;
        }
    }
}
