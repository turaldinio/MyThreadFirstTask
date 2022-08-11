import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Thread> threadList = new ArrayList<>();

        for (int a = 1; a < 5; a++) {
            threadList.add(new Thread(new MyThread(), "Я поток " + a));
        }
        threadList.forEach(Thread::start);

        try {
            Thread.sleep(10000);
            System.out.println("Завершаю все потоки");
            threadList.forEach(Thread::interrupt);
        } catch (InterruptedException e) {
            return;
        }

    }
}
