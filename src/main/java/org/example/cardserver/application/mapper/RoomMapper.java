package org.example.cardserver.application.mapper;

import org.example.cardserver.domain.model.*;
import org.example.cardserver.infrastructure.dto.*;

import java.util.List;

public class RoomMapper {

    public static RoomResponse toResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                mapRoomPlayers(room.getPlayers()),
                mapGame(room.getCurrentGame())
        );
    }

    // -------- Room Level --------

    private static List<RoomPlayerResponse> mapRoomPlayers(List<Player> players) {
        return players.stream()
                .map(player -> new RoomPlayerResponse(
                        player.getId(),
                        player.getName()
                ))
                .toList();
    }

    // -------- Game Level --------

    private static GameResponse mapGame(BlackjackGame game) {
        if (game == null) return null;

        return new GameResponse(
                game.getState(),
                game.getCurrentPlayerId(),
                mapGamePlayers(game.getPlayers()),
                mapHand(game.getDealer())
        );
    }

    private static List<GamePlayerResponse> mapGamePlayers(List<Player> players) {
        return players.stream()
                .map(RoomMapper::mapGamePlayer)
                .toList();
    }

    private static GamePlayerResponse mapGamePlayer(Player player) {
        return new GamePlayerResponse(
                player.getId(),
                player.getName(),
                player.getStatus(),
                mapHand(player.getHand())
        );
    }

    // -------- Shared Hand Mapping --------

    private static HandResponse mapHand(Hand hand) {
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
