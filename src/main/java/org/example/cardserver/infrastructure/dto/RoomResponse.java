package org.example.cardserver.infrastructure.dto;

import java.util.List;
import java.util.UUID;

public record RoomResponse(
        UUID id,
        List<RoomPlayerResponse> players,
        GameResponse currentGame
) {}