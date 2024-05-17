package fr.isencaen.Gameplatform.models.dto;

public record MoveDto(
        boolean moveValid,
        int id_current_game,
        String type_piece,
        int x,
        int y
){

}
