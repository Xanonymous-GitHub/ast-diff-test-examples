package uk.ac.warwick.dcs.sherlock.module.model.base.detection;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ParallelFibonacci2 {

    private ParallelFibonacci2() {
        // Private constructor to prevent instantiation
    }

    public static void main(String[] args) {
        try {
            int number = 30;

            final var scanner = new Scanner(System.in);
            final int input = scanner.nextInt();

            if (input > 4) {
                System.out.println("Input is greater than 4");
            }

            scanner.close();

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

        logCalculation(n); // Log the start and end of the calculation

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

    /**
     * Logs the start and end of a Fibonacci calculation.
     *
     * @param n the Fibonacci index being calculated
     */
    private static void logCalculation(int n) {
        System.out.println("Starting calculation for Fibonacci index: " + n);
        // Dummy logic, you can replace this with actual logging or monitoring code.
        System.out.println("Finished calculation for Fibonacci index: " + n);
    }

    /**
     * Safe wrapper for the calculateFibonacci method.
     *
     * @param n the Fibonacci index
     * @return the Fibonacci number for the given index
     */
    private static long calculateFibonacciSafe(int n) {
        try {
            return calculateFibonacci(n);
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error occurred while calculating Fibonacci sequence", e);
        }
    }

    /**
     * Generates a list of Fibonacci numbers up to the specified index.
     *
     * @param n the maximum Fibonacci index (must be >= 0)
     * @return a list of Fibonacci numbers from 0 to n
     * @throws ExecutionException   if a computation threw an exception
     * @throws InterruptedException if the current thread was interrupted while waiting
     */
    public static List<Long> generateFibonacciSequence(int n) throws ExecutionException, InterruptedException {
        if (n < 0) {
            throw new IllegalArgumentException("Fibonacci index must be non-negative");
        }

        return IntStream.rangeClosed(0, n)
                .parallel()
                .mapToObj(ParallelFibonacci2::calculateFibonacciSafe)
                .collect(Collectors.toList());
    }

    private Supplier<String> createTask(int index) {
        return () -> {
            // Dummy logic for task
            try {
                Thread.sleep(1000); // Simulate some work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Task " + index + " completed";
        };
    }

    public Map<String, List<Integer>> complexMethod(Map<String, List<Integer>> input) {
        // Dummy logic for complex method
        return input.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(i -> i * 2)
                                .collect(Collectors.toList())
                ));
    }

    public void anotherComplexMethod() {
        // Dummy logic for another complex method
        Map<String, List<Integer>> data = new HashMap<>();
        data.put("A", Arrays.asList(1, 2, 3));
        data.put("B", Arrays.asList(4, 5, 6));

        Map<String, List<Integer>> result = complexMethod(data);
        result.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    public void execute() {
        // Dummy logic to demonstrate complex structure
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futures.add(CompletableFuture.supplyAsync(createTask(i)));
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.thenRun(() -> {
            futures.forEach(future -> {
                try {
                    System.out.println(future.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }).join();
    }
}
