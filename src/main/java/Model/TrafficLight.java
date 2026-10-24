package Model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class TrafficLight {

    public enum State { GREEN, YELLOW, RED }

    private State currentState;
    private double x, y;


    // Visual components
    private Circle redLight;
    private Circle yellowLight;
    private Circle greenLight;

    public TrafficLight(double x, double y, State initialState) {
        this.x = x;
        this.y = y;
        this.currentState = initialState;
    }

    public void draw(Pane root) {
        // Draw background housing box for the traffic light
        Rectangle housing = new Rectangle(x - 10, y - 25, 20, 50);
        housing.setFill(Color.DARKGRAY);
        housing.setStroke(Color.BLACK);

        redLight = new Circle(x, y - 15, 6, Color.GRAY);
        yellowLight = new Circle(x, y, 6, Color.GRAY);
        greenLight = new Circle(x, y + 15, 6, Color.GRAY);

        updateVisuals();
        root.getChildren().addAll(housing, redLight, yellowLight, greenLight);
    }

    // Allows central control from Main.java to keep opposite lights synchronized
    public void setState(State state) {
        this.currentState = state;
        updateVisuals();
    }



    private void updateVisuals() {
        if (redLight == null) return;
        redLight.setFill(currentState == State.RED ? Color.RED : Color.DARKRED);
        yellowLight.setFill(currentState == State.YELLOW ? Color.YELLOW : Color.DARKKHAKI);
        greenLight.setFill(currentState == State.GREEN ? Color.LIME : Color.DARKGREEN);
    }

    public State getCurrentState() {
        return currentState;
    }
}
