package persistence;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.ShoppingCart;
import model.TeaMenu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ShoppingCart scCart = reader.readShoppingCart();
            fail("IOException expected");
        } catch (IOException e) {
            // the file exist
        }
    }

    @Test
    void testReadMenuNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TeaMenu menu = reader.readTeaMenu();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadEmptyShoppingCart() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyShoppingCart.json");
        try {
            ShoppingCart scCart = reader.readShoppingCart();
            assertEquals(0, scCart.getTeaInCart().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyTeaMenu() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMenu.json");
        try {
            TeaMenu menu = reader.readTeaMenu();
            assertEquals(0, menu.getTea().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralShoppingCart() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralShoppingCart.json");
        try {
            ShoppingCart scCart = reader.readShoppingCart();
            assertEquals("China", scCart.getTeaInCart().get(0).getRegion());
            assertEquals("Green Tea", scCart.getTeaInCart().get(0).getType());
            assertEquals("India", scCart.getTeaInCart().get(1).getRegion());
            assertEquals("Black Tea", scCart.getTeaInCart().get(1).getType());
            assertEquals(2, scCart.getTeaInCart().size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTeaMenu() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMenu.json");
        try {
            TeaMenu menu = reader.readTeaMenu();
            assertEquals("China", menu.getTea().get(0).getRegion());
            assertEquals("Green Tea", menu.getTea().get(0).getType());
            assertEquals(39.99, menu.getTea().get(0).getPrice());
            assertEquals(1, menu.getTea().get(0).getYear());
            assertEquals("India", menu.getTea().get(2).getRegion());
            assertEquals("Black Tea", menu.getTea().get(2).getType());
            assertEquals(53.98, menu.getTea().get(2).getPrice());
            assertEquals(2, menu.getTea().get(2).getYear());

            assertEquals(6, menu.getTea().size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
