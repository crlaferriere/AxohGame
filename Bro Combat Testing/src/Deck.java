

import java.util.Random;

public class Deck {
	
	public Deck() { //Constructor
		
	}
	
	//Make thirty cards to initialize the deck
	//Card (top, bot, left, right, TEXT, ID, isPlayer, isEnemy)
	  Card one = 			new Card(3, 5, 4, 2, "Attack (5)", 1, true, false); //basic attack
	  Card two = 			new Card(3, 5, 4, 2, "Attack (5)", 1, true, false);
	  Card three =	 		new Card(3, 5, 4, 2, "Attack (5)", 1, true, false);
	  Card four = 			new Card(2, 0, 1, 3, "Attack (8)", 2, true, false); //stronger attack
	  Card five = 			new Card(2, 0, 1, 3, "Attack (8)", 2, true, false);
	  Card six = 			new Card(2, 0, 1, 3, "Attack (8)", 2, true, false);
	  Card seven = 			new Card(0, 0, 0, 0, "Heal (10)", 3, true, false); //Weak heal
	  Card eight = 			new Card(0, 0, 0, 0, "Heal (10)", 3, true, false);
	  Card nine = 			new Card(0, 0, 0, 0, "Heal (10)", 3, true, false);
	  Card ten = 			new Card(10, 8, 6, 12, "Can't deal damage", 4, true, false); //Armor
	  Card eleven =			new Card(10, 8, 6, 12, "Can't deal damage", 4, true, false);
	  Card twelve = 		new Card(10, 8, 6, 12, "Can't deal damage", 4, true, false);
	  Card thirteen = 		new Card(2, 2, 1, 8, "Destroy the card to the right after damage", 5, true, false); //Destroys card to the right
	  Card fourteen = 		new Card(2, 2, 1, 8, "Destroy the card to the right after damage", 5, true, false);
	  Card fifteen = 		new Card(2, 2, 1, 8, "Destroy the card to the right after damage", 5, true, false);
	  Card sixteen = 		new Card(2, 2, 8, 1, "Destroy the card to the left after damage", 6, true, false); //Destroys card to the left
	  Card seventeen =		new Card(2, 2, 8, 1, "Destroy the card to the left after damage", 6, true, false);
	  Card eighteen = 		new Card(2, 2, 8, 1, "Destroy the card to the left after damage", 6, true, false);
	  Card nineteen = 		new Card(6, 2, 3, 3, "Only interacts with card directly above on play", 7, true, false); //Deals damage only to card above
	  Card twenty = 		new Card(6, 2, 3, 3, "Only interacts with card directly above on play", 7, true, false);
	  Card twentyone =	 	new Card(6, 2, 3, 3, "Only interacts with card directly above on play", 7, true, false);
	  Card twentytwo = 		new Card(2, 6, 3, 3, "Only interacts with card directly below on play", 8, true, false); //Deals damage only to card below
	  Card twentythree = 	new Card(2, 6, 3, 3, "Only interacts with card directly below on play", 8, true, false);
	  Card twentyfour = 	new Card(2, 6, 3, 3, "Only interacts with card directly below on play", 8, true, false);
	  Card twentyfive = 	new Card(3, 3, 4, 4, "Attack (5), if only one adjacent card, Attack (10)", 9, true, false); //Assassin card
	  Card twentysix = 		new Card(3, 3, 4, 4, "Attack (5), if only one adjacent card, Attack (10)", 9, true, false);
	  Card twentyseven =	new Card(3, 3, 4, 4, "Attack (5), if only one adjacent card, Attack (10)", 9, true, false);
	  Card twentyeight =	new Card(1, 2, 2, 1, "Attack (8), if no adjacent cards, Attack (12)", 10, true, false); //Good aggressive card
	  Card twentynine = 	new Card(1, 2, 2, 1, "Attack (8), if no adjacent cards, Attack (12)", 10, true, false);
	  Card thirty = 		new Card(1, 2, 2, 1, "Attack (8), if no adjacent cards, Attack (12)", 10, true, false);
	
	public   Card[] deck = {one, two, three, four, five, six, seven, eight,
			nine, ten, eleven, twelve, thirteen, fourteen, fifteen, sixteen,
			seventeen, eighteen, nineteen, twenty, twentyone, twentytwo,
			twentythree, twentyfour, twentyfive, twentysix, twentyseven,
			twentyeight, twentynine, thirty};
	
	//For the sake of time constraints, we will make the enemy's deck the same as ours
	
	public   Card[] enemyDeck = {one, two, three, four, five, six, seven, eight,
			nine, ten, eleven, twelve, thirteen, fourteen, fifteen, sixteen,
			seventeen, eighteen, nineteen, twenty, twentyone, twentytwo,
			twentythree, twentyfour, twentyfive, twentysix, twentyseven,
			twentyeight, twentynine, thirty};
	
	public Card[] backupDeck = deck; //In case either deck runs out of cards
	
	public Card getCardFromDeck(int location, Card[] Deck) {
		Card cardToRemove;
		if (deck[location] != null) {
			cardToRemove = deck[location];
			deck[location] = null;
			return cardToRemove;
		}
		else return null;
	}

	 public void shuffleDeck(Card[] Deck) {
		// If running on Java 6 or older, use `new Random()` on RHS here
		Random rnd = new Random();
		for (int i = Deck.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			Card a = Deck[index];
			Deck[index] = Deck[i];
			Deck[i] = a;
		}
	}
}
