import java.time.*;
import java.util.*;

/* This module serves as the main control class for the Pizza Ordering System.
   It coordinates all pizza ordering functions, customer management, and order processing.
   This module stores customers, pizzas, orders, customizations, and promotions in ArrayList data structures.
   A menu-driven interface allows users to access various system functions including order placement,
   customer management, feedback handling, and order tracking. The system also includes a loyalty
   points program and promotional discount features. */

public class PizzaOr_sys {
    /*These are the core data structures for the system
    5 containers created for customers, pizzas, orders, customizations, and promotions*/
    private ArrayList<Customer> customers;
    private ArrayList<Pizza> pizzas;
    private ArrayList<Order> orders;
    private ArrayList<Customization> customizations;
    private ArrayList<Promotion> promotions;
    private Scanner scanner;

    public PizzaOr_sys() {
        customers = new ArrayList<>();
        pizzas = new ArrayList<>();
        orders = new ArrayList<>();
        customizations = new ArrayList<>();
        promotions = new ArrayList<>();
        scanner = new Scanner(System.in);
        initializeCustomizations();
        initializePizzas();
        initializePromotions();
    }
    // Initializes system components and loads initial data
    private void initializeCustomizations() {
        // Initialize default Crusts
        customizations.add(new Crust("Thin Italian", 0.0, "thin"));
        customizations.add(new Crust("Deep Pan", 50.0, "thick"));
        customizations.add(new Crust("Stuffed Crust", 100.0, "thick"));

        // Initialize default Sauces
        customizations.add(new Sauce("Tomato", 0.0));
        customizations.add(new Sauce("BBQ", 30.0));
        customizations.add(new Sauce("Spicy", 30.0));

        // Initialize all possible toppings
        customizations.add(new Topping("Pepperoni", 200.0));
        customizations.add(new Topping("Mushrooms", 150.0));
        customizations.add(new Topping("Extra Cheese", 180.0));
        customizations.add(new Topping("Bell Peppers", 120.0));
        customizations.add(new Topping("Ham", 200.0));
        customizations.add(new Topping("Pineapple", 150.0));
    }
     // Initialize Pre-defiend Pizza options
    private void initializePizzas() {
        Crust defaultCrust = (Crust) findCustomization("Thin Italian", "CRUST");
        Sauce defaultSauce = (Sauce) findCustomization("Tomato", "SAUCE");
        
        // Cheese Blast - Simple cheese pizza
        Pizza cheeseblast = new Pizza("Cheese Blast", defaultCrust, defaultSauce, 1200.0);
        pizzas.add(cheeseblast);
        
        // Pepperoni Supreme
        Pizza pepperoni = new Pizza("Pepperoni Supreme", defaultCrust, defaultSauce, 1500.0);
        pizzas.add(pepperoni);
        
        // Hawaiian
        Pizza hawaiian = new Pizza("Hawaiian (Ham & Pineapple)", defaultCrust, defaultSauce, 1400.0);
        pizzas.add(hawaiian);

        // Veggie Feast
        Pizza veggieFeast = new Pizza("Veggie Feast (Mushrooms, Peppers, Onions)", defaultCrust, defaultSauce, 1300.0);
        pizzas.add(veggieFeast);

        // Meat Lovers
        Pizza meatLovers = new Pizza("Meat Lovers (Pepperoni, Ham, Bacon)", defaultCrust, defaultSauce, 1600.0);
        pizzas.add(meatLovers);
    }
    // Sets up initial promotional offers
    private void initializePromotions() {
        LocalDate now = LocalDate.now();
        promotions.add(new Promotion("WELCOME", "Welcome Discount", 10.0, 
            now, now.plusMonths(1)));
        promotions.add(new Promotion("SPECIAL", "Weekend Special", 15.0, 
            now, now.plusWeeks(1)));
    }
    // Finds customization option by name and category
    private Customization findCustomization(String name, String category) {
        for (Customization c : customizations) {
            if (c.getName().equals(name) && c.getCategory().equals(category)) {
                return c;
            }
        }
        return null;
    }
    // Main system menu and control loop
    public void run() {
        while (true) {
            System.out.println("\nPizza Ordering System");
            System.out.println("1. Manage Customers");
            System.out.println("2. Place Order");
            System.out.println("3. Track Order");
            System.out.println("4. Update Order Status");
            System.out.println("5. Manage Feedback");
            System.out.println("6. View Promotions");
            System.out.println("7. View All Orders");
            System.out.println("8. View Order Details");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: manageCustomers(); break;
                case 2: placeOrder(); break;
                case 3: trackOrder(); break;
                case 4: updateOrderStatus(); break;
                case 5: manageFeedback(); break;
                case 6: viewPromotions(); break;
                case 7: viewAllOrders(); break;
                case 8: viewOrderDetails(); break;
                case 9:
                    System.out.println("Thank you for using Pizza Ordering System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void placeOrder() {
        System.out.print("Enter customer ID: "); // Prompt user for customer ID
        String customerId = scanner.nextLine();
        Customer customer = findCustomer(customerId);
        
        if (customer == null) { // Check if the customer exists
            System.out.println("Customer not found. Please register first.");
            return;
        }

        // Add pizzas to order
        Order order = new Order("", customer, "PICKUP", 0.0); // Temporary delivery type
            
            while (true) { // Display pizza selection menu
            System.out.println("\n1. Add Pre-defined Pizza");
            System.out.println("2. Create Custom Pizza");
            System.out.println("3. Review and Checkout");
            System.out.print("Choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 3) break; // Exit loop for checkout
            
            if (choice == 1) {
                addPredefinedPizza(order); // Add predefined pizza to order
            } else if (choice == 2) {
                createCustomPizza(order); // Create custom pizza and add to order
            }
        }

        // After pizza selection, choose delivery type
        System.out.println("\nSelect delivery type:");
        System.out.println("1. Pick Up (Free)");
        System.out.println("2. Delivery (Distance-based charges)");
        System.out.print("Choice: ");
        
        int deliveryChoice = scanner.nextInt();
        scanner.nextLine();

        if (deliveryChoice == 2) { // Display delivery zones and charges
            System.out.println("\nDelivery zones and charges:");
            System.out.println("1. Within 5km - LKR 200");
            System.out.println("2. 5-10km - LKR 500");
            System.out.println("3. Beyond 10km - LKR 800");
            System.out.print("Select your zone: ");
            
            int zoneChoice = scanner.nextInt();
            scanner.nextLine();

            double deliveryCharge;
            switch (zoneChoice) {
                case 1: deliveryCharge = 200.0; break;
                case 2: deliveryCharge = 500.0; break;
                case 3: deliveryCharge = 800.0; break;
                default: 
                    System.out.println("Invalid zone. Defaulting to within 5km.");
                    deliveryCharge = 200.0;
            }
            order.setDeliveryType("DELIVERY", deliveryCharge); // Set delivery type and charge
        }

        // Display order summary
        order.displayOrderSummary();

        // Confirm order
        System.out.print("\nWould you like to confirm this order? (Y/N): ");
        String confirm = scanner.nextLine().toUpperCase();
        if (!confirm.equals("Y")) {
            order.setState(new CancelledState());
            System.out.println("Order cancelled.");
            return;
        }

        // Apply promotion if available
        System.out.print("\nEnter promotion code (or press enter to skip): ");
        String promoCode = scanner.nextLine();
        if (!promoCode.isEmpty()) {
            Promotion promo = findPromotion(promoCode);
            if (promo != null && promo.isValid()) {
                order.applyPromotion(promo);
                System.out.println("Promotion applied successfully!");
                order.displayOrderSummary();  // Display order summary after applying promotion
            } else {
                System.out.println("Invalid or expired promotion code.");
            }
        }

        // Process payment - Payment option list
        System.out.println("\nSelect payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. PayPal");
        System.out.println("3. Cash");
        System.out.print("Choice: ");
        
        int paymentChoice = scanner.nextInt();
        scanner.nextLine();

        PaymentStrategy paymentStrategy;
        switch (paymentChoice) {
            case 1:
                System.out.print("Enter credit card number: ");
                String cardNumber = scanner.nextLine();
                paymentStrategy = new CreditCardPayment(cardNumber);
                break;
            case 2:
                System.out.print("Enter PayPal email: ");
                String email = scanner.nextLine();
                System.out.print("Enter PayPal password: ");
                String password = scanner.nextLine();
                paymentStrategy = new PayPalPayment(email, password);
                break;
            case 3:
                String paymentType = order.getDeliveryType().equals("DELIVERY") ? "ON_DELIVERY" : "ON_PICKUP";
                paymentStrategy = new CashPayment(paymentType);
                break;
            default:
                System.out.println("Invalid payment method.");
                return;
        }

        // Process payment and confirm order
        order.processPayment(paymentStrategy);
        
        if (order.isPaid()) {
            orders.add(order);
            customer.addOrder(order);
            System.out.println("\nOrder placed successfully!");
            System.out.println("Your order ID is: " + order.getOrderId());
            System.out.println("You can use this ID to track your order status.");
        } else {
            System.out.println("Payment failed. Order not placed.");
        }
    }

    private void manageCustomers() { // Loop for customer management options
        while (true) { // Display customer management menu
            System.out.println("\nCustomer Management");
            System.out.println("1. Add New Customer");
            System.out.println("2. View Customer Details");
            System.out.println("3. View Loyalty Points");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) { // Perform action based on user choice
                case 1: addNewCustomer(); break; // Add a new customer
                case 2: viewCustomerDetails(); break; // View customer details
                case 3: viewLoyaltyPoints(); break; // View customer's loyalty point
                case 4: return; // Return to main menu
                default: System.out.println("Invalid choice."); // Handle invalid input
            }
        }
    }

    private void addNewCustomer() { // Collect customer information from user input
        System.out.print("Enter customer ID (NIC/Passport): ");
        String id = scanner.nextLine();
        
        // Check if customer ID already exists
        if (findCustomer(id) != null) {
            System.out.println("Error: A customer with ID " + id + " already exists!");
            return;
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        Customer customer = new Customer(id, name, address, phone, email);
        customers.add(customer);
        System.out.println("Customer added successfully!");
    }

    private void viewCustomerDetails() { // Prompt for customer ID and retrieve details
        System.out.print("Enter customer ID: ");
        String id = scanner.nextLine();
        Customer customer = findCustomer(id);
        if (customer != null) {
            customer.showDetails(); // Display customer details
        } else {
            System.out.println("Customer not found."); // Handle customer not found
        }
    }

    private void viewLoyaltyPoints() { // Prompt for customer ID and retrieve loyalty points
        System.out.print("Enter customer ID: ");
        String id = scanner.nextLine();
        Customer customer = findCustomer(id);
        if (customer != null) {
            int points = customer.getLoyaltyPoints();
            Customer.LoyaltyTier currentTier = Customer.LoyaltyTier.getTierFromPoints(points);
            
            System.out.println("\n--------------- LOYALTY STATUS ---------------");
            System.out.println("Current Points: " + points);
            System.out.println("Current Tier: " + currentTier.getName());
            System.out.println("Tier Discount: " + currentTier.getDiscountPercentage() + "%");
            
            // Show progress to next tier
            Customer.LoyaltyTier nextTier = null;
            for (Customer.LoyaltyTier tier : Customer.LoyaltyTier.values()) {
                if (tier.getMinPoints() > points) {
                    nextTier = tier;
                    break;
                }
            }
            
            if (nextTier != null) {
                int pointsNeeded = nextTier.getMinPoints() - points;
                System.out.println("\nPoints needed for " + nextTier.getName() + ": " + pointsNeeded);
                System.out.println("Next tier benefits: " + nextTier.getDiscountPercentage() + "% discount");
            } else {
                System.out.println("\nCongratulations! You've reached the highest tier!");
            }
            System.out.println("-------------------------------------------");
        } else {
            System.out.println("Customer not found.");
        }
    }

    private void addPredefinedPizza(Order order) {
        System.out.println("\nAvailable Pizzas:"); // Display the list of available pizzas
        for (int i = 0; i < pizzas.size(); i++) {
            System.out.println((i + 1) + ". " + pizzas.get(i).getName());
        }
        System.out.print("Select pizza (1-" + pizzas.size() + "): ");
        int choice = scanner.nextInt() - 1;
        scanner.nextLine(); // User selects a pizza
        // Add the selected pizza to the order
        if (choice >= 0 && choice < pizzas.size()) {
            Pizza selectedPizza = pizzas.get(choice);
            order.addPizza(selectedPizza);
            System.out.println("Pizza added to order!");
        } else {
            System.out.println("Invalid choice! Please select a number between 1 and " + pizzas.size());
        }
    }

    private void createCustomPizza(Order order) {
        System.out.print("Enter name for your custom pizza: "); // Prompt user for custom pizza name
        String name = scanner.nextLine();

        // First check if customer has any saved pizzas with this name
        Customer customer = order.getCustomer();
        Pizza savedPizza = customer.getSavedPizzas().stream() // Check if customer has any saved pizzas with this name
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (savedPizza != null) { // If saved pizza found, prompt for reordering
            System.out.println("Found a saved pizza with this name!");
            System.out.println("Would you like to reorder it? (Y/N): ");
            if (scanner.nextLine().toUpperCase().equals("Y")) {
                order.addPizza(new Pizza(savedPizza)); // Create a copy of the saved pizza
                return;
            }
        }

        // Select crust for the custom pizza
        System.out.println("\nAvailable Crusts:");
        List<Crust> crusts = getCustomizationsOfType("CRUST");
        for (int i = 0; i < crusts.size(); i++) {
            System.out.println((i + 1) + ". " + crusts.get(i).getDescription());
        }
        System.out.print("Select crust: ");
        Crust selectedCrust = crusts.get(scanner.nextInt() - 1);
        scanner.nextLine();

        // Select sauce for the custom pizza
        System.out.println("\nAvailable Sauces:");
        List<Sauce> sauces = getCustomizationsOfType("SAUCE");
        for (int i = 0; i < sauces.size(); i++) {
            System.out.println((i + 1) + ". " + sauces.get(i).getDescription());
        }
        System.out.print("Select sauce: ");
        Sauce selectedSauce = sauces.get(scanner.nextInt() - 1);
        scanner.nextLine();
        // Create a new custom pizza with the selected crust and sauce
        Pizza customPizza = new Pizza(name, selectedCrust, selectedSauce, 1000.0); // Base price for custom pizza

        // Add toppings to the custom pizza
        while (true) {
            System.out.println("\nAvailable Toppings:");
            List<Topping> toppings = getCustomizationsOfType("TOPPING");
            for (int i = 0; i < toppings.size(); i++) {
                System.out.println((i + 1) + ". " + toppings.get(i).getDescription());
            }
            System.out.println("0. Finish adding toppings");
            System.out.print("Select topping (0 to finish): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice == 0) break;
            if (choice > 0 && choice <= toppings.size()) {
                customPizza.addTopping(toppings.get(choice - 1));
                System.out.println("Topping added!");
            }
        }
        // Prompt user to save the custom pizza for future orders
        System.out.print("Would you like to save this pizza for future orders? (Y/N): ");
        if (scanner.nextLine().toUpperCase().equals("Y")) {
            customer.saveCustomPizza(customPizza);
            System.out.println("Pizza saved to your favorites!");
        }
        // Add the custom pizza to the order
        order.addPizza(customPizza);
        System.out.println("Custom pizza added to order!");
    }

    private void trackOrder() {
        System.out.print("Enter order ID: "); // Prompt user to enter order ID
        String orderId = scanner.nextLine();
        Order order = findOrder(orderId);
        
        if (order != null) { // Check if the order exists and display tracking
            order.getTracker().displayTracking();
        } else {
            System.out.println("Order not found.");
        }
    }

    private void manageFeedback() { // Display feedback management menu
        System.out.println("\nFeedback Management");
        System.out.println("1. Leave Feedback");
        System.out.println("2. View Feedback");
        System.out.println("3. Back");
        System.out.print("Choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                leaveFeedback(); // Leave feedback for an order
                break;
            case 2:
                viewFeedback(); // View all feedback
                break;
            case 3:
                return; // Return to previous menu
            default:
                System.out.println("Invalid choice."); // Handle invalid input
        }
    }

    private void leaveFeedback() {
        System.out.print("Enter order ID: "); // Prompt user for order ID
        String orderId = scanner.nextLine();
        Order order = findOrder(orderId);
        
        if (order == null) { // Check if the order exists
            System.out.println("Order not found.");
            return;
        }

        System.out.print("Enter rating (1-5): "); // Collect feedback rating and comment from user
        int rating = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter comment: ");
        String comment = scanner.nextLine();
        // Add feedback to the order
        order.addFeedback(rating, comment);
        System.out.println("Thank you for your feedback!");
        
        // Return to feedback management menu
        manageFeedback();
    }

    private void viewFeedback() { // Display feedback table
        System.out.println("\n=================================================================");
        System.out.printf("%-10s | %-7s | %-20s | %-20s%n", "Order ID", "Rating", "Comment", "Submitted");
        System.out.println("=================================================================");
        
        boolean found = false; // Iterate through orders to display feedback
        for (Order order : orders) {
            Feedback feedback = order.getFeedback();
            if (feedback != null) {
                feedback.displayInTable();
                found = true;
            }
        }
        // Check if any feedback was found
        if (!found) {
            System.out.println("No feedback found.");
        }
        System.out.println("=================================================================");
    }

    private void viewPromotions() { // Display current promotions
        System.out.println("\nCurrent Promotions:");
        for (Promotion promo : promotions) {
            if (promo.isValid()) {
                System.out.println("Code: " + promo.getCode());
                System.out.println("Description: " + promo.getDescription());
                System.out.println("Discount: " + promo.getDiscountPercentage() + "%");
                System.out.println("Valid until: " + promo.getEndDate());
                System.out.println("--------------------");
            }
        }
    }

    private void viewAllOrders() { // Display orders table
        System.out.println("\n============================================================================================");
        System.out.printf("%-10s | %-10s | %-20s | %-12s | %-12s | %-15s%n", 
            "Order ID", "Cust ID", "Order Items", "Amount", "Delivery", "Status");
        System.out.println("============================================================================================");
        
        boolean found = false; // Iterate through orders to display each order's details
        for (Order order : orders) {
            // Create order items string
            StringBuilder orderItems = new StringBuilder();
            for (Pizza pizza : order.getPizzas()) {
                orderItems.append(pizza.getName()).append(", ");
            }
            String items = orderItems.length() > 2 ? 
                orderItems.substring(0, orderItems.length() - 2) : ""; // Remove last comma
            
            // Truncate if too long
            if (items.length() > 20) {
                items = items.substring(0, 17) + "...";
            }
            // Display order details
            System.out.printf("%-10s | %-10s | %-20s | LKR %-8.2f | %-12s | %-15s%n",
                order.getOrderId(),
                order.getCustomer().getCustID(),
                items,
                order.getTotalAmount(),
                order.getDeliveryType(),
                order.getStatus());
            
            found = true;
        }
        // Check if any orders were found
        if (!found) {
            System.out.println("No orders found.");
        }
        System.out.println("============================================================================================");
    }

    private void viewOrderDetails() {
        System.out.print("Enter order ID: "); // Prompt user to enter order ID
        String orderId = scanner.nextLine();
        Order order = findOrder(orderId);
        
        if (order == null) { // Check if the order exists
            System.out.println("Order not found.");
            return;
        }

        // Display full order details
        order.displayOrderSummary();
        
        // Display tracking information
        order.getTracker().displayTracking();
        
        // Display feedback if exists
        Feedback feedback = order.getFeedback();
        if (feedback != null) {
            System.out.println("\n--------------- FEEDBACK ---------------");
            feedback.display();
        }
    }

    private Customer findCustomer(String id) { // Find and return customer by ID from the list
        return customers.stream()
            .filter(c -> c.getCustID().equals(id))
            .findFirst()
            .orElse(null);
    }

    private Order findOrder(String orderId) { // Find and return order by order ID from the list
        return orders.stream()
            .filter(o -> o.getOrderId().equals(orderId))
            .findFirst()
            .orElse(null);
    }

    private Promotion findPromotion(String code) { // Find and return promotion by code from the list
        return promotions.stream()
            .filter(p -> p.getCode().equals(code))
            .findFirst()
            .orElse(null);
    }

    @SuppressWarnings("unchecked")
    private <T extends Customization> List<T> getCustomizationsOfType(String category) {
    // Get all customizations of a specific category
        List<T> result = new ArrayList<>();
        for (Customization c : customizations) {
            if (c.getCategory().equals(category)) {
                result.add((T) c);
            }
        }
        return result;
    }

    private void updateOrderStatus() {
        System.out.print("Enter order ID: "); // Prompt user to enter order ID
        String orderId = scanner.nextLine();
        Order order = findOrder(orderId);
        // Check if the order exists
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        // Display current order status and status update options
        System.out.println("\nCurrent Status: " + order.getStatus());
        System.out.println("\nSelect new status:");
        System.out.println("1. PREPARING");
        System.out.println("2. IN_OVEN");
        System.out.println("3. READY_FOR_DELIVERY");
        System.out.println("4. OUT_FOR_DELIVERY");
        System.out.println("5. DELIVERED");
        System.out.println("6. CANCELLED");
        System.out.print("Choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        // Update order status based on user choice
        String newStatus;
        switch (choice) {
            case 1: newStatus = "PREPARING"; break;
            case 2: newStatus = "IN_OVEN"; break;
            case 3: newStatus = "READY_FOR_DELIVERY"; break;
            case 4: newStatus = "OUT_FOR_DELIVERY"; break;
            case 5: newStatus = "DELIVERED"; break;
            case 6: newStatus = "CANCELLED"; break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        order.getTracker().updateStatus(newStatus);
        System.out.println("Order status updated successfully!");
    }

    public static void main(String[] args) { // Create an instance of the system and run it
        PizzaOr_sys system = new PizzaOr_sys();
        system.run();
    }
} 