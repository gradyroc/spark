package cn.grady.spark.scala.template

/**
 * @author rociss 
 * @version 1.0, on 0:31 2022/7/6.
 */
object FuncTemplateTest_2 {

  def getCard[T](content: T) = {
    if (content.isInstanceOf[Int]) "card:001," + content
    else if (content.isInstanceOf[String]) "card: is your card," + content
    else "card:" + content
  }

  def main(args: Array[String]): Unit = {
    println(getCard(100))
    println(getCard("hello"))
    println(getCard[Int](1000))
  }

}
