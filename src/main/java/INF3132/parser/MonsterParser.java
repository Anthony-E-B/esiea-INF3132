package INF3132.parser;

import java.io.IOException;
import java.util.Map;


// @TODO Remplacer les 'T' par la classe Monster

public class MonsterParser<T> extends BaseParser<T>{
    
    public MonsterParser(String path) throws IOException {
        super(path);    
    }

    @Override
    protected T parseBlock(Map<String, String> blockData){
        T t; 
        t = null;
        String name = blockData.get("Name");
        String type = blockData.get("Type"); // @TODO Remplacer String par Type
        int hpMin = Integer.parseInt(blockData.get("HP").split(" ")[0]);
        int hpMax = Integer.parseInt(blockData.get("HP").split(" ")[0]);
        int attackMin = Integer.parseInt(blockData.get("Attack").split(" ")[0]);
        int attackMax = Integer.parseInt(blockData.get("Attack").split(" ")[0]);
        int defenseMin = Integer.parseInt(blockData.get("Defense").split(" ")[0]);
        int defenseMax = Integer.parseInt(blockData.get("Defense").split(" ")[0]);
        int speedMin = Integer.parseInt(blockData.get("Speed").split(" ")[0]);
        int speedMax = Integer.parseInt(blockData.get("Speed").split(" ")[0]);

        // @TODO En fonction du type, récupérer un ou deux champs Effect pour la capacité spéciale

        // return new Monster(name, type, getStat(hpMin, hpMax), getStat(attackMin, attackMax), getStat(defenseMin, defenseMax), getStat(speedMin, speedMax), effect1, effect2)
        return t;
    }

    private int getStat(int statMin, int statMax){
        return (int)Math.round(Math.random()* (statMax - statMin +1) + statMin);
    }
}
