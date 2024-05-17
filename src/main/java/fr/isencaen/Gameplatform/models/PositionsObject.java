package fr.isencaen.Gameplatform.models;

import jakarta.persistence.*;

@Entity
@Table(name="positions")
public class PositionsObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    int id;
    @Column(name="piece")
    String piece;
    @Column(name="position")
    String position;

    @ManyToOne(optional = true)
    @JoinColumn(name="id_account_player_time_position")
    AccountPosition account_player_time_position;

    public PositionsObject() {

    }
    PositionsObject(int id, String piece, String position, AccountPosition account_player_time_position) {
        this.id = id;
        this.piece = piece;
        this.position = position;
        this.account_player_time_position = account_player_time_position;
    }
    public PositionsObject(String piece, String position, AccountPosition account_player_time_position) {
        this.piece = piece;
        this.position = position;
        this.account_player_time_position = account_player_time_position;
    }


    public int getId() {
        return id;
    }
    public String getPiece() {
        return piece;
    }

    public String getPosition() {
        return position;
    }
}
