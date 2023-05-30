package com.dante.game.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    private Hand hand;

    @BeforeEach
    void setup() {
        hand = new Hand();
    }

    @Test
    void getHand() {
        assertEquals(0, hand.getHand().size());
    }

    @Test
    void getHandAfterAddingCard() {
        hand.addCard(new Card(Suit.CLUB, CardType.EIGHT));

        assertEquals(1, hand.getHand().size());
    }

    @Test
    void hit() {
        Deck deck = new Deck();

        hand.hit(deck);
        hand.hit(deck);

        assertEquals(2, hand.getHand().size());
    }

    @Test
    void handValueNoAce() {
        hand.addCard(new Card(Suit.CLUB, CardType.EIGHT));
        hand.addCard(new Card(Suit.HEART, CardType.FIVE));

        assertEquals(13, hand.handValue());
    }

    @Test
    void handValueAceNoBust() {
        hand.addCard(new Card(Suit.CLUB, CardType.ACE));
        hand.addCard(new Card(Suit.HEART, CardType.FIVE));

        assertEquals(16, hand.handValue());
    }

    @Test
    void handValueAceBust() {
        hand.addCard(new Card(Suit.CLUB, CardType.ACE));
        hand.addCard(new Card(Suit.HEART, CardType.FIVE));
        hand.addCard(new Card(Suit.HEART, CardType.JACK));

        assertEquals(16, hand.handValue());
    }
}