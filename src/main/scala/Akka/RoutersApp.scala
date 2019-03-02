package Akka

import akka.actor.{Actor, ActorLogging, ActorSystem, Props, Terminated}
import akka.routing._

object RoutersApp extends App {
  val actorSystem = ActorSystem("router")
  val master = actorSystem.actorOf(Props[Master])

  /*for (i <- 1 to 10) {
    master ! s"${i}Hello World"
  }*/

  /** Method-2
    * PoolRouter  - A router actor with its own children
    */
  val poolMaster =
    actorSystem.actorOf(RoundRobinPool(5).props(Props[Slave]), "master")

  /*val poolMaster2 =
    actorSystem.actorOf(FromConfig.props(Props[Slave]), "poolMaster2")*/
  /* for (i <- 1 to 10) {
    poolMaster2 ! s"${i}Hello World"
  }*/

  /**
    * Method-3
    * Group Router- slave is create some where else
    */
  val slavePaths = ((1 to 5) map { i =>
    actorSystem
      .actorOf(Props[Slave], s"poolMaster$i")
      .path
      .toString
  }).toList

  val groupMaster = actorSystem.actorOf(RoundRobinGroup(slavePaths).props())
  for (i <- 1 to 10) {
    groupMaster ! s"${i}Hello World"
  }

  class Master extends Actor with ActorLogging {

    /**
      * Manual Router
      */
    // step 1 - create routes

    /*  private val slaves = for (i <- 1 to 5) yield {
    val slave = context.actorOf(Props[Slave], s"slave_$i")
    context.watch(slave)
    ActorRefRoutee(slave)
  }
  //
  step 2- create router
  private val router = Router(RoundRobinRoutingLogic(), slaves)*/

    override def receive: Receive = {
      case message =>
        log.info(s"i recieved the message {${message.toString}")

      //step 3 -route the message // now slave can directly reply to router with out involving master

      /*router.route(message, sender())

    // step 4 handle the life cycle of router

    case Terminated(ref) =>
      router.removeRoutee(ref)
      val newRoutee = context.actorOf(Props[Slave])
      router.addRoutee(newRoutee)
      context.watch(newRoutee)*/
    }
  }

  class Slave extends Actor with ActorLogging {
    override def receive: Receive = {
      case message => log.info(message.toString)
    }

  }

}
