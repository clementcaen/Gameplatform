package fr.isencaen.gameplatform.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="currentgames")
public class CurrentGame implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_current_game")
    private int idCurrentGame;

    @ManyToOne
    @JoinColumn(name = "id_game")
    private Game game;

    @OneToMany(mappedBy = "currentGame")
    private List<AccountPosition> accountPlayersPositions;

    public CurrentGame() {
    }

    public CurrentGame(int idCurrentGame, Game game, List<AccountPosition> accountPlayersPositions) {
        this.idCurrentGame = idCurrentGame;
        this.game = game;
        this.accountPlayersPositions = accountPlayersPositions;
    }
    public CurrentGame(Game game, List<AccountPosition> accountPlayersPositions) {
        this.game = game;
        this.accountPlayersPositions = accountPlayersPositions;
    }

    public int getIdCurrentGame() {
        return idCurrentGame;
    }

    public List<AccountPosition> getAccountPlayersPositions() {
        return accountPlayersPositions;
    }

    public void setAccountPlayersPositions(List<AccountPosition> accountsPlayersPositions) {
        this.accountPlayersPositions = accountsPlayersPositions;
    }

    public Game getGame() {
        return game;
    }

    public String getPlayersNames() {
        StringBuilder playersPseudo = new StringBuilder();
        for (AccountPosition accountPosition : accountPlayersPositions) {
            playersPseudo.append(accountPosition.getPlayer().getPseudo()).append(", ");
        }
        return playersPseudo.toString();
    }

    public void setIdCurrentGame(int idCurrentGame) {
        this.idCurrentGame = idCurrentGame;
    }
}


