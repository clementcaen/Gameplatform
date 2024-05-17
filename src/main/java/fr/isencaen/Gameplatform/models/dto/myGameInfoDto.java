package fr.isencaen.Gameplatform.models.dto;

import fr.isencaen.Gameplatform.models.CurrentGame;
import fr.isencaen.Gameplatform.models.Game;

public record myGameInfoDto(
        String name_game,
        String adversaires_names, // nom de pseudo des adversaires séparés par des virgules
        int current_game_id
) {

    public static myGameInfoDto of(CurrentGame currentGame) {
        return new myGameInfoDto(
                currentGame.getGame().getName(),
                currentGame.getPlayers_names(),
                currentGame.getId_current_game()
        );
    }
}
