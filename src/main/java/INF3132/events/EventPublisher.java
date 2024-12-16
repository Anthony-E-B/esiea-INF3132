package INF3132.events;

import java.util.ArrayList;
import java.util.List;

public class EventPublisher<T> {
    private final List<EventListener<T>> listeners = new ArrayList<>();

    // Subscribe to the event
    public void addListener(EventListener<T> listener) {
        listeners.add(listener);
    }

    // Unsubscribe from the event
    public void removeListener(EventListener<T> listener) {
        listeners.remove(listener);
    }

    // Notify all listeners about the event
    public void notifyListeners(T eventData) {
        for (EventListener<T> listener : listeners) {
            listener.onEvent(eventData);
        }
    }
}
