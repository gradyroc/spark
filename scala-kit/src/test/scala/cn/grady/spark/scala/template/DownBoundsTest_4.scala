package cn.grady.spark.scala.template

import cn.grady.spark.scala.template.UpBoundsTest_3_5.PersonUpBound

/**
 * @author rociss 
 * @version 1.0, on 0:55 2022/7/6.
 */
object DownBoundsTest_4 {

  class Father(val name: String)

  class Child(name: String) extends Father(name)

  def getIdCard[R >: Child](p: R): Unit = {
    if (p.getClass == classOf[Child]) println("please tell us your parents name ")
    else if (p.getClass == classOf[Father]) println("please sign your name to get your child's lost id card")
    else println("sorry,you are not allowd to get this id card.")
  }

  def main(args: Array[String]): Unit = {
    val leo = new Father("leo")
    val tom = new Child("tom")
    val jack = new PersonUpBound("jack")
    getIdCard(leo)
    getIdCard(tom)
    getIdCard(jack)
  }
}
