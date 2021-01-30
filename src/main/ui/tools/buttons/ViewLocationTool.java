package ui.tools.buttons;

import model.Location;
import model.exceptions.InvalidIndexException;
import model.exceptions.InvalidIntegerFieldException;
import model.exceptions.InvalidNameException;
import ui.LocationTrackerGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the button that allows user to view the selected location
// SOURCE: creation and adding listeners are based on simple drawing player
//         https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter
public class ViewLocationTool extends Tool {

    private Location selectedLocation;
    private String locationName;
    private String locationDate;
    private String locationDescription;
    private String locationRating;
    private JComboBox<String> ratings;
    private JTextField nameField;
    private JTextField dateField;
    private JTextField descriptionField;
    private Integer index;
    private JTextArea descriptionDisplay;

    // EFFECTS: Constructs a new view location tool button
    public ViewLocationTool(LocationTrackerGUI gui, JComponent parent) {
        super(gui, parent);
    }

    // EFFECTS: adds a listener for this tool
    @Override
    protected void addListener() {
        button.addActionListener(new ViewLocationToolClickHandler());
    }

    // MODIFIES: parent
    // EFFECTS: creates and adds button to parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("View a Location");
        addToParent(parent);
    }

    // SOURCE: displaying of fields based on
    //         https://stackoverflow.com/questions/3002787/simple-popup-java-form-with-at-least-two-fields
    // MODIFIES: visited location list
    // EFFECTS: displays the selected location in a pop-up window with fields that are able to be changed
    private void viewSelectedLocation() throws InvalidIndexException {
        getLocation();
        getLocationInformation();
        constructTextFields();
        constructDropDownMenuFields();
        constructTextArea();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        addTextFieldToParent(nameField, panel, "Location Name:");
        addTextFieldToParent(dateField, panel, "Date Visited:");
        addTextAreaScrollPaneToPanel(panel);
        addDropDownListToPanel(ratings, panel);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Viewing: " + selectedLocation.getLocationName(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            attemptToChangeLocation();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Any changes made have not been saved.",
                    "Changes Not Saved", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: selectedLocation
    // EFFECTS: tries to change fields of location, if exception caught show invalid input error message
    private void attemptToChangeLocation() throws InvalidIndexException {
        try {
            changeLocation(nameField, dateField, descriptionDisplay, ratings);
        } catch (InvalidIntegerFieldException e) {
            showInputInvalidError("date");
        } catch (InvalidNameException e) {
            showInputInvalidError("name");
        }
    }

    // EFFECTS: shows popup error message that a field is invalid, prompts user to try again
    private void showInputInvalidError(String field) throws InvalidIndexException {
        JOptionPane.showMessageDialog(null,
                "This " + field + " is not valid. Try again",
                "Changes Not Saved", JOptionPane.ERROR_MESSAGE);
        viewSelectedLocation();
    }

    // MODIFIES: panel
    // EFFECTS: creates and adds a scroll pane of descriptionDisplay
    private void addTextAreaScrollPaneToPanel(JPanel panel) {
        JScrollPane scrollPane = new JScrollPane(descriptionDisplay);
        JLabel label = new JLabel("Location Description:");
        panel.add(label);
        panel.add(scrollPane);
    }

    // MODIFIES: descriptionDisplay
    // EFFECTS: instantiates new text area field and sets its properties
    private void constructTextArea() {
        descriptionDisplay = new JTextArea(locationDescription);
        descriptionDisplay.setColumns(10);
        descriptionDisplay.setLineWrap(true);
        descriptionDisplay.setRows(5);
        descriptionDisplay.setWrapStyleWord(true);
    }

    // EFFECTS: instantiates new fields that use a drop down menu
    private void constructDropDownMenuFields() {
        String[] possibilities = {"1", "2", "3", "4", "5"};
        ratings = new JComboBox<String>(possibilities);
        ratings.setSelectedItem(locationRating);
    }

    // EFFECTS: instantiates new text fields with the provided data
    private void constructTextFields() {
        nameField = new JTextField(locationName);
        dateField = new JTextField(locationDate);
    }

    // EFFECTS: gets the currently highlighted location
    private void getLocation() throws InvalidIndexException {
        index = locationTrackerGUI.getLocationNameDisplayList().getSelectedIndex();
        selectedLocation = locationTrackerGUI.getVisitedLocationList().getVisitedLocationAt(index);
    }

    // EFFECTS: extracts the location's information
    private void getLocationInformation() {
        locationName = selectedLocation.getLocationName();
        locationDate = Integer.toString(selectedLocation.getDateTravelled());
        locationDescription = selectedLocation.getDescription();
        locationRating = Integer.toString(selectedLocation.getRating());
    }

    // MODIFIES: selectedLocation
    // EFFECTS: replaces the fields of the location with those inputted
    private void changeLocation(JTextField nameField, JTextField dateField,
                                JTextArea descriptionDisplay, JComboBox<String> ratings)
            throws InvalidIntegerFieldException, InvalidNameException {
        selectedLocation.changeLocationName(nameField.getText());
        selectedLocation.changeDateTravelled(Integer.parseInt(dateField.getText()));
        selectedLocation.changeDescription(descriptionDisplay.getText());
        selectedLocation.changeRating(retrieveRating());
        JOptionPane.showMessageDialog(null,
                "Any changes made have been saved.", "Changes Saved", JOptionPane.INFORMATION_MESSAGE);
        locationTrackerGUI.getListModel().set(index, nameField.getText());
    }

    // EFFECTS: helper method, returns the selected rating
    private int retrieveRating() {
        return Integer.parseInt((String) ratings.getSelectedItem());
    }

    // MODIFIES: panel
    // EFFECTS: creates and adds a new dropdown field to display in panel
    private void addDropDownListToPanel(JComboBox<String> ratings, JPanel panel) {
        JLabel toAdd = new JLabel("Location Rating:");
        panel.add(toAdd);
        panel.add(ratings);
    }

    // MODIFIES: panel
    // EFFECTS: creates and adds a new text field to display in the panel
    private void addTextFieldToParent(JTextField nameField, JPanel panel, String s) {
        JLabel toAdd = new JLabel(s);
        panel.add(toAdd);
        panel.add(nameField);
    }


    // Represents a private class that handles functionality if this button is clicked
    private class ViewLocationToolClickHandler implements ActionListener {

        // EFFECTS: views the selected location, if no location selected displays error message
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                viewSelectedLocation();
            } catch (InvalidIndexException exception) {
                JOptionPane.showMessageDialog(null, "You have not selected a location.",
                        "No Location Found", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

