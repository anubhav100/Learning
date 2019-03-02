package streams

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}

object FirstPrinciple extends App {
 implicit val actorSystem: ActorSystem = ActorSystem("akkastreams")
  implicit val materilizer: ActorMaterializer = ActorMaterializer()
 val source = Source(1 to 10)
  val sink = Sink.foreach(println)
  val graph = source.to(sink)
  graph.run()
}
