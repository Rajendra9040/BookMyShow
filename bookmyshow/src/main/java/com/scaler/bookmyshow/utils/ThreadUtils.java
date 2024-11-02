package com.scaler.bookmyshow.utils;

import com.scaler.bookmyshow.advice.exception.ProgramException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadUtils {
    public static CompletableFuture<Void> runAllAsync(List<Runnable> tasks, ExecutorService executorService) {
        return CompletableFuture.allOf(tasks.stream()
            .map(CompletableFuture::runAsync)
            .toArray(CompletableFuture[]::new));
    }

    public static void runAllAsyncAndComplete(List<Runnable> tasks, ExecutorService executorService) {
        try {
            runAllAsync(tasks, executorService).join();
        } catch (CompletionException e) {
            throw new ProgramException(e.getCause().getMessage(), e);
        }
    }

    public static <V> ScheduledFuture<V> scheduledFuture(final Callable<V> callable, Long delay, TimeUnit timeUnit) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        try {
            return executorService.schedule(callable, delay, timeUnit);
        } finally {
            executorService.shutdown();
        }
    }
}
