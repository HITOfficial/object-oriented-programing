package agh.ics.oop;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnimalTest {
    public MoveDirection[] directions1 = OptionsParser.parse(new String[]{"f", "forward", "f", "f", "f", "r", "f", "f", "f", "f"});
    public MoveDirection[] directions2 = OptionsParser.parse(new String[]{"b", "backward", "b", "b", "b","l","f","f","f"});
    public MoveDirection[] directions3 = OptionsParser.parse(new String[]{"l", "left", "b", "b", "f", "f","b","left","left"});
    public MoveDirection[] directions4 = OptionsParser.parse(new String[]{"l","b","b","b","r", "right", "f", "f", "b", "f", "forward"});
    public MoveDirection[] directions5 = OptionsParser.parse(new String[]{"l", "left", "r", "right", "f", "forward", "b", "backward"});
    public MoveDirection[] directions6 = OptionsParser.parse(new String[]{"l", "left", "f", "forward", "r", "right", "backward", "b", "b", "b", "b", "b", "b", "l", "l", "b", "b", "b", "b", "b", "b"});
    public MoveDirection[] directions7 = OptionsParser.parse(new String[]{"f", "left", "f", "r", "right", "f", "forward", "l", "l", "b", "backward", "right"});
    public MoveDirection[] directions8 = OptionsParser.parse(new String[]{"b", "left", "b", "l", "b", "l", "b", "backward", "left", "b", "r", "b", "r", "b", "backward","right"});
    public MoveDirection[] directions9 = OptionsParser.parse(new String[]{"anotherText", "BackWard", "red", " ", "yellow", "b", "backward", "b", "backwarddd", "lewt", "left", "ll", "right", "Right", "rr", "f", "f", "f"});
    public MoveDirection[] directions10 = OptionsParser.parse(new String[]{"f", "abc", "fOrward", "b", "back ward", "fff", "right", "riGh t", "left", "backward","left"});

    @Test
    public void moveTest() {
        Animal animal1 = new Animal();
        updateAnimalMoves(animal1, this.directions1);
        assertEquals("Direction: Wschód Position: (4,4)", animal1.toString());
        Animal animal2 = new Animal();
        updateAnimalMoves(animal2, this.directions2);
        assertEquals("Direction: Zachód Position: (0,0)", animal2.toString());
        Animal animal3 = new Animal();
        updateAnimalMoves(animal3, this.directions3);
        assertEquals("Direction: Północ Position: (2,3)", animal3.toString());
        Animal animal4 = new Animal();
        updateAnimalMoves(animal4, this.directions4);
        assertEquals("Direction: Wschód Position: (4,2)", animal4.toString());
        Animal animal5 = new Animal();
        updateAnimalMoves(animal5, this.directions5);
        assertEquals("Direction: Północ Position: (2,2)", animal5.toString());
        Animal animal6 = new Animal();
        updateAnimalMoves(animal6, this.directions6);
        assertEquals("Direction: Południe Position: (2,4)", animal6.toString());
        Animal animal7 = new Animal();
        updateAnimalMoves(animal7, this.directions7);
        assertEquals("Direction: Północ Position: (4,3)", animal7.toString());
        Animal animal8 = new Animal();
        updateAnimalMoves(animal8, this.directions8);
        assertEquals("Direction: Zachód Position: (0,3)", animal8.toString());
        Animal animal9 = new Animal();
        updateAnimalMoves(animal9, this.directions9);
        assertEquals("Direction: Północ Position: (2,3)", animal9.toString());
        Animal animal10 = new Animal();
        updateAnimalMoves(animal10, this.directions10);
        assertEquals("Direction: Zachód Position: (2,1)", animal10.toString());


    }


    public void updateAnimalMoves(Animal animal, MoveDirection[] directions){
        for(MoveDirection direction : directions){
            animal.move(direction);
        }
    }
}
