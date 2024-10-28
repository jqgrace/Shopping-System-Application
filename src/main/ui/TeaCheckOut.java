package ui;

// Reference: fgrund, 2022. October, TellerApp, java,
// https://github.students.cs.ubc.ca/CPSC210/TellerApp

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import model.ShoppingCart;
import model.Tea;
import model.TeaMenu;
import persistence.*;

// Represents a Console based tea CheckOut System
public class TeaCheckOut {

    private Scanner input;
    private ShoppingCart shoppingCart;
    private Tea tea1;
    private Tea tea2;
    private Tea tea3;
    private Tea tea4;
    private Tea tea5;
    private Tea tea6;
    private TeaMenu menu;
    private static final String JSON_STORE = "./data/ShoppingCart.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private void init() {
        shoppingCart = new ShoppingCart();
        menu = new TeaMenu();
        tea1 = new Tea("China", "Green Tea", 1, 39.99);
        tea2 = new Tea("China", "Oolong Tea", 3, 99.99);
        tea3 = new Tea("India", "Black Tea", 2, 53.98);
        tea4 = new Tea("Japan", "Matcha", 1, 19.99);
        tea5 = new Tea("United Kingdom", "Early Grey", 2, 29.79);
        tea6 = new Tea("Argentina", "Mate Tea", 4, 15.88);

        menu.addTeaToMenu(tea1);
        menu.addTeaToMenu(tea2);
        menu.addTeaToMenu(tea3);
        menu.addTeaToMenu(tea4);
        menu.addTeaToMenu(tea5);
        menu.addTeaToMenu(tea6);

        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    public TeaCheckOut() throws FileNotFoundException {
        input = new Scanner(System.in);
        shoppingCart = new ShoppingCart();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        runCheckoutSystem();
    }

    // MODIFIES: this
    // EFFECTS: processes the customers' input
    private void runCheckoutSystem() {
        boolean process = true;
        String typedIn = "";

        init();

        while (process) {
            displayMenu();
            typedIn = input.nextLine();
            typedIn = typedIn.toLowerCase();

            if (typedIn.equals("q")) {
                process = false;
            } else {
                processTypedIn(typedIn);
            }
        }
    }

    // EFFECTS: process the command customer input
    private void processTypedIn(String key) {
        if (key.equals("m")) {
            showMenu();
        } else if (key.equals("a")) {
            addTeaToCart();
        } else if (key.equals("c")) {
            checkOut();
        } else if (key.equals("r")) {
            removeTeaFromCart();
        } else if (key.equals("v")) {
            viewCart();
        } else if (key.equals("s")) {
            saveShoppingCart();
        } else if (key.equals("l")) {
            loadShoppingCart();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: show the tea menu to customer
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tm -> Tea List(menu)");
        System.out.println("\ta -> Add tea to my cart");
        System.out.println("\tr -> Remove tea from cart");
        System.out.println("\tv -> View Cart");
        System.out.println("\tc -> Checkout");
        System.out.println("\ts -> Save the ShoppingCart history");
        System.out.println("\tl -> Load the ShoppingCart history");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: show the detailed information of tea in the menu
    private void showMenu() {
        for (Tea tea : menu.getTea()) {
            System.out.println("From: " + tea.getRegion() + " " + tea.getType() + " "
                    + tea.getYear() + " " + "$" + tea.getPrice());
        }
    }

    // MODIFIES: this
    // EFFECTS: show the total price of tea the customers has added to the cart
    private void checkOut() {
        double payment = shoppingCart.totalPrice();
        System.out.println("Your total amount is $" + payment);
    }

    // MODIFIES: this
    // EFFECTS: add tea to shopping cart
    private void addTeaToCart() {
        System.out.println("Please enter the region the tea growed in");
        String region = input.nextLine();
        System.out.println("Please enter the type of the tea");
        String type = input.nextLine();
        Tea tea = this.menu.searchAllTea(region, type);
        if (tea == null) {
            System.out.println("Sorry, we don't have the tea you are searching for.");
        } else {
            shoppingCart.addTeaToCart(tea);
        }

    }

    // MODIFIES: this
    // EFFECTS: remove tea to shopping cart
    private void removeTeaFromCart() {
        System.out.println("Please enter the region the tea growed in");
        String region = input.nextLine();
        System.out.println("Please enter the type of the tea");
        String type = input.nextLine();
        Tea tea = this.shoppingCart.searchTea(region, type);
        if (tea == null) {
            System.out.println("Sorry, we don't have the tea you are searching for.");
        } else {
            shoppingCart.removeTeaFromCart(tea);
        }
    }

    // EFFECTS: show the detailed information of tea in the shopping cart
    private void viewCart() {
        for (Tea tea : shoppingCart.getTeaInCart()) {
            System.out.println("From: " + tea.getRegion() + " " + tea.getType() + " "
                    + tea.getYear() + " " + "$" + tea.getPrice());
        }
    }

    // EFFECTS: saves the shoppingCart to file
    private void saveShoppingCart() {
        try {
            jsonWriter.open();
            jsonWriter.writeSC(shoppingCart);
            jsonWriter.close();
            System.out.println("Saved" + shoppingCart.getTeaInCart() + "to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, unable to write to file:" + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: load shoppingCart from file
    private void loadShoppingCart() {
        try {
            shoppingCart = jsonReader.readShoppingCart();
            System.out.println("Successfully loaded" + shoppingCart.getTeaInCart() + "from" + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Sorry, unable to write to file:" + JSON_STORE);
        }
    }

}
