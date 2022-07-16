package cn.grady.spark.scala.traittest

/**
 * @author rociss 
 * @version 1.0, on 0:13 2022/7/1.
 */
class Person1(val name: String) extends SignatureValidHandler with DataValidHandler {
  def sayHello = {
    println("hello ," + name);
    handle(name)
  }

}
