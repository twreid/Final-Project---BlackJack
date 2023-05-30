package com.dante.game.card;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Card> hand;
    // Optimization to not have to constantly check for it.
    private boolean containsAce;

    public Hand() {
        hand = new ArrayList<>();
        containsAce = false;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void hit(Deck deck) {
       addCard(deck.nextCard());
    }

    public void addCard(Card card) {
        if (card.cardType() == CardType.ACE) {
            containsAce = true;
        }
        hand.add(card);
    }

    public int handValue() {
        int value = hand.stream().map(Card::cardType).map(CardType::getValue).reduce(0, Integer::sum);

        if (containsAce) {
            if ((value + 10) > 21) {
                return value;
            } else {
                return value + 10;
            }
        }

        return value;
    }
}
