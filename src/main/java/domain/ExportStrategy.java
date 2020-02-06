package domain;

import java.util.ArrayList;

public interface ExportStrategy {
    void export(ArrayList<MovieTicket> tickets, int orderNr);
}
