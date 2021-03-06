/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.export;

/**
 * The factory to get the rigth movement exporter.
 *
 * Application of the Singleton and Factory patherns.
 *
 * @author Fernando
 */
public class MovementsExporterFactory {

    private static MovementsExporterFactory instance = new MovementsExporterFactory();

    private MovementsExporterFactory() {
    }

    public static MovementsExporterFactory getInstance() {
        return instance;
    }

    public MovementsExporter getExporter(int format) {
        switch (format) {
            case MovementsExporter.CSV:
                return new ExportMovementsToCsv();

            case MovementsExporter.XML:
                return new ExportMovementsToXml();

            case MovementsExporter.JSON:
                return new ExportMovementsToJson();

            default:
                return null;
        }
    }
}
