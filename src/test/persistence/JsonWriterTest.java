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
// Tests writing to json file
public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            VisitedLocationList vll = new VisitedLocationList("My visited locations");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyVisitedLocationList() {
        try {
            VisitedLocationList vll = new VisitedLocationList("My visited locations");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyVisitedLocationList.json");
            writer.open();
            writer.write(vll);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyVisitedLocationList.json");
            vll = reader.read();
            assertEquals("My visited locations", vll.getName());
            assertEquals(0, vll.visitedListSize());
        } catch (IOException e) {
            fail("Exception should not be thrown");
        } catch (InvalidNameException e) {
            fail("Written names are valid");
        } catch (InvalidIntegerFieldException e) {
            fail("Integer fields are valid");
        }
    }

    @Test
    public void testWriterGeneralVisitedLocationList() {
        try {
            VisitedLocationList vll = new VisitedLocationList("My visited locations");
            Location test1 = new Location("test1", 1, "test description", 3);
            Location test2 = new Location("test2", 2, "test description", 5);
            vll.addVisitedLocation(test1);
            vll.addVisitedLocation(test2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralVisitedLocationList.json");
            writer.open();
            writer.write(vll);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralVisitedLocationList.json");
            vll = reader.read();
            assertEquals("My visited locations", vll.getName());
            List<Location> locations = vll.getLocations();
            assertEquals(2, locations.size());
            checkLocation("test1", 1, "test description", 3, locations.get(0));
            checkLocation("test2", 2, "test description", 5, locations.get(1));
        } catch (IOException e) {
            fail("Exception should not be thrown");
        } catch (InvalidNameException e) {
            fail("Names are valid");
        } catch (InvalidIntegerFieldException e) {
            fail("Dates are valid");
        }
    }
}
