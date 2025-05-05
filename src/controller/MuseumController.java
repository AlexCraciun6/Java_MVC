package controller;

import model.Artist;
import model.Artwork;
import model.Observer;
import model.repository.ArtistRepository;
import model.repository.ArtworkRepository;
import view.MuseumGUI;

import java.util.ArrayList;
import java.util.List;

public class MuseumController {
    private final ArtistRepository artistRepository;
    private final ArtworkRepository artworkRepository;
    private final MuseumGUI view;

    public MuseumController(MuseumGUI view) {
        this.view = view;
        this.artistRepository = new ArtistRepository();
        this.artworkRepository = new ArtworkRepository();

        // Register view as observer for both repositories
        artistRepository.addObserver((Observer) view);
        artworkRepository.addObserver((Observer) view);
    }

    // Artist management methods
    public void addArtist(String name, String birthDate, String birthPlace, String nationality, String photo) {
        try {
            if (name.isEmpty() || birthDate.isEmpty() || birthPlace.isEmpty() || nationality.isEmpty()) {
                view.showMessage("Error", "All fields are required!");
                return;
            }

            Artist artist = new Artist(0, name, birthDate, birthPlace, nationality, photo);
            boolean result = artistRepository.addArtist(artist);

            if (result) {
                view.showMessage("Success", "Artist added successfully!");
                loadArtists();
            } else {
                view.showMessage("Error", "Failed to add artist!");
            }
        } catch (Exception e) {
            view.showMessage("Error", e.getMessage());
        }
    }

    public void updateArtist(String name, String birthDate, String birthPlace, String nationality, String photo) {
        try {
            if (name.isEmpty() || birthDate.isEmpty() || birthPlace.isEmpty() || nationality.isEmpty()) {
                view.showMessage("Error", "All fields are required!");
                return;
            }

            // Search for the artist by name
            List<Artist> artists = artistRepository.searchArtistByName(name);
            if (artists.isEmpty()) {
                view.showMessage("Error", "Artist not found!");
                return;
            }

            // Update the artist
            Artist artist = artists.get(0);
            artist.setNume(name);
            artist.setDataNasterii(birthDate);
            artist.setLocNasterii(birthPlace);
            artist.setNationalitate(nationality);
            artist.setFotografie(photo);

            boolean result = artistRepository.updateArtist(artist);

            if (result) {
                view.showMessage("Success", "Artist updated successfully!");
                loadArtists();
            } else {
                view.showMessage("Error", "Failed to update artist!");
            }
        } catch (Exception e) {
            view.showMessage("Error", e.getMessage());
        }
    }

    public void deleteArtist(String name) {
        try {
            if (name.isEmpty()) {
                view.showMessage("Error", "Artist name is required!");
                return;
            }

            // Search for the artist by name
            List<Artist> artists = artistRepository.searchArtistByName(name);
            if (artists.isEmpty()) {
                view.showMessage("Error", "Artist not found!");
                return;
            }

            // Delete the artist
            Artist artist = artists.get(0);
            boolean result = artistRepository.deleteArtist(artist.getIdArtist());

            if (result) {
                view.showMessage("Success", "Artist deleted successfully!");
                loadArtists();
            } else {
                view.showMessage("Error", "Failed to delete artist!");
            }
        } catch (Exception e) {
            view.showMessage("Error", e.getMessage());
        }
    }

    public void searchArtist(String name) {
        try {
            if (name.isEmpty()) {
                view.showMessage("Error", "Artist name is required!");
                return;
            }

            List<Artist> artists = artistRepository.searchArtistByName(name);
            if (artists.isEmpty()) {
                view.showMessage("Info", "No artists found!");
            } else {
                displayArtists(artists);
            }
        } catch (Exception e) {
            view.showMessage("Error", e.getMessage());
        }
    }

