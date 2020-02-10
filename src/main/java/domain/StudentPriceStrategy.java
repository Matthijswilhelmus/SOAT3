package domain;

import java.util.ArrayList;

public class StudentPriceStrategy extends PriceStrategy {

    @Override
    public double calculatePrice(ArrayList<MovieTicket> tickets) {
        double totalPrice = 0;
        if (!tickets.isEmpty()) {
            for (int i = 0; i < tickets.size(); i++) {
                if (i % 2 == 0) {
                    totalPrice += calculateTicketPrice(tickets.get(i));
                }
            }
        }
        return totalPrice;
    }

    public double calculateTicketPrice(MovieTicket ticket) {
        double ticketPrice = ticket.getPrice();
        if (ticket.isPremiumTicket()) {
            ticketPrice += 2;
        }
        return ticketPrice;
    }
}
