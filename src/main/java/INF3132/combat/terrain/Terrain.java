package INF3132.combat.terrain;

import INF3132.combat.Combat;
import INF3132.events.VoidEvent;
import INF3132.monsters.subclasses.WaterMonster;

public class Terrain {
    private int floodedRemainingTurns = 0;
    private WaterMonster flooder = null;

    private static final int FLOOD_MIN_TURNS = 1;
    private static final int FLOOD_MAX_TURNS = 3;

    private final Combat combat;

    public Terrain(Combat combat) {
        this.combat = combat;
        combat.turnChanged.addListener(t -> onTurnChanged(t));
    }

    public void onTurnChanged(Integer turn) {
        if (floodedRemainingTurns > 0) {
            floodedRemainingTurns -= 1;
        } else {
            return;
        }

        if (floodedRemainingTurns == 0) {
            notifyTerrainNotFloodedAnymore();
            flooder.died.removeListener(this::onFlooderDied);
        }
    }

    public boolean isFlooded() {
        return floodedRemainingTurns > 0;
    }

    /**
     * @return If the terrain is flooded, the {@link WaterMonster} responsible for the flood, null otherwise of if there are no flooder.
     */
    public WaterMonster getFlooder() {
        return this.flooder;
    }

    public void flood(WaterMonster flooder) {
        int floodTerrainFor = getRandomNumberOfFloodingTurns();
        this.flooder = flooder;
        if (floodTerrainFor < floodedRemainingTurns) return;

        floodedRemainingTurns = floodTerrainFor;
        combat.sendMessage("%s a inondé le terrain!");
        flooder.died.addListener(this::onFlooderDied);
    }

    public void onFlooderDied(VoidEvent ve) {
        floodedRemainingTurns = 0;
        notifyTerrainNotFloodedAnymore();
    }

    private void notifyTerrainNotFloodedAnymore() {
        combat.sendMessage("Le terrain n'est plus inondé!");
    }

    /**
     * Get a random number between
     */
    private static int getRandomNumberOfFloodingTurns() {
        return 1 + (int) Math.floor(
            Math.random() * (FLOOD_MAX_TURNS - FLOOD_MIN_TURNS)
        );
    }
}
