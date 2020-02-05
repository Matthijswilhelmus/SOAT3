package domain;

import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Order {
    private static final Logger LOGGER = Logger.getLogger(Order.class.getName());
    private int orderNr;
    private boolean isStudentOrder;

    private ArrayList<MovieTicket> tickets;

    public Order(int orderNr, boolean isStudentOrder) {
        this.orderNr = orderNr;
        this.isStudentOrder = isStudentOrder;

        tickets = new ArrayList<MovieTicket>();
    }

    public int getOrderNr() {
        return orderNr;
    }

    public Boolean isStudentOrder() {
        return isStudentOrder;
    }

    public void addSeatReservation(MovieTicket ticket) {
        tickets.add(ticket);
    }

    public double calculatePrice() {
        double price = 00.00;
        double studentPremium = 02.00;
        double premium = 03.00;
        //Below: assumption that an order is placed for 1 film screening, so you only have to check one ticket from the order
        DayOfWeek screenShowingDay = tickets.get(0).getDayOfWeek();
        boolean sixormoreTickets = (tickets.size() >= 6);

        if (this.isStudentOrder()) {
            for (int i = 0; i < tickets.size(); i = i + 2) {
                if (tickets.get(i).isPremiumTicket()) {
                    price = price + (tickets.get(i).getPrice() + studentPremium);
                } else {
                    price = price + tickets.get(i).getPrice();
                }
            }
            return price;
        } else if (!this.isStudentOrder && screenShowingDay != DayOfWeek.SATURDAY & screenShowingDay != DayOfWeek.SUNDAY & screenShowingDay != DayOfWeek.FRIDAY) {
            for (int i = 0; i < tickets.size(); i = i + 2) {
                if (tickets.get(i).isPremiumTicket()) {
                    price = price + (tickets.get(i).getPrice() + premium);
                } else {
                    price = price + tickets.get(i).getPrice();
                }
            }
            return price;
        } else if (!this.isStudentOrder && screenShowingDay == DayOfWeek.SATURDAY | screenShowingDay == DayOfWeek.SUNDAY | screenShowingDay == DayOfWeek.FRIDAY) {
            for (MovieTicket ticket : tickets) {
                if (sixormoreTickets & ticket.isPremiumTicket()) {
                    price = price + ((ticket.getPrice() + premium) * 0.90);
                } else if (sixormoreTickets & !ticket.isPremiumTicket()) {
                    price = price + (ticket.getPrice() * 0.90);
                } else if (!sixormoreTickets & !ticket.isPremiumTicket()) {
                    price = price + (ticket.getPrice());
                } else {
                    price = price + (ticket.getPrice() + premium);
                }
            }
            return price;
        }
        return price;
    }

    //-------------------------------------------------------
    /*
    public double calculatePrice() {
        double totalPrice = 0;
        if (!tickets.isEmpty()) {
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
     */

    public void export(TicketExportFormat exportFormat) {
        FileWriter fileWriter = null;
        switch (exportFormat) {
            case PLAINTEXT:
                try {
                    fileWriter = new FileWriter("Order_" + this.orderNr + ".txt", true);
                    fileWriter.write(tickets.toString());
                    fileWriter.write("\r\n");   // write new line
                    fileWriter.write("...");
                    fileWriter.close();
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                } finally {
                    if (fileWriter != null) {
                        try {
                            fileWriter.close();
                        } catch (IOException e) {
                            LOGGER.log(Level.SEVERE, e.toString(), e);
                        }
                    }
                }
                break;
            case JSON:
                //jsonify....
                break;
        }
    }

    public static void main(String[] args) {
        // do stuff here...
    }
}
