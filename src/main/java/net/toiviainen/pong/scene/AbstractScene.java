package net.toiviainen.pong.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class AbstractScene extends Scene {

	public AbstractScene(Parent root, int width, int height) {
		super(root, width, height);
	}

	public abstract void tick();

}
