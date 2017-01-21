//Enemy class , consists of an enemy that will angle itself towards the main player
//Created by: Seyoung Nakama

public class EnemyOne {
	// Member variables for the enemy class
	int ex;
	int ey;
	int sx;
	int sy;
	double speed;
	EZImage img;
	float angle = 0f;
	boolean dead;

	EnemyOne() {
	}

	// This is the EnemyOne constructor and contains the needed elements to
	// spawn it
	EnemyOne(int x, int y, int s) {
		ex = x;
		ey = y;
		speed = s;
		img = EZ.addImage("alien.png", x, y);// designates and enemy image
		dead = false;

	}

	// function that makes the enemy move, created in such a way the enemy will
	// follow the survivor
	void go(int _sx, int _sy) {
		// draws upon the center coordinates for the enemy
		ex = img.getXCenter();
		ey = img.getYCenter();
		// sets an angle by pythagorean theorem through the use of a tan
		// function
		angle = (float) (Math.atan2((ex - _sx), -(ey - _sy)) * (180 / Math.PI));
		img.rotateTo(angle - 90);// rotates the image so that it will move
									// forward
		img.moveForward(-speed);// allows the enemy to move forward since image
								// is backwards the spped is negative

	}

	// boolean to check if enemy is dead
	boolean isDead() {
		return dead;
	}

	// hit function that will hide the image of enemy and remove it
	void hit() {
		dead = true;
		img.hide();

		EZ.removeEZElement(img);
	}

	// function checks for collision between the enemy and survivor
	boolean isPointInElement(int x, int y) {
		// draws upon EZ function
		return img.isPointInElement(x, y);
	}

}