package model;

public class Artist {
    private int idArtist;
    private String nume;
    private String dataNasterii;
    private String locNasterii;
    private String nationalitate;
    private String fotografie;

    // Constructor
    public Artist(int idArtist, String nume, String dataNasterii, String locNasterii, String nationalitate, String fotografie) {
        this.idArtist = idArtist;
        this.nume = nume;
        this.dataNasterii = dataNasterii;
        this.locNasterii = locNasterii;
        this.nationalitate = nationalitate;
        this.fotografie = fotografie;
    }

    // Getters and Setters
    public int getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(int idArtist) {
        this.idArtist = idArtist;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(String dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public String getLocNasterii() {
        return locNasterii;
    }

    public void setLocNasterii(String locNasterii) {
        this.locNasterii = locNasterii;
    }

    public String getNationalitate() {
        return nationalitate;
    }

    public void setNationalitate(String nationalitate) {
        this.nationalitate = nationalitate;
    }

    public String getFotografie() {
        return fotografie;
    }

    public void setFotografie(String fotografie) {
        this.fotografie = fotografie;
    }
}