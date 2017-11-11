package net.toiviainen.pong;

/**
 * <p>
 * A game context container to store game session specific data.
 * </p>
 * <p>
 * This container tracks the game session related values. In this application
 * this includes the current score of both players. This could be expanded to
 * include all other kinds of values if and when required.
 * </p>
 */
public class PongContext {

	private int player1Score = 0;
	private int player2Score = 0;

	public PongContext() {
		// ...
	}

	public void reset() {
		player1Score = 0;
		player2Score = 0;
	}

	public int getPlayer1Score() {
		return player1Score;
	}

	public void setPlayer1Score(int player1Score) {
		this.player1Score = player1Score;
	}

	public int incPlayer1Score() {
		player1Score++;
		return player1Score;
	}

	public int getPlayer2Score() {
		return player2Score;
	}

	public void setPlayer2Score(int player2Score) {
		this.player2Score = player2Score;
	}

	public int incPlayer2Score() {
		player2Score++;
		return player2Score;
	}

}
