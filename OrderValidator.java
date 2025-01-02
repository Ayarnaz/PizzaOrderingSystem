/* This module implements the Chain of Responsibility Design Pattern for order validation.
   Provides a chain of validators to ensure order completeness and correctness.
   Includes specific validators for customer information, payment status, and other order requirements.
   Supports extensible validation chain for adding new validation rules. */

abstract class OrderValidator {
    protected OrderValidator nextValidator; // The next validator in the chain
    // Set the next validator in the chain
    public void setNext(OrderValidator validator) {
        this.nextValidator = validator;
    }
    // Abstract method to validate the order, to be implemented by concrete validators
    public abstract boolean validate(Order order);
}

class CustomerValidator extends OrderValidator {
    @Override
    public boolean validate(Order order) {
        // Check if customer information is present
        if (order.getCustomer() == null) {
            System.out.println("Error: Customer information missing");
            return false;
        }
        return nextValidator != null ? nextValidator.validate(order) : true;
    }
}

class PaymentValidator extends OrderValidator {
    @Override
    public boolean validate(Order order) {
        // Check if the order is paid
        if (!order.isPaid()) {
            System.out.println("Error: Payment not completed");
            return false;
        }
        // Pass the validation to the next validator in the chain if it exists
        return nextValidator != null ? nextValidator.validate(order) : true;
    }
} 