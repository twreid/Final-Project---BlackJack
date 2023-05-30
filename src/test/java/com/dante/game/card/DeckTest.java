package com.dante.game.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    private Deck deck;

    @BeforeEach
    void setup() {
        deck = new Deck();
    }

    @Test
    void sizeIs52() {
        assertEquals(deck.getDeck().size(), 52);
    }

    @Test
    void isNotShuffled() {
        Stack<Card> cards = new Stack<>();
        for (Suit suit : Suit.values()) {
            for (CardType cardType : CardType.values()) {
                cards.push(new Card(suit, cardType));
            }
        }

        assertIterableEquals(cards, deck.getDeck());
    }

    @Test
    void isShuffled() {
        Stack<Card> cards = new Stack<>();
        for (Suit suit : Suit.values()) {
            for (CardType cardType : CardType.values()) {
                cards.push(new Card(suit, cardType));
            }
        }

        deck.shuffle();

        boolean isShuffled = false;

        // Nasty Would be better with hamcrest since it has a matcher that checks for order
        // but didn't want extra libs.
        for (Card card : cards) {
            if (!card.equals(deck.getDeck().pop())) {
                isShuffled = true;
            }
        }

        assertTrue(isShuffled);
    }

}