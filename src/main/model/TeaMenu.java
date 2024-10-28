package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a menu that customers can read to know which kind of tea is available in this store
public class TeaMenu implements Writable {
    private List<Tea> teaMenu;

    // MODIFIES: this
    // EFFECTS: construct a empty tea menu
    public TeaMenu() {
        this.teaMenu = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add tea to the menu, you can add same tea repeatedly
    public void addTeaToMenu(Tea tea) {
        teaMenu.add(tea);
    }

    public List<Tea> getTea() {
        return teaMenu;
    }

    // EFFECTS: search for the tea that matches the input region and type;
    // if there exist a tea matches the input, return tea.
    // Otherwise, "This tea currently is unavailable in this store." and return
    // null.
    public Tea searchAllTea(String region, String type) {
        for (Tea tea : teaMenu) {
            if (tea.getRegion().equals(region) && tea.getType().equals(type)) {
                System.out.println("This tea is currently available in this store.");
                return tea;
            }
        }
        System.out.println("This tea currently is unavailable in this store.");
        return null;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("teaMenu", teaMenuToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray teaMenuToJson() {
        JSONArray jsonMenuArray = new JSONArray();

        for (Tea tea : teaMenu) {
            jsonMenuArray.put(tea.toJson());
        }

        return jsonMenuArray;
    }
}
