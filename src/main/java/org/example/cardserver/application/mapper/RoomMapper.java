package org.example.cardserver.application.mapper;

import org.example.cardserver.domain.model.*;
import org.example.cardserver.infrastructure.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomMapper {

    public RoomResponse toResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                mapRoomPlayers(room.getPlayers()),
                mapGame(room.getCurrentGame())
        );
    }

    // -------- Room Level --------

    private List<RoomPlayerResponse> mapRoomPlayers(List<Player> players) {
        return players.stream()
                .map(player -> new RoomPlayerResponse(
                        player.getId(),
                        player.getName()
                ))
                .toList();
    }

    // -------- Game Level --------

    private GameResponse mapGame(BlackjackGame game) {
        if (game == null) return null;

        return new GameResponse(
                game.getState(),
                game.getCurrentPlayerId(),
                mapGamePlayers(game.getPlayers()),
                mapHand(game.getDealer())
        );
    }

    private List<GamePlayerResponse> mapGamePlayers(List<Player> players) {
        return players.stream()
                .map(this::mapGamePlayer)
                .toList();
    }

    private GamePlayerResponse mapGamePlayer(Player player) {
        return new GamePlayerResponse(
                player.getId(),
                player.getName(),
                player.getStatus(),
                mapHand(player.getHand())
        );
    }

    // -------- Shared Hand Mapping --------

    private HandResponse mapHand(Hand hand) {
        return new HandResponse(
                hand.getCards().stream()
                        .map(card -> new CardResponse(
                                card.getSuit().name(),
                                card.getRank().name()
                        ))
                        .toList(),
                hand.getValue(),
                hand.isBlackjack(),
                hand.isBust()
        );
    }
}
