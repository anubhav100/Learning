package javamultithreading;

public class GarbageCollection {

    /**
     * There are four concepts in garbage collection which are
     *
     * 1.trade offs,2:Generational GC,3:Mark and Sweep and compact 4.Mark and Copy
     *
     * 1.TradeOff: It consists three factors first one is memory which is memory taken by programme,seconds one is throught put
     * which is time taken by programme vs tike taken by garbage collection
     * third one is latency which means for how much time programme has to be pause for gc to occur
     *
     * 2.Generational GC:
     *   Minor Collection
     *  [young generation] all the objects  comes up here which are local to a loop ends up here and removed by minor collection
     *  [old generation] when the object has greater life span like class variables they are moved from young to old generation
     *   Major Collection
     *
     * 3.Mark and Copy Algorithm:
     *   [Eden Space]
     *   [Survivor Space1] [Survivor Space2]
     *   all the objects in young generation first comes in Eden space and owhen minor collection happens
     *   out of them which are still alive were marked and then copied into survivor space and eden space is cleared
     *   same happend for survivor space2 as well
     *
     *  4.Mark and Sweep and compact Algorithm: This algo works on the old generation
     *  [[live][live][][live][][]]
     *
     *  step 1: mark the live objects
     *  [[live][live][dead][live][dead][dead][dead]
     *
     *  step 2: sweep the object
     *  [[live][live][live][dead][dead][dead]]
     *  step: one can easily add new objects now to the end for this purpose there is a pointer to last live object
     */

    /** Type of garbage collectors
     *
     *  1.serial collector: It use mark and sweep algorithrm,it is a single thread collector and best suited in case of
     *  shared cpu since you don't want a collector to consume all the cpu threads
     *
     *  2.Parallel Collector: It is multithreaded collector and those threads run parallel,it should not run with application and
     *  give greatest throughput in case of multiprocessor system
     *
     *  3.Concurrent Mark and Sweep Collector:it provides great latency as it run with the application
     *  it requires the more footprint than parallel collector and also has less throughput
     *
     *  4.G1 collector: it divides the heap in regions and then region in generations
     *  it can be given the pause time so it has the most determinstic latency
     */

}
