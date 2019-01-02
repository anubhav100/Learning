package scalaordering

object ScalaEqualsRunner extends App {

  /** Difference between equals and == in java vs scala
    * in java == checks for references and equals checks for values
    * in scala == is used for null check and then it calls equals which check values and for refernce types we used eq
    */
  val demo1 = new Demo(1)
  val demo2 = new Demo(1)
  println(demo1.equals(demo2))
  val demo3 = demo2
  println(demo2.eq(demo3))
  println(demo2.eq(demo1))

}
class Demo(val value:Int){

  def canEqual(other: Any): Boolean = other.isInstanceOf[Demo]

  override def equals(other: Any): Boolean = other match {
    case that: Demo =>
      (that canEqual this) &&
        value == that.value
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(value)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}