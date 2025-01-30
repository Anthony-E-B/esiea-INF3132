
package INF3132.combat.move;

import INF3132.monsters.Monster;

public interface CombatMove {

    /**
     * Get the priority of the move
     */
    int getPriority();

    /**
     * Execute the move
     */
    void execute();

    default Monster getAttacker() {
        return null;
    }
    default Monster getTarget() {
        return null;
    }
}
