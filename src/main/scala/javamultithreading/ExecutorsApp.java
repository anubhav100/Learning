package javamultithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorsApp {
    public static void main(String[] args) {
        // problem with thread model is suppose we have 1000 task so we have to create 1000 threads which is heavy process

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Task(), "thread" + i);
            t.start();
        }
        // executor service internally use blocking queue which is type safe
        /** TYPE-1
         *  fixed number of threads
         */
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Task());
        }
        /**
         *  in case tasks are cpu intensive take no of threads = no of cpu cores
         *  in case tasks are io intensive take no of threads > no of cpu cores
         */

        /** TYPE-2
         *  it just used the one synchronized queque which hold down one task at a time,if all thread are busy it will create new
         */
        ExecutorService executorService1 = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService1.execute(new Task());
        }

        /**  TYPE-3
         * it use the delay queue where one task can run say after 10 seconds and another after say 10 minutes
         */
        ExecutorService executorService2 = Executors.newScheduledThreadPool(4);
        for (int i = 0; i < 10; i++) {
            // run task after 10 seconds
            ((ScheduledExecutorService) executorService2).schedule(new Task(), 10, TimeUnit.SECONDS);
            // run task every 10 seconds but before that wait 15 seconds
            ((ScheduledExecutorService) executorService2).scheduleAtFixedRate(new Task(), 15L, 10L, TimeUnit.DAYS);
            // run task 10 seconds after previous instnace is completed only first turn will wait for 10 seconds
            ((ScheduledExecutorService) executorService2).scheduleWithFixedDelay(new Task(), 0L, 5L, TimeUnit.DAYS);

        }

        // should be used in case we want task to run in sequence it also uses a blocking quque
        /** Type-4
         *   should be used in case we want task to run in sequence it also uses a blocking quque
         */
        ExecutorService executorService3 = Executors.newSingleThreadExecutor();

    }
}

class Task implements Runnable {

    @Override
    public void run() {
        // do some task
    }
}