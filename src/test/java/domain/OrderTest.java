package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    @BeforeAll
    static void initAll() {

    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void changeStates() {
        //----- ARRANGE ----
        Order order1 = new Order(1, true);
        Order order2 = new Order(2, true);

        //----- ACT ----
        String currentOrderState = order1.getStateName();
        order1.submit();
        String currentOrderState2 = order1.getStateName();
        order1.edit();
        String currentOrderState3 = order1.getStateName();
        order1.pay();
        String currentOrderState4 = order1.getStateName();

        order2.cancel();
        String currentOrderState5 = order2.getStateName();
        //....

        //----- ASSERT ----
        assertEquals("CreatedOrder", currentOrderState);
        assertEquals("ReservedOrder", currentOrderState2);
        assertEquals("ReservedOrder", currentOrderState3);
        assertEquals("ProcessedOrder", currentOrderState4);
        assertEquals("CancelledOrder", currentOrderState5);
    }

    //Default ~~ template
    @Test
    void calculatePriceWithNoTickets() {

        //----- ARRANGE ----
        Order order1 = new Order(1, true);

        //----- ACT ----
        double price = order1.calculatePrice();

        //----- ASSERT ----
        assertEquals( 0.00, price);
    }

    @Test
    void calculatePriceWithStudentOrderNoPremiumTicket() {
        //----- ARRANGE ----
        //Date variables
        LocalDateTime date =  LocalDateTime.of(2020, Month.FEBRUARY, 4, 12, 50);

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

        //----- ACT ----
        order1.addSeatReservation(ticket1);
        order1.addSeatReservation(ticket2);
        order1.addSeatReservation(ticket3);
        order1.addSeatReservation(ticket4);
        double price = order1.calculatePrice();

        //----- ASSERT ----
        //every second ticket is free
        assertEquals( 20.00, price);
    }

    @Test
    void calculatePriceWithStudentOrderAndPremiumTicket() {
        //----- ARRANGE ----
        //Date variables
        LocalDateTime date =  LocalDateTime.of(2020, Month.FEBRUARY, 2, 12, 50);

        //Movies
        Movie movie1 = new Movie("Knives Out");

        //Screenings
        MovieScreening screening1 = new MovieScreening(movie1, date, 10.00);

        //Movie tickets
        MovieTicket ticket1 = new MovieTicket(screening1, true, 1, 1);
        MovieTicket ticket2 = new MovieTicket(screening1, false, 1, 2);

        //Adding stuff
        movie1.addScreening(screening1);

        //Order
        Order order1 = new Order(1, true);

        //----- ACT ----
        order1.addSeatReservation(ticket1);
        order1.addSeatReservation(ticket2);
        double price = order1.calculatePrice();

        //----- ASSERT ----
        //every second ticket is free and premium adds 2 to price of ticket
        assertEquals( 12.00, price);
    }

    @Test
    void calculatePriceNotStudentOrderAndPremiumTicketOnWeekdays() {
        //----- ARRANGE ----
        //Date variables
        LocalDateTime date =  LocalDateTime.of(2020, Month.FEBRUARY, 6, 12, 50);

        //Movies
        Movie movie1 = new Movie("1917");

        //Screenings
        MovieScreening screening1 = new MovieScreening(movie1, date, 10.00);

        //Movie tickets
        MovieTicket ticket1 = new MovieTicket(screening1, true, 1, 1);
        MovieTicket ticket2 = new MovieTicket(screening1, true, 1, 2);
        MovieTicket ticket3 = new MovieTicket(screening1, false, 1, 3);
        //Adding stuff
        movie1.addScreening(screening1);

        //Order
        Order order1 = new Order(1, false);

        //----- ACT ----
        order1.addSeatReservation(ticket1);
        order1.addSeatReservation(ticket2);
        order1.addSeatReservation(ticket3);
        double price = order1.calculatePrice();

        //----- ASSERT ----
        //every second ticket is free and premium adds 3 to price of ticket
        assertEquals( 23.00, price);
    }

    @Test
    void calculatePriceNotStudentOrderNoPremiumTicketOnWeekdays() {
        //----- ARRANGE ----
        //Date variables
        LocalDateTime date =  LocalDateTime.of(2020, Month.FEBRUARY, 3, 12, 50);

        //Movies
        Movie movie1 = new Movie("Star Wars: The Rise of Skywalker");

        //Screenings
        MovieScreening screening1 = new MovieScreening(movie1, date, 10.00);

        //Movie tickets
        MovieTicket ticket1 = new MovieTicket(screening1, false, 1, 1);
        MovieTicket ticket2 = new MovieTicket(screening1, false, 1, 2);
        //Adding stuff
        movie1.addScreening(screening1);

        //Order
        Order order1 = new Order(1, false);

        //----- ACT ----
        order1.addSeatReservation(ticket1);
        order1.addSeatReservation(ticket2);
        double price = order1.calculatePrice();

        //----- ASSERT ----
        //every second ticket is free
        assertEquals( 10.00, price);
    }

    @Test
    void calculatePriceNotStudentOrderAndPremiumTicketInWeekendWith6orMoreTickets() {
        //----- ARRANGE ----
        //Date variables
        LocalDateTime date =  LocalDateTime.of(2020, Month.FEBRUARY, 1, 12, 50);

        //Movies
        Movie movie1 = new Movie("Joker");

        //Screenings
        MovieScreening screening1 = new MovieScreening(movie1, date, 10.00);

        //Movie tickets
        MovieTicket ticket1 = new MovieTicket(screening1, true, 1, 1);
        MovieTicket ticket2 = new MovieTicket(screening1, true, 1, 2);
        MovieTicket ticket3 = new MovieTicket(screening1, true, 1, 3);
        MovieTicket ticket4 = new MovieTicket(screening1, false, 1, 4);
        MovieTicket ticket5 = new MovieTicket(screening1, false, 1, 5);
        MovieTicket ticket6 = new MovieTicket(screening1, false, 1, 6);

        //Adding stuff
        movie1.addScreening(screening1);

        //Order
        Order order1 = new Order(1, false);

        //----- ACT ----
        order1.addSeatReservation(ticket1);
        order1.addSeatReservation(ticket2);
        order1.addSeatReservation(ticket3);
        order1.addSeatReservation(ticket4);
        order1.addSeatReservation(ticket5);
        order1.addSeatReservation(ticket6);
        double price = order1.calculatePrice();

        //----- ASSERT ----
        // 6 or more tickets gives 10% discount over each ticket => (ticketprice(+ premium)) * 0,90
        // 3 tickets of (10+3premium fee - 10% discount) = 35,1
        // 3 tickets of (10 - 10% discount) = 27
        // total: 62,1
        assertEquals(62.1, price);
    }

    @Test
    void calculatePriceNotStudentOrderNoPremiumTicketInWeekendWith6orMoreTickets() {
        //----- ARRANGE ----
        //Date variables
        LocalDateTime date =  LocalDateTime.of(2020, Month.FEBRUARY, 1, 12, 50);

        //Movies
        Movie movie1 = new Movie("Joker");

        //Screenings
        MovieScreening screening1 = new MovieScreening(movie1, date, 10.00);

        //Movie tickets
        MovieTicket ticket1 = new MovieTicket(screening1, false, 1, 1);
        MovieTicket ticket2 = new MovieTicket(screening1, false, 1, 2);
        MovieTicket ticket3 = new MovieTicket(screening1, false, 1, 3);
        MovieTicket ticket4 = new MovieTicket(screening1, false, 1, 4);
        MovieTicket ticket5 = new MovieTicket(screening1, false, 1, 5);
        MovieTicket ticket6 = new MovieTicket(screening1, false, 1, 6);
        MovieTicket ticket7 = new MovieTicket(screening1, false, 1, 7);

        //Adding stuff
        movie1.addScreening(screening1);

        //Order
        Order order1 = new Order(1, false);

        //----- ACT ----
        order1.addSeatReservation(ticket1);
        order1.addSeatReservation(ticket2);
        order1.addSeatReservation(ticket3);
        order1.addSeatReservation(ticket4);
        order1.addSeatReservation(ticket5);
        order1.addSeatReservation(ticket6);
        order1.addSeatReservation(ticket7);
        double price = order1.calculatePrice();

        //----- ASSERT ----
        assertEquals( 63.00, price);
    }

    @Test
    void calculatePriceNotStudentOrderNoPremiumTicketInWeekendWithLessThan6Tickets() {
        //----- ARRANGE ----
        //Date variables
        LocalDateTime date =  LocalDateTime.of(2020, Month.JANUARY, 31, 12, 50);

        //Movies
        Movie movie1 = new Movie("Joker");

        //Screenings
        MovieScreening screening1 = new MovieScreening(movie1, date, 10.00);

        //Movie tickets
        MovieTicket ticket1 = new MovieTicket(screening1, false, 1, 1);
        MovieTicket ticket2 = new MovieTicket(screening1, false, 1, 2);
        MovieTicket ticket3 = new MovieTicket(screening1, false, 1, 3);
        MovieTicket ticket4 = new MovieTicket(screening1, false, 1, 4);
        MovieTicket ticket5 = new MovieTicket(screening1, false, 1, 5);

        //Adding stuff
        movie1.addScreening(screening1);

        //Order
        Order order1 = new Order(1, false);

        //----- ACT ----
        order1.addSeatReservation(ticket1);
        order1.addSeatReservation(ticket2);
        order1.addSeatReservation(ticket3);
        order1.addSeatReservation(ticket4);
        order1.addSeatReservation(ticket5);
        double price = order1.calculatePrice();

        //----- ASSERT ----
        //full price per ticket
        assertEquals( 50.00, price);
    }

    @Test
    void calculatePriceNotStudentOrderAndPremiumTicketInWeekendWithLessThan6Tickets() {
        //----- ARRANGE ----
        //Date variables
        LocalDateTime date =  LocalDateTime.of(2020, Month.FEBRUARY, 2, 12, 50);

        //Movies
        Movie movie1 = new Movie("Dark Waters");

        //Screenings
        MovieScreening screening1 = new MovieScreening(movie1, date, 10.00);

        //Movie tickets
        MovieTicket ticket1 = new MovieTicket(screening1, true, 1, 1);
        MovieTicket ticket2 = new MovieTicket(screening1, false, 1, 2);
        MovieTicket ticket3 = new MovieTicket(screening1, true, 1, 3);
        MovieTicket ticket4 = new MovieTicket(screening1, false, 1, 4);

        //Adding stuff
        movie1.addScreening(screening1);

        //Order
        Order order1 = new Order(1, false);

        //----- ACT ----
        order1.addSeatReservation(ticket1);
        order1.addSeatReservation(ticket2);
        order1.addSeatReservation(ticket3);
        order1.addSeatReservation(ticket4);
        double price = order1.calculatePrice();

        //----- ASSERT ----
        // full price per ticket with two ticket plus 3 premium fee
        assertEquals( 46.00, price);
    }

    @Test
    void exportAsPlaintext() {
        //----- ARRANGE ----
        //Date variables
        LocalDateTime date =  LocalDateTime.of(2020, Month.FEBRUARY, 2, 12, 50);

        //Movies
        Movie movie1 = new Movie("Dark Waters");

        //Screenings
        MovieScreening screening1 = new MovieScreening(movie1, date, 10.00);

        //Movie tickets
        MovieTicket ticket1 = new MovieTicket(screening1, true, 1, 1);
        MovieTicket ticket2 = new MovieTicket(screening1, false, 1, 2);
        MovieTicket ticket3 = new MovieTicket(screening1, true, 1, 3);
        MovieTicket ticket4 = new MovieTicket(screening1, false, 1, 4);

        //Adding stuff
        movie1.addScreening(screening1);

        //Order
        Order order1 = new Order(1, false);

        //----- ACT ----
        order1.addSeatReservation(ticket1);
        order1.addSeatReservation(ticket2);
        order1.addSeatReservation(ticket3);
        order1.addSeatReservation(ticket4);
        order1.export(TicketExportFormat.PLAINTEXT);

        //----- ASSERT ----

    }

    @Test
    void exportAsJSON() {
        //----- ARRANGE ----
        //Date variables
        LocalDateTime date =  LocalDateTime.of(2020, Month.FEBRUARY, 2, 12, 50);

        //Movies
        Movie movie1 = new Movie("Dark Waters");

        //Screenings
        MovieScreening screening1 = new MovieScreening(movie1, date, 10.00);

        //Movie tickets
        MovieTicket ticket1 = new MovieTicket(screening1, true, 1, 1);
        MovieTicket ticket2 = new MovieTicket(screening1, false, 1, 2);
        MovieTicket ticket3 = new MovieTicket(screening1, true, 1, 3);
        MovieTicket ticket4 = new MovieTicket(screening1, false, 1, 4);

        //Adding stuff
        movie1.addScreening(screening1);

        //Order
        Order order1 = new Order(1, false);

        //----- ACT ----
        order1.addSeatReservation(ticket1);
        order1.addSeatReservation(ticket2);
        order1.addSeatReservation(ticket3);
        order1.addSeatReservation(ticket4);
        order1.export(TicketExportFormat.JSON);

        //----- ASSERT ----
        
    }
}