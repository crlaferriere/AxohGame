/****************************************************************************************************
 * @author Travis R. Dewitt
 * @version 0.53
 * Date: June 14, 2015
 * 
 * 
 * Title: Judgement(The Game)
 * Description: This class extends 'Game.java' in order to run a 2D game with specificly defined
 *  sprites, animatons, and actions.
 *  
 * 
 * This work is licensed under a Attribution-NonCommercial 4.0 International
 * CC BY-NC-ND license. http://creativecommons.org/licenses/by-nc/4.0/
 ****************************************************************************************************/
//Package name
package axohEngine2;

//Imports
import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import axohEngine2.entities.AnimatedSprite;
import axohEngine2.entities.Camera;
import axohEngine2.entities.Enemy;
import axohEngine2.entities.Hero;
import axohEngine2.entities.ImageEntity;
import axohEngine2.entities.Mob;
import axohEngine2.entities.SpriteSheet;
import axohEngine2.map.Map;
import axohEngine2.map.Tile;
import axohEngine2.project.InGameMenu;
import axohEngine2.project.MapDatabase;
import axohEngine2.project.OPTION;
import axohEngine2.project.State;
import axohEngine2.project.TYPE;
import axohEngine2.project.TitleMenu;
import axohEngine2.util.RectangleCollider2D;
import axohEngine2.util.Vector2D;

//Start class by also extending the 'Game.java' engine interface
public class Judgement extends Game {
	//For serializing (The saving system)
	private static final long serialVersionUID = 1L;
	
	/****************** Variables **********************/
	
	//--------- Miscelaneous ---------
	//booleans - A way of detecting a pushed key in game
	//random - Use this to generate a random number
	//State - Game States used to show specific info ie. pause/running
	//option - In game common choices at given times
	//Fonts - Variouse font sizes in the Arial style for different in game text
	boolean keyLeft, keyRight, keyUp, keyDown, keyInventory, keyAction, 
		keyBack, keyEnter, keySpace, keyArrowLeft, keyArrowRight, keyArrowUp, keyArrowDown;
	Random random = new Random();
	OPTION option;
	private Font simple = new Font("Arial", Font.PLAIN, 72);
	//private Font simple2 = new Font("TrueType", Font.TRUETYPE_FONT,50);
	private Font bold = new Font("Arial", Font.BOLD, 72);
	private Font bigBold = new Font("Arial", Font.BOLD, 96);
		
	//--------- Player and scale ---------
	//scale - All in game art is 16 x 16 pixels, the scale is the multiplier to enlarge the art and give it the pixelated look
	//mapX/mapY - Location of the camera on the map
	//playerX/playerY - Location of the player on the map
	//npcX/npcY - Location of the NPC on the map
	//startPosX/startPosY - Starting position of the player in the map
	//playerSpeed - How many pixels the player moves in a direction each update when told to
	private int scale;
	public Camera camera;
	private boolean camFollow = true;
	
	//----------- Map and input --------
	//currentMap - The currently displayed map the player can explore
	//currentOverlay - The current overlay which usually contains houses, trees, pots, etc.
	//mapBase - The database which contains all variables which pertain to specific maps(NPCs, monsters, chests...)
	//inputWait - How long the system waits for after an action is done on the keyboard
	//confirmUse - After some decisions are made, a second question pops up, true equals continue action from before.
	private Map currentMap;
	private Map currentOverlay;
	private MapDatabase mapBase;
	private int inputWait = 5;
	private boolean confirmUse = false;
	
	//----------- Menus ----------------
	//inX/inY - In Game Menu starting location for default choice highlight
	//inLocation - Current choice in the in game menu represented by a number, 0 is the top
	//sectionLoc - Current position the player could choose after the first choice has been made in the in game menu(Items -> potion), 0 is the top.
	//titleX, titleY, titleX2, titleY2 - Positions for specific moveable sprites at the title screen (arrow/highlight).
	//titleLocation - Current position the player is choosing in the title screen(File 1, 2, 3) 0 is top
	//currentFile - Name of the currently loaded file
	//wasSaving/wait/waitOn - Various waiting variables to give the player time to react to whats happening on screen
	private int inX = 90, inY = 90;
	private int inLocation;
	private int sectionLoc;
	private int titleX = 530, titleY = 610;
	private int titleX2 = 340, titleY2 = 310;
	private int titleLocation;
	private String currentFile;
	private boolean wasSaving = false;
	private int wait;
	private boolean waitOn = false;
	private int attackCounter; 
	private int sheathCounter; 
	private int spawnRate = 10;
	public int score = 0;
	private LinkedList<Enemy> enemies;
	//----------- Game  -----------------
	//SpriteSheets (To be split in to multiple smaller sprites)
	SpriteSheet extras1;
	SpriteSheet extras1fist;
	SpriteSheet zombie;//	SpriteSheet zombie;
	SpriteSheet mainCharacter;
	
	//ImageEntitys (Basic pictures)
	ImageEntity inGameMenu;
	ImageEntity titleMenu;
	ImageEntity titleMenu2;
    ImageEntity controls;
    
	//Menu classes
	TitleMenu title;
	InGameMenu inMenu;
	
	//Animated sprites
	AnimatedSprite titleArrow;
	
	//Player and NPCs
	Mob player;
	Mob randomNPC;
	
	
	/*********************************************************************** 
	 * Constructor
	 * 
	 * Set up the super class Game and set the window to appear
	 **********************************************************************/
	public Judgement() {
		super(130, SCREENWIDTH, SCREENHEIGHT);
		camera = new Camera();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//plays music file at the beginning of the game. 
		//The music file must be .wav file
		try {
			JavaAudioPlaySoundExample("/background.wav"); 
			}
		catch(Exception ex) {
			
		}
			
	}
	
