/**********************************************************************
 * @author Travis R. Dewitt
 * @version 0.8
 * Date: June 15, 2015
 * 
 * Title: Vectors 
 * Description: Construct an Entity only using lines. This is merely some framework and is 
 * currently unused in the given game, Judgement.
 * 
 * This work is licensed under a Attribution-NonCommercial 4.0 International
 * CC BY-NC-ND license. http://creativecommons.org/licenses/by-nc/4.0/
 *********************************************************************/

package axohEngine2.util;

public class Vector2D extends Point2D {
	
	private static final long serialVersionUID = 1L;
	
	public Vector2D() {
		this(0, 0);
	}
	
	public Vector2D(Vector2D normal) {
		this(normal.getX(), normal.getY());
	}
	
	public Vector2D(double x, double y) {
		super(x, y);
	}

	public void add(Vector2D otherVector) {
		add(otherVector.getX(), otherVector.getY());
	}
	
	public void add(double x, double y) {
		setLocation(getX() + x, getY() + y);
	}
	
}
