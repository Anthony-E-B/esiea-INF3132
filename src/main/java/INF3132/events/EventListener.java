package INF3132.events;

@FunctionalInterface
public interface EventListener<T> {
    void onEvent(T eventData);
}
