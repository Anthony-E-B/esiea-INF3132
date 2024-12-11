package INF3132.parser;

import java.io.IOException;
import java.util.Map;

import INF3132.monsters.MonsterType;
import INF3132.parser.exception.InvalidMonsterTypeException;

public class MonsterParser<Monster> extends BaseParser<Monster>{
    
    public MonsterParser(String path) throws IOException {
        super(path);    
    }

    @Override
    protected Monster parseBlock(Map<String, String> blockData){
        Monster t; 
        t = null;
        String name = blockData.get("Name");
        MonsterType type = MonsterType.NORMAL;
        try {
            type = BaseParser.parseMonsterType(blockData.get("Type"));
        } catch (InvalidMonsterTypeException e) {
            System.out.println("!!");
        }
        int hpMin = Integer.parseInt(blockData.get("HP").split(" ")[0]);
        int hpMax = Integer.parseInt(blockData.get("HP").split(" ")[0]);
        int attackMin = Integer.parseInt(blockData.get("Attack").split(" ")[0]);
        int attackMax = Integer.parseInt(blockData.get("Attack").split(" ")[0]);
        int defenseMin = Integer.parseInt(blockData.get("Defense").split(" ")[0]);
        int defenseMax = Integer.parseInt(blockData.get("Defense").split(" ")[0]);
        int speedMin = Integer.parseInt(blockData.get("Speed").split(" ")[0]);
        int speedMax = Integer.parseInt(blockData.get("Speed").split(" ")[0]);
        double effect1 = 0.0;
        double effect2 = 0.0;

        switch (type) {
            case WATER:
                effect1 = Double.parseDouble(blockData.get("Flood"));
                effect2 = Double.parseDouble(blockData.get("Fall"));
                break;
            case ELECTRIC:
                effect1 = Double.parseDouble(blockData.get("Paralysis"));
                break;
            default: break;
        }

        // return new Monster(name, type, getStat(hpMin, hpMax), getStat(attackMin, attackMax), getStat(defenseMin, defenseMax), getStat(speedMin, speedMax), effect1, effect2)
        return t;
    }

    private int getStat(int statMin, int statMax){
        return (int)Math.round(Math.random()* (statMax - statMin +1) + statMin);
    }
}
