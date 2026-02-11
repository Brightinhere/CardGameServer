package org.example.cardserver.infrastructure.dto;

import org.example.cardserver.domain.model.PlayerStatus;

import java.util.UUID;

public record GamePlayerResponse(
        UUID id,
        String name,
        PlayerStatus status,
        HandResponse hand
) {}