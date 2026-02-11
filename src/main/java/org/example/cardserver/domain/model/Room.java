package org.example.cardserver.domain.model;

import java.util.List;
import java.util.UUID;

public class Room {
    private UUID id;
    private List<Player> players;
    private BlackJackGame game;
}

