package org.example.cardserver.application.port;

import org.example.cardserver.domain.model.Room;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository {
    List<Room> findAll();
    Optional<Room> findById(UUID id);
    Room save(Room room);
    void delete(UUID roomId);
}
