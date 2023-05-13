package com.example.finalprojectblackjack;

import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

public class Deck {
    /* Fields */
    private final Stack<Card> deck;
    private final Stack<Card> computerDeck;
    private final Stack<Card> userDeck;

    // Keep track of turns
    private boolean userTurn;
    // Deck totals
    private int computerDeckTotal;
    private int userDeckTotal;

    public Deck() {
        deck = new Stack<>();
        computerDeck = new Stack<>();
        userDeck = new Stack<>();
        userTurn = true;
        computerDeckTotal = 0;
        userDeckTotal = 0;
    }

    // User turn
    public boolean isUserTurn() {
        return userTurn;
    }
    public void setUserTurn(boolean userTurn) {
        this.userTurn = userTurn;
    }

    /* Getters and setters */
    // User deck
    public Stack<Card> getUserDeck() {
        return userDeck;
    }

    // Computer deck
    public Stack<Card> getComputerDeck() {
        return computerDeck;
    }

    /* Methods */
    // Method that fills the deck without shuffling
    public void fill() {
        computerDeck.clear();
        userDeck.clear();
        deck.clear();
        /* Clubs */
        // Automatically add all club cards
        for (int i = 1; i < 14; i++) {
            // Ace counts as 1 or 11
            if (i == 1) {
                deck.add(new Card("Clubs", "Ace", 1, "/cards_png/c1.png"));
            }
            // Jack
            else if (i == 11) {
                deck.add(new Card("Clubs", "Jack", 10, "/cards_png/cj.png"));
            }
            // Queen
            else if (i == 12) {
                deck.add(new Card("Clubs", "Queen", 10, "/cards_png/cq.png"));
            }
            // King
            else if (i == 13) {
                deck.add(new Card("Clubs", "King", 10, "/cards_png/ck.png"));
            } else {
                deck.add(new Card("Clubs", String.valueOf(i), i, "/cards_png/c" + i + ".png"));
            }
        }

        // Diamonds
        for (int j = 1; j < 14; j++) {
            // Ace counts as 1 or 11
            if (j == 1) {
                deck.add(new Card("Diamonds", "Ace", 1, "/cards_png/d1.png"));
            }
            // Jack
            else if (j == 11) {
                deck.add(new Card("Diamonds", "Jack", 10, "/cards_png/dj.png"));
            }
            // Queen
            else if (j == 12) {
                deck.add(new Card("Diamonds", "Queen", 10, "/cards_png/dq.png"));
            }
            // King
            else if (j == 13) {
                deck.add(new Card("Diamonds", "King", 10, "/cards_png/dk.png"));
            } else {
                deck.add(new Card("Diamonds", String.valueOf(j), j, "/cards_png/d" + j + ".png"));
            }
        }

        // Hearts
        for (int k = 1; k < 14; k++) {
            // Ace counts as 1 or 11
            if (k == 1) {
                deck.add(new Card("Hearts", "Ace", 1, "/cards_png/h1.png"));
            }
            // Jack
            else if (k == 11) {
                deck.add(new Card("Hearts", "Jack", 10, "/cards_png/hj.png"));
            }
            // Queen
            else if (k == 12) {
                deck.add(new Card("Hearts", "Queen", 10, "/cards_png/hq.png"));
            }
            // King
            else if (k == 13) {
                deck.add(new Card("Hearts", "King", 10, "/cards_png/hk.png"));
            } else {
                deck.add(new Card("Hearts", String.valueOf(k), k, "/cards_png/h" + k + ".png"));
            }
        }

        // Spades
        for (int l = 1; l < 14; l++) {
            // Ace counts as 1 or 11
            if (l == 1) {
                deck.add(new Card("Spades", "Ace", 1, "/cards_png/s1.png"));
            }
            // Jack
            else if (l == 11) {
                deck.add(new Card("Spades", "Jack", 10, "/cards_png/sj.png"));
            }
            // Queen
            else if (l == 12) {
                deck.add(new Card("Spades", "Queen", 10, "/cards_png/sq.png"));
            }
            // King
            else if (l == 13) {
                deck.add(new Card("Spades", "King", 10, "/cards_png/sk.png"));
            } else {
                deck.add(new Card("Spades", String.valueOf(l), l, "/cards_png/s" + l + ".png"));
            }
        }

    }

