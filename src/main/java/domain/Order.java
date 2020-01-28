package domain;

import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
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

    public void addSeatReservation(MovieTicket ticket)
    {
        tickets.add(ticket);
    }

    public double calculatePrice()
    {
        boolean premiumTicket = tickets.get(0).isPremiumTicket();
        double price1 = tickets.get(0).getPrice();
        String s = tickets.get(0).toString();
        DayOfWeek screenShowingDay = tickets.get(0).getDayofWeek();

        boolean sixormoreTickets = (tickets.size() >= 6);

        for (MovieTicket ticket : tickets) {
            if(ticket.isPremiumTicket() && this.isStudentOrder){
                price1 = price1 + ticket.getPrice() + 02.00 ;
                return price1;
            }
            else if(!ticket.isPremiumTicket() && !this.isStudentOrder){
                //....
            }

            //return price1 + price1;
        }
        System.out.println(price1);
        /*
        •	Elk 2e ticket is gratis voor studenten (elke dag van de week) of als het een voorstelling betreft op een doordeweekse dag (ma/di/wo/do) voor iedereen.
        if (this.isStudentOrder){
        tickets.size / 2
        }
        else {
        if datum != vrij,zat,zon
        tickets.size / modulo 2

        }
        •	In het weekend betaal je als niet-student de volle prijs, tenzij de bestelling uit 6 kaartjes of meer bestaat, dan krijg je 10% groepskorting.
        if (this.isStudentOrder){}
        else{
        ..
        if tickets.size >= 6
            return price x 0,90
        }
        •	Een premium ticket is voor studenten 2,- duurder dan de standaardprijs per stoel van de voorstelling, voor niet-studenten 3,-. Deze worden in de kortingen verrekend (dus bij een 2e ticket dat gratis is, ook geen extra kosten; bij 10% korting ook 10% van de extra kosten).
        if (this.isStudentOrder){
        foreach premiumticket
        price = tickets x (seatprice + 2)
        else {
        price = tickets x (seatprice + 2)
        }
        •	Om de casus niet nog complexer te maken, gaan we ervan uit dat bij een studenten-order alle tickets voor studenten zijn; vandaar het isStudentOrder attribuut in de Order klasse en niet in MovieTicket.
            (is wel een leuke uitdaging om deze eis er wél in op te nemen).
        */
        double price;
        price = 12.00;

        if (this.isStudentOrder){
            return price;
        }

        return price;
    }

    public void export(TicketExportFormat exportFormat)
    {
        // Bases on the string representations of the tickets (toString), write
        // the ticket to a file with naming convention Order_<orderNr>.txt or
        // Order_<orderNr>.json
        switch (exportFormat) {
        case PLAINTEXT:
            try {
                FileWriter writer = new FileWriter("Order_<orderNr>.txt", true);
                writer.write(tickets.toString());
                writer.write("\r\n");   // write new line
                writer.write("...");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case JSON:
            //jsonify....
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

        //Adding stuff
        movie1.addScreening(screening1);
        movie2.addScreening(screening2);

        //Order
        Order order1 = new Order(1, true);
        order1.addSeatReservation(ticket1);

    }
}
