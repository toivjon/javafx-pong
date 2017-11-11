package net.toiviainen.pong.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * <p>
 * A scene abstraction for all three scenes in the game.
 * </p>
 * <p>
 * This abstraction allows the game to easily change between the scenes. It also
 * provides a way to ensure that each scene contains a support for ticking the
 * current game logic related to the scene.
 * </p>
 */
public abstract class AbstractScene extends Scene {

	public AbstractScene(Parent root, int width, int height) {
		super(root, width, height);
	}

	/**
	 * A tick function that is called on each main loop iteration.
	 */
	public abstract void tick();

}
