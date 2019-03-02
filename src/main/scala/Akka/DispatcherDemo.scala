package Akka

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

import scala.util.Random

object DispatcherDemo extends App {
  class CounterActor extends Actor with ActorLogging {
    var count = 0
    override def receive: Receive = {
      case message =>
        count = count + 1
        log.info(s"[$count] $message")
    }
  }
  val system = ActorSystem("dispatcher")

  /**
    * Method-1 programmatic
    */
  /* val actors = (for (i <- 1 to 10) yield {
    system.actorOf(Props[CounterActor].withDispatcher("my-dispatcher"),
                   s"counter_$i")
  }).toList
  val r = new Random()
  for (i <- 1 to 1000) {
    actors(r.nextInt(10)) ! i
  }*/

  /**
    * Method-2
    */
  val actors = (for (i <- 1 to 10) yield {
    system.actorOf(Props[CounterActor], s"counter_$i")
  }).toList
  val r = new Random()
  for (i <- 1 to 1000) {
    actors(r.nextInt(10)) ! i
  }

  /**
  * Method-3
  * Dispatcher implement executuin context trait
  */
}
