package INF3132.monsters;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import INF3132.monsters.subclasses.*;
import INF3132.parser.exception.UnhandledMonsterTypeException;
import INF3132.attacks.Attack;
import INF3132.attacks.AttackFactory;
import INF3132.attacks.AttackType;

public class MonsterFactory {

    private String name;
    private MonsterType type;
    private Map<String, Object> additionalProperties;
    private int minHp;
    private int maxHp;
    private int minAttack;
    private int maxAttack;
    private int minDefense;
    private int maxDefense;
    private int minSpeed;
    private int maxSpeed;

    public MonsterFactory(
        String name,
        MonsterType type,
        int minHp,
        int maxHp,
        int minAttack,
        int maxAttack,
        int minDefense,
        int maxDefense,
        int minSpeed,
        int maxSpeed,
        Map<String, Object> additionalProperties
    ) {
        this.name = name;
        this.type = type;
        this.minHp = minHp;
        this.maxHp = maxHp;
        this.minAttack = minAttack;
        this.maxAttack = maxAttack;
        this.minDefense = minDefense;
        this.maxDefense = maxDefense;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.additionalProperties = additionalProperties;
    }

    /**
     * Return a subtype of {@link Monster} according to the {@code type} field.
     * @see Monster.getType
     */
    public Monster create(List<AttackFactory> attackList) throws UnhandledMonsterTypeException {
        int hp = getStat(minHp, maxHp);
        int attack = getStat(minAttack, maxAttack);
        int defense = getStat(minDefense, maxDefense);
        int speed = getStat(minSpeed, maxSpeed);

        ArrayList<Attack> attacks = generateMoveset(type, attackList);

        switch (type) {
            case INSECT:
            return new InsectMonster(name, hp, attack, defense, speed, attacks);
            case ELECTRIC:
            return new ElectricMonster(name, hp, attack, defense, speed, attacks, (float)additionalProperties.get("paralysis"));
            case FIRE:
            return new FireMonster(name, hp, attack, defense, speed, attacks);
            case GRASS:
            return new GrassMonster(name, hp, attack, defense, speed, attacks);
            case GROUND:
            return new GroundMonster(name, hp, attack, defense, speed, attacks);
            case NORMAL:
            return new NormalMonster(name, hp, attack, defense, speed, attacks);
            case WATER:
            return new WaterMonster(name, hp, attack, defense, speed, attacks, (float)additionalProperties.get("flood"), (float)additionalProperties.get("fall"));
            default:
            throw new UnhandledMonsterTypeException();
        }
    }

    /**
     * Generate a list of {@link Attack} for the monster. 3 attacks of the same type and one normal attack.
     * @param type The type of the monster.
     * @param attackList The list of all available attacks.
     * @return A list of 4 attacks.
     */
    private ArrayList<Attack> generateMoveset(MonsterType type, List<AttackFactory> attackList){
        if (attackList == null || attackList.isEmpty()) {
            throw new IllegalArgumentException("La liste d'attaques ne peut pas être vide.");
        }
        ArrayList<Attack> attacks = new ArrayList<>();
        ArrayList<Attack> attacksOfSameType = new ArrayList<>();
        AttackType typeCheck;
        if(type == MonsterType.INSECT || type == MonsterType.GRASS){
            typeCheck = AttackType.NATURE;
        } else {
            typeCheck = AttackType.valueOf(type.toString());
        }

        // Logic is adding 3 attacks of the same type, and one normal move
        for (AttackFactory a : attackList){
            if (a.getType() == typeCheck) attacksOfSameType.add(a.create());
        }

        int specialAttackPoolSize = 3;
        if (attacksOfSameType.size() < specialAttackPoolSize) specialAttackPoolSize = attacksOfSameType.size();
        for (int i = 0; i < specialAttackPoolSize; i++){
            int index = (int)Math.floor(Math.random() * attacksOfSameType.size());
            attacks.add(attacksOfSameType.get(index));
            attacksOfSameType.remove(index);
        }

        ArrayList<Attack> normalAttacks = new ArrayList<>();
        for (AttackFactory a : attackList){
            if (a.getType() == AttackType.NORMAL) normalAttacks.add(a.create());
        }

        for (int j = 0; j < 4 - specialAttackPoolSize; j++){
            int index = (int)Math.floor(Math.random() * normalAttacks.size());
            attacks.add(normalAttacks.get(index));
            normalAttacks.remove(index);
        }

        return attacks;
    }

    private static int getStat(int statMin, int statMax) {
        return (int)Math.round(Math.random() * (statMax - statMin + 1) + statMin);
    }

    public String getName() {
        return name;
    }

    public MonsterType getType() {
        return type;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public int getMinHp() {
        return minHp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getMinAttack() {
        return minAttack;
    }

    public int getMaxAttack() {
        return maxAttack;
    }

    public int getMinDefense() {
        return minDefense;
    }

    public int getMaxDefense() {
        return maxDefense;
    }

    public int getMinSpeed() {
        return minSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }
}
