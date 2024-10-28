package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a ShoppingCart that customers can add their tea inside
public class ShoppingCart implements Writable {

    private List<Tea> teaAddedInCart;

    // MODIFIES: this
    // EFFECTS: creates an empty shopping cart
    public ShoppingCart() {
        teaAddedInCart = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add tea to the shopping cart
    public void addTeaToCart(Tea tea) {
        teaAddedInCart.add(tea);
        EventLog.getInstance().logEvent(new Event("New item has been added to the ShoppingCart"
                + "(" + tea.getType() + " from " + tea.getRegion() + " -- Price: $" + tea.getPrice() + ")"));
    }

    // MODIFIES: this
    // EFFECTS: remove tea from the shopping cart
    public void removeTeaFromCart(Tea tea) {
        teaAddedInCart.remove(tea);
        EventLog.getInstance().logEvent(new Event("(" + tea.getType() + " from " + tea.getRegion()
                + " -- Price: $" + tea.getPrice() + ")" + " has been removed from the ShoppingCart"));

    }

    // EFFECTS: return the type of tea in cart
    public List<Tea> getTeaInCart() {
        if (teaAddedInCart == null) {
            teaAddedInCart = new ArrayList<>();
        }
        return teaAddedInCart;
    }

    // MODIFIES: this
    // EFFECTS: add the amount of numberOfTeas to the ShoppingCart
    public void addMultipleTeas(int numberOfTeas, Tea tea) {
        for (int i = 0; i < numberOfTeas; i++) {
            Tea newTea = new Tea(tea.getRegion(), tea.getType(), tea.getYear(), tea.getPrice());
            teaAddedInCart.add(newTea);
        }
        EventLog.getInstance().logEvent(new Event(numberOfTeas + " packages of " + tea.getType() + " from "
                + tea.getRegion() + " has been added to the ShoppingCart!"));

    }

    // MODIFIES: this
    // EFFECTS: calculate total price of tea the consumer wants to purchase
    public double totalPrice() {
        double payment = 0;
        for (Tea tea : teaAddedInCart) {
            if (tea != null) {
                payment += tea.getPrice();
            } else {
                System.out.println("Null tea found in cart");
            }
        }
        EventLog.getInstance().logEvent(new Event("Ready to checkout? \n Your total payment is $" + payment + "."));
        return payment;
    }

    // EFFECTS: search for the tea that matches the input region and type,
    // if such tea exist in this store, return all tea that matches the input,
    // otherwise, return "This tea is not found in the shopping cart."
    public Tea searchTea(String region, String type) {
        for (Tea tea : teaAddedInCart) {
            if (tea.getRegion().equals(region) && tea.getType().equals(type)) {
                return tea;
            }
        }
        System.out.println("This tea is not found in the shopping cart.");
        return null;
    }

    // MODIFIES: this
    // EFFECTS: empty the shopping cart
    public void clearCart() {
        this.teaAddedInCart = new ArrayList<>();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tea", teaToJason());
        return json;
    }

    // EFFECTS: return things in the shoppingCart as a JSON array
    public JSONArray teaToJason() {
        JSONArray jsonList = new JSONArray();

        for (Tea tea : teaAddedInCart) {
            jsonList.put(tea.toJson());
        }

        return jsonList;
    }

}
