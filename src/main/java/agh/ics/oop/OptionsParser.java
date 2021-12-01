package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public static MoveDirection[] parse(String[] stringsArray) {
        // creating temporary list, to add only correctly directions, and after that converting this list to Java array
        List<MoveDirection> directionsList = new ArrayList<>();
        for (String singleString : stringsArray) {
            switch (singleString) {
                case "f", "forward":
                    directionsList.add(MoveDirection.FORWARD);
                    break;
                case "b", "backward":
                    directionsList.add(MoveDirection.BACKWARD);
                    break;
                case "l", "left":
                    directionsList.add(MoveDirection.LEFT);
                    break;
                case "r", "right":
                    directionsList.add(MoveDirection.RIGHT);
                    break;
                default:
                    throw new IllegalArgumentException(singleString + " is not legal move specification");
            }
        }

        MoveDirection[] directionsArray = new MoveDirection[directionsList.size()];
        // adding element from tmp list to array
        directionsList.toArray(directionsArray);
        return directionsArray;
    }
}
