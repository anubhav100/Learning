package javamultithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileAndAtomics {
    public static void main(String[] args) {
        boolean flag = false;
        while(flag){
         break;
         // but it wont' work if thread2 set the value of flag to true because that value will be updated on its itw own local cache only
          //  not on the shared as different threads might be executing on different cores
        }
        AtomicInteger value = new AtomicInteger(0);
        value.getAndAdd(1);

        // atomic variables makes sure that all operations of a operation are performed on a single transaction,no intermediate read are allowed
        // example in thread1 value1 is read and at same time thread2 has read the value
    }
    //TODO DIFFERENCE BETWEEN ATOMIC AND SYNCHRONIZATION

    /**
     * difference between volatile and synchronize is that volatile is used to make sure that multiple threads can acccess data
     * but they wull update their local copy into shared cache where as synchrinized does not allow multiple threads to access the code block
     * atomic is different from synchronization in the way that it allow the whole transaction to completed in one thread where as  synchronize will not allow two thredas to
     * access data simultaneously
     */

}
