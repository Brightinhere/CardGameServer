package org.example.cardserver.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlackjackGame {

    private final Deck deck;
    private final List<Player> players;
    private final Hand dealer;

    private GameState state;
    private int currentPlayerIndex;

    public BlackjackGame(List<Player> players) {
        this.players = new ArrayList<>(players);
        this.deck = new Deck();
        this.dealer = new Hand();
        this.state = GameState.WAITING_FOR_PLAYERS;
        this.currentPlayerIndex = 0;
    }

    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public Hand getDealer() {
        return dealer;
    }

    public GameState getState() {
        return state;
    }


    public void start() {
        validateStart();
        resetPlayersForNewRound();
        deck.shuffle();
        dealInitialCards();
        state = GameState.IN_PROGRESS;
        checkForBlackjacks();
        skipNonPlayingPlayersAtStart();
    }

    private void resetPlayersForNewRound() {
        for (Player player : players) {
            player.setStatus(PlayerStatus.PLAYING);
            player.getHand().clear();
        }
    }


    private void skipNonPlayingPlayersAtStart() {
        while (currentPlayerIndex < players.size()
                && players.get(currentPlayerIndex).getStatus() != PlayerStatus.PLAYING) {
            currentPlayerIndex++;
        }

        if (currentPlayerIndex >= players.size()) {
            finishGame();
        }
    }


    private void checkForBlackjacks() {
        boolean allDone = true;

        for (Player player : players) {
            if (player.getHand().isBlackjack()) {
                player.setStatus(PlayerStatus.BLACKJACK);
            } else {
                allDone = false;
            }
        }

        if (allDone) {
            finishGame();
        }
    }


    private void dealInitialCards() {
        for (Player player : players) {
            Hand hand = player.getHand();
            hand.addCard(deck.draw());
            hand.addCard(deck.draw());
        }
        dealer.addCard(deck.draw());
        dealer.addCard(deck.draw());
    }

    private void validateStart() {
        if (players.isEmpty())
            throw new IllegalStateException("Not enough players to start");
        if (state != GameState.WAITING_FOR_PLAYERS)
            throw new IllegalStateException("Game already started");
    }

    public void hit(UUID playerId) {
        validateTurn(playerId);
        Player player = getCurrentPlayer();
        player.getHand().addCard(deck.draw());

        if (player.getHand().isBust()) {
            moveToNextPlayer();
        }
    }

    private void moveToNextPlayer() {
        currentPlayerIndex++;

        while (currentPlayerIndex < players.size()
                && players.get(currentPlayerIndex).getStatus() != PlayerStatus.PLAYING) {
            currentPlayerIndex++;
        }

        if (currentPlayerIndex >= players.size()) {
            finishGame();
        }
    }


    private Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public UUID getCurrentPlayerId() {
        if (state != GameState.IN_PROGRESS) {
            return null;
        }
        if (currentPlayerIndex < 0 || currentPlayerIndex >= players.size()) {
            return null;
        }
        Player current = players.get(currentPlayerIndex);
        return current != null ? current.getId() : null;
    }


    public void stand(UUID playerId) {
        validateTurn(playerId);
        moveToNextPlayer();
    }

    private void dealerTurn() {
        while (dealer.calculateValue() < 17) {
            dealer.addCard(deck.draw());
        }
    }

    private void finishGame() {
        if (state == GameState.FINISHED) {
            return;
        }
        dealerTurn();
        evaluateResults();
        state = GameState.FINISHED;
    }

    private void evaluateResults() {
        int dealerValue = dealer.calculateValue();
        boolean dealerBust = dealer.isBust();
        for (Player player : players) {
            int playerValue = player.getHand().calculateValue();
            if (player.getHand().isBust()) {
                player.setStatus(PlayerStatus.BUST);
            } else if (dealerBust || playerValue > dealerValue) {
                player.setStatus(PlayerStatus.WON);
            } else if (playerValue == dealerValue) {
                player.setStatus(PlayerStatus.PUSH);
            } else {
                player.setStatus(PlayerStatus.LOST);
            }
        }
    }

    private void validateTurn(UUID playerId) {
        if (getState() != GameState.IN_PROGRESS)
            throw new IllegalStateException("Game not active");

        if (!getCurrentPlayer().getId().equals(playerId))
            throw new IllegalStateException("Not your turn");
    }
}
