package org.example.cardserver.application.port;

import org.example.cardserver.domain.model.Room;

import java.util.UUID;

public interface RoomRepository {
    Room findById(UUID id);
    void save(Room room);
}
