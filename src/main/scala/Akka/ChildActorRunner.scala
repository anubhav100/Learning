package Akka

import Akka.Parent.{CreateChild, TellChild}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ChildActorRunner extends App {
  val actorSystem = ActorSystem("parent")
  val parentActor = actorSystem.actorOf(Props[Parent])
  parentActor ! CreateChild("child")
  parentActor ! TellChild("a new child is created")

}

object Parent {
  case class CreateChild(name: String)
  case class TellChild(msg: String)
}

class Parent extends Actor {
  override def receive: Receive = {
    case CreateChild(_: String) =>
      val child = context.actorOf(Props[Child], "childActor")
      context.become(withChild(child), false)
  }
  def withChild(child: ActorRef): Receive = {
    case TellChild(msg: String) => child ! msg
  }
}

class Child extends Actor {
  override def receive: Receive = {
    case message =>
      println(s"I [recieved] a msg {${self.path}}")
      println(message.toString)
  }
}
