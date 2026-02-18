package org.example.cardserver.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void shouldInitializeWithCorrectNumberOfCards() {
        // Given & When
        Deck deck = new Deck();

        // Then
        // 3 decks * 52 cards = 156 cards
        for (int i = 0; i < 156; i++) {
            assertNotNull(deck.draw());
        }
    }

    @Test
    void shouldDrawCards() {
        // Given
        Deck deck = new Deck();

        // When
        Card card1 = deck.draw();
        Card card2 = deck.draw();

        // Then
        assertNotNull(card1);
        assertNotNull(card2);
    }

    @Test
    void shouldShuffleDeck() {
        // Given
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        // When
        deck1.shuffle();
        deck2.shuffle();

        // Draw a few cards from each
        Card card1 = deck1.draw();
        Card card2 = deck2.draw();

        // Then - We can't guarantee they're different due to randomness,
        // but we can at least verify shuffle doesn't break the deck
        assertNotNull(card1);
        assertNotNull(card2);
    }

    @Test
    void shouldContainAllSuits() {
        // Given
        Deck deck = new Deck();
        boolean hasHearts = false;
        boolean hasSpades = false;
        boolean hasDiamonds = false;
        boolean hasClubs = false;

        // When - Draw all cards and check for suits
        for (int i = 0; i < 156; i++) {
            Card card = deck.draw();
            if (card.getSuit() == Suit.HEARTS) hasHearts = true;
            if (card.getSuit() == Suit.SPADES) hasSpades = true;
            if (card.getSuit() == Suit.DIAMONDS) hasDiamonds = true;
            if (card.getSuit() == Suit.CLUBS) hasClubs = true;
        }

        // Then
        assertTrue(hasHearts);
        assertTrue(hasSpades);
        assertTrue(hasDiamonds);
        assertTrue(hasClubs);
    }
}

