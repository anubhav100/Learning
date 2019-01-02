package Akka

import akka.actor.{Actor, ActorSystem, Props}

import scala.concurrent.{ExecutionContext, Future}

object ThreadModelLimitations extends App {

  /**  1.OOP Encapsulation violation
    *
    */

  class BankAccount(private var amount:Int){

    def deposit(money:Int) = amount = amount + money

    def withDraw(money:Int) = amount = amount - money

    def get = amount

  }
  val bankAccount = new BankAccount(2000)



  /*for(_ <- 1 to 1000) {
    val thread1 = new Thread() {
      override def run(): Unit = bankAccount.deposit(1)

    }
    thread1.start()

  }
  for(_ <- 1 to 1000) {
    val thread2 = new Thread() {
      override def run(): Unit = bankAccount.withDraw(1)

    }
    thread2.start()

  }*/

  // even the below future sucks in this case

 /* import ExecutionContext.Implicits.global

  Future{
    bankAccount.deposit(1)
  }
  Future{
    bankAccount.withDraw(11)
  }*/

  /** 2. Delegation of task from one thread to another thread is pain
    *
    */

  /** 3. Tracing and dealing with error is painfull
    *
    */


  /** Actor System comes to the rescue here
    * it will give correct value
    */
  val actorSystem = ActorSystem("BankAccountActor")

  val bankDepositAccountActor = actorSystem.actorOf(BankDepositAccountActor.props)
  val bankWithDrawAccountActor = actorSystem.actorOf(BankWithDrawAccountActor.props)

  bankDepositAccountActor ! ""
  bankWithDrawAccountActor ! ""
   Thread.sleep(2000)
  println(bankAccount.get)



  class BankWithDrawAccountActor extends Actor{
    override def receive: Receive = {
      case _ =>
        println(s"withDrawing money")
         bankAccount.withDraw(11)
        println("i have withDraw money")
      }
    }

  object BankWithDrawAccountActor{
    def props = Props(new BankWithDrawAccountActor)
  }

  class BankDepositAccountActor extends Actor{
    override def receive: Receive = {
      case _ =>
        println(s"Depositing money")

        bankAccount.deposit(1)
        println("i have deposited money")

    }
  }

  object BankDepositAccountActor{
    def props = Props(new BankDepositAccountActor)
  }

  }

