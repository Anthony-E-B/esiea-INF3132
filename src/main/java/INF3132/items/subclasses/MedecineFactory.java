package INF3132.items.subclasses;

import INF3132.monsters.Status;

public class MedecineFactory {
    private String name;
    private Status status;

    public MedecineFactory(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public Medecine create() {
        return new Medecine(name, status);
    }
}
