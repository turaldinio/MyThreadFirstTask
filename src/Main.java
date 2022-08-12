import javax.swing.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        Chart chart = new Chart(
                "visualization",
                "Single-thread vs multithreading");

        chart.pack();
        chart.setVisible(true);
        chart.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }


    public static int singleThreadSolution(int[] array) {
        long start = System.currentTimeMillis();
        System.out.println("Не многопоточная программа:");
//        System.out.println("сумма элементов: " + sum(array));
//        System.out.println("среднее арифметическое: " + arithmeticMean(array));
        sum(array);
        arithmeticMean(array);
        long end = System.currentTimeMillis();
        System.out.println("Затрачено времени: " + (end - start) + " мс");
        System.out.println("-----------------------------");

        return (int) (end - start);
    }

    public static int multithreadedProgramRunnable(int[] array) {
        long start = System.currentTimeMillis();
        System.out.println("Многопоточная программа Runnable:");

        new Thread(() -> {
            sum(array);
        }).start();

        new Thread(() -> {
            arithmeticMean(array);
        }).start();

        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println("-----------------------------");

        return (int) (end - start);
    }

    public static int multithreadedProgram(int[] array) {
        long start = System.currentTimeMillis();
        System.out.println("Многопоточная программа Callable:");


        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Future<Integer> future = executorService.submit(() -> sum(array));
        Future<Double> doubleFuture = executorService.submit(() -> arithmeticMean(array));

        try {
            future.get();
            doubleFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        //   System.out.println("сумма элементов: " + future.get());
        //System.out.println("среднее арифметическое: " + doubleFuture.get());
        long end = System.currentTimeMillis();
        executorService.shutdown();
        System.out.println("Затрачено времени: " + (end - start) + " мс");
        System.out.println("-----------------------------");
        return (int) (end - start);
    }


    public static int[] initArray(int arraySize) {
        return new Random().ints(arraySize, 0, arraySize).toArray();
    }

    public static int sum(int[] array) {
        return Arrays.stream(array).sum();
    }

    public static double arithmeticMean(int[] array) {
        return (double) Arrays.stream(array).sum() / array.length;
    }
}
