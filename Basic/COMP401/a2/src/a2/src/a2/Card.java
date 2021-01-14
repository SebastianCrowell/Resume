package a2;

public interface Card {

	public enum Suit {SPADES, HEARTS, DIAMONDS, CLUBS};

	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	public static final int ACE = 14;
	
	int getRank();
	Card.Suit getSuit();
	String toString();
	boolean equals(Card other);
	
	public static String suitToString(Card.Suit s) {
		switch (s) {
		case SPADES: return "Spades";
		case HEARTS: return "Hearts";
		case DIAMONDS: return "Diamonds";
		case CLUBS: return "Clubs";
		}
		// This will never happen:
		return null;
	}
}
