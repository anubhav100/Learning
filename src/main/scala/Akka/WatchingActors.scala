package Akka

import Akka.WatchingActors.Parent.{StartActor, StopActor}
import akka.actor.{
  Actor,
  ActorLogging,
  ActorRef,
  ActorSystem,
  Kill,
  PoisonPill,
  Props,
  Terminated
}

object WatchingActors extends App {
  val actorSystem = ActorSystem("watching")
  val parent = actorSystem.actorOf(Props[Parent], "parent")
  parent ! StartActor("child1")
  val childActor = actorSystem.actorSelection("/user/parent/child1")
  childActor ! PoisonPill
  childActor ! Kill
  childActor ! "Hi"

  object Parent {
    case class StartActor(name: String)
    case class StopActor(name: String)
  }
  class Parent extends Actor with ActorLogging {
    override def receive: Receive = handleActor(Map())
    def handleActor(actors: Map[String, ActorRef]): Receive = {
      case StartActor(name: String) =>
        val childActor = context.actorOf(Props[Child], name)
        log.info(s"Started new Actor ${childActor.path} ")
        context.watch(childActor)
        context.become(handleActor(actors + (name -> childActor)))

      case StopActor(name: String) =>
        val actorTobeStopOpt = actors.get(name)
        actorTobeStopOpt.foreach { actor =>
          log.info(s"Stopping Actor ${actor.path}")
          context.stop(actor)
          context.become(handleActor(actors - name))

        }
      case Terminated(ref) =>
        log.info(s"actor $ref is terminated ")
    }
  }

  class Child extends Actor with ActorLogging {
    override def receive: Receive = {
      case message => log.info(message.toString)
    }
  }
}
