package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestShoppingCart {
    private Tea t1;
    private Tea t2;
    private Tea t3;
    private Tea t4;
    private Tea t5;
    private Tea t6;
    private ArrayList<Tea> teaList;
    private ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        t1 = new Tea("China", "Green Tea", 1, 39.99);
        t2 = new Tea("China", "Oolong Tea", 3, 99.99);
        t3 = new Tea("India", "Black Tea", 2, 53.98);
        t4 = new Tea("Japan", "Matcha", 1, 19.99);
        t5 = new Tea("United Kingdom", "Early Grey", 2, 29.79);
        t6 = new Tea("Argentina", "Mate Tea", 4, 15.88);

        shoppingCart = new ShoppingCart();
    }

    @Test
    void testConstructor() {
        // test the single tea in the list
        assertEquals("China", t1.getRegion());
        assertEquals("Green Tea", t1.getType());
        assertEquals(1, t1.getYear());
        assertEquals(39.99, t1.getPrice(), 0.001);
    }


    @Test
    void testAddTeaToSC() {
        List<Tea> manyTea = shoppingCart.getTeaInCart();
        shoppingCart.addTeaToCart(t1);
        assertEquals(1, manyTea.size());
        assertEquals(t1, manyTea.get(0));
        shoppingCart.addTeaToCart(t2);
        assertEquals(2, manyTea.size());
        assertEquals(t2, manyTea.get(1));
    }

    @Test
    void testRemoveTeaFromCart() {
        List<Tea> manyTea = shoppingCart.getTeaInCart();
        shoppingCart.addTeaToCart(t1);
        shoppingCart.addTeaToCart(t2);
        shoppingCart.addTeaToCart(t3);
        shoppingCart.addTeaToCart(t4);
        assertEquals(4, manyTea.size());
        shoppingCart.removeTeaFromCart(t1);
        assertEquals(3, manyTea.size());
        assertEquals(t2, manyTea.get(0));
        shoppingCart.removeTeaFromCart(t4);
        assertEquals(2, manyTea.size());
        assertEquals(t2, manyTea.get(0));
        assertEquals(t3, manyTea.get(1));
    }

    @Test
    void testTotalPrice() {
        assertEquals(0, shoppingCart.totalPrice(),0.001);
        shoppingCart.addTeaToCart(t1);
        assertEquals(39.99, shoppingCart.totalPrice(), 0.001);
        shoppingCart.addTeaToCart(t2);
        shoppingCart.addTeaToCart(t3);
        assertEquals(193.96, shoppingCart.totalPrice(), 0.001);
        shoppingCart.addTeaToCart(t3);
        shoppingCart.addTeaToCart(t3);
        assertEquals(301.92, shoppingCart.totalPrice(), 0.001);
    }

    @Test 
    void testaddMultipleTeas() {
        shoppingCart.addMultipleTeas(1, t1);
        assertEquals(1, shoppingCart.getTeaInCart().size());
        shoppingCart.addMultipleTeas(2, t1);
        assertEquals(3, shoppingCart.getTeaInCart().size());
        shoppingCart.addMultipleTeas(3, t2);
        assertEquals(6, shoppingCart.getTeaInCart().size());
    }

    @Test
    void testSearchTeaFound() {
        shoppingCart.addTeaToCart(t1);
        shoppingCart.addTeaToCart(t5);
        assertEquals(t1, shoppingCart.searchTea("China", "Green Tea"));
        assertEquals(t5, shoppingCart.searchTea("United Kingdom", "Early Grey"));
    }

    @Test
    void testSearchTeaNotFound() {
        shoppingCart.addTeaToCart(t1);
        shoppingCart.addTeaToCart(t6);
        assertNull(shoppingCart.searchTea("China", "White Tea"));
        assertNull(shoppingCart.searchTea("America", "Green Tea"));
        assertNull(shoppingCart.searchTea("Australia", "Peach Tea"));
    }

    @Test
    void testClearCart() {
        shoppingCart.addTeaToCart(t1);
        shoppingCart.addTeaToCart(t6);
        shoppingCart.clearCart();
        assertEquals(0, shoppingCart.getTeaInCart().size());
    }
}
