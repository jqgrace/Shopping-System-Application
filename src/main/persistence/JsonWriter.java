package persistence;

// Reference: Paul Carter, Oct 16.2021, JsonSerializationDemo, java.
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/model/Category.java

import model.ShoppingCart;
import model.TeaMenu;
import org.json.JSONObject;
import java.io.*;

// Represents a file writer that writes JSON representation of the shoppingCart data
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String fileDestination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String fileDestination) {
        this.fileDestination = fileDestination;
    }

    // MODIFIES: this
    // EFFECTS: after opens writer; if destination file cannot be opened for
    // writing,
    // throws FileNotFoundException
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(fileDestination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of shoppingCart to file
    public void writeSC(ShoppingCart scCart) {
        JSONObject json = scCart.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of tea menu to file
    public void writeMenu(TeaMenu menu) {
        JSONObject json = menu.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
