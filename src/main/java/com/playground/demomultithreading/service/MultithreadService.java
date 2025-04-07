package com.playground.demomultithreading.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@Service
public class MultithreadService {

    private final Executor threadPoolTaskExecutor;

    public MultithreadService(Executor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    // Simulated task that returns a string after a delay.
    public String performTask(int taskId) {
        try {
            Thread.sleep(1000); // simulate a delay (e.g., processing, I/O, etc.)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Task " + taskId + " completed by " + Thread.currentThread().getName();
    }

    // Approach 1: Using CompletableFuture to run tasks concurrently.
    public CompletableFuture<List<String>> executeTasksWithCompletableFuture() {
        int numberOfTasks = 5;
        List<CompletableFuture<String>> futures = new ArrayList<>();

        for (int i = 1; i <= numberOfTasks; i++) {
            final int taskId = i;
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> performTask(taskId), threadPoolTaskExecutor);
            futures.add(future);
        }
        // Combine all futures into one and aggregate the results.
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> {
                    List<String> results = new ArrayList<>();
                    for (CompletableFuture<String> f : futures) {
                        results.add(f.join());
                    }
                    return results;
                });
    }

    // Approach 2: Using a callback once all tasks have completed.
    public String executeTasksWithCallback(Consumer<List<String>> callback) {
        int numberOfTasks = 5;
        List<String> results = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(numberOfTasks);

        for (int i = 1; i <= numberOfTasks; i++) {
            final int taskId = i;
            threadPoolTaskExecutor.execute(() -> {
                String result = performTask(taskId);
                synchronized (results) {
                    results.add(result);
                }
                latch.countDown();
            });
        }

        try {
            latch.await(); // Wait until all tasks are complete.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Trigger the callback with the aggregated results.
        callback.accept(results);
        return "Callback approach executed. Check console for callback output.";
    }
}
