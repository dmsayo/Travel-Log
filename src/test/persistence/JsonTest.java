package persistence;

import model.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;

// SOURCE: modelled after
//         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Contains useful test code for JsonTests
public class JsonTest {
    protected void checkLocation(String name, Integer date, String desc, Integer rating, Location location) {
        assertEquals(name, location.getLocationName());
        assertEquals(date, location.getDateTravelled());
        assertEquals(desc, location.getDescription());
        assertEquals(rating, location.getRating());
    }
}
