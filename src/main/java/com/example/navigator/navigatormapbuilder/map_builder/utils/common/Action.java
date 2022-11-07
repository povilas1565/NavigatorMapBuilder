package com.example.navigator.navigatormapbuilder.map_builder.utils.common;

@FunctionalInterface
public interface Action {
    void execute();

    default void andThen(Action action){}
}
