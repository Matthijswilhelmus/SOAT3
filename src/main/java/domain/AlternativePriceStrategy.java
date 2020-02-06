package domain;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class AlternativePriceStrategy implements PriceStrategy {
    @Override
    public double calculatePrice(ArrayList<MovieTicket> tickets, boolean isStudentOrder) {
        return 0;
    }

    public double calculateTicketPrice(MovieTicket ticket, boolean isStudentOrder) {
        double ticketPrice = ticket.getPrice();
        if (ticket.isPremiumTicket()) {
            ticketPrice += 3;
            if (isStudentOrder) {
                ticketPrice -= 1;
            }
        }
        return ticketPrice;
    }

    public boolean checkIsWeekday(DayOfWeek dayOfWeek) {
        return dayOfWeek.getValue() >= DayOfWeek.MONDAY.getValue() && dayOfWeek.getValue() <= DayOfWeek.THURSDAY.getValue();
    }
}