    // Artwork management methods
    public void addArtwork(int artistId, String title, String type, String description, String image1, String image2, String image3) {
        try {
            if (artistId <= 0) {
                view.showMessage("Error", "Please enter a valid Artist ID!");
                return;
            }

            if (title.isEmpty() || type.isEmpty() || description.isEmpty()) {
                view.showMessage("Error", "Title, type, and description are required!");
                return;
            }

            Artwork artwork = new Artwork(0, artistId, title, type, description, image1, image2, image3);
            boolean result = artworkRepository.addArtwork(artwork);

            if (result) {
                view.showMessage("Success", "Artwork added successfully!");
                loadArtworks();
            } else {
                view.showMessage("Error", "Failed to add artwork!");
            }
        } catch (Exception e) {
            view.showMessage("Error", e.getMessage());
        }
    }

    public void updateArtwork(int artistId, String title, String type, String description, String image1, String image2, String image3) {
        try {
            if (artistId <= 0) {
                view.showMessage("Error", "Please enter a valid Artist ID!");
                return;
            }

            if (title.isEmpty() || type.isEmpty() || description.isEmpty()) {
                view.showMessage("Error", "Title, type, and description are required!");
                return;
            }

            List<Artwork> artworks = artworkRepository.searchArtworkByTitle(title);
            if (artworks.isEmpty()) {
                view.showMessage("Error", "Artwork not found!");
                return;
            }

            Artwork artwork = artworks.get(0);
            artwork.setArtistId(artistId);
            artwork.setTitlu(title);
            artwork.setTip(type);
            artwork.setDescriere(description);
            artwork.setImagine1(image1);
            artwork.setImagine2(image2);
            artwork.setImagine3(image3);

            boolean result = artworkRepository.updateArtwork(artwork);

            if (result) {
                view.showMessage("Success", "Artwork updated successfully!");
                loadArtworks();
            } else {
                view.showMessage("Error", "Failed to update artwork!");
            }
        } catch (Exception e) {
            view.showMessage("Error", e.getMessage());
        }
    }

    public void deleteArtwork(String title) {
        try {
            if (title.isEmpty()) {
                view.showMessage("Error", "Artwork title is required!");
                return;
            }

            List<Artwork> artworks = artworkRepository.searchArtworkByTitle(title);
            if (artworks.isEmpty()) {
                view.showMessage("Error", "Artwork not found!");
                return;
            }

            Artwork artwork = artworks.get(0);
            boolean result = artworkRepository.deleteArtwork(artwork.getIdArtwork());

            if (result) {
                view.showMessage("Success", "Artwork deleted successfully!");
                loadArtworks();
            } else {
                view.showMessage("Error", "Failed to delete artwork!");
            }
        } catch (Exception e) {
            view.showMessage("Error", e.getMessage());
        }
    }

    public void searchArtwork(String title) {
        try {
            if (title.isEmpty()) {
                view.showMessage("Error", "Artwork title is required!");
                return;
            }

            List<Artwork> artworks = artworkRepository.searchArtworkByTitle(title);
            if (artworks.isEmpty()) {
                view.showMessage("Info", "No artworks found!");
            } else {
                displayArtworks(artworks);
            }
        } catch (Exception e) {
            view.showMessage("Error", e.getMessage());
        }
    }

    // Data loading methods
    public void loadArtists() {
        try {
            List<Artist> artists = artistRepository.getAllArtists();
            if (artists.isEmpty()) {
                view.showMessage("Info", "No artists found in the database!");
            } else {
                displayArtists(artists);
            }
        } catch (Exception e) {
            view.showMessage("Error", "Failed to load artists: " + e.getMessage());
        }
    }

    public void loadArtworks() {
        try {
            List<Artwork> artworks = artworkRepository.getAllArtworks();
            if (artworks.isEmpty()) {
                view.showMessage("Info", "No artworks found in the database!");
            } else {
                displayArtworks(artworks);
            }
        } catch (Exception e) {
            view.showMessage("Error", "Failed to load artworks: " + e.getMessage());
        }
    }

