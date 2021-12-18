package agh.ics.oop.gui;

import agh.ics.oop.Vector2d;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class MapElement {
    public static VBox draw(IMapElement type) {
        VBox vBox = new VBox();
        Circle circle = new Circle();
        circle.setCenterX(10.0f);
        circle.setCenterY(10.0f);
        circle.setRadius(5.0f);

        if (type == null) {
            circle.setFill(Color.TRANSPARENT);
        }
        else {
            circle.setFill(type.getColor());
        }
        vBox.getChildren().addAll(circle);
        return vBox;
    }
}
