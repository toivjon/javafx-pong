package net.toiviainen.pong.scene;

import static java.util.Objects.requireNonNull;
import static net.toiviainen.pong.PongApplication.BIG_FONT;
import static net.toiviainen.pong.PongApplication.RESOLUTION_HEIGHT;
import static net.toiviainen.pong.PongApplication.RESOLUTION_WIDTH;
import static net.toiviainen.pong.PongApplication.SMALL_FONT;

import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.toiviainen.pong.PongApplication;

/**
 * <p>
 * The end game scene for the Pong game.
 * </p>
 * <p>
 * This scene is the summary scene which will be shown to users when they have
 * player the game. Scene contains the information the overall result of the
 * game as well as the instructions how to proceed back into the welcome scene.
 * </p>
 */
public class EndGameScene extends AbstractScene {

	private final Text topicText;
	private final Text gameHasEndedText;
	private final Text winnerText;
	private final Text resultsTopicText;
	private final Text resultsText;
	private final Text proceedInstructionsText;

	public EndGameScene(PongApplication application) throws NullPointerException {
		super(new Group(), RESOLUTION_WIDTH, RESOLUTION_HEIGHT);

		requireNonNull(application, "The application cannot be null!");

		topicText = new Text("JavaFX Pong - Results");
		topicText.setTextOrigin(VPos.CENTER);
		topicText.setFont(BIG_FONT);
		topicText.setLayoutX((RESOLUTION_WIDTH - topicText.prefWidth(-1)) / 2);
		topicText.setLayoutY(RESOLUTION_HEIGHT / 6);
		topicText.setFill(Color.WHITE);

		gameHasEndedText = new Text("Game has ended");
		gameHasEndedText.setTextOrigin(VPos.CENTER);
		gameHasEndedText.setFont(SMALL_FONT);
		gameHasEndedText.setLayoutX((RESOLUTION_WIDTH - gameHasEndedText.prefWidth(-1)) / 2);
		gameHasEndedText.setLayoutY(topicText.getLayoutY() + 100);
		gameHasEndedText.setFill(Color.WHITE);

		winnerText = new Text("--- TODO ---"); // TODO when context is ready.
		winnerText.setTextOrigin(VPos.CENTER);
		winnerText.setFont(SMALL_FONT);
		winnerText.setLayoutX((RESOLUTION_WIDTH - winnerText.prefWidth(-1)) / 2);
		winnerText.setLayoutY(gameHasEndedText.getLayoutY() + 40);
		winnerText.setFill(Color.WHITE);

		resultsTopicText = new Text("End results:");
		resultsTopicText.setTextOrigin(VPos.CENTER);
		resultsTopicText.setFont(SMALL_FONT);
		resultsTopicText.setLayoutX((RESOLUTION_WIDTH - resultsTopicText.prefWidth(-1)) / 2);
		resultsTopicText.setLayoutY(winnerText.getLayoutY() + 40);
		resultsTopicText.setFill(Color.WHITE);

		resultsText = new Text("xx - xx"); // TODO when context is ready.
		resultsText.setTextOrigin(VPos.CENTER);
		resultsText.setFont(SMALL_FONT);
		resultsText.setLayoutX((RESOLUTION_WIDTH - resultsText.prefWidth(-1)) / 2);
		resultsText.setLayoutY(resultsTopicText.getLayoutY() + 40);
		resultsText.setFill(Color.WHITE);

		proceedInstructionsText = new Text("Press [ENTER] to proceed");
		proceedInstructionsText.setTextOrigin(VPos.CENTER);
		proceedInstructionsText.setFont(SMALL_FONT);
		proceedInstructionsText.setLayoutX((RESOLUTION_WIDTH - proceedInstructionsText.prefWidth(-1)) / 2);
		proceedInstructionsText.setLayoutY(resultsText.getLayoutY() + 160);
		proceedInstructionsText.setFill(Color.WHITE);

		Parent root = getRoot();
		if (!(root instanceof Group)) {
			throw new AssertionError("The scene root is not a Group instance!");
		}

		Group rootGroup = (Group) root;
		ObservableList<Node> children = rootGroup.getChildren();
		children.add(topicText);
		children.add(gameHasEndedText);
		children.add(winnerText);
		children.add(resultsTopicText);
		children.add(resultsText);
		children.add(proceedInstructionsText);

		setOnKeyReleased(x -> {
			if (x.getCode() == KeyCode.ENTER) {
				// move into the welcoming scene so we can reset the game.
				Stage primaryStage = application.getPrimaryStage();
				primaryStage.setScene(new WelcomeScene(application));
			}
		});

		setFill(Color.BLACK);
	}

	@Override
	public void tick() {
		// ... nothing to do ...
	}

}
