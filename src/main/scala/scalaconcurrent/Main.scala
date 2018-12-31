package scalaconcurrent

import java.util.Calendar

import scala.concurrent._

object Main extends App {
  for (x <- 1 to 100) {
    import ExecutionContext.Implicits.global // it internally use the fork join pool which creates threads equals to cpu with work stealing ability
    // optional implicit val ec = ExecutionContext.fromExecutorService(Executors.newSingleThreadExecutor)
    val f = Future {
      val sleepTime = 2000
      Thread.sleep(sleepTime)

      val today = Calendar.getInstance().getTime()
      println("Future: " + x + " - sleep was: " + sleepTime + " - " + today)
      1;
    }
  }

  Thread.sleep(10000)
}
