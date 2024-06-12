package fr.isencaen.gameplatform.models.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAccountDto(
    @NotBlank(message = "Pseudo is mandatory")
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
