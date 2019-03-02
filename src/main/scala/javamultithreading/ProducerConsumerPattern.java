/*
package javamultithreading;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerPattern {
    public static void main(String[] args) {


        class BlockingQueque<E> {
            public Queue<E> queue;
            public int size = 16;
            private ReentrantLock lock = new ReentrantLock();

            public void set(int size) {
                queue = new LinkedList<E>();
                this.size = size;
            }

            public void put(E ele) {
                lock.lock();
                try {
                    System.out.println("adding element" + ele);
                    queue.add(ele);
                } finally {
                    lock.unlock();
                }
            }

            public E get() {
                lock.lock();
                try {
                    E ele = queue.remove();
                    System.out.println("Removing element" + ele);
                    return ele;
                } finally {
                    {
                        lock.unlock();
                    }
                }
            }
        }

        class ProducerConsumer{
            BlockingQueque<Integer> blockingQueque = new BlockingQueque<>();
            Integer i = 1;
            final Runnable producer = () -> {
                while (true) {
                    blockingQueque.put(i);
                    System.out.println("[Producer] i have produced the element" + i);
                    i = i + 1;
                }
            };



            final Runnable consumer = () -> {
                while (true) {
                    Integer ele = blockingQueque.get();
                    System.out.println("[Consumer] i have consumed the element" + ele);

                }
            };
            new Thread().start();
        }
    }
}*/
