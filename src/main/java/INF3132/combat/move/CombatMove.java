
package INF3132.combat.move;

public interface CombatMove {

    /**
     * Get the priority of the move
     */
    int getPriority();

    /**
     * Execute the move
     */
    void execute();
}
