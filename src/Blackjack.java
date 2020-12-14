// ask how many chips to start with, ask how many to bet this round,
// shuffle and deal, print cards that player has, type hit or stand,
// computer goes, says hit or stand if 16 or less or if 17 or greater, switch back to player

import java.util.*;

public class Blackjack {
    private final String[] SUITS = {"C", "D", "H", "S"};
    private final String[] RANKS = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K"};
    private char whoseTurn;
    private final Player player;
    private final Player computer;
    private List<Card> deck;
    private final Scanner in;
    private boolean computerStand;
    private boolean playerStand;
    private static double chips;
    private static double wager;
    private ArrayList<Card> has = new ArrayList<>();
    private ArrayList<Card> doesNotHave = new ArrayList<>();

    public Blackjack() {
        this.whoseTurn = 'P';
        this.player = new Player();
        this.computer = new Player();
        this.in = new Scanner(System.in);
    }

    public void play() {

        String answer = "";

        System.out.println("\nPLAYER chips: " + (int) chips);
        System.out.println("PLAYER wager: " + (int) wager);

        shuffleAndDeal();

        while (true) {
            if (whoseTurn == 'P') {
                whoseTurn = takeTurn(false);
            } else if (whoseTurn == 'C') {
                whoseTurn = takeTurn(true);
            }

            if (playerStand && computerStand) {
                if (player.handSum() > computer.handSum() && player.handSum() == 21 && player.hand.size() == 2) {
                    showHand(false);
                    showHand(true);
                    System.out.println("\nBLACKJACK, PLAYER win!");
                    chips += wager * 3/2;
                    System.out.println("PLAYER chips: " + (int) chips);
                    System.out.println("Keep going? (Y/N)");
                    answer = in.next();
                    if (answer.equals("Y") || answer.equals("y")) {
                        new Blackjack().play();
                    } else if (answer.equals("N") || answer.equals("n")) {
                        System.out.println("\nSee ya next time!");
                    }
                } else if (player.handSum() > computer.handSum() && player.handSum() <= 21) {
                    showHand(false);
                    showHand(true);
                    System.out.println("\nCongratulations, PLAYER win!");
                    chips += wager;
                    System.out.println("PLAYER chips: " + (int) chips);
                    System.out.println("Keep going? (Y/N)");
                    answer = in.next();
                    if (answer.equals("Y") || answer.equals("y")) {
                        new Blackjack().play();
                    } else if (answer.equals("N") || answer.equals("n")) {
                        System.out.println("\nSee ya next time!");
                    }
                } else if (computer.handSum() > player.handSum() && computer.handSum() <= 21){
                    showHand(false);
                    showHand(true);
                    System.out.println("\nOh, better luck next time. This game goes to the computer...");
                    chips -= wager;
                    System.out.println("PLAYER chips: " + (int) chips);
                    System.out.println("Keep going? (Y/N)");
                    answer = in.next();
                    if (answer.equals("Y") || answer.equals("y")) {
                        new Blackjack().play();
                    } else if (answer.equals("N") || answer.equals("n")) {
                        System.out.println("\nSee ya next time!");
                    }
                } else if (player.handSum() == computer.handSum() && computer.handSum() <= 21 && player.handSum() <= 21) {
                    showHand(false);
                    showHand(true);
                    System.out.println("\nIt's a tie!");
                    System.out.println("Keep going? (Y/N)");
                    answer = in.next();
                    if (answer.equals("Y") || answer.equals("y")) {
                        new Blackjack().play();
                    } else if (answer.equals("N") || answer.equals("n")) {
                        System.out.println("\nSee ya next time!");
                    }
                } else if (computer.handSum() > 21 && player.handSum() <= 21) {
                    showHand(false);
                    showHand(true);
                    System.out.println("\nCongratulations, PLAYER win!");
                    chips += wager;
                    System.out.println("PLAYER chips: " + (int) chips);
                    System.out.println("Keep going? (Y/N)");
                    answer = in.next();
                    if (answer.equals("Y") || answer.equals("y")) {
                        new Blackjack().play();
                    } else if (answer.equals("N") || answer.equals("n")) {
                        System.out.println("\nSee ya next time!");
                    }
                } else if (player.handSum() > 21 && computer.handSum() <= 21) {
                    showHand(false);
                    showHand(true);
                    System.out.println("\nOh, better luck next time. This game goes to the computer...");
                    chips -= wager;
                    System.out.println("PLAYER chips: " + (int) chips);
                    System.out.println("Keep going? (Y/N)");
                    answer = in.next();
                    if (answer.equals("Y") || answer.equals("y")) {
                        new Blackjack().play();
                    } else if (answer.equals("N") || answer.equals("n")) {
                        System.out.println("\nSee ya next time!");
                    }
                } else if (player.handSum() > 21 && computer.handSum() > 21) {
                    showHand(false);
                    showHand(true);
                    System.out.println("\nIt's a tie!");
                    System.out.println("Keep going? (Y/N)");
                    answer = in.next();
                    if (answer.equals("Y") || answer.equals("y")) {
                        new Blackjack().play();
                    } else if (answer.equals("N") || answer.equals("n")) {
                        System.out.println("\nSee ya next time!");
                    }
                }
                break;
            }
        }
    }

