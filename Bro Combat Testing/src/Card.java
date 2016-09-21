

public class Card {
	public  int topNumber = 0;
	public  int bottomNumber = 0;
	public  int leftNumber = 0;
	public  int rightNumber = 0;
	public  String text = "";
	public  int id = 0;
	public  boolean isPlayer = true;
	public boolean isEnemy = false;
	
	public Card(int topNumberc, int bottomNumberc, int leftNumberc, int rightNumberc, 
			String textc, int idc, boolean isPlayerc, boolean isEnemyc) { //Constructor
		topNumber = topNumberc;
		bottomNumber = bottomNumberc;
		leftNumber = leftNumberc;
		rightNumber = rightNumberc;
		text = textc;
		id = idc;
		isPlayer = isPlayerc;
		isEnemy = isEnemyc;
	}
	
	public int getTop() {
		//return the top value of the card
		return topNumber;
	}
	public int getBot() {
		//return the bottom value of the card
		return bottomNumber;
	}
	public int getLeft() {
		//return the left value of the card
		return leftNumber;
	}
	public int getRight() {
		//return the right value of the card
		return rightNumber;
	}
	public String getText() {
		//return the text of the card
		return text;
	}
	public int getID() {
		//return the ID of the card
		return id;
	}
	public boolean getIsPlayer() {
		//return whether or not the card belongs to the player
		return isPlayer;
	}
	public boolean getIsEnemy() {
		//return whether or not the card belongs to the enemy
		return isEnemy;
	}
	
	//Add setters for later use in case the battlefield changes the states of the cards
}
