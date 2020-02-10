package State;

public class ProvisionalOrder implements OrderState {

    public ProvisionalOrder() {
    }

    @Override
    public String getStateName() {
        return "ProvisionalOrder";
    }

    @Override
    public OrderState pay() {
        System.out.println("paid for Order");
        return new ProcessedOrder();
    }

    @Override
    public OrderState submit() {
        System.out.println("Order has already been submitted...");
        return new ProvisionalOrder();
    }

    @Override
    public OrderState cancel() {
        System.out.println("Cancelled Order");
        return new CancelledOrder();
    }

    @Override
    public OrderState edit() {
        System.out.println("Order has been edited.");
        return new ProvisionalOrder();
    }
}
