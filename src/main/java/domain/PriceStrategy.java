package domain;

import java.time.DayOfWeek;
import java.util.ArrayList;

public interface PriceStrategy {
    double calculatePrice(ArrayList<MovieTicket> tickets, boolean isStudentOrder);

    double calculateTicketPrice(MovieTicket ticket, boolean isStudentOrder);

    boolean checkIsWeekday(DayOfWeek dayOfWeek);

}
