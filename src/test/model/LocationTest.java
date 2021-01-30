package model;

import model.exceptions.InvalidIntegerFieldException;
import model.exceptions.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests construction and modification of a location object
public class LocationTest {
    private Location testLocation;

    @BeforeEach
    void runBefore() {
        try {
            testLocation = new Location("A", 1);
        } catch (InvalidIntegerFieldException e) {
            fail("The date is valid");
        } catch (InvalidNameException e) {
            fail("The name is valid");
        }
    }

    @Test
    void testShortConstructor() {
        try {
            Location test = new Location("test", 2);
            assertEquals("test", test.getLocationName());
            assertEquals(2, test.getDateTravelled());
        } catch (InvalidNameException e) {
            fail("Name is valid");
        } catch (InvalidIntegerFieldException e) {
            fail("The date is valid");
        }
    }

    @Test
    void testShortConstructorLocationInvalidName() {
        try {
            Location test = new Location("", 2);
            fail("The name is not valid, exception should be thrown");
        } catch (InvalidNameException e) {
            // Expected
        } catch (InvalidIntegerFieldException e) {
            fail("The date is valid");
        }
    }

    @Test
    void testShortConstructorLocationInvalidFields() {
        try {
            Location test = new Location("", -10);
            fail("Should throw exception, both fields are invalid");
        } catch (InvalidNameException e) {
            // Expected
        } catch (InvalidIntegerFieldException e) {
            // Expected
        }
    }

    @Test
    void testShortConstructorInvalidDate() {
        try {
            Location test = new Location("test", -10);
            fail("Should throw exception, date is invalid");
        } catch (InvalidNameException e) {
            fail("Should not throw name exception");
        } catch (InvalidIntegerFieldException e) {
            // Expected
        }
    }

    @Test
    void testLongConstructorInvalidName() {
        try {
            Location test = new Location("", 1, "", 3);
            fail("Should throw exception, name is invalid");
        } catch (InvalidNameException e) {
            // Expected
        } catch (InvalidIntegerFieldException e) {
            fail("Should not catch invalid integer field exception");
        }
    }

    @Test
    void testLongConstructorInvalidDate() {
        try {
            Location test = new Location("test", -1, "", 3);
            fail("Should throw exception, date is invalid");
        } catch (InvalidNameException e) {
            fail("Name is valid");
        } catch (InvalidIntegerFieldException e) {
            // Expected
        }
    }

    @Test
    void testLongConstructorInvalidRatingNegative() {
        try {
            Location test = new Location("test", 1, "", -1);
            fail("Should throw exception, date is invalid");
        } catch (InvalidNameException e) {
            fail("Name is valid");
        } catch (InvalidIntegerFieldException e) {
            // Expected
        }
    }

    @Test
    void testLongConstructorInvalidRatingOverFive() {
        try {
            Location test = new Location("test", 1, "", 10);
            fail("Should throw exception, date is invalid");
        } catch (InvalidNameException e) {
            fail("Name is valid");
        } catch (InvalidIntegerFieldException e) {
            // Expected
        }
    }

    @Test
    void testLongConstructor() {
        try {
            Location test = new Location("test", 1, "", 3);
            assertEquals("test", test.getLocationName());
            assertEquals(1, test.getDateTravelled());
            assertEquals("", test.getDescription());
            assertEquals(3, test.getRating());
        } catch (InvalidNameException e) {
            fail("Name exception should not be thrown");
        } catch (InvalidIntegerFieldException e) {
            fail("Integer field exception should not be thrown");
        }
    }

    @Test
    void testGetName() {
        assertEquals("A", testLocation.getLocationName());
    }

    @Test
    void testGetDate() {
        assertEquals(1, testLocation.getDateTravelled());
    }

    @Test
    void testGetDescription() {
        assertEquals(Location.NO_DESCRIPTION, testLocation.getDescription());
    }

    @Test
    void testGetRating() {
        assertEquals(Location.BASE_RATING, testLocation.getRating());
    }

    @Test
    void testChangeNameValid() {
        try {
            testLocation.changeLocationName("newName");
            assertEquals("newName", testLocation.getLocationName());
        } catch (InvalidNameException e) {
            fail("Exception should not be caught");
        }
    }

    @Test
    void testChangeNameInvalid() {
        try {
            testLocation.changeLocationName("");
            fail("Exception should be caught");
        } catch (InvalidNameException e) {
            // Expected
        }

    }

    @Test
    void testChangeDateValid() {
        try {
            testLocation.changeDateTravelled(2020);
            assertEquals(2020, testLocation.getDateTravelled());
        } catch (InvalidIntegerFieldException e) {
            fail("Should not catch exception");
        }
    }

    @Test
    void testChangeDateInvalid() {
        try {
            testLocation.changeDateTravelled(-2020);
            fail("Should catch exception");
        } catch (InvalidIntegerFieldException e) {
            // Expected
        }
    }

    @Test
    void testChangeDescription() {
        testLocation.changeDescription("This is the new description");
        assertEquals("This is the new description", testLocation.getDescription());
    }

    @Test
    void testChangeRatingValid() {
        try {
            testLocation.changeRating(5);
            assertEquals(5, testLocation.getRating());
        } catch (InvalidIntegerFieldException e) {
            fail("Should not catch exception");
        }
    }

    @Test
    void testChangeRatingInvalidOver5() {
        try {
            testLocation.changeRating(6);
            fail("Should have thrown exception");
        } catch (InvalidIntegerFieldException e) {
            // Expected
        }
    }

    @Test
    void testChangeRatingInvalidUnder1() {
        try {
            testLocation.changeRating(-1);
            fail("Should have thrown exception");
        } catch (InvalidIntegerFieldException e) {
            // Expected
        }
    }
}
