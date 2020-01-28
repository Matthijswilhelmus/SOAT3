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
    void export() {
        assertEquals(1,1);
    }
}