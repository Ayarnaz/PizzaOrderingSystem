/* This abstract class serves as the base for all pizza customization options.
   Provides common attributes and methods for crusts, sauces, and toppings.
   Implements price management and availability tracking for customization options.
   Uses the Template Method pattern for consistent customization handling across different types. */
abstract class Customization {
    protected String name;
    protected double price;
    protected boolean isAvailable;
    protected String category;

    public Customization(String name, double price, String category) {
        this.name = name; // Name of the customization option
        this.price = price;  // Price of this customization
        this.isAvailable = true; // Flag indicating if this option is currently available
        this.category = category; // Category of the customization (e.g: "topping", "crust", "sauce")
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; } // New customizations are available by default
    public String getCategory() { return category; }
    // Setters for mutable properties (availability and price)
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setPrice(double price) { this.price = price; }

    // Abstract method to be implemented by subclasses
    public abstract String getDescription();
} 