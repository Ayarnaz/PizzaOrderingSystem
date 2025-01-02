import java.time.LocalDateTime;
import java.util.*;

/* This class manages the order processing and tracking in the system.
   It handles order creation, payment processing, status tracking, and delivery management.
   Implements the State pattern for order status management and Observer pattern for status notifications.
   Includes functionality for promotion application, feedback collection, and loyalty points calculation.
   Maintains detailed order information including pizzas, pricing, delivery details, and payment status. */
class Order {
    private static int orderCounter = 1000; // Static counter for generating unique order IDs
    // Basic order information
    private String orderId;
    private Customer customer;
    private List<Pizza> pizzas;
    private LocalDateTime orderTime;
    private String status;
    private String deliveryType;
    private double totalAmount;
    // Order processing related fields
    private PaymentStrategy paymentStrategy;
    private OrderTracker tracker;
    private Feedback feedback;
    private Promotion appliedPromotion;
    private boolean isPaid;
    private OrderState state;
    private double deliveryCharge;

    /*Constructor initializes a new order with basic details
    Generates unique order ID and sets initial state*/
    public Order(String orderId, Customer customer, String deliveryType, double deliveryCharge) {
        this.orderId = "ORD" + (++orderCounter);
        this.customer = customer;
        this.pizzas = new ArrayList<>();
        this.orderTime = LocalDateTime.now();
        this.status = "PENDING_PAYMENT";
        this.deliveryType = deliveryType;
        this.deliveryCharge = deliveryCharge;
        this.tracker = new OrderTracker(this, false);
        this.isPaid = false;
        this.state = new PlacedState();
    }

    /*Processes payment for the order using specified payment strategy
    * Updates order status and awards loyalty points upon successful payment*/
    public void processPayment(PaymentStrategy paymentStrategy) {
        if (!isPaid) {
            this.paymentStrategy = paymentStrategy;
            paymentStrategy.pay(totalAmount);
            this.isPaid = true;
            this.status = "PREPARING";
            tracker.updateStatus("Payment received - Order is being prepared");
            
            // Calculate loyalty points based on total amount
            int pointsEarned = (int)(totalAmount / 100);
            customer.addLoyaltyPoints(pointsEarned);
            
            // Display updated order summary with points earned
            System.out.println("\nPayment successful! Here's your updated order summary:");
            displayOrderSummary();
        }
    }
    // Adds a pizza to the order and recalculates total
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
        calculateTotal();
    }
    // Calculates total order amount including pizzas, delivery charge, and promotions
    private void calculateTotal() {
        totalAmount = 0;
        for (Pizza p : pizzas) {
            totalAmount += p.calculateTotal();
        }
        totalAmount += deliveryCharge;
        if (appliedPromotion != null) {
            totalAmount = appliedPromotion.applyDiscount(totalAmount);
        }
    }
    // Displays detailed order summary including pizzas, charges, and loyalty points
    public void displayOrderSummary() {
        System.out.println("\n================ ORDER SUMMARY ================");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Delivery Type: " + deliveryType);
        if (deliveryType.equals("DELIVERY")) {
            System.out.println("Delivery Charge: LKR " + String.format("%.2f", deliveryCharge));
        }
        
        System.out.println("\n-------------- PIZZAS ORDERED --------------");
        double subtotal = 0;
        for (Pizza pizza : pizzas) {
            subtotal += pizza.calculateTotal();
            pizza.displayPizzaDetails();
        }

        System.out.println("\n----------------- CHARGES -----------------");
        System.out.println("Subtotal: LKR " + String.format("%.2f", subtotal));
        
        if (deliveryType.equals("DELIVERY")) {
            System.out.println("Delivery Fee: LKR " + String.format("%.2f", deliveryCharge));
        }
        
        if (appliedPromotion != null) {
            double discountAmount = subtotal * (appliedPromotion.getDiscountPercentage() / 100.0);
            System.out.println("\nPromotion Applied: " + appliedPromotion.getDescription());
            System.out.println("Discount Amount: -LKR " + String.format("%.2f", discountAmount));
        }
        
        System.out.println("\nTotal Amount: LKR " + String.format("%.2f", totalAmount));
        System.out.println("Status: " + status);
        
        if (isPaid) {
            System.out.println("\n-------------- LOYALTY POINTS --------------");
            int pointsEarned = (int)(totalAmount / 100);
            System.out.println("Points Earned: " + pointsEarned);
            System.out.println("Total Points: " + customer.getLoyaltyPoints());
        }
        
        System.out.println("============================================");
    }
    // Applies a promotion to the order if valid
    public void applyPromotion(Promotion promotion) {
        if (promotion.isValid()) {
            this.appliedPromotion = promotion;
            calculateTotal();
        }
    }
    // Adds customer feedback to the order
    public void addFeedback(int rating, String comment) {
        this.feedback = new Feedback(orderId, rating, comment);
    }

    public OrderTracker getTracker() {
        return tracker;
    }

     // Standard getters and setters
    public String getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public List<Pizza> getPizzas() { return pizzas; }
    public LocalDateTime getOrderTime() { return orderTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDeliveryType() { return deliveryType; }
    public double getTotalAmount() { return totalAmount; }
    public PaymentStrategy getPaymentStrategy() { return paymentStrategy; }
    public void setPaymentStrategy(PaymentStrategy strategy) { this.paymentStrategy = strategy; }
    public Feedback getFeedback() { return feedback; }
    public Promotion getAppliedPromotion() { return appliedPromotion; }
    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid) { isPaid = paid; }

    // State management methods
    public void setState(OrderState state) {
        this.state = state;
        tracker.updateStatus(state.getStatus());
    }

    public void nextState() {
        state.next(this);
    }

    public void previousState() {
        state.prev(this);
    }
    // Updates delivery type and recalculates total
    public void setDeliveryType(String type, double charge) {
        this.deliveryType = type;
        this.deliveryCharge = charge;
        calculateTotal();  // Recalculate total with new delivery charge
    }


} 

