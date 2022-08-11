import java.util.concurrent.Callable;

public class MyCallable implements Callable<Integer> {
    private int massageCount = 0;

    @Override
    public Integer call() {
        try {
            for (int a = 0; a < 5; a++) {
                Thread.sleep(1500);
                System.out.println(Thread.currentThread().getName() + " Всем привет! ");
                massageCount++;
            }
        } catch (InterruptedException err) {
            err.printStackTrace();
        }
        return massageCount;
    }
}