package cn.grady.spark.scala

/**
 * @author rociss 
 * @version 1.0, on 0:18 2022/7/4.
 */
object HighFuncTest {

  def hello(name: String) = {
    println("hello" + name)
  }

  /**
   * 定义高阶函数
   *
   * @param func  : 参数函数的名称func: (参数函数的类型String) => 参数函数的返回值Unit
   * @param name2 ：greeting函数的参数
   */
  def greeting(func: (String) => Unit, name2: String) {
    func(name2)
  }

  /**
   * 返回函数的 高阶函数
   *
   * @param msg ：函数的参数
   * @return 返回值为函数: 函数的入参(name:String)=>
   *         返回值的函数体： println(msg+","+name)
   */
  def getGreetingFunc(msg: String) = (name: String) => println(msg + "," + name)

  def triple(func: (Int) => Int) = {
    func(3)
  }

  def main(args: Array[String]): Unit = {
    // 函数赋值给变量的方法
    val sayHelloFunc = hello _
    sayHelloFunc("gradyzhou")

    //直接定义匿名函数
    //    （参数名：参数类型）=> 函数体
    val sayHello1 = (name1: String) => println(name1)
    sayHello1("zhou")

    //高阶函数：
    //    1：接收其他函数作为参数的函数，称作高阶函数
    //    2：另一个功能是将函数作为返回值
    //1:高阶函数的使用
    greeting(sayHelloFunc, "holy")
    greeting((name: String) => println("hello," + name), "holy")
    // <==>
    greeting(name => println("hello + " + name), "holy")

    //接收匿名函数 ：(num: Int) => num * num
    //如果右侧的参数只使用一次，则可以用_ 代替
    Array(1, 2, 3, 4, 5).map(2 * _).foreach(println(_))
    //2:返回函数的高阶函数的使用
    val greetingFunHi = getGreetingFunc("hello")
    greetingFunHi("leo")

    println(triple(3 * _))

    (1 to 9).map("*" * _).foreach(println(_))

  }
}
