package domain;

import Factory.ExportStrategyFactory;
import Factory.PriceStrategyFactory;
import State.CreatedOrder;
import State.OrderState;

import java.util.ArrayList;

public class Order {
    private int orderNr;
    private boolean isStudentOrder;
    private ArrayList<MovieTicket> tickets;
    private OrderState orderState;
    private ExportStrategyFactory exportFactory;
    private PriceStrategyFactory priceFactory;

    public Order(int orderNr, boolean isStudentOrder) {
        this.orderNr = orderNr;
        this.isStudentOrder = isStudentOrder;
        exportFactory = new ExportStrategyFactory();
        priceFactory = new PriceStrategyFactory();
        //factory?...
        tickets = new ArrayList<MovieTicket>();
        orderState = new CreatedOrder();
    }

    public int getOrderNr() {
        return orderNr;
    }

    public Boolean isStudentOrder() {
        return isStudentOrder;
    }

    public String getStateName() {
        return orderState.getStateName();
    }

    public void pay() {
        this.orderState = orderState.pay();
    }

    public void submit() {
        this.orderState = orderState.submit();
    }

    public void cancel() {
        this.orderState = orderState.cancel();
    }

    public void edit() {
        this.orderState = orderState.edit();
    }


    public void addSeatReservation(MovieTicket ticket) {
        tickets.add(ticket);
    }

    public double calculatePrice() {
        //return priceStrategy.calculatePrice(tickets, this.isStudentOrder);
        PriceStrategy priceStrategy = priceFactory.createPriceStrategy(this.isStudentOrder);
        return priceStrategy.calculatePrice(tickets);
    }

    public void export(TicketExportFormat exportFormat) {
        //could use factory pattern
        ExportStrategy exportStrategy = exportFactory.createExportStrategy(exportFormat);
        exportStrategy.export(tickets, this.orderNr);
    }

    public static void main(String[] args) {
    }
}
