package agh.ics.oop.gui;

import agh.ics.oop.Vector2d;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.awt.*;

public interface IMapElement {
    VBox draw(GridPane grid, Vector2d position, Object type);
    Color getColor();
}
