import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    private static File logFile = new File("lofFile.txt");

    public static void main(String[] args) {
        Chart chart = new Chart(
                "visualization",
                "Single-thread vs multithreading");

        chart.pack();
        chart.setVisible(true);
        chart.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }


    public static int singleThreadSolution(int[] array) {
        LogWriter writer = LogWriter.getInstance();
        long start = System.currentTimeMillis();
        sum(array);
        arithmeticMean(array);
        long end = System.currentTimeMillis();
        writer.writeLog("Не многопоточная программа", array.length, (int) (end - start));


        return (int) (end - start);
    }

    public static int multithreadedProgramRunnable(int[] array) {
        LogWriter writer = LogWriter.getInstance();
        long start = System.currentTimeMillis();

        Thread first = new Thread(() -> {
            sum(array);
        });

        Thread second = new Thread(() -> {
            arithmeticMean(array);
        });

        first.start();
        second.start();

        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        writer.writeLog("Многопоточная программа (Runnable)", array.length, (int) (end - start));


        return (int) (end - start);
    }

    public static int multithreadedProgram(int[] array) {
        LogWriter writer = LogWriter.getInstance();
        long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Future<Integer> future = executorService.submit(() -> sum(array));
        Future<Double> doubleFuture = executorService.submit(() -> arithmeticMean(array));

        try {
            future.get();
            doubleFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        executorService.shutdown();

        writer.writeLog("Многопоточная программа (Callable)", array.length, (int) (end - start));

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
