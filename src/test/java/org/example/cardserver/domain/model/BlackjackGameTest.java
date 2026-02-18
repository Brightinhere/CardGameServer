package org.example.cardserver.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackGameTest {

    private List<Player> players;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        player1 = new Player("Alice");
        player2 = new Player("Bob");
        players.add(player1);
        players.add(player2);
    }

    @Test
    void shouldCreateGameWithPlayers() {
        // Given & When
        BlackjackGame game = new BlackjackGame(players);

        // Then
        assertEquals(2, game.getPlayers().size());
        assertEquals(GameState.WAITING_FOR_PLAYERS, game.getState());
        assertNotNull(game.getDealer());
    }

    @Test
    void shouldStartGame() {
        // Given
        BlackjackGame game = new BlackjackGame(players);

        // When
        game.start();

        // Then
        assertEquals(GameState.IN_PROGRESS, game.getState());
        assertNotNull(game.getCurrentPlayerId());
    }

    @Test
    void shouldDealInitialCardsOnStart() {
        // Given
        BlackjackGame game = new BlackjackGame(players);

        // When
        game.start();

        // Then
        assertEquals(2, player1.getHand().getCards().size());
        assertEquals(2, player2.getHand().getCards().size());
        assertEquals(2, game.getDealer().getCards().size());
    }

    @Test
    void shouldThrowExceptionWhenStartingWithoutPlayers() {
        // Given
        BlackjackGame game = new BlackjackGame(new ArrayList<>());

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> game.start());
        assertEquals("Not enough players to start", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenStartingTwice() {
        // Given
        BlackjackGame game = new BlackjackGame(players);
        game.start();

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> game.start());
        assertEquals("Game already started", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenHitNotYourTurn() {
        // Given
        BlackjackGame game = new BlackjackGame(players);
        game.start();
        UUID wrongPlayerId = UUID.randomUUID();

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> game.hit(wrongPlayerId));
        assertEquals("Not your turn", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenStandNotYourTurn() {
        // Given
        BlackjackGame game = new BlackjackGame(players);
        game.start();
        UUID wrongPlayerId = UUID.randomUUID();

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> game.stand(wrongPlayerId));
        assertEquals("Not your turn", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenHitGameNotActive() {
        // Given
        BlackjackGame game = new BlackjackGame(players);

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> game.hit(player1.getId()));
        assertEquals("Game not active", exception.getMessage());
    }

    @Test
    void shouldReturnImmutablePlayerList() {
        // Given
        BlackjackGame game = new BlackjackGame(players);

        // When
        var gamePlayers = game.getPlayers();

        // Then
        assertThrows(UnsupportedOperationException.class,
            () -> gamePlayers.add(new Player("Charlie")));
    }

    @Test
    void shouldReturnNullCurrentPlayerIdWhenGameNotInProgress() {
        // Given
        BlackjackGame game = new BlackjackGame(players);

        // Then
        assertNull(game.getCurrentPlayerId());
    }

    @Test
    void shouldResetPlayerStatusForNewRound() {
        // Given
        player1.setStatus(PlayerStatus.WON);
        player2.setStatus(PlayerStatus.LOST);
        BlackjackGame game = new BlackjackGame(players);

        // When
        game.start();

        // Then
        assertEquals(PlayerStatus.PLAYING, player1.getStatus());
        assertEquals(PlayerStatus.PLAYING, player2.getStatus());
    }

    @Test
    void shouldClearPlayerHandsForNewRound() {
        // Given
        player1.getHand().addCard(new Card(Suit.HEARTS, Rank.ACE));
        player2.getHand().addCard(new Card(Suit.SPADES, Rank.KING));
        BlackjackGame game = new BlackjackGame(players);

        // When
        game.start();

        // Then
        // Hands should have 2 new cards from the deal, not the old ones
        assertEquals(2, player1.getHand().getCards().size());
        assertEquals(2, player2.getHand().getCards().size());
    }
}

