package INF3132.items.subclasses;

import INF3132.items.exception.UnusableItemException;
import INF3132.monsters.Monster;
import INF3132.monsters.Status;

public class Medecine extends Consumable {
    private Status status;

    public Medecine(String name, Status status){
        super(name);
        this.status = status;
    }

    @Override
    public int use(Monster m) throws UnusableItemException {
        if(checkIfUsable(m)){
            this.setUsed(true);
            return 1;
        } else throw new UnusableItemException();
    }

    @Override
    public boolean checkIfUsable(Monster m) {
        if(m.getStatus().equals(this.status)) return true;
        return false;
    }

    public Status getStatus(){
        return this.status;
    }
}
