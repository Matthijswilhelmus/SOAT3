package domain;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class Order {
    private int orderNr;
    private boolean isStudentOrder;
    PriceStrategy priceStrategy;
    ExportStrategy exportStrategy;


    private ArrayList<MovieTicket> tickets;

    public Order(int orderNr, boolean isStudentOrder) {
        this.orderNr = orderNr;
        this.isStudentOrder = isStudentOrder;

        tickets = new ArrayList<MovieTicket>();
        priceStrategy = new NormalPriceStrategy();
        exportStrategy = new TxtExportStrategy();
    }

    public int getOrderNr() {
        return orderNr;
    }

    public Boolean isStudentOrder() {
        return isStudentOrder;
    }

    public void setPriceStrategy(PriceStrategy p) {
        priceStrategy = p;
    }

    public void setExportStrategy(ExportStrategy exp) {
        exportStrategy = exp;
    }

    public void addSeatReservation(MovieTicket ticket) {
        tickets.add(ticket);
    }

    public double calculatePrice() {
        return priceStrategy.calculatePrice(tickets, this.isStudentOrder);
    }

    public void export(TicketExportFormat exportFormat) {
        if (exportFormat == TicketExportFormat.JSON) {
            this.setExportStrategy(new JsonExportStrategy());
            exportStrategy.export(tickets, this.orderNr);
        } else if (exportFormat == TicketExportFormat.PLAINTEXT) {
            this.setExportStrategy(new TxtExportStrategy());
            exportStrategy.export(tickets, this.orderNr);
        }
    }

    public static void main(String[] args) {
        // do stuff here...

        //Date variables
        LocalDateTime date = LocalDateTime.of(2020, Month.FEBRUARY, 4, 12, 50);

        //Movies
        Movie movie1 = new Movie("Joker");

        //Screenings
        MovieScreening screening1 = new MovieScreening(movie1, date, 10.00);

        //Movie tickets
        MovieTicket ticket1 = new MovieTicket(screening1, false, 1, 1);
        MovieTicket ticket2 = new MovieTicket(screening1, false, 1, 1);
        MovieTicket ticket3 = new MovieTicket(screening1, false, 1, 1);
        MovieTicket ticket4 = new MovieTicket(screening1, false, 1, 1);

        //Adding stuff
        movie1.addScreening(screening1);

        //Order
        Order order1 = new Order(1, true);

        order1.addSeatReservation(ticket1);
        order1.addSeatReservation(ticket2);
        order1.addSeatReservation(ticket3);
        order1.addSeatReservation(ticket4);

        order1.calculatePrice();
        order1.setPriceStrategy(new AlternativePriceStrategy());
        order1.calculatePrice();

        order1.export(TicketExportFormat.JSON);
        order1.export(TicketExportFormat.PLAINTEXT);
        order1.export(TicketExportFormat.JSON);

    }
}
