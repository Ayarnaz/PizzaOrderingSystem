/* This module implements the Strategy Design Pattern for payment processing.
   Provides different payment methods (Credit Card, PayPal, Cash) with consistent interface.
   Allows flexible addition of new payment methods without modifying existing code.
   Handles payment processing and confirmation for different payment types. */

interface PaymentStrategy {
    void pay(double amount);
} 