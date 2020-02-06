package domain;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonExportStrategy implements ExportStrategy {
    private static final Logger LOGGER = Logger.getLogger(Order.class.getName());

    @Override
    public void export(ArrayList<MovieTicket> tickets, int orderNr) {

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("Order_" + orderNr + ".json", true);
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (MovieTicket ticket : tickets) {
                JSONObject jsonTicket = new JSONObject();
                jsonTicket.append("Ticket", ticket.toString());
                jsonArray.put(jsonTicket);
            }
            jsonObject.put("Tickets", jsonArray);
            fileWriter.write(jsonObject.toString());
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
