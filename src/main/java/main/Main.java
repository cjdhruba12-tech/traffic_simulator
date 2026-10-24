import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import Model.Road;
import Model.vehicle;
import Model.TrafficLight;

import java.util.List;

public class Main extends Application {
    private int lightTimer = 0;
    private int lightPhase = 0; // 0 = N/S Green / E/W Red, 1 = N/S Yellow, 2 = N/S Red / E/W Green, 3 = E/W Yellow

    @Override
    public void start(Stage stage) {

        Pane root = new Pane();

        Road road = new Road();
        road.draw(root);

        // --- Traffic Lights Position matched with Road.java Stop Lines ---
        // Road.java coordinates: Left=450, Right=750, Top=250, Bottom=550
        TrafficLight eastLight = new TrafficLight(780, 590, TrafficLight.State.GREEN); //   right road light
         TrafficLight westLight = new TrafficLight(410, 210, TrafficLight.State.GREEN); // left road  light
        TrafficLight northLight  = new TrafficLight(780, 210, TrafficLight.State.RED);   // top road light
        TrafficLight southLight  = new TrafficLight(410, 590, TrafficLight.State.RED);   // bottom road light

        northLight.draw(root);
        southLight.draw(root);
        eastLight.draw(root);
        westLight.draw(root);

        /*
         * Lane rules mapping:
         * - LEFT → RIGHT (West to East)
         * - RIGHT → LEFT (East to West)
         * - TOP → BOTTOM (North to South)
         * - BOTTOM → TOP (South to North)
         */

        vehicle car1 = new vehicle("Car 1", "CAR", "RIGHT", "LEFT");
        vehicle car2 = new vehicle("Car 2", "CAR", "RIGHT", "STRAIGHT");
        vehicle bus1 = new vehicle("Bus 1", "BUS", "RIGHT", "RIGHT");

        vehicle car3 = new vehicle("Car 3", "CAR", "LEFT", "RIGHT");
        vehicle car4 = new vehicle("Car 4", "CAR", "LEFT", "STRAIGHT");
        vehicle bus2 = new vehicle("Bus 2", "BUS", "LEFT", "LEFT");

        vehicle ambulance1 = new vehicle("Ambulance 1", "AMBULANCE", "DOWN", "LEFT");
        vehicle ambulance2 = new vehicle("Ambulance 2", "AMBULANCE", "UP", "RIGHT");

        vehicle car5 = new vehicle("Car 5", "CAR1", "DOWN", "STRAIGHT");
        vehicle car6 = new vehicle("Car 6", "CAR1", "DOWN", "RIGHT");
        vehicle car7 = new vehicle("Car 7", "CAR1", "UP", "LEFT");
        vehicle car8 = new vehicle("Car 8", "CAR1", "UP", "STRAIGHT");

        vehicle bus3 = new vehicle("Bus 3", "BUS", "RIGHT", "STRAIGHT");
        vehicle bus4 = new vehicle("Bus 4", "BUS", "LEFT", "STRAIGHT");
        vehicle bus5 = new vehicle("Bus 5", "BUS1", "DOWN", "LEFT");
        vehicle bus6 = new vehicle("Bus 6", "BUS1", "UP", "RIGHT");

        // Link Vehicles to their correct Traffic Lights
        // West/East lights (Horizontal traffic)
        car1.setTrafficLight(westLight);
        car2.setTrafficLight(westLight);
        bus1.setTrafficLight(westLight);
        bus3.setTrafficLight(westLight);


        car3.setTrafficLight(eastLight);
        car4.setTrafficLight(eastLight);
        bus2.setTrafficLight(eastLight);
        bus4.setTrafficLight(eastLight);

        // North/South lights (Vertical traffic)
        ambulance1.setTrafficLight(northLight);
        car5.setTrafficLight(northLight);
        car6.setTrafficLight(northLight);
        bus5.setTrafficLight(northLight);

        ambulance2.setTrafficLight(southLight);
        car7.setTrafficLight(southLight);
        car8.setTrafficLight(southLight);
        bus6.setTrafficLight(southLight);

        // Draw all vehicles
        car1.draw(root);
        car2.draw(root);
        bus1.draw(root);
        bus3.draw(root);

        car3.draw(root);
        car4.draw(root);
        bus2.draw(root);
        bus4.draw(root);

        ambulance1.draw(root);
        car5.draw(root);
        car6.draw(root);
        bus5.draw(root);

        ambulance2.draw(root);
        car7.draw(root);
        car8.draw(root);
        bus6.draw(root);


        List<vehicle> vehicleList = List.of(
                car1, car2, car3, car4, car5, car6, car7, car8,
                bus1, bus2, bus3, bus4, bus5, bus6,
                ambulance1, ambulance2
        );

        // Animation Loop with Custom Traffic Light Sequence
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                // Increment frame timer
                lightTimer++;

                // Manage multi-phase traffic light sequence
                switch (lightPhase) {
                    case 0: // 1. West GREEN for 36 seconds
                        westLight.setState(TrafficLight.State.GREEN);
                        northLight.setState(TrafficLight.State.RED);
                        eastLight.setState(TrafficLight.State.RED);
                        southLight.setState(TrafficLight.State.RED);

                        if (lightTimer > 2160) {
                            lightTimer = 0;
                            lightPhase = 1;
                        }
                        break;

                    case 1: // 2. West YELLOW for 2 seconds (120 frames)
                        westLight.setState(TrafficLight.State.YELLOW);
                        if (lightTimer > 120) {
                            lightTimer = 0;
                            lightPhase = 2;
                        }
                        break;

                    case 2: // --- 3-SECOND ALL-RED BREAK (West to North) ---
                        westLight.setState(TrafficLight.State.RED);
                        northLight.setState(TrafficLight.State.RED);
                        if (lightTimer > 180) {
                            lightTimer = 0;
                            lightPhase = 3;
                        }
                        break;

                    case 3: // 3. North turns GREEN for 36 seconds
                        northLight.setState(TrafficLight.State.GREEN);
                        if (lightTimer > 2160) {
                            lightTimer = 0;
                            lightPhase = 4;
                        }
                        break;

                    case 4: // 4. North YELLOW for 2 seconds
                        northLight.setState(TrafficLight.State.YELLOW);
                        if (lightTimer > 120) {
                            lightTimer = 0;
                            lightPhase = 5;
                        }
                        break;

                    case 5: // --- 3-SECOND ALL-RED BREAK (North to East) ---
                        northLight.setState(TrafficLight.State.RED);
                        eastLight.setState(TrafficLight.State.RED);
                        if (lightTimer > 180) {
                            lightTimer = 0;
                            lightPhase = 6;
                        }
                        break;

                    case 6: // 5. East turns GREEN for 36 seconds
                        eastLight.setState(TrafficLight.State.GREEN);
                        if (lightTimer > 2160) {
                            lightTimer = 0;
                            lightPhase = 7;
                        }
                        break;

                    case 7: // 6. East YELLOW for 2 seconds
                        eastLight.setState(TrafficLight.State.YELLOW);
                        if (lightTimer > 120) {
                            lightTimer = 0;
                            lightPhase = 8;
                        }
                        break;

                    case 8: // --- 3-SECOND ALL-RED BREAK (East to South) ---
                        eastLight.setState(TrafficLight.State.RED);
                        southLight.setState(TrafficLight.State.RED);
                        if (lightTimer > 180) {
                            lightTimer = 0;
                            lightPhase = 9;
                        }
                        break;

                    case 9: // 7. South turns GREEN for 36 seconds
                        southLight.setState(TrafficLight.State.GREEN);
                        if (lightTimer > 2160) {
                            lightTimer = 0;
                            lightPhase = 10;
                        }
                        break;

                    case 10: // 8. South YELLOW for 2 seconds
                        southLight.setState(TrafficLight.State.YELLOW);
                        if (lightTimer > 120) {
                            lightTimer = 0;
                            lightPhase = 11;
                        }
                        break;

                    case 11: // --- 3-SECOND ALL-RED BREAK (South to West) ---
                        southLight.setState(TrafficLight.State.RED);
                        westLight.setState(TrafficLight.State.RED);
                        if (lightTimer > 180) {
                            lightTimer = 0;
                            lightPhase = 0; // Loop back to Phase 0
                        }
                        break;
                }

                // --- Update All Vehicles ---
                for (vehicle v : vehicleList) {
                    v.update(vehicleList);
                }

                // Reset out-of-bounds vehicles
                for (vehicle v : vehicleList) {
                    v.resetIfOutOfBounds();
                }




            }
        };

        timer.start();

        Scene scene = new Scene(root, 1200, 800);

        stage.setTitle("Traffic Simulator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}