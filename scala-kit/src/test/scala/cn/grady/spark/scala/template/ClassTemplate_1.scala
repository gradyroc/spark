package cn.grady.spark.scala.template

/**
 * @author rociss 
 * @version 1.0, on 0:34 2022/7/6.
 */
class ClassTemplate_1[T](val localId: T) {

  def getSchoolId(hukouId: T) = "s-" + hukouId + "-" + localId

}
