import java.util.*;

/* This class represents a Pizza entity in the system.
   It manages the composition of pizzas including crust, sauce, toppings, and pricing.
   Supports both predefined pizzas and custom pizza creation with dynamic pricing calculation.
   Includes methods for pizza customization, price calculation, and detailed display of pizza information.
   Implements cloning functionality to support pizza customization from templates. */
class Pizza {
    private String name;
    private Crust crust;
    private Sauce sauce;
    private double basePrice;
    private List<Topping> toppings;
    private boolean isCustom;

    public Pizza(String name, Crust crust, Sauce sauce, double basePrice) { // constructor for creating a new pizza
        this.name = name;
        this.crust = crust;
        this.sauce = sauce;
        this.basePrice = basePrice;
        this.toppings = new ArrayList<>();
        this.isCustom = false;
    }

    public Pizza(Pizza other) { // copy constructor for cloning pizzas for saved custom pizzas
        this.name = other.name;
        this.crust = other.crust;
        this.sauce = other.sauce;
        this.basePrice = other.basePrice;
        this.toppings = new ArrayList<>(other.toppings);
        this.isCustom = other.isCustom;
    }

    public void addTopping(Topping topping) { // this function adds a topping to the pizza
        if (topping.isAvailable()) {
            toppings.add(topping);
            isCustom = true; // mark as custom pizza when toppings are added
        }
    }

    public void setCrust(Crust crust) { // this function changes the pizza's crust type
        if (crust.isAvailable()) {
            this.crust = crust;
            isCustom = true; // mark as custom when crust is changed
        }
    }

    public void setSauce(Sauce sauce) { // this function changes the pizza's sauce type
        if (sauce.isAvailable()) {
            this.sauce = sauce;
            isCustom = true; // mark as custom when sauce is changed
        }
    }

    public double calculateTotal() { // this function calculates the total cost of the pizza
        double total = basePrice;
        total += crust.getPrice(); // add crust price
        total += sauce.getPrice(); // add sauce price
        for (Topping t : toppings) {
            total += t.getPrice(); // add each topping's price
        }
        return total;
    }

    public void displayPizzaDetails() { // this function displays the pizza's complete information
        if (isCustom) {
            // Only show "Custom" for actual custom pizzas
            System.out.println("\n=== Custom Pizza: " + name + " ===");
            System.out.println(crust.getDescription());
            System.out.println(sauce.getDescription());
            System.out.println("Toppings:");
            for (Topping t : toppings) {
                System.out.println("- " + t.getDescription());
            }
            System.out.println("Price: LKR " + String.format("%.2f", calculateTotal()));
            System.out.println("============================");
        } else {
            // Simplified display for predefined pizzas
            System.out.println("\n=== " + name + " ===");
            System.out.println("Price: LKR " + String.format("%.2f", calculateTotal()));
            System.out.println("==================");
        }
    }

    // Getters and Setters
    public String getName() { return name; }
    public List<Topping> getToppings() { return toppings; }
    public Crust getCrust() { return crust; }
    public Sauce getSauce() { return sauce; }
    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }
    public boolean isCustom() { return isCustom; }
    public void setName(String name) { this.name = name; }
    public void setCustom(boolean custom) { isCustom = custom; }
} 