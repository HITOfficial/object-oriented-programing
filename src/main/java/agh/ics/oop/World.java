package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        String[] moves1 = {"anotherText", "BackWard", "red", " ", "yellow", "b", "backward", "b", "backwarddd","lewt","left","ll","right","Right","rr","f","f","f"};
        Animal animal1 = new Animal();
        System.out.println(animal1);
        MoveDirection[] directions = {MoveDirection.RIGHT, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD};
        for(MoveDirection direction : OptionsParser.parse(moves1)) {
            animal1.move(direction);
        }

        System.out.println(animal1);

    }

}
