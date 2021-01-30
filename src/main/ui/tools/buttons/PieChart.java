package ui.tools.buttons;

import javax.swing.*;

import model.Location;
import model.VisitedLocationList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import ui.LocationTrackerGUI;

// Represents the tool that creates the pie chart of user ratings from visited list
// SOURCE: designed after https://www.vogella.com/tutorials/JFreeChart/article.html
public class PieChart extends JFrame {

    private VisitedLocationList visited;

    // EFFECTS: creates a pie chart that displays rating data
    public PieChart(String title, LocationTrackerGUI gui) {
        super(title);
        visited = gui.getVisitedLocationList();
        PieDataset ratingData = createDataSet(); // gets the data to display in chart
        JFreeChart chart = createChart(ratingData, title); // constructs the chart
        ChartPanel chartPanel = new ChartPanel(chart); // creates the panel to display chart
        chartPanel.setMinimumSize(new java.awt.Dimension(500, 500));
        setContentPane(chartPanel); // adds the actual chart to the panel
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    // EFFECTS: creates the chart with a specified title, specified data, legend, no tooltips, no urls
    private JFreeChart createChart(PieDataset ratingData, String title) {
        JFreeChart chart = ChartFactory.createPieChart(title, ratingData, true, false, false);
        return chart;
    }

    // EFFECTS: assigns ratings to data set with quantity of that rating found
    private PieDataset createDataSet() {
        DefaultPieDataset ratings = new DefaultPieDataset();
        for (int i = 0; i < 5; i++) {
            String key = Integer.toString(i + 1);
            ratings.setValue(key + " Star", findNumberOfRating(i + 1));
        }
        return ratings;
    }

    // EFFECTS: counts up number of a specified rating to display in chart
    private Integer findNumberOfRating(Integer toFind) {
        Integer numRatingToFind = 0;
        for (Location l : visited.getLocations()) {
            if (l.getRating() == toFind) {
                numRatingToFind += 1;
            }
        }
        return numRatingToFind;
    }
}
