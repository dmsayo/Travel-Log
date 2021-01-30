package ui.tools.buttons;

import model.Location;
import model.VisitedLocationList;
import model.exceptions.InvalidIntegerFieldException;
import model.exceptions.InvalidNameException;
import ui.LocationTrackerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the button used to add new locations to the visited location list
// SOURCE: creation and adding listeners are based on simple drawing player
//         https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter
public class AddLocationTool extends Tool {
    private String newName;
    private int newDate;
    private String newDescription;
    private int newRating;
    private Location newLocation;
    private VisitedLocationList myLocations;
    private String intermediateDescription;
    private Boolean wantToCancelDescription;
    private Boolean wantToCancelAdd;

    // EFFECTS: Constructs a new add tool button
    public AddLocationTool(LocationTrackerGUI gui, JComponent parent) {
        super(gui, parent);
    }

    // EFFECTS: adds a listener for this tool
    @Override
    protected void addListener() {
        button.addActionListener(new AddLocationToolClickHandler());
    }

    // MODIFIES: parent
    // EFFECTS: creates and adds button to parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add a Location");
        addToParent(parent);
    }

    // SOURCE: Idea of using popups from
    //         https://stackoverflow.com/questions/8852560/how-to-make-popup-window-in-java
    // MODIFIES: visited location list, main panel
    // EFFECTS: prompts dialog boxes to input information to create a new location and adds to visited location list
    private void promptAddLocationProcess() {
        wantToCancelAdd = false;
        newName = setNewName();
        if (wantToCancelAdd) {
            displayLocationNotAddedMessage();
            return;
        }
        newDate = setNewDate();
        if (wantToCancelAdd) {
            displayLocationNotAddedMessage();
            return;
        }
        newDescription = setNewDescription();
        if (wantToCancelAdd) {
            displayLocationNotAddedMessage();
            return;
        }
        newRating = setNewRating();
        if (wantToCancelAdd) {
            displayLocationNotAddedMessage();
            return;
        }
        constructNewLocation();

    }

    // MODIFIES: listModel
    // EFFECTS: creates the new location and adds its name to the displayed list
    private void constructNewLocation() {
        try {
            newLocation = new Location(newName, newDate, newDescription, newRating);
            myLocations = locationTrackerGUI.getVisitedLocationList();
            myLocations.addVisitedLocation(newLocation);
            locationTrackerGUI.getListModel().addElement(newLocation.getLocationName());
        } catch (InvalidNameException e) {
            System.out.println("Name is already checked to be valid");
        } catch (InvalidIntegerFieldException e) {
            System.out.println("Fields already checked to be valid");
        }
    }

    // EFFECTS: displays the location not added message
    private void displayLocationNotAddedMessage() {
        JOptionPane.showMessageDialog(null, "Your new location was not added.",
                "Location was not added", JOptionPane.ERROR_MESSAGE);
    }


    // SOURCE: based implementation of a drop down list on
    //         https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
    // EFFECTS: prompts user to set a new rating between 1 and 5, returns rating
    private int setNewRating() {
        Object[] possibilities = {"1", "2", "3", "4", "5"};
        String rating = (String) JOptionPane.showInputDialog(null, "What is your rating?",
                "Rating", JOptionPane.QUESTION_MESSAGE, null, possibilities, possibilities[0]);

        if (rating == null) {
            wantToCancelAdd = true;
            return 0;
        }
        return Integer.parseInt(rating);
    }

    // EFFECTS: prompts user to set a new description, returns description
    private String setNewDescription() {
        wantToCancelDescription = false;
        JFrame frame = new JFrame();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextArea textInputArea = new JTextArea();
        setTextInputAreaGraphics(textInputArea);
        JScrollPane scrollPane = new JScrollPane(textInputArea);
        setPanelGraphics(panel, scrollPane);
        createButtons(frame, panel, textInputArea);
        JDialog descriptionDialog = new JDialog(frame, "Description", Dialog.DEFAULT_MODALITY_TYPE);
        setDialogBoxProperties(panel, descriptionDialog);

        if (wantToCancelDescription == true) {
            wantToCancelAdd = true;
        }
        return intermediateDescription;
    }

    // MODIFIES: descriptionDialog
    // EFFECTS: sets graphics of the dialog box
    private void setDialogBoxProperties(JPanel panel, JDialog descriptionDialog) {
        descriptionDialog.setSize(300, 200);
        descriptionDialog.setLocationRelativeTo(null);
        descriptionDialog.add(panel);
        descriptionDialog.setVisible(true);
    }


    // MODIFIES: textInputArea
    // EFFECTS: sets text input area's properties
    private void setTextInputAreaGraphics(JTextArea textInputArea) {
        textInputArea.setColumns(20);
        textInputArea.setLineWrap(true);
        textInputArea.setRows(5);
        textInputArea.setWrapStyleWord(true);
    }

    // MODIFIES: panel
    // EFFECTS: helper method, sets up the panel with text box and titles
    private void setPanelGraphics(JPanel panel, JScrollPane scrollPane) {
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("What is Your Description of The Location?"));
        panel.add(scrollPane);
        panel.setVisible(true);
    }

    // MODIFIES: panel
    // EFFECTS: creates buttons that the add description panel contains
    private void createButtons(JFrame frame, JPanel panel, JTextArea textInputArea) {
        JButton okayButton = new JButton("Okay");
        okayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                intermediateDescription = textInputArea.getText();
                frame.dispose();
            }
        });
        panel.add(okayButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wantToCancelDescription = true;
                frame.dispose();
            }
        });
        panel.add(cancelButton);
    }


    // EFFECTS: prompts user to set a new date, returns that date. If the date is negative, prompts user to try again
    private int setNewDate() {
        try {
            JOptionPane dateOption = new JOptionPane();
            String date = dateOption.showInputDialog(null, "When did you visit?",
                    "Date", JOptionPane.QUESTION_MESSAGE);
            if (date == null) {
                wantToCancelAdd = true;
            } else if (Integer.parseInt(date) < 0) {
                JOptionPane.showMessageDialog(null, "Invalid date, please try again!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                setNewDate();
            } else {
                newDate = Integer.parseInt(date);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input, please try again!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            setNewDate();
        }
        return newDate;


    }

    // EFFECTS: prompts user to set the location name, returns that name. If the name is of length 0, prompts user
    //          to try again
    private String setNewName() {
        String name =
                JOptionPane.showInputDialog(null, "What is your location name?",
                        "Name", JOptionPane.QUESTION_MESSAGE);
        if (name == null) {
            wantToCancelAdd = true;
        } else if (name.length() == 0) {
            JOptionPane.showMessageDialog(null, "Invalid name, please try again!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            setNewName();
        } else {
            newName = name;
        }
        return newName;
    }

    // Represents a private class that handles functionality if this button is clicked
    private class AddLocationToolClickHandler implements ActionListener {

        // MODIFIES: visited location list
        // EFFECTS: prompts adding location process when button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            promptAddLocationProcess();
        }
    }
}
