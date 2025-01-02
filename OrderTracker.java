import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/* This class manages the tracking and status updates for orders.
   Implements the Observer pattern to notify customers of order status changes.
   Maintains a chronological log of status updates with timestamps.
   Handles estimated delivery time calculations and status display formatting. */
class OrderTracker {
    private Order order; // The order being tracked
    private List<String> statusUpdates; // List to store status updates with timestamps
    private List<OrderObserver> observers; // List of observers to notify of status changes
    private LocalDateTime estimatedDeliveryTime; // The estimated delivery time of the order

    public OrderTracker(Order order, boolean notifyImmediately) {
        this.order = order;
        this.statusUpdates = new ArrayList<>();
        this.observers = new ArrayList<>();
        addObserver(order.getCustomer()); // Add the customer as an observe
        if (notifyImmediately) {
            updateStatus("Order placed"); // Notify observers immediately if specified

        }
    }
    // Method to add an observer to the observer list
    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }
    // Method to update the order status and notify observers
    public void updateStatus(String status) {
        // Create a timestamp for the status update
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        // Add the status update to the list with the timestamp
        statusUpdates.add(timestamp + " - " + status);
        order.setStatus(status); // Update the order status

        // Notify all observers of the status update
        for (OrderObserver observer : observers) {
            observer.update(status, order.getOrderId());
        }
    }
    // Method to set the estimated delivery time
    public void setEstimatedDeliveryTime(LocalDateTime time) {
        this.estimatedDeliveryTime = time;
    }
    // Method to display the tracking information
    public void displayTracking() {
        System.out.println("\nOrder Tracking - Order ID: " + order.getOrderId());
        System.out.println("Current Status: " + order.getStatus());
        // Display the estimated delivery time if available
        if (estimatedDeliveryTime != null) {
            System.out.println("Estimated Delivery: " + 
                estimatedDeliveryTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        // Display all status updates with timestamps
        System.out.println("\nStatus Updates:");
        for (String update : statusUpdates) {
            System.out.println(update);
        }
    }
} 