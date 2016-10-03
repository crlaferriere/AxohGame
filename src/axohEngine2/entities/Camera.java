package axohEngine2.entities;

import axohEngine2.Game;
import axohEngine2.util.Vector2D;

public class Camera {

	private Vector2D position;
	
	public Camera() {
		position = new Vector2D();
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public void track(Mob mob) {
		double halfSpriteSize = (double) mob.getSpriteSize() * 0.5;
		setLocation(mob.getXLoc() + halfSpriteSize, mob.getYLoc() + halfSpriteSize);
	}
	
	public void setLocation(Vector2D position) {
		setLocation(position.getX(), position.getY());
	}
	
	public void setLocation(double x, double y) {
		position.setLocation(x - (double) Game.CENTERX, y - (double) Game.CENTERY);
		// position.setLocation(x, y);
	}
	
	public double getX() {
		return position.getX();
	}
	
	public double getY() {
		return position.getY();
	}
	
}
