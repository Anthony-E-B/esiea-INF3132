package INF3132.combat;

import INF3132.combat.terrain.Terrain;
import INF3132.events.EventPublisher;

public class Combat {
    private int currentTurn;
    private Terrain terrain;

    public EventPublisher<Integer> turnChanged;

    private static Combat currentCombat = null;

    public Combat() {
        this.terrain =      new Terrain();
        this.turnChanged =  new EventPublisher<Integer>();
    }

    // TODO: pour le mock, visibilité ou utilité de ce truc à revoir
    public void nextTurn() {
        turnChanged.notifyListeners(currentTurn);
    }

    public void start() {
        Combat.currentCombat = this;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public void sendMessage(String message) {
        // TODO: Calculer un TTL selon la taille du message.
        this.sendMessage(message, 2000); // WARN: Constante Temporaire
    }

    /**
     * Show a status message for the fight.
     */
    public void sendMessage(String message, int ttl) {
        // TODO:
    }

    // Getters / Setters

    public static Combat getCurrentCombat() {
        return Combat.currentCombat;
    }

    public Terrain getTerrain() {
        return terrain;
    }
}
