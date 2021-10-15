public class World {
    public static void main(String[] args) {
        System.out.println("start");
        run(args);
        System.out.println("stop");
    }
    public static void run(String[] args){
        for(String arg : args){
            switch (arg) {
                case "f":
                    System.out.println("FORWARD");
                    break;
                case "b":
                    System.out.println("BACKWARD");
                break;
                case "l":
                    System.out.println("RIGHT");
                break;
                case "r":
                    System.out.println("LEFT");
                break;
                default:
                    break;
            }
        }
    }
}