package INF3132.parser;

import java.io.IOException;
import java.util.Map;

import INF3132.items.subclasses.Medecine;
import INF3132.monsters.Status;

public class MedecineParser extends BaseParser<Medecine> {

    public MedecineParser(String path) throws IOException {
        super(path);
    }

    @Override
    protected Medecine parseBlock(Map<String, String> blockData) {
        String name = blockData.get("Name");
        Status status = null;
        String statusStr = blockData.get("Status");

        try {
            status = Status.valueOf(statusStr.toUpperCase());
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la création des médicaments");
        }

        return new Medecine(name, status);
    }
}
