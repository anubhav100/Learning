package Akka

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

object WordCountActorRunner extends App{
  import ExecutionContext.Implicits.global
  implicit val timeout: Timeout = 10.seconds
  // Actor system manages thread pool
val wordCountActorSystem = ActorSystem("WordCountActorSystem")
  // instantiate actor system
  val wordCountActor = wordCountActorSystem.actorOf(WordCountActor.props)
  val result =wordCountActor ! "Hi How are you"
}

class WordCountActor extends Actor{
  override def receive: Receive = {
    case msg:String => {
      msg.split("").length
      // context contains all info about a actor context.self => this in oop
      println(s"{${context.self.path}}"+msg.split("").length)
    }
    }

}

object WordCountActor{
  def props: Props = Props(new WordCountActor)
}