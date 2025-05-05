package model.repository;

import model.Artwork;
import model.Observable;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtworkRepository extends Observable {
    private Repository repository;

    // Constructor
    public ArtworkRepository() {
        this.repository = new Repository();
    }

    // Metoda pentru adăugarea unei opere de artă
    public boolean addArtwork(Artwork artwork) {
        String sql = "INSERT INTO Artwork(artist_id, titlu, tip, descriere, imagine1, imagine2, imagine3) VALUES("
                + artwork.getArtistId() + ", '"
                + artwork.getTitlu() + "', '"
                + artwork.getTip() + "', '"
                + artwork.getDescriere() + "', '"
                + artwork.getImagine1() + "', '"
                + artwork.getImagine2() + "', '"
                + artwork.getImagine3() + "')";
        boolean result = repository.executeSQLCommand(sql);
        if (result) {
            notifyObservers();
        }
        return result;
    }

    // Metoda pentru ștergerea unei opere de artă după id
    public boolean deleteArtwork(int idArtwork) {
        String sql = "DELETE FROM Artwork WHERE id_artwork = " + idArtwork;
        boolean result = repository.executeSQLCommand(sql);
        if (result) {
            notifyObservers();
        }
        return result;
    }

    // Metoda pentru actualizarea unei opere de artă
    public boolean updateArtwork(Artwork artwork) {
        String sql = "UPDATE Artwork SET artist_id = " + artwork.getArtistId()
                + ", titlu = '" + artwork.getTitlu()
                + "', tip = '" + artwork.getTip()
                + "', descriere = '" + artwork.getDescriere()
                + "', imagine1 = '" + artwork.getImagine1()
                + "', imagine2 = '" + artwork.getImagine2()
                + "', imagine3 = '" + artwork.getImagine3()
                + "' WHERE id_artwork = " + artwork.getIdArtwork();
        boolean result = repository.executeSQLCommand(sql);
        if (result) {
            notifyObservers();
        }
        return result;
    }

    // Metoda pentru obținerea tuturor operelor de artă
    public List<Artwork> getAllArtworks() {
        List<Artwork> artworks = new ArrayList<>();
        String sql = "SELECT * FROM Artwork";
        ResultSet resultSet = repository.executeSQLQuery(sql);

        try {
            while (resultSet.next()) {
                Artwork artwork = new Artwork(
                        resultSet.getInt("id_artwork"),
                        resultSet.getInt("artist_id"),
                        resultSet.getString("titlu"),
                        resultSet.getString("tip"),
                        resultSet.getString("descriere"),
                        resultSet.getString("imagine1"),
                        resultSet.getString("imagine2"),
                        resultSet.getString("imagine3")
                );
                artworks.add(artwork);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artworks;
    }

    // Metoda pentru căutarea unei opere de artă după titlu
    public List<Artwork> searchArtworkByTitle(String title) {
        List<Artwork> artworks = new ArrayList<>();
        String sql = "SELECT * FROM Artwork WHERE titlu LIKE '%" + title + "%'";
        ResultSet resultSet = repository.executeSQLQuery(sql);

        try {
            while (resultSet.next()) {
                Artwork artwork = new Artwork(
                        resultSet.getInt("id_artwork"),
                        resultSet.getInt("artist_id"),
                        resultSet.getString("titlu"),
                        resultSet.getString("tip"),
                        resultSet.getString("descriere"),
                        resultSet.getString("imagine1"),
                        resultSet.getString("imagine2"),
                        resultSet.getString("imagine3")
                );
                artworks.add(artwork);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artworks;
    }

    // Metoda pentru obținerea operelor de artă ale unui artist specific
    public List<Artwork> getArtworksByArtistId(int artistId) {
        List<Artwork> artworks = new ArrayList<>();
        String sql = "SELECT * FROM Artwork WHERE artist_id = " + artistId;
        ResultSet resultSet = repository.executeSQLQuery(sql);

        try {
            while (resultSet.next()) {
                Artwork artwork = new Artwork(
                        resultSet.getInt("id_artwork"),
                        resultSet.getInt("artist_id"),
                        resultSet.getString("titlu"),
                        resultSet.getString("tip"),
                        resultSet.getString("descriere"),
                        resultSet.getString("imagine1"),
                        resultSet.getString("imagine2"),
                        resultSet.getString("imagine3")
                );
                artworks.add(artwork);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artworks;
    }

    // Metoda pentru salvarea listei de opere de artă în fișier CSV
    public boolean saveArtworksToCSV(List<Artwork> artworks, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("ID,Titlu,Tip,Descriere,Imagine1,Imagine2,Imagine3\n");
            for (Artwork artwork : artworks) {
                writer.write(artwork.getIdArtwork() + ",");
                writer.write(artwork.getTitlu() + ",");
                writer.write(artwork.getTip() + ",");
                writer.write(artwork.getDescriere() + ",");
                writer.write(artwork.getImagine1() + ",");
                writer.write(artwork.getImagine2() + ",");
                writer.write(artwork.getImagine3() + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Metoda pentru salvarea listei de opere de artă în fișier DOC
    public boolean saveArtworksToDOC(List<Artwork> artworks, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Lista Opere de Artă\n\n");
            for (Artwork artwork : artworks) {
                writer.write("ID: " + artwork.getIdArtwork() + "\n");
                writer.write("Titlu: " + artwork.getTitlu() + "\n");
                writer.write("Tip: " + artwork.getTip() + "\n");
                writer.write("Descriere: " + artwork.getDescriere() + "\n");
                writer.write("Imagine 1: " + artwork.getImagine1() + "\n");
                writer.write("Imagine 2: " + artwork.getImagine2() + "\n");
                writer.write("Imagine 3: " + artwork.getImagine3() + "\n\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}