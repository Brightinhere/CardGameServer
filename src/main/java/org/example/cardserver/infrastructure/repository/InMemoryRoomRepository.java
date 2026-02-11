package org.example.cardserver.infrastructure.repository;

import org.example.cardserver.application.port.RoomRepository;
import org.example.cardserver.domain.model.Room;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryRoomRepository implements RoomRepository {

    private final Map<UUID, Room> rooms = new ConcurrentHashMap<>();

    @Override
    public Room save(Room room) {
        rooms.put(room.getId(), room);
        return room;
    }

    @Override
    public Optional<Room> findById(UUID roomId) {
        return Optional.ofNullable(rooms.get(roomId));
    }

    @Override
    public void delete(UUID roomId) {
        rooms.remove(roomId);
    }
}
