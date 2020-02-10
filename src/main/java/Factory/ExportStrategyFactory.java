package Factory;

import domain.ExportStrategy;
import domain.JsonExportStrategy;
import domain.TicketExportFormat;
import domain.TxtExportStrategy;

public class ExportStrategyFactory {
    public ExportStrategy createExportStrategy(TicketExportFormat format) {
        ExportStrategy exportStrategy = null;

        if (format == TicketExportFormat.PLAINTEXT) {
            exportStrategy = new TxtExportStrategy();
        } else if (format == TicketExportFormat.JSON) {
            exportStrategy = new JsonExportStrategy();
        }
        return exportStrategy;
    }
}
