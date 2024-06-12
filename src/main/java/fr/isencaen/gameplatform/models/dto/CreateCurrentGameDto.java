package fr.isencaen.gameplatform.models.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCurrentGameDto(
    @NotBlank(message = "User token is mandatory")
    String user_token_creator,

    @NotBlank(message = "the name of the game is mandatory")
    String name_game

) {

}
