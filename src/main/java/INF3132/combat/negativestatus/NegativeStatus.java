package INF3132.combat.negativestatus;

import INF3132.monsters.Monster;
import INF3132.combat.Combat;

public abstract class NegativeStatus {

	private Monster monster;
	private Combat game;

	private int triggeredAtTurn;

	public NegativeStatus(Monster m, Combat g) {
		this.monster = m;
		this.game = g;

		this.triggeredAtTurn = g.getCurrentTurn();
	}

	/**
	 * A hook method that should be called at the start of each turn of any
	 * affected {@link Monster} on terrain.
	 */
	public void turnStartedHook() { }

	/**
	 * A hook method that should be called at the end of each turn of any
	 * affected {@link Monster} on terrain.
	 */
	public void turnEndedHook() { }

	/**
	 * A hook method that should be called each time the affected monster attacks.
	 */
	public void attackedHook(float a) { }

	/**
	 * Know if the current side effect should make the attack fail for this turn.
	 *
	 * @return {@code true} if the attack for this turn should fail,
	 *  {@code false} if not.
	 */
	public boolean triggersAttackFailure() {
		return false;
	}

	/**
	 * Know at which game turn the negative status appeared.
	 *
	 * @see Game
	 * @see Game.getCurrentTurn
	 */
	public int getTriggeredAtTurn() {
		return triggeredAtTurn;
	}


	// GETTERS

	public Monster getMonster() {
		return monster;
	}

	public Combat getGame() {
		return game;
	}
}

