package cn.grady.spark.scala.actors

import scala.actors.Actor


/**
 * @author rociss 
 * @version 1.0, on 0:21 2022/7/14.
 */
object Actor_login_Register {

  /**
   * scala actor 天然支持线程之间的精准通信，即 一个actor 可以给其他actor直接发送消息
   *
   * scala 中，通常建议使用样例类，即case class 来作为消息进行发送，然后在actor 接收消息之后，用scala的模式匹配来处理消息
   *
   *
   */

  case class Login(userName: String, password: String)

  case class Register(username: String, password: String)

  class UserManageActor extends Actor {
    override def act(): Unit = {
      while (true) {
        receive {
          case Login(userName, password) => println("login ,username is " + userName + ",password is " + password)
          case Register(userName, password) => println("register ,username is " + userName + ",password is " + password)
        }
      }
    }
  }


  /**
   * 两个Actor之间发送消息:
   * 一个actor 向另一个actor发送消息时，同时带上自己的引用，回复消息时可以直接使用
   *
   *
   * etc：打电话
   */
  case class Message(content: String, sender: Actor)

  class LeoTelephoneActor extends Actor {
    override def act(): Unit = {

      while (true) {
        receive {
          case Message(content, sender) => {
            println("leo telephone :" + content);
            sender ! "I'm leo ,please call me 10 minutes later !"
          }
        }
      }
    }
  }

  class JackTelephoneActor(val leoTelephoneActor: Actor) extends Actor {
    override def act(): Unit = {
      leoTelephoneActor ! Message("hello ,Leo,I'm Jack.", this)

      receive {
        case response: String => println("jack telephone :" + response);

      }
    }
  }

  /**
   * 同步消息和Future：
   *  默认情况下，消息都是异步的；
   *        如果是同步消息 需要等待返回结果 ！？ 方式发送消息
   *              etc：val reply = actor ！？ message
   *        如果是异步消息，但是需要后续获得消息的返回值，则使用future 即 ！！
   *              etc：val future = actor ！！ message
   *                   val reply = future()
   *
   *
   */

  def main(args: Array[String]): Unit = {
    val userManageActor = new UserManageActor
    userManageActor.start()

    userManageActor ! Register("leo", "1234")
    userManageActor ! Login("leo", "1234")

    println("======================================")
    //打电话
    val leoTelephoneActor = new LeoTelephoneActor
    leoTelephoneActor.start()

    val jackTelephoneActor = new JackTelephoneActor(leoTelephoneActor)
    jackTelephoneActor.start()

  }

}
