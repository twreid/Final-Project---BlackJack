package com.example.finalprojectblackjack;

public class GameState {
    // Fields
    private final boolean isGameOver;
    private final Boolean userWon;
    private final String message;

    public GameState(boolean isGameOver, Boolean userWon, String message) {
        this.isGameOver = isGameOver;
        this.userWon = userWon;
        this.message = message;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Boolean isUserWon() {
        return userWon;
    }

    public String getMessage() {
        return message;
    }
}
