
package a2;

public class PokerHandImpl implements PokerHand{

    private Card[] _currenthand;
    private int _value;
    private int _rank;
    
    public PokerHandImpl(Card[] hand_cards) {
        // Check each card in  hand
        if( hand_cards == null || 
            hand_cards[0] == null || 
            hand_cards[1] == null || 
            hand_cards[2] == null || 
            hand_cards[3] == null || 
            hand_cards[4] == null) {
            throw new RuntimeException("Nulls");
        	} else {
        		_currenthand = hand_cards.clone();
        	}
    }
    
    public void setValue(int value) {
        _value = value;
    }

    public void setRank(int rank) {
        _rank = rank;
    }
    
    public Card[] sortCardByRank (Card[] hand_cards){
        // Sort by checking to see if a value is greater thank another, then storing it to array
        for (int i=0; i < hand_cards.length; i++) {
        	for (int j = i+1; j < hand_cards.length; j++) {
        		if (hand_cards[i].getRank() > hand_cards[j].getRank()) {
        			Card tmp = hand_cards [i];
                    hand_cards[i] = hand_cards[j];
                    hand_cards[j] = tmp;
                }
            }
        }
        return hand_cards;
    }
    
    @Override
    public Card[] getCards() {
        return _currenthand.clone();
    }
    
    @Override
    public boolean contains(Card c) {
        // Use sorted hand of cards
        Card[] _sorted = sortCardByRank(_currenthand);
        boolean _contains = false;
        
	        for(int i=0; i < 5; i++) {
	            if (_sorted[i].equals(c)) {
	                _contains = true;
	                break;
	            	} else {
	            		_contains = false;
	            	}
	        }
	        return _contains;
    }
    
    @Override
    public boolean isOnePair() {
        // Checking all cards in hand
        Card[] _sorted = sortCardByRank(_currenthand);
        boolean onePair = false;
        
	        int thisranks[] = new int[5];
	        int otherranks[] = new int [5];
	        for(int i=0; i<5; i++) {
	            thisranks[i] = _sorted[i].getRank();
	            otherranks[i] = _sorted[i].getRank();
	        }
	        int j = 0;
	        int i = 1;
	        while(i < thisranks.length) {
	            if (thisranks[i] == otherranks[j]) {
	                i++;
	            } else {
	                thisranks[j++] = thisranks[i++];
	            }
	        }
	        int[] noDuplicates = new int[j + 1];
	        for (int k=0; k < noDuplicates.length; k++) {
	            noDuplicates[k] = thisranks[k];
	        }
	        if (noDuplicates.length != 4) {
	            onePair = false;
	        } else {
	            onePair = true;
	            this.setValue(2);
	            for(int m=0; m < noDuplicates.length; m++) {
	                int count = 0;
	                for(int n=0; n < otherranks.length; n++) {
	                    if(noDuplicates[m] == otherranks[n]) {
	                        count++;
	                    }
	                }
	                if(count == 2) {
	                    this.setRank(noDuplicates[m]);
	                    break;
	                }
	            }
	        }
	        return onePair;
    }
    
