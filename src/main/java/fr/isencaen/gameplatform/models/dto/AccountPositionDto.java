package fr.isencaen.gameplatform.models.dto;
import fr.isencaen.gameplatform.models.AccountPosition;
import fr.isencaen.gameplatform.models.PositionsObject;

import java.util.List;

public record AccountPositionDto(
    int id,
    AccountDto player,
    List<PositionsObject> positionByPieces,
    int time

) {

    public AccountPositionDto(AccountPosition accountPosition) {
        this(accountPosition.getId(), new AccountDto(accountPosition.getPlayer().getUserId(), accountPosition.getPlayer().getPseudo(), accountPosition.getPlayer().getMail(), accountPosition.getPlayer().getName(), accountPosition.getPlayer().getSection()), accountPosition.getPositionByPieces(), accountPosition.getTimePlayerInThisCurrentGame());
    }
}
