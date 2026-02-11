package org.example.cardserver.domain.model;

public class Card {
    public Suit suit;
    public Rank rank;

    Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }
}
