package Akka

import Akka.NaiveBankAccount.{Deposit, InitAccount, Print, WithDraw}
import akka.actor.{Actor, ActorSystem, Props}

object BankAccountRunner extends App {
  val system = ActorSystem("bankaccount")
  val bankAccountActor = system.actorOf(Props[NaiveBankAccount])
  bankAccountActor ! Deposit(500)
  bankAccountActor ! Print
}

object NaiveBankAccount {
  case class WithDraw(amt: Int)
  case class Deposit(amt: Int)
  case object InitAccount
  case object Print
}
class NaiveBankAccount extends Actor {
  override def receive: Receive = transAction(0)

  def transAction(amt: Int): Receive = {
    case WithDraw(amount: Int) => context.become(transAction(amount + amt))
    case Deposit(amount: Int)  => context.become(transAction(amount - amt))
    case InitAccount           => context.become(transAction(0))
    case Print                 => println(s"balance is $amt")
  }
}
// never pass this as parameter to any child actor because it will give access to actor jvm object