	/****************************************************************************
	 * Inherited Method
	 * This method is called only once by the 'Game.java' class, for startup
	 * Initialize all non-int variables here
	 *****************************************************************************/
	@Override
	void gameStartUp() {
		/****************************************************************
		 * The "camera" is the mapX and mapY variables. These variables 
		 * can be changed in order to move the map around, simulating the
		 * camera. The player is moved around ONLY when at an edge of a map,
		 * otherwise it simply stays at the center of the screen as the "camera"
		 * is moved around.
		 ****************************************************************/
		//****Initialize Misc Variables
		
		setGameState(State.TITLE);
		option = OPTION.NONE;
		scale = 4;
		enemies = new LinkedList<Enemy>();
		//****Initialize spriteSheets*********************************************************************
		//extras1 = new SpriteSheet("/textures/extras/extras1.png", 8, 8, 32, scale);
		extras1fist = new SpriteSheet("/textures/extras/extras1fist.png", 8, 8, 32, scale); 
		zombie = new SpriteSheet("/textures/characters/elephant.png", 8, 8, 32, 6); //Zombie sprite!! 
		mainCharacter = new SpriteSheet("/textures/characters/mainCharacter.png", 8, 8, 32, scale);

		//****Initialize and setup AnimatedSprites*********************************************************
		titleArrow = new AnimatedSprite(this, graphics(), extras1fist, 0, "arrow");
		titleArrow.loadAnim(4, 10);
		sprites().add(titleArrow);
		
		//****Initialize and setup image entities**********************************************************
		inGameMenu = new ImageEntity(this);
		titleMenu = new ImageEntity(this);
		titleMenu2 = new ImageEntity(this);
        controls = new ImageEntity(this);
        
        inGameMenu.load("/menus/ingamemenu02.png"); //inventory menu
		titleMenu.load("/menus/titlemenu1new.png"); //Bro and Arrow title menu
		titleMenu2.load("/menus/titlemenu2new.png"); //Bro and Arrow load menu
		controls.load("/menus/controls1new.png"); //Bro and Arrow ccontrol menu
        
        //*****Initialize Menus***************************************************************************
		title = new TitleMenu(titleMenu, titleMenu2, controls, titleArrow, SCREENWIDTH, SCREENHEIGHT, simple, bold, bigBold);
		inMenu = new InGameMenu(inGameMenu, SCREENWIDTH, SCREENHEIGHT);
		
		//****Initialize and setup Mobs*********************************************************************
		player = new Hero(this, graphics(), mainCharacter, 40, "mainC");
		player.setMultBounds(6, 50, 95, 37, 88, 62, 92, 62, 96);
		player.setMoveAnim(32, 48, 40, 56, 3, 8);
		player.addAttack("sword", 0, 5);
		player.getAttack("sword").addMovingAnim(17, 25, 9, 1, 3, 8);
		player.getAttack("sword").addAttackAnim(20, 28, 12, 4, 3, 6);
		player.getAttack("sword").addInOutAnim(16, 24, 8, 0, 1, 10);
		player.setCurrentAttack("sword"); //Starting attack
		player.setHealth(35); //If you change the starting max health, dont forget to change it in inGameMenu.java max health also
		player.setSpeed(6.0);
		sprites().add(player);
		player.setLoc(1250,500);
		//player.boundsOffset = new Point(200,200);
		
		
		
		
		//*****Initialize and setup first Map******************************************************************
		mapBase = new MapDatabase(this, graphics(), scale);
		//Get Map from the database
		for(int i = 0; i < mapBase.maps.length; i++){
			if(mapBase.getMap(i) == null) continue;
			if(mapBase.getMap(i).mapName() == "arena") currentMap = mapBase.getMap(i);
			if(mapBase.getMap(i).mapName() == "arenaO") currentOverlay = mapBase.getMap(i);
			//remember to edit these lines of code as well as the map sprites in the MapDatabase.java class
			/*if(mapBase.getMap(i).mapName() == "city") currentMap = mapBase.getMap(i); 
			if(mapBase.getMap(i).mapName() == "cityO") currentOverlay = mapBase.getMap(i);	
			if(mapBase.getMap(i).mapName() == "houses") currentMap = mapBase.getMap(i);
			if(mapBase.getMap(i).mapName() == "housesO") currentOverlay = mapBase.getMap(i);
			if(mapBase.getMap(i).mapName() == "insideHouse") currentMap = mapBase.getMap(i);
			if(mapBase.getMap(i).mapName() == "insideHouseO") currentOverlay = mapBase.getMap(i);
			if(mapBase.getMap(i).mapName() == "QU") currentMap = mapBase.getMap(i);
			if(mapBase.getMap(i).mapName() == "QUO") currentOverlay = mapBase.getMap(i);*/
		}
		//Add the tiles from the map to be updated each system cycle
		for(int i = 0; i < currentMap.getWidth() * currentMap.getHeight(); i++){
			addTile(currentMap.accessTile(i));
			addTile(currentOverlay.accessTile(i));
			if(currentMap.accessTile(i).hasMob()) sprites().add(currentMap.accessTile(i).mob());
			if(currentOverlay.accessTile(i).hasMob()) sprites().add(currentOverlay.accessTile(i).mob());
			//currentMap.accessTile(i).getEntity().setX(-300);
			//currentOverlay.accessTile(i).getEntity().setX(-300);
		}
		
		requestFocus(); //Make sure the game is focused on
		start(); //Start the game loop
	}
	
