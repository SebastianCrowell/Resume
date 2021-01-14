package a2;

import java.util.Scanner;

public class HandEvaluator {
	

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		//Scanner for each card that might be in the hand
		while (sc.hasNext()) {
			int otherHands = sc.nextInt();
			if (otherHands == 0) 
				break;
				Card[] cardHand = new Card[5];

			// Input
			for(int i = 0; i < 5; i++) {
				int rank = sc.nextInt();
				String suit = sc.next();
				switch(suit) {
					case "S": cardHand[i] = new CardImpl(rank, Card.Suit.SPADES); break;
					case "H": cardHand[i] = new CardImpl(rank, Card.Suit.HEARTS); break;
					case "D": cardHand[i] = new CardImpl(rank, Card.Suit.DIAMONDS); break;
					case "C": cardHand[i] = new CardImpl(rank, Card.Suit.CLUBS); break;
					default: suit = null;
				}	

			}

			PokerHand currentHand = new PokerHandImpl(cardHand);

			// Wins
			int wins = 0;
			for(int j = 0; j < 10000; j++) {
				Deck cardDeck = new DeckImpl();
				
				// Take drawn cards
				for(int i = 0; i < 5; i++) {
					cardDeck.findAndRemove(cardHand[i]);
				}

				// Other hand values
				PokerHand[] tempHand = new PokerHandImpl[otherHands];
				for(int i = 0; i < otherHands; i++) {
					if(cardDeck.hasHand())
						tempHand[i] = cardDeck.dealHand();
				}
				
				int flag = 1;
				for(int i = 0; i < otherHands; i++) {
					if(currentHand.compareTo(tempHand[i]) < 1)
						flag = -1;		
					}
				if(flag == 1)
					wins++;
			}
			//Win percentage
			System.out.print("\n" + Math.round((float)wins / 100));
	}
	sc.close();
	}
}