    public void shuffleAndDeal() {
        if (deck == null) {
            initializeDeck();
        }
        Collections.shuffle(deck);
        while (player.getHand().size() < 2) {
            player.takeCard(deck.remove(0));
            computer.takeCard(deck.remove(0));
        }
    }

    private void initializeDeck() {
        deck = new ArrayList<>(52);

        for (String suit : SUITS) {
            for (String rank : RANKS) {
                deck.add(new Card(rank, suit));
            }
        }
    }

    private char takeTurn(boolean cpu) {
        if (!cpu) {
           showHand(false);
        }

        Card card = requestCard(cpu);
        if (card == null) {
            return cpu ? 'P' : 'C';
        }

        return 'x';
    }

    private Card requestCard(boolean cpu) {
        Card card = null;

        while (true) {
            if (!cpu) {
                    System.out.print("PLAYER: Will you hit or stand? ");
                    String answer = in.nextLine().trim().toUpperCase();

                    if (answer.equals("HIT")) {
                        player.takeCard(deck.remove(0));
                        return null;
                    } else if (answer.equals("STAND")) {
                        playerStand = true;
                        return null;
                    }

                return null;

            } else {
                    System.out.println("\nCPU: Will you hit or stand?");
                    String answer = computer.getCardByNeed();

                    if (answer.equals("HIT")) {
                        computer.takeCard(deck.remove(0));
                        String hit = "The CPU will hit.";
                        System.out.println(hit);
                        return null;
                    } else if (answer.equals("STAND")) {
                        computerStand = true;
                        System.out.println("The CPU will stand.");
                        return null;
                    }

                }
            }
    }


    private void showHand(boolean cpu) {
        if (!cpu) {
            System.out.println("\nPLAYER hand: " + player.getHand());
        } else {
            System.out.println("CPU hand: " + computer.getHand());
        }
    }


    public static void main(String[] args) {
        System.out.println("###############################################################################");
        System.out.println("#                                                                             #");
        System.out.println("#   ####### #       ####### ####### #     #       # ####### ####### #     #   #");
        System.out.println("#   #     # #       #     # #       #   #         # #     # #       #   #     #");
        System.out.println("#   ######  #       ####### #       ####          # ####### #       ####      #");
        System.out.println("#   #     # #       #     # #       #   #   #     # #     # #       #   #     #");
        System.out.println("#   ####### ####### #     # ####### #     # ####### #     # ####### #     #   #");
        System.out.println("#                                                                             #");
        System.out.println("#   A human v. CPU rendition of the classic card game Blackjack               #");
        System.out.println("#                                                                             #");
        System.out.println("###############################################################################");

        Scanner in = new Scanner(System.in);
        System.out.print("\nHow many chips will you buy in for? ");
        chips = in.nextInt();

        do {
            System.out.print("\nHow many chips will you wager this round? ");
            wager = in.nextInt();

            if (wager < 1 || wager > 25) {
                System.out.print("\nPlease wager at least 1 chip and at most 25 chips.");
            } else if (chips < wager) {
                System.out.print("\nInsufficient funds.");
            }
        } while (wager < 1 || wager > 25);


        new Blackjack().play();
    }
}