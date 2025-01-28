package INF3132.ui;

import INF3132.events.EventPublisher;
import INF3132.events.VoidEvent;
import java.lang.Runnable;

public class MenuItem {

    public EventPublisher<VoidEvent> activated;
    protected String label;
    protected Menu childMenu;
    protected Runnable callback;

    public MenuItem(String label) {
        // NOTE: Other constructors are ambiguous : can't call this(label, null)
        this.label = label;
        this.childMenu = null;
        this.activated = new EventPublisher<VoidEvent>();
        this.callback = null;
    }

    public MenuItem(String label, Menu childMenu) {
        this.label = label;
        this.childMenu = childMenu;
        this.activated = new EventPublisher<VoidEvent>();
        this.callback = null;
    }

    public MenuItem(String label, Runnable callback) {
        this.label = label;
        this.childMenu = null;
        this.activated = new EventPublisher<VoidEvent>();
        this.callback = callback;
    }

    public void trigger() {
        if (childMenu != null) {
            childMenu.prompt();
        } else if (callback != null) {
            callback.run();
        }

        activated.notifyListeners(new VoidEvent());
    }

    public String getLabel() {
        return label;
    }
}

