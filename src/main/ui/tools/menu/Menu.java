package ui.tools.menu;

import ui.LocationTrackerGUI;

import javax.swing.*;

// Represents an abstract menu tool
public abstract class Menu {

    protected JMenuItem menu;
    protected LocationTrackerGUI locationTrackerGUI;

    // EFFECTS: constructs a new menu item and adds it to the parent menu with its functionality
    public Menu(LocationTrackerGUI gui, JMenu parentMenu) {
        this.locationTrackerGUI = gui;
        createMenuItem(parentMenu);
        addToParentMenu(parentMenu);
        addClickListener();
        addKeyListener();
    }

    // MODIFIES: this
    // EFFECTS: adds a click listener to the menu item
    protected abstract void addClickListener();

    // MODIFIES: parentMenu
    // EFFECTS: adds the menu item to the parent menu
    protected void addToParentMenu(JMenu parentMenu) {
        parentMenu.add(menu);
    }

    // MODIFIES: parentMenu
    // EFFECTS: creates the menu item
    protected abstract void createMenuItem(JMenu parentMenu);

    // MODIFIES: this
    // EFFECTS: adds a key listener to act as a keyboard shortcut
    protected abstract void addKeyListener();
}