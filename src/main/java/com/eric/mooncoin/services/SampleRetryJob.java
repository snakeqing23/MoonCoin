package com.eric.mooncoin.services;

/**
 * Created by Ericqi on 9/03/2018.
 */
public class SampleRetryJob implements IJob<String> {
    @Override
    public int maxJobCount() {
        return 4;
    }

    @Override
    public int jobWindow() {
        return 10;
    }

    @Override
    public int retryTime() {
        return 2;
    }

    @Override
    public String run() {
        System.out.println("retryJob");
        return "retryJob";
    }
}
