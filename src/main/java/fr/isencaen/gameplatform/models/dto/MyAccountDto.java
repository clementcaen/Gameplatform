package fr.isencaen.gameplatform.models.dto;

import fr.isencaen.gameplatform.models.Account;

public record MyAccountDto (
        int id,
        String pseudo,
        String email,
        String name,
        String section,
        String token
){

    public MyAccountDto(Account accountCreated) {
        this(accountCreated.getUserId(), accountCreated.getPseudo(), accountCreated.getMail(), accountCreated.getName(), accountCreated.getSection(), accountCreated.getToken());
    }


}