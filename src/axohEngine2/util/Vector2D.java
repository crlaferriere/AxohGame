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

import java.awt.geom.Point2D;

public class Vector2D extends Point2D.Double {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double x;
	private double y;
	
	public Vector2D() {
		this(0, 0);
	}
	
	public Vector2D(double x, double y) {
		super(x, y);
	}

	@Override
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public void setLocation(double x, double y) {
		setX(x);
		setY(y);
	}
	
}
