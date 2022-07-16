package cn.grady.spark.scala.implicits


/**
 * @author rociss 
 * @version 1.0, on 11:07 2022/7/13.
 */
object ImplicitTest {

  /**
   * scala 的隐式转换：核心是定义隐式转换函数： implicit conversion  function
   * 定义的隐式转换函数，只要在编写的程序中引入，就会被scala 自动使用
   * scala 会根据隐式转换函数的签名，在程序中使用到隐式转换函数接收的 参数类型定义的对象时，
   * 会自动将其传入隐式转换函数，转换为另一种类型的对象并返回，这就是隐式转换
   *
   * 隐式转换函数不会手动调用，名字无所谓，只需在使用时对隐式转换函数进行导入，通常为one2one 的形式
   *
   */


  /**
   * 特殊售票窗口
   */

  class SpecialPerson(val name: String)

  class Student(val name: String)

  class Older(val name: String)

  implicit def object2SpecialPerson(obj: Object): SpecialPerson = {
    if (obj.getClass == classOf[Student]) {
      val stu = obj.asInstanceOf[Student];
      new SpecialPerson(stu.name)
    } else if (obj.getClass == classOf[Older]) {
      val older = obj.asInstanceOf[Student];
      new SpecialPerson(older.name)
    }
    else Nil
  }

  /**
   * 特殊售票窗口
   */
  var ticketNumber = 0

  def buySpecialTicket(p: SpecialPerson) = {
    ticketNumber += 1
    "T-" + ticketNumber
  }

  /**
   * 加强版特殊售票窗口
   */
  class TicketHouse {
    var ticketNumber = 0

    def buySpecialTicket(p: SpecialPerson) = {
      ticketNumber += 1
      "T-" + ticketNumber
    }
  }

  /**
   * 使用隐式转换加强现有类型：
   * 定义一个加强版的类，并定义互相之间的隐式转换，从而让源类在使用加强版的方法时，
   * 由scala 自动进行隐式转换为加强类，再调用该方法
   *
   * etc：超人变身
   */
  class Man(val name: String)

  class Superman(val name: String) {
    def emitLaser = println("emit a laster !")
  }

  implicit def man2Superman(man: Man): Superman = new Superman(man.name)


  /**
   * scala 默认使用两种隐式转换的作用域和导入：
   * 1：源类型或者目标类型的伴生对象内的隐式转换函数；
   * 2：当前程序作用域内的可以用唯一标识符表示的隐式转换函数
   * 如果隐式转换函数不在上述两种情况下的话，就必须手动import语法引入某个包下的隐式转换函数
   * 建议：仅仅在需要进行隐式转换的地方，比如某个函数或者方法内，
   * 这样可以缩小隐式转换函数的作用域，避免不需要的隐式转换
   *
   * 隐式转换的发生时机：
   * 1：调用某个函数，但是入参的类型和函数定义的接收参数类型不匹配（etc：特殊售票窗口）
   * 2：使用某个类型的对象，调用某个方法，而这个方法并不存在于该类型时（etc： 超人变身）
   * 3：使用某个类型的对象，调用某个方法，虽该类型有这个方法但是给的入参与方法定义的接收参数类型不匹配（etc：特殊售票窗口加强版）
   */

  /**
   * 隐式参数：
   * 指的是在函数或者方法中，定义一个用implicit 修饰的参数，
   * 此时scala 会尝试找到一个指定类型的，用implicit修饰的对象，即隐式值，并注入参数
   * scala 会在 两个范围内查找：
   * 1：在当前作用域内课件的val 或者var 定义的隐式变量；
   * 2：隐式参数类型的伴生对象内的隐式值
   *
   * etc：考生签到
   */

  class SignPen {
    def write(content: String) = println(content)
  }

  //定义隐式参数
  implicit val signPen = new SignPen

  def signForExam(name: String)(implicit signPen: SignPen): Unit = {
    signPen.write(name + " come to exam in time .")
  }

  def main(args: Array[String]): Unit = {
    val leo = new Student("leo")
    println(buySpecialTicket(leo))

    val ticketHouse = new TicketHouse
    println(ticketHouse.buySpecialTicket(leo))

    //加强类
    val grady = new Man("grady")
    grady.emitLaser

    signForExam("leo")
  }

}