    // Method that shuffles the deck
    public void shuffle() {
        Collections.shuffle(deck);
    }

    // Method that calculates the deck totals for the user
    public void calculateDeckTotals() {
        userDeckTotal = calculateDeckTotal(userDeck);
        computerDeckTotal = calculateDeckTotal(computerDeck);
    }

    // Method that deals two cards to the computer and user when the game starts
    public void deal() {
        // First card
        userDeck.add(deck.pop());
        computerDeck.add(deck.pop());
        // Second card
        userDeck.add(deck.pop());
        computerDeck.add(deck.pop());
    }

    // Method that calculates the deck total (including Aces value of 1 or 11)
    public int calculateDeckTotal(Collection<Card> cards) throws StackOverflowError {
        int t = cards.stream().map(Card::getValue).reduce(0, Integer::sum);
        if (cards.stream().anyMatch(x -> x.getRank().equals("Ace")) && (t + 10) < 22) {
            return t + 10;
        }
        return t;
    }

    // Method that gives additional cards to computer or user when requested
    public void userHit() {
        if (userTurn) {
            userDeck.add(deck.pop());
        }
    }

    // Method that hits for computer
    public void computerHit() {
        // Computer hits until total is >= 17
        while (computerDeckTotal <= 16) {
            computerDeck.add(deck.pop());
            // Add that card's value to the computer's deck value
            computerDeckTotal = calculateDeckTotal(computerDeck);
        }
        calculateDeckTotals();
    }

    // Method that ends the computer/user's turn when requested
    public void stand() {
        // Make it the computer's turn when the user decides to stand
        setUserTurn(false);
        // Calculate deck totals
        calculateDeckTotals();
        // Only have computer hit if the game is still going
        if (userDeckTotal <= 21) {
            computerHit();
        }
    }

    //
    public GameState evaluateGameState() {
        calculateDeckTotals();
        // If the computer's deck total is >= 17, it has to stand. When the computer stands, the calculated deck totals are compared, and whoever is closer to 21 wins.
        /* User */
        // User busts
        if (userDeckTotal > 21) {
            return new GameState(true, false, "You busted - dealer wins!");
        }
        // User is closer to 21 than the computer
        else if (userDeckTotal < 21 && userDeckTotal > computerDeckTotal) {
            return new GameState(true, true, "You win!");
        }
        // User has 21 (or Blackjack) and the computer doesn't
        else if (userDeckTotal == 21 && computerDeckTotal < 21) {
            return new GameState(true, true, "You got a Blackjack! You win!");
        }
        // User has 21 (Blackjack) and computer has more than 21
        else if (userDeckTotal == 21 && computerDeckTotal > 21) {
            return new GameState(true, true, "You got a Blackjack (and the dealer busted)! You win!");
        }
        // Both user and computer have 21 (or Blackjack)
        else if (userDeckTotal == 21 && computerDeckTotal == 21) {
            return new GameState(true, null, "You got a Blackjack! But so did the dealer...");
        }
        // Both user and computer have the same deck total
        else if (userDeckTotal == computerDeckTotal) {
            return new GameState(true, null, "Tie!");
        }

        /* Computer */
        // Computer busts
        else if (computerDeckTotal > 21) {
            return new GameState(true, true, "Dealer busted - you win!");
        }
        // Computer is closer to 21 than user
        else if (computerDeckTotal < 21 && computerDeckTotal > userDeckTotal) {
            return new GameState(true, false, "Dealer wins!");
        }
        // Computer has 21 (Blackjack) and user has less than 21
        else if (computerDeckTotal == 21 && userDeckTotal < 21) {
            return new GameState(true, false, "Dealer wins with a Blackjack!");
        }
        // Computer has 21 (Blackjack) and user has more than 21
        else if (computerDeckTotal == 21 && userDeckTotal > 21) {
            return new GameState(true, false, "Dealer wins with a Blackjack - and you busted!");
        }
        // Both computer and user have 21 (or Blackjack)
        else if (computerDeckTotal == 21 && userDeckTotal == 21) {
            return new GameState(true, null, "Dealer got a Blackjack - but so did you");
        }
        return new GameState(false, null, "Game in progress...");
    }
}

