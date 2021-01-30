package ui.tools.buttons;

import model.VisitedLocationList;
import model.exceptions.EmptyListException;
import ui.LocationTrackerGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the button used to view statistics about user's visited locations
// SOURCE: creation and adding listeners are based on simple drawing player
//         https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter
public class StatsTool extends Tool {

    private PieChart ratingsChart;
    private VisitedLocationList visited;

    // EFFECTS: Constructs a new stats tool button
    public StatsTool(LocationTrackerGUI gui, JComponent parent) {
        super(gui, parent);
    }

    // EFFECTS: adds a listener for this tool
    @Override
    protected void addListener() {
        button.addActionListener(new StatsToolClickHandler());
    }


    // EFFECTS: shows a chart of number of different ratings, shows a number of how many locations visited
    private void displayStatistics() throws EmptyListException {
        visited = locationTrackerGUI.getVisitedLocationList();
        if (visited.visitedListSize() == 0) {
            JOptionPane.showMessageDialog(null, "You have not visited any locations.",
                    "No Locations Found", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "You have visited " + visited.visitedListSize()
                            + " locations and your average rating is " + visited.getAverageRating() + " stars.",
                    "Your Statistics",
                    JOptionPane.INFORMATION_MESSAGE);
            ratingsChart = new PieChart("Your Ratings", locationTrackerGUI);
        }

    }

    // MODIFIES: parent
    // EFFECTS: creates and adds button to parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("View your Statistics");
        addToParent(parent);
    }

    // Represents a private class that handles functionality if this button is clicked
    private class StatsToolClickHandler implements ActionListener {

        // EFFECTS: displays statistics when button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                displayStatistics();
            } catch (EmptyListException emptyListException) {
                JOptionPane.showMessageDialog(null, "You have not visited any locations.",
                        "No Locations Found", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
