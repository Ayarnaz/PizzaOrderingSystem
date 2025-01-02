import java.util.ArrayList;
import java.util.List;

/* This class represents a Customer entity in the system. 
   Manages customer information, order history, and loyalty points program.
   Implements the Observer pattern to receive order status updates.
   Supports custom pizza saving functionality for repeat orders.
   Tracks customer's loyalty points and provides methods for points redemption. */
class Customer implements OrderObserver {
    private String customerId;
    private String name;
    private String address;
    private String phone;
    private String email;
    private int loyaltyPoints;
    private List<Order> orderHistory;
    private List<Pizza> savedCustomPizzas;

    enum LoyaltyTier {
        BRONZE(0, "Bronze", 0),
        SILVER(100, "Silver", 5),
        GOLD(500, "Gold", 10),
        PLATINUM(1000, "Platinum", 15);

        private final int minPoints;
        private final String name;
        private final int discountPercentage;

        LoyaltyTier(int minPoints, String name, int discountPercentage) {
            this.minPoints = minPoints;
            this.name = name;
            this.discountPercentage = discountPercentage;
        }

        public int getMinPoints() { return minPoints; }
        public String getName() { return name; }
        public int getDiscountPercentage() { return discountPercentage; }

        public static LoyaltyTier getTierFromPoints(int points) {
            LoyaltyTier currentTier = BRONZE;
            for (LoyaltyTier tier : values()) {
                if (points >= tier.getMinPoints()) {
                    currentTier = tier;
                }
            }
            return currentTier;
        }
    }

    public Customer(String id, String name, String address, String phone, String email) {
        this.customerId = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.loyaltyPoints = 0;  // Initialize loyalty points to zero for new customers
        this.orderHistory = new ArrayList<>();  // Initialize empty order history
        this.savedCustomPizzas = new ArrayList<>();  // Initialize empty saved pizzas list
    }

    public void addLoyaltyPoints(int points) { // this function adds loyalty points to customer account
        this.loyaltyPoints += points;
    }

    public void addOrder(Order order) { // this function adds an order to customer's history
        orderHistory.add(order);
        // Add loyalty points based on order total (1 point per 100 currency units)
        addLoyaltyPoints((int)(order.getTotalAmount() / 100));
    }

    public boolean canRedeemPoints(int points) { // this function checks if customer has enough points
        return loyaltyPoints >= points;
    }
    // Deducts points from customer's account if sufficient balance exists
    public void redeemPoints(int points) {
        if (canRedeemPoints(points)) {
            loyaltyPoints -= points;
        }
    }
    // Adds a custom pizzas to customer's saved pizzas list
    public void saveCustomPizza(Pizza pizza) {
        savedCustomPizzas.add(pizza);
    }
    // Returns list of customer's saved pizza
    public List<Pizza> getSavedPizzas() {
        return savedCustomPizzas;
    }

    // Getters and Setters
    public String getCustID() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(int points) { this.loyaltyPoints = points; }
    public List<Order> getOrderHistory() { return orderHistory; }

    // Displays all customer information in a formatted output
    public void showDetails() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Loyalty Points: " + loyaltyPoints);
    }

    // Implementation of OrderObserver interface method
    // Receives and displays order status updates
    @Override
    public void update(String status, String orderId) {
        System.out.println("Notification for Order " + orderId + ": " + status);
    }
} 