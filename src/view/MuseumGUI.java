package view;

import controller.MuseumController;
import model.Observer;
import model.service.StatisticsService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class MuseumGUI extends JFrame implements Observer {
    private JTextField txtArtistName, txtArtistBirthDate, txtArtistBirthPlace, txtArtistNationality, txtArtistPhoto;
    private JTextField txtArtworkTitle, txtArtworkArtistId, txtArtworkType, txtArtworkDescription, txtArtworkImage1, txtArtworkImage2, txtArtworkImage3;
    private JButton btnAddArtist, btnUpdateArtist, btnDeleteArtist, btnSearchArtist;
    private JButton btnAddArtwork, btnUpdateArtwork, btnDeleteArtwork, btnSearchArtwork;
    private JButton btnSaveArtworksToCSV, btnSaveArtworksToDOC;
    private JButton btnLoadArtists, btnLoadArtworks;
    private JTable tblArtists, tblArtworks;
    private JTextField txtFilterArtist, txtFilterArtworkType;
    private JButton btnFilterArtworks;
    private JList<String> lstArtistArtworks;
    private DefaultListModel<String> artistArtworksListModel;
    private JButton btnShowStatistics;

    private MuseumController controller;

    public MuseumGUI() {
        initComponents();
        initUI();

        // Create controller and pass this view
        controller = new MuseumController(this);
    }

    private void initComponents() {
        // Initialize artist components
        txtArtistName = new JTextField(20);
        txtArtistBirthDate = new JTextField(20);
        txtArtistBirthPlace = new JTextField(20);
        txtArtistNationality = new JTextField(20);
        txtArtistPhoto = new JTextField(20);
        btnAddArtist = new JButton("Add Artist");
        btnUpdateArtist = new JButton("Update Artist");
        btnDeleteArtist = new JButton("Delete Artist");
        btnSearchArtist = new JButton("Search Artist");

        // Initialize artwork components
        txtArtworkTitle = new JTextField(20);
        txtArtworkArtistId = new JTextField(20);
        txtArtworkType = new JTextField(20);
        txtArtworkDescription = new JTextField(20);
        txtArtworkImage1 = new JTextField(20);
        txtArtworkImage2 = new JTextField(20);
        txtArtworkImage3 = new JTextField(20);
        btnAddArtwork = new JButton("Add Artwork");
        btnUpdateArtwork = new JButton("Update Artwork");
        btnDeleteArtwork = new JButton("Delete Artwork");
        btnSearchArtwork = new JButton("Search Artwork");

        // Initialize filter components
        txtFilterArtist = new JTextField(15);
        txtFilterArtworkType = new JTextField(15);
        btnFilterArtworks = new JButton("Filter Artworks");

        // Initialize other buttons
        btnSaveArtworksToCSV = new JButton("Save to CSV");
        btnSaveArtworksToDOC = new JButton("Save to DOC");
        btnLoadArtists = new JButton("Load Artists");
        btnLoadArtworks = new JButton("Load Artworks");

        // Initialize tables
        tblArtists = new JTable();
        tblArtworks = new JTable();

        // Initialize artist artworks list
        artistArtworksListModel = new DefaultListModel<>();
        lstArtistArtworks = new JList<>(artistArtworksListModel);

        // statistici
        btnShowStatistics = new JButton("Show Statistics");
        btnShowStatistics.addActionListener(e -> showStatisticsButtonClicked());

    }

    private void initUI() {
        setTitle("Museum Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Artists", createArtistPanel());
        tabbedPane.addTab("Artworks", createArtworkPanel());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        mainPanel.add(createFilterPanel(), BorderLayout.NORTH);

        add(mainPanel);
        setVisible(true);
    }


    private void openImagesInBrowser(String image1, String image2, String image3) {
        try {
            if (!image1.isEmpty() && image1.startsWith("http")) {
                Desktop.getDesktop().browse(new URI(image1));
            }
            if (!image2.isEmpty() && image2.startsWith("http")) {
                Desktop.getDesktop().browse(new URI(image2));
            }
            if (!image3.isEmpty() && image3.startsWith("http")) {
                Desktop.getDesktop().browse(new URI(image3));
            }
        } catch (Exception e) {
            showMessage("Error", "Could not open images: " + e.getMessage());
        }
    }

    private JPanel createArtistPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Artists"));

        // Artist form
        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Name:"));
        formPanel.add(txtArtistName);
        formPanel.add(new JLabel("Birth Date:"));
        formPanel.add(txtArtistBirthDate);
        formPanel.add(new JLabel("Birth Place:"));
        formPanel.add(txtArtistBirthPlace);
        formPanel.add(new JLabel("Nationality:"));
        formPanel.add(txtArtistNationality);
        formPanel.add(new JLabel("Photo:"));
        formPanel.add(txtArtistPhoto);

        // Artist buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAddArtist);
        buttonPanel.add(btnUpdateArtist);
        buttonPanel.add(btnDeleteArtist);
        buttonPanel.add(btnSearchArtist);

        // Artist details and artworks list
        JPanel artistDetailsPanel = new JPanel(new BorderLayout());
        artistDetailsPanel.add(formPanel, BorderLayout.NORTH);

        JPanel artworksListPanel = new JPanel(new BorderLayout());
        artworksListPanel.setBorder(BorderFactory.createTitledBorder("Artist's Artworks"));
        artworksListPanel.add(new JScrollPane(lstArtistArtworks), BorderLayout.CENTER);

        artistDetailsPanel.add(artworksListPanel, BorderLayout.CENTER);
        artistDetailsPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Artists table
        JScrollPane scrollPane = new JScrollPane(tblArtists);

        panel.add(artistDetailsPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createArtworkPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Artworks"));

        // Artwork form
        JPanel formPanel = new JPanel(new GridLayout(7, 2));
        formPanel.add(new JLabel("Artist ID:"));
        formPanel.add(txtArtworkArtistId);
        formPanel.add(new JLabel("Title:"));
        formPanel.add(txtArtworkTitle);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(txtArtworkType);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(txtArtworkDescription);
        formPanel.add(new JLabel("Image 1:"));
        formPanel.add(txtArtworkImage1);
        formPanel.add(new JLabel("Image 2:"));
        formPanel.add(txtArtworkImage2);
        formPanel.add(new JLabel("Image 3:"));
        formPanel.add(txtArtworkImage3);

        // Artwork buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAddArtwork);
        buttonPanel.add(btnUpdateArtwork);
        buttonPanel.add(btnDeleteArtwork);
        buttonPanel.add(btnSearchArtwork);

        // Artwork table
        JScrollPane scrollPane = new JScrollPane(tblArtworks);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.add(btnLoadArtists);
        panel.add(btnLoadArtworks);
        panel.add(btnSaveArtworksToCSV);
        panel.add(btnSaveArtworksToDOC);
        panel.add(btnShowStatistics);
        return panel;
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Filter Artworks"));

        panel.add(new JLabel("Artist Name:"));
        panel.add(txtFilterArtist);
        panel.add(new JLabel("Artwork Type:"));
        panel.add(txtFilterArtworkType);
        panel.add(btnFilterArtworks);

        return panel;
    }

    private void clearFilterFields() {
        txtFilterArtist.setText("");
        txtFilterArtworkType.setText("");
    }

    private boolean isUpdating = false;
    // Implementation of Observer interface
    @Override
    public void update() {
        // Prevent recursive updates
        if (isUpdating) {
            return;
        }

        isUpdating = true;
        try {
            // Refresh data when notified of changes
            controller.loadArtists();
            controller.loadArtworks();
        } finally {
            isUpdating = false;
        }
    }

    // Methods to access form data
    public String getArtistName() {
        return txtArtistName.getText();
    }

    public String getArtistBirthDate() {
        return txtArtistBirthDate.getText();
    }

    public String getArtistBirthPlace() {
        return txtArtistBirthPlace.getText();
    }

    public String getArtistNationality() {
        return txtArtistNationality.getText();
    }

    public String getArtistPhoto() {
        return txtArtistPhoto.getText();
    }

    public int getArtworkArtistId() {
        try {
            return Integer.parseInt(txtArtworkArtistId.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getArtworkTitle() {
        return txtArtworkTitle.getText();
    }

    public String getArtworkType() {
        return txtArtworkType.getText();
    }

    public String getArtworkDescription() {
        return txtArtworkDescription.getText();
    }

    public String getArtworkImage1() {
        return txtArtworkImage1.getText();
    }

    public String getArtworkImage2() {
        return txtArtworkImage2.getText();
    }

    public String getArtworkImage3() {
        return txtArtworkImage3.getText();
    }

    public String getFilterArtistName() {
        return txtFilterArtist.getText().trim();
    }

    public String getFilterArtworkType() {
        return txtFilterArtworkType.getText().trim();
    }

    // Methods to set table data
    public void setArtistTable(Object[][] data, String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblArtists.setModel(model);
    }

    public void setArtworkTable(Object[][] data, String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblArtworks.setModel(model);
    }

    // Method to display artist's artworks
    public void displayArtistArtworks(List<String> artworkTitles) {
        artistArtworksListModel.clear();
        for (String title : artworkTitles) {
            artistArtworksListModel.addElement(title);
        }
    }

    // Method to show messages
    public void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }


    // Add this new method
    private void showStatisticsButtonClicked() {
        controller.showStatistics();
    }

    public void showStatisticsDialog(Map<String, Integer> artworksByType, Map<String, Integer> artworksByArtist) {
        // Create the dialog
        JDialog dialog = new JDialog(this, "Museum Statistics", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);

        // Create tabbed pane for different charts
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add chart by artwork type
        JPanel typeChartPanel = StatisticsService.createPieChart("Artworks by Type", artworksByType);
        tabbedPane.addTab("By Type", typeChartPanel);

        // Add chart by artist
        JPanel artistChartPanel = StatisticsService.createBarChart("Artworks by Artist", artworksByArtist);
        tabbedPane.addTab("By Artist", artistChartPanel);

        dialog.add(tabbedPane, BorderLayout.CENTER);

        // Add close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Show dialog
        dialog.setVisible(true);
    }

    // Add getters for all UI components the controller needs
    public JButton getBtnAddArtist() {
        return btnAddArtist;
    }

    public JButton getBtnUpdateArtist() {
        return btnUpdateArtist;
    }

    public JButton getBtnDeleteArtist() {
        return btnDeleteArtist;
    }

    public JButton getBtnSearchArtist() {
        return btnSearchArtist;
    }

    public JButton getBtnAddArtwork() {
        return btnAddArtwork;
    }

    public JButton getBtnUpdateArtwork() {
        return btnUpdateArtwork;
    }

    public JButton getBtnDeleteArtwork() {
        return btnDeleteArtwork;
    }

    public JButton getBtnSearchArtwork() {
        return btnSearchArtwork;
    }

    public JButton getBtnSaveArtworksToCSV() {
        return btnSaveArtworksToCSV;
    }

    public JButton getBtnSaveArtworksToDOC() {
        return btnSaveArtworksToDOC;
    }

    public JButton getBtnLoadArtists() {
        return btnLoadArtists;
    }

    public JButton getBtnLoadArtworks() {
        return btnLoadArtworks;
    }

    public JButton getBtnFilterArtworks() {
        return btnFilterArtworks;
    }

    public JButton getBtnShowStatistics() {
        return btnShowStatistics;
    }

    public JTable getTblArtists() {
        return tblArtists;
    }

    public JTable getTblArtworks() {
        return tblArtworks;
    }

    public void setArtistName(String name) {
        txtArtistName.setText(name);
    }

    public void setArtistBirthDate(String birthDate) {
        txtArtistBirthDate.setText(birthDate);
    }

    public void setArtistBirthPlace(String birthPlace) {
        txtArtistBirthPlace.setText(birthPlace);
    }

    public void setArtistNationality(String nationality) {
        txtArtistNationality.setText(nationality);
    }

    public void setArtistPhoto(String photo) {
        txtArtistPhoto.setText(photo);
    }

    public void setArtworkArtistId(String artistId) {
        txtArtworkArtistId.setText(artistId);
    }

    public void setArtworkTitle(String title) {
        txtArtworkTitle.setText(title);
    }

    public void setArtworkType(String type) {
        txtArtworkType.setText(type);
    }

    public void setArtworkDescription(String description) {
        txtArtworkDescription.setText(description);
    }

    public void setArtworkImage1(String image) {
        txtArtworkImage1.setText(image);
    }

    public void setArtworkImage2(String image) {
        txtArtworkImage2.setText(image);
    }

    public void setArtworkImage3(String image) {
        txtArtworkImage3.setText(image);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MuseumGUI();
        });
    }
}