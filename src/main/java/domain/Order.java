package domain;

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

    }
}
