package axohEngine2.util;

public class Line2D extends java.awt.geom.Line2D {
	
	private Point2D a;
	private Point2D b;
	
	public Line2D() {
		this(0, 0, 0, 0);
	}
	
	public Line2D(Point2D a, Point2D b) {
		this(a.getX(), a.getY(), b.getX(), b.getY());
	}
	
	public Line2D(double x1, double y1, double x2, double y2) {
		a = new Point2D(x1, y1);
		b = new Point2D(x2, y2);
	}

	@Override
	public Rectangle2D getBounds2D() {
		return new Rectangle2D(Math.abs(a.getX() - b.getX()), Math.abs(a.getY() - b.getY()));
	}
	
	public double getSlope() {
		return (getY2() - getY1()) / (getX2() - getX1());
	}
	
	public double getIntercept() {
		return getY1() - getSlope() * getX1();
	}
	
	public Point2D getIntersection(Line2D otherLine) {
		if (!intersectsLine(otherLine)) {
			// In the case that the lines never intersect...
			return null;
		}
		double m1 = getSlope();
		double m2 = otherLine.getSlope();
		if (m1 == m2) {
			// In the case that the lines overlap and are parallel...
			return null;
		}
		double x;
		if (m1 == java.lang.Double.POSITIVE_INFINITY || m2 == java.lang.Double.POSITIVE_INFINITY) {
			// If one of the two lines' slope is undefined, set the intersection's x position to that
			// line's x position.
			x = m1 == java.lang.Double.POSITIVE_INFINITY ? getX1() : otherLine.getX1();
		} else {
			double b1 = getIntercept();
			double b2 = otherLine.getIntercept();
			// Solve for x.
			x = (b2 - b1) / (m1 - m2);
		}
		double y;
		//if (m1 == 0 || m2 == 0) {
			// If one of the two lines' slope is 0, set the intersection's y to that line's y position.
		//	y = m1 == 0 ? getY1() : otherLine.getY1();
		//} else {
		if (m1 == java.lang.Double.POSITIVE_INFINITY) {
			y = otherLine.getYAt(x);
		} else {
			y = getYAt(x);
		}
		//}
		return new Point2D(x, y);
	}

	@Override
	public Point2D getP1() {
		return new Point2D(a);
	}

	@Override
	public Point2D getP2() {
		return new Point2D(b);
	}

	@Override
	public double getX1() {
		return a.getX();
	}
	
	@Override
	public double getY1() {
		return a.getY();
	}

	@Override
	public double getX2() {
		return b.getX();
	}

	@Override
	public double getY2() {
		return b.getY();
	}
	
	public double getXAt(double y) {
		if (Math.abs(getSlope()) == java.lang.Double.POSITIVE_INFINITY) {
			return getX1();
		}
		return y / getSlope() - getIntercept();
	}
	
	public double getYAt(double x) {
		if (getSlope() == 0) {
			return getY1();
		}
		return getSlope() * x + getIntercept();
	}

	@Override
	public void setLine(double x1, double y1, double x2, double y2) {
		a.setLocation(x1, y1);
		b.setLocation(x2, y2);
	}
	
	@Override
	public String toString() {
		return super.toString() + " | {" + a.toString() + ", " + b.toString() + "}";
	}

}
