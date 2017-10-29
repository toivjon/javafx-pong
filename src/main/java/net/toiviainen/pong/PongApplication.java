package net.toiviainen.pong;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import net.toiviainen.pong.scene.WelcomeScene;

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

    /** The width of the initial application resolution. */
    public static final int RESOLUTION_WIDTH = 800;

    /** The height of the initial application resolution. */
    public static final int RESOLUTION_HEIGHT = 600;

    /** The name of the font used for the application texts. */
    public static final String FONT_NAME = "Arial";

    /** The font for big texts like topics etc. */
    public static final Font BIG_FONT = new Font(FONT_NAME, 32);

    /** The font for normal texts like descriptions etc. */
    public static final Font SMALL_FONT = new Font(FONT_NAME, 18);

    private Stage primaryStage;

    @Override
    public void init() throws Exception {
        // ... something here?
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // store the primary stage reference.
        this.primaryStage = primaryStage;

        // set definitions for the primary stage.
        primaryStage.setTitle("JavaFX - Pong");
        primaryStage.setScene(new WelcomeScene(this));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        // ... something here?
        super.stop();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String args[]) {
        launch(args);
    }

}
