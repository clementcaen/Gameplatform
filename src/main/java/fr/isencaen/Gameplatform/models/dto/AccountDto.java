package fr.isencaen.Gameplatform.models.dto;

import java.sql.Date;

public record AccountDto(
        int id,
        String pseudo,
        String email,
        String name,
        String section
){

}