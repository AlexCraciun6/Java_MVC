package view;

import controller.MuseumController;
import model.Observer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

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
        registerListeners();

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

    private void registerListeners() {
        btnAddArtist.addActionListener(e -> addArtistButtonClicked());
        btnUpdateArtist.addActionListener(e -> updateArtistButtonClicked());
        btnDeleteArtist.addActionListener(e -> deleteArtistButtonClicked());
        btnSearchArtist.addActionListener(e -> searchArtistButtonClicked());

        btnAddArtwork.addActionListener(e -> addArtworkButtonClicked());
        btnUpdateArtwork.addActionListener(e -> updateArtworkButtonClicked());
        btnDeleteArtwork.addActionListener(e -> deleteArtworkButtonClicked());
        btnSearchArtwork.addActionListener(e -> searchArtworkButtonClicked());

        btnSaveArtworksToCSV.addActionListener(e -> saveArtworksToCSVButtonClicked());
        btnSaveArtworksToDOC.addActionListener(e -> saveArtworksToDOCButtonClicked());

        btnLoadArtists.addActionListener(e -> loadArtistsButtonClicked());
        btnLoadArtworks.addActionListener(e -> loadArtworksButtonClicked());

        btnFilterArtworks.addActionListener(e -> filterArtworksButtonClicked());

        // Listener for artist selection
        tblArtists.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblArtists.getSelectedRow() != -1) {
                int row = tblArtists.getSelectedRow();
                txtArtistName.setText(tblArtists.getValueAt(row, 1).toString());
                txtArtistBirthDate.setText(tblArtists.getValueAt(row, 2).toString());
                txtArtistBirthPlace.setText(tblArtists.getValueAt(row, 3).toString());
                txtArtistNationality.setText(tblArtists.getValueAt(row, 4).toString());
                txtArtistPhoto.setText(tblArtists.getValueAt(row, 5).toString());

                // Load artist's artworks
                int artistId = (int) tblArtists.getValueAt(row, 0);
                controller.loadArtistArtworks(artistId);
            }
        });

        // Listener for artwork selection
        tblArtworks.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblArtworks.getSelectedRow() != -1) {
                int row = tblArtworks.getSelectedRow();
                txtArtworkArtistId.setText(tblArtworks.getValueAt(row, 1).toString());
                txtArtworkTitle.setText(tblArtworks.getValueAt(row, 2).toString());
                txtArtworkType.setText(tblArtworks.getValueAt(row, 3).toString());
                txtArtworkDescription.setText(tblArtworks.getValueAt(row, 4).toString());

                try {
                    Object value1 = tblArtworks.getValueAt(row, 5);
                    Object value2 = tblArtworks.getValueAt(row, 6);
                    Object value3 = tblArtworks.getValueAt(row, 7);

                    txtArtworkImage1.setText(value1 != null ? value1.toString() : "");
                    txtArtworkImage2.setText(value2 != null ? value2.toString() : "");
                    txtArtworkImage3.setText(value3 != null ? value3.toString() : "");
                } catch (Exception ex) {
                    txtArtworkImage1.setText("");
                    txtArtworkImage2.setText("");
                    txtArtworkImage3.setText("");
                    showMessage("Error", "Failed to load artwork image data: " + ex.getMessage());
                }

                if (row != -1) {
                    String image1 = tblArtworks.getValueAt(row, 5) != null ? tblArtworks.getValueAt(row, 5).toString().trim() : "";
                    String image2 = tblArtworks.getValueAt(row, 6) != null ? tblArtworks.getValueAt(row, 6).toString().trim() : "";
                    String image3 = tblArtworks.getValueAt(row, 7) != null ? tblArtworks.getValueAt(row, 7).toString().trim() : "";

                    openImagesInBrowser(image1, image2, image3);
                }
            }
        });
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

    // Button action methods
    private void addArtistButtonClicked() {
        controller.addArtist(
                getArtistName(),
                getArtistBirthDate(),
                getArtistBirthPlace(),
                getArtistNationality(),
                getArtistPhoto()
        );
        clearFilterFields();
    }

    private void updateArtistButtonClicked() {
        controller.updateArtist(
                getArtistName(),
                getArtistBirthDate(),
                getArtistBirthPlace(),
                getArtistNationality(),
                getArtistPhoto()
        );
        clearFilterFields();
    }

    private void deleteArtistButtonClicked() {
        controller.deleteArtist(getArtistName());
        clearFilterFields();
    }

    private void searchArtistButtonClicked() {
        controller.searchArtist(getArtistName());
        clearFilterFields();
    }

    private void addArtworkButtonClicked() {
        controller.addArtwork(
                getArtworkArtistId(),
                getArtworkTitle(),
                getArtworkType(),
                getArtworkDescription(),
                getArtworkImage1(),
                getArtworkImage2(),
                getArtworkImage3()
        );
        clearFilterFields();
    }

    private void updateArtworkButtonClicked() {
        controller.updateArtwork(
                getArtworkArtistId(),
                getArtworkTitle(),
                getArtworkType(),
                getArtworkDescription(),
                getArtworkImage1(),
                getArtworkImage2(),
                getArtworkImage3()
        );
        clearFilterFields();
    }

    private void deleteArtworkButtonClicked() {
        controller.deleteArtwork(getArtworkTitle());
        clearFilterFields();
    }

    private void searchArtworkButtonClicked() {
        controller.searchArtwork(getArtworkTitle());
        clearFilterFields();
    }

    private void loadArtistsButtonClicked() {
        controller.loadArtists();
    }

    private void loadArtworksButtonClicked() {
        controller.loadArtworks();
    }

    private void saveArtworksToCSVButtonClicked() {
        controller.saveArtworksToCSV();
    }

    private void saveArtworksToDOCButtonClicked() {
        controller.saveArtworksToDOC();
    }

    private void filterArtworksButtonClicked() {
        controller.filterArtworks(
                getFilterArtistName(),
                getFilterArtworkType()
        );
    }

    // Add this new method
    private void showStatisticsButtonClicked() {
        controller.showStatistics();
    }

    // Add this new method to create and display statistics dialog
    public void showStatisticsDialog(Map<String, Integer> artworksByType, Map<String, Integer> artworksByArtist) {
        // Create the dialog
        JDialog dialog = new JDialog(this, "Museum Statistics", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);

        // Create tabbed pane for different charts
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add chart by artwork type
        JPanel typeChartPanel = createPieChart("Artworks by Type", artworksByType);
        tabbedPane.addTab("By Type", typeChartPanel);

        // Add chart by artist
        JPanel artistChartPanel = createBarChart("Artworks by Artist", artworksByArtist);
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

    private JPanel createPieChart(String title, Map<String, Integer> data) {
        // Create dataset
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        // Add data to dataset
        for (String key : data.keySet()) {
            dataset.setValue(key, data.get(key));
        }

        // Create chart
        JFreeChart chart = ChartFactory.createPieChart(
                title,
                dataset,
                true, // legend
                true, // tooltips
                false // URLs
        );

        // Create panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 400));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBarChart(String title, Map<String, Integer> data) {
        // Create dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Add data to dataset
        for (String key : data.keySet()) {
            dataset.setValue(data.get(key), "Count", key);
        }

        // Create chart
        JFreeChart chart = ChartFactory.createBarChart(
                title,
                "Artist",
                "Number of Artworks",
                dataset,
                PlotOrientation.VERTICAL,
                false, // legend
                true,  // tooltips
                false  // URLs
        );

        // Customize chart
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setItemMargin(0.1);

        // Create panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 400));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MuseumGUI();
        });
    }
}