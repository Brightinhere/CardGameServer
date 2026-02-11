package org.example.cardserver.domain.model;

import java.util.UUID;

public class Player {
    private UUID id;
    private String name;
    private Hand hand;
    private PlayerStatus status;

    public Player(String name) {
        this.id = UUID.randomUUID();
        this. name = name;
        this.status = PlayerStatus.PLAYING;
        this.hand = new Hand();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }
}
