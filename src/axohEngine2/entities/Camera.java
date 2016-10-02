package axohEngine2.entities;

import axohEngine2.util.Vector2D;

public class Camera {

	private Vector2D position;
	
	public Camera() {
		position = new Vector2D();
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public void setPosition(Vector2D position) {
		setPosition(position.getX(), position.getY());
	}
	
	public void setPosition(double x, double y) {
		position.setLocation(x, y);
	}
	
	public double getX() {
		return position.getX();
	}
	
	public double getY() {
		return position.getY();
	}
	
	public void setLocation(double x, double y) {
		position.setLocation(x, y);
	}
	
}
