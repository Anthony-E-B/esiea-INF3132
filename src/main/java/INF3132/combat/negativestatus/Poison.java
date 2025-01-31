package INF3132.combat.negativestatus;

import INF3132.monsters.Monster;
import INF3132.combat.Combat;

public class Poison extends NegativeStatus {

    public Poison(Monster m, Combat c) {
        super(m, c);
    }

    @Override
    public void turnStartedHook() {
        Combat c = getCombat();
        Monster m = getMonster();

        m.inflictDamage(
            Math.round(m.getAttack() / 10)
        );
        c.sendMessage(
            String.format("%s souffre du poison !", getMonster().getName())
        );

        if (c.getTerrain().isFlooded()) {
            c.sendMessage(String.format(
                "%s est guéri du poison par le terrain inondé.", m.getName()
            ));
            m.disposeNegativeStatus(this);
        }
    }
}
