package cn.grady.spark.scala.template

/**
 * @author rociss 
 * @version 1.0, on 1:47 2022/7/6.
 */
object CoProcessNonCoProcessTest_8 {
  /**
   * scala的协变 和逆变
   * 解决java中的泛型的一大缺陷
   * 举例：java 中，Professional 是Master的子类，那么 Card[Professional] 却不是Card[Master]的子类
   *
   * scala 中灵活使用协变和逆变，就可以解决上诉问题
   * etc：进入会场
   */

  class Master

  class Professional extends Master

  /**
   * 协变：[+T]
   *
   * 经过协变之后：
   * Professional 是Master的子类，则 Card[Professional] 就是Card[Master]的子类
   *
   */
  class CardCo[+T](val name: String) {
  }

  //大师以及大师级以下的名片都可以进入会场
  def enterMeetingCon(card: CardCo[Master]): Unit = {
    println("welcome to have this meeting !" + card.name)
  }

  /**
   * 逆变：[-T]
   *
   * 经过逆变之后：方向相反
   * Professional 是Master的子类，则 Card[Master] 就是Card[Professional]的子类
   *
   */
  class CardNon[-T](val name: String)

  //只要专家级别的名片就可以进入会场，如果大师级别的过来更可以
  def enterMeetingNon(card: CardNon[Professional]): Unit = {
    println("welcome to have this meeting !" + card.name)
  }

  def main(args: Array[String]): Unit = {

    val leo = new CardCo[Master]("leo")
    val tom = new CardCo[Professional]("tom")
    enterMeetingCon(leo)
    enterMeetingCon(tom)
    println("==============")
    val jack = new CardNon[Professional]("jack")
    val jucy = new CardNon[Master]("jucy")
    enterMeetingNon(jack)
    enterMeetingNon(jucy)


  }

  /**
   * existential type
   * scala 中一种特殊的类型参数，
   * Array[T] forSome{type T}
   * 等价于：Array[_]
   */
}
