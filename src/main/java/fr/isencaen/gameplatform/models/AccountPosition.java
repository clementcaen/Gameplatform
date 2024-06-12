package fr.isencaen.gameplatform.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="accountplayertimepositions")
public class AccountPosition {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="id")
        private int id;

        @ManyToOne
        @JoinColumn(name="player_id")
        private Account player;
        @OneToMany(mappedBy = "accountPlayerTimePosition")
        private List<PositionsObject> positionByPieces;
        @Column(name="time")
        private int timePlayerInThisCurrentGame;

        @ManyToOne(optional = false)
        @JoinColumn(name="current_game_id")
        private CurrentGame currentGame;

        public AccountPosition() {
        }

        public AccountPosition(Account player, List<PositionsObject> positionByPieces, int timePlayerInThisCurrentGame, CurrentGame currentGame) {
            this.player = player;
            this.positionByPieces = positionByPieces;
            this.timePlayerInThisCurrentGame = timePlayerInThisCurrentGame;
            this.currentGame = currentGame;
        }

        public void setCurrentGame(CurrentGame currentGame) {
                this.currentGame = currentGame;
        }

        public CurrentGame getCurrentGame() {
                return currentGame;
        }

        public int getId() {
                return id;
        }

        public Account getPlayer() {
                return player;
        }

        public List<PositionsObject> getPositionByPieces() {
                return positionByPieces;
        }

        public void setPositionByPieces(List<PositionsObject> positionByPieces) {
                this.positionByPieces = positionByPieces;
        }

        public int getTimePlayerInThisCurrentGame() {
                return timePlayerInThisCurrentGame;
        }

        public void setTimePlayerInThisCurrentGame(int timePlayer) {
                this.timePlayerInThisCurrentGame = timePlayer;
        }

        @Override
        public String toString() {
                return "AccountPosition{" +
                        "id=" + id +
                        ", player=" + player +
                        ", positionByPieces=" + positionByPieces +
                        ", time_player_in_this_currentGame=" + timePlayerInThisCurrentGame +
                        '}';
        }
}
