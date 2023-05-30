package com.dante.game.card;

import java.util.Collections;
import java.util.Optional;
import java.util.Stack;

public class Deck {

    private final Stack<Card> deck;

    public Deck() {
        deck = new Stack<>();
        for (Suit suit : Suit.values()) {
            for (CardType cardType : CardType.values()) {
                deck.push(new Card(suit, cardType));
            }
        }
    }

    public Stack<Card> getDeck() {
        return deck;
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card nextCard() {
       return deck.pop();
    }
}
