package Akka

import Akka.BankAccountActor.{Deposit, TransactionFailed, TransactionSuccess, WithDraw}
import Akka.Person.LiveTheLIfe
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object BankAccountActorApp extends App{
val actorSystem = ActorSystem("bankAccountActorSystem")
  val accountActor = actorSystem.actorOf(BankAccountActor.props)
  val person = actorSystem.actorOf(Props[Person])
  person ! LiveTheLIfe(accountActor)
}
class BankAccountActor(var amount:Int) extends Actor{
  override def receive: Receive = {
    case Deposit(addedAmount:Int) =>
      amount = addedAmount + amount
      sender() ! TransactionSuccess("amount is added ")
    case WithDraw(debitedAmount:Int) =>
      if(debitedAmount<amount) {
        amount = debitedAmount - amount
        sender() ! TransactionSuccess("amount is debited ")

      }
      else{
        sender() ! TransactionFailed("not enough balance")
      }
  }
}
object BankAccountActor {
  case class Deposit(amount:Int)
  case class WithDraw(amount:Int)
  case class TransactionFailed(msg:String)
  case class TransactionSuccess(msg:String)
  def props:Props = Props(new BankAccountActor(5000))
}

object Person {
  case class LiveTheLIfe(actor:ActorRef)
}

class Person extends Actor{
  override def receive: Receive = {
    case LiveTheLIfe(account) =>
      account !Deposit(100)
      account ! WithDraw(50)
    case message=> println(message.toString)
  }

}