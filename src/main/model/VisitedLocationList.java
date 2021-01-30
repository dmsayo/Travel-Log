package model;

import model.exceptions.EmptyListException;
import model.exceptions.InvalidIndexException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// Represents a list of locations user has already visited
public class VisitedLocationList implements Writable {

    private List<Location> myVisitedLocations;
    private String name;

    // Constructor of a list of locations
    // EFFECTS: creates a new list of visited locations
    public VisitedLocationList(String name) {
        this.name = name;
        myVisitedLocations = new ArrayList<>();
    }

    // EFFECTS: returns name
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds l to the visited location list
    public void addVisitedLocation(Location l) {
        myVisitedLocations.add(l);
    }

    //EFFECTS: Returns the number of locations in the list
    public int visitedListSize() {
        return myVisitedLocations.size();
    }

    // EFFECTS: if index is not within the size of the list, throw new InvalidIndexException,
    //          otherwise returns the location at the given index of the visited locations list
    public Location getVisitedLocationAt(int index) throws InvalidIndexException {
        if (index < 0 || index > (myVisitedLocations.size() - 1)) {
            throw new InvalidIndexException();
        }
        return myVisitedLocations.get(index);
    }


    // MODIFIES: this
    // EFFECTS: if index is not within the size of the list, throw new InvalidIndexException,
    //          otherwise removes the location at the given index of the visited locations list
    public void removeVisitedLocationAt(int index) throws InvalidIndexException {
        if (index < 0 || index > (myVisitedLocations.size() - 1)) {
            throw new InvalidIndexException();
        }
        myVisitedLocations.remove(index);
    }

//    public void clearVisitedLocationList() {
//        for (Location l : myVisitedLocations) {
//            myVisitedLocations.clear();
//        }
//    }

    // EFFECTS: return a list of location names visited
    public List<String> getAllVisitedLocationNames() {
        List<String> names = new ArrayList<>();
        for (Location l : myVisitedLocations) {
            names.add(l.getLocationName());
        }
        return names;
    }


    // EFFECTS: if visited locations is empty, throw EmptyListException,
    //          otherwise returns the average rating of your visited list as a double
    public Double getAverageRating() throws EmptyListException {
        if (myVisitedLocations.size() == 0) {
            throw new EmptyListException();
        }

        Double numerator = 0.0;
        for (Location l : myVisitedLocations) {
            numerator = numerator + l.getRating();
        }
        return numerator / this.visitedListSize();
    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns an unmodifiable list of locations in this visited location list
    public List<Location> getLocations() {
        return Collections.unmodifiableList(myVisitedLocations);
    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns a JSONObject containing the name and visited locations
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("visited locations", locationsToJson());
        return json;
    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns things in this visited location list as a JSON array
    private JSONArray locationsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Location l : myVisitedLocations) {
            jsonArray.put(l.toJson());
        }

        return jsonArray;
    }
}




