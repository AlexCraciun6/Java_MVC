package controller;

import model.Artist;
import model.Artwork;
import model.Observer;
import model.viewmodel.MuseumViewModel;
import view.MuseumGUI;

import java.util.List;

public class MuseumController {
    private final MuseumViewModel viewModel;
    private final MuseumGUI view;

    public MuseumController(MuseumGUI view) {
        this.view = view;
        this.viewModel = new MuseumViewModel();

        // Register view as observer for the ViewModel
        viewModel.addObserver((Observer) view);
    }

    // Artist management methods
    public void addArtist(String name, String birthDate, String birthPlace, String nationality, String photo) {
        try {
            if (name.isEmpty() || birthDate.isEmpty() || birthPlace.isEmpty() || nationality.isEmpty()) {
                view.showMessage("Error", "All fields are required!");
                return;
            }

            boolean result = viewModel.addArtist(new model.Artist(0, name, birthDate, birthPlace, nationality, photo));

            if (result) {
                view.showMessage("Success", "Artist added successfully!");
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
            var artists = viewModel.searchArtists(name);
            if (artists.isEmpty()) {
                view.showMessage("Error", "Artist not found!");
                return;
            }

            // Update the artist
            var artist = artists.get(0);
            artist.setNume(name);
            artist.setDataNasterii(birthDate);
            artist.setLocNasterii(birthPlace);
            artist.setNationalitate(nationality);
            artist.setFotografie(photo);

            boolean result = viewModel.updateArtist(artist);

            if (result) {
                view.showMessage("Success", "Artist updated successfully!");
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
            var artists = viewModel.searchArtists(name);
            if (artists.isEmpty()) {
                view.showMessage("Error", "Artist not found!");
                return;
            }

            // Delete the artist
            var artist = artists.get(0);
            boolean result = viewModel.deleteArtist(artist.getIdArtist());

            if (result) {
                view.showMessage("Success", "Artist deleted successfully!");
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
                view.showMessage("Error", "Please enter an artist name to search.");
                return;
            }

            var artists = viewModel.searchArtists(name);
            if (artists.isEmpty()) {
                view.showMessage("Information", "No artists found with name: " + name);
            } else {
                displayArtists(artists);
                view.showMessage("Success", "Found " + artists.size() + " artists with name: " + name);
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

            model.Artwork artwork = new model.Artwork(0, artistId, title, type, description, image1, image2, image3);
            boolean result = viewModel.addArtwork(artwork);

            if (result) {
                view.showMessage("Success", "Artwork added successfully!");
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

            var artworks = viewModel.searchArtworks(title);
            if (artworks.isEmpty()) {
                view.showMessage("Error", "Artwork not found!");
                return;
            }

            var artwork = artworks.get(0);
            artwork.setArtistId(artistId);
            artwork.setTitlu(title);
            artwork.setTip(type);
            artwork.setDescriere(description);
            artwork.setImagine1(image1);
            artwork.setImagine2(image2);
            artwork.setImagine3(image3);

            boolean result = viewModel.updateArtwork(artwork);

            if (result) {
                view.showMessage("Success", "Artwork updated successfully!");
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

            var artworks = viewModel.searchArtworks(title);
            if (artworks.isEmpty()) {
                view.showMessage("Error", "Artwork not found!");
                return;
            }

            var artwork = artworks.get(0);
            boolean result = viewModel.deleteArtwork(artwork.getIdArtwork());

            if (result) {
                view.showMessage("Success", "Artwork deleted successfully!");
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

            var artworks = viewModel.searchArtworks(title);
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
            viewModel.loadArtists();
            var artists = viewModel.getCurrentArtists();
            if (artists.isEmpty()) {
                view.showMessage("Info", "No artists found in the database!");
            } else {
                Object[][] artistData = viewModel.getArtistsAsTableData();
                String[] columnNames = viewModel.getArtistTableColumnNames();
                view.setArtistTable(artistData, columnNames);
            }
        } catch (Exception e) {
            view.showMessage("Error", "Failed to load artists: " + e.getMessage());
        }
    }

    public void loadArtworks() {
        try {
            viewModel.loadArtworks();
            var artworks = viewModel.getCurrentArtworks();
            if (artworks.isEmpty()) {
                view.showMessage("Info", "No artworks found in the database!");
            } else {
                Object[][] artworkData = viewModel.getArtworksAsTableData();
                String[] columnNames = viewModel.getArtworkTableColumnNames();
                view.setArtworkTable(artworkData, columnNames);
            }
        } catch (Exception e) {
            view.showMessage("Error", "Failed to load artworks: " + e.getMessage());
        }
    }

    public void loadArtistArtworks(int artistId) {
        try {
            viewModel.loadArtistArtworks(artistId);
            var artworkTitles = viewModel.getCurrentArtistArtworks();

            if (artworkTitles.isEmpty()) {
                view.showMessage("Info", "This artist has no artworks in the museum.");
            } else {
                view.displayArtistArtworks(artworkTitles);
            }
        } catch (Exception e) {
            view.showMessage("Error", "Failed to load artist's artworks: " + e.getMessage());
        }
    }

    // File export methods
    public void saveArtworksToCSV() {
        try {
            var artworks = viewModel.getCurrentArtworks();
            if (artworks.isEmpty()) {
                view.showMessage("Error", "No artworks to save!");
                return;
            }

            String filePath = "artworks.csv";
            boolean result = viewModel.saveArtworksToCSV(filePath);

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
            var artworks = viewModel.getCurrentArtworks();
            if (artworks.isEmpty()) {
                view.showMessage("Error", "No artworks to save!");
                return;
            }

            String filePath = "artworks.doc";
            boolean result = viewModel.saveArtworksToDOC(filePath);

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

            var artworks = viewModel.filterArtworks(artistName, artworkType);

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

    /// Helper methods for displaying data
    private void displayArtists(List<Artist> artists) {
        if (artists == null || artists.isEmpty()) {
            view.setArtistTable(new Object[0][0], new String[]{"ID", "Name", "Birth Date", "Birth Place", "Nationality", "Photo"});
            return;
        }

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
        if (artworks == null || artworks.isEmpty()) {
            view.setArtworkTable(new Object[0][0], new String[]{"ID", "Artist ID", "Title", "Type", "Description", "Image 1", "Image 2", "Image 3"});
            return;
        }

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