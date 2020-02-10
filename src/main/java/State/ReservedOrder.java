package State;

public class ReservedOrder implements OrderState {

    public ReservedOrder() {
    }

    @Override
    public String getStateName() {
        return "ReservedOrder";
    }

    @Override
    public OrderState pay() {
        System.out.println("paid for Order");
        return new ProcessedOrder();
    }

    @Override
    public OrderState submit() {
        System.out.println("Order has already been submitted");
        return new ReservedOrder();
    }

    @Override
    public OrderState cancel() {
        System.out.println("Cancelled Order");
        return new CancelledOrder();
    }

    @Override
    public OrderState edit() {
        System.out.println("Order has been edited.");
        return new ReservedOrder();
    }
}
