package uk.ac.warwick.dcs.sherlock.module.model.base.detection;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public final class ParallelFibonacci {

    private ParallelFibonacci() {
        // Private constructor to prevent instantiation
    }

    public static void main(String[] args) {
        try {
            int number = 30;
            System.out.println("Fibonacci number for " + number + " is: " + calculateFibonacci(number));
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error occurred while calculating Fibonacci", e);
        }
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
            throw new IllegalArgumentException("Fibonacci index must be non-negative");
        }

        if (n == 0) return 0L;
        if (n == 1) return 1L;

        CompletableFuture<Long> future1 = CompletableFuture.supplyAsync(createFibonacciTask(n - 1));
        CompletableFuture<Long> future2 = CompletableFuture.supplyAsync(createFibonacciTask(n - 2));

        return future1.thenCombine(future2, Long::sum).get();
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
                throw new RuntimeException("Error occurred while calculating Fibonacci", e);
            }
        };
    }
}
