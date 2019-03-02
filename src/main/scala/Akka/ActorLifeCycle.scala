package Akka

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

object ActorLifeCycle extends App {

  /**
    * Difference between actor and actor ref
    * Actor-it has methods and internal state
    * ActorRef- it has mailbox and can receive messages,it contains one actor instance and one uuid
    */
  /**
    * Actor Life Cycle
    * started - Actor ref is create at a given path
    * stopped - message of actorref will enqueque but not processed
    * resume- actor can processed the message again
    * Restart - old actor instance is replaced with new actor instance
    * old actor instance will call prestart and so internal state of old actor instance is destroyed
    * and then actor is restarted and then new actor instance will call poststart method of actor
    */
  object Fail

  class SupParent extends Actor with ActorLogging {
    private val child = context.actorOf(Props[SupChild], "supchild")
    override def receive: Receive = {
      case Fail => child ! Fail
    }
  }
  class SupChild extends Actor with ActorLogging {
    override def receive: Receive = {
      case Fail =>
        log.warning("child will fail now")
        throw new RuntimeException("I m stopped")
      case message => log.info(message.toString)
    }
  }
  val actorSystem = ActorSystem("supParent")
  val parent = actorSystem.actorOf(Props[SupParent], "supparent")
  parent ! Fail
  val child = actorSystem.actorSelection("/user/supparent/supchild")
  child ! "Hi"

  /**
  * Default supervision strategy will restart actor even if it failed by exception
  */
}
