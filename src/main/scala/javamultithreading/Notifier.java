package javamultithreading;

import javamultithreading.Message;

public class Notifier implements Runnable {

    private Message msg;

    public Notifier(Message msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " started");
        try {
            Thread.sleep(1000);
            // use synchronize keyword because one can only gain access when it has aqquaired the lock other wise not in case of objects
            synchronized (msg) {
                msg.setMsg(name + " javamultithreading.Notifier work done");
                // notify the origin thread that my work is completed
                msg.notify();
                // msg.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
