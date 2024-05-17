package fr.isencaen.Gameplatform.models.dto;

import fr.isencaen.Gameplatform.models.Account;

public record MyAccountDto (
        int id,
        String pseudo,
        String email,
        String name,
        String section,
        String token
){
    public MyAccountDto(int id, String pseudo, String email, String name, String section, String token) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.name = name;
        this.section = section;
        this.token = token;
    }

    public MyAccountDto(Account accountCreated) {
        this(accountCreated.getId(), accountCreated.getPseudo(), accountCreated.getMail(), accountCreated.getNom(), accountCreated.getSection(), accountCreated.getToken());
    }


}