import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Card {
    private final String rank;
    private final String suit;
    public static int totals = 0;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue(){
        String cardValue = this.getRank();
        try {int value = Integer.parseInt(cardValue);
        return value;
        } catch (NumberFormatException e) {
            int values = 1;
            return values; }

    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

class Deck {
    private final List<Card> cards;
    private int currentCardIndex;

    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
        currentCardIndex = 0;
    }

    private void initializeDeck() {
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};

        for (String suit : suits) {
            for (String rank : ranks) {
                Card card = new Card(rank, suit);
                cards.add(card);
            }
        }
    }

    public void shuffle() {
        Random random = new Random();
        for (int i = 0; i < cards.size(); i++) {
            int j = random.nextInt(cards.size());
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
        currentCardIndex = 0;
    }

    public Card drawCard() {
        if (currentCardIndex < cards.size()) {
            Card card = cards.get(currentCardIndex);
            currentCardIndex++;
            return card;
        } else {
            return null;
        }
    }
}

public class BlackJackJava {
    private List<Card> playerHand;
    private List<Card> dealerHand;

    public BlackJackJava() {
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
    }

    public void startGame() {
        // Create a new deck and shuffle it
        Deck deck = new Deck();
        deck.shuffle();

        // Deal two cards to the player
        playerHand.add(deck.drawCard());
        playerHand.add(deck.drawCard());

        // Deal two cards to the dealer
        dealerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());

        // Print the initial hands
        System.out.println("Player's cards: " + playerHand);
        System.out.println("Dealer's cards: " + dealerHand);

        playGame(dealerHand, playerHand, deck);
    }

    public void playGame(List<Card> dealerHand, List<Card> playerHand, Deck deck){
        boolean stand = false;
        int totals = 0;
        for (Card card : playerHand){
            System.out.println(card.getRank());
            totals+= card.getValue();
        }
        while (!stand){
        System.out.println("Your score totals: " + totals);
        System.out.println("Would you like to [H]it, [S]tand or [D]ouble?");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        answer = answer.toUpperCase();
        if (answer.charAt(0) == 'H'){
            Card newCard = deck.drawCard();
            playerHand.add(newCard);
            totals += newCard.getValue();
            System.out.println("You drew: " + newCard.getRank());
            System.out.println("New Total: " + totals);

        } else if (answer.charAt(0) == 'S') {
            System.out.println("User sticks with a total of: " + totals);
            stand = true;
        } else if (answer.charAt(0) == 'D') {
            Card newCard = deck.drawCard();
            playerHand.add(newCard);
            totals += newCard.getValue();
            System.out.println("You doubled and drew: " + newCard.getRank());
            System.out.println("Final Total: " + totals);
            stand = true;
        }
    }
        // Dealers Totals
        int dealer = 0;
        for (Card card : dealerHand){
            dealer+= card.getValue();
        }
        System.out.println("Dealer total: " + dealer);
        while(dealer < 17){
            Card newCard = deck.drawCard();
            dealerHand.add(newCard);
            dealer += newCard.getValue();
            System.out.println("Dealer total: " + dealer);
        }
        System.out.println("Dealer final total: " + dealer);


    }

    public static void main(String[] args) {
        BlackJackJava game = new BlackJackJava();
        game.startGame();
    }
}
