package scalaconcurrent

object ProducerConsumerV1 extends App {

 /* def producerConsumer = {
    val container = new Container
    val consumer = new Thread { () => println("[consumer] is waiting")
      while (container.isEmpty) {
        println("[consumer] is actively waiting")
      }
      println(s"[consumer] i have consumed {${container.get}}")
    }

    val producer = new Thread { () => println("[producer] is computing")
      Thread.sleep(500)
      val value = 42
      println(s"[producer]  i have produced the value $value")
      container.set(value)
      System.exit(0)
    }
    consumer.start()
    producer.start()
  }*/

  def smartProducerConsumer = {
    val container = new Container
    println("[consumer]  is waiting")

    val consumer = new Thread {
      override def run(): Unit = {
        println("[consumer]  is waiting")

        container.synchronized {
          container.wait()
        }
        println(s"[consumer] i have consumed {${container.get}}")
      }
    }
    val producer = new Thread {
      override def run(): Unit = {
      val value = 42
      println(s"[producer] is doing hard work")

       Thread.sleep(2000)
      container.synchronized {
        println(s"[producer]  i have produced the value $value")
        container.set(value)
        container.notify()
      }
    }
    }

    consumer.start()
    producer.start()
  }

  /** Classic Producer Consumer Problem
    * producer -> [container]-> consumer
    */
  class Container {
    var value = 0

    def isEmpty: Boolean = value == 0

    def get: Int = {
      val result = value
      value = 0
      result
    }

    def set(newValue: Int) = value = newValue
  }

  //producerConsumer
  smartProducerConsumer
}
