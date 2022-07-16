package cn.grady.spark.scala.traittest

/**
 * @author rociss 
 * @version 1.0, on 0:12 2022/7/1.
 */
trait SignatureValidHandler extends Handler {
  override def handle(data: String): Unit = {
    println("check signature:" + data)
    super.handle(data)
  }
}
