package org.example.cardserver.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private static final int DEFAULT_DECK_SIZE = 3;
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        initializeDeck();
    }

    private void initializeDeck() {
        for (int i = 0; i < Deck.DEFAULT_DECK_SIZE; i++) {
            for(Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    cards.add(new Card(suit, rank));
                }
            }
        }
    }


    public void shuffle() {
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }

    }

    public Card draw() {
        return cards.removeFirst();
    }
}
