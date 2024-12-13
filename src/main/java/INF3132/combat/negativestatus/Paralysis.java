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
        // 1/4 odds of failing.
        if (Math.random() < 0.25) throw new AttackFailedException();
    }
}
