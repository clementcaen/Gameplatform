package fr.isencaen.Gameplatform.models;

import ch.qos.logback.classic.model.LoggerModel;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="currentgames")
public class CurrentGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_current_game")
    private int id_current_game;

    @ManyToOne
    @JoinColumn(name = "id_game")
    private Game game;

    @OneToMany(mappedBy = "current_game")
    private List<AccountPosition> account_players_positions;

    public CurrentGame() {
    }

    public CurrentGame(int id_current_game, Game game, List<AccountPosition> account_players_positions) {
        this.id_current_game = id_current_game;
        this.game = game;
        this.account_players_positions = account_players_positions;
    }
    public CurrentGame(Game game, List<AccountPosition> account_players_positions) {
        this.game = game;
        this.account_players_positions = account_players_positions;
    }

    public int getId_current_game() {
        return id_current_game;
    }

    public List<AccountPosition> getAccount_players_positions() {
        return account_players_positions;
    }

    public void setAccount_players_positions(List<AccountPosition> accounts_players_positions) {
        this.account_players_positions = accounts_players_positions;
    }

    public Game getGame() {
        return game;
    }

    public String getPlayers_names() {
        StringBuilder players_pseudo = new StringBuilder();
        for (AccountPosition accountPosition : account_players_positions) {
            players_pseudo.append(accountPosition.getPlayer().getPseudo()).append(", ");
        }
        return players_pseudo.toString();
    }
}


