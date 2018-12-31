package scalaconcurrent

import scala.collection.mutable

object ProducerConsumerV2 extends App {
  // producer ->[***]-> consumer[new value]
  /**
    * if producer filled the buffer completely then it must wait for consumer to empty it and vice versa
    */

  def producerConsumer = {
    val capacity = 3
    val buffer = mutable.Queue[Int](capacity)
    val consumer = new Thread() {
      override def run(): Unit = {
        while (true) {
          buffer.synchronized {
            if (buffer.isEmpty) {
              println("[Consumer] is waiting buffer is empty")
              // let the producer produced the result
              buffer.wait()
            }
            val value = buffer.dequeue()
            println(s"[Consumer] consumer has consumed value $value")
            buffer.notify()
          }
        }
      }
    }
    val producer = new Thread() {
      override def run(): Unit = {
        var i = 0
        while (true) {
          buffer.synchronized {
            if (buffer.size == capacity) {
              println("[Producer] is waiting buffer is full")
              buffer.wait()
            }
            println(s"[Producer] is producing value $i")
            buffer.enqueue(i)
            buffer.notify()
            i = i + 1
          }
        }
      }
    }
    consumer.start()
    producer.start()
  }

  producerConsumer
}
