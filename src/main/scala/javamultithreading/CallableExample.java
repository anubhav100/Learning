package javamultithreading;

import java.util.concurrent.*;

public class CallableExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 10; i++) {
            Future<Integer> result = executorService.submit(new Tasking());
            result.get(); // blocking call
        }
    }
}
 class Tasking implements Callable<Integer>{

     @Override
     public Integer call() throws Exception {
         return 2;
     }
 }