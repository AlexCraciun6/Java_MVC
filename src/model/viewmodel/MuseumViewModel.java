package model.viewmodel;

import model.Artist;
import model.Artwork;
import model.Observable;
import model.repository.ArtistRepository;
import model.repository.ArtworkRepository;

import java.util.ArrayList;
import java.util.List;

public class MuseumViewModel extends Observable {
    private ArtistRepository artistRepository;
    private ArtworkRepository artworkRepository;

    // Data holders for view
    private List<Artist> currentArtists;
    private List<Artwork> currentArtworks;
    private List<String> currentArtistArtworks;

    public MuseumViewModel() {
        this.artistRepository = new ArtistRepository();
        this.artworkRepository = new ArtworkRepository();
        this.currentArtists = new ArrayList<>();
        this.currentArtworks = new ArrayList<>();
        this.currentArtistArtworks = new ArrayList<>();
    }

    // Methods to interact with repositories and update view data
    public void loadArtists() {
        this.currentArtists = artistRepository.getAllArtists();
        notifyObservers();
    }

    public void loadArtworks() {
        this.currentArtworks = artworkRepository.getAllArtworks();
        notifyObservers();
    }

    public void loadArtistArtworks(int artistId) {
        List<Artwork> artworks = artworkRepository.getArtworksByArtistId(artistId);
        this.currentArtistArtworks.clear();

        for (Artwork artwork : artworks) {
            this.currentArtistArtworks.add(artwork.getTitlu() + " (" + artwork.getTip() + ")");
        }

        notifyObservers();
    }

    public boolean addArtist(Artist artist) {
        boolean result = artistRepository.addArtist(artist);
        if (result) {
            loadArtists(); // Refresh data
        }
        return result;
    }

    public boolean updateArtist(Artist artist) {
        boolean result = artistRepository.updateArtist(artist);
        if (result) {
            loadArtists(); // Refresh data
        }
        return result;
    }

    public boolean deleteArtist(int artistId) {
        boolean result = artistRepository.deleteArtist(artistId);
        if (result) {
            loadArtists(); // Refresh data
        }
        return result;
    }

    public List<Artist> searchArtists(String name) {
        return artistRepository.searchArtistByName(name);
    }

    public boolean addArtwork(Artwork artwork) {
        boolean result = artworkRepository.addArtwork(artwork);
        if (result) {
            loadArtworks(); // Refresh data
        }
        return result;
    }

    public boolean updateArtwork(Artwork artwork) {
        boolean result = artworkRepository.updateArtwork(artwork);
        if (result) {
            loadArtworks(); // Refresh data
        }
        return result;
    }

    public boolean deleteArtwork(int artworkId) {
        boolean result = artworkRepository.deleteArtwork(artworkId);
        if (result) {
            loadArtworks(); // Refresh data
        }
        return result;
    }

    public List<Artwork> searchArtworks(String title) {
        return artworkRepository.searchArtworkByTitle(title);
    }

    public List<Artwork> filterArtworks(String artistName, String artworkType) {
        return artistRepository.filterArtworks(artistName, artworkType);
    }

    public boolean saveArtworksToCSV(String filePath) {
        return artworkRepository.saveArtworksToCSV(getCurrentArtworks(), filePath);
    }

    public boolean saveArtworksToDOC(String filePath) {
        return artworkRepository.saveArtworksToDOC(getCurrentArtworks(), filePath);
    }

    // Getters for current data (for view)
    public List<Artist> getCurrentArtists() {
        return currentArtists;
    }

    public List<Artwork> getCurrentArtworks() {
        return currentArtworks;
    }

    public List<String> getCurrentArtistArtworks() {
        return currentArtistArtworks;
    }

    // Methods to convert data to view-friendly format
    public Object[][] getArtistsAsTableData() {
        Object[][] data = new Object[currentArtists.size()][6];
        for (int i = 0; i < currentArtists.size(); i++) {
            Artist artist = currentArtists.get(i);
            data[i][0] = artist.getIdArtist();
            data[i][1] = artist.getNume();
            data[i][2] = artist.getDataNasterii();
            data[i][3] = artist.getLocNasterii();
            data[i][4] = artist.getNationalitate();
            data[i][5] = artist.getFotografie();
        }
        return data;
    }

    public Object[][] getArtworksAsTableData() {
        Object[][] data = new Object[currentArtworks.size()][8];
        for (int i = 0; i < currentArtworks.size(); i++) {
            Artwork artwork = currentArtworks.get(i);
            data[i][0] = artwork.getIdArtwork();
            data[i][1] = artwork.getArtistId();
            data[i][2] = artwork.getTitlu();
            data[i][3] = artwork.getTip();
            data[i][4] = artwork.getDescriere();
            data[i][5] = artwork.getImagine1();
            data[i][6] = artwork.getImagine2();
            data[i][7] = artwork.getImagine3();
        }
        return data;
    }

    public String[] getArtistTableColumnNames() {
        return new String[]{"ID", "Name", "Birth Date", "Birth Place", "Nationality", "Photo"};
    }

    public String[] getArtworkTableColumnNames() {
        return new String[]{"ID", "Artist ID", "Title", "Type", "Description", "Image 1", "Image 2", "Image 3"};
    }


}