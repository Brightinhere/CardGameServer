package org.example.cardserver.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public void shuffle();
    public Card draw();
}