	/**************************************************************************** 
	 * Inherited Method
	 * Method that updates with the default 'Game.java' loop method
	 * Add game specific elements that need updating here
	 *****************************************************************************/
	@Override
	void gameTimedUpdate() {
		checkInput(); //Check for user input
		
		// Makes the enemies follow the player based on their location
		for (int i = 0; i < enemies.size(); i++ ){
		double dx = player.getXLoc() - enemies.get(i).getXLoc(); 
		double dy = player.getYLoc() - enemies.get(i).getYLoc(); 
		dx = Math.min(Math.abs(dx),  player.getSpeed() * 0.5) *Math.signum(dx); 
		dy = Math.min(Math.abs(dy),  player.getSpeed() * 0.5) *Math.signum(dy); 
		enemies.get(i).move(dx, dy);
		}
		
		//Update certain specifics based on certain game States
		if(getGameState() == State.TITLE) title.update(option, titleLocation); //Title Menu update
		if(getGameState() == State.INGAMEMENU) inMenu.update(option, isSaved(), sectionLoc, player.health()); //In Game Menu update
		updateData(currentMap, currentOverlay, (int) player.getXLoc(), (int) player.getYLoc()); //Update the current file data for saving later
		if (camFollow) {
			camera.track(player);
			attackCounter++; 
			sheathCounter++;
			score++;
			//camera.setLocation(player.getXLoc(), player.getYLoc());
			//camera.setLocation(player.getXLoc() + player.getSpriteSize() / 2 - CENTERX, player.getYLoc() + player.getSpriteSize() / 2 - CENTERY);
		}
		// Get rid of this spamtastic logging...
		// System.out.println(frameRate()); //Print the current framerate to the console
		if(waitOn) wait--;
		//System.out.println(playerX + " " + playerY + " " + mapX + " " + mapY); //print out player coordinates
		// System.out.println(npcX + " " + npcY); //print out NPC coordinates
	
	}
	
	/**
	 * Inherited Method
	 * Obtain the 'graphics' passed down by the super class 'Game.java' and render objects on the screen
	 */
	@Override
	void gameRefreshScreen() {		
		/*********************************************************************
		* Rendering images uses the java class Graphics2D
		* Each frame the screen needs to be cleared and an image is setup as a back buffer which is brought 
		* to the front as a full image at the time it is needed. This way the screen is NOT rendered pixel by 
		* pixel in front of the user, which would have made a strange lag effect.
		* 
		* 'graphics' objects have parameters that can be changed which effect what it renders, two are font and color
		**********************************************************************/
		Graphics2D g2d = graphics();
		g2d.clearRect(0, 0, SCREENWIDTH, SCREENHEIGHT);
		g2d.setFont(simple);
		
		//GUI rendering for when a specific State is set, only specific groups of data is drawn at specific times
		if(getGameState() == State.GAME) {
			//Render the map, the player, any NPCs or Monsters and the player health or status
			int camX = - (int) camera.getX();
			int camY = - (int) camera.getY();
			currentMap.render(this, g2d, 0, 0);
			currentOverlay.render(this, g2d, 0, 0);
			//player.move(CENTERX + (int) (player.getXLoc() - camera.getX()), CENTERY + (int) (player.getYLoc() - camera.getY()));
			player.renderMob();
			for (int i = 0; i < enemies.size(); i++)
				enemies.get(i).renderMob();
			
			g2d.setColor(Color.GREEN);
			g2d.drawString("Health: " + inMenu.getHealth(), CENTERX - 650, CENTERY - 370);
			g2d.setColor(Color.WHITE);
			g2d.drawString("Score: " + score/60, CENTERX - 650, CENTERY - 310);
			//g2d.setColor(Color.RED);
			//g2d.drawString("NPC health: " + currentOverlay.accessTile(98).mob().health(), CENTERX - 650, CENTERY - 250);
		}
		if(getGameState() == State.INGAMEMENU){
			//Render the in game menu and specific text
			inMenu.render(this, g2d, inX, inY);
			g2d.setColor(Color.red);
			if(confirmUse) g2d.drawString("Use this?", CENTERX, CENTERY);
		}
		if(getGameState() == State.TITLE) {
			//Render the title screen
			title.render(this, g2d, titleX, titleY, titleX2, titleY2);
		}
		
		//Render save time specific writing
		if(option == OPTION.SAVE){
			drawString(g2d, "Are you sure you\n  would like to save?", 660, 400);
		}
		if(wasSaving && wait > 0) {
			g2d.drawString("Game Saved!", 700, 500);
		}
	}
	
	/*******************************************************************
	 * The next four methods are inherited
	 * Currently these methods are not being used, but they have
	 * been set up to go off at specific times in a game as events.
	 * Actions that need to be done during these times can be added here.
	 ******************************************************************/
	@Override
	void gameShutDown() {
	}

	@Override
	void spriteUpdate(AnimatedSprite sprite) {
	}

	@Override
	void spriteDraw(AnimatedSprite sprite) {
	}

	@Override
	void spriteDying(AnimatedSprite sprite) {
	}

	/*************************************************************************
	 * @param AnimatedSprite
	 * @param AnimatedSprite
	 * @param int
	 * @param int
	 * 
	 * Inherited Method
	 * Handling for when a SPRITE contacts a SPRITE
	 * 
	 * hitDir is the hit found when colliding on a specific bounding box on spr1 and hitDir2
	 * is the same thing applied to spr2
	 * hitDir is short for hit direction which can give the data needed to move the colliding sprites
	 * hitDir is a number between and including 0 and 3, these assignments are taken care of in 'Game.java'.
	 * What hitDir is actually referring to is the specific hit box that is on a multi-box sprite.
	 *****************************************************************************/
	/*void spriteCollision(AnimatedSprite spr1, AnimatedSprite spr2, int hitDir, int hitDir2) {
		//Get the smallest possible overlap between the two problem sprites
		double leftOverlap = (spr1.getBoundX(hitDir) + spr1.getBoundSize() - spr2.getBoundX(hitDir2));
		double rightOverlap = (spr2.getBoundX(hitDir2) + spr2.getBoundSize() - spr1.getBoundX(hitDir));
		double topOverlap = (spr1.getBoundY(hitDir) + spr1.getBoundSize() - spr2.getBoundY(hitDir2));
		double botOverlap = (spr2.getBoundY(hitDir2) + spr2.getBoundSize() - spr1.getBoundY(hitDir));
		double smallestOverlap = Double.MAX_VALUE; 
		double shiftX = 0;
		double shiftY = 0;

		if(leftOverlap < smallestOverlap) { //Left
			smallestOverlap = leftOverlap;
			shiftX -= leftOverlap; 
			shiftY = 0;
		}
		if(rightOverlap < smallestOverlap){ //right
			smallestOverlap = rightOverlap;
			shiftX = rightOverlap;
			shiftY = 0;
		}
		if(topOverlap < smallestOverlap){ //up
			smallestOverlap = topOverlap;
			shiftX = 0;
			shiftY -= topOverlap;
		}
		if(botOverlap < smallestOverlap){ //down
			smallestOverlap = botOverlap;
			shiftX = 0;
			shiftY = botOverlap;
		}

		//Handling very specific collisions
		if(spr1.spriteType() == TYPE.PLAYER && getGameState() == State.GAME){
			if(spr2 instanceof Mob) ((Mob) spr2).stop();
			
			//This piece of code is commented out because I still need the capability of getting a tile from an xand y position
			//if(((Mob) spr1).attacking() && currentOverlay.getFrontTile((Mob) spr1, playerX, playerY, CENTERX, CENTERY).getBounds().intersects(spr2.getBounds())){
				//((Mob) spr2).takeDamage(25);
				//TODO: inside of take damage should be a number dependant on the current weapon equipped, change later
			//}
			
			//Handle simple push back collision
			if(playerX != 0) playerX -= shiftX;
			if(playerY != 0) playerY -= shiftY;
			if(playerX == 0) mapX -= shiftX;
			if(playerY == 0) mapY -= shiftY;
		}
	}*/
	
