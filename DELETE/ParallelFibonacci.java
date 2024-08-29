package uk.ac.warwick.dcs.sherlock.module.model.base.detection;

import java.util.concurrent.ExecutionException;

public final class ParallelFibonacci2 {
    public static void main(String[] args) {
        try {
            int number = 30;
            System.out.println("Fibonacci number for " + number + " is: " + calculateFibonacci(number));
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error occurred while calculating Fibonacci", e);
        }
    }
}
