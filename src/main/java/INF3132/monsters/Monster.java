package INF3132.monsters;

import INF3132.attacks.Attack;
import INF3132.attacks.AttackType;
import INF3132.attacks.exception.AttackFailedException;
import INF3132.attacks.exception.SlippedAndFailedException;
import INF3132.items.exception.UnusableItemException;
import INF3132.items.subclasses.Medecine;
import INF3132.items.subclasses.Potion;
import INF3132.monsters.subclasses.WaterMonster;
import INF3132.combat.Combat;
import INF3132.combat.negativestatus.Burned;
import INF3132.combat.negativestatus.NegativeStatus;
import INF3132.combat.negativestatus.Paralysis;
import INF3132.combat.negativestatus.Poison;
import INF3132.combat.terrain.Terrain;
import INF3132.events.EventPublisher;
import INF3132.events.VoidEvent;

import java.util.List;
import java.util.ArrayList;

public abstract class Monster {

    public EventPublisher<VoidEvent> died;

    private String name;

    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private int speed;

    private final List<Attack> attacks;
    private final MonsterType type;

    private NegativeStatus negativeStatus;

    /**
     * A type this monster is weak against
     */
    private AttackType weakType = null;

    /**
     * A type this monster is strong against.
     */
    private AttackType strongType = null;

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
        this.name = name;
        this.type = type;
        this.maxHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.attacks = attacks;
        this.hp = maxHp;

        this.died = new EventPublisher<>();
    }

    /**
     * Handle fist-fight attack from the monster {@code m}.
     *
     * @param m The attacker.
     * @return The inflicted damage, not rounded.
     */
    public int receiveAttack(Monster m) {
        float damage = 20 * (m.getAttack() / getDefense()) * Monster.getRandomCoef();
        Combat combat = Combat.getCurrentCombat();

        if (m.getType().equalsType(weakType)) {
            damage *= 2;
            combat.sendMessage("C'est super efficace !");
        }
        else if (m.getType().equalsType(strongType)) {
            damage /= 2;
            combat.sendMessage("Ce n'est pas très efficace...");
        }

        int roundedDamage = Math.round(damage);

        inflictDamage(roundedDamage);
        return roundedDamage;
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
    public int receiveAttack(Monster m, Attack a) {
        float avantage;

        AttackType attackType = a.getType();
        Combat combat = Combat.getCurrentCombat();

        if (attackType == weakType) {
            avantage = 0.5f;
            combat.sendMessage("Ce n'est pas très efficace...");
        }
        else if (attackType == strongType) {
            avantage = 2.0f;
            combat.sendMessage("C'est super efficace !");
        }
        else {
            avantage = 1.0f;
        }

        float damage = (
            ((11 * m.getAttack() * a.getPower()) / (25 * getDefense()) + 2)
            * avantage * getRandomCoef()
        );

        int roundedDamage = Math.round(damage);

        inflictDamage(roundedDamage);
        return roundedDamage;
    }

    public void turnStartedHook() {
        // This functions is intented to be overridden for handling of special behaviours
    }

    /**
     * Inflicts damage to this monster up to 100% of its remaining health.
     */
    public void inflictDamage(int damage) {
        setHp(getHp() - Math.min(hp, damage));
        if (getHp() == 0) died.notifyListeners(new VoidEvent());
    }

    /**
     * Restores health up to the maximum amount.
     */
    public void restoreHealth(int power) {
        setHp(Math.min(getMaxHp(), getHp() + power));
    }

    /**
     * Increase the attack points.
     * @implNote uncapped.
     */
    public void improveAttack(int amount) {
        attack += amount;
    }

    /**
     * Increase the attack points.
     * @implNote uncapped.
     */
    public void improveDefense(int amount) {
        defense += amount;
    }

    /**
     * Increase the attack points.
     * @implNote uncapped.
     */
    public void improveSpeed(int amount) {
        speed += amount;
    }

    public static final float COEF_MIN = 0.85f;
    public static final float COEF_MAX = 1f;

    /**
     * @return a random coef between {@link COEF_MIN} and {@link COEF_MAX}.
     */
    public static float getRandomCoef() {
        return COEF_MIN + (float)Math.random() * (COEF_MAX - COEF_MIN);
    }

    public int attack(Monster target) throws AttackFailedException, SlippedAndFailedException {
        beforeAttack();
        int inflictedDamage = target.receiveAttack(this);
        afterAttack(inflictedDamage);
        return inflictedDamage;
    }

    public int attack(Monster target, Attack a) throws AttackFailedException, SlippedAndFailedException {
        beforeAttack();
        int inflictedDamage = target.receiveAttack(this, a);
        afterAttack(inflictedDamage, a);
        return inflictedDamage;
    }

    /**
     * Hook to be called before each attack.
     * @implNote If you override this method, always call {@code super.beforeAttack()} at the top of the override method.
     */
    protected void beforeAttack() throws AttackFailedException {
        if (negativeStatus != null) negativeStatus.beforeAttackHook();

        if (!(this instanceof FloodAffectedMonster)) return;

        Combat c = Combat.getCurrentCombat();
        Terrain t = c.getTerrain();
        if (!t.isFlooded()) return;

        WaterMonster flooder = t.getFlooder();
        // NOTE: If there are no original flooder (e.g. rainy biome), monsters have 1/2 odds of falling due to the flooded terrain. Should rarely happen.
        float odds = flooder == null ? .5f : flooder.getFall();

        if ((float)Math.random() <= odds) {
            c.sendMessage(String.format(
                "%s glisse et rate son attaque !",
                getName()
            ));
            throw new AttackFailedException();
        }
    }

    /**
     * Hook to be called after each attack.
     * @implNote If you override this method, always call {@code super.beforeAttack()} at the top of the override method.
     */
    protected void afterAttack(float inflictedDamage) {
        this.afterAttack(inflictedDamage, null);
    }

    protected void afterAttack(float inflictedDamage, Attack a) {
        if (negativeStatus != null) negativeStatus.afterAttackHook(inflictedDamage);
    }

    public void disposeNegativeStatus(NegativeStatus negativeStatus) {
        if (this.negativeStatus == negativeStatus) {
            this.negativeStatus = null;
        }
    }

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

    protected void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    protected void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    // Attack : protected set

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    // Attacks
    public List<Attack> getAttacks() {
        return attacks;
    }

    // Status
    public Status getStatus() {
        if (negativeStatus instanceof Poison)       return Status.POISON;
        if (negativeStatus instanceof Burned)       return Status.BURNED;
        if (negativeStatus instanceof Paralysis)    return Status.BURNED;
        return null;
    }

    public NegativeStatus getNegativeStatus(){
        return this.negativeStatus;
    }

    /**
     * Try and set the negative status to a new value.
     * This will only work if the {@link Monster} is not currently affected by a negative status.
     * @see NegativeStatus
     */
    public void trySetNegativeStatus(NegativeStatus ns) {
        if (this.negativeStatus == null) this.negativeStatus = ns;
        return;
    }

    /**
     * Reset negative status with an appropriate medecine
     */
    public void resetNegativeStatus(Medecine medecine) {
        if (medecine.getStatus() == getStatus()) {
            this.negativeStatus = null;
        }
    }

    // Type
    public MonsterType getType() {
        return type;
    }

    public void drinkPotion(Potion p) throws UnusableItemException {
        p.use(this);
    }

    public String getSummary() {
        String summary = String.format("%s (Type %s, santé : %d/%d", getName(), getType().toString(), getHp(), getMaxHp());
        summary += getStatus() != null ? String.format(" %s)", getStatus().toString()) : ")";
        return summary;
    }
}
