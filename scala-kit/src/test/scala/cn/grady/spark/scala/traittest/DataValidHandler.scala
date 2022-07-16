package cn.grady.spark.scala.traittest

/**
 * @author rociss 
 * @version 1.0, on 0:11 2022/7/1.
 */
trait DataValidHandler extends Handler {
  override def handle(data: String): Unit = {
    println("check data:" + data)
    super.handle(data)
  }
}
