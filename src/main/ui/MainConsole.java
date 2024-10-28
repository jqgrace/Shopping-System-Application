package ui;

import java.io.FileNotFoundException;

public class MainConsole {
    public static void main(String[] args) {
        try {
            new TeaCheckOut();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
