

import java.util.Scanner;

public class BattleField {
	
	int damage = 0; //Number to determine damage to an entity upon placing a card
	int playerHealth = 30;
	int enemyHealth = 20;
	
	int cardToPlay;
	int x, y;
	
	boolean playerTurn = true;
	
	Scanner sc = new Scanner(System.in);
	
	Deck d = new Deck();
	Hand h = new Hand();
	
	Card c = new Card(0, 0, 0, 0, "", -1, false, false); //blank card
//	public   Card[][] field =  {{c, c, c, c, c},
//									 {c, c, c, c, c},
//									 {c, c, c, c, c},
//									 {c, c, c, c, c},
//									 {c, c, c, c, c}}; // 5x5 array of cards
	Card f = new Card(-10, -10, -10, -10, "", 1, false, true); //dummy enemy card for testing
	public Card[][] field =  {{c, c, f, c, c},
							  {c, c, c, c, c},
							  {f, c, f, c, f},
							  {c, c, c, c, c},
							  {c, c, f, c, c}}; // 5x5 array of cards
	
	public Card[][] backupField = field; //Used to reset the field to its original, empty state
	
	public BattleField() { //Constructor
		
	}
	
	public void enterBattle(boolean goFirst) {
		//When you hit the enemy, call this method with (true)
		//When you are hit by the enemy, call this method with (false)
		
		d.deck = d.backupDeck;
		d.enemyDeck = d.backupDeck; //Set both deck to their original state
		
		//d.shuffleDeck(d.deck); //Shuffle our deck
		//Deck.shuffleDeck(Deck.enemyDeck); //Shuffle enemy deck
		
		h.draw(h.hand, d.deck); //Draws 4 times to fill up your hand
		//Hand.draw(Hand.enemyHand, Deck.enemyDeck); //Draws 4 times to fill the enemy hand
		
		//Will eventually have to make a method to clear hand for next battle
		
		field = backupField;
		
//		for (int i = 0; i < 5; i++) {
//			for (int j = 0; j < 5; j++) {
//				System.out.print(field[i][j].getIsEnemy());
//			}
//			System.out.println("");
//		}
		
		//At this point, the deck are shuffled, and hands are full, and battlefield is empty
		
		if (goFirst) playerTurn = true;
		else playerTurn = false;
		
		//Make a scanner so the user inputs the location of the card in their hand they want to play
		//Have user input x and y of location they want their card placed.
		while (playerHealth > 0) {
			playerTurn();
			play(x, y, cardToPlay, true);
			h.draw(h.hand, d.deck); //Draws 4 times to fill up your hand
		}
	}
	
	public void play(int x, int y, int cardToPlay, boolean PlayerTurn) {
		//play a card onto a x, y location on the board
		
		if (field[x][y] == c) { //if the selected area is empty
			
			if (PlayerTurn) { //if it is the player that is attacking
				field[x][y] = h.hand[cardToPlay]; //make the selected area the card
				//Now we do its effect
					//TODO Add unique combat effect for each card ID
				
				//Now we interact with other surrounding cards, if any
				
				if (y <= 3 && field[x][y+1].getIsEnemy()) {
					damage += field[x][y].getTop() - field[x][y+1].getBot();
				}
				if (y >= 1 && field[x][y-1].getIsEnemy()) {
					damage += field[x][y].getBot() - field[x][y-1].getTop();
				}
				if (x <= 3 && field[x+1][y].getIsEnemy()) {
					damage += field[x][y].getRight() - field[x+1][y].getLeft();
				}
				if (x >= 1 && field[x-1][y].getIsEnemy()) {
					damage += field[x][y].getLeft() - field[x-1][y].getRight();
				}
				//System.out.println(field[x+1][y].getLeft());
				//System.out.println(field[x][y].getRight());
//				if (damage <= 0) {
//					damage = 0;
//					System.out.println("You did no damage with that attack.");
//				} else {
					enemyHealth -= damage;
					System.out.println("You dealt " + damage + " damage to the enemy.");
//				}
				h.hand[cardToPlay] = null;
				PlayerTurn = false;
			} else { //If it is the enemy that is attacking
				//TODO Randomly choose a card in the enemy's hand
				
				//TODO Play that card on a random spot on the field
				
				field[x][y] = h.enemyHand[cardToPlay]; //make the selected area the card
				//Now we do its effect
					//TODO Add unique combat effect for each card ID
					//For example, ID 1 would set damage to 5 to start
				
				//Now we interact with other surrounding cards, if any
				
				if (field[x][y+1].getIsPlayer()) {
					damage += field[x][y].getTop() - field[x][y+1].getBot();
				}
				if (field[x][y+1].getIsPlayer()) {
					damage += field[x][y].getBot() - field[x][y-1].getTop();
				}
				if (field[x][y+1].getIsPlayer()) {
					damage += field[x][y].getRight() - field[x+1][y].getLeft();
				}
				if (field[x][y+1].getIsPlayer()) {
					damage += field[x][y].getLeft() - field[x-1][y].getRight();
				}
				if (damage < 0) {
					damage = 0;
					System.out.println("The enemy did no damage with that attack.");
				} else {
					playerHealth -= damage;
					System.out.println("The enemy did "+ damage +" damage to you.");
				}
				PlayerTurn = true;
			}
			if (checkOutcome(playerHealth, enemyHealth) == -1) {
				System.out.println("You have won");
				//end game as victory
			} else if (checkOutcome(playerHealth, enemyHealth) == 1) {
				System.out.println("You have lost");
				//end game as Loss
			}
		}
	}
	
	public int checkOutcome(int playerHP, int enemyHP) {
		int result = 0;
		if (playerHealth == 0) {
			result = 1;
		} else if (enemyHealth == 1) {
			result = -1;
		} else {
			result = 0;
		}
		return result;
		//Check if either player is dead
		
		//If player is dead, return 1
		//If enemy is dead, return -1
		//If neither is dead, return 0
	}
	public void playerTurn() {
		System.out.println("Enter the card location in your hand");
		cardToPlay = sc.nextInt();
		System.out.println("Enter the x location on the field (-1<x<4)");
		x = sc.nextInt();
		System.out.println("Enter the y location on the field (-1<x<4)");
		y = sc.nextInt();
	}
}
