package INF3132.parser;

import java.io.IOException;
import java.util.Map;

import INF3132.items.Stats;
import INF3132.items.subclasses.Potion;

public class PotionParser extends BaseParser<Potion> {

    public PotionParser(String path) throws IOException {
        super(path);
    }

    @Override
    protected Potion parseBlock(Map<String, String> blockData){
        String name = blockData.get("Name");
        Stats statAffected = null;
        String statStr = blockData.get("StatAffected");
        try {
            statAffected = Stats.valueOf(statStr.toUpperCase());
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la création des objets (StatAffected invalide)");
        }
        int itemPower = 0;
        String itemPowStr = blockData.get("ItemPower");
        try {
            itemPower = Integer.parseInt(itemPowStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la création des objets (ItemPower invalide)");
        }
        return new Potion(name, itemPower, statAffected);
    }
}
