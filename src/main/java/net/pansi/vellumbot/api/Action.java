package net.pansi.vellumbot.api;

public interface Action<T> {

    void run(T t);

}
