package cn.grady.spark.scala.template

/**
 * @author rociss 
 * @version 1.0, on 0:39 2022/7/6.
 */
object UpBoundsTest_3_5 {
  /**
   * 上边界Bounds:
   * 指定泛型类型的时候，需要对泛型类型的范围进行界定，而不是任意的类型
   * etc：
   * 要求某个泛型类型，必须是某个类的子类，这样程序就可以放心的调用泛型类型继承的父类的方法，此时就需要上边界的特性
   *
   * 案例：派对上交朋友
   */

  class PersonUpBound(val name: String) {
    def sayHello = println("hello ,I'm " + name)

    def makeFriends(p: PersonUpBound): Unit = {
      sayHello
      p.sayHello
    }
  }

  class StudentUpbound(name: String) extends PersonUpBound(name)

  //上边界的泛型用法
  class Party[T <: PersonUpBound](p1: T, p2: T) {
    def play = p1.makeFriends(p2)
  }


  class WorkerTest(name: String)

  ///////////////////////////////////////////////////////////////////
  // View bounds是上下边界Bounds的加强版，支持可以对类型进行隐式转换，
  // 将指定的类型进行隐式转换后，再判断是否在边界指定的类型范围内
  // 跟小狗交朋友
  class Dog(val name: String) {
    def sayHello = println("wang wang,I'm " + name)
  }

  //View Bound 的用法,表示是 PersonUpBound的子类或者隐式转换之后为 PersonUpBound 的子类
  class Party1[T <% PersonUpBound](p1: T, p2: T) {
    def play = p1.makeFriends(p2)
  }

  implicit def dog2PersonUpBound(dog: Object): PersonUpBound =
    if (dog.isInstanceOf[Dog]) {
      val _dog = dog.asInstanceOf[Dog];
      new PersonUpBound(_dog.name)
    } else {
      Nil
    }

  def main(args: Array[String]): Unit = {
    val leo = new StudentUpbound("leo")
    val tom = new StudentUpbound("tom")
    val jack = new WorkerTest("jack")

    val party = new Party[PersonUpBound](leo, tom)
    party.play

    //编译报错
    //    val party_1 = new Party[PersonUpBound](leo,jack)

    val dog = new Dog("阿三")
    val party1 = new Party1[PersonUpBound](leo, tom)
    party1.play
    val party2 = new Party1[PersonUpBound](leo, dog)
    party2.play
  }
}