    public void loadArtistArtworks(int artistId) {
        try {
            List<Artwork> artworks = artworkRepository.getArtworksByArtistId(artistId);
            if (artworks.isEmpty()) {
                view.showMessage("Info", "This artist has no artworks in the museum.");
            } else {
                // Create a list of artwork titles to display
                List<String> artworkTitles = new ArrayList<>();
                for (Artwork artwork : artworks) {
                    artworkTitles.add(artwork.getTitlu() + " (" + artwork.getTip() + ")");
                }
                // Display the list of artworks
                view.displayArtistArtworks(artworkTitles);
            }
        } catch (Exception e) {
            view.showMessage("Error", "Failed to load artist's artworks: " + e.getMessage());
        }
    }

    // File export methods
    public void saveArtworksToCSV() {
        try {
            List<Artwork> artworks = artworkRepository.getAllArtworks();
            if (artworks.isEmpty()) {
                view.showMessage("Error", "No artworks to save!");
                return;
            }

            String filePath = "artworks.csv";
            boolean result = artworkRepository.saveArtworksToCSV(artworks, filePath);

            if (result) {
                view.showMessage("Success", "Artworks saved to CSV successfully!");
            } else {
                view.showMessage("Error", "Failed to save artworks to CSV!");
            }
        } catch (Exception e) {
            view.showMessage("Error", e.getMessage());
        }
    }

    public void saveArtworksToDOC() {
        try {
            List<Artwork> artworks = artworkRepository.getAllArtworks();
            if (artworks.isEmpty()) {
                view.showMessage("Error", "No artworks to save!");
                return;
            }

            String filePath = "artworks.doc";
            boolean result = artworkRepository.saveArtworksToDOC(artworks, filePath);

            if (result) {
                view.showMessage("Success", "Artworks saved to DOC successfully!");
            } else {
                view.showMessage("Error", "Failed to save artworks to DOC!");
            }
        } catch (Exception e) {
            view.showMessage("Error", e.getMessage());
        }
    }

    // Filter method
    public void filterArtworks(String artistName, String artworkType) {
        try {
            if (artistName.isEmpty() && artworkType.isEmpty()) {
                view.showMessage("Error", "Please enter at least one filter criteria!");
                return;
            }

            List<Artwork> artworks = artistRepository.filterArtworks(artistName, artworkType);

            if (artworks.isEmpty()) {
                view.showMessage("Info", "No artworks found matching your criteria!");
            } else {
                displayArtworks(artworks);
                view.showMessage("Success", "Found " + artworks.size() + " artworks matching your criteria.");
            }
        } catch (Exception e) {
            view.showMessage("Error", "Error filtering artworks: " + e.getMessage());
        }
    }

    // Helper methods for displaying data
    private void displayArtists(List<Artist> artists) {
        Object[][] data = new Object[artists.size()][6];
        for (int i = 0; i < artists.size(); i++) {
            Artist artist = artists.get(i);
            data[i][0] = artist.getIdArtist();
            data[i][1] = artist.getNume();
            data[i][2] = artist.getDataNasterii();
            data[i][3] = artist.getLocNasterii();
            data[i][4] = artist.getNationalitate();
            data[i][5] = artist.getFotografie();
        }

        String[] columnNames = {"ID", "Name", "Birth Date", "Birth Place", "Nationality", "Photo"};
        view.setArtistTable(data, columnNames);
    }

    private void displayArtworks(List<Artwork> artworks) {
        Object[][] data = new Object[artworks.size()][8];
        for (int i = 0; i < artworks.size(); i++) {
            Artwork artwork = artworks.get(i);
            data[i][0] = artwork.getIdArtwork();
            data[i][1] = artwork.getArtistId();
            data[i][2] = artwork.getTitlu();
            data[i][3] = artwork.getTip();
            data[i][4] = artwork.getDescriere();
            data[i][5] = artwork.getImagine1();
            data[i][6] = artwork.getImagine2();
            data[i][7] = artwork.getImagine3();
        }

        String[] columnNames = {"ID", "Artist ID", "Title", "Type", "Description", "Image 1", "Image 2", "Image 3"};
        view.setArtworkTable(data, columnNames);
    }
}