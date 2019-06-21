
import java.util.*;

class Deck {

    private char spade ='\u2660';
    private char heart ='\u2665';
    private char club ='\u2663';
    private char diamond ='\u2666';
    public Card[] deck = new Card[52];

    Deck () {
        char[] suit = {spade,heart,club,diamond};
        char[] rank = {'A','2','3','4','5','6','7','8','9','T','J','Q','K'};
        int count = 0;
        for ( char s : suit) { 
            for ( char r : rank) { 
                Card newCard = new Card(r,s);
                deck[count++] = newCard;
            }
        }
    }

/*    public Card[] getDeck() {
        return deck.clone();
    }
*/

    public void shuffleDeck(Card[] deck) {
        Random randomGenerator = new Random();
        for (int x = 1; x <= 6; x++) { // go through deck 6 times
            for (int y = 0; y < deck.length; y++) {
                int random = randomGenerator.nextInt(deck.length);
                Card cardHolder = deck[y];
                deck[y] = deck[random];
                deck[random] = cardHolder;
            }
        }
    }
}
