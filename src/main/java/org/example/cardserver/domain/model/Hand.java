package org.example.cardserver.domain.model;


import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards;

    public void addCard(Card card);
    public int calculateValue();
    public boolean isBust();
    public boolean isBlackjack();
}
