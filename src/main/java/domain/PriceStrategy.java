package domain;

import java.time.DayOfWeek;
import java.util.ArrayList;

public abstract class PriceStrategy {
    abstract double calculatePrice(ArrayList<MovieTicket> tickets);

    abstract double calculateTicketPrice(MovieTicket ticket);

    public boolean checkIsWeekday(DayOfWeek dayOfWeek) {
        return dayOfWeek.getValue() >= DayOfWeek.MONDAY.getValue() && dayOfWeek.getValue() <= DayOfWeek.THURSDAY.getValue();
    }

}
