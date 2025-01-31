package INF3132.items.subclasses;

import INF3132.items.exception.UnusableItemException;
import INF3132.monsters.Monster;
import INF3132.monsters.Status;

public class Medecine extends Consumable {
    private Status status;

    public Medecine(String name, Status status) {
        super(name);
        this.status = status;
    }

    @Override
    public void use(Monster m) throws UnusableItemException {
        if (checkIfUsable(m)){
            m.resetNegativeStatus(this);
            setUsed(true);
        } else throw new UnusableItemException();
    }

    @Override
    public boolean checkIfUsable(Monster m) {
        return m.getStatus() != null && m.getStatus().equals(this.status);
    }

    public Status getStatus(){
        return this.status;
    }
}
