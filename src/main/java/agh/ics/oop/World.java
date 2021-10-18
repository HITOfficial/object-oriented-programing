package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        System.out.println("start");
        Direction[] moves = new Direction[args.length];
        for (int i = 0; i < args.length; i++) {
            moves[i] = switch (args[i]){
                case "f" -> Direction.FORWARD;
                case "b" -> Direction.BACKWARD;
                case "l" -> Direction.LEFT;
                case "r" -> Direction.RIGHT;
                default -> throw new IllegalStateException("bad arg");
            };
        }
        run(moves);
        System.out.println("stop");
    }

    public static void run(Direction[] moves) {
        for (Direction move : moves) {
            System.out.println(move);
        }
    }
}
