package com.eric.mooncoin.services;

/**
 *
 */
public interface IJob<T> {
    int maxJobCount();

    int jobWindow();

    T run();

    default String jobClass() {
        return this.getClass().getSimpleName();
    }
}
