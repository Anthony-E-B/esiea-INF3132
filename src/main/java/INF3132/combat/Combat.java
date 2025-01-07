package INF3132.combat;

import INF3132.combat.terrain.Terrain;
import INF3132.events.EventPublisher;
import INF3132.trainer.Trainer;

public class Combat {
    private int currentTurn;
    private Terrain terrain;

    public EventPublisher<Integer> turnChanged;

    private static Combat currentCombat = null;

    public Trainer t1;
    public Trainer t2;

    public Combat(Trainer t1, Trainer t2) {
        this.terrain =      new Terrain();
        this.turnChanged =  new EventPublisher<Integer>();

        this.t1 = t1;
        this.t2 = t2;
    }

    // TODO: pour le mock, visibilité ou utilité de ce truc à revoir
    public void nextTurn() {
        turnChanged.notifyListeners(currentTurn);
    }

    public void start() {
        t1.giveUp.addListener(ve -> onGiveUp(t1));
        t2.giveUp.addListener(ve -> onGiveUp(t2));

        // try {
        // } catch (CombatLogicException e) {
        // }
    }

    /**
     * Handle one of the player giving up.
     * @param trainerGivingUp The trainer giving up combat.
     */
    public void onGiveUp(Trainer trainerGivingUp) {
        sendMessage(String.format("%s abandonne !", trainerGivingUp.getName()));
        if (trainerGivingUp == t1) setWinner(t2);
        else setWinner(t1);
    }

    /**
     * Defines the winner and ends combat.
     * @param t The trainer who has won the combat.
     */
    public void setWinner(Trainer t) {
        sendMessage(String.format("%s gagne le combat!", t.getName()), 4000);
        Combat.currentCombat = null;
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

    public static Combat initCombat(Trainer t1, Trainer t2) {
        Combat.currentCombat = new Combat(t1, t2);
        return Combat.currentCombat;
    }

    // Getters / Setters
    public static Combat getCurrentCombat() {
        return Combat.currentCombat;
    }

    public Terrain getTerrain() {
        return terrain;
    }
}
