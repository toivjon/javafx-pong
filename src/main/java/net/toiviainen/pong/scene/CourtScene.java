package net.toiviainen.pong.scene;

import static java.util.Objects.requireNonNull;
import static net.toiviainen.pong.PongApplication.RESOLUTION_HEIGHT;
import static net.toiviainen.pong.PongApplication.RESOLUTION_WIDTH;

import javafx.scene.Group;
import javafx.scene.Scene;
import net.toiviainen.pong.PongApplication;

public class CourtScene extends Scene {

    public CourtScene(PongApplication application) throws NullPointerException {
        super(new Group(), RESOLUTION_WIDTH, RESOLUTION_HEIGHT);

        requireNonNull(application, "The application cannot be null!");
    }

}
