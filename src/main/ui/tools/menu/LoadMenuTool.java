package ui.tools.menu;

import model.VisitedLocationList;
import model.exceptions.InvalidIntegerFieldException;
import model.exceptions.InvalidNameException;
import persistence.JsonReader;
import ui.LocationTrackerGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Represents the menu tool that loads locations list from file
public class LoadMenuTool extends Menu {

    private VisitedLocationList loadedVisitedList;
    private JsonReader jsonReader;
    private static final String JSON_LOCATION = "./data/visitedLocationList.json";


    // EFFECTS: constructs the save menu tool
    public LoadMenuTool(LocationTrackerGUI gui, JMenu parentMenu) {
        super(gui, parentMenu);
    }

    // MODIFIES: this
    // EFFECTS: adds a click listener bound to this menu item
    @Override
    protected void addClickListener() {
        menu.addActionListener(new LoadMenuToolActionHandler());
    }


    // MODIFIES: parentMenu
    // EFFECTS: creates the load menu item and adds it to the parent menu
    @Override
    protected void createMenuItem(JMenu parentMenu) {
        menu = new JMenuItem("Load Locations");
        addToParentMenu(parentMenu);

    }

    // MODIFIES: this
    // EFFECTS: adds a shortcut to load locations
    @Override
    protected void addKeyListener() {
        KeyStroke ctrlL = KeyStroke.getKeyStroke("control L");
        menu.setAccelerator(ctrlL);
    }

    // MODIFIES: visited
    // EFFECTS: reads the saved visited location list and replaces visited with it
    private void loadVisitedLocations() {
        jsonReader = new JsonReader(JSON_LOCATION);
        try {
            loadedVisitedList = jsonReader.read();
            locationTrackerGUI.setVisitedLocationList(loadedVisitedList);
            locationTrackerGUI.addLocationNames();
            JOptionPane.showMessageDialog(null,
                    "Loaded " + loadedVisitedList.getName() + " from " + JSON_LOCATION,
                    "Successfully Loaded", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to read" + JSON_LOCATION);
        } catch (InvalidNameException e) {
            JOptionPane.showMessageDialog(null,
                    "Some location is invalid" + JSON_LOCATION);
        } catch (InvalidIntegerFieldException e) {
            JOptionPane.showMessageDialog(null,
                    "Some location is invalid" + JSON_LOCATION);
        }
    }


    // Represents a private class that handles any click or key stroke events
    private class LoadMenuToolActionHandler implements ActionListener {

        // MODIFIES: visited
        // EFFECTS: loads locations when menu item is used
        @Override
        public void actionPerformed(ActionEvent e) {
            loadVisitedLocations();
        }
    }

}
