import java.util.List;

/*Defines the command interface for order operations
Part of the Command pattern implementation for order management */

interface OrderCommand {
    void execute(); // Executes the command operation
    void undo(); // Reverses the command operation
}
/*Concrete implementation of OrderCommand for placing new orders
Handles adding and removing orders from the order list*/
class PlaceOrderCommand implements OrderCommand {
    private Order order; // The order to be placed
    private List<Order> orderList; // Reference to the main list of orders in the system
    // Executes the place order command by adding order to list
    public PlaceOrderCommand(Order order, List<Order> orderList) {
        this.order = order;
        this.orderList = orderList;
    }
    // Executes the place order command by adding order to list
    @Override
    public void execute() {
        orderList.add(order);
    }
    // Undoes the place order command by removing order from list
    @Override
    public void undo() {
        orderList.remove(order);
    }
} 