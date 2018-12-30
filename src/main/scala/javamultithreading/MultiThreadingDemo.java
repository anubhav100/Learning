package javamultithreading;

public class MultiThreadingDemo {
    public static void main(String[] args) throws InterruptedException {
        MyRunnable1 myRunnable1 = new MyRunnable1(4);
        MyRunnable2 myRunnable2 = new MyRunnable2(3);

        Thread t1 = new Thread(myRunnable1);
        t1.start();

        Thread t2 = new Thread(myRunnable2);
        t2.start();
        // running this programme we can see some time main thread gets completed before the both worker threads which we don't want
        // here we will use join function to make main thread wait until thread1 and thread2 completes
        t1.join();
        t2.join();
        if (t1.isAlive()) {
            System.out.println("Thread1 is still doing its work");
        }
        if (t1.isAlive()) {
            System.out.println("Thread2 is still doing its work");
        }
        System.out.println("main completed");

    }

}

class MyRunnable1 implements Runnable {

    private int var;

    public MyRunnable1(int var) {
        this.var = var;
    }

    public void run() {
        System.out.println("thread1 is started");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {

        }
        System.out.println("Thread1 is returning");

        // code in the other thread, can reference "var" variable
    }
}

class MyRunnable2 implements Runnable {

    private int var;

    public MyRunnable2(int var) {
        this.var = var;
    }

    public void run() {
        try {
            System.out.println("Thread2 is started");
            Thread.sleep(2000);
            System.out.println("Thread2 is returning");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("thread2 function called");

        // code in the other thread, can reference "var" variable
    }

    class CountRunnable implements Runnable {
        int count;

        CountRunnable(int counter) {
            count = counter;
        }

        @Override
        public void run() {
            count = count + 1;
        }
    }
}