package Model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class vehicle {

    private final String id;
    private final String type;
    private final String startingDirection;
    private final String movement;

    private double centerX;
    private double centerY;

    private double speed;
    private double originalSpeed; // Saved speed to restore after yellow light
    private double vehicleLength;
    private double vehicleWidth;

    private Color vehicleColor;
    private Rectangle shape;

    private final List<Point> route = new ArrayList<>();
    private int targetIndex = 0;

    /*
     * Constructor for the newer Main.java.
     *

     */

    // Traffic light reference for stopping logic
    private TrafficLight trafficLight;
    public vehicle(
            String id,
            String type,
            String direction,
            String movement
    ) {
        this.id = id;
        this.type = type.toUpperCase();
        this.startingDirection = direction.toUpperCase();
        this.movement = movement.toUpperCase();

        configureVehicle();
        createRoute();
        setStartingPosition();
    }

    /*
     * Constructor for the older Main.java.
     * but the vehicle's correct lane position is determined
     * by its direction and movement.
     */
    public vehicle(
            String id,
            String type,
            double x,
            double y,
            String direction,
            String movement
    ) {
        this.id = id;
        this.type = type.toUpperCase();
        this.startingDirection = direction.toUpperCase();
        this.movement = movement.toUpperCase();

        configureVehicle();
        createRoute();

        this.centerX = x;
        this.centerY = y;

        /*
         * If the supplied position is not useful, the route's
         * correct starting position will still be applied.
         */
        if (startingDirection.equals("RIGHT")
                || startingDirection.equals("LEFT")) {

            if (y == 0) {
                setStartingPosition();
            }

        } else {

            if (x == 0) {
                setStartingPosition();
            }
        }
    }
    public void setTrafficLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    private void configureVehicle() {

        switch (type) {

            case "CAR":
                vehicleLength = 42;
                vehicleWidth = 20;
                originalSpeed = 2.5;
                vehicleColor = Color.DODGERBLUE;
                break;
            case "CAR1":
                vehicleLength = 20;
                vehicleWidth = 42;
                originalSpeed = 2.5;
                vehicleColor = Color.DODGERBLUE;
                break;

            case "BUS":
                vehicleLength = 70;
                vehicleWidth = 26;
                originalSpeed = 1.5;
                vehicleColor = Color.ORANGE;
                break;
            case "BUS1":
                vehicleLength = 26;
                vehicleWidth = 70;
                originalSpeed = 1.5;
                vehicleColor = Color.ORANGE;
                break;

            case "BIKE":
                vehicleLength = 28;
                vehicleWidth = 14;
                originalSpeed = 3.0;
                vehicleColor = Color.LIMEGREEN;
                break;

            case "AMBULANCE":
                vehicleLength = 23;
                vehicleWidth = 58;
                originalSpeed = 2.2;
                vehicleColor = Color.RED;
                break;


            default:
                vehicleLength = 42;
                vehicleWidth = 20;
                originalSpeed = 2.5;
                vehicleColor = Color.GRAY;
                break;
        }
    }

    /*
     * Road geometry used by Road.java:
     *
     * Horizontal road: y = 250 to 550
     * Vertical road:   x = 450 to 750
     *
     * Horizontal lane centres:
     *
     * RIGHT direction:
     * y = 275, 325, 375
     *
     * LEFT direction:
     * y = 475, 525, 425
     *
     * Vertical lane centres:
     *
     * DOWN direction:
     * x = 625, 675, 725
     *
     * UP direction:
     * x = 475, 525, 575
     *
     * The route is made from straight sections only.
     * Therefore, vehicles never travel diagonally.
     */

    private void createRoute() {

        route.clear();

        switch (startingDirection) {

            case "RIGHT":
                createRightRoute();
                break;

            case "LEFT":
                createLeftRoute();
                break;

            case "UP":
                createUpRoute();
                break;

            case "DOWN":
                createDownRoute();
                break;

            default:
                createRightRoute();
                break;
        }
    }

    /*
     * Vehicle enters from the LEFT and moves RIGHT.
     *
     * LEFT turn:
     *   travels right, then turns upward.
     *
     * STRAIGHT:
     *   travels directly to the right.
     *
     * RIGHT turn:
     *   travels right, then turns downward.
     */

    private void createRightRoute() {

        if (movement.equals("LEFT")) {

            /*
             * Left-turn lane.
             * Enter horizontally, reach the upward exit lane,
             * then travel upward.
             */
            route.add(new Point(-180, 275));
            route.add(new Point(475, 275));
            route.add(new Point(475, -180));

        } else if (movement.equals("RIGHT")) {

            /*
             * Right-turn lane.
             * Enter horizontally, reach the downward exit lane,
             * then travel downward.
             */
            route.add(new Point(-180, 375));
            route.add(new Point(625, 375));
            route.add(new Point(625, 980));

        } else {

            /*
             * Straight lane.
             */
            route.add(new Point(-180, 325));
            route.add(new Point(1380, 325));
        }
    }

    /*
     * Vehicle enters from the RIGHT and moves LEFT.
     *
     * LEFT turn:
     *   travels left, then turns downward.
     *
     * STRAIGHT:
     *   travels directly to the left.
     *
     * RIGHT turn:
     *   travels left, then turns upward.
     */

    private void createLeftRoute() {
        if (movement.equals("LEFT")) {
            // Left lane (y = 525) turns LEFT (downward into vertical lane x = 675)
            route.add(new Point(1380, 525));
            route.add(new Point(675, 525));
            route.add(new Point(675, 980));
        } else if (movement.equals("RIGHT")) {
            // Right lane (y = 425) turns RIGHT (upward into vertical lane x = 575)
            route.add(new Point(1380, 425));
            route.add(new Point(575, 425));
            route.add(new Point(575, -180));
        } else {
            // Straight (Middle lane y = 475)
            route.add(new Point(1380, 475));
            route.add(new Point(-180, 475));
        }
    }

    private void createDownRoute() {
        if (movement.equals("LEFT")) {
            // Left lane (x = 625) turns LEFT (west into horizontal lane y = 525)
            route.add(new Point(625, -180));
            route.add(new Point(625, 525));
            route.add(new Point(-180, 525));
        } else if (movement.equals("RIGHT")) {
            // Right lane (x = 725) turns RIGHT (east into horizontal lane y = 375)
            route.add(new Point(725, -180));
            route.add(new Point(725, 375));
            route.add(new Point(1380, 375));
        } else {
            // Straight (Middle lane x = 675)
            route.add(new Point(675, -180));
            route.add(new Point(675, 980));
        }
    }

    private void createUpRoute() {
        if (movement.equals("LEFT")) {
            // Left lane (x = 575) turns LEFT (east into horizontal lane y = 375)
            route.add(new Point(475, 980));
            route.add(new Point(475, 475));
            route.add(new Point(-180, 375));
        } else if (movement.equals("RIGHT")) {
            // Right lane (x = 475) turns RIGHT (west into horizontal lane y = 275)
            route.add(new Point(575, 980));
            route.add(new Point(575, 375));
            route.add(new Point(1380, 275));
        } else {
            // Straight (Middle lane x = 525)
            route.add(new Point(525, 980));
            route.add(new Point(525, -180));
        }
    }
    /*
     * Set the correct starting position for each lane.
     */

    private void setStartingPosition() {

        targetIndex = 0;

        switch (startingDirection) {

            case "RIGHT":

                centerX = -180;

                if (movement.equals("LEFT")) {
                    centerY = 275;
                } else if (movement.equals("RIGHT")) {
                    centerY = 375;
                } else {
                    centerY = 325;
                }

                break;

            case "LEFT":

                centerX = 1380;

                if (movement.equals("LEFT")) {
                    centerY = 525;
                } else {
                    centerY = 475;
                }

                break;

            case "UP":

                centerY = 980;

                if (movement.equals("LEFT")) {
                    centerX = 475;
                } else if (movement.equals("RIGHT")) {
                    centerX = 575;
                } else {
                    centerX = 525;
                }

                break;

            case "DOWN":

                centerY = -180;

                if (movement.equals("LEFT")) {
                    centerX = 725;
                } else if (movement.equals("RIGHT")) {
                    centerX = 625;
                } else {
                    centerX = 675;
                }

                break;
        }

        updateShape();
    }

    public void draw(Pane root) {

        /*
         * Initially, vehicles entering horizontally are horizontal.
         * Vehicles entering vertically are vertical.
         */

        boolean initiallyVertical =
                startingDirection.equals("UP")
                        || startingDirection.equals("DOWN");

        double drawWidth =
                initiallyVertical ? vehicleWidth : vehicleLength;

        double drawHeight =
                initiallyVertical ? vehicleLength : vehicleWidth;

        shape = new Rectangle(
                centerX - drawWidth / 2,
                centerY - drawHeight / 2,
                drawWidth,
                drawHeight
        );

        shape.setFill(vehicleColor);
        shape.setStroke(Color.BLACK);
        shape.setStrokeWidth(1);

        root.getChildren().add(shape);
    }

    public void update(List<vehicle> allVehicles) {

        if (shape == null || route.isEmpty()) {
            return;
        }




        // --- DETERMINE BASE SPEED (Traffic Light Check) ---
        double targetSpeed = originalSpeed;

        if (trafficLight != null && isApproachingStopLine()) {
            TrafficLight.State state = trafficLight.getCurrentState();

            if (state == TrafficLight.State.RED) {
                targetSpeed = 0;
            } else if (state == TrafficLight.State.YELLOW) {
                targetSpeed = originalSpeed * 0.6;
            }
        }

        // --- CAR-FOLLOWING LOGIC (Avoid Overlapping) ---
        vehicle vehicleAhead = getVehicleAhead(allVehicles) ;
        if (vehicleAhead != null) {
            // If a vehicle is ahead, match its speed or slow down
            double distance = calculateDistanceTo(vehicleAhead);
            if (distance < 80) { // Safe following distance threshold
                targetSpeed = Math.min(targetSpeed, vehicleAhead.speed);
                if (distance < 70) {
                    targetSpeed = 0; // Stop completely if too close
                }
            }
        }

        speed = targetSpeed;

        // When the vehicle completes its route, reset it to the entrance
        if (targetIndex >= route.size()) {
            setStartingPosition();
            return;
        }

        Point target = route.get(targetIndex);

        double differenceX = target.x - centerX;
        double differenceY = target.y - centerY;

        // First move horizontally until target X is reached
        if (Math.abs(differenceX) > 1) {
            if (Math.abs(differenceX) <= speed) {
                centerX = target.x;
            } else {
                centerX += Math.signum(differenceX) * speed;
            }

            if (centerX < target.x) {
                shape.setRotate(0);       // moving RIGHT
            } else {
                shape.setRotate(180);     // moving LEFT
            }
        }
        // Then move vertically until target Y is reached
        else if (Math.abs(differenceY) > 1) {
            centerX = target.x;

            if (Math.abs(differenceY) <= speed) {
                centerY = target.y;
            } else {
                centerY += (Math.signum(differenceY) * speed);
            }

            if (centerY < target.y) {
                shape.setRotate(90);      // moving DOWN
            } else {
                shape.setRotate(270);     // moving UP
            }
        }
        // Target reached: move to the next waypoint
        else {
            centerX = target.x;
            centerY = target.y;
            targetIndex++;
        }

        updateShape();
    }


    private vehicle getVehicleAhead(List<vehicle> allVehicles) {
        vehicle nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (vehicle other : allVehicles) {
            if (other == this) continue;

            double dist = calculateDistanceTo(other);

            // Check if vehicles are sharing the same lane path right now
            boolean sameLane = false;

            // If both are moving horizontally (similar Y coordinates)
            if (Math.abs(this.centerY - other.centerY) < 15) {
                // Check if they are facing the same horizontal direction
                if ((this.shape.getRotate() == 0 && other.shape.getRotate() == 0) ||
                        (this.shape.getRotate() == 180 && other.shape.getRotate() == 180)) {
                    sameLane = true;
                }
            }
            // If both are moving vertically (similar X coordinates)
            else if (Math.abs(this.centerX - other.centerX) < 15) {
                // Check if they are facing the same vertical direction
                if ((this.shape.getRotate() == 90 && other.shape.getRotate() == 90) ||
                        (this.shape.getRotate() == 270 && other.shape.getRotate() == 270)) {
                    sameLane = true;
                }
            }
            // Fallback to original starting direction check if they haven't turned yet
            else if (other.startingDirection.equals(this.startingDirection)) {
                if ((startingDirection.equals("UP") || startingDirection.equals("DOWN"))
                        && Math.abs(other.centerX - this.centerX) <= 15) {
                    sameLane = true;
                } else if ((startingDirection.equals("LEFT") || startingDirection.equals("RIGHT"))
                        && Math.abs(other.centerY - this.centerY) <= 15) {
                    sameLane = true;
                }
            }

            if (sameLane) {
                if (dist == 0.0) {
                    if (this.id.compareTo(other.id) > 0) {
                        minDistance = 0;
                        nearest = other;
                    }
                }
                else if (isAheadUniversal(other) && dist < minDistance) {
                    minDistance = dist;
                    nearest = other;
                }
            }
        }
        return nearest;
    }

    // Universal helper that checks if 'other' is ahead based on current rotation instead of startingDirection
    private boolean isAheadUniversal(vehicle other) {
        double rot = this.shape.getRotate();
        if (rot == 0)   return other.centerX > this.centerX; // Moving Right
        if (rot == 180) return other.centerX < this.centerX; // Moving Left
        if (rot == 90)  return other.centerY > this.centerY; // Moving Down
        if (rot == 270) return other.centerY < this.centerY; // Moving Up
        return isAhead(other); // Fallback
    }

    private boolean isAhead(vehicle other) {
        switch (startingDirection) {
            case "RIGHT": return other.centerX > this.centerX;
            case "LEFT":  return other.centerX < this.centerX;
            case "DOWN":  return other.centerY > this.centerY;
            case "UP":    return other.centerY < this.centerY;
        }
        return false;
    }

    private double calculateDistanceTo(vehicle other) {
        return Math.sqrt(Math.pow(other.centerX - this.centerX, 2) + Math.pow(other.centerY - this.centerY, 2));
    }

    private boolean isApproachingStopLine() {
        switch (startingDirection) {
            case "RIGHT":
                // Stop line is at X = 450. Trigger closer (e.g., between 430 and 450)
                return centerX >= 400 && centerX < 430;

            case "LEFT":
                // Stop line is at X = 750. Trigger closer (e.g., between 750 and 770)
                return centerX <= 800 && centerX > 770;

            case "UP":
                // Stop line is at Y = 550. Trigger closer (e.g., between 550 and 570)
                return centerY <= 580 && centerY > 550;

            case "DOWN":
                // Stop line is at Y = 250. Trigger closer (e.g., between 230 and 250)
                return centerY >= 220 && centerY < 250;
        }

        return false;
    }





    public void resetIfOutOfBounds() {

        boolean outsideLeft = centerX < -250;
        boolean outsideRight = centerX > 1450;
        boolean outsideTop = centerY < -250;
        boolean outsideBottom = centerY > 1050;

        if (outsideLeft
                || outsideRight
                || outsideTop
                || outsideBottom) {

            setStartingPosition();
        }
    }

    private void updateShape() {

        if (shape == null) {
            return;
        }

        boolean initiallyVertical =
                startingDirection.equals("UP")
                        || startingDirection.equals("DOWN");

        double drawWidth =
                initiallyVertical ? vehicleWidth : vehicleLength;

        double drawHeight =
                initiallyVertical ? vehicleLength : vehicleWidth;

        shape.setX(centerX - drawWidth / 2);
        shape.setY(centerY - drawHeight / 2);
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getStartingDirection() {
        return startingDirection;
    }

    public String getMovement() {
        return movement;
    }

    /*
     * Small internal point class for route waypoints.
     */

    private static class Point {

        private final double x;
        private final double y;

        private Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}