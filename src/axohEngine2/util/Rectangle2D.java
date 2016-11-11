package axohEngine2.util;

public class Rectangle2D extends java.awt.geom.Rectangle2D {
	
	private Point2D location;
	
	private double width;
	private double height;
	
	public Rectangle2D() {
		this(0, 0, 0, 0);
	}
	
	public Rectangle2D(double width, double height) {
		this(0, 0, width, height);
	}
	
	public Rectangle2D(Point2D location, double width, double height) {
		this(location.getX(), location.getY(), width, height);
	}
	
	public Rectangle2D(double x, double y, double width, double height) {
		super();
		location = new Point2D(x, y);
		setWidth(width);
		setHeight(height);
	}

	@Override
	public java.awt.geom.Rectangle2D createIntersection(java.awt.geom.Rectangle2D other) {
		double left 	= Math.max(getX(), other.getX());
		double top		= Math.max(getY(), other.getY());
		double right 	= Math.min(getX() + getWidth(), other.getX() + other.getWidth());
		double bottom	= Math.min(getY() + getHeight(), other.getY() + other.getHeight());
		double width 	= Math.max(right - left, 0);
		double height 	= Math.max(bottom - top, 0);
		return new Rectangle2D(left, top, width, height);
	}

	@Override
	public java.awt.geom.Rectangle2D createUnion(java.awt.geom.Rectangle2D other) {
		double left		= Math.min(getX(), other.getX());
		double top 		= Math.min(getY(), other.getY());
		double right 	= Math.max(getX() + getWidth(), other.getX() + other.getWidth());
		double bottom 	= Math.max(getY() + getHeight(), other.getY() + other.getHeight());
		double width 	= right - left;
		double height 	= bottom - top;
		return new Rectangle2D(left, top, width, height);
	}

	@Override
	public int outcode(double x, double y) {
		double left = getX();
		double top = getY();
		double right = getX() + getWidth();
		double bottom = getY() + getHeight();
		int code = 0;
		if (x > right) {
			code |= Rectangle2D.OUT_RIGHT;
		}
		if (x < left) {
			code |= Rectangle2D.OUT_LEFT;
		}
		if (y > bottom) {
			code |= Rectangle2D.OUT_BOTTOM;
		}
		if (y < top) {
			code |= Rectangle2D.OUT_TOP;
		}
		return code;
	}
	
	public void setRect(Point2D position, double width, double height) {
		setLocation(location);
		setWidth(width);
		setHeight(height);
	}

	@Override
	public void setRect(double x, double y, double width, double height) {
		setLocation(x, y);
		setWidth(width);
		setHeight(height);
	}

	@Override
	public double getHeight() {
		return height;
	}
	
	public void setHeight(double value) {
		height = value;
	}

	@Override
	public double getWidth() {
		return width;
	}
	
	public void setWidth(double value) {
		width = value;
	}
	
	public void setLocation(Point2D point) {
		location.setLocation(point);
	}
	
	public void setLocation(double x, double y) {
		location.setLocation(x, y);
	}

	@Override
	public double getX() {
		return location.getX();
	}
	
	public void setX(double value) {
		location.setX(value);
	}

	@Override
	public double getY() {
		return location.getY();
	}
	
	public void setY(double value) {
		location.setY(value);
	}

	@Override
	public boolean isEmpty() {
		return width * height == 0;
	}

	@Override
	public String toString() {
		return super.toString() + " {x: " + getX() + ", y: " + getY() + ", width: " + getWidth() + ", height: " + getHeight() + "}";
	}
	
}
