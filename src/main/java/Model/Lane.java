package Model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

    public class Lane {

        private final int laneNumber;
        private final String direction;

        public Lane(int laneNumber, String direction) {
            this.laneNumber = laneNumber;
            this.direction = direction;
        }

        public void draw(Pane root) {

            double y;

            if (direction.equals("RIGHT")) {

                // Three lanes travelling right
                y = 125 + (laneNumber * 80);

            } else {

                // Three lanes travelling left
                int leftLaneNumber = laneNumber - 3;

                y = 475 + (leftLaneNumber * 80);
            }

            Line laneLine = new Line(
                    0,
                    y,
                    1200,
                    y
            );

            laneLine.setStroke(Color.WHITE);
            laneLine.setStrokeWidth(2);

            root.getChildren().add(laneLine);
        }

        public int getLaneNumber() {
            return laneNumber;
        }

        public String getDirection() {
            return direction;
        }
    }
