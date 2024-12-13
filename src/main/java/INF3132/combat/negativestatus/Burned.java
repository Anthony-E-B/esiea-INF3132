package INF3132.combat.negativestatus;

import INF3132.monsters.Monster;
import INF3132.combat.Combat;

public class Burned extends NegativeStatus {

    public Burned(Monster m, Combat c) {
        super(m, c);
    }

    @Override
    public void turnStartedHook() {
        getMonster().inflictDamage(
            Math.round(getMonster().getAttack() / 10)
        );
        getCombat().sendMessage(
            String.format("%s souffre de sa brûlure !", getMonster().getName())
        );
    }
}
