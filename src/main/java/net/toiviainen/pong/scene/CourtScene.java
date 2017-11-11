package net.toiviainen.pong.scene;

import static java.util.Objects.requireNonNull;
import static net.toiviainen.pong.PongApplication.RESOLUTION_HEIGHT;
import static net.toiviainen.pong.PongApplication.RESOLUTION_WIDTH;

import java.util.Random;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.toiviainen.pong.PongApplication;
import net.toiviainen.pong.util.Args;

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

	// =========================================
	// = number indicator generation constants =
	// =========================================

	/** The width of a number indicator. */
	private static final double NUMBER_WIDTH = (RESOLUTION_WIDTH / 10);

	/** The height of a number indicator. */
	private static final double NUMBER_HEIGHT = (RESOLUTION_HEIGHT / 6);

	/** The thickness of a number indicator number side. */
	private static final double NUMBER_THICKNESS = NUMBER_HEIGHT / 5;

	// ==================
	// = ball constants =
	// ==================

	/** The initial velocity of the ball movement. */
	private static final double BALL_INITIAL_SPEED = 3.0;

	/** The amount of velocity to be added to ball on each paddle collision. */
	private static final double BALL_SPEED_INCREASE = 0.5;

	/** The maximum velocity for the ball movement. */
	private static final double BALL_MAX_SPEED = 10.0;

	// ===================
	// = class variables =
	// ===================

	private final Rectangle topWall;
	private final Rectangle bottomWall;

	private final Rectangle leftGoal;
	private final Rectangle rightGoal;

	private final Rectangle leftPaddle;
	private final Rectangle rightPaddle;

	private final Group leftScoreIndicator;
	private final Group rightScoreIndicator;

	private final Group centerLine;

	private final Rectangle ball;

	private final Random random = new Random();

	private double leftPaddleYDirection;
	private double rightPaddleYDirection;

	private double ballMovementSpeed = BALL_INITIAL_SPEED;
	private double ballXDirection = DIRECTION_RIGHT;
	private double ballYDirection = DIRECTION_UP;

	private int player1Score = 0;
	private int player2Score = 0;

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
		leftGoal.setWidth(RESOLUTION_WIDTH - BOX_WIDTH);
		leftGoal.setHeight(RESOLUTION_HEIGHT);

		rightGoal = new Rectangle();
		rightGoal.setLayoutX(RESOLUTION_WIDTH + BOX_WIDTH);
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

		leftScoreIndicator = new Group();
		leftScoreIndicator.setLayoutX(RESOLUTION_WIDTH / 2 - (70 + RESOLUTION_WIDTH / 10));
		leftScoreIndicator.setLayoutY(RESOLUTION_HEIGHT / 10);

		rightScoreIndicator = new Group();
		rightScoreIndicator.setLayoutX(RESOLUTION_WIDTH / 2 + 70);
		rightScoreIndicator.setLayoutY(RESOLUTION_HEIGHT / 10);

		centerLine = new Group();
		centerLine.setLayoutX(RESOLUTION_WIDTH / 2 - BOX_WIDTH / 2);
		for (double y = WALL_HEIGHT; y < RESOLUTION_HEIGHT; y += (1.93 * BOX_WIDTH)) {
			Rectangle box = new Rectangle(0, y, BOX_WIDTH, BOX_WIDTH);
			box.setFill(Color.WHITE);
			centerLine.getChildren().add(box);
		}

		ball = new Rectangle();
		ball.setFill(Color.WHITE);
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
		children.add(leftScoreIndicator);
		children.add(rightScoreIndicator);
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

		// assign initial scores and update score indicators.
		setPlayerScore(1, 0);
		setPlayerScore(2, 0);
	}

	@Override
	public void tick() {
		// move the ball.
		ball.setLayoutX(ball.getLayoutX() + ballMovementSpeed * ballXDirection);
		ball.setLayoutY(ball.getLayoutY() + ballMovementSpeed * ballYDirection);

		// move the paddles if and when moved by the players.
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

			// increase the movement speed of the ball.
			ballMovementSpeed += BALL_SPEED_INCREASE;
			ballMovementSpeed = Math.min(ballMovementSpeed, BALL_MAX_SPEED);
		} else if (ballBounds.intersects(rightPaddleBounds)) {
			// prevent ball from invading the paddle and set a new direction.
			ball.setLayoutX(rightPaddleBounds.getMinX() - ball.getWidth() - NUDGE);
			ballXDirection = DIRECTION_LEFT;

			// increase the movement speed of the ball.
			ballMovementSpeed += BALL_SPEED_INCREASE;
			ballMovementSpeed = Math.min(ballMovementSpeed, BALL_MAX_SPEED);
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
				player1Score++;
				if (player1Score >= 10) {
					// TODO end the game.
				} else {
					setPlayerScore(1, player1Score);
					reset();
				}
			} else if (ballBounds.intersects(rightGoalBounds)) {
				player2Score++;
				if (player2Score >= 10) {
					// TODO end the game.
				} else {
					setPlayerScore(2, player2Score);
					reset();
				}
			}
		}
	}

	/**
	 * <p>
	 * Reset the game state.
	 * </p>
	 * <p>
	 * This function can be used to reset the game position into the default
	 * position where the ball and the paddles are being repositioned in the
	 * middle of the screen. Typically used when either player scores a point.
	 * </p>
	 */
	private void reset() {
		// set the ball back into the middle of the scene.
		ball.setLayoutX(RESOLUTION_WIDTH / 2 - BOX_WIDTH / 2);
		ball.setLayoutY(RESOLUTION_HEIGHT / 2 - BOX_WIDTH / 2);

		// randomise a new direction for the ball.
		int randomValue = random.nextInt(3);
		switch (randomValue) {
			case 0:
				ballXDirection = DIRECTION_LEFT;
				ballYDirection = DIRECTION_UP;
				break;
			case 1:
				ballXDirection = DIRECTION_LEFT;
				ballYDirection = DIRECTION_DOWN;
				break;
			case 2:
				ballXDirection = DIRECTION_RIGHT;
				ballYDirection = DIRECTION_UP;
				break;
			case 3:
				ballXDirection = DIRECTION_RIGHT;
				ballYDirection = DIRECTION_DOWN;
				break;
			default:
				throw new IllegalStateException("Unsupport direction random: " + randomValue);
		}

		// reset the ball movement velocity.
		ballMovementSpeed = BALL_INITIAL_SPEED;

		// set paddles back into the middle of the y-axis.
		leftPaddle.setLayoutY(RESOLUTION_HEIGHT / 2 - PADDLE_HEIGHT / 2);
		rightPaddle.setLayoutY(leftPaddle.getLayoutY());
	}

	/**
	 * Set the given score for the target player.
	 * @param player The index [1|2] of the target player.
	 * @param score The score [0..9] for the target player.
	 * @throws IllegalArgumentException On any invalid values.
	 */
	private void setPlayerScore(int player, int score) throws IllegalArgumentException {
		Args.isBetween(player, 1, 2, "The number must be either one or two!");
		Args.isBetween(score, 0, 9, "The score must be within the [0..9] range!");

		if (player == 1) {
			ObservableList<Node> children = rightScoreIndicator.getChildren();
			children.clear();
			children.add(createNumberGroup(score));
		} else if (player == 2) {
			ObservableList<Node> children = leftScoreIndicator.getChildren();
			children.clear();
			children.add(createNumberGroup(score));
		}
	}

	/**
	 * Create a new rectangle that is filled with white colour.
	 * @param x The x-coordinate of the rectangle.
	 * @param y The y-coordinate of the rectangle.
	 * @param w The width of the rectangle.
	 * @param h The height of the rectangle.
	 * @return A new rectangle filled with white colour.
	 * @throws IllegalArgumentException Whether any negative values were given.
	 */
	private static Rectangle whiteRect(double x, double y, double w, double h) throws IllegalArgumentException {
		Args.isGte(x, 0, "The x-coordinate must be equal or higher than zero!");
		Args.isGte(y, 0, "The y-coordinate must be equal or higher than zero!");
		Args.isGte(w, 0, "The width must be equal or higher than zero!");
		Args.isGte(h, 0, "The heigh must be equal or higher than zero!");

		// create a new rectangle that is filled with white colour.
		Rectangle rectangle = new Rectangle(x, y, w, h);
		rectangle.setFill(Color.WHITE);
		return rectangle;
	}

	/**
	 * Create a new number group to render the provided number [0..9] in JavaFX.
	 * @param number The number to create.
	 * @return A new group that can be used to render the provided number.
	 * @throws IllegalArgumentException Whether any invalid number was given.
	 */
	private static Group createNumberGroup(int number) throws IllegalArgumentException {
		Args.isBetween(number, 0, 9, "The number must be within the [0..9] range!");

		// @formatter:off
		// construct a new group with the necessary graphics.
		Group group = new Group();
		ObservableList<Node> children = group.getChildren();
		switch (number) {
			case 0:
				children.add(whiteRect(0, 0, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(0, 0, NUMBER_THICKNESS, NUMBER_HEIGHT));
				children.add(whiteRect(NUMBER_WIDTH - NUMBER_THICKNESS, 0, NUMBER_THICKNESS, NUMBER_HEIGHT));
				children.add(whiteRect(0, NUMBER_HEIGHT - NUMBER_THICKNESS, NUMBER_WIDTH, NUMBER_THICKNESS));
				break;
			case 1:
				children.add(whiteRect(NUMBER_WIDTH / 2 - NUMBER_THICKNESS, 0, NUMBER_THICKNESS, NUMBER_HEIGHT));
				break;
			case 2:
				children.add(whiteRect(0, 0, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(NUMBER_WIDTH - NUMBER_THICKNESS, 0, NUMBER_THICKNESS, NUMBER_HEIGHT / 2));
				children.add(whiteRect(0, NUMBER_HEIGHT / 2 - NUMBER_THICKNESS / 2, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(0, NUMBER_HEIGHT / 2, NUMBER_THICKNESS, NUMBER_HEIGHT / 2));
				children.add(whiteRect(0, NUMBER_HEIGHT - NUMBER_THICKNESS, NUMBER_WIDTH, NUMBER_THICKNESS));
				break;
			case 3:
				children.add(whiteRect(0, 0, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(0, NUMBER_HEIGHT / 2 - NUMBER_THICKNESS / 2, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(0, NUMBER_HEIGHT - NUMBER_THICKNESS, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(NUMBER_WIDTH - NUMBER_THICKNESS, 0, NUMBER_THICKNESS, NUMBER_HEIGHT));
				break;
			case 4:
				children.add(whiteRect(0, 0, NUMBER_THICKNESS, NUMBER_HEIGHT / 2));
				children.add(whiteRect(0, NUMBER_HEIGHT / 2 - NUMBER_THICKNESS / 2, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(NUMBER_WIDTH - NUMBER_THICKNESS, 0, NUMBER_THICKNESS, NUMBER_HEIGHT));
				break;
			case 5:
				children.add(whiteRect(0, 0, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(0, 0, NUMBER_THICKNESS, NUMBER_HEIGHT / 2));
				children.add(whiteRect(0, NUMBER_HEIGHT / 2 - NUMBER_THICKNESS / 2, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(NUMBER_WIDTH - NUMBER_THICKNESS, NUMBER_HEIGHT / 2, NUMBER_THICKNESS, NUMBER_HEIGHT / 2));
				children.add(whiteRect(0, NUMBER_HEIGHT - NUMBER_THICKNESS, NUMBER_WIDTH, NUMBER_THICKNESS));
				break;
			case 6:
				children.add(whiteRect(0, 0, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(0, 0, NUMBER_THICKNESS, NUMBER_HEIGHT));
				children.add(whiteRect(0, NUMBER_HEIGHT / 2 - NUMBER_THICKNESS / 2, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(NUMBER_WIDTH - NUMBER_THICKNESS, NUMBER_HEIGHT / 2, NUMBER_THICKNESS, NUMBER_HEIGHT / 2));
				children.add(whiteRect(0, NUMBER_HEIGHT - NUMBER_THICKNESS, NUMBER_WIDTH, NUMBER_THICKNESS));
				break;
			case 7:
				children.add(whiteRect(0, 0, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(NUMBER_WIDTH - NUMBER_THICKNESS, 0, NUMBER_THICKNESS, NUMBER_HEIGHT));
				break;
			case 8:
				children.add(whiteRect(0, 0, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(0, 0, NUMBER_THICKNESS, NUMBER_HEIGHT));
				children.add(whiteRect(0, NUMBER_HEIGHT / 2 - NUMBER_THICKNESS / 2, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(NUMBER_WIDTH - NUMBER_THICKNESS, 0, NUMBER_THICKNESS, NUMBER_HEIGHT));
				children.add(whiteRect(0, NUMBER_HEIGHT - NUMBER_THICKNESS, NUMBER_WIDTH, NUMBER_THICKNESS));
				break;
			case 9:
				children.add(whiteRect(0, 0, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(0, 0, NUMBER_THICKNESS, NUMBER_HEIGHT / 2));
				children.add(whiteRect(0, NUMBER_HEIGHT / 2 - NUMBER_THICKNESS / 2, NUMBER_WIDTH, NUMBER_THICKNESS));
				children.add(whiteRect(NUMBER_WIDTH - NUMBER_THICKNESS, 0, NUMBER_THICKNESS, NUMBER_HEIGHT));
				children.add(whiteRect(0, NUMBER_HEIGHT - NUMBER_THICKNESS, NUMBER_WIDTH, NUMBER_THICKNESS));
				break;
			default:
				break;
		}
		// @formatter:on
		return group;
	}

}
