package domain;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class Order
{
    private int orderNr;
    private boolean isStudentOrder;

    private ArrayList<MovieTicket> tickets;

    public Order(int orderNr, boolean isStudentOrder)
    {
        this.orderNr = orderNr;
        this.isStudentOrder = isStudentOrder;

        tickets = new ArrayList<MovieTicket>();
    }

    public int getOrderNr()
    {
        return orderNr;
    }

    public Boolean isStudentOrder()
    {
        return isStudentOrder;
    }

    public void addSeatReservation(MovieTicket ticket)
    {
        tickets.add(ticket);
    }

    //-------------------------------------------------------
    public double calculatePrice() {
        double totalPrice = 0;
        if (tickets.size() > 0) {
            DayOfWeek dayOfWeek = tickets.get(0).getDayOfWeek();
            boolean isWeekDay = checkIsWeekday(dayOfWeek);
            if (isStudentOrder || isWeekDay) {
                for (int i = 0; i < tickets.size(); i++) {
                    if (i % 2 == 0) {
                        totalPrice += calculateTicketPrice(tickets.get(i));
                    }
                }
            } else {
                for (MovieTicket ticket : tickets) {
                    totalPrice += calculateTicketPrice(ticket);
                }
                if (tickets.size() >= 6) {
                    totalPrice = totalPrice * 0.9;
                }
            }
        }
        return totalPrice;
    }

    public double calculateTicketPrice(MovieTicket ticket) {
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

    public void export(TicketExportFormat exportFormat)
    {
        // Bases on the string representations of the tickets (toString), write
        // the ticket to a file with naming convention Order_<orderNr>.txt or
        // Order_<orderNr>.json
        switch (exportFormat) {
        case PLAINTEXT:
            try {
                FileWriter writer = new FileWriter("Order_"+this.orderNr+".txt", true);
                writer.write(tickets.toString());
                writer.write("\r\n");   // write new line
                writer.write("The total price: " + this.calculatePrice());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case JSON:
            try {
                FileWriter fileWriter = new FileWriter("Order_"+this.orderNr+".json");
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < tickets.size() ; i++) {
                    JSONObject jsonTicket = new JSONObject();
                    jsonTicket.append("Ticket", tickets.get(i).toString());
                    if (i % 2 == 0) {
                        jsonTicket.append("Price", calculateTicketPrice(tickets.get(i)));
                    } else {
                        jsonTicket.append("Price", "0 (Second ticket free) (" + calculateTicketPrice(tickets.get(i)) + ")");
                    }
                    jsonArray.put(jsonTicket);
                }
                jsonObject.put("Tickets", jsonArray);
                jsonObject.put("Total price", String.format("%.2f", calculatePrice()));
                fileWriter.write(jsonObject.toString());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        }
    }

    public static void main(String[] args) {
        // do stuff here...

        //Date variables
        LocalDateTime date =  LocalDateTime.of(2020, Month.JANUARY, 28, 12, 50);

        //Movies
        Movie movie1 = new Movie("Joker");
        Movie movie2 = new Movie("1917");

        //Screenings
        MovieScreening screening1 = new MovieScreening(movie1, date, 10.00);
        MovieScreening screening2 = new MovieScreening(movie2, date, 10.00);

        //Movie tickets
        MovieTicket ticket1 = new MovieTicket(screening1, true, 1, 1);
        MovieTicket ticket2 = new MovieTicket(screening1, true, 1, 2);
        MovieTicket ticket3 = new MovieTicket(screening1, true, 1, 3);

        //Adding stuff
        movie1.addScreening(screening1);
        movie2.addScreening(screening2);

        //Order
        Order order1 = new Order(1, true);
        order1.addSeatReservation(ticket1);
        order1.addSeatReservation(ticket2);
        order1.addSeatReservation(ticket3);
        order1.calculatePrice();
        order1.export(TicketExportFormat.PLAINTEXT);
        order1.export(TicketExportFormat.JSON);
    }
}
