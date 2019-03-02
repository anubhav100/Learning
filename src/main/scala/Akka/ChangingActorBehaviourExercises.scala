package Akka

import Akka.CountActorPro.{Decrement, Increment, Print}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ChangingActorBehaviourExercises extends App {
  val actorSystem = ActorSystem("exe")
  /*val counterActor = actorSystem.actorOf(Props[CountActorPro])
  counterActor ! Increment
  counterActor ! Decrement
  counterActor ! Increment

  counterActor ! Print*/

  val alice = actorSystem.actorOf(Props[Citizen])
  val bob = actorSystem.actorOf(Props[Citizen])
  val anu = actorSystem.actorOf(Props[Citizen])

  alice ! "prince"
  bob ! "anubhav"
  anu ! "anubhav"

  val voteAggregator = actorSystem.actorOf(Props[VoteAggregator])
  voteAggregator ! AggregateVotes(Set(alice, bob, anu))
  // final result should be a map
}

object CountActorPro {

  case object Increment

  case object Decrement

  case object Print

}

class CountActorPro extends Actor {
  override def receive: Receive = countRecieved(0)

  def countRecieved(currentCount: Int): Receive = {
    case Increment =>
      println("[incrementing] the counter")
      context.become(countRecieved(currentCount + 1), false)
    case Decrement =>
      println("[decrementing] the counter")
      context.become(countRecieved(currentCount - 1), false)
    case Print => println(s"Final count is $currentCount")
  }
}

case class Vote(name: String)
case class AggregateVotes(citizens: Set[ActorRef])
case object VoteStatusRequest
case class VoteStatusReply(candidate: Option[String])

class Citizen extends Actor {
  override def receive: Receive = ???
}
class VoteAggregator extends Actor {
  override def receive: Receive = ???
}
