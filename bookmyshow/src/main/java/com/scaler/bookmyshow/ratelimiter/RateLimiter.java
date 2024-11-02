package com.scaler.bookmyshow.ratelimiter;

import com.scaler.bookmyshow.advice.exception.ProgramException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Deque;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class RateLimiter {
    private final ScheduledExecutorService executorService;
    private final long timeIntervalInMillis = 100L;
    private final Deque<LocalDateTime> queue;
    private static final ConcurrentHashMap<String, RateLimiter> rateLimiterRegistry = new ConcurrentHashMap<>();

    private RateLimiter(String key) {
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.queue = new ConcurrentLinkedDeque<>();
    }

    public static RateLimiter getInstance(String key) {
        return rateLimiterRegistry.computeIfAbsent(key, RateLimiter::new);
    }

    public <T> ScheduledFuture<T> scheduleTask(Callable<T> task) {
        long delay = getDelay();
        return executorService.schedule(() -> {
            try {
                return task.call();
            } catch (Exception e) {
                log.error("Error while running the task. {}", e.getMessage());
                throw new ProgramException(e.getMessage(), e);
            }
        }, delay, TimeUnit.MILLISECONDS);
    }

    public void scheduleTask(Runnable task) {
        long delay = getDelay();
        executorService.schedule(() -> {
            try {
                task.run();
            } catch (Exception e) {
                log.error("Error while running the task. {}", e.getMessage());
            }
        }, delay, TimeUnit.MILLISECONDS);
    }

    private synchronized long getDelay() {
        return Optional.ofNullable(queue.removeLast())
            .map(lastScheduledTime -> {
                long timeToWait = Duration.between(lastScheduledTime, LocalDateTime.now()).toMillis();
                long delay = Math.max(timeToWait, 0);
                recordScheduledTime(delay+timeIntervalInMillis);
                return delay;
            })
            .orElseGet(() -> {
                recordScheduledTime(timeIntervalInMillis);
                return 0L;
            });
    }

    private synchronized void recordScheduledTime(long delay) {
        queue.addFirst(LocalDateTime.now().plus(Duration.ofMillis(delay)));
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

    }
}
