package dev.ctrlneo.playerutils.client.event;

public interface Event<T> {
    T invoker();
    void register(T listener);
}
