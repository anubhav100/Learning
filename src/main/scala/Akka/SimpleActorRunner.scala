package Akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object SimpleActorRunner extends App{
  val actorSystem = ActorSystem("SimpleActor")

  val alice = actorSystem.actorOf(Props[SimpleActor],"alice")
 // alice ! SendContent("hi")
  val bob = actorSystem.actorOf(Props[SimpleActor],"bob")
  alice ! SayHiTo(bob)

}
class SimpleActor extends Actor{
  override def receive: Receive = {
    case msg:String => println(s"$self i have recieved message $msg")
    case "Hi" => context.sender() ! "Hello"
    case sayHiTo: SayHiTo => sayHiTo.ref ! "Hi"
    case sendContent: SendContent => self ! sendContent.content
  }
}
case class SayHiTo(ref:ActorRef)
case class SendContent(content:String)
