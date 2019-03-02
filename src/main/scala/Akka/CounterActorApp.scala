package Akka

import Akka.CounterActor.{Dec, Inc, Print}
import akka.actor.{Actor, ActorSystem, Props}

object CounterActorApp extends App{

  val counterActorSystem = ActorSystem("counterActorSystem")
  val counterActor = counterActorSystem.actorOf(Props[CounterActor])
  counterActor ! Inc
  counterActor ! Print
}
class CounterActor extends Actor{
  var count =0
  override def receive: Receive = {
    case Inc => count = count +1
    case Dec => count = count - 1
    case Print => println("count value is"+count)
  }
}

object CounterActor{
  case object Inc
  case object Dec
  case object Print
}