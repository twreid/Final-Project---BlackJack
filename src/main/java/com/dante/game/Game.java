package com.dante.game;

import com.dante.game.card.Deck;
import com.dante.game.card.Hand;

public class Game {
    private static final int NUMBER_OF_STARTING_CARDS = 2;

    private final Deck deck;

    private final Hand player;

    private final Hand computer;

    private boolean isPlayerTurn;

    public Game() {
        // Initialize the Game State
        deck = new Deck();
        player = new Hand();
        computer = new Hand();
        isPlayerTurn = true;
        deck.shuffle();
    }

    public void deal() {
        for (int i = 0; i < NUMBER_OF_STARTING_CARDS; i++) {
            player.addCard(deck.nextCard());
            computer.addCard(deck.nextCard());
        }
    }

    public void hit() {
        if (isPlayerTurn) {
            player.hit(deck);
        } else {
            computer.hit(deck);
        }
    }

    public void stand() {
        isPlayerTurn = !isPlayerTurn;
    }

    public int playerScore() {
        return player.handValue();
    }

    public int computerScore() {
        return computer.handValue();
    }
}
