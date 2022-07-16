package cn.grady.spark.scala.traittest.template

/**
 * @author rociss 
 * @version 1.0, on 0:51 2022/7/1.
 */

/**
 * 具体方法依赖于抽象方法，而抽象方法则放到继承trait的类中去实现
 * 这种trait 就是设计模式中的模板设计模式
 */

/**
 * trait的构造机制： 不接收参数的构造
 * 1：父类的构造函数执行
 * 2：trait的构造代码执行，多个trait从左到右依次执行；
 * 3：构造trait时会先构造父trait，如果多个trait继承同一个父trait。则父trait只会构造依次
 * 4：所有trait构造完毕之后，子类的构造函数执行
 */
trait Valid {

  def getName:String

  def valid :Boolean={
    getName == "leo"
  }
}
