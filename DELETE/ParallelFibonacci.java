package uk.ac.warwick.dcs.sherlock.module.model.base.detection;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public final class ParallelFibonacci2 {

    private ParallelFibonacci2() {
        // Private constructor to prevent instantiation
    }

    public static void main(String[] args) {
        try {
            int number = 30;
            System.out.println("Fibonacci number for " + number + " is: " + calculateFibonacci(number));
        } catch (ExecutionException | InterruptedException e) {
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

        CompletableFuture<Long> future1 = CompletableFuture.supplyAsync(createFibonacciTask(n - 1));
        CompletableFuture<Long> future2 = CompletableFuture.supplyAsync(createFibonacciTask(n - 2));

        return future1.thenCombine(future2, Long::sum).get();
    }
}
