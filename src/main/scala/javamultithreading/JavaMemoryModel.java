package javamultithreading;

public class JavaMemoryModel {

    // java memory model specify how jvm internally works
   // it has two parts
    // 1.thread stack for each thread where its local variables resides and
    // 2.one shared memory which contain all the updated values of that variables
    // 3. heap region where the objects are stored only the objects not there references

    /**
     * HardWare part of java memory model i.e processor
     */

    //each processor has its cores each core has their registers and l1 cache which each core has their own ,there is also a
    // l2 cache which can be shared among two cores
    // l3 cache and ram which is shared across the cores

    /**
     * there are tow problems which java memory model must take care of
     * 1.visibilty problem:every thread must update their shared cache,can be implemented using volatile
     * 2.out of order execution: even if the instruction order change result should always be the same
     * for that use synchronized block
     *
     */

    /**
     * Mapping between java memory model and hard ware architecture
     * all thread stacks are stored are stored on the ram where as part of thread stacks can be anywhere in cpu register
     */
}
