package org.example.cardserver.infrastructure.dto;

import java.util.List;

public record HandResponse(
        List<CardResponse> cards,
        int value,
        boolean blackjack,
        boolean bust
) {}