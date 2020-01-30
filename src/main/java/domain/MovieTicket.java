package domain;

import com.google.gson.annotations.Expose;

import java.time.DayOfWeek;

public class MovieTicket
{
    @Expose(deserialize = false)
    private MovieScreening movieScreening;
    @Expose
    private boolean isPremiumTicket;
    @Expose
    private int seatRow;
    @Expose
    private int seatNr;

    public MovieTicket(
            MovieScreening movieScreening,
            boolean isPremiumTicket,
            int seatRow,
            int seatNr)
    {
        this.movieScreening = movieScreening;
        this.isPremiumTicket = isPremiumTicket;
        this.seatRow = seatRow;
        this.seatNr = seatNr;
    }

    public boolean isPremiumTicket()
    {
        return isPremiumTicket;
    }

    public double getPrice()
    {
        return movieScreening.getPricePerSeat();
    }

    //get ScreenDate
    public DayOfWeek getDayOfWeek()
    {
        return movieScreening.getDateAndTime().getDayOfWeek();
    }

    @Override
    public String toString() {
        return movieScreening.toString() + " - row " + seatRow + ", seat " + seatNr +
                (isPremiumTicket ? " (Premium)" : "");
    }
}
