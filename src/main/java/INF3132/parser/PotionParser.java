package INF3132.parser;

import java.io.IOException;
import java.util.Map;

import INF3132.items.Stats;
import INF3132.items.subclasses.PotionFactory;

public class PotionParser extends BaseParser<PotionFactory> {

    public PotionParser(String path) throws IOException {
        super(path);
    }

    @Override
    protected PotionFactory parseBlock(Map<String, String> blockData) {
        String name = blockData.get("Name");
        Stats statAffected = null;
        String statStr = blockData.get("StatAffected");

        try {
            statAffected = Stats.valueOf(statStr.toUpperCase());
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.err.println(String.format("Erreur lors de la création de la potion %s (StatAffected invalide)", name));
        }

        int itemPower = 0;
        String itemPowStr = blockData.get("ItemPower");

        try {
            itemPower = Integer.parseInt(itemPowStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println(String.format("Erreur lors de la création de la potion %s (ItemPower invalide)", name));
        }

        return new PotionFactory(name, itemPower, statAffected);
    }
}
