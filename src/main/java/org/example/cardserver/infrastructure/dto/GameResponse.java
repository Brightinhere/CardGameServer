package org.example.cardserver.infrastructure.dto;

import org.example.cardserver.domain.model.GameState;

import java.util.List;
import java.util.UUID;

public record GameResponse(
        GameState state,
        UUID currentPlayerId,
        List<GamePlayerResponse> players,
        HandResponse dealer
) {}
