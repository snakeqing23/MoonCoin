package com.eric.mooncoin.services;

/**
 * Created by Ericqi on 9/03/2018.
 */
public class SampleJob implements IJob<Integer> {
    @Override
    public int maxJobCount() {
        return 5;
    }

    @Override
    public int jobWindow() {
        return 10;
    }

    @Override
    public Integer run() {
        System.out.println(100);
        return 100;
    }
}
