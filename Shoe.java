
import java.util.*;

class Shoe {

    private Card[] shoe;

    Shoe () {
        shoe = new Card[52 * 6];
        populateShoe();
    }

    Shoe (int numDecks) {
        shoe = new Card[52 * numDecks];
        populateShoe();
    }

    public void populateShoe() {
        Deck newDeck = new Deck();
        Card[] addDeck = newDeck.deck;
        int index = 0;
        for (int x = 0; x < shoe.length; x++) {
            shoe[x] = addDeck[index];
            if (index == 51) {
                index = 0;
            } else {
                index++;
            }
        }
    }

    public Card[] getShoe() {
        return shoe.clone();
    }

    public static void shuffleShoe(Card[] shoe) {
        Random randomGenerator = new Random();
        for (int x = 1; x <= 6; x++) { // go through shoe 6 times
            for (int y = 0; y < shoe.length; y++) {
                int random = randomGenerator.nextInt(shoe.length);
                Card cardHolder = shoe[y];
                shoe[y] = shoe[random];
                shoe[random] = cardHolder;
            }
        }
    }
}
