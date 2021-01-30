package persistence;

import org.json.JSONObject;

// SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Interface containing the toJson method
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
