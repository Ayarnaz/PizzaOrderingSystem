/* This module implements the State Design Pattern for order status management.
   Defines different states an order can be in (Placed, Preparing, Out for Delivery, etc.).
   Manages state transitions and ensures proper order flow through the system.
   Provides clear separation of state-specific behaviors and transition logic. */

interface OrderState {
    // Move to the next state
    void next(Order order);
    // Move to the previous state
    void prev(Order order);
    // Get the current status of the order
    String getStatus();
}

class PlacedState implements OrderState {
    // Transition to the Preparing state
    public void next(Order order) {
        order.setState(new PreparingState());
    }
    public void prev(Order order) { /* Cannot go back */ }
    public String getStatus() { return "PLACED"; }
}

class PreparingState implements OrderState {
    // Transition to the Out for Delivery state
    public void next(Order order) {
        order.setState(new OutForDeliveryState());
    }
    public void prev(Order order) {
        order.setState(new PlacedState());
    }
    public String getStatus() { return "PREPARING"; }
}

class OutForDeliveryState implements OrderState {
    // Transition to the Out for Delivering state
    public void next(Order order) {
        order.setState(new DeliveredState());
    }
    public void prev(Order order) {
        order.setState(new PreparingState());
    }
    public String getStatus() { return "OUT_FOR_DELIVERY"; }
}

class DeliveredState implements OrderState {
    // Delivered state is the final state, can't move forward
    public void next(Order order) { /* Final state */ }
    public void prev(Order order) {
        order.setState(new OutForDeliveryState());
    }
    public String getStatus() { return "DELIVERED"; }
}

class CancelledState implements OrderState {
    // Cancelled state is terminal, can't move forward
    public void next(Order order) { /* Cannot proceed from cancelled */ }
    public void prev(Order order) { /* Cannot go back from cancelled */ }
    public String getStatus() { return "CANCELLED"; }
}