package com.eric.mooncoin.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Ericqi on 9/03/2018.
 */
public class JobScheduler {
    private static Logger logger = LoggerFactory.getLogger(JobScheduler.class);
    private final ExecutorService executorService;
    private final Map<String, LinkedBlockingQueue<Long>> jobLimitState = new ConcurrentHashMap<>();

    public JobScheduler(int numberOfConcurrentJobs) {
        this.executorService = Executors.newFixedThreadPool(numberOfConcurrentJobs);
    }

    public <T> Optional<T> startJob(IJob<T> job) {
        jobLimitState.computeIfAbsent(job.jobClass(), k -> new LinkedBlockingQueue<Long>(job.maxJobCount()));
        long currentJobId = System.currentTimeMillis();
        boolean[] canRun = {true};
        LinkedBlockingQueue<Long> jobQueue = jobLimitState.computeIfPresent(job.jobClass(), (k, queue) -> {
            if (queue.remainingCapacity() == 0) {
                long firstJob = queue.peek();
                long timeFromFirstJob = (currentJobId - firstJob) / 1000;
                if (timeFromFirstJob <= job.jobWindow()) {
                    // still in the window but run out of job capacity
                    System.out.println("Running out of capacity max: " + job.jobClass() + " " + job.maxJobCount());
                    canRun[0] = false;
                } else {
                    queue.remove();
                    queue.add(currentJobId);
                }
            } else {
                queue.add(currentJobId);
            }
            return queue;
        });
        if (canRun[0]) {
            return Optional.of(job.run());
        } else {
            return Optional.empty();
        }
    }

}
