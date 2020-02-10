package State;

public interface OrderState {
    //could use inter-state creation
    String getStateName();

    OrderState pay();

    OrderState submit();

    OrderState cancel();

    OrderState edit();


}
