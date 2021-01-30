package model;


import model.exceptions.InvalidIntegerFieldException;
import model.exceptions.InvalidNameException;
import org.json.JSONObject;
import persistence.Writable;

// Represents a single location with a name, date travelled, description, and rating
public class Location implements Writable {
    public static final int BASE_RATING = 1;
    public static final String NO_DESCRIPTION = "No description available";

    private int rating;
    private int dateTravelled;
    private String description;
    private String locationName;


    // Constructor of a location
    // EFFECTS: The name of the location is set to locationName and the date you travelled there.
    //          is set to dateTravelled
    //          If the name, date, or rating field is invalid, throws exception
    public Location(String locationName, Integer date) throws InvalidNameException, InvalidIntegerFieldException {

        if (locationName.length() == 0) {
            throw new InvalidNameException();
        }
        if (date < 0) {
            throw new InvalidIntegerFieldException();
        }

        this.locationName = locationName;
        this.dateTravelled = date;
        this.description = NO_DESCRIPTION;
        this.rating = BASE_RATING;
    }

    // Constructor of a location, has all fields
    // EFFECTS: The name of the location is set to locationName and the date you travelled there
    //          is set to dateTravelled, description is set to description, rating is set to rating.
    //          If the name, date, or rating field is invalid, throws exception
    public Location(String locationName, Integer date, String description, Integer rating)
            throws InvalidNameException, InvalidIntegerFieldException {

        if (locationName.length() == 0) {
            throw new InvalidNameException();
        }
        if (date < 0 || rating < 1 || rating > 5) {
            throw new InvalidIntegerFieldException();
        }

        this.locationName = locationName;
        this.dateTravelled = date;
        this.description = description;
        this.rating = rating;
    }

    // EFFECTS: returns location name
    public String getLocationName() {
        return locationName;
    }

    // EFFECTS: returns location date travelled
    public int getDateTravelled() {
        return dateTravelled;
    }

    // EFFECTS: returns location description
    public String getDescription() {
        return description;
    }

    // EFFECTS: returns location rating
    public int getRating() {
        return rating;
    }

    // MODIFIES: this
    // EFFECTS: if the length of the new name is 0, throw InvalidNameException, otherwise
    //          sets location name to newName
    public void changeLocationName(String newName) throws InvalidNameException {
        if (newName.length() == 0) {
            throw new InvalidNameException();
        }
        this.locationName = newName;
    }

    // MODIFIES: this
    // EFFECTS: if newDate < 0, throws new InvalidIntegerChangeException, otherwise
    //          sets location date travelled to newDate
    public void changeDateTravelled(int newDate) throws InvalidIntegerFieldException {
        if (newDate < 0) {
            throw new InvalidIntegerFieldException();
        }
        this.dateTravelled = newDate;
    }

    // MODIFIES: this
    // EFFECTS: changes a location's description to newDescription
    public void changeDescription(String newDescription) {
        this.description = newDescription;
    }

    // MODIFIES: this
    // EFFECTS: if rating is < 1 or > 5, throw new InvalidIntegerChangeException, otherwise
    //          changes rating of location to rating
    public void changeRating(int rating) throws InvalidIntegerFieldException {
        if (rating < 1 || rating > 5) {
            throw new InvalidIntegerFieldException();
        }

        this.rating = rating;
    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns a JSONObject containing the data of a location
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("location name", locationName);
        json.put("date travelled", dateTravelled);
        json.put("description", description);
        json.put("rating", rating);
        return json;
    }
}
