package NinetyNineProblems

import scala.annotation.tailrec
import scala.collection.immutable
import scala.collection.mutable.ListBuffer

object Exercises extends App {

  val list = List(1,2,2,2,2,3,3,4,4,5,6,5)

  @tailrec
  def reverseList[A](list:List[A],result:List[A]=Nil):List[A]={
    list match{
      case Nil => result
      case head::tail => reverseList(tail,head :: result)
    }
  }

  def removeConsecutiveDuplicates[A](list:List[A]): List[A] = {
    list.sliding(2).toList.collect{
      case List(a,b) if a!=b => a
    }
  }

  def packConsecutiveDuplicates(list:List[Char]): immutable.Seq[List[Char]] ={
    list.foldLeft(List(List[Char]())){
      case(current,next) => current.headOption.flatMap{_.headOption} match {
        case Some(value) if value == next => (current.head :+ next) +:current.tail
        case _ => List(next) +: current
      }
      }
    }

  val duplicateEleList = List('a', 'a', 'a', 'a', 'b', 'c', 'c', 'a', 'a', 'd', 'e', 'e', 'e', 'e')

  def runLengthEncoding(list:List[Symbol]):List[Any] = {
    list.groupBy(identity).mapValues(_.size).toList.map{
      case(symbol,value) => if(value==1){symbol} else (symbol,value)
    }
  }
  def decode(list:List[(Symbol,Int)]) ={
    list.flatMap{
      case(symbol,value) => List.fill(value) (symbol)
    }

  }
 // println(decode(List(('a,4),('b,5))))

  @tailrec
  def encodeDirect(list:List[Symbol],result:List[(Int,Symbol)]=Nil):List[(Int,Symbol)]= {
    list match {
      case Nil => result
      case head::tail =>
        val lengthOfLastMatch = tail.takeWhile(_ == head).length
        encodeDirect(tail.slice(lengthOfLastMatch,list.length),result :+ (lengthOfLastMatch+1,head))
    }

  }
  def dropEveryNthElement(list: List[Symbol],indextoDrop:Int):List[Symbol]={
    list.zipWithIndex.collect{
      case(value,index) if index != indextoDrop => value
    }
  }

  def rotate(list:List[Symbol],places:Int):List[Symbol] = {

    def rotateLeft(listToPerform: List[Symbol], result: List[Symbol] = Nil): List[Symbol] = {
      listToPerform.slice(places, listToPerform.length) ::: listToPerform.slice(0, places)
    }

    def rotateRight(listToPerform: List[Symbol], result: List[Symbol] = Nil): List[Symbol] = {
      listToPerform.slice(listToPerform.length-1-(-places), listToPerform.length+21) ::: listToPerform.slice(0, listToPerform.length-(-places))
    }
    if (places > 0) rotateLeft(list) else rotateRight(list)

  }

  def removeElementFromIndex(indexToBeDeleted:Int,list:List[Symbol])= {
    list.zipWithIndex.collect{
      case (a,b) if b!=indexToBeDeleted => a
    }
  }

  def insertEleAtPos(element:Symbol,postion:Int,list:List[Symbol]) = {
    list.zipWithIndex.foldLeft(List[Symbol]()){
      case(current,(next,index)) =>if(index==postion) current:+element else current:+next
    }
  }

  def sortListBySublistLength(list:List[List[Symbol]]) = {
    list.sortBy{
      innerList => innerList.length
    }
  }
  def sortListBySublistLengthFreq(list:List[List[Symbol]]) = {

    list.sortWith{
      case(lt1,lt2) => lt1.length>lt2.length
    }
    }

  def isPrime(number:Int)={
  val result = (2 to number/2) count(ele => number %ele ==0)
    result <0
}

  def findGcd(number1:Int,number2:Int) = {
    if(number1>number2) {
      ((1 to number2).toList filter {ele => number1%ele==0 && number2%ele==0}).max
    }
    else{
      ((1 to number1).toList filter {ele => number1%ele==0 && number2%ele==0}).max
    }
  }


  println(findGcd(5,81))

//    println(sortListBySublistLengthFreq(List(List('a, 'b, 'c), List('d, 'e), List('f, 'g, 'h), List('d, 'e), List('i, 'j, 'k, 'l), List('m, 'n), List('o))))


}
