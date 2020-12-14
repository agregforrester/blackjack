import java.util.ArrayList;
import java.util.List;

public class Player {

    public final List<Card> hand;
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

    private void sortHand() {
        hand.sort((a, b) -> {
            if (Card.getOrderedRank(a.getRank()) == Card.getOrderedRank(b.getRank())) {
                return Card.getOrderedSuit(a.getSuit()) - Card.getOrderedSuit(b.getSuit());
            }

            return Card.getOrderedRank(a.getRank()) - Card.getOrderedRank(b.getRank());
        });
    }

    public int handSum() {
        int sum = 0;
        int nextCardRank = 0;

        for (Card card : hand) {
                switch (card.getRank()) {
                    case "2":
                        nextCardRank = 2;
                        break;
                    case "3":
                        nextCardRank = 3;
                        break;
                    case "4":
                        nextCardRank = 4;
                        break;
                    case "5":
                        nextCardRank = 5;
                        break;
                    case "6":
                        nextCardRank = 6;
                        break;
                    case "7":
                        nextCardRank = 7;
                        break;
                    case "8":
                        nextCardRank = 8;
                        break;
                    case "9":
                        nextCardRank = 9;
                        break;
                    case "T":
                    case "J":
                    case "Q":
                    case "K":
                        nextCardRank = 10;
                        break;
                    case "A":
                        if (sum > 10) {
                            nextCardRank = 1;
                        } else {
                            nextCardRank = 11;
                        }
                        break;
                }

                sum = sum + nextCardRank;
            }
        return sum;
        }
    }

