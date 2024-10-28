package persistence;

// Reference: Paul Carter, Oct 16.2021, JsonSerializationDemo, java
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/model/Category.java

import org.json.*;

import model.ShoppingCart;
import model.Tea;
import model.TeaMenu;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a file reader that reads the shoppingCart from JSON data stored in the file
public class JsonReader {

    private String source;

    // EFFECTS: constructs a reader to read the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: read ShoppingCart from file and return it;
    // thorws IOException if error occurs when reading data from file
    public ShoppingCart readShoppingCart() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseShoppingCart(jsonObject);
    }

    // EFFECTS: read TeaMenu from file and return it;
    // thorws IOException if error occurs when reading data from file
    public TeaMenu readTeaMenu() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTeaMenu(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ShoppingCart from JSON object and returns it
    private ShoppingCart parseShoppingCart(JSONObject jsonObject) {
        ShoppingCart teaCart = new ShoppingCart();
        addManyTea(teaCart, jsonObject);
        return teaCart;
    }

    // MODIFIES: teaCart
    // EFFECTS: parses tea from JSON object and adds them to ShoppingCart
    private void addManyTea(ShoppingCart teaCart, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tea");
        for (Object json : jsonArray) {
            JSONObject nextTea = (JSONObject) json;
            addTea(teaCart, nextTea);
        }
    }

    // MODIFIES: sCart
    // EFFECTS: parses tea from JSON object and adds it to shoppingCart
    private void addTea(ShoppingCart scCart, JSONObject jsonObject) {
        String region = jsonObject.getString("Region");
        String type = jsonObject.getString("Type");
        int year = jsonObject.getInt("Year");
        double price = jsonObject.getDouble("Price");
        Tea tea = new Tea(region, type, year, price);
        scCart.addTeaToCart(tea);
    }

    // EFFECTS: parses TeaMenu from JSON object and returns it
    private TeaMenu parseTeaMenu(JSONObject jsonObject) {
        TeaMenu menu = new TeaMenu();
        addManyTeaToMenu(menu, jsonObject);
        return menu;
    }

    // MODIFIES: menu
    // EFFECTS: parses tes from JSON object and adds them to menu
    private void addManyTeaToMenu(TeaMenu menu, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("teaMenu");
        for (Object json : jsonArray) {
            JSONObject nextTea = (JSONObject) json;
            addTeaToMenu(menu, nextTea);
        }
    }

    // MODIFIES: menu
    // EFFECTS: parses tea from JSON object and adds it to menu
    private void addTeaToMenu(TeaMenu menu, JSONObject jsonObject) {
        String region = jsonObject.getString("Region");
        String type = jsonObject.getString("Type");
        int year = jsonObject.getInt("Year");
        double price = jsonObject.getDouble("Price");
        Tea tea = new Tea(region, type, year, price);
        menu.addTeaToMenu(tea);
    }

}
