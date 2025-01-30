
package INF3132.combat.move;

import INF3132.monsters.Monster;
import INF3132.trainer.Trainer;

public class SwapMonsterMove implements CombatMove {
    private Trainer trainer;
    private Monster monsterToSwitchTo;

    public SwapMonsterMove(Trainer t, Monster monsterToSwitchTo) {
        this.trainer = t;
        this.monsterToSwitchTo = monsterToSwitchTo;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public void execute() {
        try {
            trainer.swapCurrentFightingMonster(monsterToSwitchTo);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
