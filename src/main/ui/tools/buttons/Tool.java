package ui.tools.buttons;

import ui.LocationTrackerGUI;

import javax.swing.*;

// Represents an abstract button tool
// SOURCE: tools package is based on simple drawing player
//         https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter
public abstract class Tool {

    protected JButton button;
    protected LocationTrackerGUI locationTrackerGUI;

    // MODIFIES: parent
    // EFFECTS: constructs tool and adds it to parent
    public Tool(LocationTrackerGUI gui, JComponent parent) {
        this.locationTrackerGUI = gui;
        createButton(parent);
        addToParent(parent);
        addListener();
    }

    // EFFECTS: adds a listener for this tool
    protected abstract void addListener();

    // MODIFIES: parent
    // EFFECTS: creates and adds button to parent
    protected abstract void createButton(JComponent parent);

    // MODIFIES: parent
    // EFFECTS: adds the given button to the parent component
    public void addToParent(JComponent parent) {
        parent.add(button);
    }
}
