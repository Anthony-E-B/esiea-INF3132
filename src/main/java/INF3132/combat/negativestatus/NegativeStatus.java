package INF3132.combat.negativestatus;

import INF3132.monsters.Monster;
import INF3132.attacks.exception.AttackFailedException;
import INF3132.combat.Combat;

public abstract class NegativeStatus {

    private Monster monster;
    private Combat combat;

    private int triggeredAtTurn;

    public NegativeStatus(Monster m, Combat c) {
        this.monster = m;
        this.combat = c;

        this.triggeredAtTurn = c.getCurrentTurn();
    }

    /**
     * A hook method that should be called at the start of each turn of any
     * affected {@link Monster} on terrain.
     */
    public void turnStartedHook() { }

    /**
     * A hook method that should be called at the end of each turn of any
     * affected {@link Monster} on terrain.
     */
    public void turnEndedHook() { }

    /**
     * A hook method to trigger before doing an attack.
     */
    public void beforeAttackHook() throws AttackFailedException { }

    /**
     * A hook method that should be called each time the affected monster attacks.
     *
     * @param inflictedDamage The damage inflicted to the opponent when attacking.
     */
    public void afterAttackHook(float inflictedDamage) { }

    /**
     * Know at which game turn the negative status appeared.
     *
     * @see Game
     * @see Game.getCurrentTurn
     */
    public int getTriggeredAtTurn() {
        return triggeredAtTurn;
    }


    // GETTERS

    public Monster getMonster() {
        return monster;
    }

    public Combat getCombat() {
        return combat;
    }
}

