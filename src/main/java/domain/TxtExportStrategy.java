package domain;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TxtExportStrategy implements ExportStrategy {
    private static final Logger LOGGER = Logger.getLogger(Order.class.getName());

    @Override
    public void export(ArrayList<MovieTicket> tickets, int orderNr) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("Order_" + orderNr + ".txt", true);
            fileWriter.write(tickets.toString());
            fileWriter.write("\r\n");   // write new line
            fileWriter.write("...");
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
    }
}
