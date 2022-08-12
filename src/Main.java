import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        int[] array = initArray(10);
        System.out.println("входные данные: массив из 1 млн элементов.");

        System.out.println("-----------------------------");
        singleThreadSolution(array);
        System.out.println("-----------------------------");
        multithreadedProgram(array);

    }

    public static void singleThreadSolution(int[] array) {
        long start = System.currentTimeMillis();
        System.out.println("Не многопоточная программа:");
        System.out.println("сумма элементов: " + sum(array));
        System.out.println("среднее арифметическое: " + arithmeticMean(array));
        long end = System.currentTimeMillis();
        System.out.println("Затрачено времени: " + (end - start) + " мс");
    }

    public static void multithreadedProgram(int[] array) {
        long start = System.currentTimeMillis();
        System.out.println("Многопоточная программа:");


        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> future = executorService.submit(() -> sum(array));
        Future<Double> doubleFuture = executorService.submit(() -> arithmeticMean(array));

        try {
            System.out.println("сумма элементов: " + future.get());

            System.out.println("среднее арифметическое: " + doubleFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        executorService.shutdown();
        System.out.println("Затрачено времени: " + (end - start) + " мс");
    }

    public static int recursionSum(int[] array, int currentNumber, int result) {
        if (currentNumber == array.length) {
            return result;
        }
        result += array[currentNumber];
        return recursionSum(array, ++currentNumber, result);
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
