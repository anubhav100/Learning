package Akka

import Akka.Kid.{Accept, Reject}
import Akka.Mom.{Ask, Food, MomStartRef}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ChangingActorBehaviour extends App{

  val actorSystem = ActorSystem("change")
  val momActor = actorSystem.actorOf(Props[Mom])
  val kidActor = actorSystem.actorOf(Props[Kid])

  momActor ! MomStartRef(kidActor)
}

object Mom {
  case class Food(foodName:String)
  case class Ask(msg:String)
  val VEG = "veg"
  val CHOCLATE = "choco"
  case class MomStartRef(kidRef:ActorRef)
}
class Mom extends Actor{
  import Mom._
  override def receive: Receive = {
    case MomStartRef(kidRef)=>
     kidRef ! Food(VEG) // 1.sad recieved
      kidRef ! Food(VEG)//1.sad 2.sad
      kidRef ! Food(CHOCLATE)//1.happy 2.sad 3.sad
      kidRef ! Food(CHOCLATE)//1.happy 2.happy 3.sad 4.sad
      kidRef !Ask("do you want to play")
    case Accept => println("kid is happy")
    case Reject => println("kid is not happy")
  }
}

object Kid {
case object Accept
case object Reject
}
class Kid extends Actor {
  import Kid._
  import Mom._
  override def receive: Receive =happyReceive

    def happyReceive:Receive ={
      case Food(VEG) => context.become(sadReceive,false)
      // false means don't discard the old handler but put it on stack *
      // - if `discardOld = true` it will replace the top element (i.e. the current behavior)
      //   *  - if `discardOld = false` it will keep the current behavior and push the given one on top
      case Food(CHOCLATE) => // stay happy
      case Ask(_) => sender() ! Accept
    }
  def sadReceive:Receive ={
    case Food(VEG ) => // stay sad
    case Food(CHOCLATE) => context.become(happyReceive,false)
    case Ask => sender() ! Reject

  }

  /**
    * original handler = happy recieved
    * Food(Veg) => stack.push(sadRecieved)
    * stack
    * 1.happyRecieved => 1.sadRecieved 2.HappyRecieved
    * Food(CHOLCALTE) => stack.push(happyRecieved) => 1.happyRecieved,2.sadRecieved 3.HappyRecieved
    */

}