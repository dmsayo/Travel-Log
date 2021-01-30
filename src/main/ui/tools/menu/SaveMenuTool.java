package ui.tools.menu;

import model.VisitedLocationList;
import persistence.JsonWriter;
import ui.LocationTrackerGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

// Represents the save tool in the menu that saves the visited locations to file
public class SaveMenuTool extends Menu {

    private JsonWriter jsonWriter;
    private VisitedLocationList mainVisitedList;
    private static final String JSON_LOCATION = "./data/visitedLocationList.json";

    // EFFECTS: constructs the save menu tool
    public SaveMenuTool(LocationTrackerGUI gui, JMenu parentMenu) {
        super(gui, parentMenu);
    }


    // MODIFIES: this
    // EFFECTS: adds a click listener bound to this menu item
    @Override
    protected void addClickListener() {
        menu.addActionListener(new SaveMenuToolActionHandler());
    }

    // MODIFIES: parentMenu
    // EFFECTS: creates the save menu item and adds it to the parent menu
    @Override
    protected void createMenuItem(JMenu parentMenu) {
        menu = new JMenuItem("Save Locations");
        addToParentMenu(parentMenu);
    }

    // MODIFIES: this
    // EFFECTS: adds a accelerator shortcut to save locations
    @Override
    protected void addKeyListener() {
        KeyStroke ctrlL = KeyStroke.getKeyStroke("control S");
        menu.setAccelerator(ctrlL);
    }

    // EFFECTS: saves visited to file
    private void saveVisitedLocations() {
        jsonWriter = new JsonWriter(JSON_LOCATION);
        mainVisitedList = locationTrackerGUI.getVisitedLocationList();
        try {
            jsonWriter.open();
            jsonWriter.write(mainVisitedList);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null,
                    "Saved " + mainVisitedList.getName() + " to " + JSON_LOCATION,
                    "Successfully Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"Unable to write to: " + JSON_LOCATION);
        }

    }

    // Represents a private class that handles any click or key stroke events
    private class SaveMenuToolActionHandler implements ActionListener {

        // EFFECTS: saves locations when menu item is used
        @Override
        public void actionPerformed(ActionEvent e) {
            saveVisitedLocations();
        }
    }

}
