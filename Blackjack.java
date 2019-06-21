
import java.util.*;
import java.text.DecimalFormat;

public class Blackjack {

    public int score(Card[] h) {
        int total = 0;
        boolean soft = false;
        for (Card scoreThis : h) {
            if (scoreThis != null) {
                switch (scoreThis.getRank()) {
                    case 'A': { if (total <= 10) { total += 11; soft = true; } else { total += 1; } break; }
                    case '2': { total += 2; break; }
                    case '3': { total += 3; break; }
                    case '4': { total += 4; break; }
                    case '5': { total += 5; break; }
                    case '6': { total += 6; break; }
                    case '7': { total += 7; break; }
                    case '8': { total += 8; break; }
                    case '9': { total += 9; break; }
                    default: { total += 10; break; }
                }
                if ((total > 21) && (soft)) {
                    total -= 10;
                    soft = false;
                }
            }
        }
        return total;
    }

    public boolean soft(Card[] h) {
        int total = 0;
        boolean soft = false;
        for (Card scoreThis : h) {
            if (scoreThis != null) {
                switch (scoreThis.getRank()) {
                    case 'A': { if (total <= 10) { total += 11; soft = true; } else { total += 1; } break; }
                    case '2': { total += 2; break; }
                    case '3': { total += 3; break; }
                    case '4': { total += 4; break; }
                    case '5': { total += 5; break; }
                    case '6': { total += 6; break; }
                    case '7': { total += 7; break; }
                    case '8': { total += 8; break; }
                    case '9': { total += 9; break; }
                    default: { total += 10; break; }
                }
                if ((total > 21) && (soft)) {
                    total -= 10;
                    soft = false;
                }
            }
        }
        return soft;
    }

    public String show(Card[] h) {
        String allCards = "";
        for (Card showThis : h) {
            if (showThis != null) {
                allCards += showThis.getCard() + " ";
            }
        }
        return allCards;
    }

    public String showDealer (Card[] dh) {
        return dh[0].getCard() + " ?";
    }

    public boolean checkSplit (Card[] h) {
        char card1 = h[0].getRank();
        System.out.println(show(h));
        char card2 = h[1].getRank();
        if (card1 == card2) {
            return true;
        } else if ((card1 == 'T' || card1 == 'J' || card1 == 'Q' || card1 == 'K') && (card2 == 'T' || card2 == 'J' || card2 == 'Q' || card2 == 'K')) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkBlackjack (Card[] h) {
        char card1 = h[0].getRank();
        char card2 = h[1].getRank();
        if ((card1 == 'A') && (card2 == 'T' || card2 == 'J' || card2 == 'Q' || card2 == 'K')) {
            return true;
        } else if ((card1 == 'T' || card1 == 'J' || card1 == 'Q' || card1 == 'K') && (card2 == 'A')) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkInsurance(Card[] dh) {
        if (dh[0].getRank() == 'A') {
            return true;
        } else {
            return false;
        }
    }

    public int cardCount (Card[] h) {
        int cardCount = 0;
        for (Card cardToCount : h) {
            if (cardToCount != null) {
                cardCount++;
            }
        }
        return cardCount;
    }

    public int countHands(Card[][] allHands) {
        int count = 0;
        for (Card[] handToCount : allHands) {
            if (handToCount[0] != null) {
                count++;
            }
        }
        return count;
    }

    public boolean yesOrNo (String question) {
        Scanner input = new Scanner(System.in);
        String rawInput;
        char answer;
        boolean exit = false;
        boolean tf = false;
        while (! exit) {
            System.out.print(question + " y or n -> ");
            rawInput = input.nextLine();
            if (rawInput == null || rawInput.isEmpty()) { answer = 'n'; }
            else { answer = rawInput.charAt(0); }
            if (answer == 'y') {
                tf = true;
                exit = true;
            } else if (answer == 'n') {
                tf = false;
                exit = true;
            } else {
                System.out.println("Please enter y or n");
            }
        }
        return tf;
    }

    public int enterBet(String message, int prevBet) {
        int bet = 0;
        String inputString = new String();
        boolean stay = true;
        Scanner input = new Scanner(System.in);
        String rawInput;
        DecimalFormat df = new DecimalFormat("$#,##0;-$#,##0");
        while (stay) {
            System.out.print(message + " -> ");
                rawInput = input.nextLine();
            if (rawInput == null || rawInput.isEmpty()) {
                System.out.println("using previous bet: " + df.format(prevBet));
                bet = prevBet;
                stay = false;
            } else {
                try {
                    bet = Integer.parseInt(rawInput);
                    stay = false;
                } catch (Exception e) {
                    System.out.println("input error");
                }
            }
        }
        return bet;
    }

    public boolean playerWin(boolean pbj, boolean dbj, int ps, int ds) {
        boolean result = false;
        if ((! dbj) && (pbj)) { result = true; }
        if ((! dbj) && (! pbj) && (ps <= 21) && (ds <= 21) && (ps > ds)) { result = true; }
        if ((! dbj) && (! pbj) && (ps <= 21) && (ds > 21)) { result = true; }
        return result;
    }

    public boolean push(boolean pbj, boolean dbj, int ps, int ds) {
        boolean result = false;
        if ((dbj) && (pbj)) { result = true; }
        if ((! dbj) && (! pbj) && (ps <= 21) && (ds <= 21) && (ps == ds)) { result = true; }
        return result;
    }

/*    public void showAllHands(Card[][] allHands) {
        for (Card[] handToCount : allHands) {
            System.out.println(show(handToCount));
        }
    }
*/

    public void sleep(int secs) {
        try { Thread.sleep(secs * 1000); }
        catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
    }

    public void sleep(double secs) {
        try { Thread.sleep((int)(secs * 1000)); }
        catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
