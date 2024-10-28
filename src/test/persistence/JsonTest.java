package persistence;

import model.Tea;
import static org.junit.Assert.assertEquals;

public class JsonTest {
    protected void checkTea(String region, String type, Tea tea) {

        assertEquals(region, tea.getRegion());
        assertEquals(type, tea.getType());
    }
}
