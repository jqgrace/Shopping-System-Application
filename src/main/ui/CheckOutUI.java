package ui;

/*  References:
 1. Paul Carter, java
    https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter
 2. Paul Carter, java 
    https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
 3. Cillian Dong, java
    https://github.com/dongbaiqi22/Restaurant-Management-System-
 4. fgrund, java
    https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter.git
 5. Paul Carter, java
    https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase.git
 6. https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html 
*/

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.util.List;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import model.Event;


// Represents a Self CheckOut System GUI

public class CheckOutUI extends JFrame {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 409;

    private static final String JSON_SC = "./data/ShoppingCart.json";
    private JsonWriter jsonWriterSC;
    private JsonReader jsonReaderSC;

    private Tea t1;
    private Tea t2;
    private Tea t3;
    private Tea t4;
    private Tea t5;
    private Tea t6;

    private ShoppingCart teaInCart;
    private List<Tea> teaList;

    private TeaMenu teaMenu;

    private JTextField username;
    private JTextField password;
    private JPanel controlPanel;
    private JPanel logPanel;
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private Vector<String> orderVector;
    private JTable orderTable;
    private JComboBox<String> orderComboBox;

    public CheckOutUI() {
        super();
        initializeField();
        initializeGraphics();
        login();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: set up six tea field
    private void setUp() {
        t1 = new Tea("China", "Green Tea", 1, 39.99);
        t2 = new Tea("China", "Oolong Tea", 3, 99.99);
        t3 = new Tea("India", "Black Tea", 2, 53.98);
        t4 = new Tea("Japan", "Matcha", 1, 19.99);
        t5 = new Tea("United Kingdom", "Early Grey", 2, 29.79);
        t6 = new Tea("Argentina", "Mate Tea", 4, 15.88);
    }

    // MODIFIES: this
    // EFFECTS: set up the other fields of this GUI
    private void initializeField() {
        teaMenu = new TeaMenu();

        setUp();
        teaInCart = new ShoppingCart();
        teaMenu.addTeaToMenu(t1);
        teaMenu.addTeaToMenu(t2);
        teaMenu.addTeaToMenu(t3);
        teaMenu.addTeaToMenu(t4);
        teaMenu.addTeaToMenu(t5);
        teaMenu.addTeaToMenu(t6);

        teaList = new ArrayList<>();
        teaList.add(t1);
        teaList.add(t2);
        teaList.add(t3);
        teaList.add(t4);
        teaList.add(t5);
        teaList.add(t6);

        jsonReaderSC = new JsonReader(JSON_SC);
        jsonWriterSC = new JsonWriter(JSON_SC);

        orderVector = new Vector<>();

        updateOrderVector();
        setOrderComboBox();
    }

    // MODIFIES: this
    // EFFECTS: create the JFrame window for the CheckOutUI to operate
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    // MODIFIES: this
    // EFFECTS: set up orderComboBox which contains all items avilable in store
    private void setOrderComboBox() {
        orderComboBox = new JComboBox<>(this.orderVector);
        orderComboBox.setSize(new Dimension(300, 30));
        orderComboBox.setBounds(360, 150, 400, 30);
        addListenerToComboBox(orderComboBox);
    }

    // MODIFIES: welcomePanel
    // EFFECTS: adds an image to welcomePanel
    private void addImage() {
        try {
            BufferedImage image = ImageIO.read(new File("./data/TeaImage (1).jpg"));
            ImageIcon imageIcon = new ImageIcon(image);
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setBounds(110, 180, 264, 170);
            logPanel.add(imageLabel);
            logPanel.revalidate();
            logPanel.repaint();
        } catch (IOException e) {
            System.out.println("Image error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // EFFECTS: build a login page
    // letting user to input his/her username and the password,
    // provide the choices for whether load in previous data
    private void login() {
        logPanel = new JPanel();
        logPanel.setSize(new Dimension(1000, 1300));
        logPanel.setBackground(Color.white);
        add(logPanel);
        logPanel.setLayout(null);
        getUsername();
        getPassword();
        addLogButton();
        addImage();
    }

    // MODIFIES: this
    // EFFECTS: create and add buttons to login page.
    // provide options for customers to choose to log in with/without loading the
    // previous data
    private void addLogButton() {
        JLabel instruction = new JLabel("Log in with your shopping cart hsitory?");
        instruction.setBounds(130, 110, 500, 30);
        logPanel.add(instruction);

        JButton yesButton = new JButton("Yes");
        yesButton.setBounds(130, 145, 60, 31);
        addListenerToYesButton(yesButton);

        logPanel.add(yesButton);

        JButton noButton = new JButton("No");
        noButton.setBounds(320, 145, 60, 31);
        addListenerToNoButton(noButton);

        logPanel.add(noButton);

        revalidate();
    }

    // MODIFIES: noButton
    // EFFECTS: press the button and login without the data loaded
    private void addListenerToNoButton(JButton noButton) {

        noButton.addActionListener(new AbstractAction() {

            // MODIFIES: this
            // EFFECTS: jump into main panel without loading shopping history

            @Override
            public void actionPerformed(ActionEvent e) {
                remove(logPanel);
                updateFrameForNo();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: resize the frame to width 900, height 700,
    // set mainPanel, controlPanel into this frame
    private void updateFrameForNo() {
        setSize(new Dimension(900, 700));
        setLayout(new GridLayout(2, 1));
        addWindowListenerClosing();
        setMenuBar();
        setOrderComboBox();
        String[][] empty = {};
        setMainPanel(empty);
        setControlPanel();
    }

    // MODIFIES: this
    // EFFECTS: turn all the items information to a vector, called OrderVector,
    // orderVector contains the type and region of items in the menu
    private void updateOrderVector() {
        List<Tea> teas = teaMenu.getTea();
        Vector<String> vector = new Vector<>();
        for (Tea t : teas) {
            String typeAndRegion = t.getType() + " -- " + t.getRegion();
            vector.add(typeAndRegion);
        }
        this.orderVector = vector;
    }

    // MODIFIES: yesButton
    // EFFECTS: press the button and login with the data loaded

    private void addListenerToYesButton(JButton yesButton) {

        yesButton.addActionListener(new AbstractAction() {

            // MODIFIES: this
            // EFFECTS: jump into main panel with loading shopping history

            @Override
            public void actionPerformed(ActionEvent e) {
                remove(logPanel);
                loadCartGUI();
                updateFrameForYes();
                revalidate();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: resize the JFrame to width 900, height 700, add
    // mainPanel, controlPanel into the frame and load in previous data
    private void updateFrameForYes() {
        setSize(new Dimension(900, 700));
        setLayout(new GridLayout(2, 1));
        addWindowListenerClosing();
        setMenuBar();
        updateOrderVector();
        setOrderComboBox();
        String[][] data = {};

        if (teaInCart == null) {
            teaInCart = new ShoppingCart();
        }
        List<Tea> teas = teaInCart.getTeaInCart();
        data = translateOrder(teas);

        setMainPanel(data);
        setControlPanel();

        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: create a JLabel asking for the username of the customer,
    // create a JTextField where to type the username
    private void getUsername() {
        JLabel askUsername = new JLabel("Username: ");
        askUsername.setBounds(100, 20, 150, 30);
        logPanel.add(askUsername);

        username = new JTextField();
        username.setBounds(180, 20, 150, 30);
        logPanel.add(username);

        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: create a JLabel asking for the password of the customer,
    // create a JTextField where to input their password, then put them on logPanel
    private void getPassword() {
        JLabel askUsername = new JLabel("Password: ");
        askUsername.setBounds(100, 60, 150, 30);
        logPanel.add(askUsername);

        password = new JPasswordField();
        password.setBounds(180, 60, 150, 30);
        logPanel.add(password);

        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: create a menuBar to save shoppingCart data
    // save has one item, "save"
    private void setMenuBar() {
        menuBar = new JMenuBar();
        JMenu save = new JMenu("save");
        JMenuItem saveData = new JMenuItem("save");
        save.add(saveData);
        menuBar.setBorderPainted(true);
        menuBar.setPreferredSize(new Dimension(1000, 20));
        menuBar.setBounds(0, 0, 1000, 30);
        menuBar.add(save);
        addListenerToSave(saveData);
        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: set up main panel and put it into the frame,
    // create a JTable with two columns Item, Price
    // when information passed in, put the table into main panel
    private void setMainPanel(String[][] rows) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        String[] columnNames = { "Item", "Price" };

        orderTable = new JTable(rows, columnNames);

        JScrollPane scrollPane = new JScrollPane(orderTable);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: saves the shoppingCart to file
    private void saveCartGUI() {
        try {
            jsonWriterSC.open();
            jsonWriterSC.writeSC(teaInCart);
            jsonWriterSC.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file:" + JSON_SC);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the shoppingCart from file
    private void loadCartGUI() {
        try {
            teaInCart = jsonReaderSC.readShoppingCart();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_SC);
        }
    }

    // MODIFIES: save
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed
    // to save
    private void addListenerToSave(JMenuItem save) {
        save.addActionListener(new AbstractAction() {

            // MODIFIES: this
            // EFFECTS: save the data of current state
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCartGUI();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: set controlPanel, create three buttons, "Add Items", "Remove
    // Item","CheckOut",
    // and put the control panel into the frame
    private void setControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setBackground(new Color(0, 0, 0, 0));
        JButton addItem = new JButton("Add Items");
        JButton removeItem = new JButton("Remove Item");
        JButton check = new JButton("CheckOut");
        JButton viewMenuButton = new JButton("View Items");
        viewMenuButton.addActionListener(e -> displayMenu());
        controlPanel.add(viewMenuButton);
        add(controlPanel, BorderLayout.SOUTH);
        addListenerToCheck(check);
        addListenerToRemoveItem(removeItem);
        addListenerToAddItem(addItem);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.add(addItem);
        buttonPanel.add(removeItem);
        buttonPanel.add(check);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(controlPanel);

    }

    // EFFECTS: show the full menu when click the button
    @SuppressWarnings("methodlength")
    private void displayMenu() {
        JFrame menuFrame = new JFrame("Tea Menu");
        menuFrame.setSize(600, 400);
        menuFrame.setLocationRelativeTo(null);

        String[] columnNames = { "Region", "Type", "Year", "Price" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        try (InputStream is = new FileInputStream("./data/menu.json")) {
            JSONTokener tokener = new JSONTokener(is);
            JSONObject jsonObject = new JSONObject(tokener);
            JSONArray teaMenu = jsonObject.getJSONArray("teaMenu");

            for (int i = 0; i < teaMenu.length(); i++) {
                JSONObject tea = teaMenu.getJSONObject(i);
                String region = tea.getString("Region");
                String type = tea.getString("Type");
                int year = tea.getInt("Year");
                double price = tea.getDouble("Price");
                Object[] row = { region, type, year, price };
                tableModel.addRow(row);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading the menu file: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        menuFrame.add(scrollPane);
        menuFrame.setVisible(true);
    }

    // MODIFIES: check
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed
    // to checkOut
    private void addListenerToCheck(JButton check) {
        check.addActionListener(new ActionListener() {

            // MODIFIES: this
            // EFFECTS: display a JOptionPane.showConfirmDialog that shows the total price
            // of
            // the tea added
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel checkOutPanel = new JPanel();

                String finalDue = "Your total due is $" + teaInCart.totalPrice() + ".";

                JLabel due = new JLabel(finalDue);

                due.setPreferredSize(new Dimension(200, 50));

                checkOutPanel.add(due);

                int result = JOptionPane.showConfirmDialog(null, checkOutPanel,
                        "Bill", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    teaInCart.clearCart();
                    updateMainPanel();
                    updateControlPanel();
                }
            }
        });
    }

    // MODIFIES: Assign Customer
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed
    // to assign customer
    @SuppressWarnings("methodlength")
    private void addListenerToAddItem(JButton assign) {
        assign.addActionListener(e -> {
            JPanel inputsPanel = new JPanel(new GridLayout(0, 1));
            JComboBox<Tea> teaComboBox = new JComboBox<>();
            for (Tea tea : teaList) {
                teaComboBox.addItem(tea);
            }
            JTextField numberField = new JTextField(5);
            inputsPanel.add(new JLabel("Select Tea:"));
            inputsPanel.add(teaComboBox);
            inputsPanel.add(new JLabel("Number of teas:"));
            inputsPanel.add(numberField);

            int result = JOptionPane.showConfirmDialog(null, inputsPanel, "Enter Tea Information",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    Tea selectedTea = (Tea) teaComboBox.getSelectedItem();
                    int num = Integer.parseInt(numberField.getText());
                    teaInCart.addMultipleTeas(num, selectedTea);
                    createSpreadsheet();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number");
                }
            }
        });
    }

    // MODIFIES: spreadsheet
    // EFFECTS: build a spreadsheet that shows the items has added into the
    // ShoppingCart
    private void createSpreadsheet() {
        String[] columnNames = { "Item", "Price" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        for (Tea tea : teaInCart.getTeaInCart()) {
            Object[] row = { tea.getType(), tea.getPrice() };
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.removeAll();
        mainPanel.add(scrollPane);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // MODIFIES: remove item
    // EFFECTS: adds ActionListener with specific implementation to remove items
    // form ShoppingCart
    private void addListenerToRemoveItem(JButton removeItem) {
        removeItem.addActionListener(e -> {
            JPanel inputsPanel = new JPanel(new GridLayout(0, 1));
            JComboBox<Tea> teaComboBox = new JComboBox<>();
            for (Tea tea : teaInCart.getTeaInCart()) {
                teaComboBox.addItem(tea);
            }

            inputsPanel.add(new JLabel("Select Tea to Remove:"));
            inputsPanel.add(teaComboBox);

            int result = JOptionPane.showConfirmDialog(null, inputsPanel, "Remove Tea", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Tea selectedTea = (Tea) teaComboBox.getSelectedItem();
                teaInCart.removeTeaFromCart(selectedTea);
                createSpreadsheet();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: updates the order information of current table
    private void updateMainPanel() {
        mainPanel.removeAll();

        if (teaInCart == null) {
            teaInCart = new ShoppingCart();
        }
        List<Tea> teas = teaInCart.getTeaInCart();
        String[][] orderData = translateOrder(teas);

        String[] columnNames = { "Item", "Price" };
        DefaultTableModel model = new DefaultTableModel(orderData, columnNames);
        orderTable.setModel(model);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // EFFECTS: turn the orders information to String[][], returns it
    private String[][] translateOrder(List<Tea> teas) {
        String[][] ordersInfo = new String[teas.size()][2];

        for (int i = 0; i < teas.size(); i++) {
            Tea t = teas.get(i);
            ordersInfo[i][0] = t.getRegion() + " -- " + t.getType();
            ordersInfo[i][1] = String.valueOf(t.getPrice());
        }

        return ordersInfo;
    }

    // MODIFIES: this
    // EFFECTS: update the components on control panel according to the current
    // state
    private void updateControlPanel() {
        controlPanel.revalidate();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds ActionListener with specific implementation of actionPerformed
    // to orderBox
    private void addListenerToComboBox(JComboBox orderComboBox) {
        orderComboBox.addActionListener(e -> {
            String selectedType = (String) orderComboBox.getSelectedItem();
            Tea selectedTea = findTeaByType(selectedType);
            if (selectedTea != null) {
                System.out.println("Selected tea: " + selectedTea);
            } else {
                System.out.println("Tea not found");
            }
        });
    }

    private Tea findTeaByType(String type) {
        for (Tea tea : teaList) {
            if (tea.getType().equals(type)) {
                return tea;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: add WindowListener with windowClosing to the frame
    private void addWindowListenerClosing() {
        // EFFECTS: print all events in log to the console when the window is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printEvents();
            }
        });
    }

    // EFFECTS: print all events from the start of this system
    private void printEvents() {
        for (Event next : EventLog.getInstance()) {
            if (!next.getDescription().equals("Event log is cleared.")) {
                System.out.println(next.toString());
            }
        }
    }

}
