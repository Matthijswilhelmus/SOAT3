package domain;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
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
        String extensionName = "";
        if (exportFormat.name().equals(TicketExportFormat.JSON.toString())) {
            extensionName = ".json";
        }
        if (exportFormat.name().equals(TicketExportFormat.PLAINTEXT.toString())) {
            extensionName = ".txt";
        }
        String baseFileName = "Order_";
        String orderNumber = String.valueOf(orderNr);
        String finalFileName = baseFileName.concat(orderNumber);

        File file = new File(finalFileName + extensionName);

        FileWriter fileWriter = null;

        try {
            if (!file.exists()) {
                boolean isCreated = file.createNewFile();
                System.out.println(isCreated);
            }
            fileWriter = new FileWriter(file);

            if (exportFormat.name().equals(TicketExportFormat.JSON.toString())) {
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
            }
            if (exportFormat.name().equals(TicketExportFormat.PLAINTEXT.toString())) {
                for (int i = 0; i < tickets.size(); i++) {
                    if (i % 2 == 0) {
                        fileWriter.write(tickets.get(i).toString() + " : " + calculateTicketPrice(tickets.get(i)) + "\n");
                    } else {
                        fileWriter.write(tickets.get(i).toString() + " : 0 (" + calculateTicketPrice(tickets.get(i)) + ")" + "\n");
                    }
                }
                fileWriter.write(String.format("Total price: %.2f", calculatePrice()));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        // do stuff here...
    }
}
