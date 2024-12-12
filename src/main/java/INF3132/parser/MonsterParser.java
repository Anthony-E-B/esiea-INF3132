package INF3132.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import INF3132.attacks.Attack;
import INF3132.monsters.Monster;
import INF3132.monsters.MonsterType;
import INF3132.monsters.subclasses.ElectricMonster;
import INF3132.monsters.subclasses.FireMonster;
import INF3132.monsters.subclasses.GrassMonster;
import INF3132.monsters.subclasses.GroundMonster;
import INF3132.monsters.subclasses.WaterMonster;
import INF3132.monsters.subclasses.InsectMonster;
import INF3132.monsters.subclasses.NormalMonster;
import INF3132.parser.exception.InvalidMonsterTypeException;

public class MonsterParser extends BaseParser<Monster>{
    
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
            e.printStackTrace();
            System.err.println(e);
        }

        int hpMin =         Integer.parseInt(blockData.get("HP").split(" ")[0]);
        int hpMax =         Integer.parseInt(blockData.get("HP").split(" ")[0]);
        int attackMin =     Integer.parseInt(blockData.get("Attack").split(" ")[0]);
        int attackMax =     Integer.parseInt(blockData.get("Attack").split(" ")[0]);
        int defenseMin =    Integer.parseInt(blockData.get("Defense").split(" ")[0]);
        int defenseMax =    Integer.parseInt(blockData.get("Defense").split(" ")[0]);
        int speedMin =      Integer.parseInt(blockData.get("Speed").split(" ")[0]);
        int speedMax =      Integer.parseInt(blockData.get("Speed").split(" ")[0]);

        double effect1 = 0.0;
        double effect2 = 0.0;

        List<Attack> attacks = new ArrayList<Attack>();

        switch (type) {
            case WATER:
            effect1 = Double.parseDouble(blockData.get("Flood"));
            effect2 = Double.parseDouble(blockData.get("Fall"));
            return new WaterMonster(
                name,
                getStat(hpMin, hpMax),
                getStat(attackMin, attackMax),
                getStat(defenseMin, defenseMax),
                getStat(speedMin, speedMax),
                attacks,
                effect1,
                effect2
            ); 
            case ELECTRIC:
            effect1 = Double.parseDouble(blockData.get("Paralysis"));
            return new ElectricMonster(
                name,
                getStat(hpMin, hpMax),
                getStat(attackMin, attackMax),
                getStat(defenseMin, defenseMax),
                getStat(speedMin, speedMax),
                attacks,
                effect1
            ); 
            case FIRE:
            return new FireMonster(
                name,
                getStat(hpMin, hpMax),
                getStat(attackMin, attackMax),
                getStat(defenseMin, defenseMax),
                getStat(speedMin, speedMax),
                attacks
            ); 
            case GROUND:
            return new GroundMonster(
                name,
                getStat(hpMin, hpMax),
                getStat(attackMin, attackMax),
                getStat(defenseMin, defenseMax),
                getStat(speedMin, speedMax),
                attacks
            ); 
            case INSECT:
            return new InsectMonster(
                name,
                getStat(hpMin, hpMax),
                getStat(attackMin, attackMax),
                getStat(defenseMin, defenseMax),
                getStat(speedMin, speedMax),
                attacks
            ); 
            case GRASS:
            return new GrassMonster(
                name,
                getStat(hpMin, hpMax),
                getStat(attackMin, attackMax),
                getStat(defenseMin, defenseMax),
                getStat(speedMin, speedMax),
                attacks
            ); 
            case NORMAL: 
            return new NormalMonster(
                name,
                getStat(hpMin, hpMax),
                getStat(attackMin, attackMax),
                getStat(defenseMin, defenseMax),
                getStat(speedMin, speedMax),
                attacks
            );
            default:
            break;
        }

        return t;
    }

    private int getStat(int statMin, int statMax){
        return (int)Math.round(Math.random()* (statMax - statMin +1) + statMin);
    }
}
