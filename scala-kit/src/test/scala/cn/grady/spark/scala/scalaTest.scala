package cn.grady.spark

import cn.grady.spark.scala.traittest.Person1

/**
 * @author rociss 
 * @version 1.0, on 0:01 2022/7/1.
 */

trait Logged {
  def log(msg: String) {}
}

trait MyLogger extends Logged {
  override def log(msg: String): Unit = {
    println("log:" + msg)

  }
}

class Person(val name: String) extends Logged {
  def sayHello {
    println("Hi,I'm " + name)
    log("sayHello is invoked!")
  }
}

object scalaTest {

  def main(args: Array[String]): Unit = {

    val  p1  = new Person("leo")
    p1.sayHello

    //指定某个对象混入某个trait，则只有这个对象混入该trait的方法，而其他对象则没有
    val  p2 = new Person("grady") with MyLogger
    p2.sayHello

    val p3 = new Person1("gradyroc")
    //责任链模式
    //    hello ,gradyroc
    //check data:gradyroc
    //check signature:gradyroc
    p3.sayHello
  }

}
