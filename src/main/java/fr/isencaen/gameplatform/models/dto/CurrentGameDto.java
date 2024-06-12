package fr.isencaen.gameplatform.models.dto;

import fr.isencaen.gameplatform.models.AccountPosition;
import fr.isencaen.gameplatform.models.CurrentGame;

import java.util.ArrayList;
import java.util.List;

public record CurrentGameDto(
        int id_current_game,
        List<AccountPositionDto> accounts_players_positions


) {

    public CurrentGameDto(CurrentGame currentGame) {
        this(currentGame.getIdCurrentGame(), new ArrayList<>());
        for (AccountPosition accountPosition : currentGame.getAccountPlayersPositions()) {
            this.accounts_players_positions.add(new AccountPositionDto(accountPosition));
        }
    }

}
