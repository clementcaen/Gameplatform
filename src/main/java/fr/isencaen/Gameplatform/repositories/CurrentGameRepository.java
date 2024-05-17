package fr.isencaen.Gameplatform.repositories;
import fr.isencaen.Gameplatform.models.Account;
import fr.isencaen.Gameplatform.models.AccountPosition;
import fr.isencaen.Gameplatform.models.CurrentGame;
import fr.isencaen.Gameplatform.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CurrentGameRepository extends JpaRepository<CurrentGame, Integer> {
    CurrentGame findByGame(Game game);

    @Query("SELECT cg FROM CurrentGame cg " +
            "INNER JOIN cg.account_players_positions ap " +
            " WHERE ap.player = :account ")
    List<CurrentGame> findByAccount(@Param("account") Account account);
}
