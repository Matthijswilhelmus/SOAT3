package domain;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class NormalPriceStrategy extends PriceStrategy {

    @Override
    public double calculatePrice(ArrayList<MovieTicket> tickets, boolean isStudentOrder) {
        double totalPrice = 0;
        if (!tickets.isEmpty()) {
            DayOfWeek dayOfWeek = tickets.get(0).getDayOfWeek();
            boolean isWeekDay = checkIsWeekday(dayOfWeek);
            if (isStudentOrder || isWeekDay) {
                for (int i = 0; i < tickets.size(); i++) {
                    if (i % 2 == 0) {
                        totalPrice += calculateTicketPrice(tickets.get(i), isStudentOrder);
                    }
                }
            } else {
                for (MovieTicket ticket : tickets) {
                    totalPrice += calculateTicketPrice(ticket, false);
                }
                if (tickets.size() >= 6) {
                    totalPrice = totalPrice * 0.9;
                }
            }
        }
        return totalPrice;
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
}
