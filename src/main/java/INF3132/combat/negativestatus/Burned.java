package INF3132.combat.negativestatus;

import INF3132.monsters.Monster;
import INF3132.combat.Combat;

public class Burned extends NegativeStatus {

    public Burned(Monster m, Combat c) {
        super(m, c);
    }

    @Override
    public void turnStartedHook() {
        Monster m = getMonster();
        Combat c = getCombat();

        m.inflictDamage(Math.round(m.getAttack() / 10));

        c.sendMessage(String.format(
            "%s souffre de sa brûlure !", m.getName()
        ));

        if (c.getTerrain().isFlooded()) {
            c.sendMessage(String.format(
                "%s n'est plus brûlé grâce à l'inondation du terrain.", m.getName()
            ));
            m.disposeNegativeStatus(this);
        }

    }
}
