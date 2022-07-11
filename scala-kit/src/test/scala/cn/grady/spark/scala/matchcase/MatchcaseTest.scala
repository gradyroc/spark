package cn.grady.spark.scala.matchcase

import java.io.FileNotFoundException

/**
 * @author rociss 
 * @version 1.0, on 23:30 2022/7/4.
 */

/**
 * match case  等价与java的switch case
 * 而且 scala的模式匹配可以匹配各种情况：
 * 变量的类型，集合的元素、有值或无值
 * match {case 值 => 代码}，如果值为_ 代表不满足以上所有情况的默认情况如何处理
 * 而且scala的模式匹配 不需要break 阻止继续执行（跳出）
 */
object MatchcaseTest {

  def judgeGrade(name: String, grade: String) = {
    grade match {
      case "A" => println("Excellent")
      case "B" => println("Good")
      case "C" => println("Just so so ")
      //if 守卫，对条件进行细化
      // 当进入_ 默认情况下，想获取到具体的值进行处理，
      // 则在模式匹配中进行变量赋值,_加参数名
      case _grade if name == "leo" =>
        println(name + ", you are a good boy ,come on,your grade is " + _grade)
      case _grade => println(" you need work harder，your grade is ：" + _grade)
    }
  }

  /**
   * 对类型进行模式匹配
   * case 变量：类型 => 代码
   *
   * @param e
   */
  def processException(e: Exception): Unit = {
    e match {
      case e1: IllegalArgumentException => println("you passed illegal argument , exception is :" + e1)
      case e2: FileNotFoundException => println("can not find the file, exception is :" + e2)
      case _: Exception => println("exception occurs .")
    }
  }

  def matchCaseArray(arr: Array[String]) = {
    arr match {
      //指定元素
      case Array("leo") => println("how are you ,leo !!")
      // 指定元素个数
      case Array(girl1, girl2, girl3) => println("how are you ,nice to meet you. " + girl1 + "," + girl2 + "," + girl3)
      // 指定元素打头的数组
      case Array("leo", _*) => println("hi leo ,why not introduce your friends to me !")
      case _ => println("hey ,who are you ?")
    }
  }

  //对List 进行模式匹配
  def matchCaseList(arr: List[String]) = {
    arr match {
      //指定元素
      case "leo" :: Nil => println("how are you ,leo !!")
      // 指定元素个数
      case girl1 :: girl2 :: girl3 => println("how are you ,nice to meet you. " + girl1 + "," + girl2 + "," + girl3)
      // 指定元素打头的数组
      case "leo" :: tail => println("hi leo ,why not introduce your friends to me !")
      case _ => println("hey ,who are you ?")
    }
  }

  /**
   * case class（javaBean） 与模式匹配
   *
   * @param args
   */

  class Person

  case class Teacher(name: String, subject: String) extends Person

  case class Student(name: String, classroom: String) extends Person

  case class Worker(name: String, works: String) extends Person

  def judgeIdentify(p: Person) = {
    p match {
      case Teacher(name, subject) => println("Teacher:" + p)
      case Student(name, classroom) => println("Student :" + p)
      case _p => println(s"illegal access ,please go out of the school _ = ${_p}")
    }
  }

  /**
   * Option 与模式匹配
   * scala的特殊类型 Option ，有两种值，Some 表示有值，None 表示没有值
   * Option 通常用在模式匹配中，判断某个变量有没有值，比null 更明了
   */
  def getGrade(name: String) = {
    val grades = Map("leo" -> "A", "jack" -> "B")
    val grade = grades.get(name)
    grade match {
      case Some(x) => println("your grade is " + x)
      case None => println("Sorry ,your grade information is not in the system")
    }
  }

  def main(args: Array[String]): Unit = {
    judgeGrade("grady", "B")
    judgeGrade("leo", "D")
    judgeGrade("leo", "A")

    //类型的模式匹配
    processException(new IllegalArgumentException("xxxxxxxxxxxxxxxx"))

    // 对Array 和List 进行模式匹配
    // 分别可以匹配带有指定元素的数组，带有指定个数元素的数组，以某元素打头的数组
    // 对List 进行模式匹配，与Array类似，但是需要使用List 特有的 :: 操作符

    matchCaseArray(Array("jack"))
    matchCaseList(List("grady"))

    //case class 与模式匹配
    judgeIdentify(Teacher("grady", "math"))
    judgeIdentify(Worker("grady", "math"))

    getGrade("leo")
    getGrade("grady")


  }
}
