package axohEngine2.util;

public class RectangleCollider2D extends Rectangle2D {
	
	private Surface2D left;
	private Surface2D right;
	private Surface2D top;
	private Surface2D bottom;
	
	private Surface2D[] surfaces;
	
	public RectangleCollider2D(double x, double y, double width, double height) {
		super(x, y, width, height);
		left = new Surface2D(x, y, x, y + height, -1.0, 0.0);
		right = new Surface2D(x + width, y, x + width, y + height, 1.0, 0.0);
		top = new Surface2D(x, y, x + width, y, 0.0, -1.0);
		bottom = new Surface2D(x, y + height, x + width, y + height, 0.0, 1.0);
		surfaces = new Surface2D[] { left, right, top, bottom };
	}
	
	public Line2D[] getIntersectedLines(Line2D line) {
		Line2D[] possibleLines = new Line2D[4];
		int lineCount = 0;
		for (Line2D surface : surfaces) {
			if (surface.intersectsLine(line)) {
				possibleLines[lineCount++] = surface;
			}
		}
		Line2D[] intersectedLines = new Line2D[lineCount];
		System.arraycopy(possibleLines, 0, intersectedLines, 0, lineCount);
		return intersectedLines;
	}
	
	public Point2D[] getIntersectedPoints(Line2D line) {
		Point2D[] possiblePoints = new Point2D[4];
		int pointCount = 0;
		for (Line2D segment : surfaces) {
			Point2D intersection = segment.getIntersection(line);
			if (intersection != null) {
				possiblePoints[pointCount++] = intersection;
			}
		}
		Point2D[] intersectedPoints = new Point2D[pointCount];
		System.arraycopy(possiblePoints, 0, intersectedPoints, 0, pointCount);
		return intersectedPoints;
	}
	
	public Surface2D rectCast(RectangleCollider2D r, double dx, double dy) {
		Line2D topLeft 		= new Line2D(r.getX(), r.getY(), r.getX() + dx, r.getY() + dy);
		Line2D topRight 	= new Line2D(r.getX() + r.getWidth(), r.getY(), r.getX() + r.getWidth() + dx, r.getY() + dy);
		Line2D bottomLeft 	= new Line2D(r.getX(), r.getY() + r.getHeight(), r.getX() + dx, r.getY() + r.getHeight() + dy);
		Line2D bottomRight	= new Line2D(r.getX() + r.getWidth(), r.getY() + r.getHeight(), r.getX() + r.getWidth() + dx, r.getY() + r.getHeight() + dy);
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
		if (closestPoint == null) {
			topLeft 	= new Line2D(getX(), getY(), getX() - dx, getY() - dy);
			topRight 	= new Line2D(getX() + getWidth(), getY(), getX() + getWidth() - dx, getY() - dy);
			bottomLeft 	= new Line2D(getX(), getY() + getHeight(), getX() - dx, getY() + getHeight() - dy);
			bottomRight	= new Line2D(getX() + getWidth(), getY() + getHeight(), getX() + getWidth() - dx, getY() + getHeight() - dy);
			lazy = new Line2D[] { topLeft, topRight, bottomLeft, bottomRight };
			smallestDistance = 0;
			for (Line2D line : lazy) {
				if (r.intersectsLine(line)) {
					Point2D[] points = r.getIntersectedPoints(line);
					for (Point2D p : points) {
						double potentialLargestDistance = p.distance(line.getP1());
						if (potentialLargestDistance > smallestDistance) {
							closestLine = line;
							closestPoint = p;
							smallestDistance = potentialLargestDistance;
						}
					}
				}
			}
			if (closestPoint == null) {
				return null;
			}
			return ((Surface2D) r.getIntersectedLines(new Line2D(closestLine.getP1(), closestPoint))[0]).inverted();
		}
		return (Surface2D) getIntersectedLines(new Line2D(closestLine.getP1(), closestPoint))[0];
	}
	
}