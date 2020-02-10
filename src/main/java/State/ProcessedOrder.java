package State;

public class ProcessedOrder implements OrderState {

    public ProcessedOrder() {
    }

    @Override
    public String getStateName() {
        return "ProcessedOrder";
    }

    @Override
    public OrderState pay() {
        System.out.println("Order has already been processed...");
        return new ProcessedOrder();
    }

    @Override
    public OrderState submit() {
        System.out.println("Order has already been processed...");
        return new ProcessedOrder();
    }

    @Override
    public OrderState cancel() {
        System.out.println("Processed orders need to be cancelled at the service center inside the cinema.");
        return new ProcessedOrder();
    }

    @Override
    public OrderState edit() {
        System.out.println("Processed orders cannot be edited.");
        return new ProcessedOrder();
    }
}
