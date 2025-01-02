/* This class implements the PaymentStrategy interface for cash payments.
   Supports both cash on delivery and cash on pickup payment options.
   Manages payment type tracking and confirmation.
   Provides appropriate payment instructions based on delivery method. */
class CashPayment implements PaymentStrategy {
    private String paymentType; // "ON_DELIVERY" or "ON_PICKUP"
    // Constructor to set the payment type
    public CashPayment(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public void pay(double amount) {
        // Print out the payment instruction based on the payment type
        System.out.println("Cash payment of LKR " + String.format("%.2f", amount) + 
            " to be collected " + (paymentType.equals("ON_DELIVERY") ? "upon delivery" : "at pickup"));
    }
}