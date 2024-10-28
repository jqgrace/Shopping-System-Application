# My Personal Project

## Introduction

Lately, I've become quite interested in tea and amazed by the various flavors it offers from different types. However, I've encountered a significant problem: many tea stores have inefficient shooping systems that fail to narrow down options according to the customers' needs. This challenge inspired me to design a user-friendly shopping system for customers.

In my system, the customers can specify criteria such as the tea's growing region, growing years, type, and price range to find the best match. After selection, a page of more detailed introduction of this tea will be presented. If customers are interested in more than one tea, they can save their preferred options to the "My Shopping Cart". They can select the tea they want to purchase and the total price will be calculated automatically.

This project merges computer science with real-world applications, has genuinely piqued my interest by offering a practical solution to optimize business operations. It not only makes the check out process more efficient but also adds real value, which has motivated me to design this.

## User Stories

**User-Friendly Filter System**:

- As a user, I want to be able to view the list of tea I am interested in to purchase.
- As a user, I want to be able to add my favourite tea to *"My Shopping Cart"*.
- As a user, I want to be able to remove tea from *"My Shopping Cart"*.
- As a user, I want to be able to select the tea I added and view its detailed features.
- As a user, I want to be able to see the total price of all tea I added to the shopping cart.
- As a user, I want to be able to save my shopping system to file.
- As a user, I want to be able to be able to load my shopping system from file.


## *Instruction for Grader*
- You can generate the first required action "add multiple Xs to Y" by clicking "Add Item" button. Select the type of tea and input the amount of that tea you would like to purchase. After clicking the "OK", they will be added into the ShoppingCart and display at the top part of this page.
- You can generate the second required action "remove multiple Xs to Y" by clicking "Remove Item" button. Select the tea you want to remove and click "OK". After clicking the "OK", they will be removed from the ShoppingCart and remove from the top part of this page.
- You can "viewing all Xs in Y" from the top half of this page.
- You can save the state of my application by clicking the "save" at the MenuBar.
- You can reload the state of my application by choose "Yes" at the login page.
- You can locate my visual component on the login page, which is an image of tea. 
- You can locate another visual component on the main page by clicking the "View Item", then a window displaying all the items that this store sells will pop up.


## Phase 4: Task 2
The events I choose to log are:
1. add tea to ShoppingCart
2. remove tea from ShoppingCart
3. add multiple tea to ShoppingCart
4. calculate the total price


- Tue Aug 06 23:39:19 PDT 2024
- 2 packages of Green Tea from China has been added to the ShoppingCart!
- Tue Aug 06 23:39:30 PDT 2024
- 3 packages of Early Grey from United Kingdom has been added to the ShoppingCart!
- Tue Aug 06 23:39:37 PDT 2024
- (Green Tea from China -- Price: $39.99) has been removed from the ShoppingCart
- Tue Aug 06 23:39:42 PDT 2024
- (Early Grey from United Kingdom -- Price: $29.79) has been removed from the ShoppingCart
- Tue Aug 06 23:39:56 PDT 2024
- Ready to checkout?
-  Your total payment is $99.57.


 ### Explanation:
- The fist four line is examples of logging the event of "add multiple tea to the ShoppingCart", which is one of the required actions.
- The next four line is examples of logging the event of "remove tea from the ShoppingCart", which is the another required actions.
- The last two line is an example of "checkout the bill" event.


## Phase 4: Task 3
The first improvement I would make is to split the CheckOutUI class into multiple classes, with each class responsible for only one panel. Currently, my logPanel, mainPanel, controlPanel, etc., are all in the same class. If I wanted to add some more functions or revise a specific panel, it would be very painful and time-consuming as I would have to search through the entire class to find the right place to make changes. With this improvement, the cohesion of the program would be greatly increased.

Another change is to decrease the redundancy in my code. For example, the methods addListenerToRemoveItem and addListenerToAddItem are somewhat similar in how they construct the JPanel and scan the teaList to add tea to the ComboBox. The only differences are the JLabel of the ComboBox and the specific function. What I could do is extract the common code to a new method and call this new method from both of the original methods. Another pair of methods that are highly similar are getUsername and getPassword. These two methods differ only in the JLabel and the y-position passed in. Therefore, I would pass the label text and Y-position as parameters, allowing the method to configure and add different types of fields (like usernames or passwords) dynamically to the GUI.
