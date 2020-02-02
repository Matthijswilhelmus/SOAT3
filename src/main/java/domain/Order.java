package domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

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

    public double calculatePrice()
    {
        double price = 00.00;
        //Below: assumption that an order is placed for 1 film screening, so you only have to check one ticket from the order
        DayOfWeek screenShowingDay = tickets.get(0).getDayOfWeek();
        boolean sixormoreTickets = (tickets.size() >= 6);

        if (this.isStudentOrder()) {
            for (int i = 0; i < tickets.size(); i = i + 2) {
                if(tickets.get(i).isPremiumTicket()){
                    price = price + (tickets.get(i).getPrice() + 02.00);
                }
                else{
                    price = price + tickets.get(i).getPrice();
                }
            }
            return price;
        } else if (!this.isStudentOrder && screenShowingDay != DayOfWeek.SATURDAY & screenShowingDay != DayOfWeek.SUNDAY & screenShowingDay != DayOfWeek.FRIDAY) {
            for (int i = 0; i < tickets.size(); i = i + 2) {
                if(tickets.get(i).isPremiumTicket()){
                    price = price + (tickets.get(i).getPrice() + 03.00);
                }
                else{
                    price = price + tickets.get(i).getPrice();
                }
            }
            return price;
        } else if (!this.isStudentOrder && screenShowingDay == DayOfWeek.SATURDAY | screenShowingDay == DayOfWeek.SUNDAY | screenShowingDay == DayOfWeek.FRIDAY) {
            for (MovieTicket ticket : tickets) {
                if (sixormoreTickets & ticket.isPremiumTicket()) {
                    price = price + ((ticket.getPrice() + 03.00) * 0.90);
                } else if (sixormoreTickets & !ticket.isPremiumTicket()) {
                    price = price + (ticket.getPrice() * 0.90);
                } else if (!sixormoreTickets & !ticket.isPremiumTicket()) {
                    price = price + (ticket.getPrice());
                } else{
                    price = price + (ticket.getPrice() + 03.00);
                }
            }
            return price;
        }
        return price;
    }
        /*
        //check if the order has tickets..?
        •	Elk 2e ticket is gratis voor studenten (elke dag van de week) of als het een voorstelling betreft op een doordeweekse dag (ma/di/wo/do) voor iedereen.
        •	In het weekend betaal je als niet-student de volle prijs, tenzij de bestelling uit 6 kaartjes of meer bestaat, dan krijg je 10% groepskorting.
        •	Een premium ticket is voor studenten 2,- duurder dan de standaardprijs per stoel van de voorstelling, voor niet-studenten 3,-.
            Deze worden in de kortingen verrekend (dus bij een 2e ticket dat gratis is, ook geen extra kosten; bij 10% korting ook 10% van de extra kosten).
        •	Om de casus niet nog complexer te maken, gaan we ervan uit dat bij een studenten-order alle tickets voor studenten zijn; vandaar het isStudentOrder attribuut in de Order klasse en niet in MovieTicket.
            (is wel een leuke uitdaging om deze eis er wél in op te nemen).
        */

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
            //Does not work yet (circular references in the serialized data)
            Gson gsonx = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            try {
                gsonx.toJson(tickets, new FileWriter("Order_"+this.orderNr+".json"));
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
