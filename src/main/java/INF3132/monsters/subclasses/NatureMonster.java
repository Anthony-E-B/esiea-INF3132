package INF3132.monsters.subclasses;

import java.util.List;

import INF3132.attacks.Attack;
import INF3132.combat.Combat;
import INF3132.combat.terrain.Terrain;
import INF3132.monsters.FloodAffectedMonster;
import INF3132.monsters.Monster;
import INF3132.monsters.MonsterType;
import INF3132.monsters.Status;

public abstract class NatureMonster extends Monster implements FloodAffectedMonster {

    public NatureMonster(
        String name,
        MonsterType type,
        int hpMax,
        int attack,
        int defense,
        int speed,
        List<Attack> attacks
    ) {
        super(name, type, hpMax, attack, defense, speed, attacks);
    }

    @Override
    public void turnStartedHook() {
        Combat c = Combat.getCurrentCombat();
        Terrain t = c.getTerrain();

        if (t.isFlooded()) {
            c.sendMessage(String.format(
                "%s se regénère grâce au terrain inondé !",
                getName()
            ));

            restoreHealth(this.getMaxHp()/20);
        }
    }
}
