package INF3132.monsters;

import INF3132.attacks.Attack;
import INF3132.attacks.exception.AttackFailedException;
import INF3132.items.exception.UnusableItemException;
import INF3132.items.subclasses.Potion;
import INF3132.items.Stats;
import INF3132.combat.negativestatus.NegativeStatus;

import java.util.List;
import java.util.ArrayList;

public abstract class Monster {
    private String name;

    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private int speed;

    private final List<Attack> attacks;
    private Status status;
    private final MonsterType type;

    private NegativeStatus negativeStatus;

    /**
     * A type this monster is weak against
     */
    private MonsterType weakType = null;

    /**
     * A type this monster is strong against.
     */
    private MonsterType strongType = null;

    public Monster(
        String name,
        MonsterType type,
        int maxHp,
        int attack,
        int defense,
        int speed
    ) {
        this(name, type, maxHp, attack, defense, speed, new ArrayList<Attack>());
    }

    public Monster(
        String name,
        MonsterType type,
        int maxHp,
        int attack,
        int defense,
        int speed,
        List<Attack> attacks
    ) {
        this(name, type, maxHp, attack, defense, speed, attacks, null);
    }

    public Monster(
        String name,
        MonsterType type,
        int maxHp,
        int attack,
        int defense,
        int speed,
        List<Attack> attacks,
        Status status
    ) {
        this.name = name;
        this.type = type;
        this.maxHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.attacks = attacks;
        this.status = status;
    }

    /**
     * Handle fist-fight attack from the monster {@code m}.
     *
     * @param m The attacker.
     * @return The inflicted damage, not rounded.
     */
    public float receiveAttack(Monster m) {
        float damage = 20 * (m.getAttack() / getDefense()) * Monster.getRandomCoef();

        if (m.getType() == weakType) damage *= 2;
        else if (m.getType() == strongType) damage /= 2;

        int roundedDamage = Math.round(damage);

        inflictDamage(roundedDamage);
        return damage;
    }

    /**
     * Handle attacks of different types.
     * Takes into account the type of the current Monster instance and the type
     * of the attack
     *
     * @param m The attacker.
     * @param a The attack to take damage from
     * @return The inflicted damage, not rounded.
     */
    public float receiveAttack(Monster m, Attack a) {
        float avantage;
        MonsterType attackType = a.getType();

        if (attackType == weakType)         avantage = 0.5f;
        else if (attackType == strongType)  avantage = 2.0f;
        else                                avantage = 1.0f;

        float damage = (
            (
                (11 * m.getAttack() * a.getPower()) / (25 * getDefense())
                + 2
            ) * avantage * getRandomCoef()
        );

        int roundedDamage = Math.round(damage);

        inflictDamage(roundedDamage);
        return damage;
    }

    /**
     * Inflicts damage to this monster up to 100% of its remaining health.
     */
    public void inflictDamage(int damage) {
        this.hp -= Math.min(hp, damage);
    }

    public static final float COEF_MIN = 0.85f;
    public static final float COEF_MAX = 1f;

    /**
     * @return a random coef between {@link COEF_MIN} and {@link COEF_MAX}.
     */
    public static float getRandomCoef() {
        return COEF_MIN + (float)Math.random() * (COEF_MAX - COEF_MIN);
    }

    public void attack(Monster target) {
        float inflictedDamage = target.receiveAttack(this);
        if (negativeStatus != null) negativeStatus.attackedHook(inflictedDamage);

        doAfterAttack();
    }

    public void attack(Monster target, Attack a) throws AttackFailedException {
        float inflictedDamage = target.receiveAttack(this, a);
        if (negativeStatus != null) negativeStatus.attackedHook(inflictedDamage);

        doAfterAttack();
    }

    public abstract void doAfterAttack();

    /**
     * Checks if the monster is able to fight.
     */
    public boolean isKO() {
        return hp <= 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    // Attacks
    public List<Attack> getAttacks() {
        return attacks;
    }

    // Status
    public Status getStatus() {
        return status;
    }

    // Type
    public MonsterType getType() {
        return type;
    }

    public void drinkPotion(Potion p) throws UnusableItemException {
        Stats stat = p.getStatAffected();
        int power = p.use(this);
        switch (stat) {
            case HP:
                this.hp = Math.min(this.hp + power, this.maxHp);
                break;
            case ATTACK:
                this.attack += power;
                break;
            case DEFENSE:
                this.defense += power;
                break;
            case SPEED:
                this.speed += power;
                break;
            default:
                throw new UnusableItemException();
        }
    }
}
