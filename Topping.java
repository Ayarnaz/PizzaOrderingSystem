/* This class represents pizza topping customization options.
   Extends the Customization base class for consistent pricing and availability management.
   Provides specific implementation for topping description and pricing.
   Supports the system's topping management and pizza customization features. */

class Topping extends Customization {
    public Topping(String name, double price) {
        super(name, price, "TOPPING"); // Sets category as "TOPPING
    }

    /* Provides a formatted description of the topping
    * Including name and additional cost in LKR */
    @Override
    public String getDescription() {
        return "Topping: " + getName() + " (+LKR " + String.format("%.2f", getPrice()) + ")";
    }
}