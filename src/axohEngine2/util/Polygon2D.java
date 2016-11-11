package axohEngine2.util;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;

public class Polygon2D implements Shape {
	
	private Line2D[] segments;
	
	public Polygon2D(double x, double y) {
		/*super(x, y);
		left = new Line2D(x, y, x, y + height);
		right = new Line2D(x + width, y, x + width, y + height);
		top = new Line2D(x, y, x + width, y);
		bottom = new Line2D(x, y + height, x + width, y + height);
		segments = new Line2D[] { left, right, top, bottom };*/
	}
	
	/*public Line2D[] getIntersectedLines(Line2D line) {
		Line2D[] possibleLines = new Line2D[4];
		int lineCount = 0;
		for (Line2D segment : segments) {
			if (segment.intersectsLine(line)) {
				possibleLines[lineCount++] = segment;
			}
		}
		Line2D[] intersectedLines = new Line2D[lineCount];
		System.arraycopy(possibleLines, 0, intersectedLines, 0, lineCount);
		return intersectedLines;
	}
	
	public Point2D[] getIntersectedPoints(Line2D line) {
		Point2D[] possiblePoints = new Point2D[4];
		int pointCount = 0;
		for (Line2D segment : segments) {
			Point2D intersection = segment.getIntersection(line);
			if (intersection != null) {
				possiblePoints[pointCount++] = intersection;
			}
		}
		Point2D[] intersectedPoints = new Point2D[pointCount];
		System.arraycopy(possiblePoints, 0, intersectedPoints, 0, pointCount);
		return intersectedPoints;
	}
	
	public Line2D rectCast(RectangleCollider2D r, double dx, double dy) {
		Line2D topLeft = new Line2D(r.getX(), r.getY(), r.getX() + dx, r.getY() + dy);
		Line2D topRight = new Line2D(r.getX() + r.getWidth(), r.getY(), r.getX() + r.getWidth() + dx, r.getY() + dy);
		Line2D bottomLeft = new Line2D(r.getX(), r.getY() + r.getHeight(), r.getX() + dx, r.getY() + r.getHeight() + dy);
		Line2D bottomRight = new Line2D(r.getX() + r.getWidth(), r.getY() + r.getHeight(), r.getX() + r.getWidth() + dx, r.getY() + r.getHeight() + dy);
		Line2D[] lazy = new Line2D[] { topLeft, topRight, bottomLeft, bottomRight };
		Line2D closestLine = null;
		Point2D closestPoint = null;
		double smallestDistance = java.lang.Double.MAX_VALUE;
		for (Line2D line : lazy) {
			if (intersectsLine(line)) {
				Point2D[] points = getIntersectedPoints(line);
				for (Point2D p : points) {
					double potentialSmallestDistance = p.distance(line.getP1());
					if (potentialSmallestDistance < smallestDistance) {
						closestLine = line;
						closestPoint = p;
						smallestDistance = potentialSmallestDistance;
					}
				}
			}
		}
		return new Line2D(closestLine.getP1(), closestPoint);
	}*/

	@Override
	public boolean contains(java.awt.geom.Point2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean intersects(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
