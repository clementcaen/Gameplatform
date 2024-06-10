package fr.isencaen.gameplatform.repositories;
import fr.isencaen.gameplatform.models.Account;
import fr.isencaen.gameplatform.models.CurrentGame;
import fr.isencaen.gameplatform.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface CurrentGameRepository extends JpaRepository<CurrentGame, Integer> {
    CurrentGame findByGame(Game game);

    @Query("SELECT cg FROM CurrentGame cg " +
            "INNER JOIN cg.accountPlayersPositions ap " +
            " WHERE ap.player = :account ")
    List<CurrentGame> findByAccount(@Param("account") Account account);

    //List<CurrentGame> findByAccount(Account account) throws SQLException;

}
