package INF3132.parser;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import INF3132.monsters.MonsterFactory;
import INF3132.monsters.MonsterType;
import INF3132.parser.exception.InvalidMonsterTypeException;

public class MonsterParser extends BaseParser<MonsterFactory>{

    public MonsterParser(String path) throws IOException {
        super(path);
    }

    @Override
    protected MonsterFactory parseBlock(Map<String, String> blockData) {
        String name = blockData.get("Name");
        MonsterType type = MonsterType.NORMAL;

        try {
            type = BaseParser.parseMonsterType(blockData.get("Type"));
        } catch (InvalidMonsterTypeException e) {
            e.printStackTrace();
            System.err.println(e);
        }

        int minHp =         Integer.parseInt(blockData.get("HP").split(" ")[0]);
        int maxHp =         Integer.parseInt(blockData.get("HP").split(" ")[0]);
        int minAttack =     Integer.parseInt(blockData.get("Attack").split(" ")[0]);
        int maxAttack =     Integer.parseInt(blockData.get("Attack").split(" ")[0]);
        int minDefense =    Integer.parseInt(blockData.get("Defense").split(" ")[0]);
        int maxDefense =    Integer.parseInt(blockData.get("Defense").split(" ")[0]);
        int minSpeed =      Integer.parseInt(blockData.get("Speed").split(" ")[0]);
        int maxSpeed =      Integer.parseInt(blockData.get("Speed").split(" ")[0]);

        HashMap<String, Object> additionalProperties = new HashMap<>();

        switch (type) {
            case WATER:
            additionalProperties.put("flood", Float.parseFloat(blockData.get("Flood")));
            additionalProperties.put("fall", Float.parseFloat(blockData.get("Fall")));
            break;
            case ELECTRIC:
            additionalProperties.put("paralysis", Float.parseFloat(blockData.get("Paralysis")));
            break;
            default:
            break;
        }

        return new MonsterFactory(
            name,
            type,
            minHp,
            maxHp,
            minAttack,
            maxAttack,
            minDefense,
            maxDefense,
            minSpeed,
            maxSpeed,
            additionalProperties
        );
    }
}
