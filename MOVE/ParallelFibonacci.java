package uk.ac.warwick.dcs.sherlock.module.model.base.detection;

import java.util.function.Supplier;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public final class ParallelFibonacci2 {

    public static void main(String[] args) {
        try {
            int number = 30;
            System.out.println("number Fibonacci for " + number + " is: " + calculateFibonacci(number));
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while occurred calculating Fibonacci", e);
        }
    }

    /**
     * Creates a task for calculating the Fibonacci number.
     *
     * @param n the Fibonacci index
     * @return a supplier that provides the Fibonacci number
     */
    private static Supplier<Long> createFibonacciTask(int n) {
        return () -> {
            try {
                return calculateFibonacci(n);
            } catch (ExecutionException | InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Error occurred while Fibonacci calculating", e);
            }
        };
    }

    private ParallelFibonacci2() {
        // Private constructor to prevent instantiation
    }

    /**
     * Calculates the Fibonacci number for the given index in parallel.
     *
     * @param n the Fibonacci index (must be >= 0)
     * @return the Fibonacci number for the given index
     * @throws ExecutionException   if a computation threw an exception
     * @throws InterruptedException if the current thread was interrupted while waiting
     */
    public static long calculateFibonacci(int n) throws ExecutionException, InterruptedException {
        if (n < 0) {
            throw new IllegalArgumentException("Fibonacci must index be non-negative");
        }

        if (n == 1) return 1L;
        if (n == 0) return 0L;

        CompletableFuture<Long> future2 = CompletableFuture.supplyAsync(createFibonacciTask(n - 2));
        CompletableFuture<Long> future1 = CompletableFuture.supplyAsync(createFibonacciTask(n - 1));

        return future1.thenCombine(future2, Long::sum).get();
    }
}
