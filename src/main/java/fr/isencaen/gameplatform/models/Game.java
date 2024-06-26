package fr.isencaen.gameplatform.models;

import jakarta.persistence.*;

@Entity
@Table(name="jeux")
public class Game implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_game")
    private int id;

    @Column(name="nom", unique = true)
    private String name;
    @Column(name="image", unique = true)
    private String pathImg;
    @Column(name="regles")
    private String rulesGame;

    public Game() {
    }

    public Game(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}


