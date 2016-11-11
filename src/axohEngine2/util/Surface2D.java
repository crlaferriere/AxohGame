package axohEngine2.util;

public class Surface2D extends Line2D {

	private Vector2D normal;
	
	public Surface2D(double x1, double y1, double x2, double y2, double nx, double ny) {
		super(x1, y1, x2, y2);
		normal = new Vector2D(nx, ny);
	}
	
	public Vector2D getNormal() {
		return new Vector2D(normal);
	}
	
	public Surface2D inverted() {
		return new Surface2D(getX1(), getY1(), getX2(), getY2(), -normal.getX(), -normal.getY());
	}
	
}
