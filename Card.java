
class Card {
    char rank;
    char suit;
    Card (char r, char s) {
        rank = r;
        suit = s;
    }
    public String getCard() {
        return rank + "" + suit + " ";
    }
    public char getRank() {
        return rank;
    }
    public char getSuit() {
        return suit;
    }
}
