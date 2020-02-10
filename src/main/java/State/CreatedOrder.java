package State;

public class CreatedOrder implements OrderState {

    public CreatedOrder() {
    }

    @Override
    public String getStateName() {
        return "CreatedOrder";
    }

    @Override
    public OrderState pay() {
        System.out.println("Order needs to be submitted first");
        return new CreatedOrder();
    }

    @Override
    public OrderState submit() {
        System.out.println("Order submitted");
        return new ReservedOrder();
    }

    @Override
    public OrderState cancel() {
        System.out.println("Cancelled Order");
        return new CancelledOrder();
    }

    @Override
    public OrderState edit() {
        System.out.println("Order needs to be submitted before changes can be made.");
        return new CreatedOrder();
    }
}
