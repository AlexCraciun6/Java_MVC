package model;

public class Artwork {
    private int idArtwork;
    private int artistId;
    private String titlu;
    private String tip;
    private String descriere;
    private String imagine1;
    private String imagine2;
    private String imagine3;

    // Constructor
    public Artwork(int idArtwork, int artistId, String titlu, String tip, String descriere, String imagine1, String imagine2, String imagine3) {
        this.idArtwork = idArtwork;
        this.artistId = artistId;
        this.titlu = titlu;
        this.tip = tip;
        this.descriere = descriere;
        this.imagine1 = imagine1;
        this.imagine2 = imagine2;
        this.imagine3 = imagine3;
    }

    // Getters and Setters
    public int getIdArtwork() {
        return idArtwork;
    }

    public void setIdArtwork(int idArtwork) {
        this.idArtwork = idArtwork;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getImagine1() {
        return imagine1;
    }

    public void setImagine1(String imagine1) {
        this.imagine1 = imagine1;
    }

    public String getImagine2() {
        return imagine2;
    }

    public void setImagine2(String imagine2) {
        this.imagine2 = imagine2;
    }

    public String getImagine3() {
        return imagine3;
    }

    public void setImagine3(String imagine3) {
        this.imagine3 = imagine3;
    }
}