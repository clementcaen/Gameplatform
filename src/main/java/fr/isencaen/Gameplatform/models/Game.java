package fr.isencaen.Gameplatform.models;

import fr.isencaen.Gameplatform.models.dto.CreateAccountDto;
import jakarta.persistence.*;
import org.apache.commons.codec.digest.DigestUtils;

@Entity
@Table(name="jeux")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_game")
    private int id;

    @Column(name="nom", unique = true)
    private String name;
    @Column(name="image", unique = true)
    private String path_img;
    @Column(name="regles")
    private String rules_game;

    public Game() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath_img() {
        return path_img;
    }

    public String getRules_game() {
        return rules_game;
    }
}


