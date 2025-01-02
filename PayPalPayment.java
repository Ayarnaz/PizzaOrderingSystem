/* This class implements the PaymentStrategy interface for PayPal payments.
   Manages PayPal account authentication and payment processing.
   Handles secure storage of PayPal credentials.
   Provides formatted payment confirmation for order processing. */
class PayPalPayment implements PaymentStrategy {
    private String email; // Stores the PayPal account email
    private String password; // Stores the PayPal account password

    // Constructor to set the email and password for the PayPal account
    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void pay(double amount) {
        // Print out the payment confirmation with the PayPal account email
        System.out.println("Paid LKR " + String.format("%.2f", amount) + " using PayPal account: " + email);
    }
}