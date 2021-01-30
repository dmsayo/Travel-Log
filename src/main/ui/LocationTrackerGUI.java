package ui;

import model.VisitedLocationList;
import ui.tools.buttons.*;
import ui.tools.menu.LoadMenuTool;
import ui.tools.menu.SaveMenuTool;

import javax.swing.*;
import java.awt.*;

// Represents a gui for the location tracker app
// SOURCE: the layout of this class is based on simple drawing player
//         https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter
public class LocationTrackerGUI extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    private static final String TITLE = "Location Tracker";
    private VisitedLocationList visited;
    private JFrame frame;
    private DefaultListModel listModel;
    private JList locationNameDisplayList;
    private Font font;
    private JMenuBar menuBar;
    private JMenu parentDropDown;

    // SOURCE: layout of initializing and populating gui based on simple drawing player
    //         https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter
    // EFFECTS: constructs the gui and initializes components
    public LocationTrackerGUI() {
        frame = new JFrame(TITLE);
        initializeFields();
        initializeGraphics();
    }

    // EFFECTS: creates new instances for fields
    private void initializeFields() {
        visited = new VisitedLocationList("Derryl's Visited Locations");
        listModel = new DefaultListModel();
        locationNameDisplayList = new JList(listModel);
        font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    }


    // SOURCE: graphics initialization based on layout of simple drawing player
    //         https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter
    // MODIFIES: this
    // EFFECTS: draws the JFrame window where the location tracker will operate, populates it with menu and buttons
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createTools();
        createMenu();
        addLocationNames();
        createLocationNameDisplay();
        setTitle(TITLE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a and houses a drop down menu
    private void createMenu() {
        menuBar = new JMenuBar();
        parentDropDown = new JMenu("Options");
        menuBar.add(parentDropDown);
        LoadMenuTool loadMenuTool = new LoadMenuTool(this, parentDropDown);
        parentDropDown.addSeparator();
        SaveMenuTool saveToolMenu = new SaveMenuTool(this, parentDropDown);
        this.setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: creates the scroll pane to display names at top of panel
    private void createLocationNameDisplay() {
        locationNameDisplayList = new JList(listModel);
        locationNameDisplayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        locationNameDisplayList.setSelectedIndex(0);
        locationNameDisplayList.setFont(font);
        JScrollPane listScrollPane = new JScrollPane(locationNameDisplayList);
        add(listScrollPane, BorderLayout.CENTER);
    }

    // EFFECTS: gets visited location list
    public VisitedLocationList getVisitedLocationList() {
        return visited;
    }

    // EFFECTS: overwrites the master visited location list with vll
    public void setVisitedLocationList(VisitedLocationList vll) {
        visited = vll;
    }

    // EFFECTS: gets the list model
    public DefaultListModel getListModel() {
        return listModel;
    }

    // EFFECTS: gets location list JList field
    public JList getLocationNameDisplayList() {
        return locationNameDisplayList;
    }

    // SOURCE: adding elements to a displayed list based off of list demo project found here:
    //         https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
    // MODIFIES: this
    // EFFECTS: adds a list of all location names visited  at top of panel
    public void addLocationNames() {
        listModel.clear();
        for (String s : visited.getAllVisitedLocationNames()) {
            listModel.addElement(s);
        }
    }

    // SOURCE: layout of creating buttons based on simple drawing player
    //         https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter
    // MODIFIES: this
    // EFFECTS: adds buttons for all tools in main window and adds them to tools list
    private void createTools() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(0, 1));
        toolArea.setSize(new Dimension(0, 0));
        add(toolArea, BorderLayout.SOUTH);
        DeleteLocationTool deleteLocationTool = new DeleteLocationTool(this, toolArea);
        AddLocationTool addLocationTool = new AddLocationTool(this, toolArea);
        ViewLocationTool viewLocationTool = new ViewLocationTool(this, toolArea);
        StatsTool statsTool = new StatsTool(this, toolArea);
    }

}
