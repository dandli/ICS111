/*Created by: Creighton Gorai
 * 
 * This class with create the player you can control
 * to defeat enemies that spawn in to destroy you
 * 
 */
public class Survivor {

	// member variables for movable survivor
	int x;
	int y;
	double speed;
	EZImage img;

	// constructor for movable survivor
	Survivor(int x0, int y0, double s) {

		// initialize x with x0
		x = x0;
		// initialize y with y0
		y = y0;
		// initialize speed with s
		speed = s;
		// img is of type EZImage, so initlaize it with EZImage
		img = EZ.addImage("ship2.png", x, y);
		// resize img by calling img.gif
		img.scaleBy(1);
	}

	// Accessor method to retrieve the position of the survivor
	int getX() {
		return img.getXCenter();
	}

	int getY() {
		return img.getYCenter();
	}

	// Accessor method to retrieve the position of the survivor
	void turnRight(double degrees) {
		img.turnRight(degrees);
	}

	void turnLeft(double degrees) {
		img.turnLeft(degrees);
	}

	// Member function that tells survivor to move a certain distance at a
	// caertain speed
	void moveForward(double distance) {
		// call the moveForward command and move img forward by speed*distance
		img.moveForward(speed * distance);

		// set x to img.getXCenter();
		x = img.getXCenter();
		// set x to img.getYCenter();
		y = img.getYCenter();

		// Here we get the width and the height of the window on the screen
		int wx = EZ.getWindowWidth();
		int wy = EZ.getWindowWidth();

		// Now call the Math.floorMod function to calculate x Mod wx and set x
		// to this variable
		x = Math.floorMod(x, wx);
		// Now call the Math.floorMod function to calculate y Mod wy and set x
		// to this variable
		y = Math.floorMod(y, wy);
		// Using img.translateTo translate img to the new coordinates (xy)
		img.translateTo(x, y);
	}

	// Member function that checks if the character was told to move in a
	// certain direction/angle
	void checkKeys() {

		// if key 'd' is held down turnRight(5)
		if (EZInteraction.isKeyDown('d')) {
			turnRight(4);
			/// if key 'a' is held down turnLeft(5)
		} else if (EZInteraction.isKeyDown('a')) {
			turnLeft(4);
			// if key 's' is held down moveForward(-5) "backwards
		} else if (EZInteraction.isKeyDown('s')) {
			moveForward(-4);
			// if key 'w' is held down moveForward(5)
		} else if (EZInteraction.isKeyDown('w')) {
			moveForward(4);
		}

	}

}