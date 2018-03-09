package com.eric.mooncoin.services;

import org.junit.Test;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Created by Ericqi on 9/03/2018.
 */
public class JobSchedulerTest {
    JobScheduler scheduler = new JobScheduler(1);

    @Test
    public void testStartJob() throws Exception {
        long count = IntStream.range(0, 100)
                .mapToObj(i -> scheduler.startJob(new SampleJob()))
                .filter(Optional::isPresent)
                .count();
        assertEquals(5, count);
    }

    @Test
    public void testStartRetryJob() throws Exception {
        long count = IntStream.range(0, 100)
                .mapToObj(i -> scheduler.startJob(new SampleRetryJob()))
                .filter(Optional::isPresent)
                .count();
        assertEquals(100, count);
    }
}