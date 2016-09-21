

public class Hand {
	public   Card[] hand = {null, null, null, null};
	public   Card[] enemyHand = {null, null, null, null};
	int handSize = 4;
	Card cardToDraw = null;
	int cardLocation = 0;
	  
	Deck d = new Deck();
	
	public Hand() { //Constructor
		
	}
	
	public   void draw(Card[] hand, Card[] deck) {
		for (int i = 0; i < handSize; i++) {
			//put a card from the deck into the hand slot if the hand slot is null
			if (hand[i] == null) {
				cardToDraw = d.getCardFromDeck(cardLocation, deck);
				System.out.println("Card Location in deck: " + cardLocation);
				cardLocation++;
				System.out.println("Drawn card: " + cardToDraw.getTop() + ", " + cardToDraw.getBot()
						 + ", " + cardToDraw.getLeft() + ", " + cardToDraw.getRight() + ", "
						 + cardToDraw.getID());
				if (cardToDraw == null) {
					draw(hand, deck); //do it again
				}
			}
			hand[i] = cardToDraw;
		} //At this point, the hand should be full of cards once more
		cardLocation = 0;
	}
}