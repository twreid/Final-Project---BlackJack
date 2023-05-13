package com.example.finalprojectblackjack;

public class Card {
    // Fields
    private final String suit;
    private final String rank;
    private final int value;
    private final String imagePath;

    // Constructor
    public Card(String suit, String rank, int value, String imagePath) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
        this.imagePath = imagePath;
    }

    /* Getters and setters */
    // Suit
    public String getSuit() {
        return suit;
    }

    // Rank
    public String getRank() {
        return rank;
    }

    // Value
    public int getValue() {
        return value;
    }

    // Path
    public String getImagePath() {
        return imagePath;
    }
}
