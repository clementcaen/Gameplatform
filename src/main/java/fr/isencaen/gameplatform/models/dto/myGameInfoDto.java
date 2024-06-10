package fr.isencaen.gameplatform.models.dto;

import fr.isencaen.gameplatform.models.CurrentGame;

public record myGameInfoDto(
        String name_game,
        String adversaires_names, // nom de pseudo des adversaires séparés par des virgules
        int current_game_id
) {

    public static myGameInfoDto of(CurrentGame currentGame) {
        return new myGameInfoDto(
                currentGame.getGame().getName(),
                currentGame.getPlayersNames(),
                currentGame.getIdCurrentGame()
        );
    }
}
