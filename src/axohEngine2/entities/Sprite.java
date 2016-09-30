/****************************************************************************************************
 * @author Travis R. Dewitt
 * @version 1.2
 * Date: July 5, 2015
 * 
 * Title: Sprite
 * Description: Define a more complex image object with bounding boxes and lifespans
 * 
 * This work is licensed under a Attribution-NonCommercial 4.0 International
 * CC BY-NC-ND license. http://creativecommons.org/licenses/by-nc/4.0/
 ****************************************************************************************************/
//Package
package axohEngine2.entities;

//Imports
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JFrame;

import axohEngine2.project.TYPE;

public class Sprite extends Object {
	
	/***************
	 * Variables
	 ***************/
	//image - The class which contains location data about the sprite
	//image - The actual pixel data of an image
	//sheet - The spriteSheet the sprite image can be found on
	//spriteNumber - The position on the spriteSheet the image is found
	//scale - The number the sprite width and height will be multiplied with when displaying the image
	protected ImageEntity image;
    protected int scale;
	
	//currentState - Int which defines many possible states(This could be changed to an enum in the future for better readability)
	//sprType - The Type of the sprite, warping, npc, player, ai etc. (Reference Type.java class)
	//_collided - boolean defining if a sprite has recently hit something solid
    protected int currentState;
    protected TYPE sprType;
    
    //spriteSize - pixel size of the sprite, width * height
    //boundLeg/Left/Right/HeadX/Y - X and Y ints defining Bounding boxes on the sprite used for collision (Hit boxes)
    //left,right,head,leg - Boolean defining if a bound is being used
    //hasMultBounds - Set to true if there is more than one bound being used
    //boundSize - Width * height of the hit boxes on the sprite
    protected int spriteSize;
    protected int _boundLegX, _boundLegY;
    protected int _boundLeftX, _boundLeftY;
    protected int _boundRightX, _boundRightY;
    protected int _boundHeadX, _boundHeadY;
    protected boolean left = false, right = false, head = false, leg = false;
    protected boolean hasMultBounds = false;
    protected int boundSize;
    
    //nx,ny - Offset x value from the x or y position of the sprite to the hit box
    protected int nx, ny;

    /*******************************************
     * Constructor - Set all default parameters
     * 
     * @param frame - JFrame Window the sprite will be displayed in
     * @param g2d - Graphics needed to display the image
     ******************************************/
    public Sprite(JFrame frame, Graphics2D g2d) {
        image = new ImageEntity(frame);
        image.setGraphics(g2d);
        currentState = 0;
    }
    
    /*********************************************
     * Set an image for the sprite or change the current one
     * 
     * @param sheet - spriteSheet which contains the image for the sprite
     * @param spriteNumber - Int location on the spriteSheet the image is located on
     * @return - The Image object that is now the sprite
     ***********************************************/
    public Image setSprite(SpriteSheet sheet, int spriteNumber) {
    	// image = (Image) sheet.getSprite(spriteNumber);
    	image.setImage(sheet.getSprite(spriteNumber));
    	if(spriteSize == 0) spriteSize = sheet.getSpriteSize() * sheet.getScale();
    	return getImage();
    }
    
    /***********************************************
     * Set the default bounding box on the sprite
     * 
     * @param BoundSize - Width * height of the bound
     * @param x - int x location of the box based on the top left corner of the sprite
     * @param y - int y location of the box based on the top left corner of the sprite
     ***********************************************/
    public void setBounds(int BoundSize, int x, int y) {
    	boundSize = BoundSize * scale;
    	nx = x;
    	ny = y;
    }
    
    /*******************************************************************************
     * Set up 5 different bounds on the sprite 
     * In the future, this should be a very different method when bounds become their own
     * seperate class with which contains all of these many variables in simpler form.
     * Instead, this method will be instantiating and adding another bound to the list of bounds on a sprite.
     * 
     * Initially, the legBoundX and legBoundY is the currently selected bound for the sprite to use in collision
     * 
     * @param BoundSize - width * height of the bound
     * @param boundLegX - x position of this bound
     * @param boundLegY - y position of this bound
     * @param boundLeftX - x position of this bound
     * @param boundLeftY - y position of this bound
     * @param boundRightX - x position of this bound
     * @param boundRightY - y position of this bound
     * @param boundHeadX - x position of this bound
     * @param boundHeadY - y position of this bound
     ******************************************************************************/
    public void setMultBounds(int BoundSize, int boundLegX, int boundLegY, int boundLeftX, int boundLeftY, int boundRightX, int boundRightY, int boundHeadX, int boundHeadY) {
    	boundSize = BoundSize * scale;
    	nx = boundLegX;
    	ny = boundLegY;
    	leg = true;
    	_boundLegX = boundLegX;
    	_boundLegY = boundLegY;
    	_boundLeftX = boundLeftX;
    	_boundLeftY = boundLeftY;
    	_boundRightX = boundRightX;
    	_boundRightY = boundRightY;
    	_boundHeadX = boundHeadX;
    	_boundHeadY = boundHeadY;
    	hasMultBounds = true;
    }
    
    //Turn on or off possible bounds(Toggling)
    public void toggleLeg(boolean change) { leg = change; }
    public void toggleLeft(boolean change) { left = change; }
    public void toggleRight(boolean change) { right = change; }
    public void toggleHead(boolean change) { head = change; }
   
