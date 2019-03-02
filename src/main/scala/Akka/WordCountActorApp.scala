package Akka

import Akka.WordCounterMaster.{Initialize, WordCountReply, WordCountTask}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.pattern.{ask, pipe}
import akka.util.Timeout

import scala.concurrent.duration._

object WordCountActorApp extends App {
  val testActorSystem = ActorSystem("testActor")
  val testActor = testActorSystem.actorOf(Props[TestActor])
  testActor ! "go"
}

object WordCounterMaster {
  case class Initialize(nchildren: Int)
  case class WordCountTask(id: Int, task: String)
  case class WordCountReply(id: Int, count: Int)
}
class WordCounterMaster extends Actor {
  override def receive: Receive = {
    case Initialize(nchildren: Int) =>
      val childs = for { i <- 1 to nchildren } yield
        context.actorOf(Props[WordCounterWorker], s"worker$i")
      context.become(childActor(childs.toList, 0, 0, Map()))
  }
  def childActor(childs: List[ActorRef],
                 currentChildIndex: Int,
                 currentTaskIndex: Int,
                 requestMap: Map[Int, ActorRef]): Receive = {
    case task: String =>
      // current index is keeping track of index of actor refs
      // original sender is here so keep the track of sender here
      println(
        s"[master] i have recieved the task $task and i am sending it to child $currentChildIndex")
      val originalSender = sender()

      val taskToSend = WordCountTask(currentTaskIndex, task)
      val newRequestMap = requestMap + (currentTaskIndex -> originalSender)
      childs(currentChildIndex) ! taskToSend
      context.become(
        childActor(childs,
                   (currentChildIndex + 1) % childs.length,
                   currentTaskIndex + 1,
                   newRequestMap)) // mode is for round robing

    case WordCountReply(id: Int, count: Int) =>
      println(
        s"[master] i have recieved the reply for task $id with count $count")
      val sender = requestMap(id)
      sender ! count
      context.become(
        childActor(
          childs,
          (currentChildIndex + 1) % childs.length,
          currentTaskIndex + 1,
          requestMap - id)) // sender() ! count we can't use sender here because sender will point to child actor ref so we shouldn't
  }
}
class WordCounterWorker extends Actor {
  override def receive: Receive = {
    case WordCountTask(id: Int, text: String) =>
      println(s"[Worker] i have recieved task $id with  $text")
      sender() ! WordCountReply(id, text.split("").length)
  }
}
///val data = List("Hi","Hello")
// data.foreach(currentIndexData => actor ! currentIndexData)
// for each bang of message we have a different sender
// "HI" -> "childActor1" -> 2 ,"Hello" -> "childActor2" -> 3

class TestActor extends Actor {
  override def receive: Receive = {
    case "go" =>
      val master = context.actorOf(Props[WordCounterMaster], "master")
      master ! Initialize(3)
      val data = List("HELLO", "HI", "ME TOO")
      data.foreach(x => master ! x)
    case count: Int => println(s"[TestActor] i recieved a reply $count")
  }
}
