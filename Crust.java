/* This class represents the pizza crust customization option.
   Extends the Customization base class for consistent pricing and availability management.
   Manages crust-specific attributes like thickness (thin, regular, thick).
   Provides formatted description output for order display and receipt generation. */
class Crust extends Customization {
    private String thickness; // thin, regular, thick
    /*Constructor creates a new crust option with specified properties
    * Automatically sets category to "CRUST"*/
    public Crust(String name, double price, String thickness) {
        super(name, price, "CRUST");
        this.thickness = thickness;
    }

    public String getThickness() { return thickness; }

    /*Provides a formatted description of the crust option
    * Including name, thickness, and additional cost in LKR*/
    @Override
    public String getDescription() {
        return "Crust: " + getName() + " (" + thickness + ") (+LKR " + String.format("%.2f", getPrice()) + ")";
    }
} 