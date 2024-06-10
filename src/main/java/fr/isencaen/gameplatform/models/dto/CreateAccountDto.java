package fr.isencaen.gameplatform.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateAccountDto(
    @NotBlank(message = "Pseudo is mandatory")
    @Pattern(regexp = "^(?=\\S+$).{6,}$", message = "Pseudo must contain at least 6 characters")
    String pseudo,

    @NotBlank(message = "Mail is mandatory")
    String email,

    @NotBlank(message = "Name is mandatory")
    String name,

    //adding annotations of password policy
    @NotBlank(message = "the password is mandatory")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{10,}$", message = "Password must contain at least one digit, one lowercase, one uppercase, one special character and at least 10 characters")
    String pwd,

    String section

) {

}
