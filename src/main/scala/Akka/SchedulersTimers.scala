package Akka

import akka.actor.{Actor, ActorLogging, ActorSystem, Cancellable, Props}

import scala.concurrent.duration._
object SchedulersTimers extends App {
  val system = ActorSystem("timer")
  implicit val executionContext = system.dispatcher

  class SimpleActor extends Actor with ActorLogging {
    override def receive: Receive = {
      case message => log.info(message.toString)
    }
  }
  /*
  val simpleActor = system.actorOf(Props[SimpleActor])
  implicit val executionContext = system.dispatcher
  val routine: Cancellable =
    system.scheduler.schedule(1.seconds, 2.seconds) {
      simpleActor ! "hearBeat"
    }
  system.scheduler.scheduleOnce(5.seconds) {
    routine.cancel()*/ // cancel after 5 minutes

  class SelfClosingActor extends Actor with ActorLogging {
    var schedule = timeWindow
    def timeWindow: Cancellable = {
      context.system.scheduler.scheduleOnce(1.seconds) {
        self ! "timeout"
      }
    }
    override def receive: Receive = {
      case "timeout" =>
        log.info("stopping myself")
        context.stop(self)
      case "ping" =>
        log.info("i am still alive")
        schedule.cancel()
        schedule = timeWindow
    }
  }
  val selfClosingActor =
    system.actorOf(Props[SelfClosingActor], "selfClosingActor")
  system.scheduler.scheduleOnce(250.millisecond) {
    selfClosingActor ! "ping"
  }
  system.scheduler.scheduleOnce(2.seconds) {
    system.log.info("sending pong message")
    selfClosingActor ! "pong"

  }
}
