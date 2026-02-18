package org.example.cardserver.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void shouldCreatePlayerWithName() {
        // Given & When
        Player player = new Player("Alice");

        // Then
        assertNotNull(player.getId());
        assertEquals("Alice", player.getName());
        assertEquals(PlayerStatus.PLAYING, player.getStatus());
        assertNotNull(player.getHand());
    }

    @Test
    void shouldInitializeWithEmptyHand() {
        // Given & When
        Player player = new Player("Bob");

        // Then
        assertTrue(player.getHand().getCards().isEmpty());
    }

    @Test
    void shouldSetStatus() {
        // Given
        Player player = new Player("Charlie");

        // When
        player.setStatus(PlayerStatus.WON);

        // Then
        assertEquals(PlayerStatus.WON, player.getStatus());
    }

    @Test
    void shouldSetName() {
        // Given
        Player player = new Player("David");

        // When
        player.setName("Dave");

        // Then
        assertEquals("Dave", player.getName());
    }

    @Test
    void shouldSetHand() {
        // Given
        Player player = new Player("Eve");
        Hand newHand = new Hand();
        newHand.addCard(new Card(Suit.HEARTS, Rank.ACE));

        // When
        player.setHand(newHand);

        // Then
        assertEquals(newHand, player.getHand());
        assertEquals(1, player.getHand().getCards().size());
    }
}

