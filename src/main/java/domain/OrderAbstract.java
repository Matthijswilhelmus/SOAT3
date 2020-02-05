package domain;

public abstract class OrderAbstract {
    PriceStrategy priceStrategy;

    public OrderAbstract() {

    }

    public double calculatePrice() {
        return priceStrategy.CalculatePrice();
    }

    public void setPriceStrategy(PriceStrategy p) {
        priceStrategy = p;
    }
}
