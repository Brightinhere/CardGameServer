package org.example.cardserver.application.service;

import org.example.cardserver.application.mapper.RoomMapper;
import org.example.cardserver.application.port.RoomRepository;
import org.example.cardserver.domain.model.Player;
import org.example.cardserver.domain.model.Room;
import org.example.cardserver.infrastructure.dto.RoomResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room createRoom() {
        Room room = new Room();
        return roomRepository.save(room);
    }

    public Room getRoom(UUID roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
    }

    public Room joinRoom(UUID roomId, String playerName) {
        Room room = getRoom(roomId);
        room.addPlayer(new Player(playerName));
        return roomRepository.save(room);
    }

    public Room startGame(UUID roomId) {
        Room room = getRoom(roomId);
        room.startGame();
        return roomRepository.save(room);
    }

    public Room hit(UUID roomId, UUID playerId) {
        Room room = getRoom(roomId);
        room.hit(playerId);
        return roomRepository.save(room);
    }

    public Room stand(UUID roomId, UUID playerId) {
        Room room = getRoom(roomId);
        room.stand(playerId);
        return roomRepository.save(room);
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }
}
