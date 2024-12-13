package INF3132.combat;

public class Combat {
    // TODO: Compléter

    private int currentTurn;

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public void sendMessage(String message) {
        // TODO: Calculer un TTL selon la taille du message.
        this.sendMessage(message, 8000); // WARN: Censtante Temporaire
    }

    /**
     * Show a status message for the fight.
     */
    public void sendMessage(String message, int ttl) {
        // TODO:
    }
}