	void attack(String normalAttack, int MagicDam, int StrengthDeam) {
		
	}
	
	void addHitbox(int x, int y, int size) {
		
	}
	
	@Override
	protected void handleCollisions() {
		for (AnimatedSprite a : sprites()) {
			if (a instanceof Mob) {
				Mob mob = (Mob) a;
				//System.out.println(mob.getPosition());

				// Vector2D v = new Vector2D();
				for (Tile b : tiles()) {
					if (b.isSolid()) {
						double finalX = mob.getXLoc();
						double finalY = mob.getYLoc();
						double right = Math.min(finalX + mob.collider.getWidth(), b.getXLoc() + (double) b.getSpriteSize());
						double left = Math.max(finalX, b.getXLoc());
						double down = Math.min(finalY + mob.collider.getHeight(), b.getYLoc() + (double) b.getSpriteSize());
						double up = Math.max(finalY, b.getYLoc());
						double overlapX = right - left;
						double overlapY = down - up;
						if (overlapX > 0 && overlapY > 0) {
							/*Vector2D normal = null;
							double normalX = 0;
							double normalY = 0;
							try {
								normal = new RectangleCollider2D(b.getXLoc() - finalX, b.getYLoc() - finalY, (double) b.getSpriteSize(), (double) b.getSpriteSize()).rectCast(mob.collider, mob.getXVel(), mob.getYVel()).getNormal();
								normalX = normal.getX();
								normalY = normal.getY();
							} catch(Exception e) {continue;}
							System.out.println(normal);
							double centerX = finalX + mob.collider.getCenterX() * 0.5;
							double centerY = finalY + mob.collider.getCenterY() * 0.5;
							double tileCenterX = b.getXLoc() + (double) b.getSpriteSize() * 0.5;
							double tileCenterY = b.getYLoc() + (double) b.getSpriteSize() * 0.5;
							double angle = Math.atan2(centerY - tileCenterY, centerX - tileCenterX);
							double normalX = Math.cos(angle);
							double normalY = Math.sin(angle);
							if (Math.abs(normalX) > 1.0 / Math.sqrt(2.0)) {
								normalX = normalX > 0.0 ? 1.0 : -1.0;
							} else {
								normalX = 0.0;
							}
							if (Math.abs(normalY) > 1.0 / Math.sqrt(2.0)) {
								normalY = normalY > 0.0 ? 1.0 : -1.0;
							} else {
								normalY = 0.0;
							}*/
							double normalX = 0;
							double normalY = 0;
							if (mob.getXLoc() <= mob.collider.getWidth() || mob.getXLoc() >= 2432 - mob.collider.getWidth()) {
								normalX = mob.getXLoc() <= mob.collider.getWidth() ? 1 : -1;
							}
							if (mob.getYLoc() <= mob.collider.getHeight() || mob.getYLoc() >= 1024 - mob.collider.getHeight()) {
								normalY = mob.getYLoc() <= mob.collider.getHeight() ? 1 : -1;
							}
							double offX = overlapX * normalX;
							double offY = overlapY * normalY;
							double adjX = 0;
							double adjY = 0;
							if (Math.abs(mob.getYVel()) > 0) {
								adjX = mob.getXVel() * overlapY / mob.getYVel() * normalY;
							}
							if (Math.abs(mob.getXVel()) > 0) {
								adjY = mob.getYVel() * overlapX / mob.getXVel() * normalX;
							}
							mob.setLoc(finalX + offX + adjX, finalY + offY + adjY);
							mob.move(mob.getXVel() * Math.abs(normalY), mob.getYVel() * Math.abs(normalX));
							//mob.velocity.setX(mob.getXVel() * Math.abs(normalY) * 0.1);
							//mob.velocity.setY(mob.getYVel() * Math.abs(normalX) * 0.1);
							//mob.setLoc(mob.getXLoc() + mob.getXVel() * Math.abs(normalY) * 0.1, mob.getYLoc() + mob.getYVel() * Math.abs(normalX) * 0.1);
							//v.add(overlapX * Math.abs(normalY), overlapY * Math.abs(normalX));
							//mob.setLoc(mob.getXLoc() + offX, mob.getYLoc() + offY);
						}
					}
				}
				//mob.setLoc(mob.getXLoc() + v.getX(), mob.getYLoc() + v.getY());
			}
		}
	}

	
	/***********************************************************************
	* @param AnimatedSprite
	* @param Tile
	* @param int
	* @param int
	* 
	* Inherited Method
	* Set handling for when a SPRITE contacts a TILE, this is handy for
	* dealing with Tiles which contain Events. When specifying a new
	* collision method, check for the type of sprite and whether a tile is
	* solid or breakable, both, or even if it contains an event. This is
	* mandatory because the AxohEngine finds details on collision and then 
	* returns it for specific handling by the user.
	* 
	* For more details on this method, refer to the spriteCollision method above
	*************************************************************************/
	/*void tileCollision(AnimatedSprite spr, Tile tile, int hitDir, int hitDir2) {
		double leftOverlap = (spr.getBoundX(hitDir) + spr.getBoundSize() - tile.getBoundX(hitDir2));
		double rightOverlap = (tile.getBoundX(hitDir2) + tile.getBoundSize() - spr.getBoundX(hitDir));
		double topOverlap = (spr.getBoundY(hitDir) + spr.getBoundSize() - tile.getBoundY(hitDir2));
		double botOverlap = (tile.getBoundY(hitDir2) + tile.getBoundSize() - spr.getBoundY(hitDir));
		double smallestOverlap = Double.MAX_VALUE; 
		double shiftX = 0;
		double shiftY = 0;

		if(leftOverlap < smallestOverlap) { //Left
			smallestOverlap = leftOverlap;
			shiftX += leftOverlap; 
			shiftY = 0;
		}
		if(rightOverlap < smallestOverlap){ //right
			smallestOverlap = rightOverlap;
			shiftX = -rightOverlap;
			shiftY = 0;
		}
		if(topOverlap < smallestOverlap){ //up
			smallestOverlap = topOverlap;
			shiftX = 0;
			shiftY += topOverlap;
		}
		if(botOverlap < smallestOverlap){ //down
			smallestOverlap = botOverlap;
			shiftX = 0;
			shiftY = -botOverlap;
		}
		
		//Deal with a tiles possible event property
		if(tile.hasEvent()){
			if(spr.spriteType() == TYPE.PLAYER) {
				//Warp Events(Doors)
				if(tile.event().getEventType() == TYPE.WARP) {
					tiles().clear();
					sprites().clear();
					sprites().add(player);
					//Get the new map
					for(int i = 0; i < mapBase.maps.length; i++){
						 if(mapBase.getMap(i) == null) continue;
						 if(tile.event().getMapName() == mapBase.getMap(i).mapName()) currentMap = mapBase.getMap(i);
						 if(tile.event().getOverlayName() == mapBase.getMap(i).mapName()) currentOverlay = mapBase.getMap(i);
					}
					//Load in the new maps Tiles and Mobs
					for(int i = 0; i < currentMap.getWidth() * currentMap.getHeight(); i++){
						addTile(currentMap.accessTile(i));
						addTile(currentOverlay.accessTile(i));
						if(currentMap.accessTile(i).hasMob()) sprites().add(currentMap.accessTile(i).mob());
						if(currentOverlay.accessTile(i).hasMob()) sprites().add(currentOverlay.accessTile(i).mob());
					}
					//Move the player to the new position
					playerX = tile.event().getNewX();
					playerY = tile.event().getNewY();
					mapX = tile.event().getNewMapX();
					mapY = tile.event().getNewMapY();
				}	
			} //end warp
			//Item exchange event
			if(spr.spriteType() == TYPE.PLAYER && tile.event().getEventType() == TYPE.ITEM && keyAction){
				if((tile._name).equals("chest")) tile.setFrame(tile.getSpriteNumber() + 1); //Chests should have opened and closed version next to each other
				inMenu.addItem(tile.event().getItem()); //Add item to inventory
				tile.endEvent();
			}
		}//end check events
		
		//If the tile is solid, move the player off of it and exit method immediately
		if(spr.spriteType() == TYPE.PLAYER && tile.solid() && getGameState() == State.GAME) {
			playerX += shiftX;
			playerY += shiftY;
			//if(playerX != 0) playerX -= shiftX;
			//if(playerY != 0) playerY -= shiftY;
			//if(playerX == 0) mapX -= shiftX;
			//if(playerY == 0) mapY -= shiftY;
			return;
		}
		//If an npc is intersecting a solid tile, move it off
		if(spr.spriteType() != TYPE.PLAYER && tile.solid() && getGameState() == State.GAME){
			if(spr instanceof Mob) {
				((Mob) spr).setLoc((int)shiftX, (int)shiftY);
				((Mob) spr).resetMovement();
			}
		}
	}//end tileCollision method*/
	
