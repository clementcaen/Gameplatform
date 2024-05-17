package fr.isencaen.Gameplatform.models.dto;
import fr.isencaen.Gameplatform.models.Account;
import fr.isencaen.Gameplatform.models.AccountPosition;
import fr.isencaen.Gameplatform.models.PositionsObject;

import java.util.List;

public record AccountPositionDto(
    int id,
    Account player,
    List<PositionsObject> positionByPieces,
    int time

) {
    public AccountPositionDto(int id, Account player, List<PositionsObject> positionByPieces, int time) {
        this.id = id;
        this.player = player;
        this.positionByPieces = positionByPieces;
        this.time = time;
    }

    public AccountPositionDto(AccountPosition accountPosition) {
        this(accountPosition.getId(), accountPosition.getPlayer(), accountPosition.getPositionByPieces(), accountPosition.getTime_player_in_this_currentGame());
    }
}
