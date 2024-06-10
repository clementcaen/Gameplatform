//package fr.isencaen.gameplatform.repositories;
//
//import fr.isencaen.gameplatform.models.Account;
//import fr.isencaen.gameplatform.models.CurrentGame;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class CurrentGameRepositoryImpl implements CurrentGameRepository{
//    public List<CurrentGame> findByAccount(Account account) throws SQLException {
//        List<CurrentGame> currentGames = new ArrayList<>();
//
//        String sql = "SELECT cg.* FROM CurrentGame cg " +
//                "INNER JOIN AccountPlayersPositions ap ON cg.id = ap.current_game_id " +
//                "WHERE ap.player_id = " + account.getUserId();
//
//        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/plateform", "plateform", "plateform")) {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//
//            while (rs.next()) {
//                CurrentGame currentGame = new CurrentGame();
//                currentGame.setIdCurrentGame(rs.getInt("id_current_game"));
//                currentGames.add(currentGame);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return currentGames;
//    }
//}
