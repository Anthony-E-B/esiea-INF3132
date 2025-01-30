package INF3132.combat.move;

import INF3132.trainer.Trainer;

public class GiveUpMove implements CombatMove {
    private Trainer trainer;

    public GiveUpMove(Trainer t) {
        this.trainer = t;
    }

	@Override
	public int getPriority() {
        return 1; // TODO:
	}

	@Override
	public void execute() {
        trainer.giveUp();
	}
}
