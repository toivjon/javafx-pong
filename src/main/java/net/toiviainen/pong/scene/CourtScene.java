package net.toiviainen.pong.scene;

import static java.util.Objects.requireNonNull;
import static net.toiviainen.pong.PongApplication.RESOLUTION_HEIGHT;
import static net.toiviainen.pong.PongApplication.RESOLUTION_WIDTH;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
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
public class CourtScene extends AbstractScene {

	/** The width of the small boxes used around the scene. */
	private static final int BOX_WIDTH = (RESOLUTION_WIDTH / 40);

	/** The height for the top and bottom walls. */
	private static final int WALL_HEIGHT = BOX_WIDTH;

	/** The height for the left and right paddle. */
	private static final int PADDLE_HEIGHT = BOX_WIDTH * 5;

	/** The offset of the paddles from the edges of the scene. */
	private static final int EDGE_OFFSET = RESOLUTION_HEIGHT / 20;

	/** A constant definition for paddle movement speed. */
	private static final double PADDLE_MOVEMENT_SPEED = 7.5;

	/** The amount to nudge items on a collision. */
	private static final double NUDGE = 0.01;

	// ================================
	// = movement direction constants =
	// ================================

	/** A direction constant for the upward movement. */
	private static final double DIRECTION_UP = -1.0;

	/** A direction constant for the downward movement. */
	private static final double DIRECTION_DOWN = 1.0;

	/** A direction constant for the right movement. */
	private static final double DIRECTION_RIGHT = 1.0;

	/** A direction constant for the left movement. */
	private static final double DIRECTION_LEFT = -1.0;

	/** A direction constant for being still. */
	private static final double DIRECTION_NONE = 0.0;

	// ===================
	// = class variables =
	// ===================

	private final Rectangle topWall;
	private final Rectangle bottomWall;

	private final Rectangle leftGoal;
	private final Rectangle rightGoal;

	private final Rectangle leftPaddle;
	private final Rectangle rightPaddle;

	private final Group centerLine;

	private final Rectangle ball;

	private double leftPaddleYDirection;
	private double rightPaddleYDirection;

	private double ballMovementSpeed = 2.75;
	private double ballXDirection = DIRECTION_RIGHT;
	private double ballYDirection = DIRECTION_UP;

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

		leftGoal = new Rectangle();
		leftGoal.setLayoutX(-RESOLUTION_WIDTH);
		leftGoal.setLayoutY(0);
		leftGoal.setWidth(RESOLUTION_WIDTH);
		leftGoal.setHeight(RESOLUTION_HEIGHT);

		rightGoal = new Rectangle();
		rightGoal.setLayoutX(RESOLUTION_WIDTH);
		rightGoal.setLayoutY(0);
		rightGoal.setWidth(RESOLUTION_WIDTH);
		rightGoal.setHeight(RESOLUTION_HEIGHT);

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
		children.add(leftGoal);
		children.add(rightGoal);
		children.add(leftPaddle);
		children.add(rightPaddle);
		children.add(centerLine);
		children.add(ball);

		setFill(Color.BLACK);

		// activate the way to active paddle movement.
		setOnKeyPressed(x -> {
			switch (x.getCode()) {
				case UP:
					rightPaddleYDirection = DIRECTION_UP;
					break;
				case DOWN:
					rightPaddleYDirection = DIRECTION_DOWN;
					break;
				case W:
					leftPaddleYDirection = DIRECTION_UP;
					break;
				case S:
					leftPaddleYDirection = DIRECTION_DOWN;
					break;
				default:
					break;
			}
		});

