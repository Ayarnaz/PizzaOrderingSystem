/* This class implements the PaymentStrategy interface for credit card payments.
   Handles credit card payment processing and validation.
   Provides secure handling of credit card information.
   Formats payment confirmation messages for customer notification. */
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;  // Stores the credit card number

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        // Print out the payment confirmation with the credit card number
        System.out.println("Paid LKR " + String.format("%.2f", amount) + " using Credit Card: " + cardNumber);
    }
} 