package agh.ics.oop;

public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2, 2);

    public String toString() {
        return "Direction: " + this.direction + " Position: " + this.position;
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT:
                this.direction = this.direction.next();
                break;
            case LEFT:
                this.direction = this.direction.previous();
                break;
            case FORWARD:
                switch (this.direction) {
                    case NORTH:
                        if (this.position.y < 4) {
                            this.position = position.add(MapDirection.NORTH.toUnitVector());
                        }
                        break;
                    case EAST:
                        if (this.position.x < 4) {
                            this.position = position.add(MapDirection.EAST.toUnitVector());
                        }
                        break;
                    case SOUTH:
                        if (this.position.y > 0) {
                            this.position = position.add(MapDirection.SOUTH.toUnitVector());
                        }
                        break;
                    case WEST:
                        if (this.position.x > 0) {
                            this.position = position.add(MapDirection.WEST.toUnitVector());
                        }
                        break;
                }
                break;
            case BACKWARD:
                switch (this.direction) {
                    case NORTH:
                        if (this.position.y > 0) {
                            this.position = position.subtract(MapDirection.NORTH.toUnitVector());
                        }
                        break;
                    case EAST:
                        if (this.position.x > 0) {
                            this.position = position.subtract(MapDirection.EAST.toUnitVector());
                        }
                        break;
                    case SOUTH:
                        if (this.position.y < 4) {
                            this.position = position.subtract(MapDirection.SOUTH.toUnitVector());
                        }
                        break;
                    case WEST:
                        if (this.position.x < 4) {
                            this.position = position.subtract(MapDirection.WEST.toUnitVector());
                        }
                        break;
                }
        }
    }
}
