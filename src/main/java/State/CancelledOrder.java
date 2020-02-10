package State;

public class CancelledOrder implements OrderState {

    public CancelledOrder() {
    }

    @Override
    public String getStateName() {
        return "CancelledOrder";
    }

    @Override
    public OrderState pay() {
        System.out.println("This order has been cancelled");
        return new CancelledOrder();
    }

    @Override
    public OrderState submit() {
        System.out.println("This order has been cancelled");
        return new CancelledOrder();
    }

    @Override
    public OrderState cancel() {
        System.out.println("This order has already been cancelled");
        return new CancelledOrder();
    }

    @Override
    public OrderState edit() {
        System.out.println("This order has been cancelled");
        return new CancelledOrder();
    }
}
