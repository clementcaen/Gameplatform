package fr.isencaen.gameplatform.models.dto;

public class CellInfoDto {
    private String pathImg;/*Peut avoir la chaine de caractère "empty" Exemple :square*/
    private int idUser;// peut être -1 si la case n'a pas été jouée par aucun joueur
    private String statusCase; // refused / accepted

    public CellInfoDto() {
    }

    public CellInfoDto(String pathImg, int idUser, String statusCase) {
        this.pathImg = pathImg;
        this.idUser = idUser;
        this.statusCase = statusCase;
    }

    public String getPathImg() {
        return pathImg;
    }

    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getStatusCase() {
        return statusCase;
    }

    public void setStatusCase(String statusCase) {
        this.statusCase = statusCase;
    }
}
