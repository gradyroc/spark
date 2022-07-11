package cn.grady.spark.scala.template

/**
 * @author rociss 
 * @version 1.0, on 1:20 2022/7/6.
 */
object ContextBoundsTest_6 {
  /**
   * 特殊的Bounds，根据泛型类型的声明，比如 T:类型，要求必须存在一个类型为 类型[T]的隐式值
   * 基于一种全局的上下文，需要使用上下文中的隐式值以及注入
   * etc：使用scala内置的比较器
   */
  class Calculator[T: Ordering](val n1: T, val n2: T) {
    def max(implicit ordering: Ordering[T]) =
      if (ordering.compare(n1, n2) > 0) n1 else n2
  }

  def main(args: Array[String]): Unit = {
    val cal = new Calculator(1, 2)
    println(cal.max)
  }

}
