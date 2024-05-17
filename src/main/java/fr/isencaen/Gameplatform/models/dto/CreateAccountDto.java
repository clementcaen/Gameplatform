package fr.isencaen.Gameplatform.models.dto;

import jakarta.validation.constraints.NotBlank;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public record CreateAccountDto(
    @NotBlank(message = "pseudo is mandatory")
    String pseudo,

    @NotBlank(message = "Mail is mandatory")
    String email,

    @NotBlank(message = "Name is mandatory")
    String name,

    @NotBlank(message = "the password is mandatory")
    String pwd,

    String section

) {

}