    @Override
    public boolean isTwoPair() {
        // TODO Auto-generated method stub
        Card[] _sorted = sortCardByRank(_currenthand);
        boolean twoPair = false;
        
	        if(!isThreeOfAKind()) {
		        int thisranks[] = new int[5];
		        int otherranks[] = new int [5];
		        for(int i=0; i < 5; i++) {
		            thisranks[i] = _sorted[i].getRank();
		            otherranks[i] = _sorted[i].getRank();
		        }
		        int j = 0;
		        int i = 1;
		        while(i < thisranks.length) {
		            if (thisranks[i] == otherranks[j]) {
		                i++;
		            } else {
		                thisranks[j++] = thisranks[i++];
		            }
		        }
		        int[] noDuplicates = new int[j + 1];
		        for (int k=0; k < noDuplicates.length; k++) {
		            noDuplicates[k] = thisranks[k];
		        }
		        if (noDuplicates.length != 3) {
		            twoPair = false;
		        } else {
		            int[] pair = new int[2];
		            for(int x=0; x < pair.length; x++) {
		                for(int m=0; m < noDuplicates.length; m++) {
		                    int counter = 0;
		                    for(int n=0; n < otherranks.length; n++) {
		                        if(noDuplicates[m] == otherranks[n]) {
		                            counter += 1;
		                        }
		                    }
		                    if(counter == 2) {
		                        twoPair = true;
		                        this.setValue(3);
		                        pair[x] = noDuplicates[m];
		                    }
		                }
		            }
		            if(pair[0] > pair[1]) {
		            	this.setRank(pair[0]);
		            } else {
		            	this.setRank(pair[1]);
		            }
	        }
	    }
        return twoPair;
    }
    
