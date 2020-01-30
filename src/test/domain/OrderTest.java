package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

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

    //Default ~~ template
    @Test
    void calculatePrice() {

        //----- ARRANGE ----
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


        //----- ACT ----
        order1.addSeatReservation(ticket1);
        //----- ASSERT ----

        assertEquals(order1.getOrderNr(), 1);
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

        //----- ASSERT ----
        assertEquals(order1.calculatePrice(), 20.00);
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
        MovieTicket ticket2 = new MovieTicket(screening1, true, 1, 2);

        //Adding stuff
        movie1.addScreening(screening1);

        //Order
        Order order1 = new Order(1, true);

        //----- ACT ----
        order1.addSeatReservation(ticket1);
        order1.addSeatReservation(ticket2);

        //----- ASSERT ----
        assertEquals(order1.calculatePrice(), 12.00);
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
        //Adding stuff
        movie1.addScreening(screening1);

        //Order
        Order order1 = new Order(1, false);

        //----- ACT ----
        order1.addSeatReservation(ticket1);
        order1.addSeatReservation(ticket2);

        //----- ASSERT ----
        assertEquals(order1.calculatePrice(), 13.00);
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

        //----- ASSERT ----
        assertEquals(order1.calculatePrice(), 10.00);
    }

    @Test
    void calculatePriceNotStudentOrderAndPremiumTicketInWeekendWith6orMoreTickets() {
        //----- ARRANGE ----
        //Date variables
        LocalDateTime date =  LocalDateTime.of(2020, Month.FEBRUARY, 1, 12, 50);

        //Movies
        Movie movie1 = new Movie("Joker");

        //Screenings
        MovieScreening screening1 = new MovieScreening(movie1, date, 07.00);

        //Movie tickets
        MovieTicket ticket1 = new MovieTicket(screening1, true, 1, 1);
        MovieTicket ticket2 = new MovieTicket(screening1, true, 1, 2);
        MovieTicket ticket3 = new MovieTicket(screening1, true, 1, 3);
        MovieTicket ticket4 = new MovieTicket(screening1, true, 1, 4);
        MovieTicket ticket5 = new MovieTicket(screening1, true, 1, 5);
        MovieTicket ticket6 = new MovieTicket(screening1, true, 1, 6);

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

        //----- ASSERT ----
        assertEquals(order1.calculatePrice(), 54.00);
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

        //----- ASSERT ----
        assertEquals(order1.calculatePrice(), 63.00);
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

        //----- ASSERT ----
        assertEquals(order1.calculatePrice(), 50.00);
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
        MovieTicket ticket2 = new MovieTicket(screening1, true, 1, 2);
        MovieTicket ticket3 = new MovieTicket(screening1, true, 1, 3);
        MovieTicket ticket4 = new MovieTicket(screening1, true, 1, 4);

        //Adding stuff
        movie1.addScreening(screening1);

        //Order
        Order order1 = new Order(1, false);

        //----- ACT ----
        order1.addSeatReservation(ticket1);
        order1.addSeatReservation(ticket2);
        order1.addSeatReservation(ticket3);
        order1.addSeatReservation(ticket4);

        //----- ASSERT ----
        assertEquals(order1.calculatePrice(), 52.00);
    }
    /*
    //Default ~~ template
    @Test
    void export() {
        assertEquals(1,1);
    }

    @Test
    void exportAsPlaintext() {}

    @Test
    void exportAsJSON() {}
    */
}