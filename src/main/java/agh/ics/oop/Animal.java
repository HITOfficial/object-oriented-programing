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

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
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
        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> {
                Vector2d newPosition = this.position.add(this.direction.toUnitVector());
                if (newPosition.precedes(new Vector2d(4,4)) && newPosition.follows(new Vector2d(0,0))){
                    this.position = newPosition;
                }
            }
            case BACKWARD -> {
                Vector2d newPosition = this.position.subtract(this.direction.toUnitVector());
                if (newPosition.precedes(new Vector2d(4, 4)) && newPosition.follows(new Vector2d(0, 0))) {
                    this.position = newPosition;
                }
            }
        }
    }
}