	/*****************************************************************
	 * @param int
	 * @param int
	 * 
	 *Method to call which moves the player. The player never moves apart from the map
	 *unless the player is at an edge of the generated map. Also, to simulate the movement
	 *of the space around the player like that, the X movement is flipped. 
	 *Which means to move right, you subtract from the X position.
	 ******************************************************************/
	void movePlayer(double xa, double ya) {
		player.move(xa, ya);
		/*if(xa > 0) {
			if(mapX + xa < currentMap.getMinX() && playerX < playerSpeed && playerX > -playerSpeed) mapX += xa;
			else playerX += xa; //left +#
		}
		if(xa < 0) {
			if(mapX + xa > currentMap.getMaxX(SCREENWIDTH) && playerX < playerSpeed && playerX > -playerSpeed) mapX += xa;
			else playerX += xa; //right -#
		}
		if(ya > 0) {
			if(mapY + ya < currentMap.getMinY() && playerY < playerSpeed && playerY > -playerSpeed) mapY += ya;
			else playerY += ya; //up +#
		}
		if(ya < 0) {
			if(mapY + ya > currentMap.getMaxY(SCREENHEIGHT) && playerY < playerSpeed && playerY > -playerSpeed) mapY += ya;
			else playerY += ya; //down -#
		}*/
	}
	
	/**********************************************************
	 * The Depths of Judgement Lies Below
	 * 
	 *             Key events - Mouse events
	 *                            
	 ***********************************************************/
	
