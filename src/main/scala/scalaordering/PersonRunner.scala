package scalaordering

object PersonRunner extends App{
  // ordering refers to comparator which should be used in case of thrird party libraries need to be sorted
  //implicit  val sorting:Ordering[Int] = Ordering.fromLessThan((a,b) => a > b)
  //println(List(3,11,31,1).sorted)
  println(List(Person(18,"Anubhav"),Person(12,"prince"),Person(12,"pri")).sorted)
}

class Person(val age:Int,val name:String) extends Ordered[Person]{
  override def compare(that: Person): Int = {
    if(this.age > that.age)  1
    else if(this.age==that.age) 0 else -1
  }
}
object Person{
  def apply(age: Int, name: String): Person = new Person(age, name)
}