//package model;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class WishLocationTest {
//    private WishLocationList testWishList;
//    private Location test1;
//    private Location test2;
//    private Location test3;
//
//    @BeforeEach
//    void runBefore() {
//        testWishList = new WishLocationList();
//        test1 = new Location("A", 1);
//        test2 = new Location("B", 2);
//        test3 = new Location("C", 3);
//    }
//
//    @Test
//    void testAddOneLocation() {
//        testWishList.addWishedLocation(test1);
//        assertEquals(1, testWishList.wishListSize());
//    }
//
//    @Test
//    void testAddMultipleLocation() {
//        testWishList.addWishedLocation(test1);
//        testWishList.addWishedLocation(test2);
//        testWishList.addWishedLocation(test3);
//        assertEquals(3, testWishList.wishListSize());
//    }
//
//    @Test
//    void testGetWishListSizeNoItems() {
//        assertEquals(0, testWishList.wishListSize());
//    }
//
//    @Test
//    void testGetWishListSizeMultipleItems() {
//
//    }
//
//}
