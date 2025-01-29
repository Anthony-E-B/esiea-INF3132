package INF3132.monsters;

import INF3132.attacks.AttackType;

public enum MonsterType {
    NORMAL,     // Normal
    FIRE,       // Feu
    WATER,      // Eau
    GRASS,      // Plante
    GROUND,     // Terre
    INSECT,     // Insecte
    ELECTRIC;   // Foudre

    public boolean equalsType(AttackType attackType) {
        if(attackType == null || attackType.name() == null) return false;
        return this.name().equals(attackType.name());
    }
}
