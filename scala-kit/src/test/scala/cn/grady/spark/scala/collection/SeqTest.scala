package cn.grady.spark.scala.collection

import scala.collection.mutable

/**
 * @author rociss 
 * @version 1.0, on 11:16 2022/7/4.
 */
object SeqTest {


  def linkListTest(): mutable.LinkedList[Int] = {
    val list = mutable.LinkedList(1, 2, 3, 4, 5, 6, 7, 8)
    var currentList = list
    var first = true
    while (currentList != Nil && currentList.next != Nil) {
      if (first) {
        // elem 表示第一个元素
        currentList.elem = currentList.elem * 2
        first = false
      }
      currentList = currentList.next.next
      if (currentList != Nil) currentList.elem = currentList.elem * 2
    }
    list

  }

  def setTest(): mutable.Set[Int] = {
    val s = mutable.HashSet()
    val s1 = mutable.LinkedHashSet(1, 3, 5)
    s1 += 6
    println(s"s1 = ${s1}")
    val s2 = mutable.SortedSet("orange", "apple", "banana")
    println(s2)

    s1

  }

  def highFuncTest(): Unit = {
    val list = List("hello world", "hello me ", "hello you")

    println(list.flatMap(_.split(" ")))

    val nameList = List("leo","jen")
    val scoreList = List(100,90)
    println(nameList.zip(scoreList))
  }

  def fileTest():Unit={
    val line1 = scala.io.Source.fromFile("").mkString
    val line2 = scala.io.Source.fromFile("").mkString
    val lines = List(line1,line2)

    println(lines.flatMap(_.split(" ")).map((_,1)).map(_._2).reduceLeft(_+_))

  }

  def main(args: Array[String]): Unit = {

    val list = List(1, 2, 3, 4, 5)
    list.foreach(println(_))
    println(list.head)
    println(list.tail)

    //:: 合并两个list
    println(0 :: list)

    highFuncTest()
  }
}
