package INF3132.combat.negativestatus;

import INF3132.monsters.Monster;
import INF3132.combat.Combat;

public class Burned extends NegativeStatus {

	public Burned(Monster m, Combat c) {
		super(m, c);
	}

	@Override
	public void attackedHook(float damage) {
		this.getMonster().inflictDamage(
			Math.round(damage / 10)
		);
	}
}
