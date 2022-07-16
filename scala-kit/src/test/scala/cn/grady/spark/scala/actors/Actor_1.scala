package cn.grady.spark.scala.actors

import scala.actors.Actor


/**
 * @author rociss 
 * @version 1.0, on 23:56 2022/7/13.
 */

/**
 * actor 类似于java中的多线程编程，却有所不同
 * scala的actor 尽可能的避免锁和共享状态，从而避免多线程并发时出现资源争用的情况，进而提升多线程性能
 * 此外，actor 这种模型可以避免死锁等一系列传统多线程编程的问题
 *
 * spark中使用的是分布式多线程框架、AKKa，基于actor的框架
 *
 *
 *
 * scala 提供Actor trait 来方便使用，类似于java 中的Thread 和Runnable 一样，基础的多线程基类和接口
 * 只要重写Actor trait 的方法即可实现自己的线程执行体
 * 此外：
 * 1：使用start()方法启动actor；
 * 2：使用 ！ 符号，向actor 发送消息；
 * 3：actor 内部使用receive 和模式匹配接收消息
 *
 */

/**
 * etc1: actor 的创建，启动，消息收发
 *
 */

object Actor_1 {

  class HelloActor extends Actor {
    override def act(): Unit = {
      while (true) {
        receive {
          case name: String => println("hello ," + name)
        }
      }
    }
  }

  def main(args: Array[String]): Unit = {

    val helloActor = new HelloActor
    helloActor.start()
    helloActor ! "leo"
    helloActor.send("grady",null)

  }
}
