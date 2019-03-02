package Akka

import akka.actor.{Actor, ActorLogging, ActorSystem, Props, Timers}

import scala.concurrent.duration._

object TimerBasedActor extends App {
  val system = ActorSystem("Timer")
  val timeActor = system.actorOf(Props[TimerBasedHeartBeatActor])
  timeActor ! Start
}

case object Start
case object TimerKey
case object Remainder

class TimerBasedHeartBeatActor extends Actor with ActorLogging with Timers {
  timers.startSingleTimer(TimerKey, Start, 500.milliseconds)

  override def receive: Receive = {
    case Start =>
      log.info("Bootstraping")
      timers.startPeriodicTimer(TimerKey, Remainder, 1.seconds)
    case Remainder =>
      log.info("I am alive")
  }
}
