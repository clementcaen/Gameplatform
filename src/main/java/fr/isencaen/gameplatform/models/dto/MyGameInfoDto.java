package fr.isencaen.gameplatform.models.dto;

import fr.isencaen.gameplatform.models.CurrentGame;

public record MyGameInfoDto(
        String name_game,
        String adversaires_names, // nom de pseudo des adversaires séparés par des virgules
        int current_game_id
) {

    public static MyGameInfoDto of(CurrentGame currentGame) {
        return new MyGameInfoDto(
                currentGame.getGame().getName(),
                currentGame.getPlayersNames(),
                currentGame.getIdCurrentGame()
        );
    }
}
