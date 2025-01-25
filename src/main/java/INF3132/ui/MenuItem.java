package INF3132.ui;

import INF3132.events.EventPublisher;
import INF3132.events.VoidEvent;

public class MenuItem {

    public EventPublisher<VoidEvent> activated;
    protected String label;
    protected Menu childMenu;

    public MenuItem(String label) {
        this(label, null);
    }

    public MenuItem(String label, Menu childMenu) {
        this.label = label;
        this.childMenu = childMenu;
        this.activated = new EventPublisher<VoidEvent>();
    }

    public void trigger() {
        if (childMenu != null) {
            childMenu.prompt();
        }
        activated.notifyListeners(new VoidEvent());
    }

    public String getLabel() {
        return label;
    }
}

