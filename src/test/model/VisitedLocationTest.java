package model;

import model.exceptions.EmptyListException;
import model.exceptions.InvalidIndexException;
import model.exceptions.InvalidIntegerFieldException;
import model.exceptions.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests construction and modification of the visited locations list
class VisitedLocationTest {
    private VisitedLocationList testLocList;
    private Location A;
    private Location B;
    private Location C;

    @BeforeEach
    void runBefore() {
        try {
            testLocList = new VisitedLocationList("test");
            A = new Location("A", 1);
            B = new Location("B", 2);
            C = new Location("C", 3);
        } catch (InvalidNameException e) {
            fail("Names are valid");
        } catch (InvalidIntegerFieldException e) {
            fail("Dates are valid");
        }
    }

    @Test
    void testAddOneLocation() {
        testLocList.addVisitedLocation(A);
        assertEquals(1, testLocList.visitedListSize());
    }

    @Test
    void testAddNoLocations() {
        assertEquals(0, testLocList.visitedListSize());
    }

    @Test
    void testAddMultipleLocations() {
        testLocList.addVisitedLocation(A);
        testLocList.addVisitedLocation(B);
        testLocList.addVisitedLocation(C);
        assertEquals(3, testLocList.visitedListSize());
    }

    @Test
    void testGetNoLocationNames() {
        assertEquals(0, testLocList.getAllVisitedLocationNames().size());
    }

    @Test
    void testGetThreeLocationNames() {
        testLocList.addVisitedLocation(A);
        testLocList.addVisitedLocation(B);
        testLocList.addVisitedLocation(C);
        assertEquals(3, testLocList.getAllVisitedLocationNames().size());
    }

    @Test
    void testGetLocationAtIndexZero() {
        testLocList.addVisitedLocation(B);
        try {
            assertEquals(B, testLocList.getVisitedLocationAt(0));
        } catch (InvalidIndexException e) {
            fail("Should not catch exception");
        }
    }

    @Test
    void testGetLocationAtIndexTwo() {
        testLocList.addVisitedLocation(A);
        testLocList.addVisitedLocation(B);
        testLocList.addVisitedLocation(C);
        try {
            assertEquals(C, testLocList.getVisitedLocationAt(2));
        } catch (InvalidIndexException e) {
            fail("Should not catch exception");
        }

    }

    @Test
    void testGetInvalidLocationNegative() {
        testLocList.addVisitedLocation(A);
        testLocList.addVisitedLocation(B);
        testLocList.addVisitedLocation(C);
        try {
            assertEquals(C, testLocList.getVisitedLocationAt(-2));
            fail("Exception should be thrown");
        } catch (InvalidIndexException e) {
            // Expected
        }
    }

    @Test
    void testGetInvalidLocationOverSize() {
        testLocList.addVisitedLocation(A);
        testLocList.addVisitedLocation(B);
        testLocList.addVisitedLocation(C);
        try {
            assertEquals(C, testLocList.getVisitedLocationAt(3));
            fail("Exception should be thrown");
        } catch (InvalidIndexException e) {
            // Expected
        }
    }

    @Test
    void testRemoveLocationAtIndexZero() {
        testLocList.addVisitedLocation(A);
        try {
            assertEquals(A, testLocList.getVisitedLocationAt(0));
            testLocList.removeVisitedLocationAt(0);
            assertEquals(0, testLocList.visitedListSize());
        } catch (InvalidIndexException e) {
            fail("Should not catch exception");
        }
    }

    @Test
    void testRemoveLocationAtIndexOne() {
        testLocList.addVisitedLocation(A);
        testLocList.addVisitedLocation(B);
        testLocList.addVisitedLocation(C);
        try {
            assertEquals(B, testLocList.getVisitedLocationAt(1));
            testLocList.removeVisitedLocationAt(1);
            assertEquals(C, testLocList.getVisitedLocationAt(1));
            assertEquals(2, testLocList.visitedListSize());
        } catch (InvalidIndexException e) {
            fail("Should not catch exception");
        }
    }

    @Test
    void testRemoveInvalidLocationNegativeIndex() {
        testLocList.addVisitedLocation(A);
        testLocList.addVisitedLocation(B);
        testLocList.addVisitedLocation(C);
        try {
            assertEquals(B, testLocList.getVisitedLocationAt(1));
            testLocList.removeVisitedLocationAt(-1);
            fail("Should throw exception");
        } catch (InvalidIndexException e) {
            // Expected
        }
    }

    @Test
    void testRemoveInvalidLocationTooLargeIndex() {
        testLocList.addVisitedLocation(A);
        testLocList.addVisitedLocation(B);
        testLocList.addVisitedLocation(C);
        try {
            assertEquals(B, testLocList.getVisitedLocationAt(1));
            testLocList.removeVisitedLocationAt(5);
            fail("Should throw exception");
        } catch (InvalidIndexException e) {
            // Expected
        }
    }

    @Test
    void testAverageOneLocation() {
        testLocList.addVisitedLocation(A);
        try {
            assertEquals(1.0, testLocList.getAverageRating());
        } catch (EmptyListException e) {
            fail("Exception should not be caught");
        }
    }


    @Test
    void testAverageMultipleLocationDecimal() {
        try {
            Location A = new Location("A", 1);
            Location B = new Location("B", 2);
            A.changeRating(4);
            B.changeRating(1);
            testLocList.addVisitedLocation(A);
            testLocList.addVisitedLocation(B);
            assertEquals(2.5, testLocList.getAverageRating());
        } catch (InvalidIntegerFieldException | InvalidNameException | EmptyListException e) {
            fail("Should not catch exception");
        }
    }

    @Test
    void testAverageLocationExceptionCaught() {
        try {
            assertEquals(0, testLocList.visitedListSize());
            testLocList.getAverageRating();
            fail("Exception should be caught");
        } catch (EmptyListException e) {
            // Expected
        }
    }

}