package cn.grady.spark.scala

/**
 * @author rociss 
 * @version 1.0, on 23:19 2022/7/4.
 */
object CurryingTest {

  def sum(a:Int,b:Int)=a+b

  /**
   *currying  函数指的是，将原来接收两个参数的函数，转换为两个函数
   * 第一个函数接收原先的第一个参数，然后返回接收原先第二个参数的第二个函数
   * 在函数调用过程中，就变成了两个函数连续调用的形式
   */
  def sum1(a:Int)=(b:Int)=>a+b

  def sum2(a:Int)(b:Int)=a+b

  def main(args: Array[String]): Unit = {

  }

}