	/****************************************************************
	 * Check specifically defined key presses which do various things
	 ****************************************************************/

	/*JavaAudioPlaySoundExample method enables the BGM to play at the start of the game.
	 *If you want to change when the music starts/stop refer to line 159.
	 *Make sure to import the exceptions, as well as declaring variables
	 */
	
	Clip clip;
	public void JavaAudioPlaySoundExample (String file)throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
		
		try {
			Thread.currentThread().getContextClassLoader();
			InputStream is = getClass().getResourceAsStream(file);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(is);
			
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch(Exception ex) {
			System.out.println("Audio Error");
			ex.printStackTrace();
			
		}
	}
	public void checkInput() {
		double xa = 0;
		double ya = 0;
		
	
		/********************************************
		 * Special actions for In Game
		 *******************************************/
		if(getGameState() == State.GAME && inputWait < 0) { 
			//A or left arrow(move left)
			if(keyLeft) {
				xa -= player.getSpeed();
				// player.updatePlayer(keyLeft, keyRight, keyUp, keyDown);
			}
			//D or right arrow(move right)
			if(keyRight) {
				xa += player.getSpeed();
				// player.updatePlayer(keyLeft, keyRight, keyUp, keyDown);
			}
			//W or up arrow(move up)
			if(keyUp) {
				ya -= player.getSpeed();
				// player.updatePlayer(keyLeft, keyRight, keyUp, keyDown);
			}
			//S or down arrow(move down)
			if(keyDown) {
				ya += player.getSpeed();
				// player.updatePlayer(keyLeft, keyRight, keyUp, keyDown);
			}
			
			//No keys are pressed
			if(!keyLeft && !keyRight && !keyUp && !keyDown) {
				// player.updatePlayer(keyLeft, keyRight, keyUp, keyDown);
			}
			movePlayer(xa, ya);
		
			//I(Inventory)
			if(keyInventory) {
				setGameState(State.INGAMEMENU);
				inputWait = 10;
			}
			
			//SpaceBar(action button)
			if(keySpace) {
				player.inOutItem();
				inputWait = 10;
			}
			
			
		}//end in game choices
		
		/*****************************************
		 * Special actions for the Title Menu
		 *****************************************/
		if(getGameState() == State.TITLE && inputWait < 0){
			//For when no initial choice has been made
			if(option == OPTION.NONE){
				//S or down arrow(Change selection)
				if(keyArrowDown && titleLocation < 1) {
					//titleX -= 105;
					titleY += 100;
					titleLocation++;
					inputWait = 5;
				}
				//W or up arrow(Chnage selection
				if(keyArrowUp && titleLocation > -1){
					//titleX += 105;
					titleY -= 100;
					titleLocation--;
					inputWait = 5;
				}
				//Enter key(Make a choice)
				if(keyEnter) {
					if(titleLocation == 0){
						option = OPTION.NEWGAME;
						titleLocation = 0;
						inputWait = 10;
						keyEnter = false;
					}
					if(titleLocation == 1){
						option = OPTION.LOADGAME;
						titleLocation = 0;
						inputWait = 5;
						keyEnter = false;
					}
                    if(titleLocation == -1){
                        option = OPTION.CONTROLS;
                        titleLocation = 0;
                        inputWait = 5;
                        keyEnter = false;
                    }
				}
			}//end option none
			
			//After choosing an option
			if(option == OPTION.NEWGAME || option == OPTION.LOADGAME || option == OPTION.CONTROLS){
				//Backspace(Exit choice)
				if(keyBack && !title.isGetName()){
					if(option == OPTION.NEWGAME) titleLocation = 0;
					if(option == OPTION.LOADGAME) titleLocation = 1;
                    if(option == OPTION.CONTROLS) titleLocation = -1;
                    inputWait = 5;
					titleX2 = 340;
					titleY2 = 310;
					option = OPTION.NONE;
				}
				//S or down arrow(Change selection)
				if(keyArrowDown && titleLocation < 2 && !title.isGetName()) {
					titleLocation++;
					titleY2 += 160;
					inputWait = 7;
				}
				//W or up arrow(Change selection)
				if(keyArrowUp && titleLocation > 0 && !title.isGetName()) {
					titleLocation--;
					titleY2 -= 160;
					inputWait = 7;
				}
				//Enter key(Make a choice)
			//	System.out.println(title.isGetName());
				
				if(keyEnter && !title.isGetName()) {
					
					if(option == OPTION.NEWGAME) {
						if(title.files() != null){ //Make sure the location of a new game is greater than previous ones(Not overwriting)
							
							if(title.files().length - 1 < titleLocation) {
								title.enter();
								titleX2 += 40;
								inputWait = 5;
								
							}
						}
						if(title.files() == null) { //Final check if there are no files made yet, to make the file somewhere
							title.enter();
							titleX2 += 40;
							inputWait = 5;
						}
					}
					//Load the currently selected file
					if(option == OPTION.LOADGAME) {
						currentFile = title.enter();
						if(currentFile != "") { //File is empty
							// loadGame();
							inputWait = 5;
							option = OPTION.NONE;
							setGameState(State.GAME);
						}
					}
				}//end enter key
				
				//The following is for when a new file needs to be created - Typesetting
				if(title.isGetName() == true) {
					title.setFileName(currentChar);
					currentChar = '\0'; //null
					//Back space(Delete last character)
					if(keyBack) {
						title.deleteChar();
						inputWait = 5;
					}
					//Back space(exit name entry if name has no characters)
					if(keyBack && title.getFileName().length() == 0) {
						title.setGetName(false);
						titleX2 -= 40;
						inputWait = 5;
					}
					//Enter key(Write the file using the currently typed name and save it)
					if(keyEnter && title.getFileName().length() > 0) {
						save.newFile(title.getFileName());
						title.setGetName(false);
						currentFile = title.getFileName();
						option = OPTION.NONE;
						setGameState(State.GAME);
						title.clearFileName();
						
						
					}
				}//end get name
			}//end new/load option
		}//end title State
		
		
		/******************************************
		 * Special actions for In Game Menu
		 ******************************************/
		if(getGameState() == State.INGAMEMENU && inputWait < 0) {
			//I(Close inventory)
			if(keyInventory) {
				setGameState(State.GAME);
				option = OPTION.NONE;
				inLocation = 0;
				inY = 90;
				inputWait = 8;
			}
			//No option is chosen yet
			if(option == OPTION.NONE){ 
				if(wait == 0) wasSaving = false;
				//W or up arrow(Move selection)
				if(keyArrowUp) {
					if(inLocation > 0) {
						inY -= 108;
						inLocation--;
						inputWait = 10;
					}
				}
				//S or down arrow(move selection)
				if(keyArrowDown) {
					if(inLocation < 5) {
						inY += 108;
						inLocation++;
						inputWait = 10;
					}
				}
				//Enter key(Make a choice)
				if(keyEnter) {
					if(inLocation == 0){
						option = OPTION.ITEMS;
						inputWait = 5;
					}
					if(inLocation == 1){
						option = OPTION.EQUIPMENT;
						inputWait = 5;
					}
					if(inLocation == 2){
						option = OPTION.MAGIC;
						inputWait = 5;
					}
					if(inLocation == 3){
						option = OPTION.STATUS;
						inputWait = 5;
					}
					if(inLocation == 4){
						option = OPTION.SAVE;
						inputWait = 20;
					}
					if(inLocation == 5){
						option = OPTION.QUIT;
					}
					keyEnter = false;
				}
			}
			
			//Set actions for specific choices in the menu
			//Items
			if(option == OPTION.ITEMS) {
				//W or up arrow(move selection)
				if(keyArrowUp){
					if(sectionLoc == 0) inMenu.loadOldItems();
					if(sectionLoc - 1 != -1) sectionLoc--;
					inputWait = 8;
				}
				//S or down arrow(move selection)
				if(keyArrowDown) {
					if(sectionLoc == 3) inMenu.loadNextItems();
					if(inMenu.getTotalItems() > sectionLoc + 1 && sectionLoc < 3) sectionLoc++;
					inputWait = 8;
				}
				//Enter key(Make a choice)
				if(keyEnter){
					if(confirmUse) {
						inMenu.useItem(); //then use item
						confirmUse = false;
						keyEnter = false;
					}
					if(inMenu.checkCount() > 0 && keyEnter) confirmUse = true;
					inputWait = 10;
				}
				//Back space(Go back on your last choice)
				if(keyBack) confirmUse = false;
			}
			
			//Equipment
			if(option == OPTION.EQUIPMENT) {
				//W or up arrow(move selection)
				if(keyArrowUp){
					if(sectionLoc == 0) inMenu.loadOldItems();
					if(sectionLoc - 1 != -1) sectionLoc--;
					inputWait = 8;
				}
				//S or down arrow(move selection)
				if(keyArrowDown) {
					if(sectionLoc == 3) inMenu.loadNextEquipment();
					if(inMenu.getTotalEquipment() > sectionLoc + 1 && sectionLoc < 3) sectionLoc++;
					inputWait = 8;
				}
			}
			
			//Saving
			if(option == OPTION.SAVE){
				//Key enter(Save the file)
				if(keyEnter){
					save.saveState(currentFile, data());
					inputWait = 20;
					wait = 200;
					waitOn = true;
					wasSaving = true;
					keyEnter = false;
					option = OPTION.NONE;
				}
			}
			if(option == OPTION.QUIT){
				if(keyEnter){
					option = OPTION.QUIT;

					setGameState(State.TITLE);
					keyEnter = false;
					option = OPTION.NONE;
					
					//resets the title arrow's positions and animation
					titleX = 530;
					titleY = 610;
					titleX2 = 340;
					titleY2 = 310;
					titleLocation = 0;
			
					titleArrow.loadAnim(4, 10);
					sprites().add(titleArrow);
					
					
				}
			}
			
			
			//Backspace(if a choice has been made, this backs out of it)
			if(keyBack && option != OPTION.NONE) {
				option = OPTION.NONE;
				inMenu.setItemLoc(0);
				sectionLoc = 0;
				inputWait = 8;
			}
			//Backspace(if a choice has not been made, this closes the inventory)
			else if(keyBack && option == OPTION.NONE) {
				setGameState(State.GAME);
				option = OPTION.NONE;
				inLocation = 0;
				sectionLoc = 0;
				inY = 90;
				inputWait = 8;
			}
		}
		inputWait--;
	}
	
