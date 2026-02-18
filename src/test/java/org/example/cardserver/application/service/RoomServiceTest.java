package org.example.cardserver.application.service;

import org.example.cardserver.application.port.RoomRepository;
import org.example.cardserver.domain.model.Player;
import org.example.cardserver.domain.model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    private Room room;
    private UUID roomId;

    @BeforeEach
    void setUp() {
        room = new Room();
        roomId = room.getId();
    }

    @Test
    void shouldCreateRoom() {
        // Given
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        // When
        Room createdRoom = roomService.createRoom();

        // Then
        assertNotNull(createdRoom);
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void shouldGetRoom() {
        // Given
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        // When
        Room foundRoom = roomService.getRoom(roomId);

        // Then
        assertNotNull(foundRoom);
        assertEquals(roomId, foundRoom.getId());
        verify(roomRepository, times(1)).findById(roomId);
    }

    @Test
    void shouldThrowExceptionWhenRoomNotFound() {
        // Given
        UUID nonExistentId = UUID.randomUUID();
        when(roomRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> roomService.getRoom(nonExistentId));
        assertEquals("Room not found", exception.getMessage());
        verify(roomRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void shouldJoinRoom() {
        // Given
        String playerName = "Alice";
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        // When
        Room updatedRoom = roomService.joinRoom(roomId, playerName);

        // Then
        assertNotNull(updatedRoom);
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void shouldStartGame() {
        // Given
        room.addPlayer(new Player("Alice"));
        room.addPlayer(new Player("Bob"));
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        // When
        Room updatedRoom = roomService.startGame(roomId);

        // Then
        assertNotNull(updatedRoom);
        assertNotNull(updatedRoom.getCurrentGame());
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void shouldHit() {
        // Given
        Player player = new Player("Alice");
        room.addPlayer(player);
        room.addPlayer(new Player("Bob"));
        room.startGame();
        UUID playerId = room.getCurrentGame().getCurrentPlayerId();

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        // When
        Room updatedRoom = roomService.hit(roomId, playerId);

        // Then
        assertNotNull(updatedRoom);
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void shouldStand() {
        // Given
        Player player = new Player("Alice");
        room.addPlayer(player);
        room.addPlayer(new Player("Bob"));
        room.startGame();
        UUID playerId = room.getCurrentGame().getCurrentPlayerId();

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        // When
        Room updatedRoom = roomService.stand(roomId, playerId);

        // Then
        assertNotNull(updatedRoom);
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void shouldGetAllRooms() {
        // Given
        Room room2 = new Room();
        when(roomRepository.findAll()).thenReturn(List.of(room, room2));

        // When
        List<Room> rooms = roomService.getRooms();

        // Then
        assertEquals(2, rooms.size());
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoRooms() {
        // Given
        when(roomRepository.findAll()).thenReturn(List.of());

        // When
        List<Room> rooms = roomService.getRooms();

        // Then
        assertTrue(rooms.isEmpty());
        verify(roomRepository, times(1)).findAll();
    }
}

