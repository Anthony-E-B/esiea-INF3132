package INF3132.parser;

import java.io.IOException;
import java.util.Map;

import INF3132.attacks.AttackFactory;
import INF3132.monsters.MonsterType;
import INF3132.parser.exception.InvalidMonsterTypeException;

public class AttackParser extends BaseParser<AttackFactory> {

    public AttackParser(String path) throws IOException {
        super(path);
    }

    @Override
    protected AttackFactory parseBlock(Map<String, String> blockData) {
        String name = blockData.get("Name");
        MonsterType type = MonsterType.NORMAL;

        try {
            type = BaseParser.parseMonsterType(blockData.get("Type"));
        } catch (InvalidMonsterTypeException e) {
            e.printStackTrace();
            System.err.println(e);
        }

        int pwr =       Integer.parseInt(blockData.get("Power"));
        int nbUse =     Integer.parseInt(blockData.get("NbUse"));
        float fail =    Float.parseFloat(blockData.get("Fail"));

        return new AttackFactory(
            name,
            pwr,
            nbUse,
            fail,
            type
        );
    }
}
