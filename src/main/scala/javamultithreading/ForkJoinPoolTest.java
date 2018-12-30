package javamultithreading;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolTest {
    // divide the task in sub tasks and merge the result
    // each thread has its dequqe where it will create sub tasks
    // there can be a concept of thread stealing one thread can steal task from another thread if it is idle

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(4);
        ForkJoinTask<Integer> result = pool.submit(new Fibnoccci(4));
        System.out.println("result is "+result.get());
    }



}
class Fibnoccci extends RecursiveTask<Integer> {
    int n;
    Fibnoccci(int n){
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if(n<=1)
            return  n;
        Fibnoccci f1 = new Fibnoccci(n-1);
        f1.fork();
        Fibnoccci f2 = new Fibnoccci(n-1);
        f2.fork();
        return f1.join() + f2.join();
    }
}