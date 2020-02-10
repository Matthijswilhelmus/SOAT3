package Factory;

import domain.NoStudentPriceStrategy;
import domain.PriceStrategy;
import domain.StudentPriceStrategy;

public class PriceStrategyFactory {
    public PriceStrategy createPriceStrategy(boolean OrderType) {
        PriceStrategy priceStrategy = null;

        if (OrderType) {
            priceStrategy = new StudentPriceStrategy();
        } else {
            priceStrategy = new NoStudentPriceStrategy();
        }
        return priceStrategy;
    }
}
