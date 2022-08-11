import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool((Runtime.getRuntime().availableProcessors()));

        List<Callable<Integer>> callableList = new ArrayList<>();
        for (int a = 1; a < 5; a++) {
            callableList.add(new MyCallable());
        }

        try {

            Integer i = executorService.invokeAny(callableList);
            System.out.println(i);

            executorService.shutdown();


        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
