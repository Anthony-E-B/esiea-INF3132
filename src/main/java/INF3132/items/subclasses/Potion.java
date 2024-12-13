package INF3132.items.subclasses;

import INF3132.items.exception.ItemException;
import INF3132.monsters.Monster;

public class Potion extends Consummable {
    private int itemPower;
    private String statAffected;

    public Potion(String name, int itemPower, String statAffected){
        super(name);    
        this.itemPower = itemPower;
        this.statAffected = statAffected;
    }

    @Override
    public int use(Monster m) throws ItemException{
        // TODO Implémenter un fonctionnement supplémentaire
        if(checkIfUsable(m)){
            this.setUsed(true);
            return this.itemPower;
        } else throw new ItemException();
    }

    @Override
    public boolean checkIfUsable(Monster m) {
        // TODO Implémenter une vérification pour savoir si l'objet est utilisable en l'état ou non
        return true;
    }

    public String getStatAffected() {
        return statAffected;
    }
}