		// active the way to disable paddle movement.
		setOnKeyReleased(x -> {
			switch (x.getCode()) {
				case UP:
					if (rightPaddleYDirection == DIRECTION_UP) {
						rightPaddleYDirection = DIRECTION_NONE;
					}
					break;
				case DOWN:
					if (rightPaddleYDirection == DIRECTION_DOWN) {
						rightPaddleYDirection = DIRECTION_NONE;
					}
					break;
				case W:
					if (leftPaddleYDirection == DIRECTION_UP) {
						leftPaddleYDirection = DIRECTION_NONE;
					}
					break;
				case S:
					if (leftPaddleYDirection == DIRECTION_DOWN) {
						leftPaddleYDirection = DIRECTION_NONE;
					}
					break;
				default:
					break;
			}
		});

	}

	@Override
	public void tick() {
		ball.setLayoutX(ball.getLayoutX() + ballMovementSpeed * ballXDirection);
		ball.setLayoutY(ball.getLayoutY() + ballMovementSpeed * ballYDirection);

		leftPaddle.setLayoutY(leftPaddle.getLayoutY() + (leftPaddleYDirection * PADDLE_MOVEMENT_SPEED));
		rightPaddle.setLayoutY(rightPaddle.getLayoutY() + (rightPaddleYDirection * PADDLE_MOVEMENT_SPEED));

		// get wall references to check against collisions.
		Bounds topWallBounds = topWall.getBoundsInParent();
		Bounds bottomWallBounds = bottomWall.getBoundsInParent();

		// check that the right paddle stays within the scene boundaries.
		Bounds rightPaddleBounds = rightPaddle.getBoundsInParent();
		if (rightPaddleBounds.intersects(topWallBounds)) {
			rightPaddle.setLayoutY(topWall.getLayoutY() + topWall.getHeight() + NUDGE);
		} else if (rightPaddleBounds.intersects(bottomWallBounds)) {
			rightPaddle.setLayoutY(bottomWallBounds.getMinY() - rightPaddle.getHeight() - NUDGE);
		}

		// check that the left paddle stays within the scene boundaries.
		Bounds leftPaddleBounds = leftPaddle.getBoundsInParent();
		if (leftPaddleBounds.intersects(topWallBounds)) {
			leftPaddle.setLayoutY(topWall.getLayoutY() + topWall.getHeight() + NUDGE);
		} else if (leftPaddleBounds.intersects(bottomWallBounds)) {
			leftPaddle.setLayoutY(bottomWallBounds.getMinY() - leftPaddle.getHeight() - NUDGE);
		}

		// check whether the ball hits something.
		Bounds ballBounds = ball.getBoundsInParent();
		if (ballBounds.intersects(leftPaddleBounds)) {
			// prevent ball from invading the paddle and set a new direction.
			ball.setLayoutX(leftPaddleBounds.getMaxX() + NUDGE);
			ballXDirection = DIRECTION_RIGHT;
		} else if (ballBounds.intersects(rightPaddleBounds)) {
			// prevent ball from invading the paddle and set a new direction.
			ball.setLayoutX(rightPaddleBounds.getMinX() - ball.getWidth() - NUDGE);
			ballXDirection = DIRECTION_LEFT;
		} else if (ballBounds.intersects(topWallBounds)) {
			// prevent ball from invading the wall and set a new direction.
			ball.setLayoutY(topWall.getLayoutY() + topWall.getHeight() + NUDGE);
			ballYDirection = DIRECTION_DOWN;
		} else if (ballBounds.intersects(bottomWallBounds)) {
			// prevent ball from invading the wall and set a new direction.
			ball.setLayoutY(bottomWallBounds.getMinY() - ball.getHeight() - NUDGE);
			ballYDirection = DIRECTION_UP;
		} else {
			// get goal references to check against goals.
			Bounds leftGoalBounds = leftGoal.getBoundsInParent();
			Bounds rightGoalBounds = rightGoal.getBoundsInParent();

			if (ballBounds.intersects(leftGoalBounds)) {
				System.out.println("a score for the right player!");
				// TODO add a score for the right player.
				// TODO reset game state.
			} else if (ballBounds.intersects(rightGoalBounds)) {
				System.out.println("a score for the left player!");
				// TODO add a score for the left player.
				// TODO reset game state.
			}
		}
	}

}