    //Turn all bounds off
    public void allBoundsOff() {
    	leg = false;
    	head = false;
    	left = false;
    	right = false;
    }
    /**************************************************************
     * Check each bound for intersection using another rectangle
     * 
     * @param r - Rectangle to check for intersection
     * @return - Boolean for if the intersection is happening
     ***************************************************************/
    public boolean checkLeftBound(Rectangle r) {
    	if(left){ if(r.intersects(image.getBounds(boundSize, _boundLeftX, _boundLeftY))) return true; }
    	return false;
    }
    public boolean checkRightBound(Rectangle r) {
    	if(right){ if(r.intersects(image.getBounds(boundSize, _boundRightX, _boundRightY))) return true; }
    	return false;
    }
    public boolean checkHeadBound(Rectangle r) {
    	if(head){ if(r.intersects(image.getBounds(boundSize, _boundHeadX, _boundHeadY))) return true; }
    	return false;
    }
    public boolean checkLegBound(Rectangle r) {
    	if(leg){ if(r.intersects(image.getBounds(boundSize, _boundLegX, _boundLegY))) return true; }
    	return false;
    }
    
    //Getters for image, solid, boundSize and spriteSize
    public Image getImage() { return image.getImage(); }
    public int getBoundSize() { return boundSize; }
    public int getSpriteSize() { return spriteSize; }
    
    /*******************************************************************
     * Get a bound x or y position based on the direction a sprite is hitting another
     * 0 - left
     * 1 - right
     * 2 - head
     * 3 - leg
     * 
     * @param hitDir - Described above as direction which is being collided from
     * @return - x or y position of the bound being collided
     *******************************************************************/
    public double getBoundX(int hitDir) { 
    	if(hitDir == 0) return _boundLeftX;
    	if(hitDir == 1) return _boundRightX;
    	if(hitDir == 2) return _boundHeadX;
    	if(hitDir == 3) return _boundLegX;
    	return nx;
    }
    public double getBoundY(int hitDir) {
    	if(hitDir == 0) return _boundLeftY;
    	if(hitDir == 1) return _boundRightY;
    	if(hitDir == 2) return _boundHeadY;
    	if(hitDir == 3) return _boundLegY;
    	return ny; 
    }
    
    /********************************************************
     * Getters for specific bound x and y positions, fact of multiple bounds
     * and for the rectangles pertaining to those bounds
     * 
     * @return - Various bounding box data
     *********************************************************/
    public int legBoundX() { return _boundLegX; }
    public int legBoundY() { return _boundLegY; }
    public int leftBoundX() { return _boundLeftX; }
    public int leftBoundY() { return _boundLeftY; }
    public int rightBoundX() { return _boundRightX; }
    public int rightBoundY() { return _boundRightY; }
    public int headBoundX() { return _boundHeadX; }
    public int headBoundY() { return _boundHeadY; }
    public boolean hasMultBounds() { return hasMultBounds; }
    public Rectangle getLeftBound() { return image.getBounds(boundSize, _boundLeftX, _boundLeftY); }
    public Rectangle getRightBound() { return image.getBounds(boundSize, _boundRightX, _boundRightY); }
    public Rectangle getHeadBound() { return image.getBounds(boundSize, _boundHeadX, _boundHeadY); }
    public Rectangle getLegBound() { return image.getBounds(boundSize, _boundLegX, _boundLegY); }

    /*******************************************************************
     * load bitmap file for individual sprites
     * 
     * @param filename - String directory location the sprite image is located in
     ********************************************************************/
    public void load(String filename) {
        image.load(filename);
    }
    
    /***********************************************************
     * Draw bounding rectangle around sprite bounds for debugging
     * 
     * @param c - Color for the box to be
     ***********************************************************/
    public void drawBounds(Color c) {
        image.g2d.setColor(c);
        if(!hasMultBounds) image.g2d.draw(getBounds());
        else {
        	if(leg) image.g2d.draw(getEntity().getBounds(boundSize, _boundLegX, _boundLegY));
        	if(left) image.g2d.draw(getEntity().getBounds(boundSize, _boundLeftX, _boundLeftY));
        	if(right) image.g2d.draw(getEntity().getBounds(boundSize, _boundRightX, _boundRightY));
        	if(head) image.g2d.draw(getEntity().getBounds(boundSize, _boundHeadX, _boundHeadY));
        }
    }

    /**************************************************************
     * Getter and setter for the generic sprite state variable. (alive, dead, collided, etc)
     * Right now this is just an int but should in the future become 
     * an enum for easier reading
     * 
     * @return - currentState int variable
     ************************************************************/
    public int state() { return currentState; }
    public void setState(int state) { currentState = state; }

    //returns a bounding rectangle for the default bound
    public Rectangle getBounds() { 
	    	if(nx != 0 || ny != 0) return image.getBounds(boundSize, nx, ny); 
	    	else return image.getBounds(boundSize);
    }

    /***************************************************************
     * Returns the source image width/height
     * 
     * @return - Int width or height of the image
     ***************************************************************/
    public int imageWidth() { return image.getWidth(); }
    public int imageHeight() { return image.getHeight(); }

    //check for collision with a rectangular shape
    public boolean collidesWith(Rectangle rect) {
        return (rect.intersects(getBounds()));
    }
    
    //check for collision with another sprite
    public boolean collidesWith(Sprite sprite) {
        return (getBounds().intersects(sprite.getBounds()));
    }

    //Getters for image and display variables
    public JFrame frame() { return image.frame; }
    public Graphics2D graphics() { return image.g2d; }
    public Image image() { return image.image; }
    public ImageEntity getEntity() { return image; }

    //Set the imageEntity image variable
    public void setImage(Image image) { this.image.setImage(image); }
    
    /****************************************************
     * Get or Set the sprite Type variable
     * 
     * @return - The sprite TYPE
     *****************************************************/
    public TYPE spriteType() { return sprType; }
    public void setSpriteType(TYPE type) { sprType = type; }
    
}