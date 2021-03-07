import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService es = null;
        try {
            es = Executors.newFixedThreadPool(2);
            es.execute(new Task("task 1"));
            es.execute(new Task("task 2"));
            es.execute(new Task("task 3"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (es != null) {
                es.shutdown();
            }
        }
    }
}

class Task implements Runnable {
    String msg;

    public Task(String msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        System.out.println(this.msg);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
