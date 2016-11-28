package axohEngine2.util;

public class Point2D extends java.awt.geom.Point2D {
	
	private double x;
	private double y;
	
	public Point2D() {
		this(0, 0);
	}
	
	public Point2D(Point2D location) {
		this(location.getX(), location.getY());
	}
	
	public Point2D(double x, double y) {
		setLocation(x, y);
	}

	@Override
	public double getX() {
		return x;
	}
	
	public void setX(double value) {
		x = value;
	}

	@Override
	public double getY() {
		return y;
	}
	
	public void setY(double value) {
		y = value;
	}

	@Override
	public void setLocation(double x, double y) {
		setX(x);
		setY(y);
	}
	
	@Override
	public String toString() {
		return super.toString() + " | {x: " + getX() + ", y: " + getY() + "}";
	}
	
}
