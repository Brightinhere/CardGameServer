package org.example.cardserver.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room {

    private static final int MAX_PLAYERS = 5;

    private final UUID id;
    private final List<Player> players;

    private BlackjackGame currentGame;

    public Room() {
        this.id = UUID.randomUUID();
        this.players = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public BlackjackGame getCurrentGame() {
        return currentGame;
    }

    public void addPlayer(Player player) {
        if (players.size() >= MAX_PLAYERS) {
            throw new IllegalStateException("Room is full");
        }

        if (currentGame != null && currentGame.getState() == GameState.IN_PROGRESS) {
            throw new IllegalStateException("Cannot join during active game");
        }

        players.add(player);
    }

    public void startGame() {
        if (players.isEmpty()) {
            throw new IllegalStateException("No players in room");
        }

        if (currentGame != null && currentGame.getState() == GameState.IN_PROGRESS) {
            throw new IllegalStateException("Game already running");
        }

        currentGame = new BlackjackGame(players);
        currentGame.start();
    }

    public void hit(UUID playerId) {
        validateGameActive();
        currentGame.hit(playerId);
    }

    public void stand(UUID playerId) {
        validateGameActive();
        currentGame.stand(playerId);
    }

    private void validateGameActive() {
        if (currentGame == null) {
            throw new IllegalStateException("No game started");
        }

        if (currentGame.getState() != GameState.IN_PROGRESS) {
            throw new IllegalStateException("Game is not active");
        }
    }
}


