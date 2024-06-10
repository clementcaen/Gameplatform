package fr.isencaen.gameplatform.models.dto;

public record AccountDto(
        int id,
        String pseudo,
        String email,
        String name,
        String section
){

}