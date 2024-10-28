package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTeaMenu {
    private Tea t1;
    private Tea t2;
    private Tea t3;
    private Tea t4;
    private Tea t5;
    private Tea t6;
    private ArrayList<Tea> teaMainMenu;
    private TeaMenu teaMenu;

    @BeforeEach
    void setUp() {
        t1 = new Tea("China", "Green Tea", 1, 39.99);
        t2 = new Tea("China", "Oolong Tea", 3, 99.99);
        t3 = new Tea("India", "Black Tea", 2, 53.98);
        t4 = new Tea("Japan", "Matcha", 1, 19.99);
        t5 = new Tea("United Kingdom", "Early Grey", 2, 29.79);
        t6 = new Tea("Argentina", "Mate Tea", 4, 15.88);

        teaMenu = new TeaMenu();

        teaMainMenu = new ArrayList<>();
        teaMainMenu.add(t1);
        teaMainMenu.add(t2);
        teaMainMenu.add(t3);
        teaMainMenu.add(t4);
        teaMainMenu.add(t5);
        teaMainMenu.add(t6);
    }

    @Test
    void testConstructor() {
        assertEquals("China", t1.getRegion());
        assertEquals("Green Tea", t1.getType());
        assertEquals(1, t1.getYear());
        assertEquals(39.99, t1.getPrice(), 0.001);

        assertEquals(6, teaMainMenu.size());
        assertEquals(t1, teaMainMenu.get(0));
        assertEquals(t6, teaMainMenu.get(5));

        List<Tea> manyTea = teaMenu.getTea();
        assertEquals(0, manyTea.size());

    }

    @Test
    void testAddTeaToMenu() {
        List<Tea> manyTea = teaMenu.getTea();
        teaMenu.addTeaToMenu(t1);
        assertEquals(1, manyTea.size());
        assertEquals(t1, manyTea.get(0));
        teaMenu.addTeaToMenu(t2);
        assertEquals(2, manyTea.size());
        assertEquals(t2, manyTea.get(1));
        assertEquals(manyTea, teaMenu.getTea());
    }

    @Test
    void testAllTeaThatCanBeFound() {
        teaMenu.addTeaToMenu(t1);
        teaMenu.addTeaToMenu(t2);
        assertEquals("China", teaMenu.searchAllTea("China", "Green Tea").getRegion());
        assertEquals("Green Tea", teaMenu.searchAllTea("China", "Green Tea").getType());

    }

    @Test
    void testAllTeaThatCanNotBeFound() {
        teaMenu.addTeaToMenu(t1);
        teaMenu.addTeaToMenu(t2);
        assertNull(teaMenu.searchAllTea("Canada", "Green Tea"));
        assertNull(teaMenu.searchAllTea("China", "White Tea"));
        assertNull(teaMenu.searchAllTea("Africa", "White Tea"));
    }
}
