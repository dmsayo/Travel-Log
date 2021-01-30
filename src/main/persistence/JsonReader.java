package persistence;

import model.Location;
import model.VisitedLocationList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.exceptions.InvalidIntegerFieldException;
import model.exceptions.InvalidNameException;
import org.json.*;

// SOURCE: class designed after https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads visited locations from JSON data stored in file
public class JsonReader {
    private String source;

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads visited location list from file and returns it, throws IOExcpetion if an error occurs
    //          reading data from file
    public VisitedLocationList read() throws IOException, InvalidNameException, InvalidIntegerFieldException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseVisitedLocationList(jsonObject);
    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: parses visited location list from JSON object and returns it
    private VisitedLocationList parseVisitedLocationList(JSONObject jsonObject) throws InvalidNameException,
            InvalidIntegerFieldException {
        String name = jsonObject.getString("name");
        VisitedLocationList myVisitedLocations = new VisitedLocationList(name);
        addLocations(myVisitedLocations, jsonObject);
        return myVisitedLocations;
    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: vll
    // EFFECTS: parses locations from JSON object and adds them to visited location list
    private void addLocations(VisitedLocationList vll, JSONObject jsonObject) throws InvalidNameException,
            InvalidIntegerFieldException {
        JSONArray jsonArray = jsonObject.getJSONArray("visited locations");
        for (Object json : jsonArray) {
            JSONObject nextLocation = (JSONObject) json;
            addLocation(vll, nextLocation);
        }
    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: vll
    // EFFECTS: parses locations from JSON object and adds it to visited location list
    private void addLocation(VisitedLocationList vll, JSONObject jsonObject) throws InvalidNameException,
            InvalidIntegerFieldException {
        String locationName = jsonObject.getString("location name");
        Integer dateTravelled = jsonObject.getInt("date travelled");
        String description = jsonObject.getString("description");
        Integer rating = jsonObject.getInt("rating");
        Location location = new Location(locationName, dateTravelled, description, rating);
        vll.addVisitedLocation(location);
    }
}
