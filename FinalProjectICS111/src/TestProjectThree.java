
/* "Alien extermination"
 * 
 * Created by: Creighton Gorai, Dan Li, Seyoung Nakama
 * 
 * How to play:
 * 'W' ; 'A' ; 'S' ; 'D' to move
 * 'Space Bar to shoot'
 * 
 * Objective:
 * Get the highest score as you can
 */

import java.util.*;
import java.io.*;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class TestProjectThree {

	// Constants for the game
	static final int SURVIVOR_RUNNING = 0;
	static final int SURVIVOR_DIES = 1;

	public static void main(String[] args) throws java.io.IOException {

		// Creates screen to play the game in and sets a background
		EZ.initialize(900, 900);
		EZImage backgroundPicture = EZ.addImage("galax.jpg", 450, 540);

		// Sets up background music
		EZSound galaxy = EZ.addSound("26 Untold Legends.wav");
		// plays background music
		galaxy.play();
		// Loops the song
		galaxy.loop();

		// Sets up background music
		EZSound alien = EZ.addSound("alien_explosion.wav");

		// Sets up background music
		EZSound bullet = EZ.addSound("lase_cannon.wav");

		// Hit Sounds
		EZSound crash = EZ.addSound("crash.wav");
		// failure sound
		EZSound failure = EZ.addSound("18 Relic of Hope.wav");

		// sets lives to 3
		int lives = 3;

		// creates a new font
		int fonts = 40;
		// creates the text for the score
		EZText textsss = EZ.addText(100, 30, "SCORE: 0", Color.white, fonts);
		// sets text type
		textsss.setFont("majorforce.ttf");

		// creates a new font
		int font = 40;
		// creates the text for the score
		EZText text = EZ.addText(95, 80, "LIVES: 3", Color.red, font);
		// sets text type
		text.setFont("majorforce.ttf");

		// Keep track of the score. Set it to zero in the beginning.
		int score = 0;

		// Creates a new survivor spawned at a certain point with a set amount
		// of speed
		Survivor survivor = new Survivor(450, 450, 2);

		// Set survivorState to running for the game to play
		int survivorState = SURVIVOR_RUNNING;

		// Creates 2 arraylists to spawn in enemies and bullets
		ArrayList<EnemyOne> targets = new ArrayList<EnemyOne>();
		ArrayList<Bullet> bullets = new ArrayList<Bullet>();

		// initialize angle for bullets
		float angle = 0;
		int capacity = 2;

		// creates variable for bulet time delay
		long start_time = System.currentTimeMillis();
		int sec = 0;
		int milsec = 0;

		// boolean for shooting after bullet time delay
		boolean shoot = false;

		// While loop to start playing the game
		while (survivorState == SURVIVOR_RUNNING) {

			// function that allows for the bullet to have a delay time before
			// the next shot
			milsec += (System.currentTimeMillis() - start_time);
			// the amount ofd time before the next bullet can be shot
			if (milsec >= 150) {
				sec++;
				milsec = milsec - 150;
				if (sec >= 5) {
					// allows the bullet to be shot
					shoot = true;
				}
			}
			// resets time so it can delay the same amount of time before the
			// next bullet
			start_time = System.currentTimeMillis();

			// created a Random generator for spawning
			Random randomGenerator = new Random();

			// when all the enemies are dead or at the beginning, the condition
			// for enemies to spawn
			if (targets.size() == 0 && shoot == true) {
				capacity++;

				// the for loop will iterate through spawning 4 enemies
				// depending on how big capacity is
				// waves will basically increase as 4,8,12,16, etc...
				for (int i = 0; i < capacity / 2; i++) {

					targets.add(new EnemyOne(-100, randomGenerator.nextInt(900), 1));
					targets.add(new EnemyOne(randomGenerator.nextInt(900), -100, 2));
					targets.add(new EnemyOne(1000, randomGenerator.nextInt(900), 1));
					targets.add(new EnemyOne(randomGenerator.nextInt(900), 1000, 2));
				}

			}

			// checks survivor keys at all times to see if it has moved
			survivor.checkKeys();

			// changes angle variable to the direction the survivor is
			// facing so
			// bullet spawns facing that direction
			if (EZInteraction.isKeyDown('d')) {
				angle += 4;
			}

			if (EZInteraction.isKeyDown('a')) {
				angle -= 4;
			}

			// creates a for loop to constantly check for survivor position
			// in
			// order to allow the enemies to chase the survivor
			for (int i = 0; i < targets.size(); i++) {

				int sx = survivor.getX();
				int sy = survivor.getY();
				targets.get(i).go(sx, sy);

				// Checks if Enemy has collided with survivor
				if (targets.get(i).isPointInElement(sx, sy)) {
					lives--;
					crash.play();
					targets.get(i).hit();
					targets.remove(i);

					// decreases score everytime you run over a zombie
					text.setMsg("LIVES: " + lives);
				}
			}

			// Checks if the space bar has been pressed set up for bullet
			if (shoot == true && EZInteraction.wasKeyPressed(KeyEvent.VK_SPACE)) {

				int sx = survivor.getX();
				int sy = survivor.getY();
				bullets.add(new Bullet(angle, sx, sy));
				// plays bullet sound
				bullet.play();

			}
			// sets shoot to false for the loop back bullet delay
			shoot = false;

			// Creates a for loop to go through all the bullets
			for (int a = 0; a < bullets.size(); a++) {

				// If bullet is out of our initialized screen area
				// remove
				// the bullet so it does not move forward to infinity
				if (bullets.get(a).outOfBounds(900, 900)) {
					// Hide the bullet and remove it
					bullets.get(a).hide();
					bullets.remove(a);

				}
			}
			// Allows our bullet to keep moving forward from point of
			// being
			// shot with space bar
			for (int b = 0; b < bullets.size(); b++) {

				// Move the bullet forward
				bullets.get(b).go();

				// Checks to see if the target was hit by that bullet
				EnemyOne tmp = bullets.get(b).hitTest(targets);

				// If the target is not hit
				if (tmp != null) {

					// Remove the bullet
					bullets.remove(b);

					// If the target is hit
					if (tmp.isDead())
						// Remove the target
						targets.remove(tmp);
					// plays alien death
					alien.play();
					// increase score when alien is killed
					score++;

					// decreases score every time you kill an alien
					textsss.setMsg("SCORE: " + score);

				}

			}
			// changes survivors state to game over sequence
			if (lives <= 0) {
				survivorState = SURVIVOR_DIES;

			}
			// Game OVer sequence
			if (survivorState == SURVIVOR_DIES) {

				// stops primary game sound
				galaxy.stop();
				// plays failure song
				failure.play();
				// Creates screen to play the game in and sets a background for
				// Game Over
				EZ.initialize(900, 900);
				EZImage endGamePicture = EZ.addImage("explosion.jpg", 450, 540);

				for (int k = 0; k < 50; k++) {

					// creates a new font
					int fontss = 150;
					// creates the text for the score
					EZText textss = EZ.addText(450, 257, "GAME OVER", Color.black, fontss);
					// sets text type
					textss.setFont("holojacket.ttf");

					// creates a new font
					int fontsss = 150;
					// creates the text for the score
					EZText textssss = EZ.addText(455, 260, "GAME OVER", Color.green, fontsss);
					// sets text type
					textssss.setFont("holojacket.ttf");
				}
				// creates a new font
				int fontss = 225;
				// creates the text for the score
				EZText textss = EZ.addText(450, 457, "score: " + score, Color.black, fontss);
				// sets text type
				textss.setFont("holojacket.ttf");

				// creates a new font
				int fontsss = 225;
				// creates the text for the score
				EZText textssss = EZ.addText(455, 460, "score: " + score, Color.green, fontsss);
				// sets text type
				textssss.setFont("holojacket.ttf");
			}

			// refreshes screen so images move
			EZ.refreshScreen();
		}
	}
}