package com.scaler.bookmyshow.ratelimiter;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Deque;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class RateLimiter {
    private final ScheduledExecutorService executorService;
    private final long timeIntervalInMillis;
    private final Deque<LocalDateTime> queue;
//    private static final Map<ExternalProvider, RateLimiter> rateLimiterRegistry = new ConcurrentHashMap<>();
//
//    private RateLimiter(ExternalProvider externalProvider) {
//        this.timeIntervalInMillis = externalProvider.getTimeIntervalInMillis();
//        this.executorService = Executors.newSingleThreadScheduledExecutor();
//        this.queue = new ConcurrentLinkedDeque<>();
//    }
//
//    public static RateLimiter getInstance(ExternalProvider externalProvider) {
//        return rateLimiterRegistry.computeIfAbsent(externalProvider, provider -> new RateLimiter(provider));
//    }

    public <T> ScheduledFuture<T> scheduleTask(Callable<T> task) {
        long delay = getDelay();
        return executorService.schedule(() -> {
            try {
                return task.call();
            } catch (Exception e) {
                // Use proper logging instead of printStackTrace
                // LoggerFactory.getLogger(RateLimiter.class).error("Task execution failed", e);
                throw new RuntimeException(e);
            }
        }, delay, TimeUnit.MILLISECONDS);
    }

    public void scheduleTask(Runnable task) {
        long delay = getDelay();
        executorService.schedule(() -> {
            try {
                task.run();
            } catch (Exception e) {
                // Use proper logging instead of printStackTrace
                // LoggerFactory.getLogger(RateLimiter.class).error("Task execution failed", e);
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

    public static void main(String[] args) {
        AtomicReference<Long> atomicNum = new AtomicReference<>();
        atomicNum.set(10L);
        System.out.println(atomicNum);
        System.out.println(Optional.of(10L).get());
    }
}
