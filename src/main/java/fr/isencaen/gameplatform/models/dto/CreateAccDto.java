package fr.isencaen.gameplatform.models.dto;

public class CreateAccDto {
    private String pseudo;
    private String email;
    private String name;
    private String pwd;
    private String section;

    public CreateAccDto(String pseudo, String email, String name, String pwd, String section) {
        this.pseudo = pseudo;
        this.email = email;
        this.name = name;
        this.pwd = pwd;
        this.section = section;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}

