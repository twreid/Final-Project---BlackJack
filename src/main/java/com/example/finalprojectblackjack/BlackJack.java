package com.example.finalprojectblackjack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class BlackJack extends Application {
    private final Deck blackJackDeck;
    private final HBox computerDeckHBox;
    private final HBox userDeckHBox;
    private final Label gameStateLabel;
    private final Label scoreLabel;
    private int userWins;
    private int computerWins;

    public BlackJack() {
        blackJackDeck = new Deck();
        computerDeckHBox = new HBox();
        userDeckHBox = new HBox();
        gameStateLabel = new Label("");
        scoreLabel = new Label("House wins: " + computerWins + "  User wins: " + userWins);
    }

    public static void main(String[] args) throws NumberFormatException {
        launch(args);
    }

    /* Methods */
    // Display the cards
    private void renderUserCards() {
        userDeckHBox.getChildren().clear();
        for (Card card : blackJackDeck.getUserDeck()) {
            userDeckHBox.getChildren().add(new ImageView(new Image(FileUtils.loadResource(card.getImagePath()))));
        }
    }

    // Display computer cards
    private void renderComputerCards() {
        computerDeckHBox.getChildren().clear();
        for (Card card : blackJackDeck.getComputerDeck()) {
            if (blackJackDeck.isUserTurn() && computerDeckHBox.getChildren().size() == 0) {
                computerDeckHBox.getChildren().add(new ImageView(new Image(FileUtils.loadResource("/cards_png/b1fv.png"))));
            } else {
                computerDeckHBox.getChildren().add(new ImageView(new Image(FileUtils.loadResource(card.getImagePath()))));
            }
        }
    }

    @Override
    public void start(Stage stage) {
        // Main container
        VBox mainVBox = new VBox();
        mainVBox.setPadding(new Insets(10, 10, 10, 10));
        mainVBox.setSpacing(10);
        mainVBox.setBackground(Background.fill(Color.DARKGREEN));

        // Computer deck hbox
        computerDeckHBox.setPadding(new Insets(0, 0, 20, 0));

        // Deck labels
        Label computerDeckLabel = new Label("House");
        Label userDeckLabel = new Label("User");

        /* Inputs */
        HBox buttonHBox = new HBox();

        // Game buttons
        Button dealButton = new Button("Deal");
        dealButton.setDisable(true);
        Button standButton = new Button("Stand");
        standButton.setDisable(true);
        Button hitButton = new Button("Hit");
        hitButton.setDisable(true);
        Button newGameButton = new Button("New Game");


        // Stand
        standButton.setOnAction(e -> {
            blackJackDeck.stand();
            renderComputerCards();
            GameState gameState = blackJackDeck.evaluateGameState();
            // Display who won
            gameStateLabel.setText(gameState.getMessage());
            if (gameState.isGameOver()) {
                if (gameState.isUserWon() != null && gameState.isUserWon()) {
                    userWins++;
                }
                if (gameState.isUserWon() != null && !gameState.isUserWon()) {
                    computerWins++;
                }
            }
            // Disable all buttons
            dealButton.setDisable(true);
            standButton.setDisable(true);
            hitButton.setDisable(true);

            // Enable new game button
            newGameButton.setDisable(false);
            scoreLabel.setText("Computer wins: " + computerWins + "  User wins: " + userWins);
        });

        // Hit
        hitButton.setOnAction(e -> {
            blackJackDeck.userHit();
            renderUserCards();
            // Special case where user busts and game instantly ends
            if (blackJackDeck.calculateDeckTotal(blackJackDeck.getUserDeck()) > 21) {
                computerWins++;
                // Update game state label text
                gameStateLabel.setText("You busted - dealer wins!");
                // Turn hole card over
                blackJackDeck.stand();
                renderComputerCards();
                // Disable all buttons
                dealButton.setDisable(true);
                standButton.setDisable(true);
                hitButton.setDisable(true);
                // Enable new game button
                newGameButton.setDisable(false);
            }
            scoreLabel.setText("Computer wins: " + computerWins + "  User wins: " + userWins);
        });


        // Add buttons to hbox
        buttonHBox.getChildren().addAll(newGameButton, dealButton, hitButton, standButton);
        buttonHBox.setPadding(new Insets(200, 0, 5, 0));
        buttonHBox.setSpacing(3);

        // Deal
        dealButton.setOnAction(e -> {
            // Keep buttons in the right spot
            buttonHBox.setPadding(new Insets(8, 0, 5, 0));
            // Add hit and stand buttons
            hitButton.setDisable(false);
            standButton.setDisable(false);
            blackJackDeck.setUserTurn(true);
            gameStateLabel.setText("Game in progress...");
            // Fill the deck (excluding Jokers)
            blackJackDeck.fill();
            // Shuffle the deck
            blackJackDeck.shuffle();
            // Deal the starting cards
            blackJackDeck.deal();
            // Display user cards
            renderUserCards();
            // Display computer card
            renderComputerCards();
            // Add labels for each deck
            dealButton.setDisable(true);
        });

        // New game
        newGameButton.setOnAction(e -> {
            // Keep buttons in the right spot
            buttonHBox.setPadding(new Insets(200, 0, 5, 0));
            blackJackDeck.setUserTurn(true);
            gameStateLabel.setText("Game in progress...");
            // Fill the deck (excluding Jokers)
            blackJackDeck.fill();
            // Shuffle the deck
            blackJackDeck.shuffle();
            // Clear computer and user cards
            renderUserCards();
            renderComputerCards();
            // Enable deal
            dealButton.setDisable(false);
            // Disable new game button
            newGameButton.setDisable(true);
        });

        // Add everything to the main container
        mainVBox.getChildren().addAll(computerDeckLabel, computerDeckHBox, gameStateLabel, userDeckLabel, userDeckHBox, buttonHBox, scoreLabel);

        // Show everything
        Scene scene = new Scene(mainVBox, 550, 550);
        stage.setTitle("BlackJack");
        stage.setScene(scene);
        stage.show();

    }
}
