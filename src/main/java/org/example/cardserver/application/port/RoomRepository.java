package org.example.cardserver.application.port;

import org.example.cardserver.domain.model.Room;

import java.util.Optional;
import java.util.UUID;

public interface RoomRepository {
    Optional<Room> findById(UUID id);
    Room save(Room room);
    void delete(UUID roomId);
}
