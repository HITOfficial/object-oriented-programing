public class World {
    public static void main(String[] args) {
        System.out.println("start");
        Direction[] moves = new Direction[args.length];
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "f":
                    moves[i] = Direction.FORWARD;
                    break;
                case "b":
                    moves[i] = Direction.BACKWARD;
                    break;
                case "l":
                    moves[i] = Direction.LEFT;
                    break;
                case "r":
                    moves[i] = Direction.RIGHT;
                    break;
                default:
                    throw new IllegalStateException("bad arg");
            }
        }
        run(moves);
        System.out.println("stop");
    }

    public enum Direction {
        FORWARD,
        BACKWARD,
        RIGHT,
        LEFT
    }

    public static void run(Direction[] moves) {
        for (Direction move : moves) {
            System.out.println(move);
        }
    }
}