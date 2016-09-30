/****************************************************************************************************

 * @author Travis R. Dewitt
 * @version 1.0
 * Date: July 5, 2015
 * 
 * Title: Map Database
 * Description: A data handling class used for large projects. This class contains all of the spritesheets,
 * tiles, events, items, mobs and map creations since they all interlock together.
 * 
 * This work is licensed under a Attribution-NonCommercial 4.0 International
 * CC BY-NC-ND license. http://creativecommons.org/licenses/by-nc/4.0/
 ****************************************************************************************************/
//Package
package axohEngine2.project;

//Imports
import java.awt.Graphics2D;

import axohEngine2.Judgement;
import axohEngine2.entities.Mob;
import axohEngine2.entities.SpriteSheet;
import axohEngine2.map.Event;
import axohEngine2.map.Map;
import axohEngine2.map.Tile;

public class MapDatabase {

	//SpriteSheets
	
	//Remember to define the sprite variables after updating new sprites
	SpriteSheet misc;
	SpriteSheet misc2;
	SpriteSheet buildings;
	SpriteSheet environment32;
	SpriteSheet extras2;
	SpriteSheet zombie;
	
	//Maps 
	
	//Remember to define the map variables after updating new maps
	Map city;
	Map cityO;
	Map houses;
	Map housesO;
	Map insideHouse;
	Map insideHouseO;///Modification 1 10/14/15 12: 41AM
	Map QU;
	Map QUO;
	
	//Tiles - Names are defined in the constructor for better identification
	Tile d;
	Tile g;
	Tile f;
	Tile b;
	Tile r;
	Tile e;
	Tile ro;
	Tile h;
	Tile hf;
	Tile c;
	Tile t;	
	Tile t1;
	Tile t2;
	Tile t3;
	Tile g1; //test
	
	//Events
	Event warp1;
	Event warp2;
	Event warp3;
	Event warp4;
	Event warp5;
	Event warp6;
	Event getPotion;
	Event getMpotion;
	
	//Items
	Item potion;
	Item mpotion;
	
	//NPC's and Monsters
	Mob npc;
	Mob npc2;
	//Array of maps
	public Map[] maps;
	