	/**
	 * Inherited method
	 * @param keyCode
	 * 
	 * Set keys for a new game action here using a switch Statement
	 * dont forget gameKeyUp
	 */
	@Override
	void gameKeyDown(int keyCode) {
		switch(keyCode) {
	        case KeyEvent.VK_LEFT:
	            keyArrowLeft = true;
	            break;
	        case KeyEvent.VK_A:
	        	keyLeft = true;
	        	break;
	        case KeyEvent.VK_RIGHT:
	            keyArrowRight = true;
	            break;
	        case KeyEvent.VK_D:
	        	keyRight = true;
	        	break;
	        case KeyEvent.VK_UP:
	            keyArrowUp = true;
	            break;
	        case KeyEvent.VK_W:
	        	keyUp = true;
	        	break;
	        case KeyEvent.VK_DOWN:
	            keyArrowDown = true;
	            break;
	        case KeyEvent.VK_S:
	        	keyDown = true;
	        	break;
	        case KeyEvent.VK_I:
	        	keyInventory = true;
	        	break;
	        case KeyEvent.VK_F:
	        	keyAction = true;
		    	if(player.isTakenOut() && attackCounter > 30) {
					player.attack();
					attackCounter = 0; 
				}
		   
		    	// Comment out this uselessness...
		    	//System.out.println("ACTION THROUGH F KEY");
		    	//System.out.println("Entering the battle field...");
		    	//player.addAttack("Normal Attack", 0, 5);
		    	//System.out.println("The player activated " + player.getCurrentAttack() + "!");
		    	//randomNPC.takeDamage(5);
		    	
	        	break;
	        case KeyEvent.VK_ENTER:
	        	keyEnter = true;
		    	camFollow = !camFollow;
	        	break;
	        case KeyEvent.VK_BACK_SPACE:
	        	camera.centerTarget = !camera.centerTarget;
	        	keyBack = true;
	        	break;
	        case KeyEvent.VK_SPACE:
	        if (sheathCounter > 50) {
	        	keySpace = true;
	        	sheathCounter = 0; 
	        	break;
	        }
	    }
	}

