package agh.ics.oop.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import agh.ics.oop.MapDirection;
import agh.ics.oop.Vector2d;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class GuiElementBox {
    private Image image;
    private ImageView imageView;
    private Label positionLabel = new Label();
    private VBox vBox = new VBox();

    // Grass object
    public GuiElementBox(Object type, Vector2d position) throws FileNotFoundException {
        if (type.getClass().getName().equals("agh.ics.oop.Grass")) {
            this.image = new Image(new FileInputStream("src/main/resources/grass.png"));
            this.positionLabel.setText("G " + position.toString());
            vBoxProperties();
        }
        else {
            throw new IllegalArgumentException(type + " is not Grass type");
        }

    }

    // Animal object
    public GuiElementBox(Object type, Vector2d position, MapDirection direction) throws FileNotFoundException {
        if (type.getClass().getName().equals("agh.ics.oop.Animal")) {
            // direction
            this.image = switch(direction) {
                case NORTH -> new Image(new FileInputStream("src/main/resources/up.png"));
                case SOUTH -> new Image(new FileInputStream("src/main/resources/down.png"));
                case WEST -> new Image(new FileInputStream("src/main/resources/left.png"));
                case EAST -> new Image(new FileInputStream("src/main/resources/right.png"));
                default -> throw new IllegalArgumentException(direction + " is not legal direction");
            };
            this.positionLabel.setText("A " + position.toString());
            vBoxProperties();
        }
        else {
            throw new IllegalArgumentException(type + " is not Animal type");
        }

    }

    public VBox getVBox() {
        return this.vBox;
    }

    private void vBoxProperties() {
        this.imageView = new ImageView(this.image);
        this.imageView.setFitWidth(20);
        this.imageView.setFitHeight(20);
        vBox.setAlignment(Pos.CENTER);
        this.vBox.getChildren().addAll(this.imageView, this.positionLabel);
    }
}
