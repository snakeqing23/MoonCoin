package com.eric.mooncoin.services;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ericqi on 9/03/2018.
 */
public class JobSchedulerTest {
    JobScheduler scheduler = new JobScheduler(1);

    @Test
    public void testStartJob() throws Exception {
        for (int i = 0; i < 100; i++) {
            scheduler.startJob(new SampleJob());
        }
    }
}