	/**
	 * Inherited method
	 * @param keyCode
	 * 
	 * Set keys for a new game action here using a switch Statement
	 * Dont forget gameKeyDown
	 */
	@Override
	void gameKeyUp(int keyCode) {
		switch(keyCode) {
        case KeyEvent.VK_LEFT:
            keyArrowLeft = false;
            break;
        case KeyEvent.VK_A:
        	keyLeft = false;
        	break;
        case KeyEvent.VK_RIGHT:
            keyArrowRight = false;
            break;
        case KeyEvent.VK_D:
        	keyRight = false;
        	break;
        case KeyEvent.VK_UP:
            keyArrowUp = false;
            break;
        case KeyEvent.VK_W:
        	keyUp = false;
        	break;
        case KeyEvent.VK_DOWN:
            keyArrowDown = false;
            break;
        case KeyEvent.VK_S:
        	keyDown = false;
        	break;
        case KeyEvent.VK_I:
	    	keyInventory = false;
	    	break;
	    case KeyEvent.VK_F:
	    	keyAction = false;
	    	break;
	    case KeyEvent.VK_ENTER:
	    	keyEnter = false;
	    	break;
	    case KeyEvent.VK_BACK_SPACE:
	    	keyBack = false;
	    	break;
	    case KeyEvent.VK_SPACE:
	    	keySpace = false;
	    	break;
		}
	}

	/**
	 * Inherited method
	 * Currently unused
	 */
	@Override
	void gameMouseDown() {	
	}

	/**
	 * Inherited method
	 * Currently if the game is running and the sword is out, the player attacks with it
	 */
	@Override
	void gameMouseUp() {
	}

	/**
	 * Inherited Method
	 * Currently unused
	 */
	@Override
	void gameMouseMove() {
	}
	 
	 //From the title screen, load a game file by having the super class get the data,
	 // then handling where the pieces of the data will be assigned here.
	/**
	 * Inherited Method
	 * 
	 * The title screen calls this method when a currently existing file is chosen
	 * Add new saved game details here as well as in the 'Data.java' class
	 * 
	 * Currently only the player x and y location and the current map is saved
	 */
	
	 @Override
	 public void loadGame() {
		 if(currentFile != "") {
			 System.out.println("Loading...");
			 loadData(currentFile);
			 tiles().clear();
			 sprites().clear();			

			 for(int i = 0; i < mapBase.maps.length; i++){
				 if(mapBase.getMap(i) == null) continue;
				 if(data().getMapName() == mapBase.getMap(i).mapName()) currentMap = mapBase.getMap(i);
				 if(data().getOverlayName() == mapBase.getMap(i).mapName()) currentOverlay = mapBase.getMap(i);
			 }
			 player.setLoc(data().getPlayerX(), data().getPlayerY());
			 sprites().add(player);
			// sprites().add(randomNPC);
			 for(int i = 0; i < currentMap.getWidth() * currentMap.getHeight(); i++){
					addTile(currentMap.accessTile(i));
					addTile(currentOverlay.accessTile(i));
					if(currentMap.accessTile(i).hasMob()) sprites().add(currentMap.accessTile(i).mob());
					if(currentOverlay.accessTile(i).hasMob()) sprites().add(currentOverlay.accessTile(i).mob());
			}//end for
			 
			 //Spawns the enemy every X seconds depending on the score
			Timer timer = new Timer();
			 timer.scheduleAtFixedRate(new TimerTask() {
				  @Override
				  public void run() {
					setIsSpawning(true);
				    spawnEnemy();
				    setIsSpawning(false);
				  }
				}, 0, spawnRate*1000);
			 
			System.out.println("Load Successful");
		 } //end file is not empty check
	 } //end load method

	public void checkScore(){
		/**
		if (score-scoreCounter == 100){
			spawnRate-= 3;
			scoreCounter = score;
	}
	*/
	}
	
	/************
	 * spawnEnemy:
	 * 	Initializes an enemy at a random corner on the map
	 * 
	 ************/
	 public void spawnEnemy(){
		 Enemy enemy = new Enemy(this, graphics(), zombie, 40, TYPE.NPC, "npc");
			enemy.setMultBounds(6, 50, 95, 37, 88, 62, 92, 62, 96);
			enemy.setMoveAnim(32, 48, 40, 56, 3, 8);
			enemy.addAttack("sword", 0, 5);
			enemy.getAttack("sword").addMovingAnim(17, 25, 9, 1, 3, 8);
			enemy.getAttack("sword").addAttackAnim(20, 28, 12, 4, 3, 6);
			enemy.getAttack("sword").addInOutAnim(16, 24, 8, 0, 1, 10);
			enemy.setCurrentAttack("sword"); //Starting attack
			enemy.setHealth(35); //If you change the starting max health, dont forget to change it in inGameMenu.java max health also
			
			int[] enemyX = {200, 2100}; // left or right corner
			int[] enemyY = {200, 800}; //top or bottom corner
			int randomX = (int)(Math.random()*2);
			int randomY = (int)(Math.random()*2);
			enemy.setLoc(enemyX[randomX], enemyY[randomY]); //sets the location of the enemy at a random corner
			
			enemies.add(enemy);
			sprites().add(enemy);
			}

} //end class