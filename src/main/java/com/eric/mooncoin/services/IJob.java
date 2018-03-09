package com.eric.mooncoin.services;

/**
 *
 */
public interface IJob<T> {
    int maxJobCount();

    int jobWindow();

    default int retryTime() {
        return 0;
    }

    T run();

    default String jobClass() {
        return this.getClass().getSimpleName();
    }
}
