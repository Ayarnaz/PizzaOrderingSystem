import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/* This class manages customer feedback for orders. 
   Handles storage and display of ratings, comments, and feedback timestamps.
   Provides both detailed and tabular display formats for feedback information.
   Supports the system's quality monitoring and customer satisfaction tracking. */
class Feedback {
    private String orderId;
    private int rating; // Rating out of 5 stars
    private String comment; // Customer's written feedback
    private LocalDateTime feedbackTime; // When feedback was submitted

    //Creates new feedback entry with current timestamp
    public Feedback(String orderId, int rating, String comment) {
        this.orderId = orderId;
        this.rating = rating;
        this.comment = comment;
        this.feedbackTime = LocalDateTime.now();
    }
    //Displays feedback in detailed format with full comment
    public void display() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Rating: " + "*".repeat(rating) + " (" + rating + "/5)");
        System.out.println("Comment: " + comment);
        System.out.println("Submitted: " + feedbackTime);
    }
    /*Displays feedback in compact tabular format, Truncates long comments and formats timestamp*/
    public void displayInTable() {
        String stars = "*".repeat(rating);
        String formattedTime = feedbackTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.printf("%-10s | %-7s | %-20s | %-20s%n", 
            orderId, 
            stars, 
            comment.length() > 20 ? comment.substring(0, 17) + "..." : comment,
            formattedTime);
    }

    // Getters
    public String getOrderId() { return orderId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getFeedbackTime() { return feedbackTime; }
} 