import java.util.ArrayList;
import java.util.List;

/* This class implements the Builder Design Pattern for pizza creation.
   Provides a fluent interface for step-by-step pizza construction.
   Handles the complex process of assembling different pizza components.
   Supports both predefined and custom pizza creation with consistent building process.
   Ensures proper initialization of all pizza attributes before object creation. */
class PizzaBuilder {
    private String name;
    private Crust crust;
    private Sauce sauce;
    private double basePrice;
    private List<Topping> toppings = new ArrayList<>();
    private boolean isCustom;
    // Constructor initializes a new pizza builder with a name and default base price
    public PizzaBuilder(String name) {
        this.name = name;
        this.basePrice = 1000.0; // Default base price
    }
    // Sets the crust type and returns builder for method chaining
    public PizzaBuilder setCrust(Crust crust) {
        this.crust = crust;
        return this;
    }
    // Sets the sauce type and returns builder for method chaining
    public PizzaBuilder setSauce(Sauce sauce) {
        this.sauce = sauce;
        return this;
    }
    // Adds a topping to the pizza and returns builder for method chaining
    public PizzaBuilder addTopping(Topping topping) {
        this.toppings.add(topping);
        return this;
    }
    //Sets whether this is a custom pizza and returns builder for method chaining
    public PizzaBuilder setCustom(boolean isCustom) {
        this.isCustom = isCustom;
        return this;
    }

     /* Constructs and returns the final Pizza object with all selected components
     * Creates a new Pizza instance with basic properties and adds all toppings*/
    public Pizza build() {
        Pizza pizza = new Pizza(name, crust, sauce, basePrice);
        pizza.setCustom(isCustom);
        for (Topping topping : toppings) {
            pizza.addTopping(topping);
        }
        return pizza;
    }
} 