package org.example.cardserver.infrastructure.controller;

import org.example.cardserver.application.mapper.RoomMapper;
import org.example.cardserver.application.service.RoomService;
import org.example.cardserver.domain.model.Room;
import org.example.cardserver.infrastructure.dto.RoomResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public Room createRoom() {
        return roomService.createRoom();
    }

    @PostMapping("/{roomId}/join")
    public Room joinRoom(@PathVariable UUID roomId,
                         @RequestParam String name) {
        return roomService.joinRoom(roomId, name);
    }

    @PostMapping("/{roomId}/start")
    public Room startGame(@PathVariable UUID roomId) {
        return roomService.startGame(roomId);
    }

    @PostMapping("/{roomId}/hit/{playerId}")
    public Room hit(@PathVariable UUID roomId,
                    @PathVariable UUID playerId) {
        return roomService.hit(roomId, playerId);
    }

    @PostMapping("/{roomId}/stand/{playerId}")
    public Room stand(@PathVariable UUID roomId,
                      @PathVariable UUID playerId) {
        return roomService.stand(roomId, playerId);
    }

    @GetMapping("/")
    public List<RoomResponse> getRooms() {
        return roomService.getRooms().stream()
                .map(RoomMapper::toResponse)
                .toList();
    }

    @GetMapping("/{roomId}")
    public RoomResponse getRoom(@PathVariable UUID roomId) {
        return RoomMapper.toResponse(roomService.getRoom(roomId));
    }
}
