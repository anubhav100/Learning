package scalaconcurrent

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future, Promise}

object Promises extends App {

  import ExecutionContext.Implicits.global

  val future1 = Future {
    Thread.sleep(150)
    3
  }
  val future2 = Future {
    Thread.sleep(1000)
    30
  }
  val result = last[Int](future1, future2)

  def first[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val promise = Promise[A]

    fa.onComplete(result => promise.tryComplete(result))

    fb.onComplete(result => promise.tryComplete(result))

    promise.future
  }

  def last[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val promise1 = Promise[A]
    val promise2 = Promise[A]

    fa.onComplete { result =>
      if (!promise1.tryComplete(result)) promise2.tryComplete(result)
    }
    fb.onComplete { result =>
      if (!promise1.tryComplete(result)) promise2.tryComplete(result)
    }
    promise2.future
  }

  def retryUntil[T](action: () => Future[T],
                    condition: T => Boolean): Future[T] = {
    action().
      filter(condition).
      recoverWith {
        case _ => retryUntil(action, condition)
      }
  }

  val action =()=>{ Future(List("Prince","Anubhav"))}
  val condition= (x:List[String]) => if(x.contains("Anubhav")) true else false

 val result2 = retryUntil(action,condition)

  println(Await.result(result2, 2.seconds))
}
