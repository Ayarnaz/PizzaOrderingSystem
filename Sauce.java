/* This class represents the pizza sauce customization option.
   Extends the Customization base class for consistent pricing and availability management.
   Handles sauce-specific attributes including spiciness levels.
   Supports dynamic pricing and description formatting for different sauce options. */
class Sauce extends Customization {
    private String spiciness; // mild, medium, hot
    //Basic constructor for creating a sauce without specified spiciness
    public Sauce(String name, double price) {
        super(name, price, "SAUCE");
    }
    //Extended constructor that includes spiciness level
    public Sauce(String name, double price, String spiciness) {
        super(name, price, "SAUCE");
        this.spiciness = spiciness;
    }
    /* Provides a formatted description of the sauce option
    * Including name and additional cost in LKR */
    @Override
    public String getDescription() {
        return "Sauce: " + getName() + " (+LKR " + String.format("%.2f", getPrice()) + ")";
    }

    public String getSpiciness() { return spiciness; }
    public void setSpiciness(String spiciness) { this.spiciness = spiciness; }
}