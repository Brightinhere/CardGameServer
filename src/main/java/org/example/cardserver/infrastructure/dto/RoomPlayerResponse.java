package org.example.cardserver.infrastructure.dto;

import java.util.UUID;

public record RoomPlayerResponse(
        UUID id,
        String name
) {}
