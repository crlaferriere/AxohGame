package axohEngine2.entities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

import axohEngine2.Judgement;
import axohEngine2.project.TYPE;
import axohEngine2.util.Vector2D;

public class Enemy extends Mob{

	private Random random = new Random();
	private int health;
	private TYPE ai;
	private Vector2D position;
	 
	private boolean attacking;
	private Attack currentAttack;

	//moveDir - Direction the mob was moving
	//direction - The direction the Mob is facing
	//randomDir - The random choice of a direction used in random movements
	private DIRECTION moveDir;
	private DIRECTION direction; 
	//Graphics and Window objects the mob needs for display
	protected Graphics2D g2d;
	protected Judgement game; 
	private boolean _isAlive;
	
	private double speed;

	
	public Enemy(Judgement game, Graphics2D g2d, SpriteSheet sheet, int spriteNumber, TYPE ai, String name) {
		super(game, g2d, sheet, spriteNumber, ai, name);
		this.game = game;
		this.g2d = g2d;
		this.ai = ai; 
		setName(name);
		health = 0;
		setAlive(true);
		setSpriteType(ai);
		setPosition(new Vector2D());

	}
	
	//sets the boundaries of object
	public Rectangle bounds;
	public Point boundsOffset; 
	public Rectangle currentBounds() { return new Rectangle(bounds.x + (int) getPosition().getX(), 
			bounds.y + (int) getPosition().getY(), 
			bounds.width, bounds.height); }
			
	public Point getCurrentTile() { return new Point
			(
					(bounds.x + (int) getPosition().getX()) /64, 
					(bounds.y + (int) getPosition().getY()) /64
		); }
					
	// get x and y location of enemy
	
	public double getXLoc() { return getPosition().getX(); }
	public double getYLoc() { return getPosition().getY(); }
	public void setLoc(double x, double y) { //Relative to current position
		getPosition().setLocation(x, y);
		
			//System.out.println(getPosition().getX() + ", " + getPosition().getY());
		
		//xx = xx + x;
		//yy = yy + y;
	}
	
	// move function for enemy
	public void move(double xa, double ya) {
		
		getPosition().setX(getPosition().getX() + xa);
		getPosition().setY(getPosition().getY() + ya);
		
		if(xa < 0) { //left
			//xx += xa; 
			if(moveDir != DIRECTION.LEFT) setAnimTo(leftAnim);
			startAnim();
			direction = DIRECTION.LEFT;
			moveDir = DIRECTION.LEFT;
		} else if(xa > 0) { //right
			//xx += xa; 
			
			if(moveDir != DIRECTION.RIGHT) setAnimTo(rightAnim);
			startAnim();
			direction = DIRECTION.RIGHT;
			moveDir = DIRECTION.RIGHT;
		}
			
		if(ya < 0) {  //up
			//yy += ya;

			if(moveDir != DIRECTION.UP) setAnimTo(upAnim);
			startAnim();
			direction = DIRECTION.UP;
			moveDir = DIRECTION.UP;
		} else if(ya > 0) { //down
			//yy += ya; 
			
			if(moveDir != DIRECTION.DOWN) setAnimTo(downAnim);
			startAnim();
			direction = DIRECTION.DOWN;
			moveDir = DIRECTION.DOWN;
		}
		if(xa == 0 && ya == 0) stopAnim();
	}


	public String getName() { return _name; }
	public TYPE getType() { return ai; }
	
	//Setters for current health, ai, name and speed
	public void setHealth(int health) { this.health = health; }
	public void setAi(TYPE ai) { this.ai = TYPE.RANDOMPATH; }
	public void setName(String name) { super._name = name; }
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getSpeed() {
		return speed;
	}
	public boolean isAlive() {
		return _isAlive;
	}
	public void setAlive(boolean value) {
		_isAlive = value;
	}
	
	/**************************************************
	 * Set all of the movement related variables to whatever nothing is
	 **************************************************/
	public void resetMovement() {
		moveDir = DIRECTION.NONE;
	}
	
	
	public void updateMob() {
		
		/*double dx = player.getXLoc() - this.getXLoc();
		double dy = player.getYLoc() - this.getYLoc();
		dx = Math.min(Math.abs(dx), player.getSpeed() * 0.5) * Math.signum(dx);
		dy = Math.min(Math.abs(dy), player.getSpeed() * 0.5) * Math.signum(dy);
		this.move(dx, dy); 
	}*/ 
	}
}