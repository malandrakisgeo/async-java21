package com.gmalandrakis.async.core;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Stream;

//Special thanks to Simon R.!
public class AsyncDispatcher {
    /*
        As of 3/2024, Java has a tough time using parallel streams when ThreadLocal and Fork-join pools are into play,
        so the asyncDispacher should be used instead of parallelStream() even though it is way slower.
     */
    @Async
    static public <T> CompletableFuture<T> run(Supplier<T> supplier) {
        try {
            return AsyncResult.forValue(supplier.get()).completable();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Async
    static public <R> CompletableFuture<R> call(Callable<R> callable) {
        try {
            return AsyncResult.<R>forValue((R) callable.call()).completable();
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Async
    public static <R> Stream<R> waitForAll(final List<CompletableFuture<R>> completableFutureList) {
        if (completableFutureList == null || completableFutureList.isEmpty() ) {
            return Stream.empty();
        }

        try {
            CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[completableFutureList.size()])).join();

            return completableFutureList.stream().map(f -> {
                try {
                    return f.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return null;
                }
            }).filter(Objects::nonNull);
        } catch (final Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
