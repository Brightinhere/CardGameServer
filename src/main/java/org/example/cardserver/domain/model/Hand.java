package org.example.cardserver.domain.model;


import java.util.ArrayList;
import java.util.List;


public class Hand {
    private static final int BLACKJACK_VALUE = 21;
    private static final int ACE_ADJUSTMENT = 10;

    private final List<Card> cards = new ArrayList<>();

    public Hand() {}

    public void addCard(Card card) {
        cards.add(card);
    }

    public int calculateValue() {
        int value = 0;
        int aceCount = 0;
        for (Card card : cards) {
            value += card.getRank().getValue();
            if (card.getRank() == Rank.ACE) {
                aceCount++;
            }
        }

        while (value > BLACKJACK_VALUE && aceCount > 0) {
            value -= ACE_ADJUSTMENT;
            aceCount--;
        }
        return value;
    }

    public boolean isBust() {
        return calculateValue() > BLACKJACK_VALUE;
    }

    public boolean isBlackjack() {
        return calculateValue() == BLACKJACK_VALUE;
    }

    public List<Card> getCards() {
        return List.copyOf(cards);
    }

    public int getValue() {
        return calculateValue();
    }
}
