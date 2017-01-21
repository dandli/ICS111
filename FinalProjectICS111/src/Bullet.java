
//Created By: Dan Li
import java.util.*;

public class Bullet {

	// Initialize the variables
	float angle;
	EZImage bullet;
	int x;
	int y;

	// Create the bullet with the angle and position inputs
	Bullet(float a, int x, int y) {
		// The bullet will rotate by the inputed angle
		angle = a;

		// Add the bullet image
		bullet = EZ.addImage("laser.png", x, y);

		// Rotate the bullet
		bullet.rotateBy(angle);
	}

	// Make the bullet move forward
	void go() {
		// THe bullet moves forward by 3
		bullet.moveForward(10);
	}

	// Check to see if the bullet hit the enemy created from the EnemyOne class
	EnemyOne hitTest(ArrayList<EnemyOne> targets) {
		// Initialize the variables to the bullets center
		int x = bullet.getXCenter();
		int y = bullet.getYCenter();

		// Check through all the targets
		for (int i = 0; i < targets.size(); i++) {
			// If a target is within the parameters
			if (targets.get(i).isPointInElement(x, y)) {
				// The target gets hit
				targets.get(i).hit();

				// And the bullet is hidden
				hide();
				return targets.get(i);
			}
		}

		// If the bullet does not hit, return null
		return null;
	}

	// Hide the bullet
	void hide() {
		bullet.hide();
	}

	// Check to see if the bullet is out of bounds
	boolean outOfBounds(int w, int h) {
		// Initialize the variables to the bullets center
		int x = bullet.getXCenter();
		int y = bullet.getYCenter();

		// Check to see if the bullet is within the window
		if ((x < 0 || x > w) && (y < 0 || y > h)) {
			// If it is within the window, return true
			return true;
		}

		// If it is not within the window return false
		return false;
	}

}
