
import java.util.*;
import java.text.DecimalFormat;

public class PlayBlackjack {

    public static void main(String[] args) {
        Blackjack bj = new Blackjack();
        int numDecks = 6;
        Shoe GameShoe = new Shoe(numDecks);
        Card cardShoe[] = GameShoe.getShoe();
        GameShoe.shuffleShoe(cardShoe);
        int shoeCount = 0;
        int dealerCardIndex = 0;
        int dollarTotal = 0;
        int[] bet = {0, 0, 0, 0};
        int insuranceBet = 0;
        int splitCount = 0;
        int playerHandsIndex = 0;
        int playerCardIndex = 0;
        int playerScore = 0;
        int dealerScore = 0;
        int previousBet = 0;
        int previousInsBet = 0;
        boolean doubleDown = false;
        boolean exit = false;
        boolean playerBlackjack = false;
        boolean dealerBlackjack = false;
        boolean dealerSoft = false;
        boolean playerSoft = false;
        boolean win = false;
        boolean push = false;
        boolean busted = false;
        DecimalFormat df = new DecimalFormat("$#,##0;-$#,##0");
        Card[][] playerHands = {null, null, null, null};
        Card dealerHand[] = new Card[21];
        for (int x = 0; x < 4; x++) { // this creates 4 arrays refered to by hand[x], array has room for 21 Card objects
            Card hand[] = new Card[21];
            playerHands[x] = hand;
        }
        bj.clearScreen();
        while (shoeCount + 1 < cardShoe.length - 21) { // the game will last until there are less than 21 cards left in the shoe
            playerHands[playerHandsIndex][playerCardIndex++] = cardShoe[shoeCount++];
            dealerHand[dealerCardIndex++] = cardShoe[shoeCount++];
            playerHands[playerHandsIndex][playerCardIndex++] = cardShoe[shoeCount++];
            dealerHand[dealerCardIndex++] = cardShoe[shoeCount++];
            bet[playerHandsIndex] = bj.enterBet("enter bet", previousBet);
            previousBet = bet[playerHandsIndex];
            playerSoft = bj.soft(playerHands[playerHandsIndex]);
            System.out.print("player hand: " + bj.show(playerHands[playerHandsIndex]));
            System.out.print(" score: " + bj.score(playerHands[playerHandsIndex]));
            System.out.println((playerSoft) ? " soft" : "");
            playerBlackjack = bj.checkBlackjack(playerHands[playerHandsIndex]);
            if (playerBlackjack) { 
                bj.sleep(0.4);
                System.out.println("Player Blackjack!");
            }
            System.out.println("dealer hand: " + bj.showDealer(dealerHand));
            dealerScore = bj.score(dealerHand);
            dealerSoft = bj.soft(dealerHand);
            int numPlayerHands = bj.countHands(playerHands);
            dealerBlackjack = bj.checkBlackjack(dealerHand);
            if ((bj.checkInsurance(dealerHand)) && (bj.yesOrNo("buy insurance?"))) {
                insuranceBet = bj.enterBet("enter insurance bet", previousInsBet);
                previousInsBet = insuranceBet;
                if (dealerBlackjack) {
                    System.out.print("adding $" + (2 * insuranceBet) + " for 2/1 insurance payment");
                    dollarTotal += (2 * insuranceBet);
                } else {
                    System.out.print("subtracting $" + insuranceBet + " for insurance");
                    dollarTotal -= insuranceBet;
                }
                System.out.println(" new total: " + df.format(dollarTotal));
            }
            if (dealerBlackjack) { 
                bj.sleep(0.4);
                System.out.println("Dealer Blackjack!");
            }
            do {
                while ((! dealerBlackjack) && (bj.checkSplit(playerHands[playerHandsIndex])) && (splitCount < 3)) {
                    System.out.print("player hand: " + bj.show(playerHands[playerHandsIndex]));
                    System.out.print(" score: " + bj.score(playerHands[playerHandsIndex]));
                    System.out.println((playerSoft) ? " soft" : "");
                    if (bj.yesOrNo("split?      ")) {
                        splitCount++;
                        if (splitCount == 3) { System.out.println("4 hand limit reached, no more splitting"); }
                        numPlayerHands = bj.countHands(playerHands);
                        playerHands[numPlayerHands][0] = playerHands[playerHandsIndex][1];
                        playerHands[numPlayerHands][1] = cardShoe[shoeCount++];
                        playerHands[playerHandsIndex][1] = cardShoe[shoeCount++];
                        bet[numPlayerHands] = bet[playerHandsIndex];
                        System.out.print("player hand: " + bj.show(playerHands[playerHandsIndex]));
                        System.out.print(" score: " + bj.score(playerHands[playerHandsIndex]));
                        System.out.println((playerSoft) ? " soft" : "");
                    } else { break; }
                }
                if ((! dealerBlackjack) && (! playerBlackjack) && (bj.cardCount(playerHands[playerHandsIndex]) < 3) && bj.yesOrNo("double down?")) {
                    doubleDown = true;
                    bet[playerHandsIndex] += bet[playerHandsIndex];
                    System.out.println("bet is now: $" + bet[playerHandsIndex]);
                    playerHands[playerHandsIndex][playerCardIndex++] = cardShoe[shoeCount++];
                    System.out.print("player hand: " + bj.show(playerHands[playerHandsIndex]));
                    System.out.println(" score: " + bj.score(playerHands[playerHandsIndex]));
                }
                playerScore = bj.score(playerHands[playerHandsIndex]);
                while ((! dealerBlackjack) && (! exit) && (! doubleDown) && (playerScore < 21)) {
                    if (bj.yesOrNo("hit?        ")) {
                        playerHands[playerHandsIndex][playerCardIndex++] = cardShoe[shoeCount++];
                        playerSoft = bj.soft(playerHands[playerHandsIndex]);
                        System.out.print("player hand: " + bj.show(playerHands[playerHandsIndex]));
                        System.out.print(" score: " + bj.score(playerHands[playerHandsIndex]));
                        System.out.println((playerSoft) ? " soft" : "");
                        playerScore = bj.score(playerHands[playerHandsIndex]);
                        if (playerScore >= 21) { exit = true; }
                        if (playerScore > 21) { 
                            bj.sleep(0.4);
                            System.out.println("Player Busted!");
                        }
                    } else { exit = true; }
                }
                playerHandsIndex++;
                numPlayerHands = bj.countHands(playerHands);
                doubleDown = false;
                exit = false;
                busted = false;
                playerScore = 0;
            } while (numPlayerHands > playerHandsIndex);

            System.out.print("dealer hand: " + bj.show(dealerHand));
            System.out.print(" score: " + dealerScore);
            System.out.println((dealerSoft) ? " soft" : "");

            while (((! dealerBlackjack) && (! playerBlackjack) && (dealerSoft == false) && (dealerScore < 17)) || ((! dealerBlackjack) && (! playerBlackjack) && (dealerSoft == true) && (dealerScore <= 17))) {
                bj.sleep(1.5);
                dealerHand[dealerCardIndex++] = cardShoe[shoeCount++];
                dealerScore = bj.score(dealerHand);
                dealerSoft = bj.soft(dealerHand);
                System.out.print("dealer hand: " + bj.show(dealerHand));
                System.out.print(" score: " + dealerScore);
                System.out.println((dealerSoft) ? " soft" : "");
                if (dealerScore > 21) { 
                    bj.sleep(0.4);
                    System.out.println("Dealer Busted!"); 
                }
            }
            bj.sleep(1.5);
            for (int x = 0; x < numPlayerHands; x++) {
                System.out.print("player hand: " + bj.show(playerHands[x]));
                System.out.println(" score: " + bj.score(playerHands[x]));
                win = bj.playerWin(playerBlackjack, dealerBlackjack, bj.score(playerHands[x]), bj.score(dealerHand)); 
                push = bj.push(playerBlackjack, dealerBlackjack, bj.score(playerHands[x]), bj.score(dealerHand)); 
                bj.sleep(1.5);
                if ((win) && (playerBlackjack)) {
                    System.out.print("Blackjack pays 2/1, adding $" + (2 * bet[x]));
                    dollarTotal += 2 * bet[x];
                    System.out.println(" new total: " + df.format(dollarTotal));
                }
                if ((win) && (! playerBlackjack)) {
                    System.out.print("Adding $" + bet[x]);
                    dollarTotal += bet[x];
                    System.out.println(" new total: " + df.format(dollarTotal));
                }
                if ((! win) && (! push)) {
                    System.out.print("Subtracting $" + bet[x]);
                    dollarTotal -= bet[x];
                    System.out.println(" new total: " + df.format(dollarTotal));
                }
                if (push) {
                    System.out.println("Push, no change total remains " + df.format(dollarTotal));
                }
                playerHands[x] = null;
            }
            bj.sleep(3);
            bj.clearScreen();
            playerHandsIndex = 0;
            playerCardIndex = 0;
            for (int x = 0; x < 4; x++) {
                Card hand[] = new Card[21];
                playerHands[x] = hand;
            }
            dealerHand = new Card[21];
            dealerCardIndex = 0;
            dealerSoft = false;
            dealerBlackjack = false;
            playerBlackjack = false;
            splitCount = 0;
            System.out.println(((52 * numDecks) - shoeCount) + " cards left in shoe");
            System.out.println("player total: " + df.format(dollarTotal));
        }
        if (dollarTotal > 0) { System.out.println("congratulations");
        } else { System.out.println("better luck next time"); }
        System.out.println("round over, need to re-shuffle");
        System.out.println("thanks for playing");
    } 
}
// split option does not display total
