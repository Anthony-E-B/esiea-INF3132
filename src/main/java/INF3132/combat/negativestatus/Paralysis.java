package INF3132.combat.negativestatus;

import INF3132.attacks.exception.AttackFailedException;
import INF3132.combat.Combat;
import INF3132.monsters.Monster;

public class Paralysis extends NegativeStatus {

    public Paralysis(Monster m, Combat c) {
        super(m, c);
    }

    @Override
    public void beforeAttackHook() throws AttackFailedException {
        // 3/4 odds of failing.
        if (Math.random() < 0.75) throw new AttackFailedException();
    }

    public void turnEndedHook() {
        int nbTurnsParalised = getCombat().getCurrentTurn() - getTriggeredAtTurn();

        // NOTE: the increasing chance of getting out of that state,
        // reaching 100% starting from turn 6 of being paralised
        if (Math.random() < ((1 * nbTurnsParalised) / 6.0)) {
            getCombat().sendMessage(
                String.format("%s n'est plus paralysé.", getMonster().getName())
            );

            getMonster().disposeNegativeStatus(this);
            return;
        }
    }
}
