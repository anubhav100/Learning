package scalaconcurrent

import scala.collection.mutable

object ProducerConsumerV3 extends App {
  val buffer = mutable.Queue[Int]()

  for (i <- 1 to 10) {
    new Thread(new Consumer(i, buffer)).start()
    new Thread(new Producer(i, buffer)).start()
  }
}

class Consumer(id: Int, buffer: mutable.Queue[Int]) extends Thread {
  override def run(): Unit = {
    while (true) {
      buffer.synchronized {
        while (buffer.isEmpty) {
          println(s"[Consumer $id] is waiting buffer is empty")
          // let the producer produced the result
          buffer.wait()
        }
        val value = buffer.dequeue()
        Thread.sleep(1000)
        println(s"[Consumer $id] consumer has consumed value $value")
        buffer.notifyAll()
      }
    }
  }
}

class Producer(id: Int, buffer: mutable.Queue[Int], capacity: Int = 3)
    extends Thread {
  override def run(): Unit = {
    var i = 0
    while (true) {
      buffer.synchronized {
        while (buffer.size == capacity) {
          println(s"[Producer $id] is waiting buffer is full")
          buffer.wait()
        }
        Thread.sleep(1000)

        println(s"[Producer $id] is producing value $i")
        buffer.enqueue(i)
        buffer.notifyAll()
        i = i + 1
      }
    }
  }
}
