import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Player {

    private final List<Card> hand;
    private final List<String> books;

    public Player() {
        this.hand = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    public List<Card> getHand() {
        return hand;
    }

    public void takeCard(Card card) {
        hand.add(card);
        sortHand();
    }

    public String getCardByNeed() {
        //decide if the cpu needs to hit or stand
        int cardRank = 0;
        for (int i = hand.size() - 1; i >= 0; i--) {
            int nextCardRank = 0;

            try {
                nextCardRank = Integer.parseInt(hand.get(i).getRank());
            } catch (NumberFormatException e) {
                switch (hand.get(i).getRank()) {
                    case "T":
                    case "J":
                    case "Q":
                    case "K":
                        nextCardRank = 10;
                    case "A":
                        nextCardRank = 11;
                }
            }

            if (cardRank + nextCardRank >= 17) {
                return "STAND";
            } else if (i != 0) {
                cardRank += nextCardRank;
            } else {
                return "HIT";
            }
        }
        return "";
    }

    private int findCard(Card card){
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getRank().equals(card.getRank())) {
                return i;
            }
        }

        return -1;
    }

    private boolean removeSets ( int index){
        books.add(hand.get(index).getRank());

        for (int i = 0; i < 4; i++) {
            hand.remove(index);
        }

        sortHand();
        sortBooks();

        return true;
    }

    private void sortHand () {
        hand.sort((a, b) -> {
            if (Card.getOrderedRank(a.getRank()) == Card.getOrderedRank(b.getRank())) {
                return Card.getOrderedSuit(a.getSuit()) - Card.getOrderedSuit(b.getSuit());
            }

            return Card.getOrderedRank(a.getRank()) - Card.getOrderedRank(b.getRank());
        });
    }

    private void sortBooks() {
        books.sort(Comparator.comparingInt(Card::getOrderedRank));
    }

    public int handSum() {
        int sum = 0;

        for (Card card : hand) {
            int nextCardRank = 0;

            try {
                nextCardRank = Integer.parseInt(card.getRank());
            } catch (NumberFormatException e) {
                switch (card.getRank()) {
                    case "T":
                    case "J":
                    case "Q":
                    case "K":
                        nextCardRank = 10;
                    case "A":
                        nextCardRank = 11;
                }

                sum += nextCardRank;
            }
        }
        return sum;
    }
}