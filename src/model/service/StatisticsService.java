package model.service;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class StatisticsService {

    public static JPanel createPieChart(String title, Map<String, Integer> data) {
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

    public static JPanel createBarChart(String title, Map<String, Integer> data) {
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
}