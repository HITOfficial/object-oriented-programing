package agh.ics.oop.gui;

import agh.ics.oop.Vector2d;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.awt.*;

public interface IMapElement {
    VBox draw(Object type);
    Paint getColor();
}
