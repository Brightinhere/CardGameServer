package org.example.cardserver.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void shouldStartWithEmptyHand() {
        // Given & When
        Hand hand = new Hand();

        // Then
        assertTrue(hand.getCards().isEmpty());
        assertEquals(0, hand.getValue());
    }

    @Test
    void shouldAddCardToHand() {
        // Given
        Hand hand = new Hand();
        Card card = new Card(Suit.HEARTS, Rank.ACE);

        // When
        hand.addCard(card);

        // Then
        assertEquals(1, hand.getCards().size());
        assertEquals(card, hand.getCards().get(0));
    }

    @Test
    void shouldCalculateSimpleValue() {
        // Given
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.FIVE));
        hand.addCard(new Card(Suit.SPADES, Rank.SEVEN));

        // When
        int value = hand.calculateValue();

        // Then
        assertEquals(12, value);
    }

    @Test
    void shouldCalculateValueWithFaceCards() {
        // Given
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.KING));
        hand.addCard(new Card(Suit.SPADES, Rank.QUEEN));

        // When
        int value = hand.calculateValue();

        // Then
        assertEquals(20, value);
    }

    @Test
    void shouldCalculateValueWithAceAs11() {
        // Given
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand.addCard(new Card(Suit.SPADES, Rank.NINE));

        // When
        int value = hand.calculateValue();

        // Then
        assertEquals(20, value);
    }

    @Test
    void shouldCalculateValueWithAceAs1WhenBustOtherwise() {
        // Given
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand.addCard(new Card(Suit.SPADES, Rank.NINE));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.FIVE));

        // When
        int value = hand.calculateValue();

        // Then
        assertEquals(15, value); // ACE counts as 1 to avoid bust
    }

    @Test
    void shouldDetectBlackjack() {
        // Given
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand.addCard(new Card(Suit.SPADES, Rank.KING));

        // Then
        assertTrue(hand.isBlackjack());
        assertEquals(21, hand.getValue());
    }

    @Test
    void shouldDetectBust() {
        // Given
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.KING));
        hand.addCard(new Card(Suit.SPADES, Rank.QUEEN));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.FIVE));

        // Then
        assertTrue(hand.isBust());
        assertEquals(25, hand.getValue());
    }

    @Test
    void shouldNotBeBustWith21() {
        // Given
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.TEN));
        hand.addCard(new Card(Suit.SPADES, Rank.FIVE));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.SIX));

        // Then
        assertFalse(hand.isBust());
        assertEquals(21, hand.getValue());
    }

    @Test
    void shouldClearHand() {
        // Given
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand.addCard(new Card(Suit.SPADES, Rank.KING));

        // When
        hand.clear();

        // Then
        assertTrue(hand.getCards().isEmpty());
        assertEquals(0, hand.getValue());
    }

    @Test
    void shouldHandleMultipleAces() {
        // Given
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand.addCard(new Card(Suit.SPADES, Rank.ACE));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.NINE));

        // When
        int value = hand.calculateValue();

        // Then
        assertEquals(21, value); // 11 + 1 + 9 = 21
    }
}

