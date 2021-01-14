package a2;

public class CardImpl implements Card {
    
    private int _rank;
    private Card.Suit _suit;
    private boolean _cardtrue;
    private boolean _cardfalse;
    
    public CardImpl(int rank, Card.Suit suite) {
        _rank = rank;
        _suit = suite;
        if(2 > _rank || _rank > 14) {
            throw new RuntimeException("Rank oor");
        }
    }
    
    @Override
    public int getRank() {
        // Rank getter
        return _rank;
    }
    
    @Override
    public Suit getSuit() {
        // Suit getter
        return _suit;
    }
    
    @Override
    public boolean equals(Card other) {
        // Check equals
        _cardtrue = true;
        _cardfalse = false;
        if((this.getRank() == other.getRank()) && (this.getSuit() == other.getSuit())) {
            return _cardtrue;
        } else {
            return _cardfalse;
        }
    }
    
    @Override
    public String toString() {
        
        //Switch statement for getting rank and suit then converting to string
        String cardSuit = "";
        switch (this._rank) {
        case 2: cardSuit = "2 of " + Card.suitToString(this._suit);
        break;
        case 3: cardSuit = "3 of " + Card.suitToString(this._suit);
        break;
        case 4: cardSuit = "4 of " + Card.suitToString(this._suit);
        break;
        case 5: cardSuit = "5 of " + Card.suitToString(this._suit);
        break;
        case 6: cardSuit = "6 of " + Card.suitToString(this._suit);
        break;
        case 7: cardSuit = "7 of " + Card.suitToString(this._suit);
        break;
        case 8: cardSuit = "8 of " + Card.suitToString(this._suit);
        break;
        case 9: cardSuit = "9 of " + Card.suitToString(this._suit);
        break;
        case 10: cardSuit = "10 of " + Card.suitToString(this._suit);
        break;
        case 11: cardSuit = "11 of " + Card.suitToString(this._suit);
        break;
        case 12: cardSuit = "12 of " + Card.suitToString(this._suit);
        break;
        case 13: cardSuit = "13 of " + Card.suitToString(this._suit);
        break;
        case 14: cardSuit = "14 of " + Card.suitToString(this._suit);
        break;
        }
        return cardSuit;
    }
}

