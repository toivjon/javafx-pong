# javafx-pong
A JavaFX implementation of the classic Pong game.

Contains a single game mode where two human players can play against each other.

## Scenes
Game is split into following scenes:
1. A welcome scene, which contains the main menu.
2. A court scene, which contains the actual gameplay.
3. An end game scene, which contains the results from the court scene.

The list of scene transitions:
* 1 to 2, when a player starts the game by pressing the enter key.
* 2 to 3, when either player receives the 10th point (i.e. game is over).
* 3 to 1, when the enter key is being pressed.

## Features
This Pong implementation contains the following features.
* Each game lasts until either player receives the 10th point.
* Both paddles are controlled by human players.
* Ball velocity is increased on each hit with a paddle.
* Ball velocity does not exceed the pre-defined maximum velocity.
* Ball movement is being stopped for 30 ticks after each reset.
* Ball direction is randomized from four different directions after each reset.
* Paddles are returned to their default position after each reset.

## Screenshots
![alt text](https://github.com/toivjon/javafx-pong/blob/master/screenshots/welcome-scene.png "WelcomeScene")
![alt text](https://github.com/toivjon/javafx-pong/blob/master/screenshots/court-scene.png "CourtScene")
![alt text](https://github.com/toivjon/javafx-pong/blob/master/screenshots/endgame-scene.png "EndGameScene")