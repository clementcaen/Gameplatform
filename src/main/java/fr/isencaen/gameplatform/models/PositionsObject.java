package fr.isencaen.gameplatform.models;

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
    AccountPosition accountPlayerTimePosition;

    public PositionsObject() {

    }
    PositionsObject(int id, String piece, String position, AccountPosition accountPlayerTimePosition) {
        this.id = id;
        this.piece = piece;
        this.position = position;
        this.accountPlayerTimePosition = accountPlayerTimePosition;
    }
    public PositionsObject(String piece, String position, AccountPosition accountPlayerTimePosition) {
        this.piece = piece;
        this.position = position;
        this.accountPlayerTimePosition = accountPlayerTimePosition;
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
