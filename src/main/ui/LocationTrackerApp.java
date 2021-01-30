package ui;

import model.exceptions.EmptyListException;
import model.exceptions.InvalidIndexException;
import model.exceptions.InvalidIntegerFieldException;
import model.Location;
import model.VisitedLocationList;
import model.exceptions.InvalidNameException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// The location tracker console application
// SOURCE: class and many methods modelled after TellerApp and Json Demo
//         https://github.students.cs.ubc.ca/CPSC210/TellerApp
//         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class LocationTrackerApp {
    private static final String JSON_LOCATION = "./data/visitedLocationList.json";
    private VisitedLocationList visited;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // EFFECTS: Runs the location tracker application
    public LocationTrackerApp() {
        runLocationTrackerApp();
    }

    // SOURCE: modelled after TellerApp
    //         https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // MODIFIES: this
    // EFFECTS: processes user input
    private void runLocationTrackerApp() {
        boolean keepGoing = true;
        String command = null;

        init();
        promptLoad();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                keepGoing = false;
                promptSave();
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nSee you later!");
    }


    // SOURCE: based on initialization of TellerApp
    //         https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // MODIFIES: this
    // EFFECTS: initializes starting visited location list
    private void init() {
        visited = new VisitedLocationList("Derryl's Visited Locations");
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_LOCATION);
        jsonReader = new JsonReader(JSON_LOCATION);
    }

    // SOURCE: based on TellerApp displayMenu method
    //         https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWhat would you like to do?:");
        System.out.println("\tview : select and view a location from all your visited locations");
        System.out.println("\tadd : add a new visited location");
        System.out.println("\tremove : remove a selected location from your visited locations");
        System.out.println("\tchange : change a property of a visited location");
        System.out.println("\tstats : view statistics of your travels (ie. how many locations, average rating)");
        System.out.println("\tsave : save a visited location list to view later");
        System.out.println("\tload : load a previously made visited location list");
        System.out.println("\tquit : quit");
    }

    // SOURCE: modelled after processCommand method in TellerApp
    //         https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("view")) {
            viewLocationList();
        } else if (command.equals("add")) {
            addLocationToVisited();
        } else if (command.equals("remove")) {
            removeLocationFromVisited();
        } else if (command.equals("change")) {
            changeProperty();
        } else if (command.equals("stats")) {
            viewStats();
        } else if (command.equals("save")) {
            saveVisitedLocationList();
        } else if (command.equals("load")) {
            loadVisitedLocationList();
        } else {
            System.out.println("Invalid command, please try again");
        }
    }


    // EFFECTS: prints a list of all location names travelled with index
    private void viewLocationNames() {
        Integer index = 1;
        for (String s : visited.getAllVisitedLocationNames()) {
            System.out.println(index + ": " + s);
            index++;
        }
    }

    // MODIFIES: this
    // EFFECTS: views location names and prints a description of the selected location
    private void viewLocationList() {
        viewLocationNames();
        selectLocationToView();
    }

    // MODIFIES: this
    // EFFECTS: views location names and removes selected location from visited
    private void removeLocationFromVisited() {
        viewLocationNames();
        selectLocationToRemove();
    }

    // MODIFIES: this
    // EFFECTS: removes the location at the specified index
    private void selectLocationToRemove() {
        if (visited.visitedListSize() == 0) {
            System.out.println("You have not visited any locations");
        } else {
            System.out.println("\nSelect the index of the location you wish to remove:");
            Integer selectIndex = input.nextInt();

            try {
                selectIndex = selectIndex - 1;
                visited.removeVisitedLocationAt(selectIndex);
            } catch (InvalidIndexException e) {
                System.out.println("\tNot a valid index, please try again");
                removeLocationFromVisited();
            }
        }
    }


    // EFFECTS: prompts user to select a location index, returns location information
    private void selectLocationToView() {
        if (visited.visitedListSize() == 0) {
            System.out.println("You have not visited any locations");
        } else {
            System.out.println("\nSelect the index of the location you wish to view:");
            Integer selectIndex = input.nextInt();
            try {
                selectIndex = selectIndex - 1;
                Location requestedLocation = visited.getVisitedLocationAt(selectIndex);
                System.out.println("\tName: " + requestedLocation.getLocationName());
                System.out.println("\tDate travelled: " + requestedLocation.getDateTravelled());
                System.out.println("\tYour description: " + requestedLocation.getDescription());
                System.out.println("\tRating: " + requestedLocation.getRating());
            } catch (InvalidIndexException e) {
                System.out.println("\tNot a valid index, please try again");
                viewLocationList();
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: specifies and adds a new location to visited
    private void addLocationToVisited() {
        try {
            Location toAdd = new Location("filler", 1);
            addLocationName(toAdd);
            addLocationYear(toAdd);
            addLocationDescription(toAdd);
            addLocationRating(toAdd);
            visited.addVisitedLocation(toAdd);
            System.out.println("\nYour new location can be viewed in with the view command");
        } catch (InvalidNameException e) {
            e.printStackTrace();
        } catch (InvalidIntegerFieldException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this, l
    // EFFECTS: changes l's description to input
    private void addLocationDescription(Location l) {
        System.out.println("\nHow was it? Write your description here:");
        String description = input.next();
        String fullDescription = input.nextLine();
        l.changeDescription(description + fullDescription);
    }

    // MODIFIES: this, l
    // EFFECTS: changes l's name to input
    private void addLocationName(Location l) {
        System.out.println("\nWhere did you visit? Please input the name here:");
        String name = input.next();
        String restName = input.nextLine();
        try {
            l.changeLocationName(name + restName);
        } catch (InvalidNameException e) {
            System.out.println("Invalid name, try again!");
            addLocationName(l);
        }
    }

    // MODIFIES: this, l
    // EFFECTS: prompts the user to input visited year, changes location year to the input
    private void addLocationYear(Location l) {
        System.out.println("\nWhen did you visit? Please enter the year here:");
        Integer year = input.nextInt();
        try {
            l.changeDateTravelled(year);
        } catch (InvalidIntegerFieldException e) {
            System.out.println("Please enter a non-negative year");
            addLocationYear(l);
        }
    }

    // MODIFIES: this, l
    // EFFECTS: prompts the user to input number, changes location rating to the input
    private void addLocationRating(Location l) {
        System.out.println("\nWhat do you rate it? Input your rating here:");
        Integer rating = input.nextInt();
        try {
            l.changeRating(rating);
        } catch (InvalidIntegerFieldException e) {
            System.out.println("Please enter a rating between 1 and 5");
            addLocationRating(l);
        }
    }

    // MODIFIES: this
    // EFFECTS: changes a property of a location
    private void changeProperty() {
        viewLocationNames();
        selectLocationToChange();
    }


    // MODIFIES: this
    // EFFECTS: prompts user to select a location index, changes chosen information of selected location
    private void selectLocationToChange() {
        if (visited.visitedListSize() == 0) {
            System.out.println("You have not visited any locations");
        } else {
            System.out.println("\nSelect the index of the location you wish to change:");
            Integer selectIndex = input.nextInt();

            try {
                selectIndex = selectIndex - 1;
                Location requestedLocation = visited.getVisitedLocationAt(selectIndex);
                System.out.println("\tName: " + requestedLocation.getLocationName());
                System.out.println("\tDate travelled: " + requestedLocation.getDateTravelled());
                System.out.println("\tYour description: " + requestedLocation.getDescription());
                System.out.println("\tRating: " + requestedLocation.getRating());
                displayChangeMenu();
                processChange(selectIndex);
            } catch (InvalidIndexException e) {
                System.out.println("\nNot a valid index, please try again");
                changeProperty();
            }
        }
    }


    // EFFECTS: prints out a list of options to change
    private void displayChangeMenu() {
        System.out.println("\nWhat would you like to change?");
        System.out.println("\tname : change the location's name");
        System.out.println("\tdate : change the date visited");
        System.out.println("\tdesc : revise your location description");
        System.out.println("\trate : change the location's rating");
        System.out.println("\tquit : return to option selection");
    }

    // MODIFIES: this
    // EFFECTS: changes the requested field of the location at selectIndex of visitedLocationList
    private void processChange(Integer selectIndex) throws InvalidIndexException {
        Location chosenLoc = visited.getVisitedLocationAt(selectIndex);
        String fieldToChange = input.next();
        if (fieldToChange.equals("name")) {
            changeLocationName(chosenLoc);
        } else if (fieldToChange.equals("date")) {
            changeLocationDate(chosenLoc);
        } else if (fieldToChange.equals("desc")) {
            changeLocationDescription(chosenLoc);
        } else if (fieldToChange.equals("rate")) {
            changeLocationRating(chosenLoc);
        } else if (fieldToChange.equals("quit")) {
            System.out.println("\nReturning to main menu");
        } else {
            System.out.println("\nPlease enter a valid command");
            processChange(selectIndex);
        }
    }

    // MODIFIES: this, chosenLoc
    // EFFECTS: if newRating is < 1 or > 5, changeRating throws exception and is caught,
    //          otherwise takes user input and changes the locations rating to input
    private void changeLocationRating(Location chosenLoc) {
        System.out.println("\nWhat is your new rating?");
        Integer newRating = input.nextInt();
        try {
            chosenLoc.changeRating(newRating);
        } catch (InvalidIntegerFieldException e) {
            System.out.println("Please enter a rating between 1 and 5");
            changeLocationRating(chosenLoc);
        }
    }

    // MODIFIES: this, chosenLoc
    // EFFECTS: takes user input and changes the chosen location's description to input
    private void changeLocationDescription(Location chosenLoc) {
        System.out.println("\nWhat is your new description?");
        String description = input.next();
        String restDescription = input.nextLine();
        chosenLoc.changeDescription(description + restDescription);
    }

    // MODIFIES: this, chosenLoc
    // EFFECTS: if newDate < 0, exception is thrown and caught and user is prompted to repeat input,
    //          otherwise takes user input and changes the chosen location's date visited to input
    private void changeLocationDate(Location chosenLoc) {
        System.out.println("\nWhat is the new date?");
        Integer newDate = input.nextInt();
        try {
            chosenLoc.changeDateTravelled(newDate);
        } catch (InvalidIntegerFieldException e) {
            System.out.println("Please enter a non-negative year");
            changeLocationDate(chosenLoc);
        }
    }

    // MODIFIES: this, chosenLoc
    // EFFECTS: takes user input and changes the chosen location's name to input
    private void changeLocationName(Location chosenLoc) {
        System.out.println("\nWhat is the new name?");
        String newName = input.next();
        String restNewName = input.nextLine();
        try {
            chosenLoc.changeLocationName(newName + restNewName);
        } catch (InvalidNameException e) {
            System.out.println("Invalid name, try again");
            changeLocationName(chosenLoc);
        }
    }


    // SOURCE: modelled after selectAccount from TellerApp
    //         https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // EFFECTS: prints the statistic chosen
    private void viewStats() {

        System.out.println("\nWhich statistic would you like to view?");
        String selectStat = "";

        while (!(selectStat.equals("avg") || selectStat.equals("num"))) {

            System.out.println("\tavg : average rating");
            System.out.println("\tnum : number of locations visited");
            selectStat = input.next();
            selectStat = selectStat.toLowerCase();
        }
        if (selectStat.equals(("avg"))) {
            try {
                Double avg = visited.getAverageRating();
                System.out.println("\nYour average rating is: " + visited.getAverageRating());
            } catch (EmptyListException e) {
                System.out.println("\nYou have not visited and rated any locations");
            }

        } else {
            System.out.println("\nYou have visited " + visited.visitedListSize() + " location(s)");
        }
    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: saves the visited location list to file
    private void saveVisitedLocationList() {
        try {
            jsonWriter.open();
            jsonWriter.write(visited);
            jsonWriter.close();
            System.out.println("Saved " + visited.getName() + " to " + JSON_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to: " + JSON_LOCATION);
        }
    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads the visited location list to file
    private void loadVisitedLocationList() {
        try {
            visited = jsonReader.read();
            System.out.println("Loaded " + visited.getName() + " from " + JSON_LOCATION);
        } catch (IOException e) {
            System.out.println("Unable to read" + JSON_LOCATION);
        } catch (InvalidNameException e) {
            System.out.println("Some location is invalid" + JSON_LOCATION);
        } catch (InvalidIntegerFieldException e) {
            System.out.println("Some location is invalid" + JSON_LOCATION);
        }
    }


    // EFFECTS: prompts user to save before exiting application
    private void promptSave() {
        System.out.println("\nWould you like to save your visited locations?");
        System.out.println("\tsave : yes, I want to save my visited locations");
        System.out.println("\tskip : no, I do not want to save my visited locations");
        String choice = input.next();
        if (choice.equals("save")) {
            saveVisitedLocationList();
        } else if (choice.equals("skip")) {
            System.out.println("Visited locations not saved");
        } else {
            System.out.println("Invalid command, please try again");
            promptSave();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to load a visited location  list when starting application
    private void promptLoad() {
        System.out.println("\nWould you like to load your visited locations?");
        System.out.println("\tload : yes, I want to load my visited locations");
        System.out.println("\tskip : no, I do not want to load my visited locations");
        String choice = input.next();
        if (choice.equals("load")) {
            loadVisitedLocationList();
        } else if (choice.equals("skip")) {
            System.out.println("Visited locations not saved");
        } else {
            System.out.println("Invalid command, please try again");
            promptLoad();
        }
    }

}
