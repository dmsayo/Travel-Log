package persistence;

import model.Location;
import model.VisitedLocationList;
import model.exceptions.InvalidIntegerFieldException;
import model.exceptions.InvalidNameException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// SOURCE: modelled after
//         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Tests reader reading a Json file
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            VisitedLocationList vll = reader.read();
            fail("IOException expected");
        } catch (IOException | InvalidNameException | InvalidIntegerFieldException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyVisitedLocationList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyVisitedLocationList.json");
        try {
            VisitedLocationList vll = reader.read();
            assertEquals("My visited locations", vll.getName());
            assertEquals(0, vll.visitedListSize());

        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidNameException e) {
            fail("Names are valid");
        } catch (InvalidIntegerFieldException e) {
            fail("Integer fields are valid");
        }
    }

    @Test
    public void testReaderGeneralVisitedLocationList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralVisitedLocationList.json");
        try {
            VisitedLocationList vll = reader.read();
            assertEquals("My visited locations", vll.getName());
            List<Location> locations = vll.getLocations();
            assertEquals(2, locations.size());
            checkLocation("Vancouver", 2019, "Description", 5, locations.get(0));
            checkLocation("California", 2020, "Backpacking", 4, locations.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidNameException e) {
            fail("Names are valid");
        } catch (InvalidIntegerFieldException e) {
            fail("Integer fields are valid");
        }
    }

    @Test
    public void testReaderInvalidName() {
        JsonReader reader = new JsonReader("./data/testReaderInvalidName.json");
        try {
            VisitedLocationList vll = reader.read();
            fail("Exception should be thrown");
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidNameException | InvalidIntegerFieldException e) {
            // Expected
        }
    }

    @Test
    public void testReaderInvalidDate() {
        JsonReader reader = new JsonReader("./data/testReaderInvalidDate.json");
        try {
            VisitedLocationList vll = reader.read();
            fail("Exception should be thrown");
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidNameException | InvalidIntegerFieldException e) {
            // Expected
        }
    }

}
