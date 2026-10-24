package Model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Road {

    private static final double WIDTH = 1200;
    private static final double HEIGHT = 800;

    // Six lanes × 50 pixels = 300 pixels
    private static final double ROAD_SIZE = 300;
    private static final double LANE_SIZE = 50;

    // Position of the crossing
    private static final double HORIZONTAL_TOP = 250;
    private static final double VERTICAL_LEFT = 450;

    private static final double HORIZONTAL_BOTTOM =
            HORIZONTAL_TOP + ROAD_SIZE;

    private static final double VERTICAL_RIGHT =
            VERTICAL_LEFT + ROAD_SIZE;

    public void draw(Pane root) {

        // Background
        Rectangle background = new Rectangle(
                0,
                0,
                WIDTH,
                HEIGHT
        );
        background.setFill(Color.WHITE);

        // Horizontal road
        Rectangle horizontalRoad = new Rectangle(
                0,
                HORIZONTAL_TOP,
                WIDTH,
                ROAD_SIZE
        );
        horizontalRoad.setFill(Color.rgb(55, 55, 55));

        // Vertical road
        Rectangle verticalRoad = new Rectangle(
                VERTICAL_LEFT,
                0,
                ROAD_SIZE,
                HEIGHT
        );
        verticalRoad.setFill(Color.rgb(55, 55, 55));

        root.getChildren().addAll(
                background,
                horizontalRoad,
                verticalRoad
        );

        drawHorizontalLaneLines(root);
        drawVerticalLaneLines(root);
        drawCentreDividers(root);
        drawStopLines(root);
    }

    private void drawHorizontalLaneLines(Pane root) {

        // Horizontal road has 6 lanes:
        // 3 lanes in one direction and 3 lanes in the opposite direction.

        double[] laneLines = {
                300,
                350,
                450,
                500
        };

        for (double y : laneLines) {

            // Left side of the junction
            drawDashedLine(
                    root,
                    0,
                    y,
                    VERTICAL_LEFT,
                    y
            );

            // Right side of the junction
            drawDashedLine(
                    root,
                    VERTICAL_RIGHT,
                    y,
                    WIDTH,
                    y
            );
        }
    }

    private void drawVerticalLaneLines(Pane root) {

        double[] laneLines = {
                500,
                550,
                650,
                700
        };

        for (double x : laneLines) {

            // Above the junction
            drawDashedLine(
                    root,
                    x,
                    0,
                    x,
                    HORIZONTAL_TOP
            );

            // Below the junction
            drawDashedLine(
                    root,
                    x,
                    HORIZONTAL_BOTTOM,
                    x,
                    HEIGHT
            );
        }
    }

    private void drawCentreDividers(Pane root) {

        // Horizontal centre divider
        drawSolidLine(
                root,
                0,
                400,
                VERTICAL_LEFT,
                400,
                Color.YELLOW,
                3
        );

        drawSolidLine(
                root,
                VERTICAL_RIGHT,
                400,
                WIDTH,
                400,
                Color.YELLOW,
                3
        );

        // Vertical centre divider
        drawSolidLine(
                root,
                600,
                0,
                600,
                HORIZONTAL_TOP,
                Color.YELLOW,
                3
        );

        drawSolidLine(
                root,
                600,
                HORIZONTAL_BOTTOM,
                600,
                HEIGHT,
                Color.YELLOW,
                3
        );
    }

    private void drawStopLines(Pane root) {

        // Left stop line
        drawSolidLine(
                root,
                450,
                HORIZONTAL_TOP,
                450,
                HORIZONTAL_BOTTOM,
                Color.rgb(55, 55, 55),
                5
        );

        // Right stop line
        drawSolidLine(
                root,
                750,
                HORIZONTAL_TOP,
                750,
                HORIZONTAL_BOTTOM,
                Color.rgb(55, 55, 55),
                5
        );

        // Top stop line
        drawSolidLine(
                root,
                VERTICAL_LEFT,
                250,
                VERTICAL_RIGHT,
                250,
                Color.rgb(55, 55, 55),
                5
        );

        // Bottom stop line
        drawSolidLine(
                root,
                VERTICAL_LEFT,
                550,
                VERTICAL_RIGHT,
                550,
                Color.rgb(55, 55, 55),
                5
        );
    }

    private void drawDashedLine(
            Pane root,
            double startX,
            double startY,
            double endX,
            double endY
    ) {
        Line line = new Line(
                startX,
                startY,
                endX,
                endY
        );

        line.setStroke(Color.WHITE);
        line.setStrokeWidth(2);
        line.getStrokeDashArray().addAll(15.0, 15.0);

        root.getChildren().add(line);
    }

    private void drawSolidLine(
            Pane root,
            double startX,
            double startY,
            double endX,
            double endY,
            Color color,
            double strokeWidth
    ) {
        Line line = new Line(
                startX,
                startY,
                endX,
                endY
        );

        line.setStroke(color);
        line.setStrokeWidth(strokeWidth);

        root.getChildren().add(line);
    }
}