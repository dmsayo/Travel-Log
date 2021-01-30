package ui.tools.buttons;

import model.Location;
import model.exceptions.InvalidIndexException;
import ui.LocationTrackerGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the button used to delete a selected location in the visited location list
// SOURCE: creation and adding listeners are based on simple drawing player
//         https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter
public class DeleteLocationTool extends Tool {
    private Location removedLocation;

    // EFFECTS: Constructs a new delete tool button
    public DeleteLocationTool(LocationTrackerGUI gui, JComponent parent) {
        super(gui, parent);
    }

    // EFFECTS: adds a listener for this tool
    @Override
    protected void addListener() {
        button.addActionListener(new DeleteLocationToolClickHandler());
    }

    // MODIFIES: parent
    // EFFECTS: creates and adds button to parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Delete a Location");
        addToParent(parent);
    }

    // EFFECTS: deletes the selected visited location in the list
    private void deleteSelectedLocation() throws InvalidIndexException {
        Integer index = locationTrackerGUI.getLocationNameDisplayList().getSelectedIndex();
        removedLocation = locationTrackerGUI.getVisitedLocationList().getVisitedLocationAt(index);
        locationTrackerGUI.getVisitedLocationList().removeVisitedLocationAt(index);
        locationTrackerGUI.getListModel().remove(index);
        JOptionPane.showMessageDialog(null,
                "You have removed " + removedLocation.getLocationName() + " from your visited locations",
                "Location Deleted", JOptionPane.INFORMATION_MESSAGE);
    }

    // Represents a private class that handles functionality if this button is clicked
    private class DeleteLocationToolClickHandler implements ActionListener {

        // MODIFIES: visited location list
        // EFFECTS: deletes selected location when button is pressed, if no location selected display error message
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                deleteSelectedLocation();
            } catch (InvalidIndexException invalidIndexException) {
                JOptionPane.showMessageDialog(null, "You have not selected a location.",
                        "No Location Found", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
