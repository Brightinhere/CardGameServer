package org.example.cardserver.infrastructure.controller;

import org.example.cardserver.application.mapper.RoomMapper;
import org.example.cardserver.application.service.RoomService;
import org.example.cardserver.domain.model.Room;
import org.example.cardserver.infrastructure.dto.RoomResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomControllerTest {

    @Mock
    private RoomService roomService;

    @Mock
    private RoomMapper roomMapper;

    @InjectMocks
    private RoomController roomController;

    private Room room;
    private UUID roomId;
    private RoomResponse roomResponse;

    @BeforeEach
    void setUp() {
        room = new Room();
        roomId = room.getId();
        roomResponse = mock(RoomResponse.class);
    }

    @Test
    void shouldCreateRoom() {
        // Given
        when(roomService.createRoom()).thenReturn(room);

        // When
        Room createdRoom = roomController.createRoom();

        // Then
        assertNotNull(createdRoom);
        assertEquals(roomId, createdRoom.getId());
        verify(roomService, times(1)).createRoom();
    }

    @Test
    void shouldJoinRoom() {
        // Given
        String playerName = "Alice";
        when(roomService.joinRoom(roomId, playerName)).thenReturn(room);

        // When
        Room joinedRoom = roomController.joinRoom(roomId, playerName);

        // Then
        assertNotNull(joinedRoom);
        verify(roomService, times(1)).joinRoom(roomId, playerName);
    }

    @Test
    void shouldStartGame() {
        // Given
        when(roomService.startGame(roomId)).thenReturn(room);

        // When
        Room gameRoom = roomController.startGame(roomId);

        // Then
        assertNotNull(gameRoom);
        verify(roomService, times(1)).startGame(roomId);
    }

    @Test
    void shouldHit() {
        // Given
        UUID playerId = UUID.randomUUID();
        when(roomService.hit(roomId, playerId)).thenReturn(room);

        // When
        Room updatedRoom = roomController.hit(roomId, playerId);

        // Then
        assertNotNull(updatedRoom);
        verify(roomService, times(1)).hit(roomId, playerId);
    }

    @Test
    void shouldStand() {
        // Given
        UUID playerId = UUID.randomUUID();
        when(roomService.stand(roomId, playerId)).thenReturn(room);

        // When
        Room updatedRoom = roomController.stand(roomId, playerId);

        // Then
        assertNotNull(updatedRoom);
        verify(roomService, times(1)).stand(roomId, playerId);
    }

    @Test
    void shouldGetRooms() {
        // Given
        Room room2 = new Room();
        RoomResponse response1 = mock(RoomResponse.class);
        RoomResponse response2 = mock(RoomResponse.class);

        when(roomService.getRooms()).thenReturn(List.of(room, room2));
        when(roomMapper.toResponse(room)).thenReturn(response1);
        when(roomMapper.toResponse(room2)).thenReturn(response2);

        // When
        List<RoomResponse> rooms = roomController.getRooms();

        // Then
        assertEquals(2, rooms.size());
        verify(roomService, times(1)).getRooms();
        verify(roomMapper, times(2)).toResponse(any(Room.class));
    }

    @Test
    void shouldGetRoom() {
        // Given
        when(roomService.getRoom(roomId)).thenReturn(room);
        when(roomMapper.toResponse(room)).thenReturn(roomResponse);

        // When
        RoomResponse response = roomController.getRoom(roomId);

        // Then
        assertNotNull(response);
        verify(roomService, times(1)).getRoom(roomId);
        verify(roomMapper, times(1)).toResponse(room);
    }

    @Test
    void shouldReturnEmptyListWhenNoRooms() {
        // Given
        when(roomService.getRooms()).thenReturn(List.of());

        // When
        List<RoomResponse> rooms = roomController.getRooms();

        // Then
        assertTrue(rooms.isEmpty());
        verify(roomService, times(1)).getRooms();
        verify(roomMapper, never()).toResponse(any(Room.class));
    }
}

