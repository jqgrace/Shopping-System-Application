package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a tea has growing region, growing years, type, and price per 500g.
public class Tea implements Writable {

    private String region;
    private String type;
    private int year;
    private double price;

    // EFFECTS: construct a tea with its growing region, growing years, type, and
    // price.
    public Tea(String r, String t, int y, double p) {
        this.region = r;
        this.type = t;
        this.year = y;
        this.price = p;
    }

    public String getRegion() {
        return region;
    }

    public String getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    // EFFECTS: combine all the information of tea to a sentence
    public String toString() {
        return region + " - " + type + " - " + year + " years" + " - " + price;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Region", region);
        json.put("Type", type);
        json.put("Year", year);
        json.put("Price", price);
        return json;
    }

}
