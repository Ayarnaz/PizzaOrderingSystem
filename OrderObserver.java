/* This interface implements the Observer Design Pattern for order tracking.
   Defines the contract for objects that need to receive order status updates.
   Supports real-time order status notification system.
   Enables loose coupling between order processing and status notification components. */

interface OrderObserver {
    void update(String status, String orderId);
} 