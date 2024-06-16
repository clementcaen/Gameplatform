package fr.isencaen.gameplatform.models;

import fr.isencaen.gameplatform.models.dto.CreateAccountDto;
import fr.isencaen.gameplatform.service.JwtService;
import jakarta.persistence.*;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name="utilisateur")
public class Account implements UserDetails, java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userId;

    @Setter
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
    public Account(String pseudo, String mail, String nom, String pwd, String section) {
        this.pseudo = pseudo;
        this.mail = mail;
        this.nom = nom;
        this.pwd = hashPwd(pwd);
        this.section = section;
        //this.token = creationToken(pwd, pseudo);// old version
        this.token = (new JwtService()).generateToken(pseudo); // replace manual token generation
        role = "ROLE_USER";
    }

    public Account(CreateAccountDto createAccountDto) {
        this(createAccountDto.pseudo(), createAccountDto.email(), createAccountDto.name(), createAccountDto.pwd(), createAccountDto.section());
    }
//    public static String creationToken(String pwd, String pseudo){//old version for creating token
//        return DigestUtils.sha256Hex(pwd+pseudo+"maximeestbienmieuxquuntoken");
//    }
    public static String hashPwd(String pwd){
        return DigestUtils.sha256Hex(pwd+"maximeLePlusBeau");
    }
    public void setUserId(int userId) {//only for tests
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
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

    public int getUserId() {
        return userId;
    }


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> role);
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public String getUsername() {
        return getPseudo();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setRoleAdmin() {//just for test
        this.role = "ROLE_ADMIN";
    }
}


