import java.time.LocalDate;

/* This class manages promotional discounts in the system.
   Handles discount calculations, validity periods, and promotion status.
   Provides methods for applying discounts to order totals.
   Supports time-based promotion validation and discount percentage management. */
class Promotion {
    private String code;
    private String description;
    private double discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;
    // Creates new promotion with specified parameters
    public Promotion(String code, String description, double discountPercentage, 
                    LocalDate startDate, LocalDate endDate) {
        this.code = code;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = true;
    }
    // Applies discount if promotion is valid
    public double applyDiscount(double amount) {
        if (isValid()) {
            return amount * (1 - discountPercentage / 100);
        }
        return amount;
    }
    // Checks if promotion is currently valid based on dates and active status
    public boolean isValid() {
        LocalDate now = LocalDate.now();
        return isActive && !now.isBefore(startDate) && !now.isAfter(endDate);
    }

    // Getters and setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(double discountPercentage) { 
        this.discountPercentage = discountPercentage; 
    }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
} 