    @Override
    public boolean isThreeOfAKind() {
    	// TODO Auto-generated method stub
    	Card[] _sorted = sortCardByRank(_currenthand);
    	boolean threePair = false;
    	
	    	if(!isFourOfAKind()) {
	    		if((_sorted[0].getRank() == _sorted[1].getRank()) && 
	    		   (_sorted[0].getRank() == _sorted[2].getRank()) &&
	    		   (_sorted[3].getRank() != _sorted[4].getRank())) {
	    			threePair = true;
	    			this.setValue(4);
	    			this.setRank(_sorted[0].getRank());
	    		} else if ((_sorted[1].getRank() == _sorted[2].getRank()) && 
	 		   			   (_sorted[1].getRank() == _sorted[3].getRank()) &&
	 		   			   (_sorted[0].getRank() != _sorted[4].getRank())) {
	 		   				threePair = true;
	 		   				this.setValue(4);
	 		   				this.setRank(_sorted[1].getRank());
	 		   			} else if ((_sorted[2].getRank() == _sorted[3].getRank()) &&
	 		   					   (_sorted[2].getRank() == _sorted[4].getRank()) &&
	 		   					   (_sorted[0].getRank() == _sorted[1].getRank())) {
	 		   				   		threePair = true;
	 		   				   		this.setValue(4);
	 		   				   		this.setRank(_sorted[1].getRank());
	 		   			   		   } else {
	 		   			   			   threePair = false;
	 		   			   		   }
    	}
        return threePair;			   
    }
    @Override
    public boolean isFourOfAKind() {
        // TODO Auto-generated method stub
    	Card[] _sorted = sortCardByRank(_currenthand);
    	boolean fourPair = false;
    	
    		for(int i=0; i < 2; i++) {
    			int counter = 1;
    			int temp = _sorted[i].getRank();
    			for(int j=0; j<5; j++) {
    				if(_sorted[j].getRank() == temp) {
    					counter += 1;
    				}
    			}
    			if(counter == 4) {
    				fourPair = true;
    				this.setValue(8);
    				this.setRank(temp);
    				break;
    			}
    		}
        return fourPair;
    }
    @Override
    public boolean isStraight() {
        // TODO Auto-generated method stub
    	Card[] _sorted = sortCardByRank(_currenthand);
    	boolean isStraight = false;
    	
    		if(((_sorted[4].getRank() - _sorted[3].getRank()) == 1) &&
    			((_sorted[3].getRank() - _sorted[2].getRank()) == 1) &&
    			((_sorted[2].getRank() - _sorted[1].getRank()) == 1) &&
    			((_sorted[1].getRank() - _sorted[0].getRank()) == 1)) {
    			isStraight = true;
    			this.setValue(5);
    			this.setRank(_sorted[4].getRank());
    			} else if (
    				(_sorted[4].getRank() == 14) &&
    				(_sorted[3].getRank() == 5) &&
    				(_sorted[2].getRank() == 4) &&
    				(_sorted[1].getRank() == 3) &&
    				(_sorted[0].getRank() == 2)) {
    				isStraight = true;
    				this.setValue(5);
    				this.setRank(5);
    			}
            return isStraight;
    }
    @Override
    public boolean isFlush() {
        // TODO Auto-generated method stub
    	Card[] _sorted = sortCardByRank(_currenthand);
    	boolean isFlush = false;
    	
    		if((_sorted[0].getSuit() == _sorted[1].getSuit()) &&
    		   (_sorted[0].getSuit() == _sorted[2].getSuit()) &&
    		   (_sorted[0].getSuit() == _sorted[3].getSuit()) &&
    		   (_sorted[0].getSuit() == _sorted[4].getSuit())) {
    		   isFlush = true;
    		   this.setValue(6);
    		   this.setRank(_sorted[4].getRank());
    		}
        return isFlush;
    }
    @Override
    public boolean isFullHouse() {
        // TODO Auto-generated method stub
    	Card[] _sorted = sortCardByRank(_currenthand);
    	boolean isFull = false;
    	
    		if((_sorted[0].getRank() == _sorted[1].getRank()) &&
    		   (_sorted[2].getRank() == _sorted[3].getRank()) &&
    		   (_sorted[2].getRank() == _sorted[4].getRank())) {
    		   isFull = true;
    		   this.setValue(7);
    		   this.setRank(_sorted[2].getRank());
    			} else if ((_sorted[0].getRank() == _sorted[1].getRank()) &&
    				(_sorted[3].getRank() == _sorted[2].getRank()) &&
    				(_sorted[3].getRank() == _sorted[4].getRank())) {
    				isFull = true;
    				this.setValue(7);
    				this.setRank(_sorted[0].getRank());
    					} else {
    						isFull = false;
    					}
        return isFull;
    }
    @Override
    public boolean isStraightFlush() {
        // TODO Auto-generated method stub
    	Card[] _sorted = sortCardByRank(_currenthand);
    	boolean isSF = false;
    	
    		if(this.isFlush() && this.isStraight()) {
    			isSF = true;
    			this.setRank(9);
    			if(_sorted[4].getRank() == 14) {
    				this.setRank(5);
    			} else {
    				this.setRank(_sorted[4].getRank());
    			}
    		}
        return isSF;
    }
    @Override
    public int getHandTypeValue() {
        // TODO Auto-generated method stub
    	if( this.isStraightFlush()) {
    		return _value;
    		} else if (this.isOnePair() ||
    				   this.isTwoPair() ||
    				   this.isThreeOfAKind() ||
    				   this.isStraight() ||
    				   this.isFlush() ||
    				   this.isFullHouse() ||
    				   this.isFourOfAKind()) {
    			     	  return _value;
    			   		  } else {
    			   			  return 1;
    			   		  }
    }
    @Override
    public int getHandRank() {
        // TODO Auto-generated method stub
    	if(this.isStraightFlush()) {
    		return _rank;
    		} else if (this.isOnePair() ||
    				   this.isTwoPair() ||
    				   this.isThreeOfAKind() ||
    				   this.isStraight() ||
    				   this.isFlush() ||
    				   this.isFullHouse() ||
    				   this.isFourOfAKind()) {
    			     	  return _rank;
    			   		  } else {
    			   			  Card [] _sorted = sortCardByRank(_currenthand);
    			   			  return _sorted[4].getRank();
    			   		  }
    }
    @Override
    public int compareTo(PokerHand other) {
        // TODO Auto-generated method stub
    	if(this.getHandTypeValue() < other.getHandTypeValue()) {
    		return -1;
    		} else if (this.getHandTypeValue() > other.getHandTypeValue()) {
    			return 1;
    			} else {
    				if(this.getHandRank() < other.getHandRank()) {
    					return -1;
    				} else if (this.getHandRank() > other.getHandRank()) {
    						return 1;
    					} else {
    						return 0;
    					}
    		}
    }
}
