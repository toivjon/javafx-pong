package net.toiviainen.pong;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * <p>
 * The root of the game application.
 * </p>
 * <p>
 * This class acts as the root of the game application execution. It contains
 * the functionality that is required for the application to perform the stage
 * initialisation and the construction of the initial game scene.
 * </p>
 * <p>
 * The application entry point ({@link #main(String[])} is also located here.
 * </p>
 */
public class PongApplication extends Application {

    @Override
    public void init() throws Exception {
        // ... something here?
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // ... scene construction here?
        primaryStage.setTitle("JavaFX - Pong");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        // ... something here?
        super.stop();
    }

    public static void main(String args[]) {
        launch(args);
    }

}
