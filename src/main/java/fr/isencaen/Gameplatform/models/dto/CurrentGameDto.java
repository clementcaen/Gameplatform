package fr.isencaen.Gameplatform.models.dto;

import fr.isencaen.Gameplatform.models.AccountPosition;
import fr.isencaen.Gameplatform.models.CurrentGame;

import java.util.ArrayList;
import java.util.List;

public record CurrentGameDto(
        int id_current_game,
        List<AccountPositionDto> accounts_players_positions


) {
    public CurrentGameDto(int id_current_game, List<AccountPositionDto> accounts_players_positions) {
        this.id_current_game = id_current_game;
        this.accounts_players_positions = accounts_players_positions;
    }

    public CurrentGameDto(CurrentGame currentGame) {
        this(currentGame.getId_current_game(), new ArrayList<AccountPositionDto>());
        for (AccountPosition accountPosition : currentGame.getAccount_players_positions()) {
            this.accounts_players_positions.add(new AccountPositionDto(accountPosition));
        }
    }

}
