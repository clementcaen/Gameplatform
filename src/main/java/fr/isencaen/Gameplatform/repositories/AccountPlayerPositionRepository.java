package fr.isencaen.Gameplatform.repositories;
import fr.isencaen.Gameplatform.models.Account;
import fr.isencaen.Gameplatform.models.AccountPosition;
import fr.isencaen.Gameplatform.models.CurrentGame;
import fr.isencaen.Gameplatform.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountPlayerPositionRepository extends JpaRepository<AccountPosition, Integer> {

    //AccountPosition findByCurrent_gameAndPlayer(CurrentGame currentGame, Account player);
}
