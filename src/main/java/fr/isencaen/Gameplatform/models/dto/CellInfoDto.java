package fr.isencaen.Gameplatform.models.dto;

public class CellInfoDto {
    private String path_img;/*Peut avoir la chaine de caractère "empty" Exemple :square*/
    private int id_user;// peut être -1 si la case n'a pas été jouée par aucun joueur
    private String status_case; // refused / accepted

    public CellInfoDto() {
    }

    public CellInfoDto(String path_img, int id_user, String status_case) {
        this.path_img = path_img;
        this.id_user = id_user;
        this.status_case = status_case;
    }

    public String getPath_img() {
        return path_img;
    }

    public void setPath_img(String path_img) {
        this.path_img = path_img;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getStatus_case() {
        return status_case;
    }

    public void setStatus_case(String status_case) {
        this.status_case = status_case;
    }
}
