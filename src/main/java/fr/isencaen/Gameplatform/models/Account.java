package fr.isencaen.Gameplatform.models;

import fr.isencaen.Gameplatform.models.dto.CreateAccountDto;
import jakarta.persistence.*;
import org.apache.commons.codec.digest.DigestUtils;

@Entity
@Table(name="utilisateur")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int id;

    @Column(name="pseudo", unique = true)
    private String pseudo;
    @Column(name="mail", unique = true)
    private String mail;
    @Column(name="nom")
    private String nom;
    @Column(name="passwdhash")
    private String pwd;

    @Column(name="section")
    private String section;
    @Column(name="token")
    private String token;

    @Column(name = "role")
    private String role;

    public Account() {
    }

    public Account(int id, String pseudo, String mail, String nom, String pwd, String section, String token, String role) {
        this.id = id;
        this.pseudo = pseudo;
        this.mail = mail;
        this.nom = nom;
        this.pwd = DigestUtils.sha256Hex(pwd+"maximeLePlusBeau");//custom hash with salt
        this.section = section;
        this.token = token;
        this.role = role;
    }

    public Account(String pseudo, String mail, String nom, String pwd, String section) {
        this.pseudo = pseudo;
        this.mail = mail;
        this.nom = nom;
        this.pwd = hash_pwd(pwd);
        this.section = section;
        this.token = creation_token(pwd, pseudo);
        role = "ROLE_USER";
    }

    public Account(CreateAccountDto createAccountDto) {
        this(createAccountDto.pseudo(), createAccountDto.email(), createAccountDto.name(), createAccountDto.pwd(), createAccountDto.section());
    }
    public static String creation_token(String pwd, String pseudo){
        return DigestUtils.sha256Hex(pwd+pseudo+"maximeestbienmieuxquuntoken");
    }
    public static String hash_pwd(String pwd){
        return DigestUtils.sha256Hex(pwd+"maximeLePlusBeau");
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getMail() {
        return mail;
    }

    public String getPwd() {
        return pwd;
    }

    public String getNom() {
        return nom;
    }


    public String getSection() {
        return section;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return this.role;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int getId() {
        return id;
    }


}


