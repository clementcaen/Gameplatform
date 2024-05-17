package fr.isencaen.Gameplatform.models;

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
        @OneToMany(mappedBy = "account_player_time_position")
        private List<PositionsObject> positionByPieces;
        @Column(name="time")
        private int time_player_in_this_currentGame;

        @ManyToOne(optional = false)
        @JoinColumn(name="current_game_id")
        private CurrentGame current_game;

        public AccountPosition() {
        }

        public AccountPosition(Account player, List<PositionsObject> positionByPieces, int time_player_in_this_currentGame, CurrentGame currentGame) {
            this.player = player;
            this.positionByPieces = positionByPieces;
            this.time_player_in_this_currentGame = time_player_in_this_currentGame;
            this.current_game = currentGame;
        }

        public void setCurrent_game(CurrentGame current_game) {
                this.current_game = current_game;
        }

        public CurrentGame getCurrent_game() {
                return current_game;
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

        public int getTime_player_in_this_currentGame() {
                return time_player_in_this_currentGame;
        }

        public void setTime_player_in_this_currentGame(int time_player) {
                this.time_player_in_this_currentGame = time_player;
        }

        @Override
        public String toString() {
                return "AccountPosition{" +
                        "id=" + id +
                        ", player=" + player +
                        ", positionByPieces=" + positionByPieces +
                        ", time_player_in_this_currentGame=" + time_player_in_this_currentGame +
                        '}';
        }
}
