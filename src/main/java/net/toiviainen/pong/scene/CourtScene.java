package net.toiviainen.pong.scene;

import static java.util.Objects.requireNonNull;
import static net.toiviainen.pong.PongApplication.RESOLUTION_HEIGHT;
import static net.toiviainen.pong.PongApplication.RESOLUTION_WIDTH;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.toiviainen.pong.PongApplication;

/**
 * <p>
 * The court scene for the Pong game.
 * </p>
 * <p>
 * This scene is the in-game scene which will contain the instances that are
 * required to actually play the game. This is the scene which handles the
 * simulation logics that make the game to act as an real-time application.
 * </p>
 */
public class CourtScene extends Scene {

	/** The width of the small boxes used around the scene. */
	private static final int BOX_WIDTH = (RESOLUTION_WIDTH / 40);

	/** The height for the top and bottom walls. */
	private static final int WALL_HEIGHT = BOX_WIDTH;

	/** The height for the left and right paddle. */
	private static final int PADDLE_HEIGHT = BOX_WIDTH * 5;

	/** The offset of the paddles from the edges of the scene. */
	private static final int EDGE_OFFSET = RESOLUTION_HEIGHT / 20;

	private final Rectangle topWall;
	private final Rectangle bottomWall;

	private final Rectangle leftPaddle;
	private final Rectangle rightPaddle;

	private final Group centerLine;

	private final Rectangle ball;

	public CourtScene(PongApplication application) throws NullPointerException {
		super(new Group(), RESOLUTION_WIDTH, RESOLUTION_HEIGHT);

		requireNonNull(application, "The application cannot be null!");

		topWall = new Rectangle();
		topWall.setLayoutX(0);
		topWall.setLayoutY(0);
		topWall.setWidth(RESOLUTION_WIDTH);
		topWall.setHeight(WALL_HEIGHT);
		topWall.setFill(Color.WHITE);

		bottomWall = new Rectangle();
		bottomWall.setLayoutX(0);
		bottomWall.setLayoutY(RESOLUTION_HEIGHT - WALL_HEIGHT);
		bottomWall.setWidth(RESOLUTION_WIDTH);
		bottomWall.setHeight(WALL_HEIGHT);
		bottomWall.setFill(Color.WHITE);

		leftPaddle = new Rectangle();
		leftPaddle.setLayoutX(EDGE_OFFSET);
		leftPaddle.setLayoutY(RESOLUTION_HEIGHT / 2 - PADDLE_HEIGHT / 2);
		leftPaddle.setWidth(BOX_WIDTH);
		leftPaddle.setHeight(PADDLE_HEIGHT);
		leftPaddle.setFill(Color.WHITE);

		rightPaddle = new Rectangle();
		rightPaddle.setLayoutX(RESOLUTION_WIDTH - EDGE_OFFSET - BOX_WIDTH);
		rightPaddle.setLayoutY(leftPaddle.getLayoutY());
		rightPaddle.setWidth(BOX_WIDTH);
		rightPaddle.setHeight(PADDLE_HEIGHT);
		rightPaddle.setFill(Color.WHITE);

		centerLine = new Group();
		centerLine.setLayoutX(RESOLUTION_WIDTH / 2 - BOX_WIDTH / 2);
		for (double y = WALL_HEIGHT; y < RESOLUTION_HEIGHT; y += (1.93 * BOX_WIDTH)) {
			Rectangle box = new Rectangle(0, y, BOX_WIDTH, BOX_WIDTH);
			box.setFill(Color.WHITE);
			centerLine.getChildren().add(box);
		}

		ball = new Rectangle();
		ball.setFill(Color.RED);
		ball.setLayoutX(RESOLUTION_WIDTH / 2 - BOX_WIDTH / 2);
		ball.setLayoutY(RESOLUTION_HEIGHT / 2 - BOX_WIDTH / 2);
		ball.setWidth(BOX_WIDTH);
		ball.setHeight(BOX_WIDTH);

		Parent root = getRoot();
		if (!(root instanceof Group)) {
			throw new AssertionError("The scene root is not a Group instance!");
		}

		Group rootGroup = (Group) root;
		ObservableList<Node> children = rootGroup.getChildren();
		children.add(topWall);
		children.add(bottomWall);
		children.add(leftPaddle);
		children.add(rightPaddle);
		children.add(centerLine);
		children.add(ball);

		setFill(Color.BLACK);
	}

}
