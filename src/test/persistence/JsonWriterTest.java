package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.ShoppingCart;
import model.Tea;
import model.TeaMenu;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterSCInvalidFile() {
        try {
            ShoppingCart scCart = new ShoppingCart();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterMenuInvalidFile() {
        try {
            TeaMenu menu = new TeaMenu();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySC() {
        try {
            ShoppingCart scCart = new ShoppingCart();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyShoppingCart.json");
            writer.open();
            writer.writeSC(scCart);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyShoppingCart.json");
            scCart = reader.readShoppingCart();
            assertEquals(0, scCart.getTeaInCart().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyMenu() {
        try {
            model.TeaMenu menu = new TeaMenu();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTeaMenu.json");
            writer.open();
            writer.writeMenu(menu);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTeaMenu.json");
            menu = reader.readTeaMenu();
            assertEquals(0, menu.getTea().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSC() {
        try {
            ShoppingCart scCart = new ShoppingCart();
            scCart.addTeaToCart(new Tea("China", "Green Tea", 1, 39.99));
            scCart.addTeaToCart(new Tea("India", "Black Tea", 2, 53.98));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralShoppingCart.json");
            writer.open();
            writer.writeSC(scCart);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralShoppingCart.json");
            scCart = reader.readShoppingCart();
            assertEquals(2, scCart.getTeaInCart().size());
            assertEquals("China", scCart.getTeaInCart().get(0).getRegion());
            assertEquals("India", scCart.getTeaInCart().get(1).getRegion());
            assertEquals("Green Tea", scCart.getTeaInCart().get(0).getType());

            checkTea("China", "Green Tea", scCart.getTeaInCart().get(0));
            checkTea("India", "Black Tea", scCart.getTeaInCart().get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTeaMenu() {
        try {
            TeaMenu menu = new TeaMenu();
            menu.addTeaToMenu(new Tea("China", "Green Tea", 1, 39.99));
            menu.addTeaToMenu(new Tea("India", "Black Tea", 2, 53.98));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMenu.json");
            writer.open();
            writer.writeMenu(menu);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMenu.json");
            menu = reader.readTeaMenu();
            assertEquals(2, menu.getTea().size());
            assertEquals("China", menu.getTea().get(0).getRegion());
            assertEquals("India", menu.getTea().get(1).getRegion());
            assertEquals("Green Tea", menu.getTea().get(0).getType());

            checkTea("China", "Green Tea", menu.getTea().get(0));
            checkTea("India", "Black Tea", menu.getTea().get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