	/****************************************************************
	 * Constructor
	 * Instantiate all variables for the game
	 * 
	 * @param game - JFrame Window for the map to be displayed on
	 * @param g2d - Graphics2D object needed to display images
	 * @param scale - Number to be multiplied by each image for correct on screen display
	 *******************************************************************/
	public MapDatabase(Judgement game, Graphics2D g2d, int scale) {
		//Currently a maximum of 200 maps possible(Can be changed if needed)
		maps = new Map[200];
		
		//Set up spriteSheets
		misc = new SpriteSheet("/textures/environments/environments1.png", 16, 16, 16, scale);
		//misc2 = new SpriteSheet("textures/environments/environments5.png", 16, 16, 16, scale);
		buildings = new SpriteSheet("/textures/environments/4x4buildings.png", 4, 4, 64, scale);
		environment32 = new SpriteSheet("/textures/environments/32SizeEnvironment.png", 8, 8, 32,scale);
		extras2 = new SpriteSheet("/textures/extras/extras2.png", 16, 16, 16, scale);
		zombie = new SpriteSheet("/textures/characters/zombie.png", 8, 8, 32, scale);
		
		//Set up tile blueprints and if they are animating
		d = new Tile(game, g2d, "door", environment32, 0);
		f = new Tile(game, g2d, "flower", misc, 1);
		g = new Tile(game, g2d, "grass", misc, 0);
		b = new Tile(game, g2d, "bricks", misc, 16, true);
		r = new Tile(game, g2d, "walkWay", misc, 6);
		e = new Tile(game, g2d, "empty", misc, 7);
		ro = new Tile(game, g2d, "rock", misc, 2);
		h = new Tile(game, g2d, "house", buildings, 0, true);
		hf = new Tile(game, g2d, "floor", misc, 8);
		c = new Tile(game, g2d, "chest", extras2, 0, true);
		t = new Tile(game, g2d, "tree", misc, 4, true);
		t1 = new Tile(game, g2d, "tree1", misc, 5, true);
		t2 = new Tile(game, g2d, "tree2", misc, 20, true);
		t3 = new Tile (game, g2d, "tree3", misc, 21, true);
		//g1 = new Tile(game, g2d, "test", misc2, 1);
		
		
		//Set the tile blueprints in an array for the Map
		Tile[] cityTiles = {b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b,
						    b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g,
						    b, b, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g,
						    b, b, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g,
						    b, b, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g,
						    b, b, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g,
						    b, b, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g,
						    b, b, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
						    b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b,
						    b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b};

		
		Tile[] cityOTiles = {e, e, h, e, e, e, e, h, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, h, e, e, e, e, h, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, d, e, e, e, e, d, e, e, e, c, c, c, c, c, e, e, e, e, e, e, e, e, d, e, e, e, e, d, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, t,t1, e, t,t1, e, t,t1, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, t2,t3,e,t2,t3, e,t2,t3, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, 
							 e, e, c, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, c, c, c, c, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, t,t1, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,t2,t3, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
							 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e};

		Tile[] houseTiles = {hf, hf, hf, hf, hf, hf,
							 hf, hf, hf, hf, hf, hf,
							 hf, hf, hf, hf, hf, hf,
							 hf, hf, hf, hf, hf, hf,
							 hf, hf, hf, hf, hf, hf,
							 hf, hf, hf, hf, hf, hf};
		
		Tile[] houseOTiles = { b,  b,  b,  b,  b,  b,
						 	   b,  e,  t, t1,  e,  b,
						 	   b,  e, t2, t3,  e,  b,
						 	   b,  e,  e,  e,  e,  b,
						 	   b,  e,  e,  e,  e,  b,
						 	   b,  b,  b,  e,  b,  b};	
		
		Tile[] insideHouseTiles = {hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf,
								   hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf,
							 	   hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf,
							 	   hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf,
							 	   hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf,
							 	   hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf,
							 	   hf, hf, hf, hf, hf, hf, g,   g, hf, hf, hf, hf, hf, hf, hf,
							 	   hf, hf, hf, hf, hf, hf, g,   g, hf, hf, hf, hf, hf, hf, hf,
							 	   hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf,
							 	   hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf,
							 	   hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf,
							 	   hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf,
							 	   hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf,
							 	   hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf, hf,
							 	   hf, hf, hf, hf, hf, hf, b, hf , hf, hf, hf, hf, hf, hf, hf,
							 	
		};
		
		Tile[] insideHouseOTiles = {b, b, b, b, b, b, b, b, b, b, b, b, b, b, b,
									b, b, b, b, b, b, b, b, b, b, b, b, b, b, b,
									b, b, f, g, g, g, g, g, g, g, g, g, f, b, b,
									b, b, g, g, g, g, g, g, g, g, g, g, g, b, b, 
									b, b, g, g, g, g, g, g, g, g, g, g, g, b, b,
									b, b, g, g, g, g, g, g, g, g, g, g, g, b, b,
									b, b, g, g, g, g, t,t1, g, g, g, g, g, b, b,
									b, b, g, g, g, g,t2,t3, g, g, g, g, g, b, b,
									b, b, g, g, g, g, g, g, g, g, g, g, g, b, b,
									b, b, g, g, g, g, g, g, g, g, g, g, g, b, b,
									b, b, g, g, g, g, g, g, g, g, g, g, g, b, b,
									b, b, g, g, g, g, g, g, g, g, g, g, g, b, b,
									b, b, f, g, g, g, g, g, g, g, g, g, f, b, b,
									b, b, b, b, b, b, b, g, b, b, b, b, b, b, b,
									b, b, b, b, b, b, b, e, b, b, b, b, b, b, b,
		};
									
		
		Tile[] QUTiles = {b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b,
			    b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, b, b,
			    g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, b, b,
			    g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, b, b,
			    g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, b, b,
			    g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, b, b,
			    g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, b, b,
			    g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, g, g, g, g, g, g, g, g, g, g, g, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, b, b,
			    b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b,
			    b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b};
		
		Tile[] QUOTiles = {
				 e, e, h, e, e, e, e, h, e, e, e, e, h, e, e, e, e, h, e, e, e, e, e, e, h, e, e, e, e, h, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, d, e, e, e, e, d, e, e, e, e, d, e, e, e, e, d, e, e, e, e, e, e, d, e, e, e, e, d, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, 
				 e, e, c, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, c, c, c, c, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,ro,ro,ro, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,ro,t,t1, ro, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,ro,t2,t3,ro, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, c,ro,ro, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e,
				 e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e};

		
		//Put the maps together and add them to the array of possible maps
		city = new Map(game, g2d, cityTiles, 40, 40, "city");
		maps[0] = city;
		cityO = new Map(game, g2d, cityOTiles, 40, 40, "cityO");
		maps[1] = cityO;
		houses = new Map(game, g2d, houseTiles, 6, 6, "houses");
		maps[2] = houses;
		housesO = new Map(game, g2d, houseOTiles, 6, 6, "housesO");
		maps[3] = housesO;
		
		//new map that will display the inside of the house
		insideHouse = new Map(game, g2d, insideHouseTiles, 15, 15, "insideHouse");
		maps[4] = insideHouse;
		insideHouseO = new Map(game, g2d, insideHouseOTiles, 15, 15, "insideHouseO");
		maps[5] = insideHouseO;
		
		//Extended map
		QU = new Map(game, g2d, QUTiles, 40, 40, "QU");
		maps[6] = QU;
		QUO = new Map(game, g2d, QUOTiles, 40, 40, "QUO");
		maps[7] = QUO;
		
		//Put together all items (Dont forget to add these to the count and setup methods in inGameMenu.java)
		potion = new Item(game, g2d, extras2, 2, "Potion", false);
		potion.setHealItem(25, false, "");
		mpotion = new Item(game, g2d, extras2, 2, "Mega Potion", false);
		potion.setHealItem(50, false, "");
		
		//Put together all events
		//Warping events
		warp1 = new Event("fromHouse", TYPE.WARP);
		warp1.setWarp("city", "cityO", 616, 280, 0, 0); 
		warp2 = new Event("toHouse", TYPE.WARP);
		warp2.setWarp("houses", "housesO", 616, 253, 0, 0); //find out what the x and y coordinates mean
		warp3 = new Event("insideHouse", TYPE.WARP);
		warp3.setWarp("insideHouse", "insideHouseO", 385, -205, 0, 0);
		warp4 = new Event ("ToCity", TYPE.WARP);
		warp4.setWarp("city", "cityO", 294, 238, 0, 0);
		warp5 = new Event ("ToQU", TYPE.WARP);
		warp5.setWarp("QU", "QUO", 716, 0, -2, -90);
		warp6 = new Event ("ToMain", TYPE.WARP);
		warp6.setWarp("city", "cityO", -574,0, -954, -120);
		
		//Item events
		getPotion = new Event("getPotion", TYPE.ITEM);
		getPotion.setItem(potion);
		getMpotion = new Event("getMpotion", TYPE.ITEM);
		getMpotion.setItem(mpotion);
		
		//Add the events to their specific tiles and maps
		
		cityO.accessTile(83).addEvent(warp3);	
		cityO.accessTile(88).addEvent(warp2);
		cityO.accessTile(438).addEvent(warp5);
		QUO.accessTile(401).addEvent(warp6);
	
		insideHouseO.accessTile(217).addEvent(warp1); 
		housesO.accessTile(33).addEvent(warp4);
		cityO.accessTile(92).addEvent(getPotion);
		cityO.accessTile(242).addEvent(getPotion);
		cityO.accessTile(328).addEvent(getPotion);
		cityO.accessTile(327).addEvent(getMpotion);
		cityO.accessTile(326).addEvent(getMpotion);
		cityO.accessTile(325).addEvent(getMpotion);
		cityO.accessTile(93).addEvent(getMpotion);
		cityO.accessTile(94).addEvent(getMpotion);
		cityO.accessTile(95).addEvent(getMpotion);
		cityO.accessTile(96).addEvent(getMpotion);
		
		//Set up Monsters and NPCs
		npc = new Mob(game, g2d, zombie, 40, TYPE.RANDOMPATH, "npc");
		npc.setMultBounds(6, 50, 92, 37, 88, 62, 92, 62, 96);
		npc.setMoveAnim(32, 48, 40, 56, 3, 8);
		npc.setHealth(60);
		
		//Add the mobs to their tile home
		cityO.accessTile(98).addMob(npc);
	}
	
	/************************************************************
	 * Get a map back  based on its index in the array of maps
	 * 
	 * @param index - Position in the maps array
	 * @return - Map
	 *************************************************************/
	public Map getMap(int index) {
		return maps[index];
	}
}